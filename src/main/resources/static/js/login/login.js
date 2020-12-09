$(function () {
    // $("#username").on("blur", (e) => {
    //     let username = $(e.target).val();
    //     if (username === null || username === "" || username.length === 0) {
    //         $(".login_error").text("请输入用户名");
    //         $(".login_error").show();
    //     } else {
    //         $(".login_error").hide();
    //     }
    // });
    //
    // $("#password").on("blur", (e) => {
    //     let username = $(e.target).val();
    //     if (username === null || username === "" || username.length === 0) {
    //         $(".login_error").text("请输入密码");
    //     }
    // });

    $("#user_login").click(function () {

        if (validateLogin()) {
            doLogin();
        }

    });
});

function validateLogin() {
    let u = $("#username").val();
    let p = $("#password").val();
    if (u.length === 0) {
        $(".login_error").text("请输入用户名");
        $(".login_error").show();
        $("#username").focus();
        return false;
    }
    if (p.length === 0) {
        $(".login_error").text("请输入密码");
        $(".login_error").show();
        $("#password").focus();
        return false;
    }
    $(".login_error").hide();
    return true;
}

function doLogin() {
    $.ajax({
        type: "POST",
        url: "/login",
        data: $("#loginForm").serialize(),
        dataType: "JSON",
        success: function (data) {
            if (data.code === "500") {
                $(".login_error").text(data.message);
                $(".login_error").show();
            } else {
                window.location.href = "/home";
            }

        }
    });
}

function keyup_submit(e) {
    var evt = window.event || e;
    if (evt.keyCode === 13) {
        if (validateLogin()) {
            doLogin();
        }
    }

}