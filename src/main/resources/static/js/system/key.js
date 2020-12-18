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
        hidden:true
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
        title: '排序',
        field: 'position',
        align: 'center',
        width: 1
    },
    {
        title: '颜色',
        field: 'keyColor',
        align: 'center',
        width: 1,
        formatter: (value, row, index) => {
            return '<span style="display:inline-block;width: 100%;height: 20px;color:white;background: ' + value + '">' + value + '</span>'
        }
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
        title: '单位',
        field: 'unit',
        align: 'center',
        width: 1
    },
    {
        title: '是否存在波形',
        field: 'wave',
        align: 'center',
        width: 1,
        formatter: (value, row, index) => {
            if (value) {
                return '<span>是</span>'
            } else {
                return '<span>否</span>'
            }
        }
    },
    {
        title: '缩放系数',
        field: 'scale',
        align: 'center',
        width: 1
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


var $keyTable;
var currentItem;
var currentDeviceId;

$(function () {
    initKeyTable();

    $("#add").on('click', () => {
        currentItem = undefined;
        $keyTable.datagrid('uncheckAll');
        showKeyDialog();
    });

    $("#edit").on('click', () => {
        if (currentItem) {
            showKeyDialog();
        } else {
            showToast('提示', '请选择要修改的数据');
        }
    });

    $("#delete").on('click', () => {
        if (currentItem) {
            showDeleteToast(doDeleteKeyRecord);
        } else {
            showToast('提示', '请选择要删除的数据');
        }
    });

    $("#query").on('click', () => {
        loadKeyData();
    });

    $("#reset").on('click', () => {
        currentDeviceId = undefined;
        $("#device_criteria").combobox('setValue', '');
        loadKeyData();
    });

    $("#key_save").on('click', () => {
        saveKeyInfo();
    });

    $("#key_close").on('click', () => {
        $("#key_dialog").dialog('close');
    });

    $("#reload_device").on('click', () => {
        loadDeviceData();
    });

    $("#device_criteria").combobox({
        onSelect: item => {
            currentDeviceId = item.id;
            loadKeyData();
        }
    });

    loadKeyData();
    loadDeviceData();

});

function loadDeviceData() {
    $.ajax({
        url: "/api/device",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $("#deviceId").combobox({data: data.data});
                $("#device_criteria").combobox({data: data.data});
            }
        },
        error:errorHandler
    })
}


/**
 * 初始化用户信息表单
 */
function bindKeyFormData() {
    if (currentItem) {
        $("#id").textbox('setValue', currentItem.id);
        $("#deviceId").combobox('setValue', currentItem.deviceId);
        $("#code").textbox('setValue', currentItem.code);
        $("#name").textbox('setValue', currentItem.name);
        $("#keyColor").val(currentItem.keyColor);
        $("#keySize").numberspinner('setValue', currentItem.keySize);
        $("#min").textbox('setValue', currentItem.min);
        $("#max").textbox('setValue', currentItem.max);
        $("#unit").textbox('setValue', currentItem.unit);
        $("#position").numberspinner('setValue', currentItem.position);
        $("#wave").combobox('setValue', currentItem.wave);
        $("#scale").numberspinner('setValue', currentItem.scale);
    }
}

/**
 * 重置用户信息表单
 */
function resortKeyFormData() {
    // $("#ff")[0].reset(); //并没有删除原来的值
    $("#id").textbox('setValue', '');
    $("#deviceId").combobox('setValue', '');
    $("#code").textbox('setValue', '');
    $("#name").textbox('setValue', '');
    $("#keyColor").val('#000000');
    $("#keySize").numberspinner('setValue', '');
    $("#min").textbox('setValue', '');
    $("#max").textbox('setValue', '');
    $("#unit").textbox('setValue', '');
    $("#position").numberspinner('setValue', '');
    $("#wave").combobox('setValue', '');
    $("#scale").numberspinner('setValue', '');
}

/**
 * 显示用户信息表单对话框
 */
function showKeyDialog() {
    $("#key_dialog").dialog({
        onOpen: () => {
            bindKeyFormData();
        },
        onClose: () => {
            resortKeyFormData();
        }
    });
    $("#key_dialog").dialog('open');
}

/**
 * 初始化用户信息
 */
function initKeyTable() {
    $keyTable = $("#key_list");
    $keyTable.datagrid({
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
function loadKeyData() {
    let url = '';
    if (currentDeviceId !== undefined) {
        url = "/api/key/" + currentDeviceId;
    } else {
        url = "/api/key";
    }
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $keyTable.datagrid({data: data.data});
            }
        },
        error:errorHandler
    })
}

function saveKeyInfo() {
    let form = $("#ff");
    if (form.form('validate')) {
        let data = form.serializeObject();
        // console.log(data);
        if (data.id) {
            doSaveOrUpdateKeyInfo(data, 'PUT');
        } else {
            doSaveOrUpdateKeyInfo(data, 'POST');
        }

    } else {
        showToast('警告', '存在校验未通过项目');
    }
}

function doSaveOrUpdateKeyInfo(data, method) {
    $.ajax({
        url: '/api/key',
        type: method,
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadKeyData();
                resortKeyFormData();
            }
        },
        error:errorHandler
    })
}

function doDeleteKeyRecord() {
    $.ajax({
        url: '/api/key/' + currentItem.id,
        type: 'DELETE',
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadKeyData();
            }
        },
        error:errorHandler
    })
}

