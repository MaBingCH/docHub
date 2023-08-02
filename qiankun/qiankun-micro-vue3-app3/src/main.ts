/*
import { createApp } from 'vue'
import App from './App.vue'
createApp(App).mount('#app')
*/

//@ts-ignore

import './public-path';
import { createApp } from 'vue';
import { createRouter, createWebHistory } from 'vue-router';
import App from './App.vue';
import Home from './components/Home.vue';
import About from './components/About.vue';
//import routes from './router';
//import store from './store';
let router = null;
//@ts-ignore
let history = null;

const routes = [
  { path: '/home', component: Home },
  { path: '/about', component: About },
]

//@ts-ignore
let instance = null;

function render(props = {}) {
  //@ts-ignore
  const { container } = props;
  //@ts-ignore
  history = createWebHistory(window.__POWERED_BY_QIANKUN__ ? '/micro-app3' : '/');
  router = createRouter({
   history,
   routes,
  });

  instance = createApp(App);
  instance.use(router);
  //instance.use(store);
  instance.mount(container ? container.querySelector('#app') : '#app');
}

//@ts-ignore
if (!window.__POWERED_BY_QIANKUN__) {
  render();
}

export async function bootstrap() {
  console.log('%c%s', 'color: green;', 'vue3.0 app bootstraped');
}
//@ts-ignore
function storeTest(props) {

  console.log(props)

  //@ts-ignore
  props.onGlobalStateChange &&
    props.onGlobalStateChange(
      //@ts-ignore
      (value, prev) => console.log(`[onGlobalStateChange - ${props.name}]:`, value, prev),
      true,
    );
  //@ts-ignore
  props.setGlobalState &&
    props.setGlobalState({
      ignore: props.name,
      user: {
        name: props.name,
      },
    });
}

//@ts-ignore
export async function mount(props) {
  // storeTest(props);
  console.log('-------------vue3 mount-------------');
  console.log(props);

  render(props);
  //@ts-ignore
  instance.config.globalProperties.$onGlobalStateChange = props.onGlobalStateChange;
  //@ts-ignore
  instance.config.globalProperties.$setGlobalState = props.setGlobalState;
}

export async function unmount() {
  console.log('-------------vue3 unmount-------------');
  //@ts-ignore
  instance.unmount();
  //@ts-ignore
  instance._container.innerHTML = '';
  instance = null;
  router = null;
  //@ts-ignore
  history.destroy();
}
