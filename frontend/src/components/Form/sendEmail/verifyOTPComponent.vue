
<template>
    <div class="verify">
        <navbar-component></navbar-component>
        <Form @submit="Validate" :validation-schema="schema" v-slot="{errors}">
            <div class="from_login login">
                <div class="form-group">
                    <h3 class="text-center">Nhập Mã Xác Minh</h3>
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
                    <label for="" class="pt-2 pb-1 ps-2">Mã OTP</label>
                    <Field type="text" name="otp" id="otp" class="form-control" placeholder="Tối đa 4 kí tự" :class="{'is-invalid' : errors.otp}"/>
                    <span class="invalid-feedback">{{errors.otp}}</span>
                </div>

                <div class="form-group mt-4">
                    <label for="" class="pt-2 ps-2"></label>
                    <!-- <input type="submit" class="form-control btn-login" value="Tạo tài khoản"> -->
                    <button type="submit"  class="form-control btn-login w-25 float-end">Xác Minh</button>
                </div>
            </div>
        </Form>
    </div>
</template>

<script>
import {ref} from 'vue'
import { Form, Field } from 'vee-validate';
import * as yup from 'yup';
import Swal from 'sweetalert2'

import NavbarComponent from '../../NavbarComponent.vue'
import userApi from '../../../Api/userApi';

export default {
    name: "LoginComponent",
    components:{
        NavbarComponent,
        Form,
        Field,
    },
    data(){
        const error = [];
        const schema = yup.object().shape({
            otp: yup.string().required('Vui lòng nhập thông tin').matches(/[0-9]{4}/,'').min(4)

        })
        return {
            schema,
            error
        }
    },
    methods: {

        async Validate(values) {

            const array = {
                email :  this.$route.params.otp,
                token : values.otp
            }
            const result = await userApi.verifyOTP(array);

            if(result.code === 1000){

                Swal.fire({
                    icon: "success",
                    title: "Đăng Kí Tài Khoản Thành Công",
                    showConfirmButton: false,
                    timer: 1500
                }).then(()=> {
                    this.error['success'] = result.message
                    this.$router.push({name : 'login'})
                });


            }else{
                console.log(result.message)
                this.error['error'] = result.message
            }
        },
    }
}


</script>

<style scoped>

:root{
        --primary-color: #ff5d26
    }


    .from_login{
        background-color: #fff;
        border: 1px solid #dedede;
        border-radius: 8px;
    }

    .from_login.login{
        max-width: 600px;
        margin: 0 auto;
        padding: 30px 30px 100px;
        background-color: #fff;
    }

    .from_login .form-group .btn-login{
        background-color: var(--primary-color);
        color: #ffff;
        font-size: 18px;
    }

    .from_login .form-group.footer_form a{
        list-style: none;
        text-decoration: none;
    }

    .view_home .contact_us{
        border: 5px solid rgb(255, 93, 38,0.2);
        padding: 16px;
        border-style: dashed;
        border-radius: 10px;
    }

    .view_home .btn_contact{
        background-color: var(--primary-color);
        color: #fff;
        font-weight: 600;
        border: none;
        border-radius: 8px;
    }

    .view_home .btn_contact:hover{
        background-color: #af3810 ;
    }

    .alter {
        transition: all .3s ease-in-out;
    }

    .success {
        background-color: #e5ffe7;
         width: 100%;
        margin-top: .25rem;
        font-size: .875em;
        padding: 10px 20px;
        font-weight: 490;
        transition: all .3s ease-in;
        display: flex;
        align-items: center;
    }

    .error{
        width: 100%;
        margin-top: .25rem;
        font-size: .875em;
        padding: 10px 20px;
        background-color: #ffe5ea;
        font-weight: 490;
        transition: all .3s ease-in;
        display: flex;
        align-items: center;
    }

    .verify {
        background-color: #fff;
        height: 100vh;
        overflow: hidden;

    }

    .verify .from_login{
        margin: 10% auto;
        width: 100%;
    }

    .from_login.login {
        box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 0px 1px;
        padding: 30px 30px 40px;

    }

</style>
