var KEY_USER = "KEY_USER";

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

$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    // console.debug('serializeObject', a);
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

$.fn.formSerialize = function (formdate) {
    var element = $(this);
    if (!!formdate) {
        for (var key in formdate) {
            var $id = element.find('#' + key);
            var value = $.trim(formdate[key]).replace(/ /g, '');
            var type = $id.attr('type');
            if ($id.hasClass("select2-hidden-accessible")) {
                type = "select";
            }
            switch (type) {
                case "checkbox":
                    if (value == "true") {
                        $id.attr("checked", 'checked');
                    } else {
                        $id.removeAttr("checked");
                    }
                    break;
                case "select":
                    $id.val(value).trigger("change");
                    break;
                default:
                    $id.val(value);
                    break;
            }
        }
        ;
        return false;
    }
    var postdata = {};
    element.find('input,select,textarea').each(function (r) {
        var $this = $(this);
        var id = $this.attr('id');
        var type = $this.attr('type');
        switch (type) {
            case "checkbox":
                postdata[id] = $this.is(":checked");
                break;
            default:
                var value = $this.val() == "" ? " " : $this.val();
                if (!$.request("keyValue")) {
                    value = value.replace(/ /g, '');
                }
                postdata[id] = value;
                break;
        }
    });
    if ($('[name=__RequestVerificationToken]').length > 0) {
        postdata["__RequestVerificationToken"] = $('[name=__RequestVerificationToken]').val();
    }
    return postdata;
};