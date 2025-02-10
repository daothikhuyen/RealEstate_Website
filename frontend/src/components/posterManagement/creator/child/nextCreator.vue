// view wirte post

<template>
    <div class="ViewPost bg-white container-fluid">
        <div class="bg-white mb-5">
            <div class="">
                <div class="border viewPostItems">
                    <div class="title_main w-100"  style="">
                        <h5 class="ps-3 pt-1 text-white">Đăng Tin Mới</h5>
                    </div>
                    <div class="interfacePost">
                        <div class="">
                            <section class="seeBefore m-2">
                                <div class="seeItem" v-if="demo != null">
                                    <div class="post px-2 py-3 bg-white mb-2">
                                        <Poster :user="demo.user"></Poster>
                                        <PostContent :postData="demo.postData" :images="demo.images" :extensions="demo.extensions" :videos="demo.videos"></PostContent>
                                    </div>
                                </div>
                                <div class="seeItem" v-else>
                                </div>
                            </section>

                        </div>
                    </div>
                    <div class="btn_submit h-auto">
                        <a href="listpost/see" @click.prevent="insertPost" class="button float-end">Đăng Bài</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, defineComponent } from 'vue'
import {mapGetters,mapState,mapActions} from 'vuex'
import Swal from 'sweetalert2'
import debounce from 'lodash.debounce';

import Poster from "../../../post/Poster.vue"
import PostContent from "../../../post/Content.vue"
import Comment from '../../../post/comment/index.vue'
import postApi from '../../../../Api/postApi'

export default defineComponent({
    name: "HomeManager",
    components: {
        Poster,
        PostContent,
        Comment,
    },
    computed: {
        ...mapGetters(['newPost'])
    },
    props:{
        nextScreenCreator: {
            type: Object,
            default: {}
        }
    },
    mounted(){
        // console.log(this.newPosts);
        this.checkDemoPost(this.newPosts)
    },
    data() {
        const demo = ref(null)
        const check = ref(false)
        return {
            demo,
            check
        }
    },
    methods: {

        formatPrice(price){
            // const number = Number(price)
            return Number(price).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
        },

        insertPost: debounce( async function(){

            const jsonData = this.$route.params.data;
            const data = JSON.parse(jsonData);
            console.log(data);

            const result = await postApi.createPost(data)
            if(result.code == 1000){
                    Swal.fire({
                    title: "Thông Báo!",
                    text: "Đăng Bài Thành Công",
                    icon: "success",
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    this.$router.push('/home')
                });
            }else{
                Swal.fire({
                        title: "Thông Báo!",
                        text: "Đăng Bài Đã Xảy Ra Lỗi",
                        showConfirmButton: false,
                        icon: "error",
                        timer: 1500
                    })
                console.error(error);
            }
        },1000),

        checkDemoPost(){
            const jsonData = this.$route.params.data;
            const data = JSON.parse(jsonData);

            this.demo = data
            console.log(this.demo.postData)
        }
    },
    created() {

    }
})
</script>

<style scoped>

    .homeManager{
        display: flex;
    }

    .homeManager .ViewPost{
        padding-top: 50px;
        margin:30px;
        position: relative;
    }

    .homeManager .ViewPost span{
        font-size: 15px;
    }

    .homeManager .ViewPost .seeBefore .post{
        box-shadow: none;
    }

    .homeManager .ViewPost .title{
        color: var(--primary-color);
        font-weight: 600;
    }

    .homeManager .ViewPost .info .post_title{
        font-size: 14px;
        font-weight: 700;
        margin-bottom: 8px;
        margin-top: 15px ;
        padding-left: 5px;
    }

    .homeManager .ViewPost .info .info-item{
        margin-top: 20px;
    }

    .homeManager .ViewPost .info .Address_detail select{
        width: 100%;
        padding: 5px;
        border: 1px solid rgb(162, 160, 160,0.5);
        border-radius: 2px;
    }

    .homeManager .ViewPost .info .input_number_fulladdress .input_item,
    .homeManager .ViewPost .info .info_detail .input_item{
        width: 100%;
        padding: 5px;
        border: 1px solid rgb(162, 160, 160,0.5);
        border-radius: 2px;
    }

    .homeManager .ViewPost .info .info_detail .input_special .input_item{
        border: none;
        outline: none;
        width: 90%;
    }

    .homeManager .ViewPost .info .info_detail .input_special div {
        background-color: rgb(162, 160, 160,0.5);
        height: auto;
        padding: 4.6px 8px;
    }

    .homeManager .ViewPost .info .info_detail .input_special{
        width: 100%;
        min-width: auto;
        border: 1px solid rgb(162, 160, 160,0.5);
        border-radius: 2px;
        overflow: hidden;
    }

    .homeManager .ViewPost .btn_submit{
        margin: auto;
        text-align: center;
    }

    .homeManager .ViewPost .btn_submit .button{
        width: 15%;
        background-color: var(--primary-color);
        padding: 8px;
        margin: 10px;
        font-size: 18px;
        font-weight: 500;
        letter-spacing: 1px;
        border-radius: 4px;
        text-align: center;
        color: #fff;
    }

    .homeManager .ViewPost .note_post{
        padding: 10px;
        margin: 20px;
        background-color: #FFEC8B;
        color: #A0522D;
        font-size: 15px;
    }

    .homeManager .ViewPost .note_post ul li {
        list-style: square;
        font-size: 14px;
        line-height: 25px;
    }

    .homeManager .ViewPost .note_post h3{
        font-size: 18px;
    }

        /* giao diện bài đăng xem trước */
    .interfacePost{
        width: 70%;
        margin: auto;
        /* border: 1px solid black; */
        box-shadow: rgba(0, 0, 0, 0.02) 0px 1px 3px 0px, rgba(27, 31, 35, 0.15) 0px 0px 0px 1px;
        border-radius: 3px;
        margin-top: 20px;
    }

    @media (max-width:992px) {
        .interfacePost{
            width: 100%;
            border: none;
            box-shadow: none;
        }
    }


</style>
