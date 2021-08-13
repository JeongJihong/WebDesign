<template>
  <div>
  <b-card no-body>
    <b-tabs card>
      <b-tab title="도움말">
        <b-card-img bottom src="./assets/images/main-icon.png" alt="pipl"></b-card-img>
        <b-card-title>This tab does not have the <code>no-body</code> prop set</b-card-title>
        <b-card-text>
          Quis magna Lorem anim amet ipsum do mollit sit cillum voluptate ex nulla tempor. Laborum
          consequat non elit enim exercitation cillum aliqua consequat id aliqua. Esse ex
          consectetur mollit voluptate est in duis laboris ad sit ipsum anim Lorem. Incididunt
          veniam velit elit elit veniam Lorem aliqua quis ullamco deserunt sit enim elit aliqua
          esse irure.
        </b-card-text>
      </b-tab>
      <b-tab active>
        <template #title>
        <b-icon icon="bell-fill" id='알람'></b-icon><label for="알람"> 알람</label>
      </template>
        <b-card-img bottom src="https://picsum.photos/600/200/?image=21" alt="Image 21"></b-card-img>
        <b-card-footer>Picture 1 footer</b-card-footer>
      </b-tab>
      <b-tab active>
        <template #title>
        <b-icon icon="signpost" id='약속생성'></b-icon><label for="약속생성"> 약속 생성</label>
      </template>
        <b-card-img bottom src="https://picsum.photos/600/200/?image=21" alt="Image 21"></b-card-img>
        <b-card-footer>Picture 1 footer</b-card-footer>
      </b-tab>
      <b-tab active>
        <template #title>
        <b-icon icon="pencil-square" id='글작성'></b-icon><label for="글작성"> 글 작성</label>
      </template>
        <b-card-img bottom src="https://picsum.photos/600/200/?image=21" alt="Image 21"></b-card-img>
        <b-card-footer>Picture 1 footer</b-card-footer>
      </b-tab>

      <b-tab active>
        <template #title>
        <b-icon icon="person-fill" id='프로필'></b-icon><label for="프로필"> 프로필</label>
      </template>
        <b-card-img bottom src="https://picsum.photos/600/200/?image=21" alt="Image 21"></b-card-img>
        <b-card-footer>Picture 1 footer</b-card-footer>
      </b-tab>

      <b-tab no-body title="그 외">
        <b-card-img bottom src="https://picsum.photos/600/200/?image=26" alt="Image 26"></b-card-img>
        <b-card-footer>Picture 3 footer</b-card-footer>
      </b-tab>
    </b-tabs>
  </b-card>
</div>
</template>

<script>
export default {
 name: 'Guide',
}
</script>

<style>

</style>