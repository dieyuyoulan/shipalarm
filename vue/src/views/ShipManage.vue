<template>
  <div class="home" style="padding: 10px">
    <el-card class="box-card" style="width: 1160px">
      <div v-for="o in 1" :key="o" class="text item">
        <div style="margin: 10px 0; right: 10px">
          <el-button type="primary" @click="add" style="margin-right: 5px">新增</el-button>
          <el-input v-model="search" placeholder="请输入船舶名称" style="width: 20%" clearable></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="load" icon="el-icon-search">查询</el-button>
        </div>
      </div>
    </el-card>

    <el-table
        v-loading="loading"
        :data="tableData"
        border
        stripe
        :default-sort = "{prop: 'date'}">
      <el-table-column type="expand">
        <template #default="props">
          <p>起点: {{ props.row.startOn }}</p>
          <p>终点: {{ props.row.endOn }}</p>
          <p>起航时间: {{ props.row.startDate }}</p>
          <p>预期到达时间: {{ props.row.foresDate }}</p>
        </template>
      </el-table-column>
      <el-table-column
          prop="id"
          label="ID">
      </el-table-column>
      <el-table-column
          prop="shipType"
          label="船舶类型"
          sortable>
      </el-table-column>
      <el-table-column
          prop="shipName"
          label="船舶名称">
      </el-table-column>
      <el-table-column
          prop="shipCompany"
          label="类属公司">
      </el-table-column>
      <el-table-column
          prop="captain"
          label="船长">
      </el-table-column>
      <el-table-column
          prop="isWork"
          label="是否在途"
          :filters="[
              {text: '是', value: '是'},
              {text: '否', value: '否'}
          ]"
          :filter-method="filterTag"
          filter-placement="bottom-end">
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="200">
        <template #default="scope">
          <el-button-group>
            <el-button type="primary" @click="handleDelete(scope.row.id)" icon="el-icon-delete">删除</el-button>
            <el-button type="primary" @click="shech(scope.row.id)" icon="el-icon-date">派遣</el-button>
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

    <div class="block">
      <el-dialog
          title="提示"
          v-model="dialogVisible"
          width="30%">
        <el-form :model="form" :rules="rules" label-width="120px">
          <el-form-item label="船舶类型" prop="shipType">
            <el-input v-model="form.shipType" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="船舶名称" prop="shipName">
              <el-input v-model="form.shipName" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="类属公司" prop="shipCompany">
            <el-input v-model="form.shipCompany" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="船长" prop="captain">
            <el-select v-model="form.captain" placeholder="请选择船长（负责人）">
              <el-option v-for="item in depss" :key="item.id" :label="item.username" :value="item.username"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
    <span class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </span>
        </template>
      </el-dialog>
      <el-dialog
          title="提示"
          v-model="dialogVisibleTwo"
          width="30%">
        <el-form :model="formTwo" :rules="rulesTwo" label-width="120px">
          <el-form-item label="起点" prop="startOn">
            <el-input v-model="formTwo.startOn" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="终点" prop="EndOn">
            <el-input v-model="formTwo.endOn" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="起航时间" prop="startDate">
            <el-date-picker
                v-model="formTwo.startDate"
                type="date"
                placeholder="选择日期"
                format="YYYY 年 MM 月 DD 日"
                value-format="YYYY/MM/DD">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="预计到达时间" prop="foresDate">
            <el-date-picker
                v-model="formTwo.foresDate"
                type="date"
                placeholder="选择日期"
                format="YYYY 年 MM 月 DD 日"
                value-format="YYYY/MM/DD">
            </el-date-picker>
          </el-form-item>
        </el-form>

        <template #footer>
    <span class="dialog-footer">
      <el-button @click="dialogVisibleTwo = false">取 消</el-button>
      <el-button type="primary" @click="update">确 定</el-button>
    </span>
        </template>
      </el-dialog>
    </div>


  </div>
</template>

<script>
import request from "../utils/request";
export default {
  name: "CarManage",

  data() {
      return {
        currentPage:1,
        pageSize: 10,
        total: 0,
        loading: true,
        search: '',
        tableData: [],
        form: {},
        dialogVisible: false,
        dialogVisibleTwo: false,
        formTwo: {},
        depss: [],
        rules: {
          shipType: [
            { required: true, message: '请输入船舶类型', trigger: 'blur' }
          ],
          shipCompany: [
            { required: true, message: '请输入船舶所属公司', trigger: 'blur' }
          ],
          shipName: [
            { required: true, message: '请输入船舶名称', trigger: 'blur' }
          ],
          captain: [
            { required: true, message: '请输入船长（负责人）姓名', trigger: 'blur' }
          ]
        },
        rulesTwo: {
          startOn: [
            { required: true, message: '请输入开船地点', trigger: 'blur' }
          ],
          endOn: [
            { required: true, message: '请输入目的地', trigger: 'blur' }
          ],
          startDate: [
            { required: true, message: '请输入启航时间', trigger: 'blur' }
          ],
          foresDate: [
            { required: true, message: '请输入预期到达时间', trigger: 'blur' }
          ]
        },
      }

    },

    created() {
      this.load();
      this.getCaptain();
    },

    methods: {
      load(){
        this.loading = true;
        request.get("/ship/display", {
          params: {
            search: this.search,
            pageNum: this.currentPage,
            pageSize: this.pageSize
          }
        }).then(res => {
          console.log(res)
          this.loading = false;
          this.tableData = res.data.records;
          this.total = res.data.total;
        })
      },

      add(){
        this.dialogVisible = true;
        this.form = {};
      },

      save(){
        request.post("/ship/save", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load();//刷新表格数据
          this.dialogVisible = false;
        })
      },

      handleDelete(id) {
        console.log(id);
        request.delete("/ship/delete/" + id).then(res => {
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
          this.load();  // 删除之后重新加载表格的数据
        })
      },
      filterTag(value, row) {
        return row.tag === value
      },

      shech(id){
        this.formTwo.id = id;
        this.dialogVisibleTwo = true;
      },

      update(){
        request.post("/ship/date", this.formTwo).then(res => {
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "排期成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load();//刷新表格数据
          this.dialogVisibleTwo = false;
        })
      },
      getCaptain(){
        request.post("/ship/getCaptain", this.form).then(res=>{
          console.log(res);
          this.depss = res.data
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
    },



}
</script>

<style scoped>

</style>