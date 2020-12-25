const columns = [[
    {
        field: 'ck',
        checkbox: true
    },
    // {
    //     title: 'ID',
    //     field: 'id',
    //     align: 'center',
    //     width: 1
    // },
    {
        title: '医院',
        field: 'hospitalName',
        align: 'center',
        width: 1
    }, {
        title: '床号',
        field: 'bed',
        align: 'center',
        width: 1
    },
    {
        title: '病人姓名',
        field: 'name',
        align: 'center',
        width: 1
    },
    {
        title: '住院号',
        field: 'zyh',
        align: 'center',
        width: 1
    }
]];


var $bindTable;
var currentItem;

$(function () {
    initBindTable();

    $("#add").on('click', () => {
        currentItem = undefined;
        $bindTable.datagrid('uncheckAll');
        showBindDialog();
    });

    $("#edit").on('click', () => {
        if (currentItem) {
            showBindDialog();
        } else {
            showToast('提示', '请选择要修改的数据');
        }
    });

    $("#delete").on('click', () => {
        if (currentItem) {
            showDeleteToast(doDeleteBindRecord);
        } else {
            showToast('提示', '请选择要删除的数据');
        }
    });

    $("#query").on('click', () => {
        loadBindData();
    });

    $("#bind_save").on('click', () => {
        saveBindInfo();
    });

    $("#bind_close").on('click', () => {
        $("#bind_dialog").dialog('close');
    });

    $("#bedId").combobox({
        onSelect:row=>{
            loadPatientInfo(row.hid);
        }
    });

    $("#patientId").combobox({
        onLoadSuccess:function () {
            if (currentItem) {
                $("#patientId").combobox('setValue', currentItem.patientId);
            }
        }
    });

    loadBindData();
    loadBeds();
});


/**
 * 初始化用户信息表单
 */
function bindBindFormData() {
    if (currentItem) {
        $("#id").textbox('setValue', currentItem.id);
        $("#bedId").combobox('setValue', currentItem.bedId);
        // $("#patientId").combobox('setValue', currentItem.patientId);
    }
}

/**
 * 重置用户信息表单
 */
function resortBindFormData() {
    // $("#ff")[0].reset(); //并没有删除原来的值
    $("#id").textbox('setValue', '');
    $("#bedId").combobox('setValue', '');
    $("#patientId").combobox('setValue', '');
}

/**
 * 显示用户信息表单对话框
 */
function showBindDialog() {
    $("#bind_dialog").dialog({
        onOpen: () => {
            bindBindFormData();
        },
        onClose: () => {
            resortBindFormData();
        }
    });
    $("#bind_dialog").dialog('open');
}

/**
 * 初始化用户信息
 */
function initBindTable() {
    $bindTable = $("#bind_list");
    $bindTable.datagrid({
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
        loadFilter: pagerFilter
    });
}

/**
 * 记载用户信息
 */
function loadBindData() {
    $.ajax({
        url: "/api/bedPatient",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $bindTable.datagrid({data: data.data});
            }
        },
        error:errorHandler
    })
}

function loadBeds() {
    $.ajax({
        url:'/api/bed',
        type:'get',
        dataType:'json',
        success:data=>{
            if(data.code ===200){
                if(data.data!==null){
                    data.data.forEach(item=>{
                        item.bed = item.hospitalName+"-"+item.bed;
                    });
                    $("#bedId").combobox({data:data.data});
                }

            }
        },
        error:errorHandler
    })
}

function loadPatientInfo(hid) {
    $.ajax({
        url:'/api/patient/'+hid,
        type:'get',
        dataType:'json',
        success:data=>{
            if(data.code ===200){
                if(data.data!==null){

                    $("#patientId").combobox({data:data.data});
                }

            }
        },
        error:errorHandler
    })
}

function saveBindInfo() {
    let form = $("#ff");
    if (form.form('validate')) {
        let data = form.serializeObject();
        // console.log(data);
        if (data.id) {
            doSaveOrUpdateBindInfo(data, 'PUT');
        } else {
            doSaveOrUpdateBindInfo(data, 'POST');
        }

    } else {
        showToast('警告', '存在校验未通过项目');
    }
}

function doSaveOrUpdateBindInfo(data, method) {
    $.ajax({
        url: '/api/bedPatient',
        type: method,
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadBindData();
                resortBindFormData();
            }
        },
        error:errorHandler
    })
}

function doDeleteBindRecord() {
    $.ajax({
        url: '/api/bedPatient/' + currentItem.id,
        type: 'DELETE',
        dataType: 'json',
        contentType: 'application/json',
        success: (data) => {
            showToast('提示', data.message);
            if (data.code === 200) {
                loadBindData();
            }
        },
        error:errorHandler
    })
}

