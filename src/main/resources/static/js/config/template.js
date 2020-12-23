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
        title: '当前状态',
        field: 'enable',
        align: 'center',
        width: 1,
        formatter: (value, row, index) => {
            if (value) {
                return '<span style="color: red">使用中...</span>'
            } else {
                return '<span>未使用</span>'
            }
        }
    },
    {
        title: '操作',
        field: 'edit',
        align: 'center',
        width: 1,
        formatter: (value, row, index) => {
            return '<input type="button" value="启用" onclick="applyCurrentTemplate(' + row.id + ')">';
        }
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
var cacheTempDetailData ;//用来缓存模板详情
var currentTempDetailItem; //集体模板条目
var cacheKeyDetailData;

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
            if (currentItem.username === '0') {
                showToast('提示', '默认模板无法删除');
                return;
            }
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

    $("#template_item_save").on('click', () => {
        updateTempDetailItemInfo();
    });

    $("#template_item_close").on('click', () => {
        $("#template_detail_dialog").dialog('close');
    });

    loadTemplateData();

    loadKeyDetail();
    loadKeys();

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
            bindTemplateFormData();
            loadWaveKey();
            loadDataKey();
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
            $templateTable.datagrid('checkRow', index);
            var ddv = $(this).datagrid('getRowDetail', index).find('table.ddv');
            ddv.datagrid({
                fitColumns: true,
                singleSelect: true,
                rownumbers: true,
                loadMsg: '',
                height: 'auto',
                columns: [[
                    // {field: 'id', title: 'ID', width: 100},
                    {
                        title: '代码',
                        field: 'code',
                        align: 'center',
                        width: 1
                    },
                    {
                        title: '名称',
                        field: 'name',
                        align: 'center',
                        width: 1
                    },
                    {
                        title: '排序',
                        field: 'position',
                        align: 'center',
                        width: 1
                    },
                    {
                        title: '颜色',
                        field: 'keyColor',
                        align: 'center',
                        width: 1,
                        formatter: (value, row, index) => {
                            return '<span style="display:inline-block;width: 100%;height: 20px;color:white;background: ' + value + '">' + value + '</span>'
                        }
                    },
                    {
                        title: '大小',
                        field: 'keySize',
                        align: 'center',
                        width: 1
                    },
                    {
                        title: '下限',
                        field: 'min',
                        align: 'center',
                        width: 1
                    },
                    {
                        title: '上限',
                        field: 'max',
                        align: 'center',
                        width: 1
                    },
                    {
                        title: '单位',
                        field: 'unit',
                        align: 'center',
                        width: 1
                    },
                    {
                        field: 'enable',
                        title: '',
                        width: 1,
                        formatter: (value, row, index) => {
                            if (value) {
                                return '<span style="color: red">显示波形</span>'
                            } else {
                                return '<span style="color:#000;">显示数字</span>'
                            }
                        }
                    },
                    {
                        title: '缩放系数',
                        field: 'scale',
                        align: 'center',
                        width: 1
                    },
                    {
                        title: '编辑',
                        field: 'edit',
                        align: 'center',
                        width: 1,
                        formatter:(value,row,index)=>{
                            return '<input type="button" onclick="editTempDetailItem('+row.id+')" value="编辑">';
                        }
                    }
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
    // $.ajax({
    //     url: "/api/template/detail/" + row.id,
    //     type: 'get',
    //     dataType: 'json',
    //     success: data => {
    //         console.log('loadTempDetailData', data);
    //         if (data.code === 200 && table) {
    //             table.datagrid({data: data.data});
    //         }
    //     }
    // })
    let arr = [];
    arr.push($.ajax({
        url: "/api/template/detail/wave/" + currentItem.id,
        type: 'get',
        dataType: 'json',
    }));
    arr.push($.ajax({
        url: "/api/template/detail/data/" + currentItem.id,
        type: 'get',
        dataType: 'json',
    }));
    Promise.all(arr).then(res => {
        let arr = [];
        if (res[0].code === 200) {
            res[0].data.map(item => {
                arr.push({...item, enable: true});
            })
        }

        if (res[1].code === 200) {
            res[1].data.forEach(item => {
                arr.push({...item, enable: false});
            })
        }
        cacheTempDetailData = arr;
        table.datagrid({data: arr});
    });
}

/**
 * 加载波形数据
 */
function loadWaveKey() {
    $.ajax({
        url: "/api/template/detail/wave/" + currentItem.id,
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200 && data.data!==null) {
                let arr = data.data.map(item => item.keyId);
                $("#waves").tagbox('setValues', arr);
            }
        },
        error:errorHandler
    })
}

/**
 * 加载关键字
 */
function loadDataKey() {
    $.ajax({
        url: "/api/template/detail/data/" + currentItem.id,
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200 && data.data!==null) {
                let arr = data.data.map(item => item.keyId);
                $("#data").tagbox('setValues', arr);
            }
        },
        error:errorHandler
    })
}

/**
 * 记载模板列表
 */
function loadTemplateData() {
    $.ajax({
        url: "/api/template",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $templateTable.datagrid({data: data.data});
            }
        },
        error:errorHandler
    })
}

/**
 * 编辑模板详情
 */
function editTempDetailItem(id) {
    if(cacheTempDetailData!==null){
        for(let x = 0;x<cacheTempDetailData.length;x++){
            if(id === cacheTempDetailData[x].id){
                currentTempDetailItem = cacheTempDetailData[x];
                break;
            }
        }
    }
    if(cacheTempDetailData!==undefined){
        //显示对话框
        $("#template_detail_dialog").dialog({
            onOpen:function () {
                $("#keySize").numberspinner('setValue',currentTempDetailItem.keySize);
                $("#scale").numberspinner('setValue',currentTempDetailItem.scale);
            }
        });
        $("#template_detail_dialog").dialog('open');

    }
}

/**
 * 更新模板项目信息
 */
function updateTempDetailItemInfo() {
    let form = $("#ff_deital");
    if (form.form('validate')) {
        let formData = form.serializeObject();
        let data = {...currentTempDetailItem,...formData};

        $.ajax({
            url:'/api/template/detail',
            type:'put',
            data:JSON.stringify(data),
            dataType:'json',
            contentType:'application/json',
            success:res=>{
                showToast('提示',res.message);
                if(res.code ===200){
                    loadTemplateData();
                }
            },
            error:errorHandler
        })
    } else {
        showToast('警告', '存在校验未通过项目');
    }
}


/**
 * 获取关键字详情
 */
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
        },
        error:errorHandler
    })
}

function loadKeys() {
    $.ajax({
        url: "/api/key",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                cacheKeyDetailData =data.data;
            }
        },
        error: errorHandler
    })
}


function saveTemplateInfo() {
    let form = $("#ff");
    if (form.form('validate')) {
        let data = form.serializeObject();
        let arr1 = $("#waves").tagbox('getValues');
        let arr2 = $("#waves").tagbox('getValues');
        // if (arr1.length > 4) {
        //     showToast('警告', '最多显示四条波形数据');
        //     return;
        // }
        // if (arr2.length > 8) {
        //     showToast('警告', '最多显示8个数值数据');
        //     return;
        // }
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
                let array1 = [];
                let array2 = [];

                let arr1 = $("#waves").tagbox('getValues');

                for(let x = 0;x<cacheKeyDetailData.length;x++){
                    for(let  y = 0;y<arr1.length;y++){
                        if(cacheKeyDetailData[x].id.toString() === arr1[y]){
                            array1.push(cacheKeyDetailData[x]);
                            break;
                        }
                    }
                }
                array1.forEach(item => {
                    array.push({ tempId: id,...item, type: 1,keyId:item.id})
                });

                let arr2 = $("#data").tagbox('getValues');
                for(let x = 0;x<cacheKeyDetailData.length;x++){
                    for(let  y = 0;y<arr2.length;y++){
                        if(cacheKeyDetailData[x].id.toString() === arr2[y]){
                            array2.push(cacheKeyDetailData[x]);
                            break;
                        }
                    }
                }
                array2.forEach(item => {
                    array.push({tempId: id,...item, type: 2,keyId:item.id})
                });

                console.log('doSaveOrUpdateTemplateInfo', cacheKeyDetailData,array,arr1,arr2);
                saveTempDetailInfo(array);

            }
        },
        error:errorHandler
    })
}

function applyCurrentTemplate(tempId) {
    $.ajax({
        url: '/api/template/' + tempId,
        type: 'PUT',
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            loadTemplateData();
        },
        error:errorHandler
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
        },
        error:errorHandler
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
        },
        error:errorHandler
    })
}