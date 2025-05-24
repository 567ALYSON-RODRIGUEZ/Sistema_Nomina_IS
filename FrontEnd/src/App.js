import React, { useState } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';

import EmpleadoCrud from './components/EmpleadoCrud';
import PeriodoNomina from './components/PeriodoNomina';
import DetalleConceptos from './components/DetalleConceptos';
import Departamentos from './components/Departamentos';
import Roles from './components/Roles';
import Puestos from './components/Puestos';
import Empresa from './components/Empresa';
import Usuarios from './components/Usuarios';
import Sidebar from './components/Sidebar';
import Login from './components/Login';
import Permisos from './components/Permisos';
import Isr from './components/Isr';
import PlanillaAguinaldo from './components/PlanillaAguinaldo';
import PlanillaBono14 from './components/PlanillaBono14';
import Finiquito from './components/Finiquito';
import { useNavigate } from 'react-router-dom';

import './App.css';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [role, setRole] = useState('');
  const navigate = useNavigate();

  const ProtectedLayout = ({ children }) => {
    if (!isAuthenticated) return <Navigate to="/login" replace />;
    return (
      <div className="app-container">
        <Sidebar role={role} onSelect={(ruta) => navigate(`/${ruta}`)} />
        <div className="main-content">{children}</div>
      </div>
    );
  };

  return (
    <Routes>
      <Route
        path="/login"
        element={
          isAuthenticated ? (
            <Navigate to="/" replace />
          ) : (
            <Login setIsAuthenticated={setIsAuthenticated} setRole={setRole} />
          )
        }
      />
      <Route
        path="/"
        element={
          <ProtectedLayout>
            <h2>Bienvenida</h2>
          </ProtectedLayout>
        }
      />
      <Route
        path="/empleado"
        element={
          <ProtectedLayout>
            <EmpleadoCrud />
          </ProtectedLayout>
        }
      />
      <Route
        path="/periodo"
        element={
          <ProtectedLayout>
            <PeriodoNomina />
          </ProtectedLayout>
        }
      />
      <Route
        path="/detalle"
        element={
          <ProtectedLayout>
            <DetalleConceptos />
          </ProtectedLayout>
        }
      />
      <Route
        path="/rol"
        element={
          <ProtectedLayout>
            <Roles />
          </ProtectedLayout>
        }
      />
      <Route
        path="/puesto"
        element={
          <ProtectedLayout>
            <Puestos />
          </ProtectedLayout>
        }
      />
      <Route
        path="/departamento"
        element={
          <ProtectedLayout>
            <Departamentos />
          </ProtectedLayout>
        }
      />
      <Route
        path="/empresa"
        element={
          <ProtectedLayout>
            <Empresa />
          </ProtectedLayout>
        }
      />
      <Route
        path="/usuario"
        element={
          <ProtectedLayout>
            <Usuarios />
          </ProtectedLayout>
        }
      />
      <Route
        path="/permisos"
        element={
          <ProtectedLayout>
            <Permisos />
          </ProtectedLayout>
        }
      />
      <Route
        path="/isr"
        element={
          <ProtectedLayout>
            <Isr />
          </ProtectedLayout>
        }
      />
      <Route
        path="/planilla"
        element={
          <ProtectedLayout>
            <PlanillaAguinaldo />
          </ProtectedLayout>
        }
      />
      <Route
        path="/planilla14"
        element={
          <ProtectedLayout>
            <PlanillaBono14 />
          </ProtectedLayout>
        }
      />
      <Route
        path="/finiquito"
        element={
          <ProtectedLayout>
            <Finiquito />
          </ProtectedLayout>
        }
      />
    </Routes>
  );
}

export default App;
