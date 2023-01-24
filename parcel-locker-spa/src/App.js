import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Nav from './common/layouts/components/Nav/Nav';
import LogIn from './pages/LogIn';
import { ClientPanel } from './modules/clients/ClientPanel';
import { DeliveriesPanel } from './modules/deliveries/DeliveriesPanel';

function App() {
  return (
    <Router>
      <Nav />
      <Routes>
        <Route path="/" exact element={<LogIn />} />
        <Route path="/log-in" element={<LogIn />} />
        <Route path="/deliveries" element={<DeliveriesPanel />} />
        <Route path="/clients/self" element={<ClientPanel />} />
      </Routes>
    </Router>
  );
}

export default App;
