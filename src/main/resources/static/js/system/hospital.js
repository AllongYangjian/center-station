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
        title: '地址',
        field: 'address',
        align: 'center',
        width: 1
    }
]];

var bedColumns = [[
    {
        title: 'ID',
        field: 'id',
        align: 'center',
        width: 1,
        hidden:true
    },
    {
        title: '医院',
        field: 'hid',
        align: 'center',
        width: 1
    },
    {
        title: '床号',
        field: 'bed',
        align: 'center',
        width: 1
    }
]];

var $hospitalTable;
var $bedTable;
var currentItem,currentBedItem;

$(function () {
    initHospitalTable();
    initHospitalToolbar();
    loadHospitalData();

    initBedListTable();
    initBedListTableToolbar();
});


function initHospitalToolbar() {
    $("#add").on('click', () => {
        currentItem = undefined;
        $hospitalTable.datagrid('uncheckAll');
        showHospitalDialog();
    });

    $("#edit").on('click', () => {
        if (currentItem) {
            showHospitalDialog();
        } else {
            showToast('提示', '请选择要修改的数据');
        }
    });

    $("#delete").on('click', () => {
        if (currentItem) {
            showDeleteToast(doDeleteHospitalRecord);
        } else {
            showToast('提示', '请选择要删除的数据');
        }
    });

    $("#query").on('click', () => {
        loadHospitalData();
    });

    $("#hospital_save").on('click', () => {
        saveHospitalInfo();
    });

    $("#hospital_close").on('click', () => {
        $("#hospital_dialog").dialog('close');
    });
}

function bindHospitalFormData() {
    if (currentItem) {
        $("#id").textbox('setValue', currentItem.id);
        $("#code").textbox('setValue', currentItem.code);
        $("#name").textbox('setValue', currentItem.name);
        $("#address").textbox('setValue', currentItem.address);
    }
}

function resortHospitalFormData() {
    // $("#ff")[0].reset(); //并没有删除原来的值
    $("#id").textbox('setValue', '');
    $("#code").textbox('setValue', '');
    $("#name").textbox('setValue', '');
    $("#address").textbox('setValue', '');
}

function showHospitalDialog() {
    $("#hospital_dialog").dialog({
        onOpen: () => {
            bindHospitalFormData();
        },
        onClose: () => {
            resortHospitalFormData();
        }
    });
    $("#hospital_dialog").dialog('open');
}



function initHospitalTable() {
    $hospitalTable = $("#hospital_list");
    $hospitalTable.datagrid({
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
            //查找医院床位信息
            queryHospitalBedByHid();
        },
        onUncheck: (index, data) => {
            currentItem = undefined;
        },
        loadFilter: pagerFilter
    });
}

/**
 * 查找医院下属床位列表
 * @param id
 */
function queryHospitalBedByHid() {
    let url;
    if(currentItem){
        url = '/api/bed/'+currentItem.id;
    }else {
        url = '/api/bed';
    }
    $.ajax({
        url:url,
        type:'get',
        dataType:'json',
        success:data=>{
            $bedTable.datagrid({data:data.data});
        },
        error:errorHandler
    })
}

function initBedListTable() {
    $bedTable = $("#bed_list");
    $bedTable.datagrid({
        rownumbers: true,
        showFooter: true,
        fitColumns: true,
        pagination: true,
        remoteSort: false,
        singleSelect: true,
        pagePosition: 'bottom',
        pageNumber: 1,
        pageSize: 20,
        columns: bedColumns,
        onCheck: (index, data) => {
            currentBedItem = data;
        },
        onUncheck: (index, data) => {
            currentBedItem = undefined;
        },
        loadFilter: pagerFilter
    });
}

function initBedListTableToolbar() {
    $("#bed_add").on('click', () => {
        if(currentItem ===undefined){
            showToast('提示','请先选择医院');
            return;
        }
        currentBedItem = undefined;
        showBedDialog();
    });

    $("#bed_edit").on('click', () => {
        if (currentBedItem) {
            showBedDialog();
        } else {
            showToast('提示', '请选择要修改的数据');
        }
    });

    $("#bed_delete").on('click', () => {
        if (currentBedItem) {
            showDeleteToast(deleteBedItem);
        } else {
            showToast('提示', '请选择要删除的数据');
        }
    });

    $("#bed_query").on('click', () => {
        queryHospitalBedByHid();
    });

    $("#bed_save").on('click',function () {
        saveOrUpdateBedInfo();
    });

    $("#bed_close").on('click',function () {
        $("#bed_ff").dialog('close');
    });
}

function showBedDialog() {
    $("#bed_ff").dialog({
        title:'床位信息',
        onOpen:function () {
            if(currentBedItem){
                $("#bed_id").textbox('setValue',currentBedItem.id);
                $("#bed_name").textbox('setValue',currentBedItem.bed);
            }
        },
        onClose:function () {
            $("#bed_id").textbox('setValue','');
            $("#bed_name").textbox('setValue','');
        }
    });
    $("#bed_ff").dialog('open');
}


function loadHospitalData() {
    $.ajax({
        url: "/api/hospital",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $hospitalTable.datagrid({data: data.data});
            }
        },
        error:errorHandler
    })
}

function saveHospitalInfo() {
    let form = $("#ff");
    if (form.form('validate')) {
        let data = form.serializeObject();
        // console.log(data);
        if (data.id) {
            doSaveOrUpdateHospitalInfo(data, 'PUT');
        } else {
            doSaveOrUpdateHospitalInfo(data, 'POST');
        }

    } else {
        showToast('警告', '存在校验未通过项目');
    }
}

function doSaveOrUpdateHospitalInfo(data, method) {
    $.ajax({
        url: '/api/hospital',
        type: method,
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadHospitalData();
                resortHospitalFormData();
            }
        },
        error:errorHandler
    })
}

function doDeleteHospitalRecord() {
    $.ajax({
        url: '/api/hospital/' + currentItem.id,
        type: 'DELETE',
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadHospitalData();
            }
        },
        error:errorHandler
    })
}

function saveOrUpdateBedInfo() {
    let form = $("#bed_ff");
    if (form.form('validate')) {
        let data = form.serializeObject();
        // console.log(data);
        if (data.id) {
            doSaveOrUpdateBedInfo(data, 'PUT');
        } else {
            doSaveOrUpdateBedInfo(data, 'POST');
        }

    } else {
        showToast('警告', '存在校验未通过项目');
    }
}

function doSaveOrUpdateBedInfo(data,method) {
    data.hid = currentItem.id;
    $.ajax({
        url: '/api/bed',
        type: method,
        data:JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                queryHospitalBedByHid();
                $("#bed_id").textbox('setValue','');
                $("#bed_name").textbox('setValue','');
            }
        },
        error:errorHandler
    })
}

function deleteBedItem() {
    $.ajax({
        url: '/api/bed/'+currentBedItem.id,
        type: 'delete',
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                queryHospitalBedByHid();
            }
        },
        error:errorHandler
    })
}