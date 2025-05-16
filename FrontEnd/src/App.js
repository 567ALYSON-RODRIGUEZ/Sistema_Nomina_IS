import React, { useState } from 'react';
import PeriodoNomina from './components/PeriodoNomina';
import DetalleConceptos from './components/DetalleConceptos';
import EmpleadoCrud from './components/EmpleadoCrud';
import Departamentos from './components/Departamentos';
import Roles from './components/Roles'
import Puestos from './components/Puestos'
import Sidebar from './components/Sidebar'; 
import Nomina from './components/Nomina';
import Empresa from './components/Empresa';
import Usuarios from './components/Usuarios'

import './App.css';


function App() {
  const [selected, setSelected] = useState('');

  const renderContent = () => {
    switch (selected) {
      case 'empleado':
        return <EmpleadoCrud />;
      case 'nomina':
        return <Nomina />;
      case 'periodo':
        return <PeriodoNomina />;
      case 'detalle':
        return <DetalleConceptos />;
        case 'rol':
        return <Roles />;
      case 'puesto':
        return <Puestos />;
      case 'departamento':
        return <Departamentos />;
      case 'empresa':
        return <Empresa />
      case 'usuario':
        return <Usuarios />
      
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



