var bedContainView;
var $hospitalSelect;
var mData;
var mPatientList;
var mWaveKeys = [];
var mDataKeys = [];
var id1 = -1;
var id2 = -1;
var itemWidth;
var itemHeight;
var canvasWidth;
var row = 3;
var column = 2;
var currentPage = 1;
const mBedAlarmConfigMap = new Map();
const mBedThresholdMap = new Map();
var currentAlarmInfo = {};
const cacheAlarmArray = new Map();
$(function () {
    // console.log(window.innerWidth,itemWidth, canvasWidth);
    window.onbeforeunload = function (ev) {
        clearData();
        restoreData();
        close();
    };
    initView();
    calculateViewSize();
    loadPatientDataAndTempData();
    // connectServer();

});

/**
 * 计算界面布局大小
 */
function calculateViewSize() {
    row = getBindData('row', '3');
    column = getBindData('column', '2');
    itemWidth = $(".bed-container").width() / parseInt(column);
    itemHeight = $(".bed-container").height() / parseInt(row);
    canvasWidth = Math.round(itemWidth * 0.6);
    // console.log(row, column, itemWidth, itemHeight, canvasWidth);
}

/**
 * 初始化布局
 * 床位卡片，
 * 病人过滤
 *
 */
function initView() {
    bedContainView = $(".bed-container");
    $hospitalSelect = $("#hospital_select");
    $hospitalSelect.combobox({
        onSelect: data => {
            // filterPatient(data.name);
        }
    });
    $.ajax({
        url: "/api/hospital",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $hospitalSelect.combobox({data: data.data});
            }
        },
        error: errorHandler
    });
    $("#view_config_save").on('click', () => {
        saveViewConfigInfo();
    });

    $("#view_config_close").on('click', () => {
        $("#view_config").dialog('close');
    });
}

/**
 * 加载当前使用的模板
 */
function loadPatientDataAndTempData() {

    $.ajax({
        url: '/api/template/enable',
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                //获取模板详情
                let id = data.data.id;
                doLoadPatientAndTempDetail(id);
            }
        },
        error: errorHandler
    });
}

/**
 * 记载病人信息及模板详情
 * 模板详情包括 波形字段、数值字段
 * @param id 模板id
 */
function doLoadPatientAndTempDetail(id) {
    let arr = [];
    arr.push($.ajax({
        url: '/user/bed/patient',
        type: 'get',
        dataType: 'json'
    }));
    arr.push($.ajax({
        url: '/api/template/detail/wave/' + id,
        type: 'get',
        dataType: 'json'
    }));
    arr.push($.ajax({
        url: '/api/template/detail/data/' + id,
        type: 'get',
        dataType: 'json'
    }));
    Promise.all(arr).then(res => {
        if (res[1].code === 200) {
            mWaveKeys = res[1].data; //缓存波形字段
        }
        if (res[2].code === 200) {
            mDataKeys = res[2].data; //缓存数值字段
        }
        if (res[0].code === 200) {
            mPatientList = res[0].data;
            mData = res[0].data;
            inflateViewByData(); //界面布局
        }

    });

    loadBedAlarmConfig();
    loadBedAlarmThreshold();
}

/**
 * 床架床位报警配置
 */
function loadBedAlarmConfig() {
    $.ajax({
        url: '/api/bedAlarm',
        type: 'get',
        dataType: 'json',
        success:function (data) {
            if(data.code ===200 &&data.data!==null){
                data.data.forEach(item=>{
                    mBedAlarmConfigMap.set(item.bedId,item);
                });
                // console.error(mBedAlarmConfigMap);
            }
        }
    });
}

/**
 * 加载床位报警阀值
 */
function loadBedAlarmThreshold() {
    $.ajax({
        url: "/api/bedThreshold",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200 && data.data!==null) {
                data.data.forEach(item=>{
                    mBedThresholdMap.set(item.patientId+"_"+item.keyCode,item);
                })
            }
        },
        error:errorHandler
    })
}

function filterPatient(h) {
    if (h === '全部医院') {
        mData = patient;
    } else {
        mData = patient.filter(item => item.hospital === h);
    }
    clearData();
    restoreData();
    inflateViewByData();
}

function clearData() {
    clearInterval(id1);
    clearInterval(id2);
}

/**
 * 根据病人信息布局床位界面
 */
function inflateViewByData() {
    //根据病人数据填充分页
    let pageCount;
    if (mData.length <= (column * row)) {
        pageCount = 1;
    } else {
        pageCount = mData.length % (column * row) === 0 ? mData.length / (column * row) : mData.length / (column * row) + 1;
    }
    // console.log('inflateViewByData', pageCount);
    $(".page").empty();
    for (let x = 1; x <= pageCount; x++) {
        // if (x < mData.length) {
        $(".page").append('<li class="page-item" onclick="pagePatient(this)">' + x + '</li>')
        // }
    }
    $(".page li:first-child").trigger('click');
    // bedContainView.empty();
    // for (var x = 0; x < mData.length; x++) {
    //     bedContainView.append($(getItemView(mData[x])));
    // }
    id1 = setInterval(startTest, 20*1000);
    id2 = setInterval(startTest2, 30 * 60 * 1000);
    startTest2();
}

/**
 * 按分页显示病人信息
 * @param e
 */
function pagePatient(e) {
    let val = parseInt($(e).text());
    $(e).addClass("active");
    $(e).siblings('li').removeClass('active');
    // console.log($(e).text());
    let start = (val - 1) * row * column;
    let end = val * row * column;
    bedContainView.empty();
    for (let x = start; x < end; x++) {
        if (x < mData.length) { //泛指越界
            bedContainView.append($(getItemView(mData[x])));
        }
    }
    restoreData();//先清除所有缓存
    bindViewData();
}

/**
 * 测试数据 ，实施更新除血压外的所有数据
 */
function startTest() {
    for (var x = 0; x < mData.length; x++) {
        let p = mData[x];
        for (let y = 0; y < mDataKeys.length; y++) {
            let key = mDataKeys[y];
            if (key.code !== 'NIBP') {
                let value = getRandomValue(parseInt(key.min), parseInt(key.max));
                updateValue(p.pid, key.code, value);
            }
        }
    }
}

/**
 * 测试数据，仅更新血压数据，每30分钟更新一次
 */
function startTest2() {
    for (var x = 0; x < mData.length; x++) {
        let p = mData[x];
        for (let y = 0; y < mDataKeys.length; y++) {
            let key = mDataKeys[y];
            if (key.code === 'NIBP') {
                let szy = key.min.split('-');
                let ssy = key.max.split('-');
                let value1 = getRandomValue(parseInt(szy[0]), parseInt(szy[1]));
                let value2 = getRandomValue(parseInt(ssy[0]), parseInt(ssy[1]));
                updateValue(p.pid, key.code, value2 + "/" + value1);
            }
        }
    }
}

/**
 * 更新界面数据值
 * @param pid 床位
 * @param key 字段
 * @param value 值
 */
function updateValue(pid, key, value) {
    var view = document.getElementById(key + "_" + pid);
    if (view) {
        view.innerHTML = value;
    }

    //根据pid 查找bedid
    let patients = mData.filter(item=>item.pid === pid);
    if(patients.length>0){
        let config =  mBedAlarmConfigMap.get(patients[0].bedId);
        if(config.enable){
            //从床位报警阀值中获取
            let threshold = mBedThresholdMap.get(patients[0].id+"_"+key);
            if(threshold ===undefined ||threshold ===null){
                //如果没有，则使用模板配置报警阀值
                let arr = mDataKeys.filter(item=>item.code === key);
                if(arr.length>0){
                    judgeIsAlarm(pid,key,value,arr[0].min,arr[0].max,patients[0]);
                }
            }else {
                judgeIsAlarm(pid,key,value,threshold.min,threshold.max,patients[0]);
            }
        }
    }
}

/**
 * 判断是否报警
 * @param pid 病人pid
 * @param key 报警关键字
 * @param value 报警值
 * @param min 最小值
 * @param max 最大是
 * @param patient 病人信息
 */
function judgeIsAlarm(pid,key,value,min,max,patient) {
    // console.error('judgeIsAlarm',pid,key,value,min,max);
    let msg=undefined;
    if(key === 'NIBP'){
        let szy = min.split("/");
        let ssy = max.split("/");
        let val = value.split("/");
        if(Number(val[0])<Number(szy[0])){
            msg = '舒张压过低';
        }else if(Number(val[0])>Number(szy[1])){
            msg = '舒张压过高';
        }

        if(Number(val[1])<Number(ssy[0])){
            msg = '收缩压过低';
        }else if(Number(val[1])>Number(ssy[1])){
            msg = '收缩压过高';
        }
    }else {
        if(Number(value)<Number(min)){
            msg = key+"过低"
        }else if(Number(value)>Number(max)){
            msg = key+"过高"
        }
    }
    if(msg!==undefined && msg.length>0){
        currentAlarmInfo = {pid:pid,alarmType:'暂未关联类型',alarmKey:key,alarmValue:value,alarmMsg:msg,min:min,max:max
        ,pat:{...patient}};

        saveOrUpdateAlarmInfo(currentAlarmInfo,'post');
        showAlarmDialog();
    }
}

/**
 * 根据上下限获取随机值
 * @param min
 * @param max
 * @returns {number} 随机值
 */
function getRandomValue(min, max) {
    return Math.floor(Math.random() * (max - min)) + min+2;
}

/**
 * 获取床位布局
 * @param patient 病人信息
 * @returns {string} 布局
 */
function getItemView(patient) {
    return '<div class="bed-item" style="height: ' + itemHeight + 'px;width: ' + itemWidth + 'px">'
        + getBedItemTitle(patient)
        + getBedContentView(patient)
        +
        '</div>'
}

/**
 * 获取床位头部病人信息
 * @param patient 病人信息
 * @returns {string} 布局
 */
function getBedItemTitle(patient) {
    let gender;
    if (patient.gender === 1) {
        gender = '男';
    } else if (patient.gender === 2) {
        gender = '女';
    } else {
        gender = '未知';
    }
    let config = mBedAlarmConfigMap.get(patient.bedId);
    // console.error('getBedItemTitle',config);
    let imageView;
    if(config ===undefined || config ===null || !config.enable){
        imageView =   '<img src="/static/images/ic_disable_alarm.png" id="BED_ID_'+patient.bedId+'" style="float: right;" onclick="enableBedAlarm(this)">';
    }else {
        imageView =   '<img src="/static/images/ic_alarm.png" id="BED_ID_'+patient.bedId+'" style="float: right;" onclick="enableBedAlarm(this)">';
    }
    return '<div class="item-title">' +
        '<img src="/static/images/bed.png">' +
        '<label id=' + "BED_" + patient.pid + '>' + patient.bed + ' 床</label>' +
        '<label id=' + "NAME_" + patient.pid + '>' + patient.name + '</label>' +
        '<label id=' + "GENDER_" + patient.pid + '>' + gender + '</label>' +
        '<label id=' + "AGE_" + patient.pid + '>' + patient.age + '岁</label>' +
        '<label id=' + "HOSPITAL_" + patient.pid + '>医院:' + patient.hospitalName + '</label>' +
        '<label id=' + "DEPT_" + patient.pid + '>科室:' + patient.dept + '</label>' +
        '<label id=' + "COMPLAINT_" + patient.pid + ' class="complaint-desc" onclick="showComplaint(this)"><span class="iconfont" style="font-size: 18px">&#xe773;</span>主诉</label>' +
        // '<div class="complaint" style="height: '+(itemHeight-50)+'px;width: '+(itemWidth*0.25)+'px">'+patient.complaint+'</div>'+
            imageView+
        '</div>'
}

/**
 * 获取床位内容布局，包括曲线和数据
 * @param patient
 * @returns {string}
 */
function getBedContentView(patient) {
    return '<div class="item-container">' +
        getBedContentLeftView(patient)
        + getBedContentRightView(patient)
        +
        '</div>'
}

/**
 * 获取床位曲线布局
 * <p>
 *     包括背景网格和曲线字段
 * </p>
 * @param patient 病人信息
 * @returns {string} 布局
 */
function getBedContentLeftView(patient) {
    let canvasBgHeight = itemHeight - 32;
    let canvasKeyHeight = canvasBgHeight / mWaveKeys.length;
    return '<div class="item-container-left" id="size-of-chart">' +
        '    <div class="boack">' +
        '        <canvas id="background_' + patient.pid + '" width="' + canvasWidth + 'px" height="' + canvasBgHeight + 'px">' +
        '        </canvas>' +
        '    </div>' +
        '    <div class="boack">' +
        getKeyCanvas(patient, canvasKeyHeight) +
        '    </div>' +
        '</div>'
}

/**
 * 获取曲线布局
 *        <p>
 *        '        <canvas id="line_key1_' + patient.bed + '" width="500px" height="62px"></canvas>' +
 '        <canvas id="line_key2_' + patient.bed + '" width="500px" height="62px"></canvas>' +
 '        <canvas id="line_key3_' + patient.bed + '" width="500px" height="62px"></canvas>' +
 '        <canvas id="line_key4_' + patient.bed + '" width="500px" height="62px"></canvas>' +
 <p>
 * @param patient 病人信息
 * @param canvasKeyHeight 曲线宽度
 */
function getKeyCanvas(patient, canvasKeyHeight) {
    let view = '';
    for (let x = 0; x < mWaveKeys.length; x++) {
        let id = `line_${mWaveKeys[x].code}_${patient.pid}`;
        view += '<canvas id="' + id + '" width="' + canvasWidth + 'px" height="' + canvasKeyHeight + 'px"></canvas>'
    }
    return view;
}

/**
 * 获取数值字段布局信息
 * @param patient 病人
 * @returns {string} 布局
 */
function getBedContentRightView(patient) {
    return '<div class="item-container-right">' +
        getKeyData(patient) +
        '</div>';
}

/**
 * 获取字段项目布局信息
 * <p>
 *     包括特殊字段和不同字段
 *     </p>
 * @param patient
 * @returns {string}
 */
function getKeyData(patient) {
    let rows = mDataKeys.length / 3;
    let rowHeight = (itemHeight-32)/rows;
    let view = '';
    for (let x = 0; x < mDataKeys.length; x++) {
        let item = mDataKeys[x];
        let id = item.code + "_" + patient.pid;
        if (item.code === 'NIBP') {
            view += getKeyItemSpecial(id, item,rowHeight);
        } else {
            view += getKeyItem(id, item,rowHeight);
        }
    }
    return view;
}

/**
 * 获取普通字段布局信息
 * @param id  布局id
 * @param key 字段
 * @returns {string} 布局
 * @param h 高度
 */
function getKeyItem(id, key,h) {
    let keyTitleSize =key.keySize/2;
    let keyThresholdSize = keyTitleSize-2;
    let keyUnitSize = keyThresholdSize-2;
    let w = parseInt(itemWidth*0.4/3)-1;
    return '<div class="key" style="color: ' + key.keyColor + ';height: '+h+'px;width: '+w+'px">' +
        '<label class="key_title" style="font-size: '+keyTitleSize+'px">' + key.code + '</label>' +
        '<span class="key_unit"  style="font-size: '+keyUnitSize+'px">(' + key.unit + ')</span>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max"  style="font-size: '+keyThresholdSize+'px">' + key.max + '</label>' +
        '    <label class="key_threshold key_threshold_min"  style="font-size: '+keyThresholdSize+'px">' + key.min + '</label>' +
        '    <label class="key_value" id="' + id + '" style="font-size: ' + key.keySize + 'px">-</label>' +
        '</div>' +
        '</div>';
}

/**
 * 获取特殊字段布局
 * @param id 布局id
 * @param key 字段
 * @returns {string} 布局
 * @param h 高度
 */
function getKeyItemSpecial(id, key,h) {
    let keyTitleSize =key.keySize/2;
    let keyThresholdSize = keyTitleSize-2;
    let keyUnitSize = keyThresholdSize-2;
    let w = parseInt(itemWidth*0.4/3)-1;
    let szy = key.min.split('-');
    let ssy = key.max.split('-');
    return '  <div class="key key_nibp" style="color: ' + key.keyColor + ';height: '+h+'px;width: '+(w*2)+'px">' +
        '<label class="key_title" style="font-size: '+keyTitleSize+'px">' + key.code + '</label>' +
        '<span class="key_unit" style="font-size: '+keyUnitSize+'px">' + key.unit + '</span>' +
        '<label class="key_send_value">-</label>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max" style="font-size: '+keyThresholdSize+'px">' + ssy[0] + '</label>' +
        '    <label class="key_threshold key_threshold_min" style="font-size: '+keyThresholdSize+'px">' + ssy[1] + '</label>' +
        '    <label class="key_value2" id="' + id + '" style="font-size: ' + key.keySize + 'px">-</label>' +
        '    <label class="key_threshold key_threshold_max2" style="font-size: '+keyThresholdSize+'px">' + szy[0] + '</label>' +
        '    <label class="key_threshold key_threshold_min2" style="font-size: '+keyThresholdSize+'px">' + szy[1] + '</label>' +
        '</div>' +
        '</div>'
}

/**
 * 显示界面布局对话框
 * 主要用来设置每页床位显示的数量
 */
function showViewDialog() {
    $("#view_config").dialog({
        onOpen: () => {
            bindDefaultViewData();
        }
    });
    $("#view_config").dialog('open');
}

function bindDefaultViewData() {
    let val = getBindData('row', '3');
    $("#row").numberbox('setValue', val);

    val = getBindData('column', '2');
    $("#column").numberbox('setValue', val);
}

function saveViewConfigInfo() {
    let form = $("#ff");
    if (form.form('validate')) {
        let data = form.serializeObject();
        for (let key in data) {
            localStorage.setItem(key, data[key]);
        }
        showToast('提示', '保存成功');
        //重新加载布局
        //清楚数据
        clearData();
        restoreData();
        //重新计算大小
        calculateViewSize();
        inflateViewByData();
    } else {
        showToast('警告', '存在校验未通过项目');
    }
}

function getBindData(key, defaultValue) {
    let val = localStorage.getItem(key);
    if (val === undefined || val === null) {
        val = defaultValue;
    }
    return val;
}


function showComplaint(e) {
    let id = $(e).attr('id');
    let ids = id.split('_');
    let pid = ids[1];

    let currentPatient = undefined;

    for (let x = 0; x < mData.length; x++) {
        if (mData[x].pid === pid) {
            currentPatient = mData[x];
            break;
        }
    }

    $("#complaint_config").dialog({
        title: `${currentPatient.bed} 床详情`,
        onOpen: () => {
            let val = 'PID：' + currentPatient.pid + "<br/>";
            val += '住院号：' + currentPatient.pid + "<br/>";
            val += '患者主诉：<strong>' + currentPatient.complaint + "</strong><br/>";
            $('#patient-detail').html(val);
        },
        onClose: () => {
            currentPatient = undefined;
        }
    });
    $("#complaint_config").dialog('open');

}

function enableBedAlarm(e) {
    let id = $(e).attr('id');
    let bedId = id.replace('BED_ID_','');
    let config = mBedAlarmConfigMap.get(parseInt(bedId));
    if(config ===undefined || config ===null){
        config = {bedId:bedId,enable:true};
    }else{
        config.enable = !config.enable;
    }
    if(config.id){
        doSaveOrUpdateBedAlarmConfigInfo(config,'put');
    }else{
        doSaveOrUpdateBedAlarmConfigInfo(config,'POST');
    }
}



function doSaveOrUpdateBedAlarmConfigInfo(config, method) {
    $.ajax({
        url: '/api/bedAlarm',
        type: method,
        data: JSON.stringify(config),
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadBedAlarmConfig();
                if(config.enable){
                    $("#BED_ID_"+config.bedId).attr('src','/static/images/ic_alarm.png');
                }else {
                    $("#BED_ID_"+config.bedId).attr('src','/static/images/ic_disable_alarm.png');
                }
            }
        },
        error:errorHandler
    })
}

function showAlarmDialog() {
    let patient = currentAlarmInfo.pat;
    let content = `${patient.name}/${patient.age}岁/${patient.gender===1?'男':'女'}/${patient.hospitalName}/${patient.dept}/${patient.bed}`
    $(".alarm-title").text(currentAlarmInfo.alarmType);
    $(".alarm-content").text(content);
    $(".alarm-msg").text(currentAlarmInfo.alarmMsg);
    $(".alarm-times").text(getCurrentYMDHMS());
    // $("#alarm_dialog").dialog({
    //     onOpen:function () {
    //
    //     }
    // });
    $("#alarm_dialog").dialog('open');
}

function saveOrUpdateAlarmInfo(alarm,method) {
    $.ajax({
        url:'/api/alarmInfo',
        method:method,
        data:JSON.stringify(alarm),
        contentType:'application/json',
        success:function (data) {
            if(data.code === 200){
                alarm.id = data.data;
                let item = {...alarm};
                if(method ==='post'){
                    cacheAlarmArray.set(data.data,item);
                }else {
                    cacheAlarmArray.delete(data.data);
                    if(cacheAlarmArray.size === 0){
                        $("#alarm_dialog").dialog('close');
                    }else {
                        for(let item of cacheAlarmArray.keys()){
                            currentAlarmInfo = cacheAlarmArray.get(item);
                            showAlarmDialog();
                            break;
                        }
                    }
                }
            }else {
                showToast('提示',data.message);
            }
        },
        error:errorHandler
    })
}

function dealCurrentAlarmInfo() {
    if(currentAlarmInfo!==null){
        currentAlarmInfo.handle = true;
        saveOrUpdateAlarmInfo(currentAlarmInfo,'put');
    }
}


