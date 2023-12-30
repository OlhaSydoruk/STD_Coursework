<script>
import Footer from "@/components/UI/Footer.vue";
import LoginPage from "@/pages/LoginPage.vue";

export default {
  name: "App",
  components: {
    LoginPage,
    Footer
  }
}
</script>
<template>

  <div class="app">
    <div class="logotype">
      <img src="@/assets/images/logotype.jpg" alt="logo">
    </div>
    <div class="content">

    <router-view/>
    </div>
    <Footer/>
  </div>
</template>
<style scoped>
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.content{
  min-height: 500px;
}

.logotype {
  width: 100%;
  height: 10%;
  background: black;
}
.logotype img{
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 9%;
  height: 9%;
}
</style>