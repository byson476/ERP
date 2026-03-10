import { createApp } from 'vue';
import App from './App.jsx';
import router from './router'; // 아래 3번에서 만들 파일

const app = createApp(App);
app.use(router);
app.mount('#container');
