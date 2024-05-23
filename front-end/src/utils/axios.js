import axios from 'axios'
import store from '@/store'

const service = axios.create({baseURL: '/api'})

service.interceptors.request.use(config => {
    const token = store.state.token;
    if (token != '') {
        config.headers.Authorization = token;
    }
    return config;
}, error => {
    return Promise.reject(error);
});

export default service;