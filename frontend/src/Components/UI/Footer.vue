<template>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"/>
  <div id = "footer" class="footer">
    <div class="textFooter">
      <h2>Structure </h2>
      <a href="">Rector's office</a>
      <a href="">Academic Council</a>
      <a href="">Departments</a>
    </div>
    <div class="textFooter">
      <h2>Educational opportunities</h2>
      <a href="">Distance education</a>
      <a href="">Academic mobility</a>
      <a href="">Retraining (second higher education)</a>
    </div>
    <div class="textFooter">
      <h2>for users</h2>
      <a href="tel:+380505489646"><i class="fa-solid fa-phone"></i>+38050 5489646</a>
      <a href="mailto:sidorukolgasev@gmail.com"><i class="fa-regular fa-envelope-open"></i>sidorukolgasev@gmail.com</a>
      <a href="https://telegram.me/einfach_Olya"><i class="fa-brands fa-telegram"></i>@einfach_Olya</a>
      <a href="https://wa.me/0505489627"><i class="fa-brands fa-whatsapp"></i>+380505489627</a>
    </div>
  </div>
</template>

<script>
export default {
  name: "Footer"
}
</script>

<style scoped>
#footer {
  height: 100%;
  width: 100%;
  position: static;
  display: flex;

  background-color: #343436;
  padding-top: 10px;
  overflow: hidden;
}

.footer a {
  padding: 6px 8px 6px 16px;
  text-decoration: none;
  font-size: 15px;
  color: #ffffff;
  display: flex;
}

.textFooter {
  margin: auto;
  width: auto;
  height: auto;
}

.textFooter img {
  width: 25px;
}

.textFooter h2 {
  color: #ffffff;
}
@media screen and  (max-width: 500px) {
  .footer {
    flex-direction: column;
  }
}
</style>