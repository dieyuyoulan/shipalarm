<template>
  <div class="schedued" style="padding: 10px">
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
          prop="accidence"
          label="事件">
      </el-table-column>

      <el-table-column label="操作" fixed="right" width="200">
        <template #default="scope">
          <el-button-group>
            <el-button size="mini" type="primary" @click="handleDelete(scope.row.id)">删除</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
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


  </div>
</template>

<script>
import request from "../utils/request";
import { Edit } from '@element-plus/icons'
import mqtt from 'mqtt'
export default {
  name: "AccidentManage",
  data(){
    return {
      loading: true,
      currentPage:1,
      search: '',
      pageSize: 10,
      total: 0,
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

  },

  methods: {

    // tableRowClassName({row}) {
    //   if(!row.isDelay.compareTo("正常")){
    //     return 'warning-row'
    //   }
    // },

    load() {
      this.loading = true
      request.get("/accident/display", {
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

    handleDelete(id) {
      console.log(id)
      request.delete("/accident/delete/" + id).then(res => {
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

    handleSizeChange(val){
      this.pageSize = val;
      this.load();
    },
    handleCurrentChange(val){
      this.currentPage = val;
      this.load();
    },
  }
}
</script>

<style scoped>
.el-table .warning-row {
  background: oldlace;
}

.box-card {
  width: 1200px;
}

</style>