
<template>
    <div class="navbarCommpent">
        <div class="header py-2 bg-light border-0 px-2">
            <div class="d-flex justify-content-between aglin-items-center text-center">
                <div class="ps-md-5 text-start">
                    <span class="info_nav text-secondary">Nơi cung cấp những căn phòng tốt nhất</span>
                </div>
                <div class="pe-md-5 text-start">
                    <span class="info_nav text-secondary">Liên lạc: 0834006771 | Phòng tốt | Nhà tốt| <a class="contact" href="/contact">Liên hệ</a></span>
                </div>
            </div>
        </div>
        <div class="navbar" id="navbar-menu">
            <div class="container navbar_content w-100 d-flex justify-content-between align-items-center">
                <div class="navbar-brand">
                    <a class="mx-auto" href="/home">
                        <div class="logo">
                            <img src="../assets/image/logo.png" alt="">
                        </div>
                    </a>
                </div>
                <div class="iconBars" @click.prevent="MenuOpen">
                    <i class="fa-solid fa-bars  fa-xl"></i>
                </div>
                <div class="navbar_body "  id="navbarSupportedContent" >
                    <ul class="navbar_link d-md-flex justify-content-center align-items-center " :class="{dropdown : open}">
                        <div class="sidebarOpen">
                            <sidebar :open="open" @changeValueOpen="changeValueOpen"></sidebar>
                        </div>
                    </ul>
                    <ul class="navbar_link d-md-flex justify-content-center align-items-center">
                        <li class="nav-item dropdown" v-if="!isLoggedIn">
                            <a class="nav-link d-flex align-items-center" href="/login" >
                                <div class="avatar d-flex align-items-center">
                                <i class="bi bi-person-plus-fill"></i>
                                </div>
                                <small><span class="ps-1">Đăng Nhập</span></small>
                            </a>
                        </li>
                        <li class="nav-item dropdown" v-if="!isLoggedIn">
                            <a class="nav-link d-flex align-items-center" href="/signup" >
                                <div class="avatar d-flex align-items-center">
                                    <i class="bi bi-box-arrow-in-right"></i>
                                </div>
                                <small><span class="ps-1">Đăng Kí</span></small>
                            </a>
                        </li>
                        <li v-if="!isLoggedIn">
                            <button class="have_user bg-transparent border-0 d-sm-none d-md-block">
                                <a class="post_new nav-link d-flex align-items-center px-4 w-sm-auto" href="/postManagement/index/creator" >
                                    <div class="avatar d-flex align-items-center">
                                        <i class="bi bi-file-earmark-plus-fill"></i>
                                    </div>
                                    <span class="ps-1">Đăng Tin</span>
                                </a>
                            </button>
                        </li>
                    </ul>                  
                    <div class="small_menu">
                        <ul :class="[isLoggedIn ? 'd-flex': 'd-none']">
                            <li class="me-4" v-if="authUser">
                                <div class="userLogin w-100">
                                    <div class="avatar_name d-flex align-items-center dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        <div class="avatar inline-block">
                                            <img v-if="authUser.avatar" :src="`http://localhost:8001${authUser.avatar}`" alt="avatar" class="image_avatar_post">
                                            <img v-else class="image_avatar_post" src="../assets/image/avatar/profile.jpg" alt="">
                                        </div>
                                        <div class="name ps-1 fw-semibold" >
                                            <span style="font-size:15px">{{authUser.username}}</span>
                                        </div>
                                    </div>
                                    <ul class="dropdown-menu mt-2 p-1" aria-labelledby="navbarDropdown">
                                        <li v-for="item in menu.user" :key="item.index" class="menu-child border-bottom py-1">
                                            <a class="dropdown-item" :href="item.url">{{item.title}}</a>
                                        </li>
                                        <ul class="px-0" v-if="authUser.role[0] === 'ADMIN'">
                                            <li v-for="item in menu.admin" :key="item.index" class="menu-child border-bottom py-1">
                                                <a class="dropdown-item" :href="item.url">{{item.title}}</a>
                                            </li>
                                        </ul>
                                        <li>
                                            <a class="dropdown-item" href="/login" @click.prevent="logoutWeb" ><i class="fa-solid fa-right-from-bracket"></i> Thoát</a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                            <li>
                                <button class="have_user bg-transparent border-0 d-sm-none d-md-block">
                                    <a class="post_new nav-link d-flex align-items-center px-4 w-sm-auto" href="/postManagement/index/creator" >
                                        <div class="avatar d-flex align-items-center">
                                            <i class="bi bi-file-earmark-plus-fill"></i>
                                        </div>
                                        <span class="ps-1">Đăng Tin</span>
                                    </a>
                                </button>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>


<script>
import { ref } from 'vue'
import {mapState,mapGetters,mapActions} from 'vuex'
import Swal from 'sweetalert2'

import Sidebar from '../components/posterManagement/Sidebar.vue'
import notification from '../assets/js/Notification';

export default {
    components: {
        Sidebar
    },
    computed: {
        ...mapGetters(['isLoggedIn']),
        ...mapGetters(['authUser'])
    },
    data() {
        const open = ref(false)
        const menu = {
            user: [
                {
                    title: '📝 Đăng tin cho thuê',
                    url: '/postManagement/index/creator'
                },
                {
                    title: '📑 Quản lí tin đăng',
                    url: '/postManagement/my_posts/list_posts'
                },
                {
                    title: '👤 Thông tin tài khoản',
                    url: '/postManagement/account/personal_info'
                },
                {
                    title: '📞 Liên hệ với chúng tôi',
                    url: '/contact'
                },
            ],
            admin: [
                {
                    title: '🧑‍🤝‍🧑 Bài đăng của khách hàng',
                    url: '/postManagement/admin/list_posts_user'
                },
                {
                    title: '🗃️ Quản lí người dùng',
                    url: '/postManagement/admin/list_user'
                },
            ]
        }
        return {
            open,
            menu
        }
    },
    methods: {

        logoutWeb(){
            Swal.fire({
                title: "Thông Báo!",
                text: "Banh Muốn Rời Web!",
                icon: "success",
                confirmButtonText: "Đồng Ý",
            }).then((result) => {
                if (result.isConfirmed) {
                    this.$store.dispatch('logout');
                }
            });
        },

        MenuOpen(){
            this.open = !this.open
            console.log(this.open)
        },

        changeValueOpen(open){
            this.open = open

        }

    },
    created() {
       this.user = this.$store.dispatch('getUser');
    },
}

</script>

<style>
    :root{
        --primary-color: #ff5d26
    }

    ul , li {
        list-style: none;
        margin: 0;
    }
    a {
        color: #2e3336;
    }

    .header .info_nav{
        font-size: 14px;
    }

    .header .contact:hover{
        color: #f19171;
    }

    .navbarCommpent{
        position: static;

    }

    .navbar{
        background-color: #fff;
        /* padding: 3.5px 6px; */
        color: #2e3336;
        transition: all .3s ease-in-out;
    }

    .App.fix_body #navbar-menu {
        position: fixed;
        min-width: 100%;
        background-color: #ffff;
        top: 0;
        z-index: 9998;
        padding: 10px 10px 8px 10px;
        box-shadow: rgba(0, 0, 0, 0.02) 0px 1px 3px 0px, rgba(27, 31, 35, 0.15) 0px 0px 0px 1px;
    }

    .navbar .navbar-brand{
        width: 120px;
    }

    .navbar .iconBars{
        display: none;
        position: absolute;
        top: 20px;
        bottom: 0;
        right: 20px;
        color: #2e3336;
        font-weight: 500;
    }

    .navbar .navbar-brand img {
        width: 100%;
    }

    .navbar_body button .post_new{
        background-color:#ff5d26;
        color: #fff;
        border: none;
        font-weight: 500;
        border-radius: 8px;
        margin-left: 20px;
        cursor: pointer;
    }

    .navbar_body button .post_new:hover{
        background-color: #f19171;
    }

    .navbar_body .navbar_link{
        position: static;
    }

    .navbar_body .userLogin{
        position: relative;
        top: 4px;
    }

    .navbar_body .navbar_link .nav-item a{
        color: #a2a0a0;
        font-weight: 0;
    }

    .navbar_body .navbar_link .nav-item a:hover{
        color: var(--primary-color);
    }

    .sidebarOpen{
        display: none;
    }

    .dropdown-item.active, .dropdown-item:active {
        color: #ff5d26;
        text-decoration: none;
        background-color: #e9ecef;
    }

    @media only screen and (max-width: 992px) {
        .sidebarOpen{
            display: block;
        }

        .navbar .iconBars{
            display: block;
        }

        .navbar_body .small_menu{
            display: none;
        }
    }

    @media only screen and (max-width: 768px) {
        .dropdown-menu[data-bs-popper] {
            top: 100%;
            right: 0;
            margin-top: .125rem;
        }

        .dropdown-menu{
            font-size: 12.5px;
            right: 0;
        }

        .header .info_nav{
            font-size: 10px;
        }

        .navbar_body .navbar_link{
            position: absolute;
            padding: 0 0 10px 0px;
            width: 100%;
            top: 100%;
            transition-duration: 500ms;
            transition-timing-function: ease-in;
            cursor: pointer;
            z-index: 99999;

        }

        .navbar_body .navbar_link{
            left: -100%;
            position: absolute;
            /* background-color: #fff; */
        }

        .navbar_body .navbar_link::before{
            background-color: rgb(0, 0, 0,0.2);
            width: 100vw;
            height: 100vh;
        }

        .navbar_body .navbar_link.dropdown{
            left: 0;
        }

        .navbar_body .navbar_link li:hover a {
            background-color: #f19171;
            border-radius:8px;
            color: #fff;
            cursor: pointer;
        }


    }

    @media only screen and (max-width: 600px) {

        .dropdown-menu[data-bs-popper] {
            top: 100%;
            right: 0;
            margin-top: .125rem;
        }

        .dropdown-menu{
            font-size: 12.5px;
            right: 0;
        }

        .avatar_name .avatar .image_avatar_post {
            width: 30px;
            height: 30px;
        }

        .header .info_nav{
            font-size: 45%;
        }

        .header div > div{
            font-size: px;
            padding: 0;
        }

        .navbar_body .have_user{
            display: none;
        }

        .navbar>.container{
            flex-wrap: nowrap;
        }
    }

    @media only screen and (max-width: 477px) {

        .dropdown-menu{
            font-size: 13.5px;
            right: 0;
        }
    }

</style>
