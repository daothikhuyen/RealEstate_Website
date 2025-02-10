// view list post

<template>
    <div class="container-fluid">
        <div class="listFeedback mt-5">
            <div class="title_main w-100"  style="">
                <h5 class="ps-3 pt-1 text-white">Bình luận: </h5>
            </div>
            <section>
                <section>
                    <div class="text-center py-3" v-if="list_feedback.length === 0">
                       Hiện chưa có bình luận
                    </div>
                    <div v-else>
                        <feebackChidrenAdmin v-for="item in list_feedback" :key="item.id" :data="item" :destroyFeedback="destroyFeedback"></feebackChidrenAdmin>
                    </div>
                </section>
            </section>
            <div class="loading">
                <div :class="{'d-block': !loading}"></div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, defineComponent } from 'vue'
import { Bootstrap5Pagination } from 'laravel-vue-pagination';
import {mapState,mapGetters,mapActions} from 'vuex'
import Swal from 'sweetalert2'

import feebackChidrenAdmin from './chidren/list_feedbackChildren.vue'
import adminApi from '../../../Api/adminApi'

import Sidebar from '../Sidebar.vue'

export default defineComponent({
    name: "User Management",
    computed:{
        ...mapGetters(['authUser']),
    },
    components: {
        feebackChidrenAdmin,
        Sidebar,
        Bootstrap5Pagination
    },
    data() {
        const list_feedback = ref([])
        const loading = ref(true)

        return {
            list_feedback,
            loading
        }
    },
    methods : {

        async load_Feedback(page = 1){
            const response =  await adminApi.list_feedback(page,this.$route.params.id);
            if(response.code == 1000){
                this.list_feedback = response.result
            }

        },

        async destroyFeedback(id,index){
            this.loading = !this.loading
            const response = await adminApi.destroyFeedback(id);
            if(response.code== 1000){
                this.loading = !this.loading
                this.load_Feedback();
            }
        }

    },

    async created(){
        this.load_Feedback()
    }
})
</script>

<style scoped>

    .listFeedback{
        background-color: #ffff;
    }

    .table>:not(:last-child)>:last-child>*{
        font-size: 15px;
    }

    .listFeedback .title_main{
        background-color:#ff5d26;
        padding: 6px 3px ;
        border-radius:8px ;
        /* border-top-right-radius: 1rem ;
        border-top-left-radius: 1rem ; */

    }

    .listFeedback .list_table{
        padding: 1rem;
        margin-top: 4rem;

    }

    .loading div{
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        z-index: 99999;
        overflow: hidden;
        width: 40px;
        max-height: 40px;
        margin: auto;
        border: 4px solid #45474b;
        border-top-color: #ffff ;
        border-radius: 50%;
        border-style: dotted;
        animation: spin 1.5s infinite linear;
    }

</style>
