<template>
  <div class="wrap" id="app">
    <div class="search_form">
      <div class="form_group">
        <input type="text" class="input_txt" placeholder="请输入查询的天气" v-model="city" @keyup.enter="queryWeather" />
        <button class="input_sub" @click="queryWeather">
          搜 索
        </button>
      </div>
      <div class="hotkey">
<!--          <a href="javascript:;" @click="clickSearch('北京')">北京</a>-->
<!--          <a href="javascript:;" @click="clickSearch('上海')">上海</a>-->
<!--          <a href="javascript:;" @click="clickSearch('广州')">广州</a>-->
<!--          <a href="javascript:;" @click="clickSearch('深圳')">深圳</a>-->
        <a href="javascript:;" v-for="city in hotCitys" @click="clickSearch(city)">{{ city }}</a>
      </div>
    </div>
    <ul class="weather_list">
      <li v-for="(item,index) in forecastList" :key="item.date" :style="{transitionDelay:index*100+'ms'}">
        <div class="info_type">
          <span class="iconfont">{{ item.type }}</span>
        </div>
        <div class="info_temp">
          <b>{{ item.low}}</b>
          ~
          <b>{{ item.high}}</b>

        </div>
        <div class="info_date">
          <span>{{ item.date }}</span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: "weather",
  data(){
    return{
      city: "",
      forecastList: [],
      hotCitys: ["北京", "上海", "广州", "深圳"]
    }
  },
  mounted() {
    this.getCity()
  },
  methods: {
    queryWeather() {
      this.forecastList = [];
      request.get(`http://wthrcdn.etouch.cn/weather_mini?city=${this.city}`)
          .then(res => {
            console.log(res);
            this.forecastList = res.data.forecast;
          })
          .catch(err => {
            console.log(err);
          })
          .finally(() => { });
    },
    clickSearch(city) {
      this.city = city;
      this.queryWeather();
    },
    searchWeather:function(){
      //  console.log('天气查询');
      //  console.log(this.city);
      // 调用接口
      // 保存this
      var that = this;
      request.get('http://wthrcdn.etouch.cn/weather_mini?city='+this.city)
          .then(function(response){
            // console.log(response);
            console.log(response.data.forecast);
            that.weatherList = response.data.forecast
          })
          .catch(function(err){})
    },
    getCity() {
      const getLocation = new BMap.Geolocation();
      var _this = this;
      getLocation.getCurrentPosition((position) => {
        // position中存放所有的定位数据
        // console.log(position);
        // 这里获取的是城市和省
        let city = position.address.city;

        this.city = city.substring(0, 2);

      }, () => {
        _this.locationMsg = '定位失败!';
      }, {provider: 'baidu'});
    },
  }
}
</script>

<style scoped>
.search_form{
  width:640px;
  margin:100px auto 0;
}
.form_group{
  width:640px;
  height:40px;
  margin-top:45px;
}
.input_txt{
  width:538px;
  height:38px;
  padding:0px;
  float:left;
  border:1px solid #41a1cb;
  outline:none;
  text-indent:10px;
}
.input_sub{
  width:100px;
  height:40px;
  border:0px;
  float: left;
  background-color: #41a1cb;
  color:#fff;
  font-size:16px;
  outline:none;
  cursor: pointer;
  position: relative;
}
.input_sub.loading::before{
  content:'';
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
}
.hotkey{
  margin:3px 0 0 2px;
}
.hotkey a{
  font-size:14px;
  color:#666;
  padding-right:15px;
}
.weather_list{
  height:200px;
  text-align:center;
  margin-top:50px;
  font-size:0px;
}
.weather_list li{
  display:inline-block;
  width:140px;
  height:200px;
  padding:0 10px;
  overflow: hidden;
  position: relative;
  background-size: 1px 130px;
}

.weather_list li:last-child{
  background:none;
}
.info_date{
  width:100%;
  height:40px;
  line-height:40px;
  color: #2d6e99;
  font-size:14px;
  left:0px;
  bottom:0px;
  margin-top: 15px;
}
.info_date b{
  float: left;
  margin-left:15px;
}
.info_type span{
  color: #073689;
  font-size:30px;
  line-height:80px;
}
.info_temp{
  font-size:14px;
  color:#073689;
}
.info_temp b{
  font-size:13px;
}
</style>