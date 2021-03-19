/**
 * 获取当前用户id
 * @returns {Document.deptId}
 */
function getCurrentUserDeptId() {
    var data = getKey(KEY_USER,{});
    return data.deptId;
}

/**
 * 获取当前用户科室代码
 * @returns {Document.deptCode}
 */
function getCurrentUserDeptCode() {
    var data = getKey(KEY_USER,{});
    return data.deptCode;
}

/**
 * 获取当前科室 web socket url
 * @returns {Document.wsUrl}
 */
function getCurrentDeptUrl() {
    var data = getKey(KEY_DEPT,{});
    return data.wsUrl;
}



