<template>
    <div class="content_writeComment">
        <div class="d-flex align-items-center">
            <div class="user_writeComment">
                <div class="avatar_comment border rounded-circle">
                    <img v-if="authUser != null && authUser.avatar != null" :src="authUser.avatar" alt="" class="avatar">
                    <img v-else src="/storage/uploads/2024/06/04/profile.jpg" alt="" class="avatar">
                </div>
            </div>
            <form action="" class="fromSubmitComment w-100" @submit.prevent="onAddComment">
                <input type="text" class="inputComment w-100" placeholder="Viết bình luận..." ref="input" v-model="commentContent">
                <button class="btn_send" type="submit" >
                    <i class="bi bi-send-fill" style="color:#ea4e27 "></i>
                </button>
            </form>
        </div>
    </div>
</template>
<script>
import { ref, defineComponent } from 'vue'
import {mapGetters,mapActions} from 'vuex'

export default defineComponent({
    name: "AddComment",
     computed: {
        ...mapGetters(['authUser'])
    },
    props:{
        add: Function,
        postId: Number,
        nodeData: Object,
        showReply: Boolean,

    },
    data(){
        const commentContent = ref(null)
        return{
            commentContent
        }
    },
    methods:{
        onAddComment(){

            this.add(this.postId, this.nodeData, this.commentContent,this.showReply)
            this.commentContent = ""
        }
    }
})
</script>
<style scoped>
.writeComment {
    border: none;
    box-shadow: none;
    padding-top: 10px;
    margin: 0 0 20px 0px;
}

.writeComment .content_writeComment {
    width: 95%;
    height: auto;
    max-height: 70px;
    overflow-y: auto;
}

.writeComment .content_writeComment .fromSubmitComment {
    display: flex;
    align-items: center;
    margin-left: 15px;
    background-color: rgb(119, 119, 119, 0.20);
    padding: 10px 5px 10px 20px;
    border-radius: 10px;

}

.TimeAndReply .reply {
    cursor: pointer;
}
</style>
