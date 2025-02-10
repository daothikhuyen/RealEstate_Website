import baseApi from "./base";

const login = (data) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/log-in`,
    data: data
})

const signup = (data) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/users/processregister`,
    data: data
})

const verifyOTP = (data) =>  baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/users/check/verifyOTP`,
    data: data
})

const logout = (data) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/log-out`,
    data: data
})

const getMyInfo = () => baseApi.baseApi({
    method: 'GET',
    url : `http://localhost:8001/users/getMyInfo`,
    data: ""
})

const update_personalInfo = (data,id) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/users/update/${id}`,
    data: data
})

const delete_account = () => baseApi.baseApi({
    method: 'DELETE',
    url : `http://localhost:8001/users/destroy`,
    data: ""
})

const change_password = (data) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/users/update_password`,
    data: data
})

const forgot_password = (data) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/users/forgot_password`,
    data: data,
})

const reset_password = (data,token) => baseApi.baseApi({
    method: 'POST',
    url : `http://localhost:8001/users/reset_password/${token}`,
    data: data,
})

export default {
    login,
    signup,
    verifyOTP,
    logout,
    getMyInfo,
    update_personalInfo,
    delete_account,
    change_password,
    forgot_password,
    reset_password
}