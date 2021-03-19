document.write('<script src="/static/easyui/jquery.min.js"></script>');
document.write('<script src="/static/easyui/jquery.easyui.min.js"></script>');
document.write('<script src="/static/easyui/datagrid-detailview.js"></script>');
document.write('<script src="/static/easyui/datagrid-filter.js"></script>');
document.write('<link rel="stylesheet" type="text/css" href="/static/css/base.css"/>');
// document.write('<script src="/static/js/common/common-utils.js"></script>');
document.write('<script src="/static/js/utils/utils.js"></script>');
document.write('<script src="/static/js/utils/api-utils.js"></script>');
document.write('<script src="/static/js/utils/key-utils.js"></script>');
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

function showLoading() {
    $.messager.progress({title: '正在操作', msg: '操作中...'});
}

function closeLoading() {
    $.messager.progress('close');
}

function errorHandler(xhr, status, error) {
    closeLoading();
    if (xhr.status === 403) {
        showToast('提示', '权限不足，无法访问');
    } else if (xhr.status === 500) {
        showToast('提示', '服务器出错，获取数据失败');
    } else {
        showToast('提示', status);
    }

}


const MouseTip = {
    $: function (ele) {
        if (typeof (ele) == "object")
            return ele;
        else if (typeof (ele) == "string" || typeof (ele) == "number")
            return document.getElementById(ele.toString());
        return null;
    },
    mousePos: function (e) {
        var x, y;
        var e = e || window.event;
        return {
            x: e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft,
            y: e.clientY + document.body.scrollTop + document.documentElement.scrollTop
        };
    },
    start: function (obj) {
        var self = this;
        var t = self.$("mjs:tip");
        obj.onmousemove = function (e) {
            var mouse = self.mousePos(e);
            t.style.left = mouse.x + 10 + 'px';
            t.style.top = mouse.y + 10 + 'px';
            t.innerHTML = obj.getAttribute("data-tooltip");
            t.style.display = '';
        };
        obj.onmouseout = function () {
            t.style.display = 'none';
        };
    }
};

function isEmpty(val) {
    return val === undefined || val === null || val.length === 0;
}
