var bedContainView;
var mData;
$(function () {
    initView();
});

function initView() {
    bedContainView = $(".bed-container");
    mData = patient;
    inflateViewByData();
    bindViewData();
}

function inflateViewByData() {
    bedContainView.empty();
    for (var x = 0; x < mData.length; x++) {
        bedContainView.append($(getItemView(mData[x])));
    }
}

function getItemView(patient) {
    return '<div class="bed-item">'
        + getBedItemTitle(patient)
        + getBedContentView(patient)
        +
        '</div>'
}

function getBedItemTitle(patient) {
    return '<div class="item-title">' +
        '<label id=' + "BED_" + patient.bed + '>' + patient.bedLabel + '</label>' +
        '<label id=' + "NAME_" + patient.bed + '>' + patient.name + '</label>' +
        '<label id=' + "GENDER_" + patient.bed + '>' + patient.gender + '</label>' +
        '<label id=' + "AGE_" + patient.bed + '>' + patient.age + '</label>' +
        '<label id=' + "HOSPITAL_" + patient.bed + '>医院:' + patient.hospital + '</label>' +
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
        '        <canvas id="background_' + patient.bed + '" width="500px" height="248px">' +
        '        </canvas>' +
        '    </div>' +
        '    <div class="boack">' +
        '        <canvas id="line_' + patient.bed + '" width="500px" height="248px">' +
        '        </canvas>' +
        '    </div>' +
        '</div>'
}

function getBedContentRightView(patient) {
    return '<div class="item-container-right">' +
        getHRView(patient) +
        getRRView(patient) +
        getSpO2View(patient) +
        getNIBPView(patient) +
        getPRView(patient) +
        getTView(patient) +
        '</div>'
}

function getHRView(patient) {
    return '<div class="hr">' +
        '<label class="key_title">HR</label><span class="key_unit">' + patient.hr.unit + '</span>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max">' + patient.hr.max + '</label>' +
        '    <label class="key_threshold key_threshold_min">' + patient.hr.min + '</label>' +
        '    <label class="key_value">' + patient.hr.value + '</label>' +
        '</div>' +
        '</div>'
}

function getRRView(patient) {
    return '<div class="rr">' +
        '<label class="key_title">Resp</label><span class="key_unit">' + patient.Resp.unit + '</span>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max">' + patient.Resp.max + '</label>' +
        '    <label class="key_threshold key_threshold_min">' + patient.Resp.min + '</label>' +
        '    <label class="key_value">' + patient.Resp.value + '</label>' +
        '</div>' +
        '</div>'
}

function getSpO2View(patient) {
    return '<div class="spo2">' +
        '<label class="key_title">SpO2</label><span class="key_unit">' + patient.spo2.unit + '</span>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max">' + patient.spo2.max + '</label>' +
        '    <label class="key_threshold key_threshold_min">' + patient.spo2.min + '</label>' +
        '    <label class="key_value">' + patient.spo2.value + '</label>' +
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
        '    <label class="key_value2">' + patient.nibp.value1 + '</label>' +
        '    <label class="key_threshold key_threshold_max2">' + patient.nibp.max2 + '</label>' +
        '    <label class="key_threshold key_threshold_min2">' + patient.nibp.min2 + '</label>' +
        '</div>' +
        '<div class="key_time">测量时间:' + patient.nibp.time +
        '</div>' +
        '</div>'
}

function getPRView(patient) {
    return ' <div class="pr">' +
        '<label class="key_title">PR</label><span class="key_unit">' + patient.pr.unit + '</span>' +
        '<div class="key_content">' +
        '    <label class="key_threshold key_threshold_max">' + patient.pr.max + '</label>' +
        '    <label class="key_threshold key_threshold_min">' + patient.pr.min + '</label>' +
        '    <label class="key_value">' + patient.pr.value + '</label>' +
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


