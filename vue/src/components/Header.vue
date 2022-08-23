<template>
  <el-row :gutter="20">
    <el-col :span="6">
    <div style="width: 200px; padding-left: 30px; font-weight: bold; color: #fff; font-size: 20px; margin-top: 15px; margin-bottom: 10px">后台管理</div>
    </el-col>
    <el-col :span="12"><div style="text-align: right; margin-top: 0px"></div></el-col>
    <el-col :span="6">
    <div style="text-align: right; margin-top: 20px">
      <el-dropdown trigger="click">
        <span class="el-dropdown-link" style="font-size: 18px; font-weight: bold; color: #00040a">
          <i class="el-icon-arrow-down el-icon--right" style="color: #cccccc">{{username}}</i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
            <el-dropdown-item @click="dialogVisible = true">上传头像</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    </el-col>
  </el-row>

  <el-dialog
      v-model="dialogVisible"
      title="Tips"
      width="30%"
      :before-close="handleClose"
  >
    <el-upload
        class="avatar-uploader"
        action="https://jsonplaceholder.typicode.com/posts/"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
    >
      <el-image v-if="imageUrl" :src="imageUrl" class="avatar"></el-image>
      <el-icon v-else class="avatar-uploader-icon"><plus /></el-icon>
    </el-upload>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogVisible = false"
        >确定</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>



<script>
import request from "../utils/request";
import { Plus } from '@element-plus/icons'

export default {
  name: "Header",
  components: {
    Plus,
  },
  data() {
    return {
      isCollapse: true,
      username: "",
      dialogVisible: false,
      imageUrl: '',
    };
  },
  created() {
    this.getUser()
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    getUser(){
      request.get("/user/user", {

      }).then(res => {
        console.log(res)
        this.username = res.data
      })
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('Avatar picture must be JPG format!')
      }
      if (!isLt2M) {
        this.$message.error('Avatar picture size can not exceed 2MB!')
      }
      return isJPG && isLt2M
    },
  },

}
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
.avatar-uploader-icon svg {
  margin-top: 74px; /* (178px - 28px) / 2 - 1px */
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>