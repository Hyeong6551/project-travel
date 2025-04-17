<script setup lang="ts">
import { defineProps } from 'vue'
import { useRouter } from 'vue-router'

const props = defineProps<{
  post: {
    id: number
    title: string
    createdAt: string
  }
}>()

const router = useRouter()

const goDetail = () => {
  router.push(`/board/${props.post.id}`)
}
</script>

<template>
  <tr @click="goDetail" style="cursor: pointer">
    <td>{{ post.id }}</td>
    <td>{{ post.title }}</td>
    <td>{{ new Date(post.createdAt).toLocaleDateString() }}</td>
  </tr>
</template>
