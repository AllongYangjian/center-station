var data = [
    {
        text: '数据',
        iconCls: 'icon-wave',
        state: 'open',
        children: [
            {
                text: '波形展示',
                url: '/wave/data'
            },
        ]
    },
    {
        text: '配置',
        iconCls: 'icon-config',
        children: [
            {
                text: '界面模板',
                url: '/config/template'
            },
            {
                text: '病人录入',
                url: '/config/patient'
            }
        ]
    },
    {
        text: '运维',
        iconCls: 'icon-operation',
        children: [
            {
                text: '性能'
            },
            {
                text: '日志'
            }
        ]
    },
    {
        text: '系统',
        iconCls: 'icon-setting',
        children: [
            {
                text: '设备',
                url: '/system/device'
            },
            {
                text: '关键字',
                url: '/system/key'
            },
            {
                text: '医院',
                url: '/system/hospital'
            },
            {
                text: '用户',
                url: '/system/user'
            },
            {
                text: '角色',
                url: '/system/role'
            }
        ]
    }
];

var $slideMenu;
var $tabContainer;

$(function () {
    loadUserInfo();
    initEvent();
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
                $("#username").text(data.data.name);
            }
        }
    })
}

var nodeMap = new Map();

function initEvent() {
    // $("#sm").sidemenu({
    //     onSelect: item => {
    //         nodeMap.set(item.text, item);
    //         addTabItem(item);
    //     }
    // });
    //

    $slideMenu = $("#sm");

    $("#tt").tabs({
        onSelect: onTabSelect
    });

    $slideMenu.sidemenu({
        data: data,
        onSelect: onSideMenuSelect,
        border: false
    });
}

function logout() {
    $.ajax({
        url: '/logout',
        type: "POST",
        success: data => {
        }
    })
}

// function addTabItem(item) {
//     var tab = $("#tt").tabs('getTab', item.text);
//     if (tab !== undefined && tab !== null) {
//         $("#tt").tabs('select', item.text)
//     } else {
//         tab = {
//             title: item.text,
//             href: item.url,
//             closable: true
//         };
//         $("#tt").tabs('add', tab);
//     }
//
// }


function onPanelOpen() {
    var panel = $(this);
    var _1e = panel.panel("header").children("div.panel-tool");
    _1e.children("a.panel-tool-collapse").hide();

    var _20 = "layout-button-left";// + _1f[dir];
    var t = _1e.children("a." + _20);
    if (!t.length) {
        t = $("<a href=\"javascript:;\"></a>").addClass(_20).appendTo(_1e);
        t.bind("click", {dir: "left"}, function (e) {
            if (e.target.className == "layout-button-right") {
                e.target.className = "layout-button-left"
                onWestExpand();
                panel.panel("setTitle", panel.titleTemp);
                panel.panel('resize', {
                    width: 202
                });

                var leftMenuDiv = $('#smDiv');
                leftMenuDiv.each(function () {
                    this.style.width = '202px';
                });
            } else {
                e.target.className = "layout-button-right"
                onWestCollapse();
                var opt = panel.panel('options');
                panel.titleTemp = opt.title;
                panel.panel("setTitle", "");
                panel.panel('resize', {
                    width: 42
                });

                var leftMenuDiv = $('#smDiv');
                leftMenuDiv.each(function () {
                    this.style.width = '42px';
                });
            }

            return false;
        });
    }
    //$(this).panel("options").collapsible ? t.show() : t.hide();
}

function onTabSelect(title, index) {
    var tabs = $('#tt');
    var tab = tabs.tabs('getTab', index);
    var menus = $slideMenu;
    if (menus.hasClass('sidemenu')) {
        var opts = menus.sidemenu("options");
        changeMenuSelect(menus, opts, tab[0].id);
    }
}

function onWestCollapse() {
    var opts = $slideMenu.sidemenu('options');
    if (opts.collapsed != 'collapse') {
        $slideMenu.sidemenu('collapse');
        $slideMenu.sidemenu('resize', {
            width: 40
        });
    }
}

function onWestExpand() {
    var opts = $slideMenu.sidemenu('options');
    if (opts.collapsed != 'expand') {
        $slideMenu.sidemenu('expand');
        $slideMenu.sidemenu('resize', {
            width: 200
        });
    }
}

function onSideMenuSelect(item) {
    if (item.url === '/wave/data') {
        window.open(item.url);
        return;
    }
    if (!$('#tt').tabs('exists', item.text)) {
        $('#tt').tabs('add', {
            title: item.text,
            content: '<iframe scrolling="auto" frameborder="0"  src="' + item.url + '" style="width:100%;height:99%;"></iframe>',
            // href: item.url,
            closable: true,
            // icon: item.iconCls,
            id: item.id
        });
    } else {
        $('#tt').tabs('select', item.text);
    }
    addTabMenu();
}

function addTabMenu() {
    /* 双击关闭TAB选项卡 */
    $(".tabs-inner").dblclick(function () {
        var subtitle = $(this).children(".tabs-closable").text();
        $('#tt').tabs('close', subtitle);
    });
    /* 为选项卡绑定右键 */
    $(".tabs-inner").bind('contextmenu', function (e) {
        $('#tab_menu').menu('show', {
            left: e.pageX,
            top: e.pageY
        });

        var subtitle = $(this).children(".tabs-closable").text();

        $('#tab_menu').data("currtab", subtitle);
        $('#tt').tabs('select', subtitle);
        return false;
    });
}

function changeMenuSelect(menus, opts, selectId) {
    var menutrees = menus.find(".sidemenu-tree");
    menutrees.each(function () {
        var menuItem = $(this);
        changeMenuStyle(menuItem, opts, selectId);
    });

    var tooltips = menus.find(".tooltip-f");
    tooltips.each(function () {
        var menuItem = $(this);
        var tip = menuItem.tooltip("tip");
        if (tip) {
            tip.find(".sidemenu-tree").each(function () {
                changeMenuStyle($(this), opts, selectId);
            });
            menuItem.tooltip("reposition");
        }
    });
}

function changeMenuStyle(menuItem, opts, selectId) {
    menuItem.find("div.tree-node-selected").removeClass("tree-node-selected");
    var node = menuItem.tree("find", selectId);
    if (node) {
        $(node.target).addClass("tree-node-selected");
        opts.selectedItemId = node.id;
        menuItem.trigger("mouseleave.sidemenu");
    }

    changeMenuSelect(menuItem);
}