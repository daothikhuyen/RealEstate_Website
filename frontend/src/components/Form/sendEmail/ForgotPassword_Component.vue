
<template>
    <div class="HomeComponent">
        <navbar-component></navbar-component>
        <div class="recover_pass container-fluid">
            <div class="row p-0">
                <div class="col-lg-5 px-lg-0">
                    <div class="img_advertisement">
                        <div class="img_quangcao">
                            <div class="img w-100">
                                <img src="../../../assets/image/quangCao.png" class="quangcao1 w-100" alt="">
                                <img src="../../../assets/image/quangcao2.png" class="quangcao2 w-100" alt="">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-7 py-2 my-auto h-100">

                    <div class="recover_account mt-3" >
                        <h5 class="text-center pb-5">Khôi phục tài khoản</h5>
                        <div :class="{hide: !showSuccess}">
                            <div class="form_reset_account" >
                                <form action="" class="w-100">
                                    <div class="form-group">
                                        <div class="mb-2">
                                            <input type="email" name="email" id="email"  placeholder="Nhập email..." class="form-control" v-model="email" :class="{is_invalid :  error.email}"/>
                                            <span class="text-danger ps-2"><small>{{error.email}}</small></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="mb-2 w-75">
                                            <button type="submit" @click.prevent="showView()" class="button">Gửi</button>
                                        </div>
                                    </div>
                                </form>
                                <div class="d-flex justify-content-center align-items-center">
                                    <div class="loading" :class="{'d-block': !loading}">
                                        <div></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="bg-white py-5" :class="{hide: showSuccess}">
                            <div class="success bg-white">
                                <div>
                                    <div class="img_success">
                                        <img src="../../../assets/image/sendMail_success.png" alt="">
                                    </div>
                                    <div class="text-center" style="font-weight:500">
                                        Gửi Mail Thành Công.
                                    </div>
                                    <div class="py-4 text-center">
                                        <p>Đường dẫn khôi phục mật khẩu đã được gửi đến email bạn đăng ký với PhongTot.com
                                            Vui lòng kiểm tra và làm theo hướng dẫn.</p>
                                    </div>
                                    <button class="resetEmail btn float-end" @click.prevent="giveBack"><i class="fa-solid fa-arrow-left"></i> Trở về</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <FooterComponentVue></FooterComponentVue>
    </div>

</template>

<script>
import { ref, defineComponent, onMounted } from 'vue'
import {mapState,mapGetters,mapActions} from 'vuex'
import Swal from 'sweetalert2'

import userApi from '../../../Api/userApi'
import FooterComponentVue from '../../FooterComponent.vue'
import NavbarComponent from '../../NavbarComponent.vue'

export default defineComponent(
    {
        name: "Page Recover Pass",
        computed: {
        },
        components: {
            NavbarComponent,
            FooterComponentVue
        },
        data() {
            const loading = ref(true);
            const email = ref(null)
            const showSuccess = ref(true)

            const error = {
                email : '',
            }

            return {
                error,
                email,
                showSuccess,
                loading
            }
        },
        methods: {

            async sendEmail(){

                if(this.email === null){
                    this.error.email = 'Vui lòng không để trống'
                }else{

                    const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/
                    if (!reg.test(this.email)) {
                        this.error.email = 'Bạn nhập sai định dạng';
                    } else {
                        this.error.email = null
                    }
                }
            },

            async showView(){
                this.sendEmail();

                if(this.error.email === null){
                    this.loading = !this.loading
                    const response =  await userApi.forgot_password(this.email)

                    if(response.code == 1000){
                        this.showSuccess =! this.showSuccess
                        this.loading = !this.loading
                        
                    }else{

                        this.loading = !this.loading
                        Swal.fire({
                            icon: "error",
                            title: "Gửi email thất bại, vui lòng xem lại",
                            showConfirmButton: true,
                        }).then(()=> {
                            location.reload()
                        });
                    }
                }

            },

            giveBack(){
                console.log("hello")
                location.reload()
            }
        },
    }
)


</script>

<style scoped>

.hide{
    display: none;
}

form .form-group .form-control.is_invalid{
    border: 1px solid rgb(224, 60, 49);
}

.form_reset_account{
    position: relative;
}

.recover_pass{
    width: 60%;
    /* height: 615px; */
    height: auto;
    margin: 50px auto;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    background-color: #ffff;
}

.recover_pass .img_quangcao .img .quangcao2{
    width: 100%;
    display: none;
}

form .form-group{
    display: flex;
    justify-content: center;

}

form .form-group div{
    width: 75%;
}

form .form-group div label{
    padding: 10px;
}

form .form-group div input{
    width: 100%;
    padding: 8px 5px;
    border-radius: 8px;
}

form .form-group div .button{
    min-width: 100%;
    background-color:#ff5d26;
    padding: 7px 0;
    color: #fff;
    font-size: 18px;
    border-radius: 8px;
    margin-top: 10px;
}

form .form-group div .button:hover{
    background-color: rgb(255, 93, 38,0.8);
}

/* img send success */
.success .img_success {
    width: 100%;
    text-align: center;
    transition: all .3s ease-out;
}

.success .img_success img{
    width: 100px;
    max-width: 100%;
    object-fit: cover;
    object-position: center;
}

.success div div:nth-child(3){
    font-size: 15px;
    font-weight: 490;
}

.success .resetEmail{
    background-color: #e84c18;
    color: #fff;
    font-weight: 500;
}

.loading{
    width: 100vw;
    height: 100vh;
    display: none;
    overflow: hidden;
    position: absolute;
    /* background-color: #45474b; */
}

.loading div{
    height: 35px;
    width: 35px;
    border-top-color: #DCDCDC ;
    border-radius: 50%;
    border-style:dotted ;
    animation: spin 1.5s infinite linear;
    margin: auto;
}

@media only screen and (max-width: 992px) {
    .recover_pass .img_quangcao .img .quangcao2{
        width: 100%;
        display: block;
    }

     .recover_pass .img_quangcao .img .quangcao1{
        width: 100%;
        display: none;
    }

    .container-fluid{
        padding-left: 0px;
        padding-right: 0px;
    }
}

@media only screen and (max-width: 600px) {
    .row>* {
        padding-right: 0px;
        padding-left: 0px;
    }

    .recover_pass{
        width: 100%;
        /* height: 615px; */
        height: auto;
        margin: 50px auto;
        box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
        background-color: #ffff;
    }
}

</style>
