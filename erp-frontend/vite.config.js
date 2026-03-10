import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

export default defineConfig({
  plugins: [
    vue(), // 기본 Vue 지원
    vueJsx() // JSX 지원 추가
  ]
})