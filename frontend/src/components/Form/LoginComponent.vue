// HomeComponent.vue

<template>
    <div class="LoginComponent" :class="{loading: showLoading}">
        <navbar-component></navbar-component>
        <div class="view_home container-fluid mt-3" style="">
            <div class="row">
                <div class="col-md-2">

                </div>
                <div class="col-md-8 py-3">
                    <Form @submit="Validate" :validation-schema="schema" v-slot="{errors}">
                        <div class="from_login login">
                            <div class="form-group">
                                <h3 class="text-center">Đăng Nhập</h3>
                            </div>
                            <div class="alter w-100">
                                <div  v-if="this.error['error'] != null" class="error">
                                        <i class="bi bi-exclamation-circle text-danger fw-bolder fs-5 pe-2"></i>
                                        {{error['error']}}
                                </div>
                                <div  v-if="this.error['success'] != null" class="success">
                                        <i class="fa-solid fa-check text-success fw-bolder fs-5 pe-2"></i>
                                        {{error['success']}}
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="" class="pt-2 pb-1 ps-2">Email</label>
                                <Field type="text" name="email" id="email" class="form-control" :class="{'is-invalid' : errors.email}" />
                                <span class="invalid-feedback">{{errors.email}}</span>
                            </div>
                            <div class="form-group">
                                <label for="" class="pt-2 pb-1 ps-2">Mật Khẩu</label>
                                <Field type="password" name="password" id="password" class="form-control" :class="{'is-invalid' : errors.password}" />
                                <span class="invalid-feedback">{{errors.password}}</span>
                            </div>
                            <div class="form-group">
                                <label for="" class="form-label"></label>
                                <button type="submit"  class="form-control btn-login">Đăng Nhập</button>
                            </div>
                            <div class="form-group footer_form d-flex justify-content-between my-3">
                                <a href="account/forgot_password" ><small>Bạn quên mật khẩu ?</small></a>
                                <a href="/signup"><small>Tạo tài khoản mới</small></a>

                            </div>
                        </div>
                    </Form>
                </div>
                <div class="col-md-2">

                </div>
            </div>
            <!-- <div class="row">
                <div class="contact_us offset-md-2 col-md-8 offset-sm-1 col-sm-10  bg-white mb-4">
                    <div class="contact_img w-50 mx-auto d-flex justify-content-center" >
                        <img class="w-75 " src="../../../image/contact.png" alt="">
                    </div>
                    <div class="info_contact text-center">
                        <span class="text-black-50" >Liên hệ với chúng tôi nếu bạn cần hổ trợ</span>
                    </div>
                    <div class=" d-flex justify-content-center my-3">
                        <button class="btn_contact p-2 px-3">Gửi Liên Hệ</button>
                    </div>
                </div>
            </div> -->
        </div>
        <footer-component></footer-component>
    </div>
    <div :class="{hide: !showLoading}">
        <div class="page_loading">
            <div>
                <div></div>
                <div class="">
                    Loading...
                </div>
            </div>
        </div>
    </div>

</template>

<script>
import "../../assets/css/form.css"
import {ref} from 'vue'
import { Form, Field } from 'vee-validate';
import * as yup from 'yup';
import debounce from 'lodash.debounce';

import NavbarComponent from '../NavbarComponent.vue'
import FooterComponent from '../FooterComponent.vue'

import notification from '../../assets/js/Notification';
import userApi from '../../Api/userApi'

export default {
    name: "LoginComponent",
    components:{
        NavbarComponent,
        FooterComponent,
        Form,
        Field,
    },
    data(){
        const error = [];
        const showLoading = ref(false)
        const schema = yup.object().shape({
            email: yup.string().required('Vui lòng nhập thông tin').email('Email không đúng định dạng'),
            password: yup.string().required('Vui lòng nhập thông tin'),

        })
        return {
            schema,
            error,
            showLoading
        }
    },
    mounted() {
    },
    methods: {
        Validate : debounce(async function (values) {
            this.showLoading = !this.showLoading

            try {
                const result = await userApi.login(values);

                if (result.code === 1000) {
                    this.$store.commit('SET_TOKEN', result.result.token);
                    this.$store.dispatch('getUser');
                    this.$store.commit('LOGIN');
                    this.showLoading = !this.showLoading;

                    notification.showNotification("success", "Đăng Nhập Thành Công", "", 1500)
                    .then(() => {
                        this.showLoading = !this.showLoading;
                        this.$router.push('/home');
                    });
                }
            } catch (error) {
                console.error("Lỗi trong quá trình đăng nhập:", error);

                this.showLoading = !this.showLoading;

                delete this.error['error'];
                this.error['error'] = error.response.data.message;
            }
        },1000)
    },
    created(){

    }
}

</script>
