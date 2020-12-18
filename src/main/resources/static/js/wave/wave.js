

/**
 * 每秒画多少个点
 * @type {number}
 */
const one_time_points = 8;
/**
 * 存放心背景canvas对象的集合
 * @type {Map<id, canvas>}
 */
const bedBackgroundCanvasMap = new Map();
/**
 * 存放每条曲线的集合
 * @type {Map<id, canvas>}
 */
const bedLineMap = new Map();
/**
 * 存放 setInterval返回值的map
 * <p>
 *  1、界面每次重新加载时需要重置该map,同时清楚id对应的周期函数
 * </p>
 * @type {Map<any, any>}
 */
const timeIntervalIdsMap = new Map();

const testMode = true;
const LINE_START_X = 50;
const ONE_POINT_PIXEL = 1.0;

const TIME_INTERVAL = 5000;

/**
 * 供界面调用的方法
 */
function bindViewData() {
    // drawEcgBg();
    ecg();
}

function restoreData() {
    bedBackgroundCanvasMap.clear();
    bedLineMap.clear();
    timeIntervalIdsMap.forEach((value) => {
        clearInterval(value);
    })
}

/**
 * 画背景图
 */
function drawEcgBg() {
    for (let x = 0; x < mPatientList.length; x++) {
        let p = mPatientList[x];
        const canvas = document.getElementById("background_" + p.pid);
        if (canvas) {
            let canvasObj = new Object(); //创建一个对象用来缓存数据
            const ctx = canvas.getContext("2d");
            canvasObj.canvas = canvas;
            canvasObj.canvasCtx = ctx;
            canvasObj.width = canvas.width;
            canvasObj.height = canvas.height;
            drawGrid(canvasObj);
            bedBackgroundCanvasMap.set(p.pid, canvasObj);//缓存背景canvas对象
        }
    }
}

/**
 * 画网格
 * @param canvasObj
 */
function drawGrid(canvasObj) {
    let w = 25;//大网格宽度
    let h = 25;//大网格高度
    let left = 0; //七点坐标
    let top = 0; //终点坐标
    let right = canvasObj.width;
    let bottom = canvasObj.height;

    let ctx = canvasObj.canvasCtx;
    ctx.lineWidth = 1;
    ctx.strokeStyle = "rgba(77,76,97,0.53)";
    ctx.moveTo(left, top);
    ctx.lineTo(right, top);
    ctx.stroke();
    let F = w / 5;//小网格间距
    //画横线
    while (top < bottom) {
        top = top + h;
        ctx.moveTo(left, top);
        ctx.lineTo(right, top);
        ctx.stroke()
    }
    top = 0;
    ctx.moveTo(left, top);
    ctx.lineTo(left, bottom);
    ctx.stroke();
    //画竖线
    while (left < right) {
        left = left + w;
        ctx.moveTo(left, top);
        ctx.lineTo(left, bottom);
        ctx.stroke()
    }
    ctx.lineWidth = 1;
    ctx.strokeStyle = "rgba(77,76,97,0.53)";
    left = 0;
    top = 0;
    right = canvasObj.width;
    bottom = canvasObj.height;
    ////画小网格横线
    while (top < bottom) {
        for (var A = 0; A < 4; A++) {
            top = top + F;
            ctx.moveTo(left, top - 0.5);
            ctx.lineTo(right, top - 0.5);
            ctx.stroke()
        }
    }
    left = 0;
    top = 0;
    right = canvasObj.width;
    bottom = canvasObj.height;
    ////画小网格竖线
    while (left < right) {
        for (var A = 0; A < 4; A++) {
            left = left + F;
            ctx.moveTo(left - 0.5, top);
            ctx.lineTo(left - 0.5, bottom);
            ctx.stroke()
        }
    }
}

/**
 * 画图
 */
function ecg() {
    initParams();
    //测试逻辑
    if (testMode) {
        for (let x = 0; x < mPatientList.length; x++) {
            let p = mPatientList[x];
            for (let y = 0; y < mWaveKeys.length; y++) {
                let key = mWaveKeys[y];
                let id = `line_${key.code}_${p.pid}`;
                setTimeout(testData, 10 + x * 10 + y * 10, id, key.scale);
                // testData(id);
                // sleep(100);
            }
        }
    }

}

/**
 * 初始化参数，
 * <p>
 *     初始化每条曲线:
 *          1、起始位置为40，
 *          2、结束位置为曲线区域宽度
 *          3、当前频次为0
 * </p>
 */
function initParams() {
    console.log('initParams', mWaveKeys);
    for (let x = 0; x < mPatientList.length; x++) {
        let p = mPatientList[x];

        for (let y = 0; y < mWaveKeys.length; y++) {
            let key = mWaveKeys[y];
            let id = `line_${key.code}_${p.pid}`;
            let canvasKey = document.getElementById(id);
            if (canvasKey) {
                let canvasObj = new Object(); //创建一个对象用来缓存数据
                let ctx = canvasKey.getContext("2d");
                //设置初始化属性
                ctx.fillStyle = key.keyColor;
                ctx.strokeStyle = key.keyColor;
                ctx.lineWidth = 1;
                ctx.lineJoin = 'round';

                ctx.translate(0, canvasKey.height / 2);//平移坐标

                canvasObj.startX = LINE_START_X;
                canvasObj.endX = canvasKey.width;
                canvasObj.lineTimes = 0;
                canvasObj.canvas = canvasKey;
                canvasObj.ctx = ctx;
                canvasObj.width = canvasKey.width;
                canvasObj.height = canvasKey.height;
                canvasObj.maxHeight = 160;

                bedLineMap.set(id, canvasObj);
                drawFillText(ctx, key.code);

                if(key.code.indexOf('RESP')!==-1){
                    ctx.translate(0, canvasKey.height/2);//平移坐标
                }
            }

        }
    }
}

/**
 * 画文本
 * @param ctx canvas 对象
 * @param keyText 文本
 */
function drawFillText(ctx, keyText) {
    ctx.font = "bold 15px 黑体";
    ctx.fillText(keyText, 1, 0);
}

var flag = 1;
var flagIndex = 0;

/**
 * 测试方法
 * @param id
 * @param scale
 */
function testData(id, scale) {
    let bedLine = bedLineMap.get(id);
    if (bedLine === null || bedLine === undefined) {
        return;
    }
    let array = new Array();

    // let data = getOriginData();
    let data = getRandomArray(id);
    if(data ===undefined){
        console.log(id);
    }
    for (let y = 0; y < data.length; y += 2) {
        // 将值装换成负数，然后加上上限，这样就可以将数据倒转，不会导致波峰波谷颠倒
        array.push((-parseInt(data.substr(y, 2), 16)+bedLine.height) / scale);
    }
    console.log(array);
    let i = setInterval(() => {
        loopData(bedLine, array);
    }, TIME_INTERVAL);
    timeIntervalIdsMap.set(id, i, scale);
}

function getRandomArray(id) {
    if (id.indexOf('SpO2') !== -1) {
        let index = randomNum(0, spo2Data.length);
        if(index>=spo2Data.length){
            index = spo2Data.length-1;
        }
        return spo2Data[index];
    }else if(id.indexOf('RESP')!==-1){
        let index = randomNum(0, respData.length);
        if(index>=respData.length){
            index = respData.length-1;
        }
        return respData[index];
    }else if(id.indexOf('ECG')!==-1){
        let index = randomNum(0, waveData.length);
        if(index>=waveData.length){
            index = waveData.length-1;
        }
        return waveData[index];
    }
    else {
        let index = randomNum(0, waveData.length);
        if(index>=waveData.length){
            index = waveData.length-1;
        }
        return waveData[index];
    }
}


/**
 * 获取原始演示数据
 * @returns {any[]}
 */
function getOriginData() {
    flag++;
    if (flagIndex >= waveData.length) {
        flagIndex = 0;
    }
    let data = waveData[flag];
    flagIndex++;
    return data;
}

/**
 * 更新数据的方法
 * @param bed 床位
 * @param key 关键字
 * @param data 数据
 * @param scale 缩放系数
 */
function updateWaveData(bed, key, data, scale) {
    let id = `line_${key}_${bed}`;
    let bedLine = bedLineMap.get(id);
    if (bedLine === null || bedLine === undefined) {
        console.error('updateWaveData', id);
        return;
    }
    let array = data.map(item => -item / scale);

    loopData(bedLine, array);
}

/**
 * 循环画曲线
 * <p>
 *     该方法需要优化
 *      存在的问题：
 *      1、曲线不够圆滑，会存在锯齿现象
 *      2、画的点太多会导致频率过快，曲线更新频率过快，肉眼难以识别
 * </p>
 * @param bedLine 曲线对象
 * @param array 数据
 */
function loopData(bedLine, array) {
    let ctx = bedLine.ctx;
    let count = array.length / one_time_points;
    let time = TIME_INTERVAL / count;//需要间隔多久
    // console.log('loopData', time, new Date().getTime());
    let startX = bedLine.startX;
    let lineTimes = bedLine.lineTimes;

    if (lineTimes === 0 && startX === LINE_START_X) {
        ctx.beginPath();
        // ctx.moveTo(startX, bedLine.height/2);//起始位置
    }
    for (var x = 0; x < one_time_points; x++) {
        let index = lineTimes * one_time_points + x;
        if (lineTimes === 0 && startX === LINE_START_X) {
            ctx.moveTo(startX, array[index]);//起始位置
            // bedLine.lastX = startX;
            // bedLine.lastY = array[index];
        } else {
            startX += ONE_POINT_PIXEL;
            if (startX > bedLine.endX) {//说明到终点
                startX = LINE_START_X;
                ctx.stroke();
                ctx.beginPath();
                ctx.moveTo(startX, array[index]);
                bedLine.startX = startX;
            } else {
                bedLine.startX = startX;
                if (index < array.length) {
                    ctx.lineTo(startX, array[index]);
                    // bedLine.lastX = startX;
                    // bedLine.lastY = array[index];
                } else {
                    ctx.stroke();
                    bedLine.lineTimes = 0;
                    return;
                }
            }
        }
        ctx.clearRect(startX - 10, -1000, 20, 10000);
    }
    ctx.stroke();

    bedLine.lineTimes = (++lineTimes);
    setTimeout(loopData, time, bedLine, array);
}

