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

var deptColumns = [[
    {
        title: 'ID',
        field: 'id',
        align: 'center',
        width: 1,
        hidden: true
    },
    {
        title: '代码',
        field: 'deptCode',
        align: 'center',
        width: 1
    },
    {
        title: '名称',
        field: 'deptName',
        align: 'center',
        width: 1
    },
    {
        title: '说明',
        field: 'remark',
        align: 'center',
        width: 1
    },
    {
        title: '地址',
        field: 'wsUrl',
        align: 'center',
        width: 2
    },

]];

var bedColumns = [[
    {
        title: 'ID',
        field: 'id',
        align: 'center',
        width: 1,
        hidden: true
    },
    {
        title: '床号',
        field: 'bedNo',
        align: 'center',
        width: 1
    },
    {
        title: '床标号',
        field: 'bedLabel',
        align: 'center',
        width: 1
    }
]];

var $hospitalTable;
var $bedTable;
var $deptTable;
var currentItem, currentBedItem, currentDeptItem;

$(function () {
    initHospitalTable();
    initHospitalToolbar();
    loadHospitalData();

    initDeptToolbar();
    initDeptTable();

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
            loadDeptListByHid(data.id);
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
    if (currentItem) {
        url = '/api/bed/' + currentItem.id;
    } else {
        url = '/api/bed';
    }
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',
        success: data => {
            $bedTable.datagrid({data: data.data});
        },
        error: errorHandler
    })
}

function loadDeptListByHid(hid) {

    queryDeptListByHid(hid, function (data) {
        showToast('提示', data.message);
        if (data.code === 200) {
            $deptTable.datagrid({data: data.data});
        } else {
            $deptTable.datagrid({data: []});
        }
        $bedTable.datagrid({data:[]});
    });
}


function initDeptToolbar() {
    $("#dept_save").on('click', function () {
        saveOrUpdateDeptInfo();
    });

    $("#dept_close").on('click', function () {
        $("#dept_dialog").dialog('close');
    });

    $("#dept_query").on('click', function () {
        currentDeptItem = undefined;
        if (currentItem === undefined || currentItem === null) {
            showToast('提示', '请先选择医院');
            return;
        }
        loadDeptListByHid(currentItem.id);
    });

    $("#dept_add").on('click', function () {
        currentDeptItem = undefined;
        if (currentItem === undefined || currentItem === null) {
            showToast('提示', '请先选择医院');
            return;
        }
        $("#dept_dialog").dialog('open');
    });

    $("#dept_edit").on('click', function () {
        if (currentDeptItem === undefined || currentDeptItem === null) {
            showToast('提示', '请先选择科室');
            return;
        }
        $("#dept_dialog").dialog('open');
    });

    $("#dept_delete").on('click', function () {
        if (currentDeptItem === undefined || currentDeptItem === null) {
            showToast('提示', '请先选择科室');
            return;
        }
        showDeleteToast(deleteDeptInfo);
    });


    $("#dept_dialog").dialog({
        onOpen: function () {
            if (currentDeptItem) {
                $("#dept_id").textbox('setValue', currentDeptItem.id);
                $("#dept_code").textbox('setValue', currentDeptItem.deptCode);
                $("#dept_name").textbox('setValue', currentDeptItem.deptName);
                $("#dept_url").textbox('setValue', currentDeptItem.wsUrl);
            } else {
                $("#dept_id").textbox('setValue', '');
                $("#dept_code").textbox('setValue', '');
                $("#dept_name").textbox('setValue', '');
                $("#dept_url").textbox('setValue', '');
            }
        },
        onClose: function () {
            $("#dept_id").textbox('setValue', '');
            $("#dept_code").textbox('setValue', '');
            $("#dept_name").textbox('setValue', '');
            $("#dept_url").textbox('setValue', '');
        }
    });
}

function initDeptTable() {
    $deptTable = $("#dept_list");
    $deptTable.datagrid({
        rownumbers: true,
        showFooter: true,
        fitColumns: true,
        pagination: true,
        remoteSort: false,
        singleSelect: true,
        pagePosition: 'bottom',
        pageNumber: 1,
        pageSize: 20,
        columns: deptColumns,
        onCheck: (index, data) => {
            currentDeptItem = data;
            //查找医院床位信息
            loadDeptBedListByDeptId();
        },
        onUncheck: (index, data) => {
            currentItem = undefined;
        },
        loadFilter: pagerFilter
    });
}

function saveOrUpdateDeptInfo() {
    let form = $("#dept_ff");
    if (form.form('validate')) {
        let data = {};
        data.id = $("#dept_id").textbox('getValue');
        data.hospitalId = currentItem.id;
        data.deptCode = $("#dept_code").textbox('getValue');
        data.deptName = $("#dept_name").textbox('getValue');
        data.wsUrl = $("#dept_url").textbox('getValue');

        if (data.id) {
            doSaveOrUpdateDeptInfo(data, 'put');
        } else {
            doSaveOrUpdateDeptInfo(data, 'post');
        }
    } else {
        showToast('警告', '存在校验未通过项目');
    }
}

function doSaveOrUpdateDeptInfo(data, method) {
    saveOrUpdateDept(data, method, function (data) {
        showToast('提示', data.message);
        if (data.code === 200) {
            loadDeptListByHid(currentItem.id);
        }
    });
}

function deleteDeptInfo() {
    deleteDeptById(currentDeptItem.id, function (data) {
        showToast('提示', data.message);
        if (data.code === 200) {
            loadDeptListByHid(currentItem.id);
        }
    });
}

/**
 * 点击科室列表加载床位信息
 */
function loadDeptBedListByDeptId() {
    queryDeptBedListByDeptId(currentDeptItem.id, function (data) {
        showToast('提示', data.message);
        if (data.code === 200) {
            $bedTable.datagrid({data: data.data});
        } else {
            $bedTable.datagrid({data: []});
        }
    });
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
        if (currentDeptItem === undefined) {
            showToast('提示', '请先选择科室');
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
        if (currentDeptItem === undefined) {
            showToast('提示', '请先选择科室');
            return;
        }
        loadDeptBedListByDeptId();
    });

    $("#bed_save").on('click', function () {
        saveOrUpdateBedInfo();
    });

    $("#bed_close").on('click', function () {
        $("#bed_ff").dialog('close');
    });
}

function showBedDialog() {
    $("#bed_ff").dialog({
        title: '床位信息',
        onOpen: function () {
            if (currentBedItem) {
                $("#bed_id").textbox('setValue', currentBedItem.id);
                $("#bed_name").textbox('setValue', currentBedItem.bedNo);
                $("#bed_label").textbox('setValue', currentBedItem.bedLabel);
            } else {
                $("#bed_id").textbox('setValue', '');
                $("#bed_name").textbox('setValue', '');
                $("#bed_label").textbox('setValue', '');
            }
        },
        onClose: function () {
            $("#bed_id").textbox('setValue', '');
            $("#bed_name").textbox('setValue', '');
            $("#bed_label").textbox('setValue', '');
        }
    });
    $("#bed_ff").dialog('open');
}


function loadHospitalData() {
    queryHospitalList(function (data) {
        if (data.code === 200) {
            $hospitalTable.datagrid({data: data.data});
        }else {
            $hospitalTable.datagrid({data: []});
        }
        $deptTable.datagrid({data:[]});
        $bedTable.datagrid({data:[]});
    });
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
        error: errorHandler
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
        error: errorHandler
    })
}

function saveOrUpdateBedInfo() {
    let form = $("#bed_ff");
    if (form.form('validate')) {
        let data = {};
        data.id = $("#bed_id").textbox('getValue');
        data.bedNo = $("#bed_name").textbox('getValue');
        data.bedLabel = $("#bed_label").textbox('getValue');
        data.deptId = currentDeptItem.id;
        data.hospitalId = currentDeptItem.id;
        if (!isEmpty(data.id)) {
            doSaveOrUpdateBedInfo(data, 'PUT');
        } else {
            doSaveOrUpdateBedInfo(data, 'POST');
        }

    } else {
        showToast('警告', '存在校验未通过项目');
    }
}

function doSaveOrUpdateBedInfo(data, method) {
    saveOrUpdateDeptBedInfo(data, method, function (data) {
        showToast('提示', data.message);
        if (data.code === 200) {
            loadDeptBedListByDeptId();
        }
    });
}

function deleteBedItem() {
    deleteDeptBedById(currentBedItem.id, function (data) {
        showToast('提示', data.message);
        if (data.code === 200) {
            loadDeptBedListByDeptId();
        }
    });
}