<template>
  <div id="login">

      <div class="header">
        <!--<div class="logo">-->
        <!--<img src="../../assets/logo.png">-->
        <!--</div>-->
      </div>
      <div class="loginBox">
        <div class="loginCon">
          <p class="title">内河船舶远程</p>
          <p class="title">安全报警系统</p>
          <el-card shadow="always" class="login-module" v-if="smdl">
            <template #header>
              <div class="card-header">
                <span>密码登录</span>
              </div>
            </template>
            <div>
              <el-form ref="form" :model="form" :rules="rules">
                <el-form-item prop="username">
                  <el-input prefix-icon="el-icon-user-solid"  v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                  <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button style="width: 48%;" type="primary" @click="login">管理员登录</el-button>
                  <el-button style="width: 48%;" type="primary" @click="login">船员登录</el-button>
                </el-form-item>
                <el-form-item>
                  <el-button style="width: 100%;" type="primary" @click="smdl = !smdl">注 册</el-button>
                </el-form-item>
              </el-form>
            </div>

          </el-card>
          <el-card shadow="always" class="login-module3" v-else="smdl">
            <template #header>
              <div class="card-header">
                <span>密码登录</span>
              </div>
            </template>
            <div>
              <el-form ref="formRes" :model="formRes" :rules="rulesRes">
                <el-form-item prop="id">
                  职工号：<el-input prefix-icon="el-icon-user-solid"  v-model="formRes.id" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item prop="username">
                  用户名：<el-input prefix-icon="el-icon-user-solid"  v-model="formRes.username" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                  密码：<el-input prefix-icon="el-icon-lock" v-model="formRes.password" show-password style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item prop="company">
                  公司：<el-input prefix-icon="el-icon-lock" v-model="formRes.company" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item prop="section">
                  部门：<el-input prefix-icon="el-icon-lock" v-model="formRes.section" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item prop="position">
                  职位：<el-input prefix-icon="el-icon-lock" v-model="formRes.position" style="width: 80%"></el-input>
                </el-form-item>


                <el-form-item>
                  <el-button style="width: 100%;" type="primary" @click="res">注 册</el-button>
                </el-form-item>
                <el-form-item>
                  <el-button style="width: 100%;" type="primary" @click="smdl = !smdl">返回登录</el-button>
                </el-form-item>
              </el-form>
            </div>

          </el-card>


        </div>
      </div>

  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: "Login",
  data() {
    return {
      smdl: true,
      form: {role: 1},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      },
      formRes: {},
      rulesRes: {
        id: [
          {required: true, message: '请输入职工号', trigger: 'blur'},
        ],
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        position: [
          {required: true, message: '请输入职位', trigger: 'blur'}
        ],
        section: [
          {required: true, message: '请输入部门', trigger: 'blur'}
        ],
        company: [
          {required: true, message: '请输入公司', trigger: 'blur'}
        ],
      }
      // 加背景图片
      // bg: {
      //   backgroundImage: "url(" + require("@/assets/bg.jpg") + ")",
      //   backgroundRepeat: "no-repeat",
      //   backgroundSize: "100% 100%"
      // }
    }
  },
  props:{
    autoComplete: {
      type: String,
      validator(val) {
        process.env.NODE_ENV !== 'production' &&
        console.warn('[Element Warn][Input]\'auto-complete\' property will be deprecated in next major version. please use \'autocomplete\' instead.');
        return true;
      }
    },
  },
  created() {
    sessionStorage.removeItem("user")
  },
  methods: {
    login() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post("/user/login", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "登录成功"
              })
              sessionStorage.setItem("user", JSON.stringify(res.data))  // 缓存用户信息
              this.$router.push("/")  //登录成功之后进行页面的跳转，跳转到主页
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })
    },

    res() {
      this.$refs['formRes'].validate((valid) => {
        if (valid) {
          request.post("/user/res", this.formRes).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "注册成功"
              })
              sessionStorage.setItem("user", JSON.stringify(res.data))  // 缓存用户信息
              this.smdl = !this.smdl  //登录成功之后进行页面的跳转，跳转到主页
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.demo-ruleForm {
  width: 460px;
  padding-top: 25px;
}
#login {
  width: 100%;
  height: 100%;
  background-color: #2d3a4b;
  .loginConbox{
    background: #2d3a4b;
  }
  .header {
    height: 50px;
    position: relative;
    background: #2d3a4b;
/*border-bottom: 1px solid rgba(255, 255, 255, 0.3);*/
    .logo{
      margin-left: 30px;
      width: 500px;
      float: left;
      height: 40px;
      padding-top: 10px;
      img{
        height: 100%;
      }
    }
  }
  .loginBox {
    .iconcolor {
      color: #436e9a;
    }
    padding: 74px 0 118px;
    .loginCon {
      width: 990px;
      margin: auto;
      position: relative;
      height: 560px;

      .el-card__header {
        border-bottom: 0px;
      }
      .title{
        font-size: 50px;
        font-weight: 600;
        color: #ffffff;
        width: 600px;
        float: left;
        margin-top: 0px;
        &:first-child{
          font-size: 50px;
          margin-top: 100px;
          margin-bottom: 30px;
        }
      }
      .login-module {
        width: 380px;
        height: 340px;
        margin-top: 60px;
        border: none;
        position: absolute;
        right: 0;

        .formTitlt {
          font-size: 18px;
          font-weight: 400;

          .titIconbox {
            float: right;

            .pointer {
              cursor: pointer;
            }
          }
        }

        .smalltxt {
          text-align: right;

          .a {
            text-decoration: none;
            color: #999999;
            font-size: 12px;
            margin-left: 8px;
          }
        }
      }

      .login-module3 {
        width: 380px;
        height: 470px;
        margin-top: 60px;
        border: none;
        position: absolute;
        right: 0;

        .formTitlt {
          font-size: 18px;
          font-weight: 400;

          .titIconbox {
            float: right;

            .pointer {
              cursor: pointer;
            }
          }
        }

        .smalltxt {
          text-align: right;

          .a {
            text-decoration: none;
            color: #999999;
            font-size: 12px;
            margin-left: 8px;
          }
        }
      }

      .el-form-item__content {
        margin-left: 0px !important;

        .subBtn {
          width: 100%;
        }
      }
    }

    .el-input__inner, .el-button, .el-card, .el-message {
      border-radius: 0px !important;
    }

    .el-form-item__content .ico {
      position: absolute;
      top: 0px;
      left: 0px;
      z-index: 999;
      width: 40px;
      height: 39px;
      text-align: center;
      border-right: 1px solid #ccc;
    }

    .ewmbox {
      width: 100%;
      height: 240px;
      margin-top: -25px;

      .ewm {
        width: 140px;
        height: 140px;
        margin: 20px auto;

        p {
          font-size: 12px;
          padding-left: 40px;
          margin: 0;
        }
      }

      .ewmicon {
        width: 140px;
        margin: 20px auto 0;

        .iconfont {
          float: left;
        }

        p {
          font-size: 12px;
          padding-left: 40px;
          margin: 0;
        }
      }
    }
  }
}
</style>
