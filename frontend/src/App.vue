// App.vue

<template>
  <div class="App" :class="{'fix_body' : isScroll}">
    <!-- <navbar-component></navbar-component> -->
    <transition name="fade">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
import { ref,onMounted,onUnmounted, defineComponent } from 'vue'
import NavbarComponent from './components/NavbarComponent.vue';

export default {
    components: {
        NavbarComponent
    },
    data() {
        const isScroll = ref(false)
        return {
            isScroll
        }
    },
    methods: {
        handleScroll() {
            this.isScroll = window.scrollY > 0
        },

        // myEventHandler(){
        //     window.location.reload()
        // }
    },

    destroyed(){
        window.removeEventListener('scroll', this.handleScroll)
        // window.removeEventListener("resize", this.myEventHandler);
    },

    created() {
        window.addEventListener('scroll', this.handleScroll)
        // window.addEventListener("resize", this.myEventHandler);
    },
}
</script>
