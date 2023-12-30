<template>
  <h1>Error Page</h1>
  <div class="error-content">
  <p class="error-header">Error code: {{ errorCode }}</p>
  <pre class="error-body">Error stack: {{ errorStack }}</pre>
  </div>
</template>

<script>
export default {
  name: "ErrorPage",
  computed: {
    errorCode() {
      return this.$route.query.errorCode;
    },
    errorStack() {
      return this.$route.query.errorStack;
    }
  },
}
</script>

<style scoped>

.error-content{
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  margin-top: 2rem;

}
.error-header{
  margin-left: 2rem;
  margin-bottom: 1rem;
}
.error-body{
  margin-left: 5rem;
}
</style>