import baseApi from './base'

const getLikePost = () => {

    return baseApi.baseApi({
        method: 'GET',
        url : `http://localhost:8001/api/ratting/getLikePost`,
        data:""
    })
}

const getLikeFeedback = (feedbackId) => {

    const value = {
        "feedbackId" : feedbackId,
    }
    return baseApi.baseApi({
        method: 'POST',
        url : `http://localhost:8001/api/ratting/getlikeFeedback`,
        data:value
    })

}
const createLikePost = (data) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/api/ratting/likePost`,
    data: data
})

const createLikeFeedback = (data) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/api/ratting/likeFeedback`,
    data: data
})

export default {
    getLikePost,
    getLikeFeedback,
    createLikePost,
    createLikeFeedback,

}
