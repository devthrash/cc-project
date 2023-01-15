<template>
  <div class="field">
    <div class="control">
      <input class="input" type="text" placeholder="Search..." v-on:input="debouncedHandler">
    </div>
  </div>
  <div>
    <div class="box" v-for="result in results">
      <a @click="onClickResult(result.blogId)">{{ result.title }}</a>
    </div>
  </div>
</template>

<script>
import debounce from "lodash/debounce"
import axios from "./../utils/http"

export default {
  name: "SearchBar",

  data() {
    return {
      results: []
    }
  },

  created() {
    this.debouncedHandler = debounce((event) => {
      axios.get(`api/v1/posts/search?text=${encodeURIComponent(event.target.value)}`).then((response) => {
        this.results = response.data.posts
      })
    }, 200);
  },

  beforeUnmount() {
    this.debouncedHandler.cancel();
  },

  methods: {
    onClickResult(id) {
      this.$router.push({
        name: 'view',
        params: {
          id
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
