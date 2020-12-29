let containerWidth,containerHeight;
let itemWidth; //最小宽度为80
let mCacheBeds;
let margin = 16;
let columns = 6;


$(function () {
    calculateViewSize();
    window.onresize = ()=>{
        calculateViewSize();
    };
    loadBeds();
});

function calculateViewSize() {
    containerHeight = $(".bed-container").height();
    containerWidth = $(".bed-container").width();
    itemWidth = (containerWidth-(columns+1)*margin)/6;
    // console.log(containerWidth,containerHeight);
    if(itemWidth<80){
        itemWidth = 80;
    }
    inflateViewByData();
}


function loadBeds() {
    $.ajax({
        url:'/api/bed/alarm',
        type:'get',
        dataType:'json',
        success:data=>{
            if(data.code ===200){
                mCacheBeds = data.data;
                inflateViewByData();
            }
        },
        error:errorHandler
    })
}

function inflateViewByData() {
    if(mCacheBeds ===undefined){
        return;
    }
    $(".bed-container").empty();
    let view = "";
    mCacheBeds.forEach(item=>{
        view+=getBedItemView(item);
    });
    // mCacheBeds.forEach(item=>{
    //     view+=getBedItemView(item);
    // });
    // mCacheBeds.forEach(item=>{
    //     view+=getBedItemView(item);
    // });
    $(".bed-container").append($(view));
}

function getBedItemView(item) {
    let imageView;
    let tips;
    if(item.alarmed){
        imageView =  '<img  class="checkbox"  src="/static/images/ic_alarm.png">';
        tips = "点击关闭床位报警"
    }else {
        imageView =  '<img class="checkbox" src="/static/images/ic_disable_alarm.png">';
        tips = "点击开启床位报警"
    }
    return '<div class="item-bed" onmouseover="MouseTip.start(this)" data-tooltip="'+tips+'" id="bed_'+item.id+'" onclick="changeAlarmEnabled(this)" style="width: '+itemWidth+'px;">' +
        '<div class="hospital">'+item.hospitalName+'</div>'+
        '<div class="bed">'+item.bed+'</div>'+
        imageView+
        '</div>';
}

function changeAlarmEnabled(e) {
    // console.log(e);
    // let checkbox =$(e).children('.checkbox')[0];
    // $(checkbox).toggle();
    // let visible = $(checkbox).is(':visible');
    // console.log(visible);
    let id = $(e).attr('id');
    let idStr = id.split("_");
    let bedId = idStr[1];
    $.ajax({
        url:'/api/bedAlarm/'+bedId,
        type: 'put',
        dataType: 'json',
        success:function (data) {
            showToast('提示',data.message);
            if(data.code ===200){
                loadBeds();
            }
        },
        error:errorHandler
    })
}