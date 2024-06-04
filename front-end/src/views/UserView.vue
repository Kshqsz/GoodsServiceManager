<template>
    <div id = "user">
      <el-card class="page-container">
        <h1 style="text-align: center">用户管理</h1>
        <el-table :data="userList" stripe>
            <el-table-column prop="id" label="id">
            <template slot-scope="scope">
                <span>{{ scope.$index + 1 }}</span>
            </template>
        </el-table-column>
        
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column label="权限">
            <template slot-scope="scope">
                <span>{{ scope.row.username == 'admin' ? '超级管理员': '普通用户'}}</span>
            </template>
        </el-table-column>
        <el-table-column label="操作">
            <template slot-scope="scope">
                <i v-if="scope.row.username == 'admin'" class="el-icon-minus"></i>
                <el-button v-else type="danger"  icon="el-icon-delete" @click="deleteUser(scope.row.id)">删除</el-button>
            </template>
        </el-table-column>
        </el-table>
      </el-card>
    </div>

</template>

<script>
import axios from '@/utils/axios';
export default {
  data() {
    return {
      userList: [],
    }
  },
  methods: {
    getUsers() {
        axios.get('/users').then((res) => {
            this.userList = res.data.data;
        }).catch(error => {
            this.$message.error(error);
        })
    },
    showUsers() {
        this.dialogUserVisible = true;
    },
    deleteUser(id) {
        this.$confirm('你确定要删除该用户吗?', '温馨提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            axios.delete(`/deleteUser/${id}`).then((res) => {
                if (res.data.code === 0) {
                    this.getUsers();
                    this.$message.success("删除用户成功~");
                } else {
                    this.$message.error(res.data.message)
                }
            }).catch(error => {
                this.$message.error(error)
            })
        })
    },
  },
  mounted() {
    this.getUsers();
  }
}
</script>

<style lang="scss">
.page-container {
    min-height: 100%;
    box-sizing: border-box;
    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}
</style>