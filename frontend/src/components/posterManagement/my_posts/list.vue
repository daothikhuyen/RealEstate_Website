// view list post

<template>
    <div class="container-fluid">
        <div class="listPost mt-5">
            <div class="title_main w-100"  style="">
                <h5 class="ps-3 pt-1 text-white">Danh Sách</h5>
            </div>
            <section class="search">
                <div class="search_item d-flex justify-content-end">
                    <div class="">
                        <input type="text" class="border-0" style="outline: none" placeholder="Tìm kiếm..." @change="searchInput" v-model="textSearch">
                    </div>
                    <button class="btn_search ps-1" @click.prevent="searchInput">
                        <i class="fa-solid fa-magnifying-glass"></i>
                    </button>
                </div>
            </section>
            <section class="list_table">
                <div class="table table-hover">
                    <div class="table_list">
                        <div class="header_list bg-secondary text-center d-flex fw-bold align-content-center">
                            <div class="STT">STT</div>
                            <div class="Title">Tiêu đề</div>
                            <div class="Price text-start">Giá phòng</div>
                            <div class="Acrege">Diện tích</div>
                            <div class="Adress delete"><div>Địa chỉ</div></div>
                            <div class="box_choose">&nbsp;</div>
                        </div>
                    </div>
                    <div class="table_list" v-if="my_posts.length != 0">
                        <div v-for="(attribute,index) in my_posts" :key="index" class="body_list d-flex  align-items-center"   @click="toggleDetails(index)">
                            <div scope="row" class="list_item STT">{{ index+1}}</div>
                            <div class="list_item Title">
                                <div class="line-clamp-2 ">
                                    {{ attribute.title }}
                                </div>
                            </div>
                            <div class="list_item Price" id="Price">
                                {{formatPrice(attribute.price)}}
                            </div>
                            <div class="list_item Acrege ">{{ attribute.area }} m<sup>2</sup></div>
                            <div class="list_item Adress">
                                <div class="line-clamp-2 ">
                                    {{ attribute.full_address }}
                                </div>
                            </div>
                            <div class="list_item text-center box_choose">
                                <router-link class="btn btn-primary btn-sm" :to="'edit_post/'+attribute.id">
                                    <i class="fa-solid fa-pen-to-square"></i>
                                </router-link>
                                <a class="btn btn-danger btn-sm ms-1"  @click.prevent="deletePosts(attribute.id)">
                                    <i class="fa-solid fa-trash"></i>
                                </a>
                            </div>
                            <div class="choose_small">
                                <div class="box_small list_item text-center" :class="{'d-block': box_choose[index]}">
                                    <div>
                                        <router-link class="btn edit_btn" :to="'edit_post/'+attribute.id">
                                            <i class="fa-solid fa-pen-to-square"></i>
                                            <span>Chỉnh Sửa</span>
                                        </router-link>
                                    </div>
                                    <div>
                                        <a class="btn delete_btn"  @click.prevent="deletePosts(attribute.id)">
                                            <i class="fa-solid fa-trash"></i>
                                            <span>Xoá</span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="overlay" @click="delete box_choose[index].value" :class="{'d-block': box_choose[index]}" id="overlay"></div>
                        </div>
                    </div>
                    <div v-else>
                        <div>
                            <div class="text-center border-bottom">
                                <span class="text-center">
                                    Rất tiếc, dữ liệu không tồn tại
                                </span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Pagination -->
                <div class="d-flex justify-content-center">
                    <Bootstrap5Pagination :data="currentPage" @pagination-change-page="load_My_Posts"/>
                </div>
            </section>
        </div>
    </div>
</template>

<script>
import { ref, defineComponent } from 'vue'
import { Bootstrap5Pagination } from 'laravel-vue-pagination';
import {mapState,mapGetters,mapActions} from 'vuex'
import Swal from 'sweetalert2'
import postApi from '../../../Api/postApi'

import Sidebar from '../Sidebar.vue'

export default defineComponent({
    name: "HomeManager",
    computed:{
        ...mapGetters(['authUser']),
    },
    components: {
        Sidebar,
        Bootstrap5Pagination
    },
    data() {
        const error = ref([])
        const currentPage = ref([])
        const my_posts = ref([])
        const textSearch = ref(null)
        const box_choose = ref([])
        const attribute = {
            price: ""
        }

        return {
            currentPage,
            textSearch,
            my_posts,
            attribute,
            box_choose
        }
    },
    methods : {

        toggleDetails(index){
            this.box_choose[index] = !this.box_choose[index]
        },

        async searchInput(page = 1){

            const response =  await postApi.searchInput(this.textSearch);

            if(response){
                this.currentPage = response.data
                this.my_posts = response.data.data
            }
        },

        formatPrice(price){
            return price.toLocaleString('vi', {style : 'currency', currency : 'VND'})
        },

        async load_My_Posts(page = 1){

            const response =  await postApi.getMyPosts(page);

            if(response.code == 1000){

                const res = response.result

                this.currentPage = res.paginate
                this.my_posts = res.data
            }

        },

        async deletePosts($id){

            const value = {
                "id" : $id
            }

            if(confirm('Xoá bài viết. Xoá Không Khôi Phục ?')){

            const result = await postApi.deletePosts($id);
                if(result){
                    Swal.fire({
                        title: "Thông Báo!",
                        text: "Xoá bài viết thành công",
                        icon: "success",
                          showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                          location.reload()
                    });
                }else{
                    this.error['error'] = "Đã xảy ra lỗi, vui lòng kiểm tra lại"
                }
            }

        }
    },

    async created(){
        this.load_My_Posts()
    }
})
</script>

<style scoped>

    .listPost{
        background-color: #ffff;
    }

    .table>:not(:last-child)>:last-child>*{
        font-size: 15px;
    }

    .listPost .title_main{
        background-color:#ff5d26;
        padding: 6px 3px ;
        border-radius:8px ;
        /* border-top-right-radius: 1rem ;
        border-top-left-radius: 1rem ; */

    }

    .listPost .list_table{
        padding: 1rem;
        margin-top: 4rem;

    }

    .table .table_list  {
        cursor: pointer;
    }

    .table .table_list .body_list{
        border-bottom:1px solid #DCDCDC ;
    }

    .table .table_list .body_list:hover{
        background-color: rgb(220, 220, 220,0.2);
     }

    .table .table_list .STT{
        width: 5%;
        text-align: center;
        overflow: hidden;
    }

    .table .table_list .Adress,
    .table .table_list .Title{
        width: 30%;
        cursor: pointer;
        font-size: 15px;
        overflow: hidden;
    }

    .table .table_list .Acrege,
    .table .table_list .Price{
        width: 15%;
        text-align: center;
        cursor: pointer;
        overflow: hidden;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 1;
        display: -webkit-box;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .table_list .Price input[type="text"]{
        background-color: transparent;
        outline: none;
        cursor: pointer;
        /* text-align: center; */
    }

    .table .table_list .box_choose{
        width: 10%;
        cursor: pointer;
        overflow: hidden;
    }

    .table .btn_delete,
    .table .btn_detail{
        padding: 5px 45px;
        margin: 0 5px;
    }

    .table .table_list .body_list > div .line-clamp-2 {
        max-height: 3.5rem;
        overflow: hidden;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        display: -webkit-box;

    }

    /* search */

    .listPost .search{
        float: right;
        margin-top: 20px;
        margin-right: 1rem ;
        padding: 5px;
        width: auto;
        border: 1px solid rgb(162, 160, 160,0.5);
        border-radius:.25rem ;
    }

    .listPost .search .search_item div:nth-child(1) input[type = 'text']{
        width: 300px;
    }

    .choose_small{
        display: none;
    }

    .box_small {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: #fff;
        width: 300px;
        height: 100px;
        margin: auto;
        z-index: 9999;
        box-shadow: rgba(67, 71, 85, 0.27) 0px 0px 0.25em, rgba(90, 125, 188, 0.05) 0px 0.25em 1em;
        border-radius: 8px;
        transition: all .3s ease-in-out;
    }

    .box_small .btn {
        /* float: left; */
        width: 100%;
        text-align: start;
        padding: 10px 10px;
        border-bottom:1px solid #DCDCDC ;
    }

    .box_small .btn span {
        padding: 5px 10px;
        font-size: 16px;
        font-family: "Lexend Medium", Roboto, Arial !important;
    }

    .box_small .btn:hover{
        background-color: rgb(220, 220, 220,0.5);
    }

    @media only screen and (max-width: 992px) {

        .table .table_list > div {
            font-size: 14px;
        }

        .table .table_list .box_choose{
            width: 15%;
        }
    }

     @media only screen and (max-width: 768px) {
        .table .table_list .Adress{
            display: none;
        }

        .table .table_list .Title{
            width: 40%;
        }

        .table .table_list .box_choose,
        .table .table_list .Acrege,
        .table .table_list .Price{
            width: 20%;
        }

     }

    @media only screen and (max-width: 600px) {
        .container-fluid{
            padding-left:0px ;
            padding-right: 0px;
        }

        .listPost .title_main{
            border-radius: 0px;
        }

        .table .table_list .Acrege{
            display: none;
        }

        .table .table_list .Adress{
            display: none;
        }

        .table .table_list .Title{
            width: 50%;
            font-size: 12px;
        }

        .table .table_list .box_choose,
        .table .table_list .Price{
            width: 20%;
            font-size: 13px;
        }

        .table .table_list .STT{
            width: 10%;
            font-size: 13px;
        }

        .btn-group-sm>.btn, .btn-sm {
            font-size: .752rem;
        }

        .listPost .search .search_item div:nth-child(1) input[type = 'text']{
            width: 200px;
        }
    }

    @media only screen and (max-width: 500px) {
        .overlay {
            position: fixed;
            width: 100vw;
            height: 100vh;
            background-color: black;
            top: 0;
            left: 0;
            opacity: 0.5;
            z-index: 9998;
            display: none;
            transition: all .3s ease-in-out;
        }

        .choose_small{
            display: block;
        }

        .table .table_list .box_choose{
            display: none;
        }

        .table .table_list .Title{
            width: 55%;
            font-size: 13px;
        }

        .table .table_list .Price{
            width: 35%;

        }

        .table .table_list .STT{
            width: 10%;
            font-size: 13px;
        }

        .listPost .list_table{
            padding: 0px;
        }
    }

</style>
