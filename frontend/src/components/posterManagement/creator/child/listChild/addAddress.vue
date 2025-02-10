<template>
    <section class="Address_detail d-md-block ps-md-3">
        <div class="location pb-3 d-flex align-items-center">
            <select class="input_item" name="provinceLocation" id="provinceLocation" @change="changeProvince(select.province.id)"  v-model="select.province.id">
                <option :value="null" disabled selected>--Chọn Tỉnh/TP--</option>
                <option v-for="province in getData.provinces" :key="province"  :value="province.id" >{{province.name}}</option>
            </select>
            <span class="text-danger fw-bold ps-1">* </span>
        </div>
        <div class="location pb-3  d-flex align-items-center">
            <select class="input_item" name="districtLocation" id="districtLocation" @change="changeDistrict(select.district.id)" v-model="select.district.id">
                <option :value="null" disabled selected>--Chọn Quận/Huyện--</option>
                <option v-for="district in getData.districts" :key="district"  :value="district.id" >{{district.name}}</option>
            </select>
            <span class="text-danger fw-bold ps-1">* </span>
        </div>
        <div class="location pb-3  d-flex align-items-center">
            <select class="input_item" name="wardsLocation" id="wardsLocation" @change="changeWard(select.ward.id)" v-model="select.ward.id">
                <option :value="null" disabled selected>--Chọn Phường/Xã--</option>
                <option  v-for="ward in getData.wards" :key="ward"  :value="ward.id" >{{ward.name}}</option>
            </select>
            <span class="text-danger fw-bold ps-1">* </span>
        </div>
    </section>
    <div class="input_number_fulladdress">
        <div class="number_house ps-md-3">
            <div class="post_title">Số Nhà</div>
            <input type="text" name="number_home" id="number_home" class="w-100 input_item" v-model="select.numberHome"  @input="getNumber_houer(select.numberHome)" tag="Nhập số nhà ">
        </div>
        <div class="info-item full_address ps-md-3">
            <div class="post_title">Địa chỉ đầy đủ</div>
            <div class="fullAddress"  name="fullAddress" id="fullAddress">
                <span v-if="!textFullAddress">{{fullAddress}}</span>
                <span v-else>
                    {{textFullAddress}}
                </span>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, defineComponent } from 'vue'
import {mapState,mapActions, mapGetters} from 'vuex'
import locationApi from '../../../../../Api/locationApi.js'

export default defineComponent({
    name : "addAdress",
    computed: {
        // ...mapState(['getLocation'])
        ...mapGetters(['newPost'])
    },
    props:{
        createAddress : Function,
        locations : Array,
        fullAddress: String
    },
    data() {
        const getLocation = ref([])
        const arrayLocation_Id = ref([])
        const selected = ref([])
        const select = {
            province : {
                id: ref(null),
                name: ref(null),
            },
            district :{
                id: ref(null),
                name: ref(null),
            },
            ward: {
                id: ref(null),
                name: ref(null),
            },
            numberHome: ref(null)
        }

        const getData = {
            provinces: ref([]),
            districts: ref([]),
            wards : ref([])
        }

        return {
            arrayLocation_Id,
            selected,
            select,
            textFullAddress : "",
            full_address : [],
            getData
        }
    },
    computed: {},
    watch: {
        locations(value){
            this.uploadLocation(value)
        },

        fullAddress(value){
            const text = value.split(',')
            this.select.numberHome = text[0]
        }
    },
    methods: {

        uploadLocation(value){
            
            this.arrayLocation_Id = value,
            this.full_address = this.fullAddress;

            this.select.province.id = value['provinceId']
            this.select.district.id = value['districtId']
            this.select.ward.id = value['wardId']

            this.get_Provinces()
            this.get_Districts(value['provinceId'])
            this.get_Wards(value['districtId'])

        },

        deleteSelect(value){
            value.forEach(element => {
                const keys = element.split('.');
                if (keys.length === 2) {
                    const [parent, child] = keys;
                    this.select[parent][child] = ref(null);
                } else {
                    this.select[element] = ref(null);
                }
            });
        },

        async get_Full_Address(value){

            this.data = [this.select.province.name,this.select.district.name,this.select.ward.name]
            this.full_address = [];
            this.data.forEach((element,index) => {
                if(element != null){
                    this.full_address.push(element)
                }
            });

            if(value != ""){
                this.full_address.push(value)
            }

            this.textFullAddress = ""
            this.full_address.reverse().forEach((element,index) => {
                this.textFullAddress += element
                if(index < this.full_address.length -1){
                    this.textFullAddress += ", "
                }
            });

            this.arrayLocation_Id = {
                province :this.select.province.id,
                district : this.select.district.id,
                ward : this.select.ward.id
            }

            this.createAddress(this.textFullAddress,this.arrayLocation_Id)
        },

        async get_Provinces(){
            const response = await locationApi.get_Provinces();
            this.getData.provinces = response
        },

        async get_Districts(province_id){

            if(this.select.province.id != null || this.select.province.id != ""){
                const response = await locationApi.get_Districts(province_id);
                this.getData.districts = response
            }
        },

        async get_Wards(district_id){

            if(this.select.district.id != null || this.select.district.id != ""){
                const response = await locationApi.get_Wards(district_id);
                this.getData.wards = response
            }
        },

        filterLocation(nameTable,selectName,filterId){
            const result = this.getData[nameTable].filter(item => item.id == filterId)
            this.select[selectName].name = result[0].name
        },

        changeProvince(provinceId){
            this.filterLocation('provinces','province',provinceId)
            const array = ["district.name","district.id","ward.name","ward.id","numberHome"]
            this.deleteSelect(array)
            this.get_Full_Address("")
            this.get_Districts(provinceId)
        },

        changeDistrict(districtId){
            this.filterLocation('districts','district',districtId)
            const array = ["ward.name","ward.id","numberHome"]
            this.deleteSelect(array)
            this.get_Full_Address("")
            this.get_Wards(districtId)
        },

        changeWard(wardId){
            this.filterLocation('wards','ward',wardId)
            const array = ["numberHome"]
            this.deleteSelect(array)
            this.select.ward.id = wardId
            this.get_Full_Address("")
        },

        getNumber_houer(textAddress){
            this.select.numberHome = textAddress
            this.get_Full_Address(textAddress)

        },
    },
    async created(){
        // this.getLocation = await locationApi.getLocation()
        this.get_Provinces();
    }
})
</script>

<style scoped>

    .info .info-item{
        margin-top: 20px;
    }

    .info .Address_detail select{
        width: 100%;
        padding: 5px;
        border: 1px solid rgb(162, 160, 160,0.5);
        border-radius: 2px;
    }

    .info .input_number_fulladdress .input_item,
    .info .info_detail .input_item{
        width: 100%;
        padding: 5px;
        border: 1px solid rgb(162, 160, 160,0.5);
        border-radius: 2px;
    }

    .info .input_item:hover{
        border: 1px solid black;
    }

    .fullAddress {
        width: 100%;
        height: auto;
        min-height: 38px;
        padding: 5px;
        margin: auto 0;
        border-radius: 2px;
        line-height: 25px;
        background-color: rgb(162, 160, 160,0.15);
    }

    input[type = 'text']:focus{
        background: #ffff;
        outline: none;
    }

</style>

