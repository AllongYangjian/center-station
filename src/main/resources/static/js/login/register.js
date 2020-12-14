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

function resortData() {
    $("#username").textbox('setValue', '');
    $("#password").passwordbox('setValue', '');
    $("#pwd").passwordbox('setValue', '');
    $("#hid").combobox('setValue', '');
    $("#name").textbox('setValue', '');
    $("#gender").combobox('setValue', '');
    $("#age").numberspinner('setValue', '');
    $(".password_error").hide();
    $(".password_error").hide();
}

/**
 * 保存账户
 * @param data
 */
function doSaveAccount(data) {
    $.ajax({
        url: '/auth/register',
        data: JSON.stringify(data),
        type: 'post',
        contentType: 'application/json',
        success: function (data) {
            if (data.code === 200) {
                showToast('提示', '保存成功');
                resortData();
            } else {
                showToast('提示', data.message);
                $(".account_error").show();
            }
        }
    })
}

/**
 * 注册账户
 */
function registerAccount() {
    var form = $("#register-form");
    if (form.form('validate')) {
        let data = form.serializeObject();
        if (data.password !== data.pwd) {
            $(".password_error").show();
            showToast('提示', '两次密码不一致')
            return;
        } else {
            $(".password_error").hide();
        }
        doSaveAccount(data);
    } else {
        showToast('提示', '存在校验未通过项目')
    }
}

$(function () {
    loadHospitalData();
    $(".password_error").hide();
    $(".password_error").hide();
    $("#register").on('click', function () {
        registerAccount();
    });
});

function loadHospitalData() {
    $.ajax({
        url: "/api/hospital",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $("#hid").combobox({data: data.data});
            }
        }
    })
}