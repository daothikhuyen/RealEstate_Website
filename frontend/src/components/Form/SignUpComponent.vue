// HomeComponent.vue

<template>
    <div class="SignComponent " :class="{loading: showLoading}">
        <navbar-component></navbar-component>
        <div class="view_home container-fluid mt-3" style="">
            <div class="row">
                <div class="col-md-2">

                </div>
                <div class="col-md-8 py-3">
                    <Form @submit="signup" :validation-schema="schema" v-slot="{errors}">
                        <div class="from_sign sign">
                            <div class="form-group">
                                <h3 class="text-center">Đăng Kí Tài Khoản</h3>
                            </div>
                            <div class="alter w-100">
                                <div  v-if="this.error['error'] != null" class="error">
                                        <i class="bi bi-exclamation-circle text-danger fw-bolder fs-5 pe-2"></i>
                                        {{this.error['error']}}
                                </div>
                                <div  v-if="this.error['success'] != null" class="success">
                                        <i class="fa-solid fa-check text-success fw-bolder fs-5 pe-2"></i>
                                        {{this.error['success']}}
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="" class="pt-2 pb-1 ps-2">Họ Tên</label>
                                <Field type="text" name="username" id="username" class="form-control" :class="{'is-invalid' : errors.name}" />
                                <span class="invalid-feedback">{{errors.name}}</span>
                                 <!-- <ErrorMessage name="name" class="invalid-feedback" /> -->
                            </div>
                            <div class="form-group">
                                <label for="" class="pt-2 pb-1 ps-2">Email</label>
                                <Field type="email" name="email" id="" class="form-control" :class="{'is-invalid' : errors.email}" />
                                 <span class="invalid-feedback">{{errors.email}}</span>
                            </div>
                            <div class="form-group">
                                <label for="" class="pt-2 pb-1 ps-2">Mật Khẩu</label>
                                <Field type="password" name="password" id="" class="form-control" :class="{'is-invalid' : errors.password}"/>
                                <span class="invalid-feedback">{{errors.password}}</span>
                            </div>
                            <div class="form-group">
                                <label for="" class="pt-2 pb-1 ps-2">Nhập lại mật khẩu</label>
                                <Field type="password" name="confirmPassword" id="" class="form-control" :class="{'is-invalid' : errors.confirmPassword}"/>
                                 <span class="invalid-feedback">{{errors.confirmPassword}}</span>
                            </div>
                            <div class="form-group d-flex flex-wrap flex-50" >
                                <Field type="checkbox" name="checkbox" id="" value="đồng ý"  :class="{'is-invalid' : errors.checkbox}"/>
                                <label for="" class="label_agree pt-2 pb-1 ps-3 ">Đồng ý với <span class="text-primary">các chính sách bảo mật</span> của  <br>chúng tôi.</label>
                                <span class="invalid-feedback ps-2">{{errors.checkbox}}</span>
                            </div>
                            <div class="form-group">
                                <label for="" class="pt-2 pb-1 ps-2"></label>
                                <!-- <input type="submit" class="form-control btn-login" value="Tạo tài khoản"> -->
                                <button type="submit"  class="form-control btn-login">Tạo tài khoản</button>
                            </div>
                            <div class="form-group footer_form mt-3 text-black-50">
                                <span>Bạn đã có tài khoản? </span>
                                <a href="/login">Đăng nhập ngay</a>
                            </div>
                            <div>
                                <small class="fw-medium">
                                    <span class="text-danger"><i class="fa-solid fa-circle-exclamation"></i> Lưu ý:</span>
                                    <ul>
                                        <li>+ Độ dài ít nhất 8 ký tự.Chứa ít nhất một chữ cái viết thường.</li>
                                        <li>+ Chứa ít nhất một chữ cái viết hoa,một số, một kí tự đặc biệt</li>
                                    </ul>
                                </small>
                            </div>
                        </div>
                    </Form>
                </div>
                <div class="col-md-2">

                </div>
            </div>
            <!-- <div class="row">
                <div class="contact_us offset-md-2 col-md-8 offset-sm-1 col-sm-10 bg-white mb-4">
                    <div class="contact_img w-50 mx-auto d-flex justify-content-center" >
                        <img class="w-75 " src="../../../image/contact.png" alt="">
                    </div>
                    <div class="info_contact text-center">
                        <span class="text-black-75" >Liên hệ với chúng tôi nếu bạn cần hổ trợ</span>
                    </div>
                    <div class=" d-flex justify-content-center my-3">
                        <button class="btn_contact p-2 px-3">Gửi Liên Hệ</button>
                    </div>
                </div>
            </div> -->
        </div>
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
    <footer-component></footer-component>
</template>

<script>
import "../../assets/css/form.css"
import {ref} from 'vue'
import { Form, Field } from 'vee-validate';
import * as yup from 'yup';
import {mapState,mapActions} from 'vuex'
import debounce from 'lodash.debounce';

import NavbarComponent from '../NavbarComponent.vue'
import FooterComponent from '../FooterComponent.vue'

import userApi from '../../Api/userApi';


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
        const phoneRegExp = /^((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?$/
        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        const schema = yup.object().shape({
            username: yup.string().required('Vui lòng nhập thông tin'),
            email: yup.string().required('Vui lòng nhập thông tin').email('Email không đúng định dạng'),
            password: yup.string().required('Vui lòng nhập thông tin').matches(regex,'Độ dài ít nhất 8 ký tự.Chứa ít nhất một chữ cái viết thường. Chứa ít nhất một chữ cái viết hoa.\n Chứa ít nhất một số. Chứa ít nhất một ký tự đặc biệt'),
            confirmPassword:  yup.string().required('Vui lòng nhập thông tin')
                            .oneOf([yup.ref('password'), null], 'Mật khẩu phải trùng khớp'),
            checkbox: yup.string().required("Đồng ý với chính sách bảo mật để bảo mật thông tin")

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

        signup: debounce(async function(values){
             this.showLoading = !this.showLoading

            const newUser = {
                username : values.username,
                email : values.email,
                password : values.password

            }
           
            const result = await userApi.signup(newUser)

            if(result.code === 1000){
                this.$store.commit('OTP',result.otp)
                this.$router.push({name : 'verify_otp', params: {otp: values.email}})


            }else{
                this.error['error'] = result.message
            }

        },1000)
    }
}

</script>

