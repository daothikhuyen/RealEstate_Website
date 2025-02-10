<template>
    <div class=" container-fluid">
        <div class="view_profile">
            <div class=" row">
                <div class="col-12">
                    <div class="title_main w-100"  style="">
                        <h5 class="ps-3 pt-1 text-white">Quản Lí Tài Khoản</h5>
                    </div>
                    <router-view></router-view>
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

import userApi from '../../../Api/userApi'

export default defineComponent({
    components: {
        Form,
        Field,
    },
    computed: {
        ...mapGetters(['authUser'])
    },
    data(){
        const img_avatar = ref("/storage/uploads/2024/06/04/profile.jpg")

        return {
            img_avatar
        }
    },
    watch: {
        authUser(value) {
            if(value.avatar != null){
                this.img_avatar = value.avatar
            }
        }
    },
    methods: {
        UploadAvatar(event){
            const file = event.target.files
            if(file.length == 0) return

            const formData = new FormData()
            formData.append("file", file[0])
            console.log(formData);

            axios.post('/api/upload-file', formData)
            .then((result) => {

                if(result.url != ""){
                    this.img_avatar = result.data.url

                }
            })



        }
    }
})
</script>

<style scoped>

    .view_profile{
        background-color: #ffff;
        min-height: 500px;
        margin-top: 50px;
        border-radius:1rem ;
        margin-left: 40px;
        margin-right: 40px;
        margin-bottom: 40px;
    }

    .view_profile .title_main{
        background-color:#ff5d26;
        padding: 6px 3px ;
        border-top-right-radius: 1rem ;
        border-top-left-radius: 1rem ;

    }


</style>
