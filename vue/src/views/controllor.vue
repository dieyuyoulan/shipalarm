<template>
  <div class="schedued" style="padding: 10px">
    <el-card class="box-card" style="width: 1160px">
      <div v-for="o in 1" :key="o" class="text item">
        <div style="margin: 10px 0; right: 10px">
          <el-input v-model="search" placeholder="请输入事件名称" style="width: 20%" clearable></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="load" icon="el-icon-search">查询</el-button>
          <el-input v-model="message" placeholder="请输入事件名称" style="width: 20%" clearable></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="mqttPublish" icon="el-icon-search">发送</el-button>
        </div>
      </div>
    </el-card>
    <el-table
        ref="filterTable"
        v-loading="loading"
        :data="tableData"
        border
        stripe
        style="width: 1200px"
        :row-class-name="tableRowClassName"
        :default-sort = "{prop: 'date', order: 'descending'}">
      <el-table-column
          fixed
          prop="id"
          label="ID"
          sortable
          >
      </el-table-column>
      <el-table-column
          prop="time"
          label="时间">
      </el-table-column>
      <el-table-column
          prop="topic"
          label="事件">
      </el-table-column>
      <el-table-column
          prop="x"
          label="经度">
      </el-table-column>
      <el-table-column
          prop="y"
          label="纬度">
      </el-table-column>

      <el-table-column label="操作" fixed="right" width="200">
        <template #default="scope">
          <el-button-group>
            <el-button size="mini" type="primary" @click="handleDelete(scope.row.id)">删除</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0" class="block">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog v-model="dialogalert" title="alarm" width="30%" center>
    <span
    >{{alertMessage}}</span
    >
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogalert = false">Cancel</el-button>
        <el-button type="primary" @click="dialogalert = false"
        >Confirm</el-button
        >
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script lang="ts">

import request from "../utils/request";
import { Edit } from '@element-plus/icons'
import mqtt from 'mqtt'





export default {
  name: "Schedued",
  data(){
    return {
      dialogVisible_third : false,
      editForm: {},
      InnerdialogVisible: false,
      Inform: {},
      bind: true,
      dialogVisible: false,
      form: {
        x: "",
        y: "",
        topic: "",
        time: "",
      },
      loading: true,
      currentPage:1,
      search: '',
      pageSize: 10,
      total: 0,
      index: -1,
      depss: [],
      message: "",
      client: "",
    }

  },

  components: {
    // 全名
    [Edit.name]: Edit,
    // 或者以缩写的方式,
    Edit,
  },

  created() {
    this.load()

  },

  mounted() {
    this.initMqtt()
    this.mqttReceive()
  },

  methods: {

    getEditionName(){
      request.post("/editionName", this.form).then(res=>{
        this.depss = res.data;

      });

    },

    // tableRowClassName({row}) {
    //   if(!row.isDelay.compareTo("正常")){
    //     return 'warning-row'
    //   }
    // },

    load() {
      this.loading = true
      request.get("/task/display", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
        }
      }).then(res => {
        console.log(res);
        this.loading = false;
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
    },

    filterTag(value, row) {
      return row.isDelay === value;
    },

    handleDelete(id) {
      console.log(id)
      request.delete("/task/deleteTask/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()  // 删除之后重新加载表格的数据
      })
    },

    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.load();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.load();
    },

    filterHandler(value, row, column) {
      const property = column['property']
      return row[property] === value
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

    mqttReceive() {
      let topic = "public/TEST/accident"; //要接收的主题
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
        var myDate = new Date();
        console.log(topic);
        var json=JSON.parse(message);

        var alertMessage = "报警信息："+"x轴: "+json.x+", y轴: "+json.y+", 事件:"+json.topic+", 时间"+myDate.toLocaleString();
        alert(alertMessage)
        var json2 = "{ \"topic\":" + json.topic + ",\"x\":"+json.x+",\"y\":"+json.y+", \"time\": " + myDate.toDateString()+"}";
        console.log(json2);

        this.loading = true;
        request.get("/task/save", {
          params: {
            x: json.x,
            y: json.y,
            topic: json.topic,
            time: myDate.toLocaleString(),
          }
        }).then(res => {
          console.log(res);
          this.load();
        })

      });
    },
    mqttPublish() {
      let topic = "public/TEST/command"; //和后台约定好的主题
      console.log(topic)
      var message = JSON.stringify(this.message)
      this.client.publish(topic, message);
      alert("发送成功");
    },
  }
}


</script>

<style>
  .el-table .warning-row {
    background: oldlace;
  }

  .box-card {
    width: 1200px;
  }

</style>