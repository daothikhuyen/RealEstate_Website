const Menus = () => ({
    user : [
        {
            icon : 'fa-solid fa-house',
            title : 'Trang chủ',
            url : '/home'
        },
        {
            icon : 'fa-solid fa-user',
            title : 'Tài khoản',
            url: '',
            children: [
                {
                    icon: '',
                    title : 'Chỉnh sửa thông tin',
                    url : '/postManagement/account/personal_info'
                },
                {
                    icon: '',
                    title : 'Cài đặt tài khoản',
                    url : '/postManagement/account/account_setup'
                },
            ],
            expandedIndex: null,
        },
        {
            icon : 'fa-solid fa-notes-medical',
            title : 'Đăng bài',
            url : '/postManagement/index/creator'
        },
        {
            icon : 'fa-solid fa-list-check',
            title : 'Quản lí tin đăng',
            url : '/postManagement/my_posts/list_posts'
        },
        {
            icon : 'fa-solid fa-phone',
            title : 'Liên Hệ',
            url : '/contact'
        }
    ],

    admin : [
        {
            icon: 'fa-solid fa-address-book',
            title : 'Bài đăng của người dùng',
            url : '/postManagement/admin/list_posts_user'
        },
        {
            icon: 'fa-solid fa-bars-progress',
            title : 'Quản lí người dùng',
            url : '/postManagement/admin/list_user'
        },
    ],

    logout : [
        {
            icon: 'fa-solid fa-right-from-bracket',
            title: 'Đăng xuất',
            url : "/logout",
        }
    ]
})

export default {
    Menus,
}
