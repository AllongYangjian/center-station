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
        title: '名称',
        field: 'name',
        align: 'center',
        width: 1
    }, {
        title: '制造商',
        field: 'manufacturer',
        align: 'center',
        width: 1
    }, {
        title: '品牌',
        field: 'brand',
        align: 'center',
        width: 1
    },
    {
        title: '型号',
        field: 'model',
        align: 'center',
        width: 1
    }
]];

var keyColumns = [[
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
        title: '代码',
        field: 'code',
        align: 'center',
        width: 1
    },
    {
        title: '名称',
        field: 'name',
        align: 'center',
        width: 1
    },
    {
        title: '颜色',
        field: 'keyColor',
        align: 'center',
        width: 1
    },
    {
        title: '大小',
        field: 'keySize',
        align: 'center',
        width: 1
    },
    {
        title: '下限',
        field: 'min',
        align: 'center',
        width: 1
    },
    {
        title: '上限',
        field: 'max',
        align: 'center',
        width: 1
    },

    {
        title: '索引',
        field: 'position',
        align: 'center',
        width: 1
    },
    {
        title: '是否存在波形',
        field: 'wave',
        align: 'center',
        width: 1
    },
    {
        title: '缩放系数',
        field: 'scale',
        align: 'center',
        width: 1
    }
]];


var $deviceTable;
var currentItem;

$(function () {
    initDeviceTable();

    $("#add").on('click', () => {
        currentItem = undefined;
        $deviceTable.datagrid('uncheckAll');
        showDeviceDialog();
    });

    $("#edit").on('click', () => {
        if (currentItem) {
            showDeviceDialog();
        } else {
            showToast('提示', '请选择要修改的数据');
        }
    });

    $("#delete").on('click', () => {
        if (currentItem) {
            showDeleteToast(doDeleteDeviceRecord);
        } else {
            showToast('提示', '请选择要删除的数据');
        }
    });

    $("#query").on('click', () => {
        loadDeviceData();
    });

    $("#device_save").on('click', () => {
        saveDeviceInfo();
    });

    $("#device_close").on('click', () => {
        $("#device_dialog").dialog('close');
    });

    loadDeviceData();
});


/**
 * 初始化用户信息表单
 */
function bindDeviceFormData() {
    if (currentItem) {
        $("#id").textbox('setValue', currentItem.id);
        $("#name").textbox('setValue', currentItem.name);
        $("#manufacturer").textbox('setValue', currentItem.manufacturer);
        $("#brand").textbox('setValue', currentItem.brand);
        $("#model").textbox('setValue', currentItem.model);
    }
}

/**
 * 重置用户信息表单
 */
function resortDeviceFormData() {
    // $("#ff")[0].reset(); //并没有删除原来的值
    $("#id").textbox('setValue', '');
    $("#name").textbox('setValue', '');
    $("#manufacturer").textbox('setValue', '');
    $("#brand").textbox('setValue', '');
    $("#model").textbox('setValue', '');
}

/**
 * 显示用户信息表单对话框
 */
function showDeviceDialog() {
    $("#device_dialog").dialog({
        onOpen: () => {
            bindDeviceFormData();
        },
        onClose: () => {
            resortDeviceFormData();
        }
    });
    $("#device_dialog").dialog('open');
}

/**
 * 初始化用户信息
 */
function initDeviceTable() {
    $deviceTable = $("#device_list");
    $deviceTable.datagrid({
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
function loadDeviceData() {
    $.ajax({
        url: "/api/device",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $deviceTable.datagrid({data: data.data});
            }
        },
        error:errorHandler
    })
}

function saveDeviceInfo() {
    let form = $("#ff");
    if (form.form('validate')) {
        let data = form.serializeObject();
        // console.log(data);
        if (data.id) {
            doSaveOrUpdateDeviceInfo(data, 'PUT');
        } else {
            doSaveOrUpdateDeviceInfo(data, 'POST');
        }

    } else {
        showToast('警告', '存在校验未通过项目');
    }
}

function doSaveOrUpdateDeviceInfo(data, method) {
    $.ajax({
        url: '/api/device',
        type: method,
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadDeviceData();
                resortDeviceFormData();
            }
        },
        error:errorHandler
    })
}

function doDeleteDeviceRecord() {
    $.ajax({
        url: '/api/device/' + currentItem.id,
        type: 'DELETE',
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadDeviceData();
            }
        },
        error:errorHandler
    })
}

