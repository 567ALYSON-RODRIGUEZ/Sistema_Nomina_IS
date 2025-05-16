import React, { useState } from 'react';
import './Sidebar.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faUsers, faBriefcase, faBuilding, faUserShield, faMoneyCheckAlt,
  faCalendarAlt, faBars, faChevronLeft, faChevronDown, faChevronUp, faLandmark
} from '@fortawesome/free-solid-svg-icons';

function Sidebar({ onSelect }) {
  const [open, setOpen] = useState(true);
  const [mantenimientosOpen, setMantenimientosOpen] = useState(false);
  const [nominaOpen, setNominaOpen] = useState(false);

  const handleClick = (option) => {
    onSelect(option);
    setOpen(false);
  };

  return (
    <div className={`sidebar ${open ? 'open' : 'closed'}`}>
      <div className="toggle-button" onClick={() => setOpen(!open)}>
        {open ? <FontAwesomeIcon icon={faChevronLeft} /> : <FontAwesomeIcon icon={faBars} />}
      </div>

      {open && (
        <ul className="menu">
          <li onClick={() => setMantenimientosOpen(!mantenimientosOpen)}>
            <FontAwesomeIcon icon={faUsers} /> Mantenimientos {mantenimientosOpen ? <FontAwesomeIcon icon={faChevronUp} /> : <FontAwesomeIcon icon={faChevronDown} />}
          </li>
          {mantenimientosOpen && (
            <ul className="submenu">
              <li onClick={() => handleClick('empleado')}><FontAwesomeIcon icon={faUsers} /> Empleados</li>
              <li onClick={() => handleClick('usuario')}><FontAwesomeIcon icon={faUserShield} /> Usuarios</li>
              <li onClick={() => handleClick('empresa')}><FontAwesomeIcon icon={faLandmark}/> Empresa</li>
              <li onClick={() => handleClick('puesto')}><FontAwesomeIcon icon={faBriefcase} /> Puestos</li>
              <li onClick={() => handleClick('departamento')}><FontAwesomeIcon icon={faBuilding} /> Departamentos</li>
              <li onClick={() => handleClick('rol')}><FontAwesomeIcon icon={faUserShield} /> Roles</li>
            </ul>
          )}

          <li onClick={() => setNominaOpen(!nominaOpen)}>
            <FontAwesomeIcon icon={faMoneyCheckAlt} /> Nómina {nominaOpen ? <FontAwesomeIcon icon={faChevronUp} /> : <FontAwesomeIcon icon={faChevronDown} />}
          </li>
          {nominaOpen && (
            <ul className="submenu">
              <li onClick={() => handleClick('periodo')}><FontAwesomeIcon icon={faCalendarAlt} /> Periodos de Nómina</li>
              <li onClick={() => handleClick('nomina')}><FontAwesomeIcon icon={faMoneyCheckAlt} /> Generar Nómina</li>
            </ul>
          )}
        </ul>
      )}
    </div>
  );
}

export default Sidebar;
