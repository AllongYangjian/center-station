var columns = [[
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
        title: '角色名称',
        field: 'roleName',
        align: 'center',
        width: 1
    },
    {
        title: '角色描述',
        field: 'roleDesc',
        align: 'center',
        width: 1
    }
]];

var $roleTable;
var currentItem;

$(function () {
    initRoleTable();

    $("#add").on('click', () => {
        currentItem = undefined;
        $roleTable.datagrid('uncheckAll');
        showRoleDialog();
    });

    $("#edit").on('click', () => {
        if (currentItem) {
            showRoleDialog();
        } else {
            showToast('提示', '请选择要修改的数据');
        }
    });

    $("#delete").on('click', () => {
        if (currentItem) {
            showDeleteToast(doDeleteRoleRecord);
        } else {
            showToast('提示', '请选择要删除的数据');
        }
    });

    $("#query").on('click', () => {
        loadRoleData();
    });

    $("#role_save").on('click', () => {
        saveRoleInfo();
    });

    loadRoleData();
});

function bindRoleFormData() {
    if (currentItem) {
        $("#id").textbox('setValue', currentItem.id);
        $("#roleName").textbox('setValue', currentItem.roleName);
        $("#roleDesc").textbox('setValue', currentItem.roleDesc);
    }
}

function resortRoleFormData() {
    // $("#ff")[0].reset(); //并没有删除原来的值
    $("#id").textbox('setValue', '');
    $("#roleName").textbox('setValue', '');
    $("#roleDesc").textbox('setValue', '');
}

function showRoleDialog() {
    $("#role_dialog").dialog({
        onOpen: () => {
            bindRoleFormData();
        },
        onClose: () => {
            resortRoleFormData();
        }
    });
    $("#role_dialog").dialog('open');
}


function initRoleTable() {
    $roleTable = $("#role_list");
    $roleTable.datagrid({
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
        }
    });
}


function loadRoleData() {
    $.ajax({
        url: "/api/role",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $roleTable.datagrid({data: data.data});
            }
        }
    })
}

function saveRoleInfo() {
    let form = $("#ff");
    if (form.form('validate')) {
        let data = form.serializeObject();
        // console.log(data);
        if (data.id) {
            doSaveOrUpdateRoleInfo(data, 'PUT');
        } else {
            doSaveOrUpdateRoleInfo(data, 'POST');
        }

    } else {
        showToast('警告', '存在校验未通过项目');
    }
}

function doSaveOrUpdateRoleInfo(data, method) {
    $.ajax({
        url: '/api/role',
        type: method,
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadRoleData();
                resortRoleFormData();
            }
        }
    })
}

function doDeleteRoleRecord() {
    $.ajax({
        url: '/api/role/' + currentItem.id,
        type: 'DELETE',
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadRoleData();
            }
        }
    })
}