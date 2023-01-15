import { createRouter, createWebHistory } from 'vue-router'

import PostsList from "../views/PostsList.vue";
import CreateBlogPost from "../views/CreateBlogPost.vue";
import ViewBlogPost from "../views/ViewBlogPost.vue";

const router = createRouter({
  linkActiveClass: 'is-active',
  linkExactActiveClass: 'is-active',
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: PostsList
    },
    {
      path: '/create',
      component: CreateBlogPost
    },
    {
      path: '/view/:id',
      component: ViewBlogPost,
      name: 'view'
    }
  ]
})

export default router
