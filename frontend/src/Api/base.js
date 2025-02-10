import axios from "axios";

const baseApi = async (data) => {
    const csrfToken = localStorage.getItem('KEY_TOKEN');

    const options = {
        method : data.method,
        url : data.url,
        data: data.data,
        headers: {
            'Content-Type': 'application/json',
        },
    }

    if(csrfToken != null){

        options.headers['Authorization'] = `Bearer ${csrfToken}`;
    }


    return await axios(options)
    .then((response) => {
        return response.data
    })
}

export default {
    baseApi
}
