<template>
  <div id = "login" >
    <span class = "title" >商品管理用户登录界面</span>
    <div id = "main" class = "login-container" >
      <el-form label-width = "80px">
          <el-form-item label = "用户名">
              <el-input v-model = "loginForm.username" placeholder = "请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="密码">
              <el-input type = "password" v-model = "loginForm.password" placeholder = "请输入密码"></el-input>
          </el-form-item>
          <el-form-item>
              <el-button type = "primary" @click = "login">登录</el-button>
          </el-form-item>
      </el-form>
    </div>
    <img src="../assets/preview.jpg" alt="" class="background-image">
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
    }
  },
  methods: {
    login() {
      axios.post("/api/login", this.loginForm)
        .then(res => {
          if (res.data.code === 0) {
            this.$router.push('/home');
            this.$message.success("登录成功~ 欢迎你," + this.loginForm.username)
          } else {
            this.$message.error("账号或密码错误")
          }
        })
    }
  }
}
</script>

<style>
  .login-container {
    width: 400px;
    margin: 0 auto;
    padding-top: 20px;
  }
  .title {
      display: block;
      margin: 0 auto;
      font-size: 40px;
      text-align: center;
  }
  .background-image {
    position: absolute; /* 设置绝对定位 */
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -1; /* 将图片放到最底层，不会覆盖原来的内容 */
    opacity: 0; /* 可以根据需要调整透明度 */
  } 
</style>
