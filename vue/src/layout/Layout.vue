<template>
  <!--    头部-->
  <el-container>
    <el-header style="font-size: 12px; background-color: #545c64; border-bottom: 1px solid #ccc; "><Header /></el-header>
    <!--    主体-->
    <el-container>
      <!--      侧边栏-->
      <el-aside width="200px"><Aside /></el-aside>
      <!--      内容区域-->
      <el-main>
        <breadcrumb />
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";
import Breadcrumb from "../components/breadcrumb";

export default {
  name: "Layout",
  components: {
    Breadcrumb,
    Header,
    Aside
  }
}

</script>

<style>

</style>
