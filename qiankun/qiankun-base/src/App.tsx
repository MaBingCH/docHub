import React from 'react';
import logo from './logo.svg';
import { Link, Route, Routes } from "react-router-dom";

import './App.css';

function App() {
  return (
    <div className="App">
      main app for qiankun
      <br/>
      BrowserRouter&nbsp;&nbsp;
      <Link to={"/micro-app1"}>micro-app1</Link>
      <Link to={"/micro-app2"}>micro-app2</Link>
      <Link to={"/micro-app3"}>micro-app3</Link>
      <hr/>
      href&nbsp;&nbsp;
      <a href='/micro-app1'>micro-app1</a>
      <a href='/micro-app2'>micro-app2</a>
      <a href='/micro-app3'>micro-app3</a>
      <hr/>
      
      <div id="micro-app1"></div>
      <div id="micro-app2"></div>
      <div id="micro-app3"></div>
    </div>
  );
}

export default App;
