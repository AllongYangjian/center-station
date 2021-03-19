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

const LINE_START_X = 50;

const IS_NEW_VERSION = true;

/**
 * 供界面调用的方法
 */
function bindViewData() {
    // drawEcgBg();
    let showBg = localStorage.getItem("showBg");
    if (showBg === "true") {
        drawEcgBg();
        $("#showBg").attr("checked", true);
    } else {
        $("#showBg").attr("checked", false);
    }

    ecg();
}

function restoreData() {
    bedBackgroundCanvasMap.clear();
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
        const canvas = document.getElementById("background_" + p.bedNo);
        if (canvas) {
            let canvasObj = new Object(); //创建一个对象用来缓存数据
            const ctx = canvas.getContext("2d");
            canvasObj.canvas = canvas;
            canvasObj.canvasCtx = ctx;
            canvasObj.width = canvas.width;
            canvasObj.height = canvas.height;
            drawGrid(canvasObj);
            bedBackgroundCanvasMap.set(p.bedNo, canvasObj);//缓存背景canvas对象
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
    // console.log('initParams', mWaveKeys);
    for (let x = 0; x < mPatientList.length; x++) {
        let p = mPatientList[x];

        for (let y = 0; y < mWaveKeys.length; y++) {
            let key = mWaveKeys[y];
            let id = `line_${key.code}_${p.bedNo}`;
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
                canvasObj.maxHeight = 100;
                canvasObj.frameSize = key.frameSize;
                canvasObj.bed = p.bedNo;
                canvasObj.yMax = key.maxValue;

                bedLineMap.set(id, canvasObj);
                drawFillText(ctx, key.code);

                if (IS_NEW_VERSION) {
                    //恢复坐标轴
                    ctx.translate(0, -canvasKey.height / 2);//平移坐标
                } else {
                    //倒转Y轴
                    ctx.translate(0, canvasKey.height / 2);//平移坐标
                    ctx.scale(1, -1);
                }
            }

        }
    }
    // console.log('ssssssssssssss', bedLineMap);
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

/**
 * 更新数据的方法
 * @param bed 床位
 * @param key 关键字
 * @param data 数据
 */
function updateWaveData(bed, key, data) {
    let id = `line_${key}_${bed}`;
    let bedLine = bedLineMap.get(id);
    if (bedLine === null || bedLine === undefined) {
        console.error('updateWaveData', id);
        return;
    } else {

        let waveView = bedLine.waveView;
        if (waveView === null || waveView === undefined) {
            waveView = new WaveView2(0, one_time_points, 1, bedLine);
            waveView.waveData = data;
            waveView.loop();
            bedLine.waveView = waveView;
        } else {
            waveView.waveData = data;
        }
    }
}

class WaveView2 {
    currentX = 0;
    currentY = 0;
    lastX = 0;
    lastY = 0;
    // 每次画几个点
    step = 10;
    // Y值最大值
    yMax = 300;
    // 每个波形的高度
    itemHeight = 100;
    itemWidth = 500;
    frameSize = 256;//默认采样率
    // 橡皮檫宽度
    clearGap = 20;
    y_offset = 0;
    // 队列
    queue = [];
    waveData = [];
    bedLine;
    x_start = 45;
    grid_width = 5;

    /**
     * @param frameSize 1秒多少个点
     * @param yMax
     * @param y_offset y偏移
     * @param step 每次画几个点
     * @param speedRatio 扫纸速度，默认 25mm/s (1秒25个小格子 每个小格子0.04s)。 0.5表示扫纸速度为 12.5mm/s。2表示扫纸速度为 50mm/s。
     * @param bedLine canvas对象
     */
    constructor(y_offset, step, speedRatio, bedLine) {
        this.lineCtx = bedLine.ctx;
        this.bedLine = bedLine;
        if (bedLine.height >= bedLine.maxHeight) {
            this.itemHeight = bedLine.maxHeight;
        } else {
            this.itemHeight = bedLine.height;
        }
        this.itemWidth = bedLine.width;
        // this.frameSize = frameSize;
        this.yMax = bedLine.yMax;
        this.lastY = this.itemHeight / 2;
        this.y_offset = y_offset;
        this.step = step;
        this.speedRatio = speedRatio;
        this.frameSize = bedLine.frameSize;
        this.drawInterval = Math.floor((1 / this.frameSize) * 1000 * this.step); // 绘制时间间隔
        // this.waveData = data;
    }

    draw = () => {
        this.lineCtx.beginPath();
        if (this.lastX === 0) {
            this.lineCtx.clearRect(this.x_start - 2, this.y_offset, this.clearGap, 1000);
        } else {
            this.lineCtx.clearRect(this.x_start + this.lastX, this.y_offset, this.clearGap, 1000);
        }

        for (let i = 0; i < this.step; i++) {
            if (this.queue.length === 0) {
                this.currentY = this.itemHeight / 2;
            } else {
                this.currentY = (-1.0 * this.queue.shift()) / this.yMax * this.itemHeight + this.itemHeight;
            }

            if (this.currentY > this.itemHeight) {
                this.currentY = this.itemHeight;
            }

            this.lineCtx.moveTo(this.x_start + this.lastX, this.y_offset + this.lastY);
            this.lineCtx.lineTo(this.x_start + this.currentX, this.y_offset + this.currentY);

            this.lastX = this.currentX;
            this.lastY = this.currentY;

            this.currentX += (this.grid_width * 25 * this.speedRatio) / this.frameSize;
            if (this.x_start + this.currentX >= this.itemWidth - 25) {
                this.currentX = 0;
                this.lastX = 0;
            }
        }

        this.lineCtx.stroke();
    };

    loop = () => {
        this.draw();
        if (this.queue.length < this.step * 2) {
            let data = this.waveData;
            // this.frameSize = data.frameSize;
            // this.yMax = 512;
            // this.drawInterval = Math.floor((1 / this.frameSize) * 1000 * this.step); // 绘制时间间隔
            this.addData(data);
        }
        setTimeout(this.loop, this.drawInterval);
    };

    addData = (arr) => {
        //console.log('addData',arr.length);
        let array = [];
        for (let i = 0; i < arr.length; i++) {
            let val = (-1.0 * arr[i]) / this.yMax * this.itemHeight + this.itemHeight;
            array.push(val);
            this.queue.push(arr[i]);
        }

    }
}




