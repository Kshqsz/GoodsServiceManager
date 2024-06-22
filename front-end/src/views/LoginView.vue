<template>
  <div id = "login" >
    <el-card class="box-card">
      <span class = "title" v-if="isRegister">商品管理用户注册界面</span>
      <span class = "title" v-else>商品管理用户登录界面</span>
      <br>
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
          <br>
          <el-form-item>
                <el-row :span="24">
                  <el-col :span="8">
                    <el-input placeholder="请输入验证码" v-model="inputCode" ></el-input>
                  </el-col>
                  <el-col :span="8">
                    <div @click="refreshCode"> <s-identify :identifyCode="identifyCode"></s-identify> </div>
                  </el-col>
                  <el-col :span="8">
                    <el-link @click="refreshCode"><span style="color: blue;">换一张?</span></el-link>
                  </el-col>
                </el-row>
          </el-form-item>
          <el-form-item>
            <el-link type="info" @click="isRegister = false" style = "margin-right: 20px;">
              ← 返回
            </el-link>
            <el-button type="primary" @click="register()" style="width: 300px">
                  注册
            </el-button>
          </el-form-item>
        </el-form>

        
        <!-- 登录表单 -->
        <el-form v-show="isRegister == false" label-width = "80px" :rules="rules" :model="loginForm" ref="loginForm">
            <el-form-item label = "用户名" prop="username">
                <el-input prefix-icon="el-icon-user" v-model = "loginForm.username" placeholder = "请输入用户名" @keyup.enter.native="login"></el-input>
            </el-form-item>
            <el-form-item label = "密码" prop="password">
                <el-input prefix-icon="el-icon-lock" type = "password" v-model = "loginForm.password" placeholder = "请输入密码" @keyup.enter.native="login"></el-input>
            </el-form-item>
            <el-form-item>
                <br>
                <el-button type = "primary" @click = "login" style="width: 300px;">登录</el-button>
                <el-link type="info" @click="isRegister = true" style = "margin-left: 250px;">
                  注册 →
                </el-link>
            </el-form-item>
        </el-form>
      </div>
    </el-card>
    <div class = "copyright">
      <span>© Copyright 2024 Created By Kshqsz </span>
    </div>
    <img src="../assets/2024-6-19.jpg" alt="" class="background-image">
  </div>
</template>

<script>
import axios from '@/utils/axios';
import SIdentify from "@/components/SIdentify"
export default {
  components: { SIdentify },
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
      identifyCodes: '1234567890abcdefjhijklinopqrsduvwxyz',//随机串内容
      identifyCode: '',
      inputCode:'',
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
  mounted() {
    this.identifyCode = ''
    this.makeCode(this.identifyCodes, 4)
  },
  methods: {
    login() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          axios.post("/login", this.loginForm).then(res => {
            if (res.data.code === 0) {
              this.$store.commit('setToken', res.data.data);
              this.$store.commit('setUsername', this.loginForm.username);
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
      if (this.inputCode.toLowerCase() !== this.identifyCode.toLowerCase()) {
          this.$message.error("验证码错误");
          return;
      }
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          axios.post('/register', this.registerForm).then((res) => {
            if (res.data.code === 0) {
              this.$message.success("注册成功~");
              this.isRegister = false;
              this.loginForm.username = this.registerForm.username;
              this.loginForm.password = '';
            } else {
              this.$message.error(res.data.message);
            }
          }).catch(error => {
            this.$message.error(error);
          })
        } else {
          this.$message.error("请正确填写注册信息");
        }
      })
    },
    refreshCode () {
      this.identifyCode = ''
      this.makeCode(this.identifyCodes, 4)
    },
    makeCode (o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[this.randomNum(0, this.identifyCodes.length)]
      }
    },
    randomNum (min, max) {
      return Math.floor(Math.random() * (max - min) + min)
    }
  }
}
</script>

<style>
  .copyright {
    position: absolute;
    width: 300px;
    height: 27px;
    margin-left: calc(50% - 150px);
    bottom: 24px;
  }
  .box-card {
    height: 600px;
    width: 500px;
    margin-top: 150px;
    margin-left: calc(80% - 250px);
    padding-top: 20px;
    opacity: 1;
  }
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
    height: 92%;
    z-index: -1; /* 将图片放到最底层，不会覆盖原来的内容 */
    opacity: 1; /* 可以根据需要调整透明度 */
  } 
</style>
