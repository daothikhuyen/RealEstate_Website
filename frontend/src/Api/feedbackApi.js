import baseApi from './base'

const getFeedback = (postId) => baseApi.baseApi({
    method: 'GET',
    url : `http://localhost:8001/api/feedback/getFeedbacks/${postId}`,
    data: ""
})

const creatFeedback = (data) =>{

    const values = {
        'userId' : data.userId,
        'postId' : data.postId,
        'comment' : data.comment,
        'parentId': data.parentId
    };

    console.log(values)

    return baseApi.baseApi({
        method: 'POST',
        url : 'http://localhost:8001/api/feedback/insert',
        data: values
    })
}

export default{
    getFeedback,
    creatFeedback
}
