<template>
  <div>
  <div id="map" class="baidu" style="width: 1185px"></div>
  <ul class="btn-wrap" style="z-index: 99;">
    <li type="text" class = "light btn" @click="addAccident">新增事件</li>
    <li class = "light btn" @click="publishMess">发布报警</li>
    <li class = "light btn" @click="advent">确认到达</li>
  </ul>
  <el-dialog v-model="dialogVisible">
    <el-form :model="form">
      <el-form-item prop="accidence" label="新增事件">
        <el-input v-model="form.accidence"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">确认</el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog v-model="dialogVisiblePublish">
    <el-form :inline="true" :model="form2">
      <el-form-item label="选择事件" prop="accidence">
        <el-select v-model="message" placeholder="请选择事件">
          <el-option v-for="item in depss" :key="item.id" :label="item.accidence" :value="item.accidence"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisiblePublish = false;">取消</el-button>
        <el-button type="danger" @click="mqttPublish">确认</el-button>
      </span>
    </template>
  </el-dialog>

  </div>

</template>

<style>

body, html {
  width: 100%;
  height: 100%;
  margin: 0;
}
#allmap {
  height: 100%;
  width: 100%;
}
ul li {
  list-style: none;
}
.btn-wrap {
  z-index: 999;
  position: fixed;
  bottom: 3.5rem;
  margin-left: 3rem;
  padding: 1rem 1rem;
  border-radius: .25rem;
  background-color: #fff;
  box-shadow: 0 2px 6px 0 rgba(236, 27, 69, 0.5);
}
.btn {
  width: 75px;
  height: 30px;
  float: left;
  background-color: #fff;
  color: rgba(236, 27, 51, 1);
  font-size: 14px;
  border:1px solid rgb(236, 27, 51);
  border-radius: 5px;
  margin: 0 5px;
  text-align: center;
  line-height: 30px;
}
.btn:hover {
  background-color: rgba(236, 27, 69, 0.8);
  color: #fff;
}



</style>

<script>

import request from "../utils/request";
import BMap from 'BMap'
import mqtt from 'mqtt'
import { defineComponent, ref } from 'vue'
import { ElMessageBox } from 'element-plus'
import {BMPGL} from "../bmpgl";


export default {
  inject: ['reload'],
  name: 'Home',
  components: {

  },
  data() {
    return {
      dialogVisible:false,
      dialogVisiblePublish: false,
      form: {},
      depss: [],
      form2: {},
      longitude: '',
      latitude: '',
      message: "",
      city: '',
      forecasts:[],
      formTwo: {}, //确认抵达表单
      adventDate: ""
    }

  },

  created() {
    this.getAccidence()

  },

  mounted() {
    this.getCity();
    this.srch();
    this.initMqtt();
    this.mqttReceive();
  },

  methods: {
    getCity() {
      var map = new BMap.Map("map");
      const getLocation = new BMap.Geolocation();
      var _this = this;
      getLocation.getCurrentPosition((position) => {
        // position中存放所有的定位数据
        // console.log(position);
        // 这里获取的是城市和省
        let city = position.address.city;

        this.city = city.substring(0, 2);
        console.log(this.city)
        let province = position.address.province;
        let longitude = position.longitude;
        let latitude = position.latitude;
        _this.locationMsg = province + city;
        var point = new BMap.Point(longitude, latitude);  // 创建点坐标
        map.centerAndZoom(point, 15);  // 初始化地图，设置中心点坐标和地图级别
        map.enableScrollWheelZoom(true);
        var marker1 = new BMap.Marker(new BMap.Point(longitude, latitude));
        map.addOverlay(marker1);
        this.longitude = longitude;
        this.latitude = latitude;
      }, () => {
        _this.locationMsg = '定位失败!';
      }, {provider: 'baidu'});
    },
    addAccident(){
      this.dialogVisible = true;
    },
    submit(){
      request.post("/accident/acc", this.form).then(res => {
          console.log(this.form)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "success"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.dialogVisible = false
          this.reload()
      })
    },

    getAccidence(){
      request.post("/accident/getAcc", this.form2).then(res=>{
        console.log(res);
        this.depss = res.data
      })
    },

    publishMess(){
      this.dialogVisiblePublish = true;
    },

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

    mqttPublish() {
      let topic = "public/TEST/accident"; //和后台约定好的主题
      console.log(topic)
      var message = JSON.stringify(this.message)
      var json = "{ \"topic\":" + message + ",\"x\":"+this.longitude+",\"y\":"+this.latitude+"}";
      this.client.publish(topic, json);
      this.dialogVisiblePublish = false;
      alert("发送成功");
    },

    srch(){
      console.log(this.city)

    },
    advent(){
      var myDate = new Date();
      let date = {
        year: myDate.getFullYear(),
        month: myDate.getMonth() + 1,
        date: myDate.getDate()
      }
      this.adventDate = date.year + "/" + date.month + "/" + date.date;
      request.get("/user/advent", {
        params: {
          adventDate : this.adventDate
        }
      }).then(res => {
        console.log(res)
        alert("确认到达")
      })
    },
    mqttReceive() {
      let topic = "public/TEST/command"; //要接收的主题
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
        console.log(topic);
        alert(message)

      });
    }
  }
}
</script>

<style>

.box-card {
  width: 1200px;
}

.baidu{
  width: 1200px;
  height: 700px;
}

.weather_list{

  text-align:center;

}


</style>
