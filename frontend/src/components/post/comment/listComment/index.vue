<template>
    <ul class="show_feedback">
        <FeedBackNode v-for="comment in treeData" :key="comment" :nodeData="comment" :add="add" :authUser="authUser" ></FeedBackNode>
    </ul>
</template>

<script>
import { ref, defineComponent } from 'vue'
import {mapGetters,mapActions} from 'vuex'

import FeedBackNode from './childComment.vue'

export default {
    name: "FeedBackTree",
    computed: {
        ...mapGetters(['authUser'])
    },
    mounted() {
        // console.log("treeData",this.treeData)
    },
    components: {
        FeedBackNode
    },
    props: {
        add: Function,

        treeData: {
            type: Array,
        },

    },
    data() {

        const like = ref([])
        return {
            treeFeedback: [],
            like
        }
    },
    methods: {
        // handleLoadComment : function(newComment) {
        //     this.treeFeedback.feedback.push(newComment);
        // }
    },
    async created() {

    }
}
</script>

<style>

</style>
