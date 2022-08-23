<template>
  <div class="chatroomBox">
    <ul class="contentBox">
      <li class="messageItem" v-for="(log, index) in logs" :key="index">
        <p>{{ log }}</p>
      </li>
    </ul>
    <el-row :gutter="20">
      <el-col :span="12"
      ><el-input v-model="message" placeholder="请输入内容"></el-input
      ></el-col>
      <el-col :span="12">
        <el-button @click="mqttPublish" type="success" round
        >发送消息</el-button
        ></el-col
      >
    </el-row>
  </div>

</template>

<script>
import mqtt from "mqtt";

export default {
  name: "test",
  data() {
    return {
      client: "",
      message: "",
      logs: [],
    };
  },
  mounted() {
    this.initMqtt();
    this.mqttReceive();
  },
  methods: {
    /**
     * @name:初始化mqtt
     * @msg:
     * @param {*}
     * @return {*}
     */
    initMqtt() {
      let vm = this;
      let commonApi = "ws://mq.tongxinmao.com:18832/mqtt";
      var mqtt = require("mqtt");
      var options = {
        //mqtt客户端的id
        clientId: "mqttjs_" + Math.random().toString(16).substr(2, 8),
      };
      vm.client = mqtt.connect(commonApi, options);
      this.client.on("connect", function () {
        console.log("连接成功....");
      });
      //如果连接错误，打印错误
      vm.client.on("error", function (err) {
        console.log("err=>", err);
        vm.client.end();
      });
    },
    /**
     * @name:发布mqtt消息
     * @msg:
     * @param {*}
     * @return {*}
     */
    mqttPublish() {
      let topic = "topic/response"; //和后台约定好的主题
      this.client.publish(topic, JSON.stringify(this.message));
    },
    /**
     * @name:接收mqtt消息
     * @msg:
     * @param {*}
     * @return {*}
     */
    mqttReceive() {
      let topic = "topic/response"; //要接收的主题
      const vm = this;
      vm.client.subscribe(topic, function (err) {
        if (!err) {
          console.log("subscribe success!");
        } else {
          //打印错误
          console.log("err", err);
        }
      });
      vm.client.on("message", function (topic, message) {
        vm.logs.push(message);
      });
    },
  },
  destroyed() {
    if (this.client.end) this.client.end();
  },
}
</script>

<style scoped>

</style>