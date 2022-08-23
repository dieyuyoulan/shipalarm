import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: '首页',
    component: Layout,
    redirect: "/home",
    children: [
      {
        path: 'home',
        name: '首页',
        component: () => import("../views/Home"),
        meta: {title: "首页"}
      },
      {
        path: 'alarm',
        name: '报警页',
        component: () => import("../views/MapManager"),
        meta: {title: "报警页"}
      },
      {
        path: 'controllor',
        name: '控制台接收',
        component: () => import("../views/controllor"),
        meta: {title: "控制台接收"}
      },
      {
        path:"test",
        name: "测试",
        component: () => import("../views/test")
      },
      {
        path:"accidence",
        name: "事件管理",
        component: () => import("../views/AccidentManage")
      },
      {
        path:"user",
        name: "人事管理",
        component: () => import("../views/UserManage")
      },
      {
        path:"ship",
        name: "船舶管理",
        component: () => import("../views/ShipManage")
      },
      {
        path:"ai",
        name: "车辆行人识别",
        component: () => import("../views/Content")
      },
      {
        path:"weather",
        name: "天气预报",
        component: () => import("../views/weather")
      },
      {
        path:"calender",
        name: "日历",
        component: () => import("../views/calender")
      },
     ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import("../views/Login")
  },
  // {
  //   path: '/echart',
  //   name: 'Echart',
  //   component: () => import("../components/Echart")
  // },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
