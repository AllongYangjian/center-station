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

var roleColumns = [[
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

const userStatus = [
    {
        id: 1,
        name: '正常'
    },
    {
        id: 2,
        name: '账号过期'
    },
    {
        id: 3,
        name: '账号锁定'
    },
    {
        id: 4,
        name: '密码过期'
    }
];

var $userTable;
var $roleTable;
var currentItem;

$(function () {
    initUserTable();
    initUserRoleTable();

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

    $("#role_query").on('click', () => {
        loadUserRoleData();
    });

    $("#role_save").on('click', () => {
        saveUserRole();
    });

    loadUserData();
    loadUserRoleData();
});

/**
 * 初始化角色列表
 */
function initUserRoleTable() {
    $roleTable = $("#role_list");
    $roleTable.datagrid({
        title: '用户角色信息',
        rownumbers: true,
        showFooter: true,
        fitColumns: true,
        pagination: true,
        remoteSort: false,
        pagePosition: 'bottom',
        pageNumber: 1,
        pageSize: 20,
        columns: roleColumns,
        onCheck: (index, data) => {
        },
        onUncheck: (index, data) => {
        }
    });
}

/**
 * 获取用户授权的角色列表
 */
function loadUserRoleList(data) {
    console.log('loadUserRoleList');
    $.ajax({
        url: "/api/user/role/" + data.id,
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                let roles = data.data;
                if (roles.length > 0) {
                    let rowsIndex = [];
                    let rows = $roleTable.datagrid('getRows');
                    for (let y = 0; y < rows.length; y++) {
                        for (var x = 0; x < roles.length; x++) {
                            if (rows[y].id === roles[x].id) {
                                rowsIndex.push(y);
                                break;
                            }
                        }
                    }
                    // console.log(rowsIndex);
                    rowsIndex.forEach(index => $roleTable.datagrid('checkRow', index))
                } else {
                    $roleTable.datagrid('clearChecked');
                }
            }
        }
    })
}

/**
 * 初始化用户信息表单
 */
function bindUserFormData() {
    if (currentItem) {
        $("#id").textbox('setValue', currentItem.id);
        $("#username").textbox('setValue', currentItem.username);
        $("#status").combobox('setValue', currentItem.status);
    }
}

/**
 * 重置用户信息表单
 */
function resortUserFormData() {
    // $("#ff")[0].reset(); //并没有删除原来的值
    $("#id").textbox('setValue', '');
    $("#username").textbox('setValue', '');
    $("#status").combobox('setValue', '');
}

/**
 * 显示用户信息表单对话框
 */
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

/**
 * 初始化用户信息
 */
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
            loadUserRoleList(data);
        },
        onUncheck: (index, data) => {
            currentItem = undefined;
        }
    });
}

/**
 * 记载用户信息
 */
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

/**
 * 加载角色信息
 */
function loadUserRoleData() {
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

function saveUserRole() {
    if (currentItem === undefined || currentItem === null) {
        showToast('提示', '请先选择用户');
        return
    }
    let rows = $roleTable.datagrid('getChecked');
    if (rows.length === 0) {
        $.messager.confirm('危险操作', '确定删除用户授权的角色吗?', function (ok) {
            if (ok) {
                deleteUserRole();
            }
        })
    } else {
        let userRole = rows.map(item => {
            return {
                uid: currentItem.id,
                rid: item.id
            }
        });
        doSaveUserRole(userRole);
    }
}

function doSaveUserRole(data) {
    $.ajax({
        url: "/api/userRole",
        type: 'POST',
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: data => {
            showToast('提示', data.message);
        }
    })
}


function deleteUserRole() {
    $.ajax({
        url: "/api/userRole/" + currentItem.id,
        type: 'DELETE',
        dataType: 'json',
        success: data => {
            showToast('提示', data.message);
        }
    })
}