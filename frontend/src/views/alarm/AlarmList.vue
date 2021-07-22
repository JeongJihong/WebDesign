<template>
  <div>
    <!-- 헤더 -->
    <div class="mt-3 mx-4 fs-1">
      <button><b-icon icon="arrow-left" class="me-4"></b-icon></button>
      <span class="fw-bold">알림</span>
    </div>

    <!-- 댓글 알림 -->
    <div class="mt-4">
      <div class="mx-3 d-flex align-items-center" style="color: #adb5bd;">
        <span class="me-3">댓글</span>
        <hr style="display: inline-block; width: 100%;">
      </div>
      <div>
        <b-list-group>
          <b-list-group-item href="#" class="border-0 py-3">
            OOO 님이 OOO.. 글에 댓글을 남기셨습니다.
          </b-list-group-item>
          <b-list-group-item href="#" class="border-0 py-3">
            OOO 님이 OOO.. 글에 댓글을 남기셨습니다.
          </b-list-group-item>
          <b-list-group-item href="#" class="border-0 py-3">
            OOO 님이 OOO.. 글에 댓글을 남기셨습니다.
          </b-list-group-item>
        </b-list-group>
      </div>
    </div>

    <!-- 팔로잉 요청 알림 -->
    <div class="mt-4">
      <div class="mx-3 d-flex align-items-center" style="color: #adb5bd;">
        <span class="me-3">팔로우</span>
        <hr style="display: inline-block; width: 100%;">
      </div>
      <div>
        <b-list-group>
          <b-list-group-item href="#" class="border-0 py-3">
            <span class="me-2">OOO 님에게서 팔로잉 신청이 왔습니다.</span>
            <span>
              <button class="badge btn-danger me-2">거절</button>
              <button class="badge btn-primary">승인</button>
            </span>
          </b-list-group-item>
          <b-list-group-item href="#" class="border-0 py-3">
            <span class="me-2">OOO 님에게서 팔로잉 신청이 왔습니다.</span>
            <span>
              <button class="badge btn-danger me-2">거절</button>
              <button class="badge btn-primary">승인</button>
            </span>
          </b-list-group-item>
          <b-list-group-item href="#" class="border-0 py-3">
            <span class="me-2">OOO 님에게서 팔로잉 신청이 왔습니다.</span>
            <span>
              <button class="badge btn-danger me-2">거절</button>
              <button class="badge btn-primary">승인</button>
            </span>
          </b-list-group-item>
        </b-list-group>
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>