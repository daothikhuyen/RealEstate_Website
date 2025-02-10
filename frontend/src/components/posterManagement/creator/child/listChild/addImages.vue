<template>
    <div class="info-item image">
        <div class="main_title">
            <h5>Hình Ảnh </h5>
        </div>
        <div class="attention mb-1 ms-3"><small> Cập nhật hình ảnh rõ ràng sẽ cho thuê nhanh hơn</small></div>
        <div class="upload_image mb-1">
            <div class="imagePreviewWrapper">
                <div>Thêm ảnh</div>
                <img src="../../../../../assets/image/device/camera.png" alt="">
            </div>
            <div class="rightRound" id="upload">
                <input type="file" name="file" class="file" ref="fileInput" multiple accept=".png,.jpg,.jpeg" @change="onUploadFile">
            </div>
        </div>
        <div class=" w-100 ps-2">
            <span class="text-danger"><small>{{notifi}}</small></span>
        </div>
        <div class="container">
            <div class="card_picture" v-for="(image,index) in images" :key="index">
                <span class="delete" @click="deleteImage(index)">&times;</span>
                <img :src="`http://localhost:8001${image.link_image}`"/>
            </div>
        </div>

    </div>

    <div class="info-item video">
        <div class="main_title">
            <h5>Video</h5>
        </div>
        <div class="attention mb-1 ms-3"><small>Upload video của bạn</small></div>
        <div class="upload_image">
            <div class="imagePreviewWrapper">
                <div>Thêm video</div>
                <img src="../../../../../assets/image/device/video.png" alt="">
            </div>
            <div class="rightRound" id="upload">
                <input type="file" name="file" class="file" ref="fileInput" accept=".mp4" multiple @change="onUploadFile">
            </div>
        </div>
        <section class="loading-area">
            <div class="row_area" v-for="(file,index) in files_loading" :key="index">
                <i class="icon fa-solid fa-file" style="font-size: 30px;"></i>
                <div class="content upload" >
                    <div class="detail">
                        <span class="name">{{ file.name }}</span>
                        <span class="size">{{ file.loading }}</span>
                    </div>
                    <div class="loading-bar">
                        <div class="loading" :style="{width: file.loading + '%'}"></div>
                    </div>
                </div>
            </div>
        </section>
        <section class="upload-area" >
            <div class="row_area" v-for="(file,index) in uploadFiles" :key="index">
                <div class="d-flex justify-content-between w-100">
                    <div class="content upload">
                        <i class="icon fa-solid fa-file" style="font-size: 30px;" aria-hidden="true"></i>
                        <div class="detail">
                            <span class="name">{{ file.name }}</span>
                            <span class="size">{{ file.size }}</span>
                        </div>
                    </div>
                    <div class="icon">
                        <span class="delete" @click="deleteVideo(index)">&times;</span>
                        <!-- <i class="fa-solid fa-check " style="font-size: 20px;"></i> -->
                    </div>
                </div>
            </div>
        </section>
    </div>

</template>

<script>
import { ref, defineComponent,watch  } from 'vue'
import axios from "axios";

export default defineComponent({
    name: "Add image into post",
    props: {
        notifi : String,
        dataImage: Array,
        dataVideo: Array
    },
    data(){

        const showProgress = ref(false)
        return {
            images : [],
            videos : [],
            uploadFiles: [],
            files: [],
            showProgress,
        }
    },
    watch: {
        dataImage(image){
            this.uploadImage(image)
        },

        dataVideo(video){
            console.log(video)
            this.uploadVideo(video)
        }
    },
    methods:{
        uploadImage(value){
            value.forEach(element => {
              
                this.images.push(element)

            });
            this.$emit('loadImage', this.images)
        },

        uploadVideo(value){
            value.forEach(element => {
                const video = element.link_video.split("/")
                this.uploadFiles.push({name: video[6], size: "123KB"});
                this.videos.push(element)
            });
                this.$emit('loadVideo', this.videos)
        },

        onUploadFile(event){
            const files_loading = event.target.files
            if(files_loading.length == 0) return
            for (let i = 0; i < files_loading.length; i++) {
                const file = files_loading[i]
                const fileName = (file.name.length >= 12) ? file.name.substring(0,13) + '....'+ file.name.split('.')[1]: file.name

                const formData = new FormData()
                formData.append("file", file)
                if(file.type.split('/')[0] == 'video'){
                    this.files.push({name: fileName, loading : 0})
                    this.showProgress = true
                }

                axios.post('http://localhost:8001/storage/fileSystem', formData, {
                    onUploadProgress: ({loaded, total}) => {
                        console.log(this.files)
                        if(file.type.split('/')[0] == 'video'){
                            this.files[this.files.length - 1].loading = Math.floor((loaded/total)*100);
                            if(loaded == total){
                                const fileSize = (total > 1024)? total + 'KB' : (loaded/(1024*1024)).toFixed(2) + 'MB'
                                this.uploadFiles.push({name: fileName, size: fileSize});
                                this.showProgress = false
                            }
                        }
                    }
                }).then((result) => {

                    if(file.type.split('/')[0] == 'image'){

                        const link = {
                            link_image : result.data.result.file,
                        }

                        this.images.push({id: "", link_image:result.data.result.file})
                        this.$emit('loadImage', this.images)
                    }
                    if(file.type.split('/')[0] == 'video'){
                        const link = {
                            id :"",
                            link_video : result.data.result.file,
                        }

                        this.videos.push(link)
                        this.$emit('loadVideo', this.videos)
                    }

                })
                .catch(error => {
                    alert("Đã Xảy Ra lỗi")
                    console.error(error);
                })
            }


        },

        deleteImage(index){
            this.images.splice(index,1)
            this.$emit('loadImage', this.images)

        },

        deleteVideo(index){
            this.videos.splice(index,1)
            this.uploadFiles.splice(index,1)
            this.$emit('loadImage', this.images)
        }


    }
})
</script>

<style scoped>
    .info .info_detail .upload_image{
        height: 160px;
        border:  1px dashed rgb(162, 160, 160,0.5);
        border-radius: 2px ;
        text-align: center;
        display: flex;
        justify-content: center;
        align-items: center;
        margin: auto;
        position: relative;
    }

    .info .info_detail .upload_image .rightRound{
        position: absolute;
        margin: 5px;
        bottom: 16px;
        left: 20px;
        text-align: center;
        width: 99%;
        height: 95%;
        overflow: hidden;
        line-height: 33px;
        cursor: pointer;
    }

    .upload_image img{
        height: 65px;
        text-align: center;
        object-fit: cover;
        object-position: center;
    }

    .homeManager .ViewPost .info .info_detail .upload_image input[type='file']{
        position: absolute;
        transform: scale(10);
        opacity: 0;
    }

    /* pictures post */
    .image .container{
        width: 100%;
        height: auto;
        display: flex;
        align-items: flex-start;
        justify-content: flex-start;
        flex-wrap: wrap;
        max-height: 200px;
        position: relative;
        margin-bottom: 8px;
        padding-top: 15px;
        overflow-y: scroll;
    }

    .image .container::-webkit-scrollbar{
        width: 0%;
    }

    .image .container .card_picture {
        width: 75px;
        margin-right: 5px;
        height: 75px;
        position: relative;
        margin-bottom: 8px;
    }

    .image .container .card_picture span{
        position: absolute;
        top: -2px;
        right: 9px;
        font-size: 20px;
        color: #fff;
        font-weight: 600;
        cursor: pointer;
    }

    .image .container .card_picture img{
        width: 100%;
        height: 100%;
        object-position: center;
        object-fit: cover;
    }

     /* video */
    section .row_area{
        background: #d0ffd3;
        list-style: none;
        padding: 10px;
        margin: 8px;
        border-radius: 10px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    section .row_area .icon {
        color: #0a8740;
        padding-right: 7px;
    }

    section .row_area .content {
        display: flex;
        justify-content: space-between;
    }

     section .row_area .content .detail .size{
        color: rgb(128, 128, 128);
        font-size: 11px;
    }


    .loading-area .content{
        width: 100%;
        margin-left: 15px;
        display: flex;
        flex-direction: column;
    }

    .loading-area .detail{
        width: 100%;
        display: flex;
        align-items: center;
        margin-bottom: 7px;
        justify-content: space-between;
    }

    .loading-area .content .loading-bar {
        height: 6px;
        width: 100%;
        margin-bottom: 4px;
        background: #fff;
        border-radius: 30px;
    }

    .content .loading-bar .loading {
        height: 100%;
        width: 0%;
        background: #1aaa4c;
        border-radius:inherit ;
    }

    .upload-area {
        max-height: 232px;
        overflow-y: scroll;
        position: relative;
    }

    .upload-area.onprogress{
        max-height: 150px ;
    }

    .upload-area::-webkit-scrollbar{
        width: 0%;
    }

    .upload-area .row_area .content .detail{
        display: flex;
        margin-left: 15px;
        flex-direction: column;
    }

    .upload-area .icon span.delete {
        font-size: 30px;
        font-weight: 800;
        cursor: pointer;
        padding-bottom: 2rem;
    }

    @media (max-width:400px) {
        .image .container .card_picture{
            width: 60px;
            height: 60px;
        }
    }

</style>
