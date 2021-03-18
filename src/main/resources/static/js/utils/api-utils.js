/**
 * 根据医院id查询科室列表
 * @param hid
 * @param callback
 */
function queryDeptListByHid(hid, callback) {
    $.ajax({
        url: '/api/dept/list/' + hid,
        type: 'get',
        dataType: 'json',
        success: function (data) {
            if (callback) {
                callback(data);
            }
        },
        error: errorHandler
    })
}

/**
 * 保存或更新科室信息
 * @param data dept
 * @param method put post
 * @param callback 回调
 */
function saveOrUpdateDept(data, method, callback) {
    $.ajax({
        url: '/api/dept',
        type: method,
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            if (callback) {
                callback(data);
            }
        },
        error: errorHandler
    })
}

/**
 * 删除科室
 * @param id 科室id
 * @param callback 回调
 */
function deleteDeptById(id, callback) {
    $.ajax({
        url: '/api/dept/' + id,
        type: 'delete',
        dataType: 'json',
        success: function (data) {
            if (callback) {
                callback(data);
            }
        },
        error: errorHandler
    })
}