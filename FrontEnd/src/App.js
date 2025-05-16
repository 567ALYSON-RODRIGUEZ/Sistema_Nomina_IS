import React, { useState } from 'react';
import PeriodoNomina from './components/PeriodoNomina';
import DetalleConceptos from './components/DetalleConceptos';
import EmpleadoCrud from './components/EmpleadoCrud';
import Sidebar from './components/Sidebar'; 
import './App.css';

function App() {
  const [selected, setSelected] = useState('empleado');

  const renderContent = () => {
    switch (selected) {
      case 'empleado':
        return <EmpleadoCrud />;
      case 'periodo':
        return <PeriodoNomina />;
      case 'detalle':
        return <DetalleConceptos />;
      default:
        return <h2>Bienvenido</h2>;
    }
  };

  return (
    <div className="app-container">
      <Sidebar onSelect={setSelected} />
      <div className="main-content">
        {renderContent()}
      </div>
    </div>
  );
}

export default App;



