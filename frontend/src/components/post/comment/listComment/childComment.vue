<template>
    <li class="content_feedback">
        <div class="show_writeComment d-flex" :id="nodeData.id">
            <div class="avatar_name d-flex ">
                <div class="avatar inline-block">
                    <img  v-if="nodeData['user'].avatar != null" :src="`http://localhost:8001${nodeData['user'].avatar}`" class="image_avatar_feedback">
                     <img v-else src="../../../../assets/image/avatar/profile.jpg" alt="" class="image_avatar_feedback">
                </div>
            </div>
            <div class="NameAndFeedback w-100 pe-3">
                <div class="node-tree d-flex align-items-center mb-1">
                    <div class="name px-2">
                        <div class="px-2 py-1 rounded-3" style=" background-color: rgb(119, 119, 119,0.10);">
                            <div class="fw-semibold">{{nodeData['user'].username}}</div>
                            <div class="show_content_feedback">
                                <div class="content">
                                    {{ nodeData.comment}}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="TimeAndReply ps-3">
                    <div class="d-flex mt-1 ms-1">
                        <div class="created_time">
                            {{ date }}
                        </div>

                        <div class="reply ps-4"  @click.prevent="like_feedback(nodeData.id)">
                           <span :class="{like_color: showLike}"> Thích </span>
                            <sub> {{numbe_like_feedback != 0 ? numbe_like_feedback: ""}} </sub>
                        </div>
                        <div class="reply ps-4" @click.prevent="relpyComment()">
                            Trả lời
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <ul v-if="nodeData.feedback && nodeData.feedback.length ">
            <FeedBackNode v-for="childNodeData in childCommentData"  :key="childNodeData.id" :nodeData="childNodeData" :add="add" :authUser="authUser"></FeedBackNode>
        </ul>
    </li>
    <div class="writeComment" :class="{hide : !showReply}">
        <div class="d-flex align-items-center">
            <AddComment :add="add" :postId="nodeData.post_id" :nodeData="nodeData" :showReply="showReply"></AddComment>
        </div>
    </div>
</template>

<script>
import { ref, defineComponent } from 'vue'
import { useDateFormat, useNow } from '@vueuse/core'
import Swal from 'sweetalert2'

import userApi from '../../../../Api/userApi'
import ratting_api from '../../../../Api/ratting_like'
import AddComment from '../AddComment.vue'

export default defineComponent ({
    name: "FeedBackNode",
    props: {
        nodeData: Object,
        add: Function,
        authUser: Object,
    },
    components:{
        AddComment,
    },
    mounted(){
        this.formatDate(this.nodeData.createdAt)
    },
    data() {
        const commentData = ref(null)
        const showReply = ref(false)
        const showLike = ref(false)
        const date = ref(null)
        const numbe_like_feedback = ref(null)
        const like = ref({})

        return {
            treeFeedback: [],
            showReply,
            commentData,
            date,
            showLike,
            numbe_like_feedback,
            like
        }
    },
    methods: {

        formatDate(value){
            this.date = useDateFormat(value, 'DD-MM-YYYY')
        },

        relpyComment(index){
            this.showReply = true
        },

        async like_feedback(feedBackId){

            if(this.authUser != null){
                const like = {
                    userId : this.authUser.id,
                    feedbackId : feedBackId,
                    liked : 1
                }
                const result = await ratting_api.createLikeFeedback(like)

                if(result.code === 1000){
                    this.showLike = !this.showLike
                    this.numbe_like_feedback += result.result.status==="like" ? 1: -1
                }
            }else{
                    Swal.fire({
                    title: "Thông Báo!",
                    text: "Vui lòng đăng nhập",
                    icon: "info",
                    confirmButtonText: "Đăng nhập",
                }).then((result) => {
                    if (result.isConfirmed) {
                        this.$router.push('/login')
                    }

                })
            }
        },

        loadLike(){
            if(this.like.code == 1000){
                this.like.result.forEach(element => {

                    if(this.authUser){
                        if(element.feedbackId == this.nodeData.id ){
                            if(element.userId == this.authUser.id){
                                this.showLike = !this.showLike
                            }
                        }
                    }
                  
                    this.numbe_like_feedback = this.numbe_like_feedback + 1;
                });
            }
        }

    },
    computed: {
        childCommentData(){
            return this.nodeData.feedback;
        },
    },
    async created() {
        this.like = await ratting_api.getLikeFeedback(this.nodeData.id)
       
        this.loadLike()
    },
})
</script>

<style scoped>
.writeComment {
    border:none;
    box-shadow: none;
    padding-top: 10px;
    margin: 0 0 20px 0px;
}

.writeComment .content_writeComment{
    width: 95%;
    height: auto;
    max-height: 70px;
    overflow-y: auto;
}

.writeComment .content_writeComment .fromSubmitComment{
    display: flex;
    align-items: center;
    margin-left: 15px;
    background-color: rgb(119, 119, 119,0.20);
    padding: 10px 5px 10px 20px;
    border-radius: 10px;

}

.TimeAndReply .reply{
    cursor: pointer;
}

.like_color {
    color: #ff5d26;
}

</style>
