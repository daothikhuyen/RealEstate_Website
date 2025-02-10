<template>
    <div>
        <div class="appChildren">
            <div>
                <sidebar/>
            </div>
            <div class="homeManager px-0">
                <navbar-component></navbar-component>
                <div class="router_view">
                    <router-view></router-view>
                    <div class="footer_index">
                        <footer-component-vue></footer-component-vue>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
import { ref, defineComponent } from 'vue'
import Sidebar from './Sidebar.vue'
import FooterComponentVue from '../FooterComponent.vue'
import NavbarComponent from '../NavbarComponent.vue'

export default defineComponent({
    components:{
        Sidebar,
        FooterComponentVue,
        NavbarComponent
    },
    data(){

    }
})
</script>

<style scoped>
    .navbarCommpent {
        /* border: 1px solid black; */
        box-shadow: rgba(17, 17, 26, 0.1) 0px 1px 0px;
    }

    .appChildren {
        display: flex;
        background-color: #fff;
        padding-left: 3.2rem;
        height: 100vh;
    }

    .homeManager{
        width: 100%;
        /* padding-bottom: 20px; */
    }

    @media (max-width: 1024px) {
        .homeManager {
            padding-left: 6px;
        }
    }

    .header_navbar.logo_info{
        width: 100px;
        height: 100px;
    }

    .navbar{
        box-shadow: rgba(33, 35, 38, 0.2) 0px 10px 10px -10px;
    }


    #navbar-menu {
        display: block;
        width: 100%;
        background-color: #fff;
    }

    .header_navbar .navbar .navbar-brand{
        width: 120px;
    }

    .navbar .collapse .post_new{
        background-color: var(--primary-color);
        color: #fff;
        font-size: 15px;
        font-weight: 500;
        border: 3px solid #d95230;
        border-radius: 10px;
    }

    .footer_admin {
        border-top: 1px  solid transparent ;
        box-shadow: rgba(0, 0, 0, 0.06) 0px 2px 4px 0px inset;
    }

     @media only screen and (min-width: 1200px) {
        .appChildren {
            max-height: 100vh;
            overflow: hidden;
            padding-left: 0rem;
        }

        .homeManager {
            min-height: 100vh;
            overflow-y: scroll;
        }
    }

    @media only screen and (max-width: 992px) {

        .homeManager {
            padding-left: 0px;
        }
    }

    @media only screen and (max-width: 992px) {
        .appChildren {
            padding-left: 0rem;
        }
    }


</style>
