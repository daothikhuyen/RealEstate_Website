// view list post

<template>
    <div class="container-fluid">
        <div class="listPost mt-5">
            <div class="title_main w-100"  style="">
                <h5 class="ps-3 pt-1 text-white">Danh Sách Người Dùng</h5>
            </div>
            <section class="search">
                <div class="search_item d-flex justify-content-end">
                    <div class="">
                        <input type="text" class="border-0" style="outline: none" placeholder="Tìm kiếm theo tên..." @change="searchInput" v-model="textSearch">
                    </div>
                    <button class="btn_search ps-1" @click.prevent="searchInput">
                        <i class="fa-solid fa-magnifying-glass"></i>
                    </button>
                </div>
                 <div class="error pt-2 text-danger" :class="{'d-block': error['search']}">
                    <span>Bạn không được để trống </span>
                 </div>
            </section>

            <section class="list_table">
                <div class="table table-hover">
                    <div class="table_list">
                        <div class="header_list bg-secondary text-center d-flex fw-bold align-content-center">
                            <div class="STT">STT</div>
                            <div class="list_item username">Tên người dùng</div>
                            <div class="list_item email">Email</div>
                            <div class="list_item contact">Liên hệ</div>
                            <div class="list_item box_choose"></div>
                            <div class="list_item box_choose"></div>
                        </div>
                    </div>
                    <div class="table_list" v-if="list_user.length != 0">
                        <div v-for="(attribute,index) in list_user" :key="index"  class="body_list d-flex  align-items-center"  @click="toggleDetails(index)">
                            <div scope="row" class="STT">{{ index+1}}</div>
                            <div class="list_item username">
                                <div class="line-clamp-2 ">
                                    {{  attribute.username }}
                                </div>
                            </div>
                            <div class="list_item email">
                                <div class="line-clamp-2 ">
                                    {{  attribute.email }}
                                </div>
                            </div>
                            <div class="list_item contact">
                                <div class="line-clamp-2 " v-if="attribute.phone != null">
                                    {{ attribute.phone }}
                                </div>
                                 <div class="line-clamp-2 text-secondary cursor-default" v-else>
                                    Chưa cập nhật
                                </div>
                            </div>
                            <div class="list_item line-clamp-2 box_choose">
                                <a v-if="attribute.is_activated == 1" class="ms-1"  @click.prevent="Account_Lock(attribute.id,0)">
                                    <span>Khoá tài khoản </span>
                                </a>
                                <a v-if="attribute.is_activated == 0" class="ms-1"  @click.prevent="Account_Lock(attribute.id,1)">
                                    <span class="text-secondary">Đã bị khoá</span>
                                </a>
                            </div>
                            <div class="list_item line-clamp-2 box_choose">
                                <a class="ms-1"  @click.prevent="deleteAccount(attribute.id)">
                                    <span>Xoá tài khoản</span>
                                </a>
                            </div>
                            <div class="choose_small">
                                <div class="box_small list_item text-center" :class="{'d-block': box_choose[index]}">
                                    <div class="">
                                        <a v-if="attribute.is_activated == 1" class="btn delete_btn" @click.prevent="Account_Lock(attribute.id,0)">
                                            <i class="fa-solid fa-lock-open"></i>
                                            <span>Khoá tài khoản</span>
                                        </a>
                                        <a v-else class="btn delete_btn" @click.prevent="Account_Lock(attribute.id,1)">
                                            <i class="fa-solid fa-lock text-secondary"></i>
                                            <span class="text-secondary">Đã bị khoá</span>
                                        </a>
                                    </div>
                                    <div>
                                        <a class="btn delete_btn"  @click.prevent="deleteAccount(attribute.id)">
                                            <i class="fa-solid fa-trash"></i>
                                            <span>Xoá tài khoản</span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="choose_small">
                                <div class="overlay" @click="delete box_choose[index].value" :class="{'d-block': box_choose[index]}" id="overlay"></div>
                            </div>
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
import adminApi from '../../../Api/adminApi'

import Sidebar from '../Sidebar.vue'

export default defineComponent({
    name: "User Management",
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
        const list_user = ref([])
        const textSearch = ref(null)
        const box_choose = ref([])
        const attribute = {
            price: ""
        }

        return {
            error,
            currentPage,
            textSearch,
            list_user,
            attribute,
            box_choose
        }
    },
    methods : {

        toggleDetails(index){
           this.box_choose[index] = !this.box_choose[index];
        },

        async searchInput(page = 1){

            if(this.textSearch == null || this.textSearch == "") {
                this.error['search'] = 'Vui lòng không để trống'
            }else{
                delete this.error['search']
            }

            if(Object.keys(this.error).length == 0){
                const response =  await adminApi.searchInput(this.textSearch,page);

                if(response){
                    this.currentPage = response.data
                    this.list_user = response.data.data
                }
            }
        },

        async deleteAccount(id){

            if(confirm('Xoá tài khoản mà không khôi phục?')){

                const result = await adminApi.deleteAccount(id);
                if(result.code == 1000){
                     this.load_My_Posts()
                }else{
                    this.error['error'] = "Đã xảy ra lỗi, vui lòng kiểm tra lại"
                }
            }

        },

        async Account_Lock(id,is_activated){

            let text = ""
            if(is_activated == 0) text = "Khoá tài khoản người dùng?";
            if(is_activated == 1) text = "Khôi phục tài khoản bị khoá?";

            if(confirm(text)){
                const data = {
                    is_activated : is_activated
                }
                const result = await adminApi.account_lock(id,data)

                if(result.code == 1000){
                    this.load_My_Posts()
                }else{
                    Swal.fire({
                        title: "Thông Báo!",
                        text: "Khoá Tài Khoản bị lỗi",
                        icon: "info",
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                          location.reload()
                    });
                }
            }

        },

        async load_My_Posts(page = 1){
            const response =  await adminApi.list_user(page);
            if(response.code == 1000){
                this.currentPage = response.result.paginate
                this.list_user = response.result.data
            }

        },

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
        cursor: default;
    }

    .table .table_list .body_list{
        border-bottom:1px solid #DCDCDC ;
        padding: 5px 0px;
        font-size: 15px;
    }

    /* .table .table_list .body_list:hover{
        background-color: rgb(220, 220, 220,0.2);
    } */

    .table .table_list .STT{
        width: 5%;
        text-align: center;
        overflow: hidden;
    }

    .table .table_list .list_item.username,
    .table .table_list .list_item.email
    {
        width: 30%;
        text-align: start;
        overflow: hidden;
    }

    .table .table_list .list_item.contact{
        width: 20%;
    }

    .table .table_list .list_item.box_choose{
        width: 15%;
        font-size: 15px;
        color: #DCDCDC;
    }

    .table .table_list .list_item.box_choose a{
        color: #102447;
        cursor: pointer;
    }

    .table .table_list .list_item.box_choose:hover a{
        color: #ff5d26;
    }

    .table .table_list .body_list > div .line-clamp-2 {
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    /* search */
    .listPost .search{
        position: relative;
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

    .error {
        display: none;
        background-color: transparent;
        width: 250px;
        border-radius: 8px;
        position: fixed;
        margin: 0 auto;
        transition: all .3s ease-in-out;
    }

    .choose_small{
        display: none;
    }

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

    @media only screen and (max-width: 1240px) {
        .table .table_list .list_item.box_choose a:nth-child(1){
            font-size: 14px;
        }
    }

    @media only screen and (max-width: 992px) {
        .table .table_list .list_item.box_choose{
            width: 20%;
        }

        .table .table_list .list_item.box_choose a:nth-child(1){
            font-size: 12px;
        }

        .listPost .list_table,
        .container-fluid{
            padding-left: 0px;
            padding-right: 0px;
        }

        .listPost .title_main {
            border-radius:0px ;
        }
    }

    @media only screen and (max-width: 768px) {
        .table .table_list .list_item.box_choose{
            display: none;
        }

        .table .table_list .list_item.username,
        .table .table_list .list_item.email{
            width: 35%;
        }

        .choose_small{
            display: block;
        }
    }

</style>
