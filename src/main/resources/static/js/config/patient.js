const columns = [[
    {
        field: 'ck',
        checkbox: true
    },
    {
        title: 'ID',
        field: 'id',
        align: 'center',
        width: 1,
        hidden: true
    },
    {
        title: '医院',
        field: 'hospitalName',
        align: 'center',
        width: 1
    },
    {
        title: '科室',
        field: 'dept',
        align: 'center',
        width: 1
    },
    {
        title: '床号',
        field: 'bed',
        align: 'center',
        width: 1
    },
    {
        title: 'PID',
        field: 'pid',
        align: 'center',
        width: 1
    },
    {
        title: '住院号',
        field: 'zyh',
        align: 'center',
        width: 1
    },
    {
        title: '姓名',
        field: 'name',
        align: 'center',
        width: 1
    },
    {
        title: '性别',
        field: 'gender',
        align: 'center',
        width: 1,
        formatter: (value, row, index) => {
            if (value === 1) {
                return '<span>男</span>'
            } else if (value === 2) {
                return '<span>女</span>'
            } else {
                return '<span>未知</span>'
            }
        }
    },
    {
        title: '年龄',
        field: 'age',
        align: 'center',
        width: 1
    },
    {
        title: '主诉',
        field: 'complaint',
        align: 'center',
        width: 3
    },
    {
        title: '登记时间',
        field: 'recordTime',
        align: 'center',
        width: 2
    },
    {
        title: '登记人',
        field: 'recordUser',
        align: 'center',
        width: 1
    },
    {
        title: '当前状态',
        field: 'status',
        align: 'center',
        width: 1,
        formatter: (value, row, index) => {
            if (value===1) {
                return '<span style="color: red">监护中...</span>'
            } else {
                return '<span>无需监护</span>'
            }
        }
    },
    {
        title: '操作',
        field: 'edit',
        align: 'center',
        width: 1,
        formatter: (value, row, index) => {
            if (row.status) {
                return '<input type="button" value="取消监护" onclick="monitorItem(' + index + ')">'
            } else {
                return '<input type="button" value="查看" onclick="monitorItem(' + index + ')">'
            }
        }
    }
]];

const enableData = [
    {
        id: 0,
        name: '否'
    },
    {
        id: 1,
        name: '是'
    }
];

const genderData = [
    {
        id: 1,
        name: '男'
    },
    {
        id: 2,
        name: '女'
    },
    {
        id: 3,
        name: '未知'
    }
];


var $patientTable;
var currentItem;
var currentHospitalId;

$(function () {
    initPatientTable();

    $("#add").on('click', () => {
        currentItem = undefined;
        $patientTable.datagrid('uncheckAll');
        showPatientDialog();
    });

    $("#edit").on('click', () => {
        if (currentItem) {
            showPatientDialog();
        } else {
            showToast('提示', '请选择要修改的数据');
        }
    });

    $("#delete").on('click', () => {
        if (currentItem) {
            showDeleteToast(doDeletePatientRecord);
        } else {
            showToast('提示', '请选择要删除的数据');
        }
    });

    $("#query").on('click', () => {
        loadPatientData();
    });

    $("#reset").on('click', () => {
        currentHospitalId = undefined;
        $("#hospital_criteria").combobox('setValue', '');
        loadPatientData();
    });

    $("#patient_save").on('click', () => {
        savePatientInfo();
    });

    $("#patient_close").on('click', () => {
        $("#patient_dialog").dialog('close');
    });

    $("#reload_hospital").on('click', () => {
        loadHospitalData();
    });

    $("#hospital_criteria").combobox({
        onSelect: item => {
            currentHospitalId = item.id;
            loadPatientData();
        }
    });

    loadPatientData();
    loadHospitalData();

});

function loadHospitalData() {
    $.ajax({
        url: "/api/hospital",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $("#hospitalId").combobox({data: data.data});
                $("#hospital_criteria").combobox({data: data.data});
            }
        },
        error: errorHandler
    })
}


/**
 * 初始化用户信息表单
 */
function bindPatientFormData() {
    if (currentItem) {
        $("#id").textbox('setValue', currentItem.id);
        $("#hospitalId").combobox('setValue', currentItem.hid);
        $("#dept").textbox('setValue', currentItem.dept);
        $("#bed").textbox('setValue', currentItem.bed);
        $("#pid").textbox('setValue', currentItem.pid);
        $("#zyh").textbox('setValue', currentItem.zyh);
        $("#name").textbox('setValue', currentItem.name);
        $("#gender").combobox('setValue', currentItem.gender);
        $("#age").numberspinner('setValue', currentItem.age);
        $("#complaint").textbox('setValue', currentItem.complaint);
        $("#status").combobox('setValue', currentItem.status);
    }
}

/**
 * 重置用户信息表单
 */
function resortPatientFormData() {
    // $("#ff")[0].reset(); //并没有删除原来的值
    $("#id").textbox('setValue', '');
    $("#hospitalId").combobox('setValue', '');
    $("#dept").textbox('setValue', '');
    $("#bed").textbox('setValue', '');
    $("#pid").textbox('setValue', '');
    $("#zyh").textbox('setValue', '');
    $("#name").textbox('setValue', '');
    $("#gender").combobox('setValue', '');
    $("#age").numberspinner('setValue', '');
    $("#complaint").textbox('setValue', '');
    $("#status").combobox('setValue', '');
}

/**
 * 显示用户信息表单对话框
 */
function showPatientDialog() {
    $("#patient_dialog").dialog({
        onOpen: () => {
            bindPatientFormData();
        },
        onClose: () => {
            resortPatientFormData();
        }
    });
    $("#patient_dialog").dialog('open');
}

/**
 * 初始化用户信息
 */
function initPatientTable() {
    $patientTable = $("#patient_list");
    $patientTable.datagrid({
        rownumbers: true,
        showFooter: true,
        fitColumns: true,
        pagination: true,
        remoteSort: false,
        singleSelect: true,
        pagePosition: 'bottom',
        pageNumber: 1,
        pageSize: 20,
        columns: columns,
        onCheck: (index, data) => {
            currentItem = data;
        },
        onUncheck: (index, data) => {
            currentItem = undefined;
        },
        loadFilter: pagerFilter
    });
}

/**
 * 记载用户信息
 */
function loadPatientData() {
    let url = '';
    if (currentHospitalId !== undefined) {
        url = "/api/patient/status/1" + currentHospitalId;
    } else {
        url = "/api/patient/status/1";
    }
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $patientTable.datagrid({data: data.data});
            }
        },
        error: errorHandler
    })
}

function savePatientInfo() {
    let form = $("#ff");
    if (form.form('validate')) {
        let data = form.serializeObject();
        // console.log(data);
        if (data.id) {
            doSaveOrUpdatePatientInfo(data, 'PUT');
        } else {
            doSaveOrUpdatePatientInfo(data, 'POST');
        }

    } else {
        showToast('警告', '存在校验未通过项目');
    }
}

function doSaveOrUpdatePatientInfo(data, method) {
    $.ajax({
        url: '/api/patient',
        type: method,
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadPatientData();
                resortPatientFormData();
            }
        },
        error: errorHandler
    })
}

function doDeletePatientRecord() {
    $.ajax({
        url: '/api/patient/' + currentItem.id,
        type: 'DELETE',
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadPatientData();
            }
        },
        error: errorHandler
    })
}

function monitorItem(index) {
    $patientTable.datagrid('checkRow', index);
    var rows = $patientTable.datagrid('getChecked');
    if (rows.length > 0) {
        rows[0].status = Math.abs(rows[0].status - 1);
        console.log(rows[0]);
        doSaveOrUpdatePatientInfo(rows[0], 'put');
    }
}

