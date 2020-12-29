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
const NUMBER_KEY_COLUMN = 3;
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
    console.log(row, column, itemWidth, itemHeight, canvasWidth);
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
        url: '/api/patient',
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
    console.log('inflateViewByData', pageCount);
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
    id1 = setInterval(startTest, 3000);
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
 * @param bed 床位
 * @param key 字段
 * @param value 值
 */
function updateValue(bed, key, value) {
    // console.log(bed, key, value);
    var view = document.getElementById(key + "_" + bed);
    if (view) {
        view.innerHTML = value;
    }
}

/**
 * 根据上下限获取随机值
 * @param min
 * @param max
 * @returns {number} 随机值
 */
function getRandomValue(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
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
    let container = false;
    for(let x = 0;x<mDataKeys.length;x++){
        if(mDataKeys[x].code ==='NIBP'){
            container = true;
            break;
        }
    }
    let length = mDataKeys.length;
    if(container){
        length+=1;
    }
    let rows = length % NUMBER_KEY_COLUMN ===0?length / NUMBER_KEY_COLUMN:length / NUMBER_KEY_COLUMN+1;
    console.log('getKeyData',rows);
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
    return '<div class="key" style="color: ' + key.keyColor + ';height: '+h+'px">' +
        '<label class="key_title">' + key.code + '</label><span class="key_unit">(' + key.unit + ')</span>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max">' + key.max + '</label>' +
        '    <label class="key_threshold key_threshold_min">' + key.min + '</label>' +
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
    let szy = key.min.split('-');
    let ssy = key.max.split('-');
    return '  <div class="key key_nibp" style="color: ' + key.keyColor + ';height: '+h+'px">' +
        '<label class="key_title">' + key.code + '</label><span class="key_unit">' + key.unit + '</span>' +
        '<label class="key_send_value">-</label>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max">' + ssy[0] + '</label>' +
        '    <label class="key_threshold key_threshold_min">' + ssy[1] + '</label>' +
        '    <label class="key_value2" id="' + id + '" style="font-size: ' + key.keySize + 'px">-</label>' +
        '    <label class="key_threshold key_threshold_max2">' + szy[0] + '</label>' +
        '    <label class="key_threshold key_threshold_min2">' + szy[1] + '</label>' +
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




