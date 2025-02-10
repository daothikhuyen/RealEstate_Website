import postApi from "./postApi"

const Extension = () => {
    return [
        {
            id: 1,
            name: 'Máy lạnh',
        },
        {
            id: 2,
            name: 'Máy nước nóng',
        },
        {
            id: 3,
            name: 'Nội thất',
        },

    ]
}

const getExtension = () => baseApi.baseApi({
    method: 'GET',
    url : `http://localhost:8001/api/posts/getExtension${postApi}`,
    data: ''
})


export default {
    Extension,
    getExtension
}
