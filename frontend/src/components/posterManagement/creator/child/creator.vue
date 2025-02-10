<template>
    <div class="ViewPost container-fluid bg-white" v-if="newPost">
        <div class="bg-white mb-5">
            <div class="">
                <div class="viewPostItems px-0">
                    <div class="title_main w-100"  style="">
                        <h5 class="ps-3 pt-1 text-white">Đăng Tin Mới</h5>
                    </div>
                    <div>
                        <section class="info w-100 p-3 row">
                            <div class="col-md-4">
                                <div class="address">
                                    <div class="">
                                        <h5>Địa chỉ cho thuê</h5>
                                    </div>
                                    <div class="w-100 ps-3 pb-3">
                                        <span class="text-danger">{{error['address']}}</span>
                                    </div>
                                    <addAddress :createAddress="createAddress" />
                                </div>
                                <div class="extentsion mt-3">
                                    <div>
                                        <h5>Tiện ích</h5>
                                    </div>
                                    <Extension  :loadExtension="loadExtension" />
                                </div>
                            </div>
                            <div class="detail_info col-md-8">
                                <div class="info_detail">
                                    <div class="">
                                        <h5>Thông tin bài đăng</h5>
                                    </div>
                                    <section class="ps-3">
                                        <div class="info-item">
                                            <div class="post_title">
                                                Tiêu đề <span class="text-danger">*</span>
                                            </div>
                                            <input type="text" name="title" id="title" class="input_item w-100 " placeholder="Cho Thuê Nhà Ở Đà Nẵng" v-model="newPost.postData.title">
                                            <div class="w-100 ps-2">
                                                <span v-if="!error['title']" class=""><small>Tối thiếu 30 ký tự, tối đa 99 ký tự</small></span>
                                                <span v-else class="text-danger"><small>{{error['title']}}</small></span>
                                            </div>
                                        </div>
                                        <div class="info-item mt-3">
                                            <div class="post_title mt-0">
                                                Giá cho thuê <span class="text-danger">*</span>
                                            </div>
                                            <div class="input_special d-flex justify-content-between align-items-center">
                                                <input type="text" name="price" id="price" class="input_item" placeholder="Nhập đầy đủ các số, ví dụ 1000000" v-model="newPost.postData.price">
                                                <div class="">VND/Tháng</div>
                                            </div>
                                            <div class="w-100 ps-2">
                                                <!-- <span v-if="!error['price']" class="attention"><small>Tối thiếu 30 ký tự, tối đa 99 ký tự</small></span> -->
                                                <span class="text-danger"><small>{{error['price']}}</small></span>
                                            </div>
                                        </div>
                                        <div class="info-item contactInfo mb-3">
                                            <div class="post_title">
                                                Diện tích <span class="text-danger">*</span>
                                            </div>
                                            <div class="input_special d-flex justify-content-between align-items-center">
                                                <input type="text" class="input_item" placeholder="Nhập diện tích, VD: 20" v-model="newPost.postData.area">
                                                <div class="">m<sup>2</sup></div>
                                            </div>
                                            <div class="w-100 ps-md--2">
                                                <span class="text-danger"><small>{{error['area']}}</small></span>
                                            </div>
                                        </div>
                                        <div class="info-item description">
                                            <div class="post_title">
                                                Nội dung mô tả <span class="text-danger">*</span>
                                            </div>
                                            <ckeditor :editor="editor" v-model="newPost.postData.description" :config="editorConfig"></ckeditor>
                                            <div class="w-100 ps-2">
                                                <span class="text-danger"><small>{{error['description']}}</small></span>
                                            </div>
                                        </div>
                                        <addImage @loadImage="UpdateImage" @loadVideo="UpdateVideo" :notifi="error['image']" />
                                    </section>
                                </div>
                            </div>

                            <div class="btn_submit ps-2 d-flex justify-content-md-end justify-content-center">
                                <button class="button" type="submit" @click.prevent="ValidatorForm()">Tiếp theo</button>
                            </div>

                        </section>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import {ref,defineComponent } from 'vue'
import {mapGetters,mapActions} from 'vuex'
import { ClassicEditor, Bold, Essentials, Italic, Mention, Paragraph, Undo } from 'ckeditor5';
import { SlashCommand } from 'ckeditor5-premium-features';
import { Ckeditor } from '@ckeditor/ckeditor5-vue';

import 'ckeditor5/ckeditor5.css';
import 'ckeditor5-premium-features/ckeditor5-premium-features.css';
import debounce from 'lodash.debounce';

import user from '../../../../Api/userApi'
import addImage from './listChild/addImages.vue'
import addAddress from './listChild/addAddress.vue'
import Extension from './listChild/extension.vue'

export default defineComponent({
    name: 'CreatePoster',
    computed: {
        ...mapGetters(['authUser']),
        ...mapGetters(['newPost'])
    },
    components: {
        addAddress,
        addImage,
        Extension,
        Ckeditor
    },
    data() {

        const error= ref([])
        const user = ref({})
        const array_nameLocation = ref([])
        const location_id = ref([])
        const newPostInfo = ref(null)
        const arrayImage = ref([])
        const arrayvideo = ref([])
        const arrayExtension = ref([])
        const newPost = {
            postData : {
                title : "Cho Thuê Nhà , Giá Đẹp",
                price : "",
                area: "",
                description: "<p>Nhập nội dung mô tả tại đây</p>",
                full_address: "",
                province_id: "",
                district_id: "",
                ward_id: "",
                streetId: ""
            }
        }

        return {
            newPost,
            editor: ClassicEditor,
            editorConfig: {
                plugins: [ Bold, Essentials, Italic, Mention, Paragraph, Undo ],
                toolbar: [ 'undo', 'redo', '|', 'bold', 'italic' ],
                licenseKey: '<YOUR_LICENSE_KEY>',
            },
            error,
            user,
            location_id,
            array_nameLocation,
            newPostInfo,
            arrayImage ,
            arrayvideo,
            arrayExtension,

        };
    },
    watch: {
        newPost(value){
            this.newPost = value;
        }
    },
    methods : {

        loadExtension(extension){
            this.arrayExtension = extension
        },

        UpdateImage(imgae){
            this.arrayImage = []

            imgae.forEach(element => {
                console.log(element)
                const newImage = {
                    id:  Math.floor(Math.random() * 1000),
                    link_image: element.link_image,
                    post_id: Math.floor(Math.random() * 1000),
                }
                this.arrayImage.push(newImage)
            });

            console.log(this.arrayImage)

        },

        UpdateVideo(video){
            video.forEach(element => {
                const newvideo = {
                    id:  Math.floor(Math.random() * 1000),
                    link_video: element.link_video,
                    post_id: Math.floor(Math.random() * 1000),
                }

                this.arrayvideo.push(newvideo)
            });
        },

        ValidatorForm: debounce(function (e) {

            let checkValidator = ref(false)
            const attribute = this.newPost.postData
            this.error = []
            const arrayText = [
                { value: attribute.title,      name: "title" },
                { value: attribute.price,  name: "price" },
                { value: attribute.area ,  name: "area" },
                { value: attribute.description, name: "description" }
            ]
            const arrayInput = [
                { value: this.full_address, name:  "address"},
                { value: this.arrayImage,  name: "image"}
            ]

            arrayText.forEach(element => {
                if(element.name == "title" && element.value.length < 30 && element.value.length >99){
                    this.error[element.name] = "Bạn nhập chưa đúng định dạng"
                }

                const match = /^[0-9]+$/;
                if(element.name == "price" && !element.value.match(match)){
                    this.error[element.name] = "Bạn phải nhập đủ các số VD: 100000"
                }

                if(element.value == ""){
                    this.error[element.name] = "Bạn cần nhập thông tin này"
                }
            });

            arrayInput.forEach(element => {
                if(!element.value || element.value.length==0 ){
                    this.error[element.name] = "Bạn cần chọn thông tin này"

                }
            });


            if(Object.keys(this.error).length === 0){
                checkValidator.value = true
            }

            if(checkValidator.value){
                this.seenBefore()
            }

        }, 500),

        createAddress(address,location){
            this.location_id = location
            this.full_address = address
        },

        seenBefore(){

            try {
                this.user = this.authUser
                const attribute = this.newPost.postData
                const newPost = {
                    user : this.user,
                    postData: {
                        title: attribute.title,
                        description: attribute.description,
                        full_address: this.full_address,
                        price: attribute.price,
                        area: attribute.area,
                        userId: this.user.id,
                        provinceId: this.location_id['province'],
                        districtId: this.location_id['district'],
                        wardId: this.location_id['ward'],
                        streetId: "",
                        category_id:1,
                        number_like: 0

                    },
                    extensions: this.arrayExtension,
                    images :this.arrayImage,
                    videos : this.arrayvideo
                }

                console.log(this.location_id)

                this.$store.dispatch('createPosts',newPost);
                const jsonData = JSON.stringify(newPost);
                console.log(newPost)
                this.$router.push({name :'next',params: {data: jsonData}})
            } catch (error) {
                console.log(error)
            }

        },

    },
    // created(){
    //     this.ValidatorForm = debounce(this.ValidatorFormCreated,2000)
    // }
})
</script>

<style>

    .ViewPost .info .post_title{
        font-size: 14px;
        font-weight: 600;
        margin-bottom: 8px;
        margin-top: 15px ;
        padding-left: 5px;
    }

    .ViewPost .info .info-item{
        margin-top: 20px;
    }

    .ViewPost .input_number_fulladdress .input_item,
    .ViewPost .input_item{
        width: 100%;
        padding: 5px;
        border: 1px solid rgb(162, 160, 160,0.5);
    }

    .ViewPost .input_special .input_item{
        border: none;
        outline: none;
        width: 90%;
    }

    .ViewPost .input_special div {
        height: auto;
        padding: 4.6px 8px;
    }

    .ViewPost .input_special{
        width: 100%;
        min-width: auto;
        border: 1px solid rgb(162, 160, 160,0.5);
        border-radius: 2px;
        overflow: hidden;
    }

    .input_item:hover{
        border: 1px solid black;
    }

    .input_special:hover{
        border: 1px solid black;
    }

    .input_item.Notification{
        border: 0.05px solid red;
        border-radius: 3px;
    }


    .ViewPost .btn_submit .button{
        width: 64%;
        background-color: var(--primary-color);
        padding: 8px;
        margin: 10px;
        font-size: 18px;
        font-weight: 500;
        letter-spacing: 1px;
        border-radius: 4px;
        text-align: center;
        color: #fff;
    }

    .ViewPost .note_post{
        padding: 10px;
        margin: 20px;
        background-color: #FFEC8B;
        color: #A0522D;
        font-size: 15px;
        border-radius: 8px;
    }

    .ViewPost .note_post ul li {
        list-style: square;
        font-size: 14px;
        line-height: 25px;
    }

    .ViewPost .note_post h3{
        font-size: 18px;
    }

    /* Ckeditor*/
    .ck.ck-editor__editable_inline>:last-child {
        margin-bottom: var(--ck-spacing-large);
        height: 200px;
    }

    input:hover{
        border: 1px solid black;
    }

    .attention small {
        font-size: 11px;
        line-height: 16px;
        font-weight: 450;
        color:rgb(162, 160, 160,1);
    }

    @media only screen and (max-width: 992px) {
        .ViewPost{
            margin-top: 50px;
            padding: 0px 0px;
        }

        .ViewPost .viewPostItems{
            border:none;
        }

        .ViewPost .title_main {
            background-color: #ff5d26;
            padding: 6px 3px;
            /* border-radius: 0px; */
        }

        .ViewPost .info_account{
            padding: 0px 10px 10px;
        }
    }

    @media (max-width: 768px) {
        /* .ViewPost{
            padding-left: 5rem;
        } */

        .ViewPost .info .Address_detail select{
            width: 100%;
            padding: 5px;
            border: 1px solid rgb(162, 160, 160,0.5);
            border-radius: 2px;
        }

    }

    @media (max-width:600px) {
        .homeManager .ViewPost{
            padding-left: 0px;
            margin: 0px;
        }

        .ViewPost .title_main {
            background-color: #ff5d26;
            padding: 6px 3px;
            border-radius: 0px;
        }
    }

</style>
