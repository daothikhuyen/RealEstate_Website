<template>
    <div>
        <div class="post px-2 py-3 bg-white mb-2" v-for="(listPost, index) in infoPost.result" :key="index">
            <Poster :user="listPost.user" :created_at="listPost.postData.created_at"></Poster>
            <PostContent :postData="listPost.postData" :images="listPost.images" :extensions="listPost.extensions" :videos="listPost.videos"></PostContent>
            <Comment :postId="listPost.postData.id" :numberLike="listPost.postData.number_like" :like="rattig_post.result"></Comment>
        </div>
    </div>
</template>

<script>

import { ref, defineComponent } from 'vue'
import {mapGetters,mapActions} from 'vuex'

import postApi from "../../Api/postApi"
import rattig_post from "../../Api/ratting_like"

import Poster from "./Poster.vue"
import PostContent from "./Content.vue"
import Comment from './comment/index.vue'

export default defineComponent(
    {
        name: "HomeComponent",
        components: {
            Poster,
            PostContent,
            Comment,
        },
        computed:{
            ...mapGetters(['authUser'])
        },
        props: {
            posts: Object
        },
        data() {
            const commentData = ref(null)
            const showWriteComment = ref(true)
            const rattig_post = ref([])
            return {
                infoPost: [],
                imagePost: [],
                user: [],
                treeFeedback: [],
                commentData,
                showWriteComment,
                rattig_post
            }
        },
        watch: {
            posts: {
                handler(newPosts) {
                    
                    this.infoPost = newPosts;
                    console.log(this.infoPost)
                },
                deep: true,
                immediate: true,
            },
        },
        methods: {

        },
        async created() {
            this.infoPost = await postApi.listPost();
            this.rattig_post = await rattig_post.getLikePost()
        }
    }
)


</script>
<style>
    .post {
        border-radius: 8px;
        box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 0px 1px;
    }

    @media only screen and (max-width: 477px) {
        .main_home .post {
            border-radius: 0px;
            box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 0px 1px;
        }
    }
</style>
