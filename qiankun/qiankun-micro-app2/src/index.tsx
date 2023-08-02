import "./public-path";

import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from "react-router-dom";

/**
 * bootstrap 只会在微应用初始化的时候调用一次，下次微应用重新进入时会直接调用 mount 钩子，不会再重复触发 bootstrap。
 * 通常我们可以在这里做一些全局变量的初始化，比如不会在 unmount 阶段被销毁的应用级别的缓存等。
 */
export async function bootstrap() {
  console.log('react2 app bootstraped');
}

let root: ReactDOM.Root | null = null;

/**
 * 应用每次进入都会调用 mount 方法，通常我们在这里触发应用的渲染方法
 */
export async function mount(props: { container: { querySelector: (arg0: string) => any; }; }) {
  console.log("-----micro app2 mount--------");
  console.log(props);

  root = ReactDOM.createRoot(props.container ? props.container.querySelector('#root') : document.getElementById('root'));  
  root.render(
    //@ts-ignore
    <BrowserRouter basename={window.__POWERED_BY_QIANKUN__?'/micro-app2':'/'}>
    <App />
    </BrowserRouter>
  );
}

/**
 * 应用每次 切出/卸载 会调用的方法，通常在这里我们会卸载微应用的应用实例
 */
export async function unmount(props: { container: { querySelector: (arg0: string) => any; }; }) {
  console.log('unmount', props);
  console.log("-----micro app2 unmount--------");
  if (root == null) {
    root = ReactDOM.createRoot(props.container ? props.container.querySelector('#root') : document.getElementById('root'));
  }

  root.unmount();
  root = null;
}

/**
 * 可选生命周期钩子，仅使用 loadMicroApp 方式加载微应用时生效
 */
export async function update(props: any) {
  console.log('update props', props);
}

//@ts-ignore
if (!window.__POWERED_BY_QIANKUN__) {
  console.log("-----micro app2 __POWERED_BY_QIANKUN__--------");
  if (root == null) {
    root = ReactDOM.createRoot(
      document.getElementById('root') as HTMLElement
    );
  }
  
  root.render(
    <React.StrictMode>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </React.StrictMode>
  );
}


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
