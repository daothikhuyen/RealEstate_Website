
<template>      
    <div class="menu-item" :class="{ opened: expanded }" v-if="data">
        <div v-if="data.title != 'Đăng xuất'">
            <router-link v-if="data.url !== ''"  :to="data.url" class="label" @click="toggleMenu()" :style="{ paddingLeft: depth * 20 + 20 + 'px' }" >
                <div class="left">
                    <i  v-if="data" :class="data.icon" class="material-icons-outlined"></i>
                    <span v-if="showLabel">{{ data.title }}</span>
                </div>
                <div v-if="data_children" class="right">
                    <i class="expand fa-solid fa-chevron-down" :class="{ opened: expanded }"></i>
                </div>
            </router-link>
            <div  v-else>
                <!-- <div v-if="data.title == 'admin'">
                    <menu-item :class="{ opened: showChildren }" v-for="(item, index) in data_children" :key="index" :data_children="item.children" :data="item" :depth="0" :smallMenu="smallMenu"/>
                </div> -->
                <div class="label" :class="{'focus_click' : expanded}"  @click="toggleMenu()" :style="{ paddingLeft: depth * 20 + 20 + 'px' }">
                    <div class="left">
                        <i  v-if="data" :class="data.icon" class="material-icons-outlined"></i>
                        <span v-if="showLabel">{{ data.title }}</span>
                    </div>
                    <div v-if="data_children" class="right">
                        <i class="expand fa-solid fa-chevron-down" :class="{ opened: expanded }"></i>
                    </div>
                </div>
            </div>
        </div>
        <div v-else @click.prevent="logoutWeb()" class="label">
            <div class="left">
                <i  v-if="data" :class="data.icon" class="material-icons-outlined"></i>
                <span v-if="showLabel">{{ data.title }}</span>
            </div>
        </div>
        <div v-show="showChildren" :class="{ 'small-menu': smallMenu }" class="items-container" :style="{ height: containerHeight }" ref="container">
            <menu-item :class="{ opened: showChildren }" v-for="(item, index) in data_children" :key="index" :data_children="item.children" :data="item" :depth="depth + 1" :smallMenu="smallMenu"/>
        </div>
    </div>
</template>

<script>
import {ref,defineComponent } from 'vue'
import {mapState,mapGetters,mapActions} from 'vuex'
import Swal from 'sweetalert2'

export default {
    name: "menu-item",
    data: () => ({
        showChildren: false,
        expanded: false,
        containerHeight: 0,
    }),
    props: {
        data : Array,
        data_children: Array,
        depth: Number,
        smallMenu: Boolean,
        role: Number
    },
    computed: {
        showLabel() {
            return this.smallMenu ? this.depth > 0 : true;
        }
    },
    methods: {
        toggleMenu() {
            this.expanded = !this.expanded;
            if (!this.showChildren) {
                this.showChildren = true;
                this.$nextTick(() => {
                    this.containerHeight = this.$refs["container"].scrollHeight + "px";
                    setTimeout(() => {
                        this.containerHeight = "fit-content";
                        if(navigator.userAgent.indexOf("Firefox") != -1)
                            this.containerHeight = "-moz-max-content"
                            this.$refs["container"].style.overflow = "visible";
                    }, 300);
                });
            } else {
                this.containerHeight = this.$refs["container"].scrollHeight + "px";
                this.$refs["container"].style.overflow = "hidden";
                setTimeout(() => {
                    this.containerHeight = 0 + "px";
                }, 10);
                setTimeout(() => {
                    this.showChildren = false;
                }, 300);
            }
        },

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
        }
    },
};
</script>

<style scoped>
.menu-item {
    position: relative;
    width: 100%;
}

.menu-item .label {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    white-space: nowrap;
    user-select: none;
    height: 50px;
    padding: 0 20px;
    box-sizing: border-box;
    font-size: 15px;
    color: black;
    transition: all 0.3s ease;
    margin: 5px auto;
    line-height: 1;
}

.menu-item .label.router-link-active.router-link-exact-active{
    border-left:5px solid #d36544 ;
    background-color: rgb(242, 242, 242);
    font-weight: 500;
}

.menu-item .label > div {
    display: flex;
    align-items: center;
    gap: 10px;
    color: rgb(44, 44, 44);;
}

.menu-item .label i {
    font-size: 20px;
    color: rgb(44, 44, 44);;
    transition: all 0.3s ease;
    /* color: #fff; */
}

.menu-item .label i.expand {
    font-size: 14px;
    color: #cacaca;
}

.menu-item .label i.expand.opened {
    transform: rotate(180deg);
}

.menu-item .label.focus_click span,
.menu-item .label.focus_click i{
    color: #a7482b;
    text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.25);
}

.menu-item .label.small-item {
    width: fit-content;
}

.menu-item .label:hover {
    background-color: rgb(242, 242, 242);
    text-decoration: none;
    cursor: pointer;
}

.menu-item .items-container {
    width: 100%;
    left: calc(100% + 6px);
    transition: height 0.3s ease;
    overflow: hidden;
}

.menu-item .items-container.small-menu {
    width: fit-content;
    position: absolute;
    background: #fff;
    box-shadow: rgba(0, 0, 0, 0.02) 0px 1px 3px 0px, rgba(27, 31, 35, 0.15) 0px 0px 0px 1px;
    top: 0;
}

.menu-item .items-container.small-menu .label {
    width: 100% !important;
    padding-left: 20px !important;
}

</style>
