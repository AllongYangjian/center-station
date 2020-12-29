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
    initBedToolbar();
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

function initBedToolbar() {
    $("#all").on('click',()=>{
        if(mCacheBeds!==undefined){
            mCacheBeds.forEach(item=>{
                item.selected = true;
            });
            inflateViewByData()
        }
    });

    $("#reverse").on('click',()=>{
        if(mCacheBeds!==undefined){
            mCacheBeds.forEach(item=>{
                item.selected = !item.selected;
            });
            inflateViewByData()
        }
    });

    $("#save").on('click',()=>{
        saveUserBedInfo();
    });

    $("#query").on('click',()=>{
        loadBeds();
    });
}

function loadBeds() {
    $.ajax({
        url:'/api/bed',
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
    return '<div class="item-bed" data-tooltip="点击选择床位" id="bed_'+item.id+'" onclick="changeVisible(this)" onmouseover="MouseTip.start(this)" style="width: '+itemWidth+'px;">' +
            '<div class="hospital">'+item.hospitalName+'</div>'+
            '<div class="bed">'+item.bed+'</div>'+
            '<img class="checkbox" src="/static/images/checkbox.png" style="display: '+(item.selected?"block":"none")+'">'+
        '</div>';
}

function changeVisible(e) {
    console.log(e);
    let checkbox =$(e).children('.checkbox')[0];
    $(checkbox).toggle();
    let visible = $(checkbox).is(':visible');
    console.log(visible);
    let id = $(e).attr('id');
    let idStr = id.split("_");
    let bedId = idStr[1];
    mCacheBeds.forEach(item=>{
        if(item.id.toString() ===bedId){
            item.selected = visible;
        }
    })
}

function saveUserBedInfo() {
    if(mCacheBeds===null ||mCacheBeds ===undefined){
        showToast('提示','未获取到床位信息');
        return;
    }
    $.ajax({
        url:'/user/bed',
        type:'post',
        data:JSON.stringify(mCacheBeds),
        contentType:'application/json',
        success:function (data) {
            showToast('提示',data.message);
            if(data.code ===200){
                loadBeds();
            }
        },
        error:errorHandler
    })
}