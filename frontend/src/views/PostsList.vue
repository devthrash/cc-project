<template>
  <SearchBar/>

  <div class="box" v-for="post in posts">
    <h1 class="title"><a @click="viewPost(post.blogId)">{{ post.title }}</a></h1>
    Comments: <strong>{{ post.comment_count || 0 }}</strong>
  </div>
</template>

<script>
import SearchBar from "../components/SearchBar.vue";
import axios from "./../utils/http"

export default {
  name: "PostsList",
  components: {SearchBar},

  data() {
    return {
      posts: []
    }
  },

  created() {
    axios.get('/api/v1/posts/feed').then((response) => this.posts = response.data.posts)
  },

  methods: {
    viewPost(id) {
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
