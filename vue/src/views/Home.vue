<template>
  <el-row>
    <el-col :span="8">
      <el-card class="box-card" style="height: 300px; width: 360px;">
        <el-row>
        <div slot="header" class="clearfix">
          <i class="el-icon-menu" style="color: #001cfe"></i>
          <span style="color:#001cfe;">  快捷方式</span>
        </div>
        </el-row>
        <el-row><div style="height: 60px"></div><el-divider></el-divider></el-row>
        <el-row>
          <el-col :span="12">
        <div class="text item">
          <i class="el-icon-box" style="color: #858bbb"></i>
          <el-button type="text" style="font-size: 16px; color: #858bbb" @click="pushOne"> 控制台</el-button>
        </div>
          </el-col>
          <el-col :span="12">
            <div class="text item">
              <i class="el-icon-notebook-1" style="color: #82b673"></i>
              <el-button type="text" style="font-size: 16px; color: #82b673" @click="alarm"> 报警页</el-button>
            </div>
          </el-col>
        </el-row>
        <el-row><div style="height: 60px"></div></el-row>
        <el-row>
          <el-col :span="12">
            <div class="text item">
              <i class="el-icon-shopping-bag-1" style="color: #ec170f"></i>
              <el-button type="text" style="font-size: 16px; color: #ec170f" @click="accident"> 事件管理</el-button>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="text item">
              <i class="el-icon-reading" style="color: #925fa8"></i>
              <el-button type="text" style="font-size: 16px; color: #925fa8" @click="resTable"> 人员管理</el-button>
            </div>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
    <el-col :span="8">
      <el-card class="box-card" style="height: 300px; width: 360px">
        <div class="text item">
          <div id="myChart" :style="{width: '360px', height: '300px'}"></div>
        </div>
      </el-card>
    </el-col>
    <el-col :span="8">
      <el-card class="box-card" style="height: 300px; width: 390px">
        <div class="card bbxx">
          <p class="title"><i class="fa fa-server"></i>版本信息</p>
        <div class="table">
          <p><span class="tit">当前版本</span>v1.0.0</p>
          <p><span class="tit">基于框架</span>vue + spring-boot</p>
          <p><span class="tit">系统名称</span>内河船舶安全预警系统</p>
          <p><span class="tit">系统功能</span>安全预警/船舶管理/消息管理</p>
        </div>
        </div>
    </el-card></el-col>
  </el-row>
  <el-row>
    <div style="height: 20px"></div>
  </el-row>
  <el-card class="box-card" style="height: 340px; width: 1177px">
 <div id="LineChart" :style="{width: '1200px', height: '350px'}"></div>
  </el-card>
</template>

<style lang="scss">
$top:top;
$bottom:bottom;
$left:left;
$right:right;
$leftright: ($left, $right);
$pinkk: #eec2d3;
$bluee: #409eff;
$yelloww: #f4d884;
$grennn: #89dda0;
$purplee: #78a2ea;
$lightBluee: #b8d6ff;

$list: bluee pinkk yelloww grennn purplee lightBluee;
$list1: $bluee $pinkk $yelloww $grennn $purplee $lightBluee;
%shadow{
  background: #fff;
  -webkit-box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.2);
  box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.2);
  border-color: rgba(0, 0, 0, 0.2);
  .title{
    font-size: 14px;
    padding: 10px;
    i{
      margin-#{$right}: 5px;
    }
  }
}

@mixin flex($direction:row,$content:space-between){
  display: flex;
  flex-direction: $direction;
  justify-content: $content;
}
.card {
  color: #666;

  .table {
    padding: 20px;
    p {
      height: 52px;
      line-height: 52px;
      border: 1px solid #cccccc;
      overflow: hidden;
      border-#{$top}: none;
      @include flex(null, start);

      &:first-child {
        border-#{$top}: 1px solid #cccccc;
      }

      span {
      }

      .tit {
        width: 80px;
        min-width: 80px;
        height: 100%;
        text-align: center;
        border-#{$right}: 1px solid #cccccc;
        margin-#{$right}: 18px;
      }

      span.gitbox {
        flex: 1;
        height: 100%;
        overflow: hidden;
        @include flex(row, start);

        a {
          &:first-child {
            margin-#{$right}: 30px;
          }
        }
      }
    }
  }
}


</style>

<script>

import * as echarts from 'echarts';
import request from "../utils/request";

export default {
  name: 'Home',
  components: {

  },
  data() {
      return {
        loading: true,
        search: '',
        tableData: [],
        form: {},
        dialogVisible: false,
        rules: {
          editionDate: [
            { required: true, message: '请输入发版时间', trigger: 'blur' }
          ],
          editionName: [
            { required: true, message: '请输入版本名称', trigger: 'blur' }
          ],
          interTest: [
            { required: true, message: '请输入内部测试时间', trigger: 'blur' }
          ],
          subTest: [
            { required: true, message: '请输入提测时间', trigger: 'blur' }
          ],
          editionManager: [
            { required: true, message: '请输入版本管理员', trigger: 'blur' }
          ]
        },
      }

  },

  created() {

  },

  methods: {


    pushOne: function (){
      this.$router.push("/controllor")
    },

    alarm(){
      this.$router.push("/alarm")
    },

    accident(){
      this.$router.push("/accidence")
    }
  },

  mounted() {
    let _this = this
    request.get('/ship/bear').then(function (response) {
      let echarts = require('echarts');
      let getChart = echarts.init(document.getElementById('myChart'));
      var option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '访问来源',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '40',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: response.bearVOList
          }
        ]
      };
      option && getChart.setOption(option);
    });
    request.get('/task/lineVO').then(function (response) {
      let echarts = require('echarts');
      let getChart = echarts.init(document.getElementById('LineChart'));
      var option = {
        title: {
          text: '每日新增',
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['新增报警信息', '新增派遣船舶']
        },
        grid: {
          left: '0%',
          right: '0%',
          bottom: '15%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: response.dates
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '新增报警信息',
            type: 'line',
            data: response.alarmCount,
          },
          {
            name: '新增派遣船舶',
            type: 'line',
            data: response.startCount,
          },
        ],
      }
      option && getChart.setOption(option);
    })
  }
}
</script>
