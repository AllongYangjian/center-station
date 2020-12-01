/**
 * 根据关键字获取颜色
 * @param key
 * @returns {string}
 */
function getColorByKey(key) {
    if (key === 'HR') {
        return '#6BE43B';
    } else if (key === 'Resp' || key === 'RR') {
        return '#F7DC3C';
    } else if (key === 'SpO2') {
        return '#6EFBFC';
    } else if (key === 'NIBP') {
        return '#ffffff';
    } else if (key === 'PR') {
        return '#6EFBFC';
    } else {
        return '#ffffff';
    }
}