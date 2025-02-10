<template>
    <div class="ps-4">
        <div class="comment_admin" @click="deletedFeedback(data.id)">
            <div class="d-flex align-items-center">
                <div class="avatar">
                    <div class="avatar_comment">
                        <img v-if="data.user != null && data.user.avatar != null" :src="data.user.avatar" alt="" class="avatar">
                        <img v-else src="../../../../assets/image/avatar/profile.jpg" alt="" class="avatar">
                    </div>
                </div>
                <div class="content ps-2">
                    <div class="username">
                        <span>{{data.user.username}}</span>
                        <span class="date">{{date}}</span>
                    </div>
                    <div class="comment">
                       {{data.comment}}
                    </div>
                </div>
            </div>
            <div class="icon_trash">
                <i class="fa-solid fa-xmark fa-xs"></i>
            </div>
        </div>
        <feebackChidrenAdmin v-for="item in data.feedback" :key="item.id" :data="item" :destroyFeedback="destroyFeedback"></feebackChidrenAdmin>
    </div>
</template>

<script lang="ts">
import { ref,defineComponent } from 'vue'
import { useDateFormat, useNow } from '@vueuse/core'
import { date } from 'yup'

import adminApi from '../../../../Api/adminApi';

export default defineComponent({
    name: 'feebackChidrenAdmin',
    props: {
        data: Object,
        destroyFeedback: Function
    },
    watch: {
    },
    mounted(){
        this.formatDate(this.data.created_at)
    },
    data(){
        const date = ref(null)

        return {
            date
        }
    },
    methods: {
        formatDate(value){
            this.date = useDateFormat(value,'DD-MM-YYYY')
        },

        async deletedFeedback(id){

            this.destroyFeedback(id)
        }
    }
})
</script>

<style scoped>
    .comment_admin {
        position: relative;
        display: inline-block;
        width: auto;
        /* max-width: 500px; */
        height: auto;
        padding: 5px 30px 5px 5px;
        margin: 5px;
        border: 1px solid #DCDCDC;
        box-shadow: rgba(9, 30, 66, 0.25) 0px 1px 1px, rgba(9, 30, 66, 0.13) 0px 0px 1px 1px;
        border-radius: 10px;
    }

    .comment_admin .avatar_comment .avatar{
        width: 40px;
        height: 40px;
        border: 1px solid #DCDCDC;
        border-radius: 50%;
    }

    .comment_admin .content .username{
        font-weight: 500;
    }

    .comment_admin .content .username .date{
        font-weight: 400;
        font-size: 10px;
        padding-left: 5px;
        color:#2e3336;
    }

    .content .comment{
        padding-left: 10px;
    }

    .comment_admin .icon_trash {
        position: absolute;
        top: 0;
        right: 0;
        padding: 0px 5px;
    }

    .comment_admin .icon_trash i {
        cursor: pointer;
    }

</style>
