import { createRouter, createWebHistory } from 'vue-router';
import { UserMainPage } from '../page/ErpMainPage';
// ... 나머지 페이지들도 import 하세요

const routes = [
  { path: '/', component: UserMainPage },
  { path: '/user_main', component: UserMainPage },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
