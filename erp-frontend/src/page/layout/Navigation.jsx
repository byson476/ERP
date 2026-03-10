import { defineComponent, inject } from 'vue';
import { RouterLink } from 'vue-router';
// 커스텀 훅(컴포저블)도 Vue용으로 변환되어 있다고 가정합니다.
import useCustomLogout from '../../hook/useCustomLogout';

export default defineComponent({
  setup() {
    // React의 useContext(UserContext) -> Vue의 inject('userContext')
    // App.jsx에서 provide('userContext', ...) 한 값을 가져옵니다.
    const { loginStatus } = inject('userContext');
    const { userLogoutAction } = useCustomLogout();

    return () => (
      <div id="navigation">
        <p>
          <strong>메뉴</strong>
        </p>
        <ul>
          {!loginStatus.isLogin ? (
            <>
              {/* 로그인 전 */}
              <li>
                <RouterLink to="/user_login_form">로그인</RouterLink>
              </li>
              <li>
                <RouterLink to="/user_write_form">회원가입</RouterLink>
              </li>
            </>
          ) : (
            <>
              {/* 로그인 후 */}
              <li>
                <RouterLink to="">{loginStatus.loginUser.userId} 님</RouterLink>
              </li>
              <li>
                <RouterLink to={`/user_view/${loginStatus.loginUser.userId}`}>
                  내정보
                </RouterLink>
              </li>
              <li>
                {/* Vue JSX에서 이벤트는 onClick (대문자 C) 또는 onClick={함수} 형식입니다. */}
                <RouterLink to="" onClick={userLogoutAction}>
                  로그아웃
                </RouterLink>
              </li>
            </>
          )}
          <li>
            <a href="http://localhost:8080/swagger-ui/index.html">swagger</a>
          </li>
        </ul>
      </div>
    );
  },
});
