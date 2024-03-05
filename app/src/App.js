import React from 'react';
import './App.css';
import Home from './Home';
import { Routes, Route,  BrowserRouter } from 'react-router-dom'; // Import only necessary components
import AccountList from './AccountList';
import AccountEdit from './AccountEdit';
import MovementList from './MovementList';
import MovementEdit from './MovementEdit';
import CustomerList from './CustomerList';
import CustomerEdit from './CustomerEdit';

const App = () => {
  return (
    <BrowserRouter> 
      <Routes>
        <Route path="/" element={<Home/>} /> 
        <Route path="/accounts" element={<AccountList />} />
        <Route path="/accounts/:id" element={<AccountEdit />} /> 
        <Route path="/movements" element={<MovementList />} />
        <Route path="/movements/:id" element={<MovementEdit />} /> 
        <Route path="/customers" element={<CustomerList />} />
        <Route path="/customers/:id" element={<CustomerEdit />} /> 
      </Routes>
    </BrowserRouter>
  );
};

export default App;
