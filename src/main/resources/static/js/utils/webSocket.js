var socket;

function connectServer(url) {
    if ('WebSocket' in window) {
        socket = new WebSocket(url);
        socket.onopen = () => {
            console.log("open");
        };
        socket.onmessage = (e) => {
            // console.log('connectServer', new Date().getTime());
            // if(e.data.indexOf('"BedLabel":"03"')!==-1){
            //     console.info(e.data);
            // }
            parseWaveDataFromServer(e.data);
        };
        socket.onclose = ev => {
            console.log('socket close,3s后自动重连');
            alert('socket关闭，请刷新界面重新链接');
        };
        socket.onerror = ev => {
            console.error(ev, '3s后自动重连');
            setTimeout(connectServer, 3000,url);
        };
    } else {
        alert('当前浏览器不支持WebSocket')
    }
}


function closeSocket() {
    if (socket !== null && socket !== undefined) {
        socket.close();
    }
}

function parseWaveDataFromServer(data) {
    let jsonData = JSON.parse(data);
    if (jsonData.TYPE === 'Wave') {
        let bedDatas = jsonData.DATA;
        bedDatas.forEach(item => {
            let patient = parsePatientInfo(item);
            parseWaveData(item.Wave, patient);
        })

    }
}

function parsePatientInfo(json) {
    let patient = {};
    for (var key in json) {
        if (key !== 'Wave') {//剔除波形数据
            patient[key] = json[key];
        }
    }
    // console.log('parsePatientInfo', patient);
    return patient;
}

function parseWaveData(waveArray, patient) {

    try {
        waveArray.forEach(item => {
            //todo 需要将床位改成pid
            let bed = patient.BedLabel;
            let invalidValue = item.InvalidValue;
            let key = item.Name;
            let arr = getItemArray(item.Data,invalidValue);
            let cyl = item.Samplerate;
            updateWaveData(bed,key,arr,cyl);
            // if (key === 'ECG I') {
            //     console.log(item.Name, new Date().getTime());
            //     key = 'ECG';
            //     bed = '123456';
            //     updateWaveData(bed, key, arr, 2);
            // }else if(key === 'Pleth'){
            //     bed = '123456';
            //     key = 'SpO2';
            //     updateWaveData(bed, key, arr, 1);
            // }
            // else if (key === 'ABP') {
            //     bed = '123456';
            //     key = 'RESP';
            //     updateWaveData(bed, key, arr, 1);
            // }
        })

    } catch (e) {
        console.error(e);
    }
}

function getItemArray(arr,value) {
    if(isEmpty(value)){
        return arr;
    }
    arr.forEach((item,index)=>{
        if(item === value){
            arr[index] = 0;
        }
    });
    return  arr;
    // console.error(arr);
}