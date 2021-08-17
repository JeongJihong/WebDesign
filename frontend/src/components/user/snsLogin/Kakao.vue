<template>
    <div id="kakao-login">

        <button @click="kakaoLogin" style="width:100%" class="my-2">
        </button>
            <div>
                <button 
                  class="d-flex align-items-center justify-content-center btn shadow-none"
                  @click="kakaoLogin"
                  style="height: 2.7rem; width: 100%; color:rgb(59,30,30); background-color:rgb(250,225,0); border:0;"
                ><b-icon icon="chat-fill"></b-icon>  카카오계정으로 로그인</button>
            </div>
    </div>
</template>

<script>
    import { mapActions, mapState } from 'vuex'
    export default {
        methods: {
            ...mapActions([
                'kakaoLogin'
            ]),
        },
        computed: {
            ...mapState([
                'loginState',
                'token'
            ])
        },
        created() {
            if (this.token !== '') {
                this.$router.push({ name: 'FeedMain' })
            }
        },
    }
</script>
