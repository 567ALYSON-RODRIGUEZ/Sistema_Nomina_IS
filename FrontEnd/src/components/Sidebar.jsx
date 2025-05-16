import React, { useState } from 'react';
import './Sidebar.css';

function Sidebar({ onSelect }) {
  const [open, setOpen] = useState(true);
  const [mantenimientosOpen, setMantenimientosOpen] = useState(false);

  const handleClick = (option) => {
    onSelect(option);
    setOpen(false);
  };

  return (
    <div className={`sidebar ${open ? 'open' : 'closed'}`}>
      <div className="toggle-button" onClick={() => setOpen(!open)}>
        {open ? '⏴' : '☰'}
      </div>

      {open && (
        <ul className="menu">
          <li onClick={() => setMantenimientosOpen(!mantenimientosOpen)}>
            Mantenimientos {mantenimientosOpen ? '▲' : '▼'}
          </li>

          {mantenimientosOpen && (
            <ul className="submenu">
              <li onClick={() => handleClick('empleado')}>Empleados</li>
              <li onClick={() => handleClick('puesto')}>Puestos</li>
              <li onClick={() => handleClick('departamento')}>Departamentos</li>
            </ul>
          )}

          <li onClick={() => handleClick('periodo')}>Periodo Nómina</li>
          <li onClick={() => handleClick('detalle')}>Detalle de Conceptos</li>
        </ul>
      )}
    </div>
  );
}

export default Sidebar;
