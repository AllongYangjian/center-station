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

var $hospitalTable;
var currentItem;

$(function () {
    initHospitalTable();

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

    loadHospitalData();
});

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
        },
        onUncheck: (index, data) => {
            currentItem = undefined;
        },
        loadFilter: pagerFilter
    });
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
        }
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
        }
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
        }
    })
}