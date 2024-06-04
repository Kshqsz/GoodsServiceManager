<template>
  <div id = "home">
    <el-container class="layout-container">
        <el-aside width="200px">
            <div class="el-aside__logo"></div>
            <el-menu active-text-color="#ffd04b" router>
                <el-menu-item index="/home">
                    <i class="el-icon-shopping-cart-2"></i>
                    <span>商品管理</span>
                </el-menu-item>
                <el-menu-item index="/category">
                    <i class="el-icon-folder-opened"></i>
                    <span>分类管理</span>
                </el-menu-item>
                <el-menu-item index="/user">
                    <i class="el-icon-user"></i>
                    <span>用户管理</span>
                </el-menu-item>
            </el-menu>
        </el-aside>
        <el-container>
            <el-header>
                <div>欢迎你~ <strong>{{ usernmae }}</strong></div>
            </el-header>  
            <el-main>
                <router-view></router-view>
            </el-main>

        </el-container>
    
    </el-container>

  </div>
</template>

<script>
import store from '@/store'
export default {

  data() {
    return {
      usernmae: store.state.username,  
    }
  }
}
</script>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;
    .el-aside {
        &__logo {
            height: 120px;
            background: url('@/assets/logo.jpg') no-repeat center / 120px auto;
        }
        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}
</style>
