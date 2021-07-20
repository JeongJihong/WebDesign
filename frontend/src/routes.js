

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import Resetpassword from './views/user/Resetpassword.vue'
import FeedMain from './views/feed/IndexFeed.vue'
import Components from './views/Components.vue'
import PageNotFound from '@/views/PageNotFound'
export default [


    {
        path : '/',
        name : 'Login',
        component : Login
    },
    {
        path : '/user/join',
        name : 'Join',
        component : Join
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
        path : '/user/Resetpassword',
        name : 'Resetpassword',
        component : Resetpassword
    },
]
