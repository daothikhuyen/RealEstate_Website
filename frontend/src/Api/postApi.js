import baseApi from './base'

const listPost = () => baseApi.baseApi({
    method: 'GET',
    url : 'http://localhost:8001/api/posts/getall',
    data: ""
})

const getMyPosts = (page) => baseApi.baseApi({
    method: 'GET',
    url : `http://localhost:8001/api/posts/getMyPosts?page=${page}`,
    data: ""
})
const createPost = (data) => baseApi.baseApi({
    method: 'POST',
    url : 'http://localhost:8001/api/posts/create',
    data: data
})

const deletePosts = (postId) => baseApi.baseApi({
    method: 'DELETE',
    url : `http://localhost:8001/api/posts/destroy/${postId}`,
    data: ''
})

const show = (id) => baseApi.baseApi({
    method: 'GET',
    url : `http://localhost:8001/api/posts/edit/${id}`,
    data: ""
})

const edit = (id,data) => baseApi.baseApi({
    method: 'PUT',
    url : `http://localhost:8001/api/posts/edit/${id}`,
    data: data
})

const searchInput = (data,page) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/api/posts/searchInput?page=${page}`,
    data: data
})

const searchPriceOrArea = (data) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/api/posts/search/searchPriceOrArea`,
    data: data
})

const searchInputAll = (data) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/api/posts/search/searchInput_All`,
    data: data
})

const searchByLocation_Id = (data) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/api/posts/search/searchByLocation_Id`,
    data: data
})

const list_searchSuggestion = (data) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/api/posts/search/list_searchSeggestion`,
    data: data
})

export default {
    listPost,
    getMyPosts,
    createPost,
    deletePosts,
    show,
    edit,
    searchInput,
    searchPriceOrArea,
    searchInputAll,
    searchByLocation_Id,
    list_searchSuggestion
}
