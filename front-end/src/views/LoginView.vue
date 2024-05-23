<template>
  <div id = "login" >
    <span class = "title" v-if="isRegister">商品管理用户注册界面</span>
    <span class = "title" v-else>商品管理用户登录界面</span>
    <div id = "main" class = "login-container" >
      <!-- 注册表单 -->
      <el-form v-show="isRegister" size="large" :model="registerForm" :rules="rules" ref="registerForm">
        <el-form-item label = "用户名" prop="username">
          <el-input prefix-icon="el-icon-user" v-model="registerForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label = "密码" prop="password">
          <el-input prefix-icon="el-icon-lock" type = "password" v-model="registerForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label = "确认密码" prop="rePassword">
          <el-input prefix-icon="el-icon-lock" type = "password" v-model="registerForm.rePassword" placeholder="请再次输入密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-link type="info" @click="isRegister = false" style = "margin-right: 150px;">
            ← 返回
          </el-link>
          <el-button type="primary" @click="register()" >
                注册
          </el-button>
        </el-form-item>
      </el-form>

      
      <!-- 登录表单 -->
      <el-form v-show="isRegister == false" label-width = "80px" :rules="rules" :model="loginForm" ref="loginForm">
          <el-form-item label = "用户名" prop="username">
              <el-input prefix-icon="el-icon-user" v-model = "loginForm.username" placeholder = "请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label = "密码" prop="password">
              <el-input prefix-icon="el-icon-lock" type = "password" v-model = "loginForm.password" placeholder = "请输入密码"></el-input>
          </el-form-item>
          <el-form-item>
              <el-button type = "primary" @click = "login">登录</el-button>
              <el-link type="info" @click="isRegister = true" style = "margin-left: 150px;">
                注册 →
              </el-link>
          </el-form-item>
      </el-form>
    </div>
    <img src="../assets/preview.jpg" alt="" class="background-image">
  </div>
</template>

<script>
import axios from '@/utils/axios';
export default {
  data() {
    var checkRePassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error("请再次输入密码"));
        } else if (value !== this.registerForm.password) {
          callback(new Error("请确保两次输入的密码一样"));
        } else {
          callback();
        }
    };
    return {
      isRegister: false,
      registerForm: {
        username: '',
        password: '',
        rePassword: ''
      },
      loginForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        rePassword: [
          {validator: checkRePassword, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    login() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          axios.post("/login", this.loginForm)
          .then(res => {
            if (res.data.code === 0) {
              this.$store.commit('setToken', res.data.data);
              this.$router.push('/home');
              this.$message.success("登录成功~ 欢迎你, " + this.loginForm.username)
            } else {
              this.$message.error("账号或密码错误")
            }
          }) 
        } else {
          this.$message.error('请填写完整的登录信息');
        }
      })
    },
    register() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          axios.post('/register', this.registerForm).then(() => {
            this.$message.success("注册成功~");
            this.isRegister = false;
            this.loginForm.username = this.registerForm.username;
            this.loginForm.password = '';
          }).catch(error => {
            this.$message.error(error);
          })
        } else {
          this.$message.error("请正确填写注册信息");
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
