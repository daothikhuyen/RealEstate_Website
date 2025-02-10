import postApi from "../../assets/js/postApi"
import router from '../../router/index';
import axios from 'axios';

export default ({
    state : { // toy
      newPost : []
    },

    getters: {
        newPost: (state) => state.newPost,
    },

    mutations: { // button (instructions)

        CREATE(state,newPosts){
          state.newPost = newPosts
        },
    },
    actions: {
        createPosts({ commit }, newPost){
            console.log(newPost)
            commit('CREATE',newPost)
        }

    }
})
