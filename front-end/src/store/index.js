import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token: ''
    },
    getters: {
    },
    mutations: {
        setToken(state, token) {
            state.token = token;
        }
    },
    plugins: [createPersistedState()]
})