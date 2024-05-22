<template>
    <div id="printPreview" ref="printArea">
      <h1>商品列表打印预览</h1>
      <table border="1" cellspacing="0" cellpadding="5">
        <thead>
          <tr>
            <th>id</th>
            <th>名称</th>
            <th>分类</th>
            <th>产地</th>
            <th>价格</th>
            <th>生产日期</th>
            <th>生产商</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in goodsList" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.name }}</td>
            <td>{{ item.category }}</td>
            <td>{{ item.origin }}</td>
            <td>{{ item.price }}</td>
            <td>{{ item.productionDate }}</td>
            <td>{{ item.manufacturer }}</td>
          </tr>
        </tbody>
      </table>
      <div style="margin-top: 20px;">
        <el-button type="primary" @click="print">打印</el-button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      goodsList: {
        type: Array,
        required: true
      }
    },
    methods: {
      print() {
        const printArea = this.$refs.printArea;
        const originalContent = document.body.innerHTML;
        document.body.innerHTML = printArea.innerHTML;
        window.print();
        document.body.innerHTML = originalContent;
        window.location.reload();
      }
    }
  }
  </script>
  
  <style scoped>
  #printPreview {
    width: 100%;
    padding: 20px;
  }
  table {
    width: 100%;
    border-collapse: collapse;
  }
  th, td {
    border: 1px solid #ccc;
    padding: 10px;
    text-align: left;
  }
  </style>
  