

import Login from './views/account/Login.vue'
import Signup from './views/account/Signup.vue'
import changePassword from './views/account/changePassword.vue'
import FeedMain from './views/feed/IndexFeed.vue'
import Components from './views/Components.vue'
import PageNotFound from '@/views/PageNotFound'
export default [
    {
        path : '/account/Login',
        name : 'Login',
        component : Login
    },
    {
        path : '/account/Signup',
        name : 'Signup',
        component : Signup
    },
    {
        path : '/feed/main',
        name : 'FeedMain',
        component : FeedMain
    },
    {
        path : '/components',
        name : 'Components',
        component : Components
    },
    {
        path: "/404",
        name: "notFound",
        component: PageNotFound
      },
    {
    path: '*',
    redirect: "/404"
    // 아래처럼 바로 NotFound 경로를 매칭해도 됨
    // component: NotFound
    },
    {
        path : '/account/changePassword',
        name : 'changePassword',
        component : changePassword
    },
]
