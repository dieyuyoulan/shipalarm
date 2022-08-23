<template>
  <el-calendar v-model="value">
  </el-calendar>

</template>

<script>
import { defineComponent, ref } from 'vue'
export default defineComponent({
  setup() {
    const value = ref(new Date())

    return {
      value,
    }
  },
})
</script>

<style scoped>

</style>