import { inject } from 'vue';
import { useRouter } from 'vue-router';
import * as userApi from "../api/userApi";
import { removeCookie } from "../util/cookieUtil";

const useCustomLogout = () => {
    // 1. App.jsx에서 provide('userContext')한 객체를 가져옵니다.
    const { loginStatus } = inject('userContext');
    const router = useRouter();

    const userLogoutAction = async () => {
        try {
            // API 호출
            await userApi.userLogoutAction();

            // 2. Vue의 reactive 객체는 직접 수정하면 화면이 자동으로 바뀝니다.
            loginStatus.isLogin = false;
            loginStatus.loginUser = {};

            // 3. 쿠키 삭제
            removeCookie("member");

            // 4. 페이지 이동 (React의 navigate와 동일)
            // { replace: true } 옵션은 뒤로가기 기록을 남기지 않습니다.
            router.replace('/user_main');
            
        } catch (error) {
            console.error("로그아웃 실패:", error);
        }
    };

    // 컴포넌트에서 사용할 변수와 함수를 반환합니다.
    return { userLogoutAction, loginStatus };
}

export default useCustomLogout;
