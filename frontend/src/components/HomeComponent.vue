// HomeComponent.vue

<template>
    <div class="HomeComponent">
        <navbar-component></navbar-component>
        <div class="view_home container-fluid mt-3" style="">
            <div class="row mx-auto">
                <div class="col-lg-2 advertise">
                    <div class="advertisement">
                        <img class="" src="../assets/image/advertisement/advertisement_01.jpg" alt="">
                    </div>
                </div>
                <div class="col-lg-8 col-md-12 col-sm-12 view_main mt-3 ">
                    <form action="" @submit.prevent="searchInput" class="position-relative">
                        <div class="parent_header_search">
                            <div class="header_search">
                                <div class="searchLocation" @click.prevent="getLocationByParent_id">
                                    <div class="listsearch">
                                        <i class="bi bi-geo-alt icon_location" ></i>
                                        <div class="name_search">{{nameLocation!=null?nameLocation:'Toàn Quốc'}}</div>
                                        <i class="fa-solid fa-chevron-down"></i>
                                        <span class="postfix-div"></span>
                                    </div>
                                </div>
                                <button type="submit" class="border-0 outline-none cursor-pointer" @click.prevent="searchInput">
                                    <i class="bi bi-search"></i>
                                </button>
                                <div class="LocationInputSearch">
                                    <input type="text" class="input_search" v-model="inputSearch" @input="SearchSuggestions" placeholder="Nhập để tìm kiếm">
                                </div>
                                <button type="submit" class="btnSearch" @click.prevent="searchInput">Tìm Kiếm</button>
                            </div>
                        </div>
                        <div class="listItemProps" :class="{'show': inputSearch != '' || inputSearch == null}">
                            <div class="py-2">
                                <ul class="ul_listItem px-0">
                                    <div class="conntent_search">
                                        Tìm kiếm từ khoá "{{inputSearch}}"
                                    </div>
                                    <li class="li_listItem" v-for="item in list_searchSeggestion.data" :key="item.id" @click.prevent="inputSearch = item.title; searchInput()">
                                        <span>{{item.title}}</span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </form>

                    <div class="main_home py-md-3 mt-3">
                        <div class="row">
                            <div class="the_left col-md-4 float-md-start d-sm-block d-md-none">
                                <div class="justify-content-between">
                                    <div class="search-price w-100 me-1">
                                        <div class="d-flex justify-content-between align-items-center" @click.prevent="dropdownBoxSearch('price')">
                                            <h6>Lọc Theo Giá</h6>
                                            <span >
                                                <i class="fa-solid fa-chevron-up"></i>
                                            </span>
                                        </div>
                                    </div>

                                    <div class="search-acreage w-100 ms-1">
                                        <div class="d-flex justify-content-between align-items-center" @click.prevent="dropdownBoxSearch('acreage')">
                                            <h6>Lọc Theo Diện Tích</h6>
                                            <span >
                                                <i class="fa-solid fa-chevron-up"></i>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-8 postcomponet">
                                <PostComponent :posts="posts"></PostComponent>
                            </div>
                            <div class="the_right col-md-4 float-md-start d-sm-none d-md-block">
                                <div class="search-price w-100">
                                    <h6>Lọc Theo Giá Thuê</h6>
                                    <ul class="list-group list-group-search list-group-flush">
                                        <li v-for="price in romePrice" :key="price.id" class="list-group-item list-group-item-action" @click.prevent="getBySearch('price',price.on,price.under)">
                                            <a href="#" class="text-decoration-none">Giá từ {{price.title}}</a>
                                        </li>
                                    </ul>
                                </div>

                                <div class="search-acreage w-100">
                                    <h6>Lọc Theo Diện Tích</h6>
                                    <ul  class="list-group list-group-search list-group-flush">
                                        <li v-for="(item,index) in area" :key="index" class="list-group-item list-group-item-action" @click.prevent="getBySearch('area',item.on,item.under)">
                                            <a href="#" v-if="index == 0" class="text-decoration-none">Dưới {{item.under}} m <sup>2</sup></a>
                                            <a href="#" v-else class="text-decoration-none">Từ {{item.on}} - {{item.under}}m <sup>2</sup></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-2 advertise">
                    <div class="advertisement">
                        <img class="" src="../assets/image/advertisement/advertisement_01.jpg" alt="">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div>
        <FilterLocationComponent
            :show_BoxChooseLocation="show_BoxChooseLocation"
            :black_screen="black_screen"
            :openSearchPrice="openSearchPrice"
            :openSearchArea="openSearchArea"
            @off_filter_location="off_filter_location"
            @searchPriceOrArea="getBySearch"
            @Update_backScreen="UpdateBackScreen"
            @title_location="titleLocation">
        </FilterLocationComponent>
    </div>
</template>

<script>
import Swal from 'sweetalert2'
import debounce from 'lodash.debounce';

import postApi from "../Api/postApi"
import romePriceApi from "../Api/roomPrice"
import areaApi from "../Api/area"

import { ref, defineComponent, onMounted } from 'vue'
import { useDateFormat, useNow } from '@vueuse/core'
import NavbarComponent from './NavbarComponent.vue'
import FilterLocationComponent from './Search_Location/SearchLocationComponent.vue';

import PostComponent from './post/index.vue'
import locationApi from '../Api/locationApi'
import axios from 'axios'

export default defineComponent(
    {
        name: "HomeComponent",
        components: {
            NavbarComponent,
            PostComponent,
            FilterLocationComponent
        },
        props: {

        },
        data() {
            const inputSearch = ref('')
            const show_BoxChooseLocation = ref(null)
            const roomPrice = ref([])
            const posts = ref([])
            const notification_bar = ref(true)
            const black_screen = ref(true)
            const list_searchSeggestion = ref([])
            const showLocaing_location = ref(true)

            const openSearchPrice = ref(false)
            const openSearchArea = ref(false)

            return {
                posts,
                notification_bar,
                inputSearch,
                roomPrice,
                show_BoxChooseLocation,
                black_screen,
                list_searchSeggestion,
                showLocaing_location,
                openSearchPrice,
                openSearchArea
            }
        },
        methods: {

            checkUser(){
                axios.post('checkSession')
            },

            filterFeedBackData(post_id) {
                return this.treeFeedback.feedback.filter(item => item.post_id === post_id)
            },

// tìm kiếm theo giá hoặc diện tích
            getBySearch: debounce(async function(name,on,under){
                const data = {
                    name: name,
                    on: on,
                    under: under
                }
                const response = await postApi.searchPriceOrArea(data);

                this.black_screen = true
                this.openSearchPrice = false
                this.openSearchArea = false

                if(response.code == 1000 && response.result.length > 0){
                    this.posts = response

                }else{
                    Swal.fire({
                    title: "Thông Báo!",
                    text: "Không Tìm Thấy Bài Đăng Phù Hợp",
                    icon: "error",
                    showConfirmButton: false,
                    timer: 1500
                    })
                }
            },300),

// Tìm kiếm theo ô nhập
            searchInput: debounce( async function(){
                console.log(this.inputSearch)
                const data = {
                    input : this.inputSearch
                }

                this.showLocaing_location = !this.showLocaing_location
                const response =  await postApi.searchInputAll(data);

                this.showLocaing_location = !this.showLocaing_location
                if(response.code == 1000 && response.result.length > 0){
                    
                    this.inputSearch = ''
                    this.posts = response
                }else{
                    Swal.fire({
                    title: "Thông Báo!",
                    text: "Không Tìm Thấy Bài Đăng Phù Hợp",
                    icon: "error",
                    showConfirmButton: false,
                    timer: 1500
                    })
                }
            },500),

// tìm kiếm theo đề xuất
            SearchSuggestions: debounce(async function(){
                const data = {
                    input : this.inputSearch
                }
                if(this.inputSearch != ""){
                    const response = await postApi.list_searchSuggestion(data);

                    if(response.code  == 1000){
                        this.list_searchSeggestion = response.result;
                    }
                }
            },400),
// tìm kiến theo id của quận huyện, thành phố
            off_filter_location(posts){
                this.posts = posts
            },

            async getLocationByParent_id(){
                this.show_BoxChooseLocation = 'provinces'
                this.black_screen = !this.black_screen
            },

            UpdateBackScreen(blaclScreen,show_Box){
                this.black_screen = blaclScreen,
                this.show_BoxChooseLocation = show_Box
                this.openSearchPrice = false
                this.openSearchArea = false
            },

            titleLocation(title){
                this.nameLocation = title
            },

            dropdownBoxSearch(text){
                if(text === "price"){
                    this.openSearchPrice = !this.openSearchPrice
                }else{
                    this.openSearchArea = !this.openSearchArea
                }
                this.black_screen = !this.black_screen

            }
        },
        created() {
            this.infoPost = postApi.listPost()
            this.romePrice = romePriceApi.Price();
            this.area = areaApi.Area();
        }
    }
)


</script>

<style scoped>

    .view_home .advertise:nth-child(1) {
        padding: 0;
        display: flex;
        justify-content: end;
    }

    .view_home .advertise .advertisement{
        position: fixed;
        /* width: 200px; */
        height: 600px;
        overflow: auto;
        padding-top: 10px;
        float: end;
    }

    .App .view_home .advertise .advertisement img{
        width: 100%;
        height: 100%;
        padding-top: 15%;
        padding-bottom: 20%;

    }

    .App.fix_body .view_home .advertise .advertisement img{
        width: 100%;
        height: 100%;
        padding-top: 30%;
        padding-bottom: 0%;
    }

    /* .view_home .advertise:nth-child(1) .advertisement img{

    } */

    .HomeComponent{
        position: relative;
    }

    .view_main .parent_header_search{
        background-color: #fff;
        padding: 10px;
        position: relative;
    }

    .view_main .header_search{
        width: 100%;
        height: 48px;
        background-color: #F2F2F2;
        border: 1px solid #F2F2F2;
        border-radius: 8px;
        padding: 6px 0px;
        display: flex;
        align-items: center;
    }

    .view_main .header_search .searchLocation{
        cursor: pointer;
        float: left;
        width: 200px !important;
    }

    .view_main .header_search .searchLocation .listsearch{
        position: relative;
        color: #2C2C2C;
        /* display: flex;
        align-items: center; */
    }

    .searchLocation .listsearch .icon_location {
        float: left;
        margin: 5px 0px 4px 12px;
    }

    .searchLocation .listsearch .name_search{
        font-size: 14px;
        line-height: 20px;
        float: left;
        color: rgb(44, 44, 44);
        font-family: "Lexend Medium", Roboto, Arial !important;
        font-weight: normal !important;
        padding: 2px 0px;
        margin: 6px 0px 5px 8px;
    }

    .searchLocation .listsearch .fa-solid.fa-chevron-down{
        float: right;
        padding: 4px 0px 4px 0px;
        margin: 4px 12px 4px 12px;
        color: #999;
        transition: transform .4s ease;
    }

    .searchLocation .listsearch span.postfix-div{
        position: absolute;
        content: "";
        display: block;
        width: 0px;
        height: 30px;
        left: calc(100% + 2px);
        top: 4px;
        border-width: 1px;
        border-style: solid;
        border-color: rgb(204, 204, 204);
        border-image: initial;
    }

    .view_main .header_search i.bi.bi-search{
        float: left;
        text-align: center;
        margin: auto;
        font-size: 18px;
        padding: 7px 7px 7px 15px;
    }

    .view_main .header_search .LocationInputSearch{
        float: left;
        display: block;
        flex: 1 0 auto;
    }

    .view_main .header_search .LocationInputSearch input.input_search{
        width: 100%;
        height: 32px;
        display: block;
        outline: none;
        border: none;
        background-color: transparent;
    }

    .view_main .header_search button.btnSearch{
        float: right;
        margin: 0 7px;
        padding: 6px 13px;
        max-height: 32px;
        max-width: 90px;
        flex: 1 0 auto;
        display: flex;
        align-items: center;
        color: #fff;
        background: #ff5d26;
        border: solid 1px #ff5d2631;
        font-family: "Lexend Medium", Roboto, Arial !important;
        font-size: 14px;
        line-height: 20px;
        font-weight: normal !important;
        letter-spacing: -.2px;
        padding: 5px 11px;
        border-radius: 4px;
    }

    .view_main .header_search button.btnSearch:hover{
        background-color: rgb(255, 93, 38,0.7);
    }

    /* đề xuất tìm kiếm theo nội dung */
    form .listItemProps{
        max-height: 380px;
        display: none;
        position: absolute;
        background-color: #fff;
        width: 100%;
        margin-top: 10px;
        padding: 8px 0px;
        box-shadow: 0px 2px 4px -1px rgba(0, 0, 0, 0.2),
                    0px 4px 5px 0px rgba(0, 0, 0, 0.14),
                    0px 1px 10px 0px rgba(0, 0, 0, 0.12);
        overflow: hidden;
        z-index: 7;
    }

    form .listItemProps.show{
        display: block;
    }

    form .listItemProps .conntent_search{
       padding: 5px 12px 8px 12px;
    }

    form .listItemProps .ul_listItem .li_listItem{
        padding: calc(2px + 4px) 12px;
        /* color: #8C8C8C; */
        font-size: 0.875rem;
        cursor: pointer;
        transition: background-color 150ms cubic-bezier(0.4,0,0.2,1) 0ms;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 1;
        display: -webkit-box;
        overflow: hidden;
    }

    form .listItemProps .ul_listItem .li_listItem:hover{
        background-color: #E8E8E8 ;
    }

    .info {
        width: 100vw;
        height: 100vh;
        position: absolute;
        top: 27%;
        left: 0;
        right: 0;
        z-index: 9999;
        text-align: center;
        animation: slideInTop ease .2s, fadeOut linear 1s 2s forwards;
    }

    @keyframes slideInTop {
        from {
            opacity: 0;
            /* transform: translateY(calc(100% + 32px)); */
        }
        to{
            opacity: 1;
            /* transform: translateY(0); */
        }
    }

    @keyframes fadeOut {
        to{
            opacity: 0;
        }
    }

    .box_search_locations {
        display: none;
        position: fixed;
        z-index: 99999;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        margin: auto;
        width: 30px;
        height: 30px;
        border: 3.5px solid #45474b;
        border-radius: 50%;
        border-style:  dotted ;
        animation: spin 2.5s infinite linear;
    }

    /* responsive */
    @media only screen and (max-width: 1200px) {
        .view_home .advertise .advertisement {
            display: block;
            position: fixed;
            width: 150px;
            height: 600px;
            overflow: auto;
            padding-top: 10px;
        }

        .view_home .main_home .list-group-search .list-group-item a {
            font-size: 13px;
        }
    }

    @media only screen and (max-width: 992px) {
        .view_home .advertise .advertisement {
            display: none;
            position: fixed;
            width: 150px;
            height: 600px;
            overflow: auto;
            padding-top: 10px;
        }

        .view_home .main_home .list-group-search .list-group-item a {
            font-size: 14px;
        }
    }

    @media only screen and (max-width: 768px) {
        .view_home .main_home .search-acreage,
        .view_home .main_home .search-price {
            max-height: 58px;
            overflow: hidden;
        }
    }

    @media only screen and (max-width: 600px) {

        .view_home .view_main .the_right{
            display: none;
        }

        .view_main .header_search .searchLocation{
            max-width: 100px;
        }

        .view_main .header_search i.bi.bi-search,
        .searchLocation .listsearch .name_search{
            font-size: 12px;
        }

        .searchLocation .listsearch .fa-solid.fa-chevron-down{
            display: none;
        }

        .swiper-slide img {
            height: auto;
        }
    }

    @media only screen and (max-width: 477px) {
        .container-fluid{
            padding-left: 0px;
            padding-right: 0px;
        }

        .row>* {
            flex-shrink: 0;
            width: 100%;
            max-width: 100%;
            padding: 0px;
            margin-top: var(--bs-gutter-y);
        }

        .view_main .header_search .searchLocation{
            max-width: 40px;
        }

        .searchLocation .listsearch .name_search{
            display: none;
        }

        .searchLocation .listsearch .icon_location{
            margin: 5px 0px 4px 14px;
        }

        .view_main .header_search .LocationInputSearch input{
            font-size: 10px;
        }

        .view_main .header_search .LocationInputSearch input.input_search{
            font-size: 15px;
        }

        .view_main .header_search button.btnSearch{
            font-size: 10px;
            max-height: 25px;
            max-width: 65px;
        }

        .the_right {
            display: none;
        }

        .view_main .header_search button.btnSearch{
            display: none;
        }
    }
</style>
