var socket;

function connectServer() {
    if ('WebSocket' in window) {
        let url = 'ws://192.168.10.105:8001/ws/wave';
        socket = new WebSocket(url);
        socket.onopen = () => {
            console.log("open");
        };
        socket.onmessage = (e) => {
            console.log(e.data);
            parseWaveDataFromServer(e.data);
        };
        socket.onclose = ev => {
            console.log('socket close,3s后自动重连');
            alert('socket关闭，请刷新界面重新链接');
        };
        socket.onerror = ev => {
            console.error(ev, '3s后自动重连');
            setTimeout(connectServer, 3000);
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
    console.log('parsePatientInfo', patient);
    return patient;
}

function parseWaveData(waveArray, patient) {

    try {
        waveArray.forEach(item => {
            let bed = patient.BedLabel;
            let key = item.Name;
            let arr = item.Data;

            if (key === 'ECG I') {
                bed = '1';
                key = 'key1';
                updateWaveData(bed, key, arr, 20);
            } else if (key === 'TThor Imped') {
                bed = '1';
                key = 'key2';
                updateWaveData(bed, key, arr, 2);
            }
        })

    } catch (e) {
        console.error(e);
    }
}