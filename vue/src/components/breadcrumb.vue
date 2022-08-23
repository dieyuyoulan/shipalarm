<template>
  <el-row style="width: 100%;">
    <el-col :span="24" style=" border-bottom: 1px solid #ccc; height: 30px; line-height: 10px; width: 100%;">
    <el-breadcrumb separator=">">
      <el-breadcrumb-item
          v-for="(item,index) in breadList"
          :key="index"
          :to="{ path: item.path }"
      >{{item.name}}</el-breadcrumb-item>
    </el-breadcrumb>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "breadcrumb",
  data() {
    return {
      breadList: [] // 路由集合
    };
  },
  watch: {
    $route() {
      this.getBreadcrumb();
    }
  },
  methods: {
    isHome(route) {
      return route.name === "home";
    },
    getBreadcrumb() {
      let matched = this.$route.matched;
      //如果不是首页
      if (!this.isHome(matched[0])) {
        matched = [{ path: "/home", meta: { title: "首页" } }].concat(matched);
      }
      this.breadList = matched;
    }
  },
  created() {
    this.getBreadcrumb();
  }
};
</script>

<style scoped>

</style>