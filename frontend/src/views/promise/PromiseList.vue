<template>
  <div>
    <br>
    <router-link :to="{ name: 'PromiseCreate' }"  class="text-decoration-none me-3 text-dark d-flex justify-content-center">
          <h1>약속 생성하기</h1>
    </router-link>
    <br>
    <ul>
      <h2>대기중인 약속!</h2>
      <br>
      <li><img src="../../assets/images/game-icon.svg" alt="game"> 3/5 레온고? 08.03 20:00</li>
      <li><img src="../../assets/images/food-icon.svg" alt="game"> 3/5 레온? 08.04 20:00</li>
      <li><img src="../../assets/images/study-icon.svg" alt="game"> 3/5 ㄹㅇㄱ 08.05 20:00</li>
      <li><img src="../../assets/images/sport-icon.svg" alt="game"> 3/5 ㄹㅇㄱ 08.06 20:00</li>
    </ul>
    <hr>
    <ul>
      <h2>다가오는 약속!</h2>
      <br>
      <li><img src="../../assets/images/game-icon.svg" alt="game"> 2명 롤 5인팟   08.04.14:00</li>
      <li><img src="../../assets/images/food-icon.svg" alt="game"> 3명 파스타 먹을사람  08.06.12:00</li>
      <li><img src="../../assets/images/study-icon.svg" alt="game"> 5명 면접준비스터디  08.05.14:00</li>
      <li><img src="../../assets/images/study-icon.svg" alt="game"> 4명 모각코  08.05.14:00 </li>
    </ul>


  </div>
</template>

<script>
export default {

}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
li { font-family: 'Jua', sans-serif; }
</style>