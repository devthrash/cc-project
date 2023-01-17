<template>

  <h1 class="title">{{ blog.title }}</h1>

  <p>
    {{ blog.description }}
  </p>

  <p>
    <span class="tag" v-show="blog.tag">{{ blog.tag}}</span>
  </p>

  <div class="container">
    <h2 class="subtitle">Comments</h2>
    <article class="media" v-for="comment in blog.comments">
      <figure class="media-left">
        <p class="image is-64x64">
          <img src="https://bulma.io/images/placeholders/128x128.png">
        </p>
      </figure>
      <div class="media-content">
        <div class="content">
          <p>
            <strong>{{comment.userAccount.firstName}} {{comment.userAccount.lastName}}</strong>
            <br>
            {{ comment.text }}
          </p>
        </div>
      </div>
    </article>

    <article class="media">
      <figure class="media-left">
        <p class="image is-64x64">
          <img src="https://bulma.io/images/placeholders/128x128.png">
        </p>
      </figure>
      <div class="media-content">
        <div class="field">
          <p class="control">
            <textarea class="textarea" placeholder="Add a comment..." v-model="commentText"></textarea>
          </p>
        </div>
        <nav class="level">
          <div class="level-left">
            <div class="level-item">
              <a class="button is-info" @click="addComment">Submit</a>
            </div>
          </div>
          <div class="level-right">
          </div>
        </nav>
      </div>
    </article>
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
      },
      commentText: ''
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
    addComment() {
      axios.post(`/api/v1/posts/${this.blog.blogId}/comments`, { text: this.commentText })
          .then(() => {
            this.commentText = ''
            this.getBlog(this.blog.blogId)
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
