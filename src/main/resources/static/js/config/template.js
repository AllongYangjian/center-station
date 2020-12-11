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
        title: '模板名称',
        field: 'tempName',
        align: 'center',
        width: 1
    },
    {
        title: '是否启用',
        field: 'enable',
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

var $templateTable;
var currentItem;

$(function () {
    initTemplateTable();

    $("#add").on('click', () => {
        currentItem = undefined;
        $templateTable.datagrid('uncheckAll');
        showTemplateDialog();
    });

    $("#edit").on('click', () => {
        if (currentItem) {
            showTemplateDialog();
        } else {
            showToast('提示', '请选择要修改的数据');
        }
    });

    $("#delete").on('click', () => {
        if (currentItem) {
            showDeleteToast(doDeleteTemplateRecord);
        } else {
            showToast('提示', '请选择要删除的数据');
        }
    });

    $("#query").on('click', () => {
        loadTemplateData();
    });

    $("#template_save").on('click', () => {
        saveTemplateInfo();
    });

    $("#template_close").on('click', () => {
        $("#template_dialog").dialog('close');
    });

    loadTemplateData();

    loadKeyDetail();
});

function bindTemplateFormData() {
    if (currentItem) {
        $("#id").textbox('setValue', currentItem.id);
        $("#tempName").textbox('setValue', currentItem.tempName);
        $("#enable").combobox('setValue', currentItem.enable);
    }
}

function resortTemplateFormData() {
    // $("#ff")[0].reset(); //并没有删除原来的值
    $("#id").textbox('setValue', '');
    $("#tempName").textbox('setValue', '');
    $("#enable").combobox('setValue', '');
    $("#waves").tagbox('setValues', []);
    $("#data").tagbox('setValues', []);
}

function showTemplateDialog() {
    $("#template_dialog").dialog({
        onOpen: () => {
            //todo 加载模板对应的数据

            bindTemplateFormData();
        },
        onClose: () => {
            resortTemplateFormData();
        }
    });
    $("#template_dialog").dialog('open');
}


function initTemplateTable() {
    $templateTable = $("#template_list");
    $templateTable.datagrid({
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
        loadFilter: pagerFilter,
        view: detailview,
        detailFormatter: function (index, row) {
            return '<div style="padding:2px"><table class="ddv"></table></div>';
        },
        onExpandRow: function (index, row) {
            var ddv = $(this).datagrid('getRowDetail', index).find('table.ddv');
            ddv.datagrid({
                fitColumns: true,
                singleSelect: true,
                rownumbers: true,
                loadMsg: '',
                height: 'auto',
                columns: [[
                    {field: 'id', title: 'ID', width: 100},
                    {field: 'name', title: '名称', width: 100},
                    {
                        field: 'enable', title: '', width: 100, formatter: (value, row, index) => {
                            if (value) {
                                return '<span>波形</span>'
                            } else {
                                return '<span>数字</span>'
                            }
                        }
                    },
                ]],
                onResize: function () {
                    $('#dg').datagrid('fixDetailRowHeight', index);
                },
                onLoadSuccess: function () {
                    setTimeout(function () {
                        $('#dg').datagrid('fixDetailRowHeight', index);
                    }, 0);
                }
            });

            //加载该模板对应的数据
            loadTempDetailData(row, ddv);
        }
    });
}

function loadTempDetailData(row, table) {
    $.ajax({
        url: "/api/template/detail/" + row.id,
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                table.datagrid({data: data.data});
            }
        }
    })
}


function loadTemplateData() {
    $.ajax({
        url: "/api/template",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $templateTable.datagrid({data: data.data});
            }
        }
    })
}

function loadKeyDetail() {
    $.ajax({
        url: "/api/key/detail",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                let keys = data.data;
                keys.forEach(item => {
                    item.keyName = item.deviceName + "-" + item.keyName
                });
                let waveData = keys.filter(item => item.wave);
                $("#waves").tagbox({data: waveData});
                $("#data").tagbox({data: keys});
            }
        }
    })
}


function saveTemplateInfo() {
    let form = $("#ff");
    if (form.form('validate')) {
        let data = form.serializeObject();
        if (data.id) {
            doSaveOrUpdateTemplateInfo(data, 'PUT');
        } else {
            doSaveOrUpdateTemplateInfo(data, 'POST');
        }

    } else {
        showToast('警告', '存在校验未通过项目');
    }
}

function doSaveOrUpdateTemplateInfo(formData, method) {
    $.ajax({
        url: '/api/template',
        type: method,
        data: JSON.stringify(formData),
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                let id = data.data;
                let array = [];

                let arr1 = $("#waves").combobox('getValues');
                let arr2 = $("#data").combobox('getValues');

                arr1.forEach(item => {
                    array.push({keyId: item, tempId: id, type: 1})
                });

                arr1.forEach(item => {
                    array.push({keyId: item, tempId: id, type: 2})
                });

                console.log('doSaveOrUpdateTemplateInfo', data, arr1, arr2);
                saveTempDetailInfo(array);

            }
        }
    })
}

function saveTempDetailInfo(data) {
    $.ajax({
        url: '/api/template/detail/all',
        type: 'POST',
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadTemplateData();
                resortTemplateFormData();
            }
        }
    })
}

function doDeleteTemplateRecord() {
    $.ajax({
        url: '/api/template/' + currentItem.id,
        type: 'DELETE',
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadTemplateData();
            }
        }
    })
}