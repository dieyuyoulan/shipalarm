<template>
  <div>
    <el-scrollbar height="calc(100vh - 50px)">
    <el-menu
        :uniqueOpened="true"
        style="width: 200px; min-height: calc(100vh - 50px)"
        default-active="path"
        router
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b">
      <el-menu-item index="/home">
        <i class="el-icon-s-home"></i>
        <template #title >首页</template>
      </el-menu-item>
      <el-menu-item index="/controllor">
        <i class="el-icon-printer"></i>
        <template #title >控制台接收</template>
      </el-menu-item>
      <el-menu-item index="/alarm">
        <i class="el-icon-location"></i>
        <template #title >报警页</template>
      </el-menu-item>
      <el-submenu index="1">
        <template #title>
          <i class="el-icon-notebook-1"></i>
          <span>管理</span>
        </template>
        <el-menu-item index="/accidence">
          事件管理
        </el-menu-item>
        <el-menu-item index="/user">
          人事管理
        </el-menu-item>
        <el-menu-item index="/ship">
          船舶管理
        </el-menu-item>
      </el-submenu>
      <el-submenu index="2">
        <template #title>
          <i class="el-icon-notebook-1"></i>
          <span>更多页面</span>
        </template>
        <el-menu-item index="/ai">
          车辆行人识别
        </el-menu-item>
        <el-menu-item index="/weather">
          天气预报
        </el-menu-item>
        <el-menu-item index="/calender">
          日历
        </el-menu-item>
        <el-menu-item index="/login">
          登录注册
        </el-menu-item>
      </el-submenu>
    </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
export default {
  name: "Aside",
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>

</style>