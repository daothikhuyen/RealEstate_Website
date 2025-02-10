
<template>

    <div class="columExtension">
        <div v-for="item in extension" :key="item" class="ps-3 pt-2 input_check">
            <input type="checkbox" class="" :id="item.name" :value="item.name" v-model="checkExtension" @change="getExtension()">
            <label for="" class="ps-1">{{item.name}}</label>
        </div>
    </div>

    <button type="button" class="btn text-black-50" data-bs-toggle="modal" @click="isBlock = !isBlock">
        <small>Thêm tiện ích...</small>
    </button>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true" :class="{show : isBlock}" :style="{display: isBlock ? 'block' : 'none'}">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <form action="" @submit.prevent="addExtension()">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Thêm vào tiện ích khác</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="isBlock = !isBlock"></button>
                    </div>
                    <div class="modal-body">
                        <input type="text" placeholder="Nhập tên" class="addExtension" v-model="titleExtention">
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">Thêm</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div v-if="isBlock">
         <div v-for="index in 2" :key="index" class="modal-backdrop fade show"></div>
    </div>

</template>

<script lang="ts">
import { ref,defineComponent } from 'vue'
import extensionApi from '../../../../../Api/extentionApi'

export default defineComponent({
    name : 'Extension',
    components: {

    },
    props: {
        loadExtension : Function,
        dataExtensions: Array
    },
    data() {

        const checkExtension = ref([])
        const titleExtention = ref(null)
        const isBlock = ref(false)
        return {
            array : [],
            extension : [],
            checkExtension,
            titleExtention,
            isBlock
        }
    },
    watch : {
        dataExtensions(extensions){
            this.extension = extensions
            this.uploadExtension(extensions)
        }
    },
    methods: {

        uploadExtension(value){

            value.forEach(element => {
                this.checkExtension.push(element.name)
                this.array.push(element)
            });

            this.loadExtension(this.array)

        },

        getExtension(){
            this.array = []
            this.checkExtension.forEach(element => {
                const newExtension = {
                    name : element,
                }

                this.array.push(newExtension)
            });

            this.loadExtension(this.array)
        },

        addExtension(){

            const newExtension = {
                id : Math.floor(Math.random() * 1000),
                name : this.titleExtention
            }

            this.extension.push(newExtension)
            this.titleExtention = ""
            this.isBlock = false
        }
    },
    created() {
        this.extension = extensionApi.Extension()
    }
})
</script>

<style scoped>
    .columExtension {
        max-height: 300px;
        /* display: flex;
        flex-wrap: wrap; */
        overflow-y: scroll;
    }

    .columExtension > div {
        flex-basis: 50%;
        box-sizing: border-box;
    }

    @supports selector(::-webkit-scrollbar) {
        .columExtension::-webkit-scrollbar {
            width: 4px;
            background: #ffff;
        }
        .columExtension::-webkit-scrollbar-thumb {
            background: #fff;
        }

        .columExtension:hover::-webkit-scrollbar-thumb{
            background: gray;
        }
    }

    /* btn thêm tiện ích */
    .btn:focus {
        outline: 0;
        box-shadow: none;
    }

    .btn:hover small {
        color: var(--primary-color);
    }

    .addExtension {
        width: 100%;
        height: 40px;
        outline: none;
        border: none;
    }

    .btn-primary {
        color: #fff;
        background-color: var(--primary-color);
        border-color: var(--primary-color);
    }

    .modal {
        transition: all 0.3s ease-in;
    }

    .modal-content {
        border-radius: 3px;
    }

    .input_check:hover ~ input[type ='checkbox']{
        border: 2px solid black;
    }
</style>

