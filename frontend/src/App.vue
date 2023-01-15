<script setup>
import { RouterLink, RouterView } from 'vue-router'

</script>

<template>
  <div class="container">
    <div class="box">
      <div class="buttons is-grouped">
          <router-link class="button" to="/">Home</router-link>
          <router-link class="button" to="/create">New blog post</router-link>
      </div>

      <RouterView />
    </div>
  </div>
</template>

<style scoped>

</style>
