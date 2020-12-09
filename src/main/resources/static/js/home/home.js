$(function () {
    loadUserInfo();
});

/**
 * 记载用户信息
 */
function loadUserInfo() {
    $.ajax({
        url: '/api/user/fetchCurrent',
        type: 'GET',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                saveKey(KEY_USER, data.data);
                $("#username").text(data.data.username);
            }
        }
    })
}

function logout() {
    $.ajax({
        url: '/logout',
        type: "POST",
        success: data => {
            console.info(data);
        }
    })
}