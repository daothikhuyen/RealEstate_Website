<template>
    <transition name="modal-animation">
        <div v-show="modalActive" class="modal">
            <transition name="modal-animation-inner modal-dialog">
                <div v-show="modalActive" class="modal-inner modal-content">
                    <div class="modal-header">
                        <h5><small>Khôi Phục Tài Khoản</small></h5>
                        <button type="button" class="btn-close bg-white" @click="close()" ></button>
                    </div>
                    <!-- Modal Content -->
                    <slot/>
                </div>
            </transition>
        </div>
    </transition>
</template>

<script>
import { ref,defineComponent } from 'vue'
import Swal from 'sweetalert2'
import {mapState,mapGetters,mapActions} from 'vuex'

import userApi from '../../Api/userApi'
import { boolean } from 'yup'


export default defineComponent({
     props: {
        modalActive: Boolean
    },
    data() {
        return {
            showHide: true
        };
    },
    computed: {
        ...mapGetters(['authUser'])
    },
    methods: {
        close(){
            this.$emit("close");
        },

    },
    created() {
        this.showHide = true;
    }
})

</script>

<style scoped>

    .hide{
        display: none;
    }

    .modal-animation-enter-active,
    .modal-animation-leave-active {
        transition: opacity 0.3s cubic-bezier(0.52, 0.02, 0.19, 1.02);
    }

    .modal-animation-enter-from,
    .modal-animation-leave-to {
        opacity: 0;
    }

    .modal-animation-inner-enter-active {
        transition: all 0.3s cubic-bezier(0.52, 0.02, 0.19, 1.02) 0.15s;
    }

    .modal-animation-inner-leave-active {
        transition: all 0.3s cubic-bezier(0.52, 0.02, 0.19, 1.02);
    }

    .modal-animation-inner-enter-from {
        opacity: 0;
        transform: scale(0.8);
    }

    .modal-animation-inner-leave-to {
        transform: scale(0.8);
    }

    .modal {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        width: 100vw;
        position: fixed;
        top: 0;
        left: 0;
        background-color: rgba(0, 0, 0, 0.5);
    }

    .modal-header {
        display: flex;
        flex-shrink: 0;
        align-items: center;
        justify-content: space-between;
        padding: .5rem 1rem;
        border-bottom: 1px solid #dee2e6;
        border-top-left-radius: calc(.3rem - 1px);
        border-top-right-radius: calc(.3rem - 1px);
    }

    .modal-footer {
        display: flex;
        flex-wrap: wrap;
        flex-shrink: 0;
        align-items: center;
        justify-content: flex-end;
        padding: .5rem;
        border-top: 1px solid #dee2e6;
        border-bottom-right-radius: calc(.3rem - 1px);
        border-bottom-left-radius: calc(.3rem - 1px);
    }

    .modal .modal-inner {
        position: relative;
        max-width: 450px;
        width: 80%;
        box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
        border-radius: 8px ;
        background-color: #fff;
        padding: 10px 16px;
    }

    /* .modal .modal-inner i {
        position: absolute;
        top: 15px;
        right: 15px;
        font-size: 20px;
        cursor: pointer
    } */

    .modal .body-send{
        padding: 10px 10px;
        border: 1px solid #dcdcdc;
        border-radius: 8px ;
        margin: 10px 0;
        cursor: pointer;
        transition: all .3s ease-in;
    }

    .modal .body-send:hover{
        box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    }

    .modal .model-body .img_success {
        width: 100%;
        text-align: center;
        transition: all .3s ease-out;
    }

    .modal .model-body .img_success img{
        width: 100px;
        max-width: 100%;
        object-fit: cover;
        object-position: center;
    }
    .modal .modal-footer button:nth-child(2) {
        /* margin: 10px; */
        padding: 10px 20px;
        border: none;
        font-size: 16px;
        background-color: rgb(233, 93, 17);
        color: #fff;
        float: end;
        cursor: pointer;
    }

    .modal .modal-footer button:nth-child(1){
        /* margin: 10px; */
        padding: 10px 20px;
        border: none;
        font-size: 16px;
        color: black;
        border: 1px solid #dee2e6d2;
        float: end;
        cursor: pointer;
    }

    .modal .modal-footer button:nth-child(2):hover{
        background-color: rgb(233, 93, 17,0.75);
    }

    .modal .modal-footer button:nth-child(1):hover{
         background-color: rgba(222, 226, 230, 0.6);
    }
</style>
