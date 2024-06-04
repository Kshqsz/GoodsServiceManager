<template>
  <div id = "user">
    <el-card class="page-container">
      <h1 style="text-align: center">分类管理</h1>
        <el-table :data="categoryList">
          <el-table-column
                label="id">
                <template slot-scope="scope">
                  <span>{{ scope.$index + 1 }}</span>
                </template>
            </el-table-column>
            <el-table-column
                prop="category"
                label="分类">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button type="primary" icon="el-icon-edit-outline" @click="updateCategory(scope.row.category)">编辑</el-button>
                  <el-button type="danger" icon="el-icon-delete" @click="deleteCategory(scope.row.category)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
    <el-dialog
      title="编辑分类"
      :visible.sync="dialogVisible"
      width="80%">
        <el-table :data="goodsBycategoryList"
            border
            style="width: 100%">
        <el-table-column label="id" width="50px">
            <template slot-scope="scope">
                <span>{{ scope.$index + 1}}</span>
            </template>
        </el-table-column>
        <el-table-column prop="name" label="名称" width="100px"></el-table-column>
        <el-table-column prop="category" label="分类"></el-table-column>
        <el-table-column prop="origin" label="产地"></el-table-column>
        <el-table-column prop="price" label="价格"></el-table-column>
        <el-table-column prop="productionDate" label="生产日期"></el-table-column>
        <el-table-column prop="manufacturer" label="生产商"></el-table-column>

        <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button type="primary" icon="el-icon-edit" round  size="small" @click="getGoodsById(scope.row.id)">编辑</el-button>
                <el-button type="danger"  icon="el-icon-delete" round  size="small" @click="deleteGoods(scope.row.id)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    </el-dialog>
    <el-dialog title="编辑商品" :visible.sync="dialogUpdateVisible">
                <el-form :model="goods">
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="id" :label-width="'50px'">
                                <el-input v-model="goods.id" style="width: 240px;" :disabled="true"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="名称" :label-width="'50px'">
                                <el-input autocomplete="off" v-model="goods.name" style="width: 240px;"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="分类" :label-width="'50px'">
                                <el-input  autocomplete="off" v-model="goods.category" style="width: 240px;"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="产地" :label-width="'50px'">
                                <el-input  autocomplete="off" v-model="goods.origin" style="width: 240px;"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="价格" :label-width="'50px'">
                                <el-input  autocomplete="off" v-model="goods.price" style="width: 240px;"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="生产日期">
                                <el-date-picker type="date" placeholder="选择日期" v-model="goods.productionDate" style="width: 240px"></el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-form-item label="生产商">
                        <el-input  autocomplete="off" v-model="goods.manufacturer" style="width: 240px;"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogUpdateVisible = false">取 消</el-button>
                    <el-button @click="updateGoods" type="primary">确 定</el-button>
                </div>
    </el-dialog>

  </div>
</template>
<script>
import axios from '@/utils/axios'
export default {
  data() {
    return {
      goods: {
          id: '',
          name: '',
          category: '',
          origin: '',
          price: '',
          productionDate: '',
          manufacturer: ''
      },
      goodsList: [],
      categoryList: [],
      goodsBycategoryList: [],
      dialogVisible: false,
      dialogUpdateVisible: false,
    }
  },
  methods: {
    async getGoods() {
      await axios.get("/goods").then(res => {
        this.goodsList = res.data;
        this.getCategories();
      }).catch(error => {
        this.$message.error(error);
      })
    },
    getCategories() {
      this.categoryList = [];
      for (var i = 0; i < this.goodsList.length; i++) {
        this.categoryList.push(this.goodsList[i].category);
      }
      this.categoryList = Array.from(new Set(this.categoryList));
      this.categoryList = this.categoryList.map(category => ({category: category}));
    },
    deleteCategory(category) {
        this.$confirm('你确定要删除该分类吗?', '温馨提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then( () => {
            axios.delete(`/deleteCategory/${category}`).then(async (res) => {
                if (res.data.code === 0) {
                    await this.getGoods(); // 删除成功后更新分类列表
                    this.$message.success("删除分类成功~");
                } else {
                    this.$message.error("服务错误");
                }
            }).catch(error => {
                this.$message.error(error);
            })
        })
    },
    async getGoodsByCategory(category) {
      await axios.get(`/getGoodsByCategory/${category}`).then((res) =>  {
        if (res.data.code === 0) {
          this.goodsBycategoryList = res.data.data;
        } else {
          this.$message.error("服务错误")
        }
      }).catch(error => {
        this.$message.error(error);
      })
    },
    updateCategory(category) {
      this.getGoodsByCategory(category);
      this.dialogVisible = true;
    },
    deleteGoods(id) {
          this.$confirm('你确定要删除吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
          }).then(() => {
              axios.delete(`/delete/${id}`).then(() => {
                  this.getGoods();
                  this.$message.success("删除商品成功~");
              }).catch(error => {
                this.$message.error(error);
              })
          })
    },
    getGoodsById(id) {
        this.dialogUpdateVisible = true;
        axios.get(`/getById/${id}`).then(res => {
            this.goods = res.data;
        }).catch(error => {
          this.$message.error(error);
        })
    },
    updateGoods() {
        axios.put('/update', this.goods).then(async () => {
          await this.getGoodsByCategory(this.goods.category);
          await this.getGoods();
          this.$message.success("更新商品成功~");
        }).catch(error => {
          this.$message.error(error);
        });
        this.dialogUpdateVisible = false;
    },
  },
  mounted() {
    this.getGoods();
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