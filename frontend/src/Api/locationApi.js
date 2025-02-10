import baseApi from './base'
import axiosDefault from 'axios'

const get_Provinces =()=> baseApi.baseApi({
    method: 'GET',
    url : 'http://localhost:8001/api/locations/get_Provinces',
    data: ""
})

const get_Districts = (province_id) =>{

    const data = {
        provinceId : province_id
    }

    return baseApi.baseApi({
        method: 'POST',
        url : `http://localhost:8001/api/locations/get_Districts`,
        data: data
    })
}

const get_Wards = (district_id) =>{
    const data = {
        districtId : district_id
    }

    return baseApi.baseApi({
        method: 'POST',
        url : `http://localhost:8001/api/locations/get_Wards`,
        data: data
    })
}

export default {
    get_Provinces,
    get_Districts,
    get_Wards
}
