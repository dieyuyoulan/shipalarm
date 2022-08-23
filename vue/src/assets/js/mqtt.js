/*
 * 订阅端
 */
function subscribe(topicArray){
    var options = {
        clientId:'mqttjs_'+Math.random().toString(16).substr(2,8),
        protocolId: 'MQIsdp',
        protocolVersion: 3
    };
    var client = mqtt.connect("ws://mq.tongxinmao.com:18832/mqtt",options)
    client.on('connect',function(connack){
        client.subscribe(topicArray,function(err){
            if(err){
                alert("订阅错误！");
                console.log("subscribe error!");
            }
            console.log("connect success!")
        });
    });
    client.on('error',function(err){
        console.log(err)
        client.end()
    });
    client.on('message',function(topicArray,message,packet){
        console.log(topicArray);
        var myDate = new Date();
        var music = document.getElementById('music');
        music.play();
        var json=JSON.parse(message);
        var alertMessage = "报警信息："+"x轴"+json.x+"y轴"+json.y+"事件:"+json.topic+"时间"+myDate.toLocaleString();
        alert(alertMessage);

        var html = '';
        html = html + '<tr>';
        html = html + '<td>' +1+Math.random()*100+ '</td>';
        html = html + '<td>' + myDate.toLocaleString() + '</td>';
        html = html + '<td>' + json.topic + '</td>';
        html = html + '<td>' + json.x + '</td>';
        html = html + '<td>' + json.y + '</td>';

        $('#tr').append(html);
        var url = "information";
        var data = {
            "id":1+Math.random()*100,
            "time":myDate.toLocaleString(),
            "topic":json.topic,
            "x":json.x,
            "y":json.y
        }
        $.ajax({
            "url": url,
            "data": data,
            "type": "POST",
            "dataType": "json",
            "success": function(json) {

            }
        });
    });



    client.on('reconnect',function(){
        console.log("reconnect");
    });
    client.on('close',function(){
        console.log("close");
    });	client.on('offline',function(){
        console.log("offline");
    })
    client.on('error',function(error){
        console.log("error");
    });
    client.on('end', function () {
        console.log("end");
    });

    /* 发送任何数据包 */
    client.on('packetsend', function (packet) {
        console.log("packetsend");
    });

    /* 收到任何数据包 */
    client.on('packetreceive', function (packet) {
        console.log("packetreceive");
    });
};



function publish(topic,id){
    var options = {
        clientId:'mqttjs_'+Math.random().toString(16).substr(2,8),
        protocolId: 'MQIsdp',
        protocolVersion: 3
    };
    var client = mqtt.connect('ws://mq.tongxinmao.com:18832/mqtt',options);
    var center = map.getCenter();

    client.on('connect',function(connack){
        console.log("success!");
        if(topic=='publish/test/water'){
            var json = "{ \"topic\":\"落水事故\",\"x\":"+center.lng+",\"y\":"+center.lat+"}";
            client.publish(topic, json);
            alert('已发送');
        }
        if(topic=='publish/test/machine') {
            var json = "{ \"topic\":\"仪器故障\", \"x\": " + center.lng + ", \"y\": " +center.lat+" }";
            client.publish(topic, json);
            alert('已发送');
        }
        if(topic=='publish/test/fire') {
            var json = "{ \"topic\":\"船体着火\", \"x\": " + center.lng + ", \"y\": " +center.lat+" }";
            client.publish(topic, json);
            alert('已发送');
        }
        if(topic=='publish/test/beat') {
            var json = "{ \"topic\":\"船内纠纷\", \"x\": " + center.lng + ", \"y\": " +center.lat+" }";
            client.publish(topic, json);
            alert('已发送');
        }

    });
    client.on('message',function(topic,message){
        console.log("message");
    });
    client.on('rennect',function(){
        console.log("reconnect");
    });
    client.on('close',function(){
        console.log("close");
    });
    client.on('error',function(){
        console.log("error");
    });
    client.on('end',function(){
        console.log("end");
    });
    client.on('packetsend', function (packet) {
        console.log("packetsend");
    });
    client.on('packetreceive', function (packet) {
        console.log("packetreceive");
    });
}