<template>
    <div class="menu" :class="[{ 'small-menu': smallMenu },showSidebar?'show':'hide']">
        <div class="header_sidebar">
            <div class="brand-link">
                <span class="brand-text font-weight-light">
                    <h5>
                        <span class="avatar inline-block">
                            <img class="image_avatar_user" src="../../assets/image/chutruong.png" alt="" style="height:2rem;width:2rem;">
                        </span>
                        Chủ Trương
                    </h5>
                    <h6>
                        An Toàn Và Chất Lượng
                    </h6>
                </span>
            </div>
            <div class="sidebarPost d-flex align-items-center mb-1" v-if="authUser">
                <div class="avatar inline-block" style="padding: 0 .5rem;">
                    <img v-if="authUser.avatar" :src="`http://localhost:8001${authUser.avatar}`" alt="avatar" class="image_avatar_user">
                    <img v-else class="image_avatar_user" src="../../assets/image/avatar/profile.jpg" alt="">
                </div>
                <div class="name d-block" >
                    <div >{{authUser.username}}</div>
                    <div class=""><small>{{authUser.email}}</small></div>
                </div>
            </div>
            <div v-else class="sidebarPost  d-flex justify-content-between align-items-center px-3 mb-1">
                <div class="btn_Login">
                    <a class="nav-link d-flex align-items-center" href="/login" >
                        <div class="d-flex align-items-center">
                            <i class="bi bi-person-plus-fill"></i>
                        </div>
                        <small><span class="ps-1">Đăng Nhập</span></small>
                    </a>
                </div>
                <div class="btn_Register">
                    <a class="nav-link d-flex align-items-center" href="/signup" >
                        <div class="d-flex align-items-center">
                            <i class="bi bi-box-arrow-in-right"></i>
                        </div>
                        <small><span class="ps-1">Đăng Kí</span></small>
                    </a>
                </div>
            </div>
        </div>

        <SidebarItem v-for="(item, index) in menuTree.user" :key="index" :data_children="item.children" :data="item" :depth="0" :smallMenu="smallMenu"/>
        <div v-if="authUser && authUser.role[0] === 'ADMIN'">
             <SidebarItem v-for="(item, index) in menuTree.admin" :key="index" :data_children="item.children" :data="item" :depth="0" :smallMenu="smallMenu"/>
        </div>
        <SidebarItem v-for="(item, index) in menuTree.logout" :key="index" :data_children="item.children" :data="item" :depth="0" :smallMenu="smallMenu"/>
        <i @click="smallMenu = !smallMenu" class="fa-solid fa-bars"></i>
    </div>
    <div class="overlay" @click="sidebarOpen" :class="{show:showSidebar}" id="overlay"></div>

</template>

<script>
import { ref, defineComponent } from 'vue'
import {mapState,mapGetters,mapActions} from 'vuex'
import SidebarItem from './SidebarItem.vue';

import menuApi from '../../Api/menuApi';

export default {
    name: 'recursive-menu',
    components: {
        SidebarItem
    },
    computed: {
        ...mapGetters(['authUser'])
    },
    props: {
        open: Boolean
    },
    watch: {
        open(value){
            this.showSidebar = value
        }
    },
    data (){
        const showSidebar = ref(false)
        const smallMenu = ref(false);
        const menuTree = ref([])
        return {
            showSidebar,
            smallMenu,
            menuTree,
        }
    },
    methods: {
        sidebarOpen(){
            this.showSidebar = !this.showSidebar
            this.$emit('changeValueOpen',!this.open)
        }
    },
    created (){
        // this.user = this.$store.dispatch('getUser');
        this.menuTree = menuApi.Menus();
    }
}
</script>

<style scoped>
    .menu {
        position: fixed;
        height: 100vh;
        width: 280px;
        left: 0;
        top: 0;
        border-right: 1px solid #ececec;
        background-color: #fff;
        box-shadow: rgba(0, 0, 0, 0.02) 0px 1px 3px 0px, rgba(27, 31, 35, 0.15) 0px 0px 0px 1px;
        transition: all .3s ease;
        overflow: auto;
        z-index: 8;
    }

    .menu i {
        position: static;
        font-size: 17px;
        user-select: none;
        cursor: pointer;
        transition: all .3s ease;
    }

    .menu i.fa-solid.fa-bars{
        position: fixed;
        left: 290px;
        font-size: 17px;
        top: 15px;
        user-select: none;
        cursor: pointer;
        transition: all .3s ease;
    }

    .small-menu {
        overflow: inherit;
        width: 60px;
        padding-top: 50px;
    }

    .small-menu  i {
        left: 20px;
    }

    .menu.small-menu i.fa-solid.fa-bars{
        position: fixed;
        left: 22px;
        top: 15px;
        font-size: 22px;
    }

    .menu .avatar img{
        height: 3rem;
        width: 3rem;
        border-radius: 100rem;
        object-fit: cover;
    }

    .menu .sidebarPost .btn_Register a{
        background-color: #ff5d26;
        border-radius: 8px;
        color: #fff;
    }

    .menu .sidebarPost .btn_Login a{
        border: 1px solid #ff5d26;
        color: #ff5d26;
        border-radius: 8px;
    }

    .menu .sidebarPost .name div:nth-child(1){
        font-size: 18px;
        line-height: 28px;
        font-weight: 500;
        letter-spacing: -0.2px;
        color: rgb(44, 44, 44);
    }

    .menu .sidebarPost .name div:nth-child(2){
        font-size: 15px;

    }

    .menu.small-menu .avatar img{
        height: 2rem;
        width: 2rem;
        border-radius: 100rem;
        object-fit: cover;
    }

    .menu .sidebarPost{
        padding: 1rem 0rem 1rem 0rem;
        color: black;
        margin: 0 5px;
        border-bottom: 1px solid #cdcdcd;

    }

    .menu .brand-link{
        /* width: 250px;
        color: #c2c7d0; */
        display: block;
        font-size: 1.25rem;
        line-height: 1.5;
        padding: 1rem;
        transition: width .3s ease-in-out;
        white-space: nowrap;
        text-align: center;
        border-bottom: 1px solid #cdcdcd;
    }

    .menu.small-menu .sidebarPost .name,
    .menu.small-menu .brand-link {
        display: none;
        opacity: 0;
     }

    .overlay {
        position: fixed;
        width: 100vw;
        height: 100vh;
        background-color: black;
        top: 0;
        left: 0;
        opacity: 0.5;
        z-index: 7;
        display: none;
    }

    .hide{
        display: block;
    }

     @media only screen and (min-width: 1200px) {

        .menu {
            position: sticky;
        }
    }

     @media only screen and (max-width: 992px) {
        .hide{
            display: none;
        }

        .menu{
            right: 0;
            left: auto;
        }
        .menu i.fa-solid.fa-bars{
            display: none;
        }

        .menu.show{
            display: block;
            transition: margin-right 2s;
        }

        .overlay.show{
            display: block;
        }


    }
</style>
