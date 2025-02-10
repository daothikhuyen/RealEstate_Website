import { createRouter, createWebHistory} from "vue-router"

import HomeComponent from '../components/HomeComponent.vue';
import LoginComponent from '../components/Form/LoginComponent.vue';
import SignUpComponent from '../components/Form/SignUpComponent.vue';

// quản lí đăng tin của người dùng
import posterManagement from '../components/posterManagement/index.vue'
import index from '../components/posterManagement/creator/index.vue'

import creator from '../components/posterManagement/creator/child/creator.vue'
import next from '../components/posterManagement/creator/child/nextCreator.vue'

import my_posts from '../components/posterManagement/my_posts/index.vue'
import list_posts from '../components/posterManagement/my_posts/list.vue'
import edit_post from '../components/posterManagement/my_posts/edit.vue'

import account from '../components/posterManagement/account/index.vue'
import personal_info from '../components/posterManagement/account/account-child/person_info.vue'
import account_setup from '../components/posterManagement/account/account-child/account_setup.vue'
import VerifyOTP from '../components/Form/sendEmail/verifyOTPComponent.vue'
import ResetPassword from '../components/Form/Reset_PasswordComponent.vue'
import ForgotPassword from '../components/Form/sendEmail/ForgotPassword_Component.vue'

// Admin
import list_user from '../components/posterManagement/admin/list_user.vue';
import list_posts_user from '../components/posterManagement/admin/list_posts_user.vue';
import posts_detail from '../components/posterManagement/admin/posts_detail.vue';
import list_feedback from '../components/posterManagement/admin/list_feedback.vue';


const routes = [
    {
        name: 'home',
        path: '/home',
        component: HomeComponent,
        meta: { public: true }
    },
    {
        name: 'login',
        path: '/login',
        component: LoginComponent,
        meta: { public: true }
    },
    {
        name: 'signup',
        path: '/signup',
        component: SignUpComponent,
        meta: { public: true }
    },
    {
        name: 'verify_otp',
        path: '/check/:otp',
        component: VerifyOTP,
        props: true,
        meta: { public: true }
    },
    {
        name: 'reset_password',
        path: '/account/reset_password/:data',
        component: ResetPassword,
        props: true,
        meta: { public: true },
    },
    {
        name: 'forgot_password',
        path: '/account/forgot_password',
        component: ForgotPassword,
        props: true,
        meta: { public: true },
    },
    {
        name: 'postManagement',
        path: '/postManagement',
        component: posterManagement,
        children: [
            {
                name: 'index',
                path: 'index',
                component: index,
                children: [
                    {
                        name: 'creator',
                        path: 'creator',
                        component: creator,
                    },
                    {
                        name: 'next',
                        path: 'next/:data',
                        component: next,
                        props: true
                    }
                ]
            },
            {
                name: 'my_posts',
                path: 'my_posts',
                component: my_posts,
                children: [
                    {
                        name: 'list_posts',
                        path: 'list_posts',
                        component: list_posts,
                    },
                    {
                        name: 'edit_post',
                        path: 'edit_post/:id',
                        component: edit_post,
                        props: true
                    }
                ]
            },
            {
                name: 'account',
                path: 'account',
                children: [
                    {
                        name: 'personal_info',
                        path: 'personal_info',
                        component: personal_info,
                    },
                    {
                        name: 'account_setup',
                        path: 'account_setup',
                        component: account_setup,
                    }
                ]
            },
            
            {
                name: 'admin',
                path: 'admin',
                children: [
                    {
                        name: 'list_user',
                        path: 'list_user',
                        component: list_user,
                    },
                    {
                        name: 'list_posts_user',
                        path: 'list_posts_user',
                        component: list_posts_user,
                    },
                    {
                        name: 'posts_detail',
                        path: 'posts_detail/:id',
                        component: posts_detail,
                    },
                    {
                        name: 'list_feedback',
                        path: 'list_feedback/:id',
                        component: list_feedback,
                    }
                ]
            }
            
        ]
    }
    
]

const router = createRouter({
    history: createWebHistory(),
    routes
})


router.beforeEach((to, from, next) => {
    // Check if the user is authenticated (e.g., check if a token is stored)
    const isAuthenticated = !!localStorage.getItem('KEY_TOKEN');

    if (!to.meta.public && !isAuthenticated) {
    // If the route is not public and the user is not authenticated, redirect to login
        next({ name: 'login' });
    }
    else {
        // Otherwise, allow navigation
        next();
    }
});

export default router