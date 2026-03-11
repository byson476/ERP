import { defineComponent } from "vue";
import useLoginForm from "../../hook/useLoginForm";

export const ErpLoginPage = defineComponent({

  setup() {

    const { 
      loginFormRef, 
      user, 
      handleChangeLoginForm, 
      message, 
      userLoginAction 
    } = useLoginForm();

    return () => (

      <div class="login-page">

        <div class="login-box">

          <h2 class="login-title">ERP LOGIN</h2>

          <form
            ref={loginFormRef}
            name="f"
            method="post"
            onSubmit={(e)=>e.preventDefault()}
          >

            <div class="input-group">
              <label>아이디</label>
              <input
                name="userId"
                type="text"
                placeholder="아이디 입력"
                value={user.userId}
                onInput={handleChangeLoginForm}
              />
              &nbsp;&nbsp;
              <font color="red">
                {message.id}
              </font>
            </div>

            <div class="input-group">
              <label>비밀번호</label>
              <input
                name="password"
                type="password"
                placeholder="비밀번호 입력"
                value={user.password}
                onInput={handleChangeLoginForm}
              />
              &nbsp;&nbsp;
              <font color="red">
                {message.password}
              </font>
            </div>

            <button
              type="button"
              class="btn btn-primary login-btn"
              onClick={userLoginAction}
            >
              로그인
            </button>

          </form>

        </div>

      </div>

    );

  }

});