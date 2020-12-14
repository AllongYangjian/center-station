var bedContainView;
var $hospitalSelect;
var mData;
var mPatientList;
var mWaveKeys = [];
var mDataKeys = [];
var id1 = -1;
var id2 = -1;
var itemWidth;
var canvasWidth;
$(function () {
    itemWidth = $(".bed-container").width() / 2;
    canvasWidth = Math.round(itemWidth * 0.6) - 10;
    // console.log(window.innerWidth,itemWidth, canvasWidth);
    window.onbeforeunload = function (ev) {
        clearData();
        restoreData();
        close();
    };
    initView();
    loadPatientDataAndTempData();
    // connectServer();

});

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
        }
    })
}

/**
 * 加载用户信息
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
        }
    });
}

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
            mWaveKeys = res[1].data;
        }
        if (res[2].code === 200) {
            mDataKeys = res[2].data;
        }
        if (res[0].code === 200) {
            mPatientList = res[0].data;
            mData = res[0].data;
            inflateViewByData();
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
    bedContainView.empty();
    for (var x = 0; x < mData.length; x++) {
        bedContainView.append($(getItemView(mData[x])));
    }
    bindViewData();
    id1 = setInterval(startTest, 1000);
    id2 = setInterval(startTest2, 30 * 60 * 1000);
    startTest2();
}

function startTest() {
    for (var x = 0; x < mData.length; x++) {
        let p = mData[x];
        for (let y = 0; y < mDataKeys.length; y++) {
            let key = mDataKeys[y];
            if (key.code !== 'NIBP') {
                let value = getRandomValue(parseInt(key.min), parseInt(key.max));
                updateValue(p.bed, key.code, value);
            }
        }
    }
}

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
                updateValue(p.bed, key.code, value1 + "/" + value2);
            }
        }
    }
}

function updateValue(bed, key, value) {
    // console.log(bed, key, value);
    var view = document.getElementById(key + "_" + bed);
    if (view) {
        view.innerHTML = value;
    }
}

function getRandomValue(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}

function getItemView(patient) {
    return '<div class="bed-item">'
        + getBedItemTitle(patient)
        + getBedContentView(patient)
        +
        '</div>'
}

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
        '<label id=' + "BED_" + patient.bed + '>' + patient.bed + ' 床</label>' +
        '<label id=' + "NAME_" + patient.bed + '>' + patient.name + '</label>' +
        '<label id=' + "GENDER_" + patient.bed + '>' + gender + '</label>' +
        '<label id=' + "AGE_" + patient.bed + '>' + patient.age + '岁</label>' +
        '<label id=' + "HOSPITAL_" + patient.bed + '>医院:' + patient.hospitalName + '</label>' +
        '<label id=' + "DEPT_" + patient.bed + '>科室:' + patient.dept + '</label>' +
        '</div>'
}

function getBedContentView(patient) {
    return '<div class="item-container">' +
        getBedContentLeftView(patient)
        + getBedContentRightView(patient)
        +
        '</div>'
}


function getBedContentLeftView(patient) {
    return '<div class="item-container-left" id="size-of-chart">' +
        '    <div class="boack">' +
        '        <canvas id="background_' + patient.bed + '" width="' + canvasWidth + 'px" height="248px">' +
        '        </canvas>' +
        '    </div>' +
        '    <div class="boack">' +
        getKeyCanvas(patient) +
        '    </div>' +
        '</div>'
}

/**
 *         '        <canvas id="line_key1_' + patient.bed + '" width="500px" height="62px"></canvas>' +
 '        <canvas id="line_key2_' + patient.bed + '" width="500px" height="62px"></canvas>' +
 '        <canvas id="line_key3_' + patient.bed + '" width="500px" height="62px"></canvas>' +
 '        <canvas id="line_key4_' + patient.bed + '" width="500px" height="62px"></canvas>' +
 * @param patient
 */
function getKeyCanvas(patient) {
    let view = '';
    for (let x = 0; x < mWaveKeys.length; x++) {
        let id = `line_${mWaveKeys[x].code}_${patient.bed}`;
        view += '<canvas id="' + id + '" width="' + canvasWidth + 'px" height="62px"></canvas>'
    }
    return view;
}

function getBedContentRightView(patient) {
    return '<div class="item-container-right">' +
        getKeyData(patient) +
        '</div>';
}

function getKeyData(patient) {
    let view = '';
    for (let x = 0; x < mDataKeys.length; x++) {
        let item = mDataKeys[x];
        let id = item.code + "_" + patient.bed;
        if (item.code === 'NIBP') {
            view += getKeyItemSpecial(id, item);
        } else {
            view += getKeyItem(id, item);
        }
    }
    return view;
}

function getKeyItem(id, key) {
    return '<div class="key" style="color: ' + key.keyColor + '">' +
        '<label class="key_title">' + key.name + '</label><span class="key_unit">(' + key.unit + ')</span>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max">' + key.max + '</label>' +
        '    <label class="key_threshold key_threshold_min">' + key.min + '</label>' +
        '    <label class="key_value" id="' + id + '">-</label>' +
        '</div>' +
        '</div>';
}

function getKeyItemSpecial(id, key) {
    let szy = key.min.split('-');
    let ssy = key.max.split('-');
    return '  <div class="key key_nibp" style="color: ' + key.keyColor + '">' +
        '<label class="key_title">' + key.name + '</label><span class="key_unit">' + key.unit + '</span>' +
        '<label class="key_send_value">-</label>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max">' + ssy[0] + '</label>' +
        '    <label class="key_threshold key_threshold_min">' + ssy[1] + '</label>' +
        '    <label class="key_value2" id="' + id + '">-</label>' +
        '    <label class="key_threshold key_threshold_max2">' + szy[0] + '</label>' +
        '    <label class="key_threshold key_threshold_min2">' + szy[1] + '</label>' +
        '</div>' +
        '</div>'
}

function getHRView(patient) {
    return '<div class="hr">' +
        '<label class="key_title">HR</label><span class="key_unit">' + patient.hr.unit + '</span>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max">' + patient.hr.max + '</label>' +
        '    <label class="key_threshold key_threshold_min">' + patient.hr.min + '</label>' +
        '    <label class="key_value" id="' + patient.hr.key + '_' + patient.bed + '">' + patient.hr.value + '</label>' +
        '</div>' +
        '</div>'
}

function getRRView(patient) {
    return '<div class="rr">' +
        '<label class="key_title">Resp</label><span class="key_unit">' + patient.Resp.unit + '</span>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max">' + patient.Resp.max + '</label>' +
        '    <label class="key_threshold key_threshold_min">' + patient.Resp.min + '</label>' +
        '    <label class="key_value" id="' + patient.Resp.key + '_' + patient.bed + '">' + patient.Resp.value + '</label>' +
        '</div>' +
        '</div>'
}

function getSpO2View(patient) {
    return '<div class="spo2">' +
        '<label class="key_title">SpO2</label><span class="key_unit">' + patient.spo2.unit + '</span>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max">' + patient.spo2.max + '</label>' +
        '    <label class="key_threshold key_threshold_min">' + patient.spo2.min + '</label>' +
        '    <label class="key_value" id="' + patient.spo2.key + '_' + patient.bed + '">' + patient.spo2.value + '</label>' +
        '</div>' +
        '</div>'
}

function getNIBPView(patient) {
    return '  <div class="nibp">' +
        '<label class="key_title">NIBP</label><span class="key_unit">' + patient.nibp.unit + '</span>' +
        '<label class="key_send_value">' + patient.nibp.value2 + '</label>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max">' + patient.nibp.max1 + '</label>' +
        '    <label class="key_threshold key_threshold_min">' + patient.nibp.min1 + '</label>' +
        '    <label class="key_value2" id="' + patient.nibp.key + '_' + patient.bed + '">' + patient.nibp.value1 + '</label>' +
        '    <label class="key_threshold key_threshold_max2">' + patient.nibp.max2 + '</label>' +
        '    <label class="key_threshold key_threshold_min2">' + patient.nibp.min2 + '</label>' +
        '</div>' +
        '</div>'
}

function getPRView(patient) {
    return ' <div class="pr">' +
        '<label class="key_title">PR</label><span class="key_unit">' + patient.pr.unit + '</span>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max">' + patient.pr.max + '</label>' +
        '    <label class="key_threshold key_threshold_min">' + patient.pr.min + '</label>' +
        '    <label class="key_value" id="' + patient.pr.key + '_' + patient.bed + '">' + patient.pr.value + '</label>' +
        '</div>' +
        '<div class="key_time">' +
        '    &nbsp;' +
        '</div>' +
        '</div>'
}

function getTView(patient) {
    return '  <div class="t">' +
        '    <label>T1：</label><label>' + patient.t1.value + '</label>' +
        '    <label class="key_unit2">' + patient.t1.unit + '</label>' +
        '</div>' +
        '<div class="t t2">' +
        '    <label>T2：</label><label>' + patient.t2.value + '</label>' +
        '    <label class="key_unit2">' + patient.t2.unit + '</label>' +
        '</div>' +
        '<div class="t td">' +
        '    <label>TD：</label><label>' + patient.td.value + '</label>' +
        '</div>'
}


