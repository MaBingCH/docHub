import React from 'react';
import logo from './logo.svg';
import './App.css';
import Cat from './pages/Cat'
import Dog from './pages/Dog'
import { Routes,Link, Route, Outlet, } from 'react-router-dom';

function App() {
  return (

    <div className="App">
      qiankun micro app1
      
      <Link to={"/cat"}>cat</Link> |
      <Link to={"/dog"}>dog</Link>
      <br />
      <hr/>
      xxxxx
      <Routes >
        <Route path="/cat"  element={<Cat/>}/>
        <Route path="/dog" element={<Dog/>}/>
      </Routes>
    </div>
  );
}

export default App;
