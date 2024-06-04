import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from '../views/LoginView.vue'
import HomeView from '@/views/HomeView.vue'
import GoodsView from '@/views/GoodsView.vue'
import CategoryView from './../views/CategoryView.vue'
import UserView from './../views/UserView.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'login',
        component: LoginView
    },
    {
        path: '/home',
        name: 'home',
        component: HomeView,
        redirect: '/goods',
        children: [
            { path: '/goods', name: 'goods', component: GoodsView },
            { path: '/category', component: CategoryView},
            { path: '/user', component: UserView}
        ]
    },
    {
        path: '/',
        redirect: '/login'
    }
]

const router = new VueRouter({
    routes
})

export default router