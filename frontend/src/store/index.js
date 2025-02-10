import { createStore } from 'vuex'

import user from './modules/User'

const store = new createStore({
    modules: {
        user
    }
})

export default store;