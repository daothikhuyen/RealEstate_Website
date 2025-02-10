<template>
    <div class="info_post py-3">
        <section class="header">
            <h6 class="title">{{ postData.title }}</h6>
            <span class="info_price_arceage px-2">
                <span class="price">
                    {{formatPrice(postData.price)}}
                </span>
                <span class="arceage"> - {{ postData.area }}m<sup>2</sup> </span>
            </span>
        </section>
        <div class="address">
            <!-- <i class="fa-solid fa-location-dot"></i> -->
            <i class="icon bi bi-geo-alt"></i>
            <span class="ps-1">{{postData.full_address }}</span>
        </div>
        <div class="extension d-flex align-items-center" v-if="extensions.length != 0">
            <div class="pe-1">
                <i class="icon fa-brands fa-intercom"></i>
            </div>
            <div class="title">Tiện ích:</div>
            <div class="ps-1" v-for="(extension,index) in extensions" :key="index">
                <span >{{extension.name}}</span>
                <span v-if="index !== extensions.length -1">,</span>
            </div>
        </div>
        <div class="description p-2">
            <span class="title">Mô tả chi tiết:</span> <br>
            <span  v-if="isSeeMoreDescription">
                <span class="ps-2 content" id="descriptionContent" v-html="postData.description"></span>
            </span>
            <span v-else>
                <span class="ps-2 content" id="descriptionContent" v-html="postData.description.substring(0, 500)"></span>
                <span class="description_etc" :class="{ hide: isSeeMoreDescription }">...</span>
            </span>

            <span class="btn-action" :class="{ hide: !isPostContextTooLong }">
                <div class=" my-2 border-top py-2 d-flex justify-content-center">
                    <button class="btn-SeeMore" :class="{ hide: isSeeMoreDescription }"
                        @click="SeeMoreDescription()">Xem thêm <i class="bi bi-arrow-down-square"></i> </button>
                    <button class="btn-short" :class="{ hide: !isSeeMoreDescription }" @click="ShortDescription()">Rút
                        gọn <i class="bi bi-arrow-up-square"></i></button>
                </div>
            </span>
        </div>
        <div class="post_image mt-2 px-2 py-1">
            <div class="swiper">
                <Carousel class="swiper-wrapper">
                    <Slide v-for="slideOne in videos" :key="slideOne.id" class="swiper-slide">
                        <video width="320" height="250" controls class="carousel__item">
                            <source :src="`http://localhost:8001${slideOne.link_video}`" type="video/mp4">
                        </video>
                    </Slide>
                    <Slide v-for="slideTwo in images" :key="slideTwo.id" class="swiper-slide">
                        <img :src="`http://localhost:8001${slideTwo.link_image}`" alt="">
                    </Slide>
                    <template #addons>
                        <Navigation />
                        <Pagination />
                    </template>
                </Carousel>
            </div>
        </div>
    </div>
</template>
<script>
import 'vue3-carousel/dist/carousel.css'

import { Carousel, Navigation, Pagination, Slide } from 'vue3-carousel'
import { defineComponent, onMounted, ref } from 'vue'
export default defineComponent({
    props: {
        postData: Object,
        images: Array,
        videos: Array,
        extensions: Array
    },
    components: {
        Carousel,
        Slide,
        Pagination,
        Navigation,
    },
    mounted(){
    },
    data() {

        const isSeeMoreDescription = ref(this.postData.description.length < 500)
        const isPostContextTooLong = ref(this.postData.description.length > 500)

        return {
            isSeeMoreDescription,
            isPostContextTooLong,
        }

    },
    methods: {

        formatPrice(price){
            return Number(price).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
        },

        SeeMoreDescription(index) {
            this.isSeeMoreDescription = true;
        },

        ShortDescription(index) {
            this.isSeeMoreDescription = false;
        },

    },
})
</script>

<style scoped>
        /* icon next sdiles */

    .carousel .carousel__prev,
    .carousel .carousel__next{
        background: #fff;
        margin: 0 1rem;
    }


    @media only screen and (max-width: 477px) {
        .info_post .header .title {
            font-size: 13px;
        }

        .main_home .post {
            border-radius: 0px;
            box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 0px 1px;
        }
    }

</style>
