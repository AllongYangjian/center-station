const columns = [[
    {
        title: 'ID',
        field: 'logId',
        align: 'center',
        width: 1,
        hidden:true
    },
    {
        title: '日志类型',
        field: 'logType',
        align: 'center',
        width: 1
    },
    {
        title: '描述',
        field: 'description',
        align: 'center',
        width: 1
    },
    {
        title: '请求方法',
        field: 'method',
        align: 'center',
        width: 1
    },
    {
        title: '请求参数',
        field: 'params',
        align: 'center',
        width: 1
    },
    {
        title: '请求IP',
        field: 'requestIp',
        align: 'center',
        width: 1
    },
    {
        title: '请求地址',
        field: 'address',
        align: 'center',
        width: 1
    },
    {
        title: '请求浏览器',
        field: 'browser',
        align: 'center',
        width: 1
    },
    {
        title: '请求耗时(毫秒)',
        field: 'time',
        align: 'center',
        width: 1
    },
    {
        title: '异常信息',
        field: 'exceptionDetail',
        align: 'center',
        width: 1
    },
    {
        title: '请求时间',
        field: 'createTime',
        align: 'center',
        width: 3
    }
]];


var $logTable;
var currentItem;
var currentHospitalId;

$(function () {
    initLogTable();


    $("#query").on('click', () => {
        loadLogData();
    });

    loadLogData();

});




/**
 * 初始化用户信息
 */
function initLogTable() {
    $logTable = $("#log_list");
    $logTable.datagrid({
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
    let dg =  $logTable.datagrid({
        filterBtnIconCls:'icon-filter'
    });
    dg.datagrid('enableFilter',[
        {
            field:'time',
            type:'numberbox',
            options:{precision:1},
            op:['equal','notequal','less','greater']
        },
        {
            field: 'logType',
            type:'combobox',
            options:{
                panelHeight:'auto',
                data:[{value:'INFO',text:'INFO'},{value:'ERROR',text:'ERROR'},{value:'',text:'ALL'}],
                onChange:function(value){
                    if (value === ''){
                        dg.datagrid('removeFilterRule', 'logType');
                    } else {
                        dg.datagrid('addFilterRule', {
                            field: 'logType',
                            op: 'equal',
                            value: value
                        });
                    }
                    dg.datagrid('doFilter');
                }
            }
        }
    ]);
    $logTable.datagrid('enableFilter');	// enable filter
}

/**
 * 记载日志信息
 */
function loadLogData() {
    $.ajax({
        url:  "/api/log",
        type: 'get',
        dataType: 'json',
        success: data => {
            if (data.code === 200) {
                $logTable.datagrid({data: data.data});
            }
        },
        error:errorHandler
    })
}


