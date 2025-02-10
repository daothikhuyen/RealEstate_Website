<template>
    <div class="ViewPost container-fluid" v-if="authUser">
        <div class="bg-white mb-5">
            <div class="">
                <div class="viewPostItems">
                    <div class="title_main w-100"  style="">
                        <h5 class="ps-3 pt-1 text-white">Thông Tin Cá Nhân</h5>
                    </div>
                    <div>
                        <div class="profile_account row  align-items-center m-auto" v-if="authUser">
                            <div class="col-lg-4 col-md-4 col-sm-12 border_avatar">
                                <div class="account_avatar">
                                    <input type="hidden" name="id">
                                    <div class="upload">
                                        <div>
                                            <img v-if="authUser.avatar!=null"  :src="`http://localhost:8001${authUser.avatar}`" id="image" alt="">
                                            <img v-else src="../../../../assets/image/avatar/profile.jpg" alt="">
                                        </div>
                                        <div class="rightRound" id="upload">
                                            <input type="file" name="file" class="file" ref="fileInput" id="fileImage" @change="UploadAvatar" accept=".jpg,.jpeg,.png">
                                            <i class="fa fa-solid fa-camera"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-7 col-md-8 col-sm-12">
                                <div class="info_account">
                                    <div class="title text-black">
                                        <h5>Chỉnh Sửa Thông Tin</h5>
                                    </div>
                                    <Form class="mt-5" @submit="Update_Profile" :validation-schema="schema" v-slot="{ errors }" >
                                        <div class="form-group">
                                            <div class="input_group mb-3">
                                                <label for="username" class="">Tên tài khoản</label>
                                                <div class="w-100">
                                                    <Field type="text" name="username" id="username" class="form-control" v-model="authUser.username" :class="{'is-invalid': errors.username}"/>
                                                    <span class="invalid-feedback">{{ errors.username }}</span>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <div class="input_group mb-3 ">
                                                <label for="email" class="">Email</label>
                                                <div class="w-100">
                                                    <Field type="email" name="email" id="email" class="form-control" readonly v-model="authUser.email" :class="{'is-invalid': errors.email}"/>
                                                    <span class="invalid-feedback">{{ errors.email }}</span>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <div class="input_group mb-3">
                                                <label for="phone" class="">Số điện thoại</label>
                                                <div class="w-100">
                                                    <Field type="text" name="phone" id="phone" class="form-control" v-model="authUser.phone" :class="{'is-invalid': errors.phone}"/>
                                                    <span class="invalid-feedback">{{ errors.phone }}</span>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group text-center">
                                            <button type="submit" class="form-control btn_update">
                                            <i class="fa-solid fa-check"></i> Xác Nhận
                                            </button>
                                        </div>
                                    </Form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>


<script>
import { ref,defineComponent } from 'vue'
import { Form, Field } from 'vee-validate';
import * as yup from 'yup';
import Swal from 'sweetalert2'
import {mapState,mapGetters,mapActions} from 'vuex'
import axios from "axios";

import userApi from '../../../../Api/userApi'

export default defineComponent({
    components: {
        Form,
        Field,
    },
    computed: {
        ...mapGetters(['authUser'])
    },
    data(){

        const img_avatar = ref('../../../../assets/image/avatar/profile.jpg')

        const phoneRegExp = /^((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?$/
        const schema = yup.object().shape({
            username: yup.string().required('Vui lòng nhập thông tin'),
            email: yup.string().required('Vui lòng nhập thông tin').email('Email không đúng định dạng'),
            phone: yup.string().matches(phoneRegExp, 'Số điện thoại không hợp lệ'),
        })

        return {
            schema,
            img_avatar,
        }

    },
    watch: {
        authUser(value) {
            if(value.avatar != null){
                this.authUser.avatar = value.avatar
            }
            if(this.authUser.phone == null){
                this.authUser.phone = "(+84)"
            }
        },

    },
    methods: {

        UploadAvatar(event){
            const files_loading = event.target.files

            if(files_loading.length == 0) return

            for (let i = 0; i < files_loading.length; i++) {
                const file = files_loading[i]
                const fileName = (file.name.length >= 12) ? file.name.substring(0,13) + '....'+ file.name.split('.')[1]: file.name

                const formData = new FormData()
                formData.append("file", file)


                axios.post('http://localhost:8001/storage/fileSystem', formData)
                .then((result) => {
                    this.authUser.avatar = result.data.result.file

                })
                .catch(error => {
                    alert("Đã Xảy Ra lỗi")
                    console.error(error);
                })
            }
        },

        async Update_Profile(values){
            const user = {
                email : values.email,
                username : values.username,
                phone: values.phone,
                avatar: this.authUser.avatar,
            }

            const response = await userApi.update_personalInfo(user,this.authUser.id)

            if(response.code == 1000){
                Swal.fire({
                    icon: "success",
                    title: "Thay đổi thông tin thành công",
                    showConfirmButton: false,
                    timer: 1500
                }).then(()=> {
                    this.$store.dispatch('getUser');
                });
            }else{
                Swal.fire({
                    icon: "error",
                    title: "Thay đổi thông tin thất bại",
                    showConfirmButton: false,
                    timer: 1500
                })
            }
        },
    },
    created(){
    }
})
</script>

<style scoped>

    .ViewPost{
        margin-top: 50px;
        padding: 0;
        padding: 0 5rem;
    }

    .ViewPost .viewPostItems{
        border: 1px solid #DCDCDC;
    }

    .ViewPost div{
        border-top-right-radius: 1rem ;
        border-top-left-radius: 1rem ;
    }

    .ViewPost .title_main{
        background-color:#ff5d26;
        padding: 6px 3px ;
        border-top-right-radius: 1rem ;
        border-top-left-radius: 1rem ;
    }

    .ViewPost span{
        font-size: 15px;
    }

    .ViewPost .title{
        color: var(--primary-color);
        font-weight: 600;
    }

    /* view avatar */

    .ViewPost .profile_account .account_avatar {
        /* border: 2px solid rgb(0, 0, 0,0.5); */
        margin-top: 20px;
        margin: 40px;
        padding: 50px 0;
        border-top-right-radius: 0rem ;
        border-top-left-radius: 0rem ;
        /* border-style:dashed; */
        background-color: rgb(220, 220, 220,0.05);
    }

    .upload{
        position: relative;
        width: 145px;
        margin: auto;
    }

    .upload img{
        border-radius:50%;
        /* border: 1px solid #DCDCDC; */
        width: 150px;
        height: 150px;
        object-position: center;
        /* object-fit: fill; */
        object-fit: cover;
    }

    .upload .rightRound{
        position: absolute;
        bottom: 0;
        right: 0;
        background: #d8d1cc;
        width: 34px;
        height: 34px;
        line-height: 33px;
        text-align: center;
        border-radius:50%;
        overflow: hidden;
        cursor: pointer;
    }

    .upload .fa{
        color: #ffff;
    }

    .upload input{
        position: absolute;
        transform: scale(2);
        opacity: 0;
    }

    .profile_account .info_account{
        margin-top: 30px;
    }

    Form .form-group label{
        width: 250px;
    }

    Form .form-group button{
        display: initial;
        width: auto;
        text-align: center;
        background-color: #123b83;
        border: 1px solid #DCDCDC;
        color: white;
    }

    Form .form-group #email:hover{
        outline: none;
        border: 1px solid #DCDCDC;
        cursor: no-drop;
    }

    Form .form-group #email:focus{
        border: 1px solid #DCDCDC;
        box-shadow: none;
    }

    Form .form-group .input_group{
        display: flex;
        justify-content: center;
        align-items: center;
    }

    @media only screen and (max-width: 992px) {
        .ViewPost{
            padding: 0px 0px 50px 0px;
        }

        .ViewPost .viewPostItems{
            border:none;
        }

        .ViewPost .title_main {
            background-color: #ff5d26;
            padding: 6px 3px;
            border-radius: 0px;
        }

        .ViewPost .title_main {
            background-color: #ff5d26;
            padding: 6px 3px;
            border-radius:0px;
        }

        .ViewPost .info_account{
            padding: 0px 10px 10px;
        }
    }

    @media only screen and (max-width: 768px) {
        .border_avatar{
            text-align: center;
            margin: auto;
        }

        .account_avatar {

        }

        .upload img{
            border: none;
            width: 120px;
            height: 120px;
            object-fit: cover;
        }

        .upload .rightRound{
            right: 10px;
        }

        Form .form-group .input_group{
            display: block;
        }

        Form .form-group .input_group label{
            padding-left: 5px;
            padding-bottom: 5px;
            /* font-size: 15px; */
            font-weight: 500;
        }

    }

</style>

