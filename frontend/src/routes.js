import Login from './views/account/Login.vue'
import Signup from './views/account/Signup.vue'
import ChangePassword from './views/account/ChangePassword.vue'
import FeedMain from './views/article/Article.vue'
import Components from './views/Components.vue'
import PageNotFound from '@/views/PageNotFound'
import AlarmList from '@/views/alarm/AlarmList.vue'
import SearchUser from '@/views/search/SearchUser.vue'
import FollowList from '@/views/profile/FollowList.vue'
import ProfileDetail from '@/views/profile/ProfileDetail.vue'
import ProfileUpdate from '@/views/profile/ProfileUpdate.vue'
import Scrap from '@/views/profile/Scrap.vue'
import Comments from '@/views/article/Comments.vue'
import ArticleCreate from '@/views/article/ArticleCreate.vue'
import ArticleDetail from '@/views/article/ArticleDetail.vue'
import PromiseList from '@/views/promise/PromiseList.vue'
import PromiseCreate from '@/views/promise/PromiseCreate.vue'
import PromiseDetail from '@/views/promise/PromiseDetail.vue'
import PromiseLocations from '@/views/promise/PromiseLocations'
import Onboarding from '@/views/account/Onboarding.vue'

import Vue from 'vue'
import VueRouter from 'vue-router'
import store from './vuex/store'

Vue.use(VueRouter)

const routes = [
    {
        path : '/',
        name : 'Login',
        component : Login
    },
    {
        path : '/account/signup',
        name : 'Signup',
        component : Signup
    },
    {
        path : '/onbording',
        name : 'Onboarding',
        component : Onboarding
    },
    {
        path : '/feed/main',
        name : 'FeedMain',
        component : FeedMain,
        meta: {
            loggedAuth: true
        }
    },
    {   
        path : '/article/create',
        name : 'ArticleCreate',
        component : ArticleCreate,
        meta: {
            loggedAuth: true
        }
    },
    {
        path : '/article/:articleid/',
        name : 'ArticleDetail',
        component : ArticleDetail,
        meta: {
            loggedAuth: true
        }
    },
    {
        path : '/components',
        name : 'Components',
        component : Components,
        meta: {
            loggedAuth: true
        }
    },
    {
        path: "/404",
        name: "notFound",
        component: PageNotFound
    },
    {
        path: '*',
        redirect: "/404"
    },
    {
        path : '/account/changePassword',
        name : 'ChangePassword',
        component : ChangePassword,
        meta: {
            loggedAuth: true
        }
    },
    {
        path : '/account/follow',
        name : 'FollowList',
        component : FollowList,
        meta: {
            loggedAuth: true
        }
    },
    {   path : '/account/profile/:nickname',
        name : 'ProfileDetail',
        component : ProfileDetail,
        meta: {
            loggedAuth: true
        }
    },
    {
        path : '/account/profile/update',
        name : 'ProfileUpdate',
        component : ProfileUpdate,
        meta: {
            loggedAuth: true
        }
    },
    {
        path : '/account/scrap',
        name : 'Scrap',
        component : Scrap,
        meta: {
            loggedAuth: true
        }
    },
    {
        path : '/search',
        name : 'SearchUser',
        component : SearchUser,
        meta: {
            loggedAuth: true
        }
    },
    {
        path : '/alarm',
        name : 'AlarmList',
        component : AlarmList,
        meta: {
            loggedAuth: true
        }
    },
    {
        path : '/article/:articleid/comments',
        name : 'Comments',
        component : Comments,
        meta: {
            loggedAuth: true
        }
    },
    {   
        path : '/promise',
        name : 'PromiseList',
        component : PromiseList,
        meta: {
            loggedAuth: true
        }
    },
    {   
        path : '/promise/create',
        name : 'PromiseCreate',
        component : PromiseCreate,
    },
    {
        path: '/promise/:promiseid',
        name: 'PromiseDetail',
        component: PromiseDetail,
        meta: {
            loggedAuth: true
        }
    },
    {
        path: '/promise/place/:promiseid',
        name: 'PromiseLocations',
        component: PromiseLocations,
        meta: {
            loggedAuth: true
        }
    },
]

const router = new VueRouter({
    mode: 'history',
    routes
})

router.beforeEach((to, from, next) => {
    if (to.meta.loggedAuth === true) {
        if (store.state.token === '') {
            next({ name: 'Login' })
        }
        else {
            next() // 원래 가려던 곳 가기
        }
    } else {
        next() // 권한 필요 없는 곳은 그냥 가기
    }
})

export default router