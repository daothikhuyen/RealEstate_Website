// view wirte post

<template>
    <div class="ViewPost container-fluid bg-white">
        <div class="bg-white mb-5">
            <div class="">
                <div class="border viewPostItems">
                    <div class="title_main w-100"  style="">
                        <h5 class="ps-3 pt-1 text-white">Xem Chi Tiết</h5>
                    </div>
                    <div class="interfacePost">
                        <div class="">
                            <section class="seeBefore m-2">
                                <div class="seeItem" v-if="show_posts != null">
                                    <div class="post px-2 py-3 bg-white mb-2">
                                        <Poster :user="show_posts.user"></Poster>
                                        <PostContent :postData="show_posts.postData" :images="show_posts.images" :extensions="show_posts.extensions" :videos="show_posts.videos"></PostContent>
                                    </div>
                                </div>
                                <div class="seeItem" v-else>
                                </div>
                            </section>

                        </div>
                    </div>
                    <div class="btn_submit">
                        <a href="#" @click.prevent="destroyPost" class="button">Xoá Bài</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="loading_destroy">
        <div :class="{'d-block': loading}"></div>
    </div>
</template>

<script>
import { ref, defineComponent } from 'vue'
import {mapGetters,mapState,mapActions} from 'vuex'
import Swal from 'sweetalert2'

import Poster from "../../post/Poster.vue"
import PostContent from "../../post/Content.vue"
import Comment from '../../post/comment/index.vue'
import adminApi from '../../../Api/adminApi'

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
    data() {
        const show_posts = ref(null)
        const loading = ref(false)
        return {
            show_posts,
            loading
        }
    },
    methods: {

        formatPrice(price){
            return Number(price).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
        },

        async destroyPost(){
            if(confirm('Xoá bài viết. Xoá Không Khôi Phục ?')){
                this.loading = !this.loading
                const result = await adminApi.destroy_posts(this.$route.params.id);
                this.loading = !this.loading
                if(result.code == 1000){
                    Swal.fire({
                        title: "Thông Báo!",
                        text: "Xoá bài viết thành công",
                        icon: "success",
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        this.$router.push({ path: '/postManagement/admin/list_posts_user' })
                    });

                }else{
                    Swal.fire({
                        title: "Thông Báo!",
                        text: "Xoá bài viết thất bại",
                        icon: "error",
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                          location.reload()
                    });
                }
            }
        },

        async loadPosts(){
            this.show_posts = (await adminApi.posts_detail(this.$route.params.id)).result;
        }
    },
    async created() {
        this.show_posts = (await adminApi.posts_detail(this.$route.params.id)).result;
    }
})
</script>

<style scoped>
    .homeManager{
        display: flex;
    }

    .homeManager .ViewPost{
        padding-top: 50px;
        /* margin:30px; */
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
        text-align: end;
        margin: 20px 0px;
    }

    .homeManager .ViewPost .btn_submit .button{
        width: 15%;
        background-color: var(--primary-color);
        padding: 8px 20px;
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

    .loading_destroy div {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        z-index: 99999;
        background-color: transparent;
        overflow: hidden;
        height: 35px;
        width: 35px;
        margin: auto;
        border: 4px solid #45474b;
        border-top-color: #ffff ;
        border-style: dotted;
        border-radius: 50%;
        animation: spin 1.5s infinite linear;
    }

    .interfacePost{
        width: 70%;
        margin: auto;
        /* border: 1px solid black; */
        box-shadow: rgba(0, 0, 0, 0.02) 0px 1px 3px 0px, rgba(27, 31, 35, 0.15) 0px 0px 0px 1px;
        border-radius: 3px;
        margin-top: 20px;
    }

    @media (max-width: 768px) {
        .interfacePost{
            width: 100%;
            border: none;
            box-shadow: none;
        }
    }


</style>
