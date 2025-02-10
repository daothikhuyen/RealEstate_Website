
<template>
    <router-view></router-view>
</template>

<script >
import { ref,defineComponent } from 'vue'
import nextCreator from './child/nextCreator.vue'
import creator from './child/creator.vue'

export default defineComponent({
    components : {
        creator,
        nextCreator
    },
    mounted(){
    },
    data(){
        const demoPost = ref({})
        return {
            demoPost
        }
    },
    methods: {
        transmitData(postInfo){
            this.demoPost = postInfo
        }
    }

})
</script>


