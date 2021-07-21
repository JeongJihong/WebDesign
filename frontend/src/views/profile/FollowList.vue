<template>
  <div class="m-4">
    <div class="d-flex">
      <button>
        <h1> ← </h1>
      </button>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <h1>
        팔로잉/팔로우
      </h1>
    </div>
    <br><br><br>
    <div>
      <b-tabs content-class="mt-3" fill>
        <b-tab title="팔로워" active>
          <b-list-group>
            <b-list-group-item class="listgroupitem" variant="light" href="#some-link" v-for="person in 30" :key="person">
              <div class="d-flex" style="align-items:center">
                <img src="@/assets/images/profile_default.png" alt="image" style="width: 40px; height: 40px" >
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <p>김싸피</p>
              </div>
            </b-list-group-item>
          </b-list-group>
        </b-tab>
        <b-tab title="팔로잉" active>
          <b-list-group>
            <b-list-group-item class="listgroupitem" variant="light" href="#some-link" v-for="person in 30" :key="person">
              <div class="d-flex" style="align-items:center">
                <img src="@/assets/images/profile_default.png" alt="image" style="width: 40px; height: 40px" >
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <p>신싸피</p>
              </div>
            </b-list-group-item>
          </b-list-group>
        </b-tab>
      </b-tabs>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
.listgroupitem {
  border: 0px solid #ddd;
}
</style>