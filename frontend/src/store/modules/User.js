import userApi from "../../Api/userApi"
import router from '../../router/index';
import axios from 'axios';

export default ({
    state : { // toy
      isLoggedIn : !!localStorage.getItem('KEY_TOKEN'),
      verifyOTP : null,
      authUser: null,
      isEmailUser: null,
    },

    getters: {
        isLoggedIn: (state) => state.isLoggedIn,
        authUser:(state) => state.authUser,
        isEmailUser:(state) => state.isEmailUser
    },

    mutations: { // button (instructions)

      SET_TOKEN (state,token){
          localStorage.setItem('KEY_TOKEN',token)
      },

      LOGIN(state){
        state.isLoggedIn = true //ON
      },

      LOGOUT(state){
        localStorage.removeItem("KEY_TOKEN")
        state.isLoggedIn = false // OFF
      },

      SET_USER(state,user){
        state.authUser = user
      },

      OTP(state,otp){
        state.verifyOTP = otp
      },

      SET_EMAIL(state,email){
        state.isEmailUser = email
      }

    },

    actions: {// kids

        login({commit}){
            commit('LOGIN')
        },

        async logout({commit, dispatch}){
            const token = {
              token : localStorage.getItem("KEY_TOKEN")
            }
            const result = await userApi.logout(token)

            if(result.code === 1000){
                commit('LOGOUT')
                dispatch('navigateTologin')
            }

        },

        navigateTologin(){
            router.push({name: 'login'});
        },

        async getUser({commit,dispatch}){

            try {
                const csrfToken = localStorage.getItem("KEY_TOKEN");

                if(csrfToken){
                    const response = await userApi.getMyInfo()
                    if(response.code === 1000){
                        commit('SET_USER',response.result)
                    }else{
                      commit('LOGOUT')
                      dispatch('navigateTologin')
                    }
                }

            } catch (error) {
              commit('LOGOUT')
                      dispatch('navigateTologin')
                console.error('Lỗi lấy thông tin:', error);
            }
        },

    },

  })

