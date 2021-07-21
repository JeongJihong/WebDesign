import Login from './views/account/Login.vue'
import Signup from './views/account/Signup.vue'
import ChangePassword from './views/account/ChangePassword.vue'
import FeedMain from './views/feed/IndexFeed.vue'
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

export default [
    {
        path : '/account/login',
        name : 'Login',
        component : Login
    },
    {
        path : '/account/signup',
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
        path : '/account/changepassword',
        name : 'ChangePassword',
        component : ChangePassword
    },
    {
        path : '/account/follow',
        name : 'FollowList',
        component : FollowList
    },
    {   path : '/account/profile',
        name : 'ProfileDetail',
        component : ProfileDetail
    },
    {
        path : '/account/profile/update',
        name : 'ProfileUpdate',
        component : ProfileUpdate
    },
    {
        path : '/account/scrap',//저건 좀바꿔야할듯
        name : 'Scrap',
        component : Scrap //시작
    },
    {
        path : '/search',
        name : 'SearchUser',
        component : SearchUser
    },
    {
        path : '/alarm',
        name : 'AlarmList',
        component : AlarmList
    },
    {
        path : '/article',
        name : 'ArticleDetail',
        component : ArticleDetail
    },
    {
        path : '/article/create',
        name : 'ArticleCreate',
        component : ArticleCreate
    },
    {
        path : '/article/comments',
        name : 'Comments',
        component : Comments
    },
]
