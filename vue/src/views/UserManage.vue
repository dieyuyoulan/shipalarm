<template>
  <div style="padding: 10px">
  <el-card class="box-card" style="width: 1160px">
    <div v-for="o in 1" :key="o" class="text item">
      <div style="margin: 10px 0; right: 10px">
        <el-input v-model="company" placeholder="请输入公司名称" style="width: 20%" clearable></el-input>
        <el-input v-model="section" placeholder="请输入部门名称" style="width: 20%" clearable></el-input>
        <el-input v-model="position" placeholder="请输入人员名称" style="width: 20%" clearable></el-input>
        <el-input v-model="username" placeholder="请输入人员名称" style="width: 20%" clearable></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="load" icon="el-icon-search">查询</el-button>
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
          prop="username"
          label="用户名">
      </el-table-column>
      <el-table-column
          prop="company"
          label="公司">
      </el-table-column>
      <el-table-column
          prop="section"
          label="部门">
      </el-table-column>
      <el-table-column
          prop="position"
          label="职务">
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

export default {
  name: "userManage",
  data(){
    return {
      loading: true,
      currentPage:1,
      pageSize: 10,
      total: 0,
      position: '',
      company: '',
      username: '',
      section: '',
    }

  },
  created() {
    this.load()

  },

  methods: {
    load() {
      this.loading = true
      request.get("/user/display", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          company: this.company,
          section: this.section,
          position: this.position,
          username: this.username
        }
      }).then(res => {
        console.log(res);
        this.loading = false;
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
    },

    handleSizeChange(val){
      this.pageSize = val;
      this.load();
    },

    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.load();
    },
  }
}
</script>

<style scoped>

</style>