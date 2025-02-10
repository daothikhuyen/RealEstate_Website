<template>
        <div class="ViewPost container-fluid">
            <div class="bg-white mb-5">
                <div class="">
                    <div class="viewPostItems">
                        <div class="title_main w-100"  style="">
                            <h5 class="ps-3 pt-1 text-white">Tiêu đề: {{show_posts.postData.title.substring(0, 25) }}....</h5>
                        </div>
                        <div>
                            <section class="info p-3 w-100 ms-2 row">
                                <div class="col-md-4">
                                    <div class="address">
                                        <div class="">
                                            <h5>Địa chỉ cho thuê</h5>
                                        </div>
                                        <div class="w-100 ps-3 pb-3">
                                            <span class="text-danger">{{error['address']}}</span>
                                        </div>
                                        <addAddress :createAddress="createAddress" :locations="show_posts.locationResponse" :fullAddress="show_posts.postData.full_address" />
                                    </div>
                                    <div class="extentsion mt-3">
                                        <div>
                                            <h5>Tiện ích</h5>
                                        </div>
                                        <Extension  :loadExtension="loadExtension" :dataExtensions="show_posts.extensions" />
                                    </div>
                                </div>
                                <div class="detail_info ps-md-3 col-md-8">
                                    <div class="info_detail">
                                        <div class="">
                                            <h5>Thông tin bài đăng</h5>
                                        </div>
                                        <section class="ps-md-3">
                                            <div class="info-item">
                                                <div class="post_title">
                                                    Tiêu đề <span class="text-danger">*</span>
                                                </div>
                                                <input type="text" name="title" id="title" class="input_item w-100 " v-model="show_posts.postData.title" placeholder="Cho Thuê Nhà Ở Thành phố Đà Nẵng" >
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
                                                    <input type="text" name="price" id="price" class="input_item"  v-model="show_posts.postData.price" placeholder="Nhập đầy đủ các số, ví dụ 1000000" >
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
                                                    <input type="text" class="input_item" placeholder="Nhập diện tích, VD: 20" v-model="show_posts.postData.area">
                                                    <div class="">m<sup>2</sup></div>
                                                </div>
                                                <div class="w-100 ps-2">
                                                    <span class="text-danger"><small>{{error['area']}}</small></span>
                                                </div>
                                            </div>
                                            <div class="info-item description">
                                                <div class="post_title">
                                                    Nội dung mô tả <span class="text-danger">*</span>
                                                </div>
                                                <ckeditor :editor="editor" v-model="show_posts.postData.description" :config="editorConfig"></ckeditor>
                                                <div class="w-100 ps-2">
                                                    <span class="text-danger"><small>{{error['description']}}</small></span>
                                                </div>
                                            </div>
                                            <addImage @loadImage="UpdateImage" @loadVideo="UpdateVideo" :dataImage="show_posts.images" :dataVideo="show_posts.videos" :notifi="error['image']" />
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
import Swal from 'sweetalert2'
import { ClassicEditor, Bold, Essentials, Italic, Mention, Paragraph, Undo } from 'ckeditor5';
import { SlashCommand } from 'ckeditor5-premium-features';
import { Ckeditor } from '@ckeditor/ckeditor5-vue';

import 'ckeditor5/ckeditor5.css';
import 'ckeditor5-premium-features/ckeditor5-premium-features.css';

import addImage from '../../posterManagement/creator/child/listChild/addImages.vue'
import addAddress from '../../posterManagement/creator/child/listChild/addAddress.vue'
import Extension from '../../posterManagement/creator/child/listChild/extension.vue'
import postApi from '../../../Api/postApi';

export default defineComponent({
    name: 'CreatePoster',
    computed: {
        ...mapGetters(['authUser'])
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
        const location_id = ref()

        const arrayImage = ref([])
        const arrayvideo = ref([])
        const arrayExtension = ref([])

        const show_posts = {
            postData : {
                title : "",
                price : "",
                description: "",
                full_address: "",
                provinceId: "",
                districtId: "",
                wardId: "",
                streetId: ""
            },
        }

        return {
            show_posts,
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

            arrayImage ,
            arrayvideo,
            arrayExtension
        };
    },
    methods : {

        loadExtension(extension){
            this.arrayExtension = extension
        },

        UpdateImage(imgae){
            this.arrayImage = imgae
        },

        UpdateVideo(video){
            this.arrayvideo = video
        },

         ValidatorForm(){
            let checkValidator = ref(false)
            this.error = []
            const attribute = this.show_posts.postData
            const arrayText = [
                { value: attribute.title,      name: "title" },
                { value: attribute.price,  name: "price" },
                { value: attribute.area ,  name: "area" },
                { value: attribute.description, name: "description" }
            ]
            const arrayInput = [
                { value: attribute.full_address, name:  "address"},
                { value: this.arrayImage,  name: "image"}
            ]

            arrayText.forEach(element => {
                if(element.name == "title" && element.value.length < 30 && element.value.length >99){
                    this.error[element.name] = "Bạn nhập chưa đúng định dạng"
                }
                const match = /^[0-9]+$/;
                if (element.name === "price" && element.value && typeof element.value === 'string' && !element.value.match(match)) {
                    this.error[element.name] = "Bạn phải nhập đủ các số VD: 100000"
                }

                if(element.value == ""){
                    this.error[element.name] = "Bạn cần nhập thông tin này"
                }
            });

            arrayInput.forEach(element => {

                if(element.value.length == 0){
                    this.error[element.name] = "Bạn cần chọn thông tin này"
                }
            });


            if(Object.keys(this.error).length === 0){
                checkValidator.value = true
                console.log("wow")
            }

            if(checkValidator.value){
                 console.log("hi")
                this.seenBefore()
            }
        },

        createAddress(address,location){
            this.location_id = location
            this.full_address = address
        },

        seenBefore(){

            if(!this.location_id){
                this.location_id = this.show_posts.locationResponse
            }
            console.log( this.location_id)
            try {
                const attribute = this.show_posts.postData
                this.user = this.authUser
                const uploadData = {
                    user : this.user,
                    postData: {
                        title: attribute.title,
                        description: attribute.description,
                        full_address: this.full_address? this.full_address : this.show_posts.postData.full_address,
                        price: attribute.price,
                        area: attribute.area,
                        userId: this.user.id,
                        provinceId: this.location_id['provinceId'],
                        districtId: this.location_id['districtId'],
                        wardId: this.location_id['wardId'],
                        streetId: "",
                        category_id:1,
                        number_like: 0
                    },
                    extensions: this.arrayExtension,
                    images :this.arrayImage,
                    videos : this.arrayvideo
                }

                console.log(uploadData)
                
                  if(confirm('Bạn chắc chắn thay đổi bài viết')){
                        window.scrollTo(0, 0);
                        const result = postApi.edit(attribute.id,uploadData)
                        if(result){
                            Swal.fire({
                                title: "Thông Báo!",
                                text: "Thay Đổi Thành Công",
                                icon: "success",
                                showConfirmButton: false,
                                timer: 1500
                            }).then(() => {

                                this.$router.push({name :'list_posts'})
                            });
                        }
                  }

            } catch (error) {
                console.log(error)
            }

        },

    },
    async created(){
        this.show_posts = (await postApi.show(this.$route.params.id)).result;
    }

})
</script>



<style scoped>

    .ViewPost span{
        font-size: 15px;
    }

    .ViewPost .title{
        color: var(--primary-color);
        font-weight: 600;
    }

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

    /* Ckeditor */
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
            padding: 0px;
        }

        .ViewPost .viewPostItems{
            border:none;
        }

        .ViewPost .title_main {
            background-color: #ff5d26;
            padding: 6px 3px;
            border-radius: 0px;
        }

        .ViewPost .title_main {
            background-color: #ff5d26;
            padding: 6px 3px;
            border-radius:0px;
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

</style>
