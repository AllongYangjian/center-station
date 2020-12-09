const KEY_USER = "KEY_USER";

/**
 * 缓存数据到本地
 * @param key
 * @param val
 * @returns {any}
 */
function getKey(key, val) {
    if (localStorage in window) {
        let value = localStorage.getItem(key);
        if (value !== null && value !== undefined) {
            return JSON.parse(value);
        }
        return val;
    } else {
        let value = sessionStorage.getItem(key);
        if (value !== null && value !== undefined) {
            return JSON.parse(value);
        }
        return val;
    }
}

/**
 * 从本地缓存获取数据
 * @param key
 * @param value
 */
function saveKey(key, value) {
    if (localStorage in window) {
        localStorage.setItem(key, JSON.stringify(value));
    } else {
        sessionStorage.setItem(key, JSON.stringify(value));
    }
}