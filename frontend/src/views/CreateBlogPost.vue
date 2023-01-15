<template>
  <article class="message is-danger" v-show="this.errorMessage !== null">
    <div class="message-header">
      <p>Error</p>
      <button class="delete" aria-label="delete"></button>
    </div>
    <div class="message-body">
      {{ this.errorMessage }}
    </div>
  </article>


  <form>
    <div class="field">
      <label class="label">Title</label>
      <div class="control">
        <input class="input" type="text" placeholder="Title" v-model="blog.title">
      </div>
    </div>

    <div class="field">
      <label class="label">Description</label>
      <div class="control">
        <input class="input" type="text" placeholder="Description" v-model="blog.description">
      </div>
    </div>

    <div class="field">
      <label class="label">Tag</label>
      <div class="control">
        <input class="input" type="text" placeholder="Tag" v-model="blog.tag">
      </div>
    </div>

    <div class="field is-grouped">
      <div class="control">
        <button class="button is-link" @click="submit">Submit</button>
      </div>
    </div>
  </form>
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
      },
      errorMessage: null
    }
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
    }
  }
}
</script>

<style scoped>

</style>
