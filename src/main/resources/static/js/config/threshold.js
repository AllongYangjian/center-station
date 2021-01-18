const columns = [[
    {
        field: 'ck',
        checkbox: true
    },
    {
        title: 'ID',
        field: 'id',
        align: 'center',
        width: 1
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
        title: '姓名',
        field: 'name',
        align: 'center',
        width: 1
    },
    {
        title: '关键字代码',
        field: 'keyCode',
        align: 'center',
        width: 1
    },
    {
        title: '关键字名称',
        field: 'keyName',
        align: 'center',
        width: 1
    },
    {
        title: '下限',
        field:'min',
        align: 'center',
        width: 1
    },
    {
        title: '上限',
        field:'max',
        align: 'center',
        width: 1
    },
    {
        title: '是否启用',
        field:'enable',
        align: 'center',
        width: 1,
        formatter:(value,row,index)=>{
            if(value){
                return '<span style="color: green">启用</span>'
            }else {
                return '<span style="color:grey;">未启用</span>'
            }

        }
    },
    {
        title:'操作',
        field:'edit',
        align:'center',
        formatter:(value,row,index)=>{
            if(row.enable){
                return '<input type="button" value="禁用" onclick="updateItemEnable('+index+')">'
            }else {
                return '<input type="button" value="启用"  onclick="updateItemEnable('+index+')">'
            }

        }
    }
]];

const enableData = [
    {
        id: false,
        name: '否'
    },
    {
        id: true,
        name: '是'
    }
];

var $alarmConfigTable;
var currentItem;

$(function () {
    initAlarmConfigTable();

    $("#add").on('click', () => {
        currentItem = undefined;
        $alarmConfigTable.datagrid('uncheckAll');
        showAlarmConfigDialog();
    });

    $("#edit").on('click', () => {
        if (currentItem) {
            showAlarmConfigDialog();
        } else {
            showToast('提示', '请选择要修改的数据');
        }
    });

    $("#delete").on('click', () => {
        if (currentItem) {
            showDeleteToast(doDeleteAlarmConfigRecord);
        } else {
            showToast('提示', '请选择要删除的数据');
        }
    });

    $("#query").on('click', () => {
        loadAlarmConfigData();
    });

    $("#alarm_config_save").on('click', () => {
        saveAlarmConfigInfo();
    });

    $("#alarm_config_close").on('click', () => {
        $("#alarm_config_dialog").dialog('close');
    });

    $("#keyId").combobox({
        onSelect:function (data) {
            $("#min").textbox('setValue',data.min);
            $("#max").textbox('setValue',data.max);
        }
    });

    loadAlarmConfigData();
    loadPatientData();
    loadKeyData();
});

function loadPatientData() {
    let url = "/api/patient/status/1";
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $("#patientId").combobox({data: data.data});
            }
        },
        error: errorHandler
    })
}

function loadKeyData() {
    let url    = "/api/key";
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $("#keyId").combobox({data: data.data});
            }
        },
        error: errorHandler
    })
}


/**
 * 初始化床位报警配置信息表单
 */
function bindAlarmConfigFormData() {
    if (currentItem) {
        $("#id").textbox('setValue', currentItem.id);
        $("#patientId").combobox('setValue', currentItem.patientId);
        $("#keyId").combobox('setValue', currentItem.keyId);
        $("#min").textbox('setValue', currentItem.min);
        $("#max").textbox('setValue', currentItem.max);
        $("#enable").combobox('setValue', currentItem.enable);
    }
}

/**
 * 重置床位报警配置信息表单
 */
function resortAlarmConfigFormData() {
    // $("#ff")[0].reset(); //并没有删除原来的值
    $("#id").textbox('setValue', '');
    $("#patientId").combobox('setValue', '');
    $("#keyId").combobox('setValue', '');
    $("#min").textbox('setValue', '');
    $("#max").textbox('setValue', '');
    $("#enable").combobox('setValue', '');
}

/**
 * 显示床位报警配置信息表单对话框
 */
function showAlarmConfigDialog() {
    $("#alarm_config_dialog").dialog({
        onOpen: () => {
            bindAlarmConfigFormData();
        },
        onClose: () => {
            resortAlarmConfigFormData();
        }
    });
    $("#alarm_config_dialog").dialog('open');
}

/**
 * 初始化床位报警配置信息
 */
function initAlarmConfigTable() {
    $alarmConfigTable = $("#alarm_config_list");
    $alarmConfigTable.datagrid({
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
 * 记载床位报警配置信息
 */
function loadAlarmConfigData() {
    $.ajax({
        url: "/api/bedThreshold",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $alarmConfigTable.datagrid({data: data.data});
            }
        },
        error:errorHandler
    })
}

function saveAlarmConfigInfo() {
    let form = $("#ff");
    if (form.form('validate')) {
        let data = form.serializeObject();
        // console.log(data);
        if (data.id) {
            doSaveOrUpdateAlarmConfigInfo(data, 'PUT');
        } else {
            doSaveOrUpdateAlarmConfigInfo(data, 'POST');
        }

    } else {
        showToast('警告', '存在校验未通过项目');
    }
}

function doSaveOrUpdateAlarmConfigInfo(data, method) {
    $.ajax({
        url: '/api/bedThreshold',
        type: method,
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadAlarmConfigData();
                resortAlarmConfigFormData();
            }
        },
        error:errorHandler
    })
}

function doDeleteAlarmConfigRecord() {
    $.ajax({
        url: '/api/bedThreshold/' + currentItem.id,
        type: 'DELETE',
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadAlarmConfigData();
            }
        },
        error:errorHandler
    })
}

function updateItemEnable(index) {
    $alarmConfigTable.datagrid('selectRow',index);
    let rows = $alarmConfigTable.datagrid('getChecked');
    // console.log(rows);
    let data = rows[0];
    data.enable = !data.enable;
    doSaveOrUpdateAlarmConfigInfo(data,'put');
}

