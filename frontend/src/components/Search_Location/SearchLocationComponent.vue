<template>
    <div class="filterLocation">
        <div class="filterPrice" :class="[openSearchPrice?'d-block': 'd-none']">
            <div class="" >
                <ul class="list-group list-group-search list-group-flush ps-0">
                    <li v-for="price in romePrice" :key="price.id" class="list-group-item list-group-item-action" @click.prevent="getBySearch('price',price.on,price.under)">
                        <a href="#" class="text-decoration-none">Giá từ {{price.title}}</a>
                    </li>

                </ul>
            </div>
        </div>
        <div class="filterAcreage" :class="[openSearchArea?'d-block': 'd-none']" >
            <div >
                <ul  class="list-group list-group-search list-group-flush ps-0">
                    <li v-for="(item,index) in area" :key="index" class="list-group-item list-group-item-action" @click.prevent="getBySearch('area',item.on,item.under)">
                        <a href="#" v-if="index == 0" class="text-decoration-none">Dưới {{item.under}} m <sup>2</sup></a>
                        <a href="#" v-else class="text-decoration-none">Từ {{item.on}} - {{item.under}}m <sup>2</sup></a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- Tìm kiếm theo chọn địa chỉ -->
        <div class="filter_group" :class="{show: show_BoxChooseLocation == 'provinces'}">
            <div class="header">
                <i class="fa-solid fa-arrow-left" @click.prevent="off_filter_location"></i>
            <span>Chọn Tỉnh Thành</span>
            </div>
            <div class="content">
                <div id="filter_group_city_option" class="city_option">
                    <ul>
                        <li :class="{selected : selectedItem == null || selectedItem == 'Toàn Quốc'}" @click.prevent="selectedItem = 'Toàn Quốc'">Toàn Quốc</li>
                        <li v-for="(items,index) in locations['provinces']" :key="index" :class="{selected: selectedItem === items.name}" @click.prevent="clickSeleted(items,'districts')">{{items.name}}</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="filter_group" :class="{show: show_BoxChooseLocation == 'districts'}">
            <div class="header">
                <i class="fa-solid fa-arrow-left" @click.prevent="off_filter_location"></i>
            <span>{{ selectedItem}}</span>
            </div>
            <div class="content">
                <div id="filter_group_districts_option" class="districts">
                    <ul>
                        <li v-for="(items,index) in locations['districts']" :key="index" :class="{selected: selectedItem=== items.name}" @click.prevent="clickSeleted(items,'wards')">{{items.name}}</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="filter_group" :class="{show: show_BoxChooseLocation == 'wards'}">
            <div class="header">
                <i class="fa-solid fa-arrow-left" @click.prevent="off_filter_location"></i>
            <span>{{selectedItem}}</span>
            </div>
            <div class="content">
                <div id="filter_group_districts_option" class="districts">
                    <ul>
                        <li v-for="(items,index) in locations['wards']" :key="index" :class="{selected: selectedItem === items.name}" @click.prevent="clickSeleted(items,'noTable')">{{items.name}}</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="black_overlay" @click.prevent="off_filter_location"  :class="{'d-block':!black_screen}"></div>

        <div class="box_search_locations" :class="{show : !showLocaing_location}">
            <div class="box_page_loading">
                <div>
                    <div></div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, defineComponent, onMounted } from 'vue'
import Swal from 'sweetalert2'

import postApi from "../../Api/postApi"
import romePriceApi from "../../Api/roomPrice"
import areaApi from "../../Api/area"

import locationApi from '../../Api/locationApi'
import axios from 'axios'

export default {
    props:{
        show_BoxChooseLocation: String,
        black_screen: Boolean,
        openSearchPrice: Boolean,
        openSearchArea: Boolean
    },
    data() {
        const locations = ref([])
        const area = ref([])
        const selectedItem  = ref(null)
        const showLocaing_location = ref(true)
        const location_id = ref([])

        const romePrice = ref([])


        return {
            area,
            locations,
            selectedItem,
            showLocaing_location,
            location_id,
            romePrice
        }
    },
    methods: {
        async getBySearch(name,on,under){
            this.$emit('Update_backScreen',true,false,false)
            this.$emit('searchPriceOrArea',name,on,under)
        },

        async off_filter_location(){

            this.$emit('Update_backScreen',true,null)
            if(Object.keys(this.location_id).length > 0 && this.selectedItem != null){
                const data = {
                    provinceId: this.location_id['provinces'],
                    districtId: this.location_id['districts']?this.location_id['districts']:0,
                    wardId: this.location_id['wards']?this.location_id['wards']:0,
                }

                const response =  await postApi.searchByLocation_Id (data);

                if(response.code == 1000 && response.result.length > 0){
                    this.$emit('off_filter_location',response)
                }else{
                    Swal.fire({
                    title: "Thông Báo!",
                    text: "Không Tìm Thấy Bài Đăng Phù Hợp",
                    icon: "error",
                    showConfirmButton: false,
                    timer: 1500
                    })
                }
            }else{
                const response =  await postApi.listPost();
                this.posts = response
            }
            this.selectedItem = null
        },

        async clickSeleted(item,nameTable){

            this.selectedItem = item.name
            var response = [];
            this.showLocaing_location = false
            switch (nameTable) {
                case "districts":
                    this.location_id['provinces'] = item.id
                     this.$emit('title_location',item.name)
                    response = await locationApi.get_Districts(item.id)
                    break;
                case "wards":
                    this.location_id['districts'] = item.id
                    response = await locationApi.get_Wards(item.id)
                    break;
                default:
                    this.location_id['wards'] = item.id
                    break
            }

            this.showLocaing_location = true
            if(response.length > 0){
                this.$emit('Update_backScreen',false,nameTable)
                this.locations[nameTable] = response
            }
        },

        // Tìm kiếm theo địa chỉ đã chọn
        async loadLocation(){
         
            const response =  await locationApi.get_Provinces();
            this.locations['provinces'] = response
        },

    },
    created() {
        this.romePrice = romePriceApi.Price();
        this.area = areaApi.Area();
        this.loadLocation();
    }
}
</script>

<style scoped>
     /* Khung tìm kiếm theo địa chỉ */

    ol, ul {
        padding-left: 2rem;
    }

    .filterLocation .box_search_locations {
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

    @keyframes spin {
        100%{
            transform: rotate(360deg);
        }
    }

    .filterLocation .black_overlay{
        display: none;
        background-color: rgba(0, 0, 0, .5);
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        z-index: 999;
    }

    .filterLocation .filter_group{
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        z-index: 99999;
        background-color: #fff;
        overflow: hidden;
        width: 680px;
        max-height: 490px;
        margin: auto;
        border-radius: 8px;
    }

    .filterLocation .box_search_locations.show,
    .black_overlay.show,
    .filter_group.show{
        display: block;
    }

    .filterLocation .filter_group .header{
        padding: 10px;
        border-bottom: 1px solid #CDCDCD;
        text-align: center;
        font-weight: 500;
        font-size: 18px;
    }

    .filterLocation .filter_group .header i.fa-solid.fa-arrow-left{
        float: left;
        align-items: center;
        font-size: 18px;
        margin: 5px;
        cursor: pointer;
    }

    .filterLocation .filter_group .content{
        width: 100%;
        height: calc(100% - 40px);
        overflow-y: scroll;
        -webkit-overflow-scrolling: touch;
        /* padding: 10px 25px; */
    }

    .filterLocation .filter_group .content ul>li{
        padding: 12px 10px 12px 25px;
        border-bottom: 1px solid #ddd;
        font-size: 1.1rem;
        position: relative;
        cursor: pointer;
    }

    .filterLocation .filter_group .content ul>li::before{
        background: url(../../assets/image/cirlcle-rec.svg) center no-repeat;
        background-size: contain;
        width: 15px;
        height: 15px;
        position: absolute;
        top: 50%;
        left: 0;
        margin-top: -8px;
        display: block;
        content: "";
        opacity: .45;
    }

    .filterLocation .filter_group .content ul>li.selected::before{
        background: url(../../assets/image/cirlcle-rec-active.svg) center no-repeat;
        background-size: contain;
        opacity: 1;
    }

    .filterLocation .filter_group .content ul>li.selected{
        color: #ff5d26;
        font-weight: 500;
        font-family: Arial, Helvetica, sans-serif;
    }

    .list-group-flush>.list-group-item a:hover{
        color: #ff5d26;
    }

        /* responsive */
    @media only screen and (min-width: 1200px) {

    }

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

    @media only screen and (max-width: 768px) {
        .view_home .main_home .search-acreage,
        .view_home .main_home .search-price {
            max-height: 58px;
            overflow: hidden;
        }

        .filterPrice,
        .filterAcreage{
            position: fixed;
            top: 45%;
            left: 10%;
            right: 10%;
            bottom: 0;
            z-index: 9999;
            overflow: hidden;
        }

        .filterPrice .list-group,
        .filterAcreage .list-group{
            border: 1px solid #dedede;
            border-radius: 8px ;
        }

        .filterLocation .filter_group{
            width: 540px;
            max-height: 400px;
        }
    }

    @media only screen and (max-width: 600px) {

        .filterLocation .filter_group{
            width: 100vw;
            max-height: 100vh;
        }
    }

    @media only screen and (max-width: 477px) {
        .filterPrice,
        .filterAcreage{
            position: fixed;
            display: inline-block;
            top: 30%;
            left: 10%;
            right: 10%;
            bottom: 0;
            z-index: 9999;
            height: 250px;
            max-height: 400px;
        }
    }

</style>
