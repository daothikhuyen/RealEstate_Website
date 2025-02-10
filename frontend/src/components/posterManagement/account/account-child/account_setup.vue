<template>
    <div class="profile_account row mb-3">
        <div class="col-md-12 mb-3 account_setup">
            <div class="info_account">
                <div class="title">
                    <h5>Đổi Mật Khẩu</h5>
                </div>
                <div :class="{hide:!error['error']}">
                    <div class="error rounded-3" >
                        <i class="bi bi-exclamation-circle text-danger fw-bolder fs-5 pe-2"></i>
                        <span>{{error['error']}}</span>
                    </div>
                </div>
                <form class="form">
                    <div class="form-group">
                        <div class="mb-2">
                            <label for="" class="">Mật khẩu hiện tại</label>
                            <div class="row align-items-center">
                                <div class="col-md-4">
                                    <input type="password" name="password" id="password" class="form-control"
                                        :class="{is_invalid :  error['current_pass']}"
                                        @input="Validate('current_pass')" v-model="password.current_pass"
                                    />
                                </div>
                                <div class="col-md-8">
                                    <span class="btn_forgot_pass ps-0" @click.prevent="forgot_pass()">Bạn quên mật khẩu?</span>
                                </div>
                            </div>
                            <span :class="{show: error['current_pass']}"><small>{{error['current_pass']}}</small></span>
                        </div>
                        <div class="mb-2">
                            <label for="" class="">Mật khẩu mới</label>
                            <div class="row align-items-center">
                                <div class="col-md-4">
                                    <input type="password" name="password" id="password" class="form-control"
                                        :class="{is_invalid :  error['new_pass']}"
                                        @input="Validate('new_pass')" v-model="password.new_pass"
                                    />
                                </div>
                            </div>
                            <span :class="{show:error['new_pass']}"><small>{{error['new_pass']}}</small></span>
                        </div>
                        <div class="mb-2">
                            <label for="" class="">Nhập lại mật khẩu mới</label>
                            <div class="row align-items-center">
                                <div class="col-md-4">
                                    <input type="password" name="password" id="password" class="form-control"
                                        :class="{is_invalid : error['re_enter_new_pass']}"
                                        @input="Validate('re_enter_new_pass')" v-model="password.re_enter_new_pass"
                                    />
                                </div>
                                <div class="col-md-8">
                                    <button type="submit" class="btnSetUp" @click.prevent="Validate($event)">Lưu thay đổi</button>
                                </div>
                            </div>
                            <span :class="{show: error['re_enter_new_pass']}"><small>{{error['re_enter_new_pass']}}</small></span>
                        </div>
                    </div>
                </form>
                <div>
                    <ul class="note_pass">
                        <li>
                            <svg width="1em" height="1em" viewBox="0 0 24 24" fill="currentColor" xmlns="http://www.w3.org/2000/svg"><path d="M15 12C15 13.6569 13.6569 15 12 15C10.3431 15 9 13.6569 9 12C9 10.3431 10.3431 9 12 9C13.6569 9 15 10.3431 15 12Z"></path></svg>
                            <span>Độ dài ít nhất 8 ký tự</span>
                        </li>
                        <li>
                            <svg width="1em" height="1em" viewBox="0 0 24 24" fill="currentColor" xmlns="http://www.w3.org/2000/svg"><path d="M15 12C15 13.6569 13.6569 15 12 15C10.3431 15 9 13.6569 9 12C9 10.3431 10.3431 9 12 9C13.6569 9 15 10.3431 15 12Z"></path></svg>
                            <span> Chứa ít nhất một chữ cái viết thường.</span>
                        </li>
                        <li>
                            <svg width="1em" height="1em" viewBox="0 0 24 24" fill="currentColor" xmlns="http://www.w3.org/2000/svg"><path d="M15 12C15 13.6569 13.6569 15 12 15C10.3431 15 9 13.6569 9 12C9 10.3431 10.3431 9 12 9C13.6569 9 15 10.3431 15 12Z"></path></svg>
                            <span>Chứa ít nhất một chữ cái viết thường. </span>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="request_deletion">
                <div class="title">
                    <h5>Yêu cầu xoá tài khoản</h5>
                </div>
                <div>
                    <div>
                        <span>
                            Gửi yêu cầu xoá toàn bộ thông tin của tài khoản. Sau khi được xử lý, <br>
                            toàn bộ thông tin sẽ được xoá và không thể hoàn tác.
                        </span>
                    </div>
                    <button class="btn_delete_account" @click.prevent="toggleModal()">
                        Yêu cầu xoá tài khoản
                    </button>
                </div>
            </div>
            <div class="home">
                <ModelDeltete @close="toggleModal" :modalActive="modalActive.delete">
                    <div class="modal-body">
                        <div class="d-flex align-items-center">
                            <span class="px-2">
                                <i class="fa-solid fa-triangle-exclamation fa-lg" style="color: #FFD43B;"></i>
                            </span>
                            <span class="">
                                Khi bạn đồng ý xóa tài khoản, toàn bộ thông tin thuộc tài khoản của bạn sẽ bị xóa vĩnh viễn.
                            </span>
                        </div>
                    </div>
                </ModelDeltete>
                <ModalForgot @close="forgot_pass" :modalActive="modalActive.forgot">
                    <div class="modal-body">
                        <div :class="{hide: showHide}" @click.prevent="forgot_password()">
                            <div class="body-send d-flex align-items-center" >
                                <span class="px-2">
                                    <i class="fa-regular fa-envelope fa-lg pe-2"></i>
                                </span>
                                <span>
                                    <div style="font-size:17px">Gửi xác minh qua email</div>
                                    <div v-if="authUser">{{formatEmail()}}</div>
                                </span>
                            </div>
                        </div>
                        <div style="color:rgb(224, 60, 49), font-weight:500" class="text-center" :class="{hide: !loading}">
                            Loading.....
                        </div>
                        <div :class="{hide: !showHide}">
                            <div class="img_success">
                                <img src="../../../../assets/image/sendMail_success.png" alt="">
                            </div>
                            <div class="text-center" style="font-weight:500">
                                Gửi Mail Thành Công.
                            </div>
                        </div>
                    </div>
                </ModalForgot>

            </div>
        </div>
    </div>
</template>

<script>
import { ref,defineComponent } from 'vue'
import Swal from 'sweetalert2'
import {mapState,mapGetters,mapActions} from 'vuex'

import ModelDeltete from "../../../../components/modal/delete_account.vue"
import ModalForgot from "../../../../components/modal/forgot_password.vue"

import userApi from '../../../../Api/userApi'

export default defineComponent({
    components: {
        ModelDeltete,
        ModalForgot,
    },
    computed: {
        ...mapGetters(['authUser']),
    },
    data(){

        const img_avatar = ref('/storage/uploads/2024/06/04/profile.jpg')
        const showHide = ref(false);
        const loading = ref(false);
        const error = ref([])
        const modalActive = {
            delete: false,
            forgot: false,
        }
        const password = {
            current_pass: "",
            new_pass: "",
            re_enter_new_pass: ""
        }

        return {
            img_avatar,
            password,
            error,
            modalActive,
            showHide,
            loading
        }

    },
    methods: {

        Validate(value){
            const array = ['current_pass','new_pass','re_enter_new_pass'];
            this.error = {}

            if(value.type == 'click'){
                array.forEach(element => {

                    if(this.password[element] === ''){
                        this.error[element] = 'Vui lòng nhập mật khẩu'
                    }
                });

                const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
                if(!this.password.new_pass.match(regex)){
                    this.error['new_pass'] = 'Mật khẩu không hợp lệ'
                }

                if(this.password.new_pass !== this.password.re_enter_new_pass){
                    this.error['re_enter_new_pass'] = 'Mật khẩu không trùng khớp'
                }

                if(Object.keys(this.error).length == 0){
                    this.changePassword()
                }

            }else{

                if(this.password[value] != ''){
                    delete this.error[value];
                }

            }

        },

        async changePassword(){

            const attribute = this.password
            const data = {
                current_pass: attribute.current_pass,
                new_pass: attribute.new_pass
            }

            const response = await userApi.change_password(data)

            if(!response.error){
                Swal.fire({
                    icon: "success",
                    title: "Thay đổi mật khẩu thành công",
                    showConfirmButton: false,
                    timer: 1500
                }).then(()=> {
                    this.$store.dispatch('logout');
                });
            }else{
                this.error['error'] = "Mật khẩu không chính xác"
            }
        },

        toggleModal (){
            this.modalActive.delete = !this.modalActive.delete;
        },

        forgot_pass(){
            this.showHide = false;
            this.loading = false;
            this.modalActive.forgot = !this.modalActive.forgot;
        },

        formatEmail(){
            const email = this.authUser.email
            const [name , domain] = email.split('@')

            return name.slice(0,2)+ '**********' +"@"+domain

        },

        async forgot_password(){
            this.loading = !this.loading

            setTimeout(() => {
                this.loading = !this.loading
                this.showHide = !this.showHide;
            },1500)

            console.log(this.authUser.email)
            const response =  await userApi.forgot_password(this.authUser.email)

            if(response.code == 1000){
                localStorage.removeItem('token')
            }else{

            }

        },


    }
})
</script>

<style scoped>
    /* .profile_account {
        padding-bottom: 30px;
    } */

    .profile_account .info_account{
        margin-top: 30px;
    }

    .show{
        display: block;
    }

    .hide{
        display: none;
        transition: .3s ease-in-out;
    }

    ul li{
        font-size: 14px;
        line-height: 16px;
        font-weight: 400;
        color: rgb(153, 153, 153);
    }

    .note_pass{
        padding-left: 5%;
    }

    .account_setup{
        padding: 0 30px;
    }

    form {
        padding-left: 30px;
    }

    form .form-group label{
        width: 250px;
        font-weight: 500;
        padding: 5px 10px;
    }

    form .form-group .btn_forgot_pass{
        color: rgb(218, 5, 5);
        font-weight: 490;
        cursor: pointer;
    }

    form .form-group .btn_forgot_pass:hover{
        color: rgb(255, 0, 0,0.5);
    }

    form .form-group button{
        display: initial;
        width: auto;
        text-align: center;
        background-color: #123b83;
        border: 1px solid #DCDCDC;
        color: white;
    }

    form .form-group .form-control:hover{
        border: 1px solid black;
    }

    form .form-group .form-control.is_invalid{
        border: 1px solid rgb(224, 60, 49);
    }

    form .form-group div:nth-child(3) .btnSetUp {
        padding: 5px 15px;
        background-color: #ff5d26;
        border: 1px solid rgb(255, 93, 38,0.5);
        border-radius: 8px ;
        font-weight: 490;
    }

    form .form-group div span {
    font-size: 14px;
    line-height: 16px;
    font-weight: 400;
    padding-left: 10px;
    color: rgb(224, 60, 49);
    }

    form .form-group div:nth-child(3) .btnSetUp:hover{
        background-color: rgb(255, 93, 38,0.8);
    }


    /* request deletion of account */
    .account_setup .request_deletion div:nth-child(2) span{
        font-size: 15px;
    }

    .request_deletion .btn_delete_account{
        margin-top: 10px;
        margin-left: 10px;
        max-width: auto;
        border: 1px solid #ff5d26;
        padding: 5px 10px;
        border-radius: 5px;
        font-weight: 500;
        color: #ff5d26;
        cursor: pointer;
        transition: .3s all ease-in-out;
    }

    .request_deletion .btn_delete_account:hover{
        background-color: rgb(238, 113, 71);
        color: #fff;
    }

    /* Thông báo */
    /* .modal {
        position: fixed;
        z-index: 9998;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        display: table;
        transition: opacity 0.3s ease;
    } */

  .home .modal-content {
    display: flex;
    flex-direction: column;
  }

  /* modal */

    .modal-body .body-send{
        padding: 10px 10px;
        border: 1px solid #dcdcdc;
        border-radius: 8px ;
        margin: 10px 0;
        cursor: pointer;
        transition: all .3s ease-in;
    }

    .modal-body .body-send:hover{
        box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    }

    .modal-body .img_success {
        width: 100%;
        text-align: center;
        transition: all .3s ease-out;
    }

    .modal-body .img_success img{
        width: 100px;
        max-width: 100%;
        object-fit: cover;
        object-position: center;
    }

    @media only screen and (max-width: 768px) {
        .account_setup{
            padding-right: 80px;
        }

        form .form-group div:nth-child(3) .btnSetUp{
            margin-top: 20px;
        }
    }

</style>

