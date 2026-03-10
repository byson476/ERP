import { defineComponent, ref, onMounted, provide, reactive } from 'vue';
import { RouterView, useRouter } from 'vue-router';
import Header from "./page/layout/Header";
import Navigation from "./page/layout/Navigation";
import Footer from "./page/layout/Footer";
import { userLoginCheck } from "./util/loginCheck.js";

export default defineComponent({
  setup() {
    const router = useRouter();
    // 1. 로그인 상태 관리 (React의 useState)
    const loginStatus = reactive({
      isLogin: false,
      loginUser: {}
    });

    // 2. Context 제공 (React의 UserContext.Provider 역할)
    provide('userContext', { loginStatus });

    onMounted(async () => {
      const { isLogin, member } = userLoginCheck();
      loginStatus.isLogin = isLogin;
      loginStatus.loginUser = member;
    });

    return () => (
      <>
        <Header />
        <Navigation />
        <div id="wrapper">
          <div id="content">
            {/* React의 <Routes> 대신 <RouterView> 사용 */}
            <RouterView />
          </div>
        </div>
        <Footer />
      </>
    );
  }
});
