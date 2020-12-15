document.write('<script src="/static/easyui/jquery.min.js"></script>');
document.write('<script src="/static/easyui/jquery.easyui.min.js"></script>');
document.write('<script src="/static/easyui/datagrid-detailview.js"></script>');
document.write('<script src="/static/easyui/datagrid-filter.js"></script>');
document.write('<link rel="stylesheet" type="text/css" href="/static/css/base.css"/>');
// document.write('<script src="/static/js/common/common-utils.js"></script>');
document.write('<script src="/static/js/utils/utils.js"></script>');
document.write('<link rel="stylesheet" type="text/css" href="/static/easyui/themes/icon.css"/>');
document.write('<link rel="stylesheet" type="text/css" href="/static/easyui/themes/default/easyui.css"/>');
document.write('<script type="text/javascript" src="/static/easyui/locale/easyui-lang-zh_CN.js"></script>');

function showToast(title, msg, time) {
    if (!time) {
        time = 500;
    }
    $.messager.show({
        title: title,
        msg: msg,
        timeout: time,
        showSpeed: 500,
        showType: 'slide',
        style: {
            right: '',
            top: document.body.scrollTop + document.documentElement.scrollTop,
            bottom: ''
        }
    });
}

function showDeleteToast(callback) {
    $.messager.confirm('危险操作', '确定删除吗?', function (ok) {
        if (ok) {
            callback();
        }
    })
}

function errorHandler(xhr, status, error) {
    if (xhr.status === 403) {
        showToast('提示', '权限不足，无法访问');
    } else if (xhr.status === 500) {
        showToast('提示', '服务器出错，获取数据失败');
    }else {
        showToast('提示', status);
    }

}
