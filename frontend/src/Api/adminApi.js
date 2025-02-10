import baseApi from './base'

const list_user = (page) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/users/admin/list_user?page=${page}`,
    data: '',
})

const account_lock = (id_user,data) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/users/admin/account_lock/${id_user}`,
    data: data,
})

const deleteAccount = (id_user) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/users/admin/destroyAccount/${id_user}`,
    data: '',
})

const searchInput = (data,page) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/api/user/admin/searchInput?page=${page}`,
    data: data
})

const searchInputAll = (data,page) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/api/posts/admin/searchInputAll_Admin?page=${page}`,
    data: data
})

const list_posts_user = (page) => baseApi.baseApi({
    method: 'GET',
    url : `http://localhost:8001/api/posts/admin/list_posts_user?page=${page}`,
    data: '',
})

const posts_detail = (id) => baseApi.baseApi({
    method: 'GET',
    url : `http://localhost:8001/api/posts/admin/detail/${id}`,
    data: ""
})

const destroy_posts = (id) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/api/posts/admin/destroy_posts/${id}`,
    data: ""
})

const list_feedback = (page,id_posts) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/api/feedback/admin/list_feedback/${id_posts}?page=${page}`,
    data: '',
})

const destroyFeedback = (id_feedback) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/api/feedback/admin/destroyFeedback/${id_feedback}`,
    data: '',
})

export default {
    list_user,
    account_lock,
    deleteAccount,
    searchInput,
    searchInputAll,
    list_posts_user,
    posts_detail,
    destroy_posts,
    list_feedback,
    destroyFeedback
}
