import { createRouter, createWebHistory } from 'vue-router';
import { ErpMainPage } from '../page/ErpMainPage';
import { ErpLoginPage } from '../page/user/erpLogin';
// ... 나머지 페이지들도 import 하세요

const routes = [
  { path: '/', component: ErpMainPage },
  { path: '/erp_main', component: ErpMainPage },
  { path: '/erp_login_form', component: ErpLoginPage },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
