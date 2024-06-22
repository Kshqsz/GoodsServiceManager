<template>
  <div>
    <el-carousel :interval="3000" type="card" height="450px">
        <el-carousel-item v-for="item in imageList" :key="item.id">
            <img :src="item.url" alt="" class="image">
        </el-carousel-item>
    </el-carousel>
    <!-- <el-carousel :interval="3000" arrow="always" height="800px">
        <el-carousel-item v-for="item in imageList" :key="item.id">
            <img :src="item.url" alt="" style="object-fit: fill; height: 100%; width: 100%;">
        </el-carousel-item>
    </el-carousel> -->
  </div>
</template>

<script>
export default {
    data() {
        return {
            imageList:[
                {id:0, url:require("../assets/2024-6-19.jpg")},
                {id:1, url:require("../assets/2024-6-21.jpg")},
                {id:4, url:require("../assets/2024-6-14.jpg")},
                {id:5, url:require("../assets/2024-6-18.jpg")},
                {id:6, url:require("../assets/2024-6-19.jpg")},
                {id:7, url:require("../assets/th.jpg")},
                {id:8, url:"https://big-event-hsk.oss-cn-hangzhou.aliyuncs.com/4d92f7cf-0500-48c2-9894-bdb60d221003.png"}
            ]
        }
    }
}
</script>

<style>
.image {
    object-fit: fill;
    width: 100%;
    height: 100%;
}
.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}
</style>