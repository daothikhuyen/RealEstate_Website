
<template>
    <div class="HomeComponent">
        <navbar-component></navbar-component>
        <div class="recover_pass container-fluid">
            <div class="row p-0">
                <div class="col-lg-5 px-lg-0">
                    <div class="img_advertisement">
                        <div class="img_quangcao">
                            <div class="quangcao1"></div>
                            <div class="img w-100">
                                <img src="../../assets/image/quangcao2.png" class="quangcao2 w-100" alt="">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-7 col-md-12 py-2">
                    <div class="create_new_password w-100 py-3">
                        <h5 class="text-center py-lg-3">Tạo mật khẩu mới</h5>
                        <div class=" d-flex justify-content-between">
                            <form action="" class="form_reset_account w-100">
                                <div class="form-group">
                                    <div class="mb-2">
                                        <label for="" class="title">Mật khẩu mới</label>
                                        <input type="password" name="password" id="password" v-model="password.new_pass"  @input="Validate('new_pass')"  class="form-control"/>
                                        <span class="text-danger ps-2"><small>{{error['new_pass']}}</small></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="mb-2">
                                        <label for="" class="title">Nhập lại mật khẩu mới</label>
                                        <input type="password" name="password" id="password" v-model="password.re_enter_new_pass"
                                            @input="Validate('re_enter_new_pass')" class="form-control"
                                        />
                                        <span class="text-danger ps-2"><small>{{error['re_enter_new_pass']}}</small></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="mb-2 w-75">
                                        <button type="submit" @click.prevent="Validate($event)" class="button">Gửi</button>
                                    </div>
                                </div>
                            </form>
                            <div class="loading" :class="{'d-block': !loading}">
                                    <div></div>
                                </div>
                        </div>
                    </div>
                    <div class="p-3">
                        <small class="fw-medium">
                            <span class="text-danger"><i class="fa-solid fa-circle-exclamation"></i> Lưu ý:</span>
                            <ul>
                                <li>+ Độ dài ít nhất 8 ký tự.Chứa ít nhất một chữ cái viết thường.</li>
                                <li>+ Chứa ít nhất một chữ cái viết hoa,một số, một kí tự đặc biệt</li>
                            </ul>
                        </small>
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

import userApi from '../../Api/userApi'
import FooterComponentVue from '../FooterComponent.vue'
import NavbarComponent from '../NavbarComponent.vue'

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
            const resetToken = ref(null)
            const password = {
                new_pass: '',
                re_enter_new_pass: '',
            }

            const error = ref([])

            return {
                loading,
                resetToken,
                error,
                password,
            }
        },
        mounted() {
            this.getToken();
        },
        methods: {

            getToken(){
                const url = window.location.href
                const match = url.match(/reset_password\/([^/]+)/)
                if(match){
                    this.resetToken = match[1]
                }
            },

            Validate(value){
                const array = ['new_pass','re_enter_new_pass'];
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

                    console.log(Object.keys(this.error).length)

                    if(Object.keys(this.error).length == 0){
                        this.recover_account()
                    }

                }else{
                    if(this.password[value] != ''){
                        delete this.error[value];
                    }

                }

            },

            async recover_account(){
                
                const attribute = {
                    new_pass: this.password.new_pass,
                }

                console.log(attribute);

                this.loading = !this.loading
                const response = await userApi.reset_password(attribute,this.resetToken)

                if(response.message == 'true' ){
                     this.loading = !this.loading

                    Swal.fire({
                        icon: "success",
                        title: "Thay đổi mật khẩu thành công",
                        showConfirmButton: false,
                        timer: 1500
                    }).then(()=> {
                        this.$router.push('/login');
                    });
                }else{
                     this.loading = !this.loading
                    Swal.fire({
                        icon: "error",
                        title: "Gửi email thất bại, vui lòng xem lại",
                        showConfirmButton: true,
                        timer: 1500
                    }).then(()=> {
                        location.reload()
                    });
                }
            },
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

.recover_pass{
    width: 60%;
    /* height: 615px; */
    height: auto;
    margin: 50px auto;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    background-color: #ffff;
}

.recover_pass .img_quangcao{
    object-fit: cover;
    object-position: center;
    width: 100%;
    text-align: center;
    margin: auto;
}

.recover_pass .img_quangcao{
    width: 100%;
    height: 560px;
}

.recover_pass .img_quangcao .quangcao1{
    width: 100%;
    height: 100%;
    background-image: url('../../assets/image/quangcao.png');
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
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


.loading{
    display: none;
    z-index: 99999;
    position: absolute;
    left: 60%;
    top: 46%;
    margin: auto;
}

.loading div{
    height: 35px;
    width: 35px;
    border: 4px solid #45474b;
    border-top-color: #DCDCDC ;
    border-radius: 50%;
    border-style:dotted ;
    animation: spin 1.5s infinite linear;
    margin: auto;
}

@media only screen and (max-width: 992px) {
    .recover_pass .img_quangcao{
        width: 100%;
        height: 100%;
    }

    .recover_pass .img_quangcao .quangcao1{
        display: none;
    }

    .recover_pass .img_quangcao .img .quangcao2{
        width: 100%;
        display: block;
    }

    .container-fluid{
        padding-left: 0px;
        padding-right: 0px;
    }

    .loading{
        display: none;
        z-index: 99999;
        position: absolute;
        left: 50%;
        top: 74%;
        margin: auto;
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

    .loading{
        display: none;
        z-index: 99999;
        position: absolute;
        left: 50%;
        bottom: 0;
        margin: auto;
    }

    .loading div{
        border: 4px solid #DCDCDC ;
        border-top-color: #45474b;
        border-style: dotted;
    }
}


</style>
