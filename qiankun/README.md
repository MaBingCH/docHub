## 创建项目
#mkdir qiankun
#cd qiankun

#npx create-react-app qiankun-base --template typescript
#npx create-react-app qiankun-micro-app1 --template typescript 
#npx create-react-app qiankun-micro-app2 --template typescript 

#vue create qiankun-micro-vue3-app3
#vue add typescript

## 主应用安装
npm i qiankun -S
## 给每个项目加入不同端口号
#cat .env
PORT=3010
...
## 主应用修改
### 注册微应用
#https://qiankun.umijs.org/guide/getting-started 
```
import { registerMicroApps, start } from 'qiankun';

registerMicroApps([
  {
    name: 'react app',
    entry: '//localhost:3011',//端口号
    container: '#micro-app1',//挂载点
    activeRule: '/micro-app1',//路由
  },
  {
    name: 'vue app',
    entry: '//localhost:3012',
    container: '#micro-app2',
    activeRule: '/micro-app2',
  },
]);

start();
```
### 编写挂载点
## 微应用修改
### 在 src 目录新增 public-path.js
if (window.__POWERED_BY_QIANKUN__) {
  /* global __webpack_public_path__:writable */
  __webpack_public_path__ = window.__INJECTED_PUBLIC_PATH_BY_QIANKUN__;
}

### 微应用中到处生命周期
import './public-path'

function render(props) {
  const { container } = props;
  ReactDOM.render(<App />, container ? container.querySelector('#root') : document.querySelector('#root'));
}

if (!window.__POWERED_BY_QIANKUN__) {
  render({});
}

export async function bootstrap() {
  console.log('[react16] react app bootstraped');
}

export async function mount(props) {
  console.log('[react16] props from main framework', props);
  render(props);
}

export async function unmount(props) {
  const { container } = props;
  ReactDOM.unmountComponentAtNode(container ? container.querySelector('#root') : document.querySelector('#root'));
}

### 微应用install react-app-rewired
npm i react-app-rewired

#### 根目录创建config-overrides.js
const { name } = require('./package');

module.exports = {
  webpack: (config) => {
    config.output.library = `${name}-[name]`;
    config.output.libraryTarget = 'umd';//采用umd方式注入
    //webpack5要把 jsonpFunction，改为 chunkLoadingGlobal
    config.output.chunkLoadingGlobal = `webpackJsonp_${name}`;
    config.output.globalObject = 'window';

    return config;
  },

  devServer: (_) => {
    const config = _;

    config.headers = {
      'Access-Control-Allow-Origin': '*',//允许跨域，主应用才能使用微应用资源
    };
    config.historyApiFallback = true;
    config.hot = false;
    config.watchContentBase = false;
    config.liveReload = false;

    return config;
  },
};


## 启动项目
npm run start

## router for react
npm i react-router-dom

## router for vue
npm i vue-router

## 参考文档

https://qiankun.umijs.org/zh/guide/tutorial

https://blog.csdn.net/weixin_47886687/article/details/129647462?spm=1001.2101.3001.6650.5&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-5-129647462-blog-130861486.235%5Ev38%5Epc_relevant_anti_vip&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-5-129647462-blog-130861486.235%5Ev38%5Epc_relevant_anti_vip&utm_relevant_index=10



### 问题1
Eslint 的问题， webpack_public_path 不是全局变量所导致的。
解决方式：
在 子应用 package.json 文件中 eslintConfig 配置全局变量后 重起服务。
"eslintConfig": {
    ...,
    "globals": {
      "__webpack_public_path__": true
    }
}
