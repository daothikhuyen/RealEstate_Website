<template>
  <transition name="modal-animation">
    <div v-show="modalActive" class="modal">
        <transition name="modal-animation-inner modal-dialog">
            <div v-show="modalActive" class="modal-inner modal-content">
                <div class="modal-header">
                    <h5><small>Yêu cầu xoá tài khoản</small></h5>
                    <button type="button" class="btn-close bg-white" @click="close()" ></button>
                </div>
                <!-- Modal Content -->
                <slot />
               <div class="modal-footer">
                    <button @click="close()" type="button" class="btn">Huỷ Bỏ</button>
                    <button @click="delete_account()" type="button" class="btn">Gửi yêu cầu</button>
               </div>
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

export default defineComponent({
    computed: {
        ...mapGetters(['authUser'])
    },
    props: {
        modalActive: Boolean
    },
    date() {
        return {
        }
    },
    methods: {
        close(){
            this.$emit("close");
        },

        async delete_account(){
            const response = await userApi.delete_account();

            if(response.code == 1000){
               Swal.fire({
                    title: "Thông Báo!",
                    text: "Tài Khoản Của Bạn Đã bị Xoá",
                    icon: "success",
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    this.$store.dispatch('logout');
                });
            }else{
                Swal.fire({
                    title: "Thông Báo!",
                    text: "Tài Khoản Đã Gặp Lỗi",
                    icon: "error",
                    showConfirmButton: false,
                    timer: 1500
                }).then((result) => {
                    this.$store.go(0);
                });
            }
        }
    }
})

</script>

<style scoped>
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
        max-width: 550px;
        width: 80%;
        box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
        border-radius: 8px ;
        background-color: #fff;
        padding: 10px 16px;
    }

    .modal .modal-inner i {
        position: absolute;
        top: 15px;
        right: 15px;
        font-size: 20px;
        cursor: pointer
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
