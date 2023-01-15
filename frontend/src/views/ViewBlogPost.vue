<template>
  <div class="box">
    <h1 class="title">{{ blog.title }}</h1>

    <p>
      {{ blog.description }}
    </p>

    <p>
      <span class="tag" v-show="blog.tag">{{ blog.tag}}</span>
    </p>
  </div>
</template>

<script>
import axios from "./../utils/http"

export default {
  name: "CreateBlogPost",

  data() {
    return {
      blog: {
        title: null,
        description: null,
        tag: null,
      }
    }
  },

  created() {
    this.$watch(
        () => this.$route.params,
        (toParams, previousParams) => {
          if (toParams.id !== previousParams.id) {
            this.getBlog(toParams.id)
          }
        }
    )

    this.getBlog(this.$route.params.id)
  },

  methods: {
    submit(event) {
      event.preventDefault()

      axios.post('/api/v1/posts', this.blog)
          .then(() => {
            this.errorMessage = null
          })
          .catch((error) => {
            this.errorMessage = error.response.data.message
          })
    },

    getBlog(id) {
      axios.get('/api/v1/posts/' + id)
          .then((response) => {
            this.blog = response.data.post
          })
    }
  }
}
</script>

<style scoped>

</style>
