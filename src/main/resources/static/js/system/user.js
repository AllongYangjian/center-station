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
        title: '账号',
        field: 'username',
        align: 'center',
        width: 1
    },
    {
        title: '状态',
        field: 'status',
        align: 'center',
        width: 1,
        formatter: (value, row, index) => {
            if (value === 1) {
                return '<span>正常</span>'
            } else if (value === 2) {
                return '<span style="color: grey">账号过期</span>'
            } else if (value === 3) {
                return '<span style="color: darkgrey">账号锁定</span>'
            } else {
                return '<span style="color: red">密码过期</span>'
            }
        }
    }
]];

const userStatus = [
    {
        id:1,
        name:'正常'
    },
    {
        id:2,
        name:'账号过期'
    },
    {
        id:3,
        name:'账号锁定'
    },
    {
        id:4,
        name:'密码过期'
    }
];

var $userTable;
var currentItem;

$(function () {
    initUserTable();

    $("#add").on('click', () => {
        currentItem = undefined;
        $userTable.datagrid('uncheckAll');
        showUserDialog();
    });

    $("#edit").on('click', () => {
        if (currentItem) {
            showUserDialog();
        } else {
            showToast('提示', '请选择要修改的数据');
        }
    });

    $("#delete").on('click', () => {
        if (currentItem) {
            showDeleteToast(doDeleteUserRecord);
        } else {
            showToast('提示', '请选择要删除的数据');
        }
    });

    $("#query").on('click', () => {
        loadUserData();
    });

    $("#user_save").on('click', () => {
        saveUserInfo();
    });

    loadUserData();
});

function bindUserFormData() {
    if (currentItem) {
        $("#id").textbox('setValue', currentItem.id);
        $("#username").textbox('setValue', currentItem.username);
        $("#status").combobox('setValue', currentItem.status);
    }
}

function resortUserFormData() {
    // $("#ff")[0].reset(); //并没有删除原来的值
    $("#id").textbox('setValue', '');
    $("#username").textbox('setValue', '');
    $("#status").combobox('setValue', '');
}

function showUserDialog() {
    $("#user_dialog").dialog({
        onOpen: () => {
            bindUserFormData();
        },
        onClose: () => {
            resortUserFormData();
        }
    });
    $("#user_dialog").dialog('open');
}


function initUserTable() {
    $userTable = $("#user_list");
    $userTable.datagrid({
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


function loadUserData() {
    $.ajax({
        url: "/api/user",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $userTable.datagrid({data: data.data});
            }
        }
    })
}

function saveUserInfo() {
    let form = $("#ff");
    if (form.form('validate')) {
        let data = form.serializeObject();
        // console.log(data);
        if (data.id) {
            doSaveOrUpdateUserInfo(data, 'PUT');
        } else {
            doSaveOrUpdateUserInfo(data, 'POST');
        }

    } else {
        showToast('警告', '存在校验未通过项目');
    }
}

function doSaveOrUpdateUserInfo(data, method) {
    $.ajax({
        url: '/api/user',
        type: method,
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadUserData();
                resortUserFormData();
            }
        }
    })
}

function doDeleteUserRecord() {
    $.ajax({
        url: '/api/user/' + currentItem.id,
        type: 'DELETE',
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadUserData();
            }
        }
    })
}