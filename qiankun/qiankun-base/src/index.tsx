import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

import { registerMicroApps, start } from 'qiankun';
import { BrowserRouter } from 'react-router-dom';

registerMicroApps([
  {
    name: 'react1 app', // app name registered
    entry: '//localhost:3011',
    container: '#micro-app1',
    activeRule: '/micro-app1',
  },
  {
    name: 'react2 app',
    entry: '//localhost:3012',
    container: '#micro-app2',
    activeRule: '/micro-app2',
  },
  {
    name: 'vue3 app',
    entry: '//localhost:3013',
    container: '#micro-app3',
    activeRule: '/micro-app3',
  },
]);

start();

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
