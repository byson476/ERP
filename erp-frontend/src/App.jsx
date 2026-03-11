import { defineComponent, onMounted, provide, reactive } from "vue";
import { RouterView, useRouter } from "vue-router";

import Header from "./page/layout/Header";
import Navigation from "./page/layout/Navigation";
import Footer from "./page/layout/Footer";

import { userLoginCheck } from "./util/loginCheck.js";

import "./assets/css/erpstyle.css";

export default defineComponent({
  setup() {

    const router = useRouter();

    // 로그인 상태
    const loginStatus = reactive({
      isLogin: false,
      loginUser: {}
    });

    // React의 setState처럼 사용할 함수
    const setLoginStatus = (status) => {
      loginStatus.isLogin = status.isLogin;
      loginStatus.loginUser = status.loginUser;
    };

    // Context 제공
    provide("userContext", {
      loginStatus,
      setLoginStatus
    });

    // 새로고침 시 로그인 유지 체크
    onMounted(() => {

      const { isLogin, member } = userLoginCheck();

      loginStatus.isLogin = isLogin;
      loginStatus.loginUser = member;

    });

    return () => (
      <>

        <Header />

        <div id="wrapper">

          <Navigation />

          <div id="content">
            <RouterView />
          </div>

        </div>

        <Footer />

      </>
    );
  }
});