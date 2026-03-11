import { ref, reactive, inject } from "vue";
import { useRouter } from "vue-router";
import * as userApi from "../api/userApi";
import * as ResponseStatusCode from "../api/ResponseStatusCode";
import * as ResponseMessage from "../api/ResponseMessage";
import { setCookie } from "../util/cookieUtil";

export default function useLoginForm() {

    // Context
    const { loginStatus, setLoginStatus } = inject("userContext");

    const loginFormRef = ref(null);
    const router = useRouter();

    const user = reactive({
        userId: "",
        password: "",
        name: "",
        email: ""
    });

    const message = reactive({
        id: "",
        password: ""
    });

    // 입력 처리
    const handleChangeLoginForm = (e) => {

        const { name, value } = e.target;

        user[name] = value;

        // 입력 시 메시지 초기화 (UX 개선)
        if (name === "userId") {
            message.id = "";
        }

        if (name === "password") {
            message.password = "";
        }
    };

    const userLoginAction = async (e) => {

        // form submit 방지
        if (e) {
            e.preventDefault();
        }

        // 로그인 시도 시 메시지 초기화
        message.id = "";
        message.password = "";

        const f = loginFormRef.value;

        if (!f.userId.value) {
            alert("사용자아이디를 입력하세요");
            f.userId.focus();
            return;
        }

        if (!f.password.value) {
            alert("비밀번호를 입력하세요");
            f.password.focus();
            return;
        }

        const responseJsonObject = await userApi.userLoginAction(user);

        console.log("UserLoginFormPage response:", responseJsonObject);

        switch (responseJsonObject.status) {

            case ResponseStatusCode.LOGIN_SUCCESS:

                setCookie("member", JSON.stringify(responseJsonObject.data), 1);

                setLoginStatus({
                    isLogin: true,
                    loginUser: responseJsonObject.data
                });

                router.push("/erp_main");
                break;

            case ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER:

                message.id = ResponseMessage.LOGIN_FAIL_NOT_FOUND_USER;
                break;

            case ResponseStatusCode.LOGIN_FAIL_PASSWORD_MISMATCH_USER:

                message.password = ResponseMessage.LOGIN_FAIL_PASSWORD_MISMATCH_USER;
                break;

            default:
                alert("로그인 중 오류가 발생했습니다.");
        }
    };

    return {
        loginFormRef,
        user,
        handleChangeLoginForm,
        message,
        userLoginAction
    };
}