
<template>
    <div class="footer">
        <div class="container-fluid">
            <div class="row">
                <!-- <div class="col-md-1"></div> -->
                <div class="footer_main">
                    <div class="row w-100">
                        <div class="col-lg-5 col-md-12 col-sm-12">
                            <div class="info_footer">
                                <div class="logo_info">
                                    <img src="../assets/image/logo.png" alt="" class="w-100">
                                </div>
                                <div class="text-secondary ms-auto w-100">
                                    <b>VND LAND</b> <small>luôn tự tin mang đến cho người dùng <br>  các cơ hội tìm nhà tốt nhất.</small>
                                </div>
                            </div>
                        </div>
                        <div class="customer col-lg-7 col-md-12 col-sm-12">
                            <div class="customer_support">
                                <span class="footer-col-title fw-semibold pb-3">Thông tin về VND LAND</span>
                                <ul class="list-unstyled">
                                    <li>
                                        <a href="#" class="text-decoration-none text-black">Trang chủ, Tin tức</a>
                                    </li>
                                    <li>
                                        <a href="#" class="text-decoration-none text-black">Quy chế hoạt động</a>
                                    </li>
                                    <li>
                                        <a href="#" class="text-decoration-none text-black">Chính sách bảo mật</a>
                                    </li>
                                    <li>
                                        <a href="#" class="text-decoration-none text-black">Quy định sử dụng</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="customer_support">
                                <span class="footer-col-title fw-semibold pb-3">Hỗ trợ khách hàng</span>
                                <ul class="list-unstyled">
                                    <li>
                                        <a href="#" class="text-decoration-none text-black">Câu hỏi thường gặp</a>
                                    </li>
                                    <li>
                                        <a href="#" class="text-decoration-none text-black">Hướng dẫn đăng tin</a>
                                    </li>
                                    <li>
                                        <a href="#" class="text-decoration-none text-black">Quy định đăng tin</a>
                                    </li>
                                    <li>
                                        <a href="#" class="text-decoration-none text-black">Giải quyết khiếu nại</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="customer_support">
                                <span class="footer-col-title fw-semibold pb-3">Liên hệ với chúng tôi</span>
                                <div class="social-link mt-1">
                                    <a v-for="social in social_link" :key="social.id" :class="social.name" :href="social.link">
                                        <i></i>
                                    </a>
                                </div>

                                <span class="footer-col-title fw-semibold pt-3">Phương thức thanh toán</span>
                                <div class="social-link">
                                    <ul class="list-unstyled">
                                        <li>
                                            <a href="#" class="text-decoration-none text-black">Thanh toán bằng tiền mặt</a>
                                        </li>
                                        <li>
                                            <a href="#" class="text-decoration-none text-black">Thanh toán quét mã</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <hr class="my-3">
                <div class="row py-2">
                    <div class="col-lg-6 col-md-6">
                        <span>
                            <ul class="ps-0">
                                <li>
                                    Chịu trách nhiệm sàn GDTMĐT: Ông Danh Lợi
                                </li>
                                <li>
                                    Quy chế, quy định giao dịch có hiệu lực từ 08/08/2024
                                </li>

                            </ul>
                        </span>
                    </div>
                    <div class="col-lg-6 col-md-6">
                        <ul class="ps-0">
                            <li>
                                Ghi rõ nguồn <span style="color:#ff5d26">DaoMy@gmail.com</span> khi phát hành lại thông tin từ website này.
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="py-3">
                    <div>
                        <img src="../assets/image/da-dang-ki-bct.svg" alt="">
                    </div>
                </div>
                <!-- <div class="col-md-1"></div> -->
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        mounted() {
            console.log('Component mounted.')
        },
        data() {
            return {
                social_link: [
                    {name: 'social-fa', link : 'https://www.facebook.com/groups/833843313788409/?hoisted_section_header_type=recently_seen&multi_permalinks=1822768544895876'},
                    {name: 'social-youtube', link : 'https://www.facebook.com/groups/833843313788409/?hoisted_section_header_type=recently_seen&multi_permalinks=1822768544895876'},
                    {name: 'social-instagram', link : 'https://www.facebook.com/groups/833843313788409/?hoisted_section_header_type=recently_seen&multi_permalinks=1822768544895876'},
                    {name: 'social-twitter', link : 'https://www.facebook.com/groups/833843313788409/?hoisted_section_header_type=recently_seen&multi_permalinks=1822768544895876'}
                ]
            }
        },
    }
</script>

<style>
    :root{
        --primary-color: #ff5d26
    }

    .footer .footer_main{
        padding: auto 5px;
        margin-left: 2px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .footer{
        box-shadow: rgba(50, 50, 93, 0.25) 0px 2px 5px -1px, rgba(0, 0, 0, 0.3) 0px 1px 3px -1px;
        /* padding: 20px; */
        padding: 25px 0px 0px 0px;
        background-color: #fff;
    }

    .footer .info_footer .logo_info{
        width: 48%;
        height: auto;
    }

    .footer .footer_main .customer{
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .customer_support ul li{
        font-size: 14px;
    }

    .customer_support ul li a{
        margin: 3px 0;
        display: block;
        padding: 5px 0;
        color: #333;
    }

    .customer_support .footer-col-title{
        display: block;
        font-size: 13px;
        font-weight: 500;
    }

    .social-link a>i{
        margin-right: 5px;
        display: inline-block;
        width: 35px;
        height: 35px;
    }

    .social-link .social-fa i {
        background-image: url("../assets/image/icon_facebook.svg");
        display: inline-block;
        background-size: contain;
        background-repeat: no-repeat;
    }

    .social-link .social-youtube i {
        background-image: url("../assets/image/icon_youtube.svg");
        display: inline-block;
        background-size: contain;
        background-repeat: no-repeat;
    }

    .social-link .social-instagram i {
        background-image: url("../assets/image/Icon_of_Zalo.svg");
        display: inline-block;
        background-size: contain;
        background-repeat: no-repeat;
    }

    .social-link .social-twitter i{
        background-image: url("../assets/image/icon_twitter.svg");
        display: inline-block;
        background-size: contain;
        background-repeat: no-repeat;
    }

    @media (min-width: 992px) {
        .footer{
            padding: 5% 8% 0px 8% ;
        }

        .footer .footer_main .customer{
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

    }

    @media (max-width: 992px) {
        /* .footer{
            position: fixed;
            z-index: 99;
            bottom: 0;
        } */

        .footer .footer_main .customer{
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .footer .customer_support{
            font-size: 18px;
        }

        .footer .info_footer .logo_info {
            width: 38%;
            height: auto;
        }

        /* .footer .info_footer .logo_info span{
            min-width: 100%;
        } */

        .footer .info_footer{
            margin-bottom: 20px;
        }

    }

     @media (max-width: 600px) {

        .row {
            padding-right: 0px;
            padding-left: 0px;
            margin-right:  0rem;
            margin-left: 0rem;
        }
        .footer .footer_main .customer .customer_support:nth-child(2){
            display: none;
        }
    }

    @media only screen and (max-width: 420px) {
        .row {
            padding-right: 0px;
            padding-left: 0px;
            margin-right:  0rem;
            margin-left: 0rem;
        }
    }

    @media only screen and (max-width: 330px) {
        .row {
            padding-right: 0px;
            padding-left: 0px;
            margin-right:  0rem;
            margin-left: 0rem;
        }

        .footer .footer_main .customer{
            display: block;
            margin: auto;
            padding-left: 15px;
        }

        .footer .footer_main .customer .customer_support:nth-child(3){
            display: block;
            margin: 20px 0px;

        }
    }

</style>
