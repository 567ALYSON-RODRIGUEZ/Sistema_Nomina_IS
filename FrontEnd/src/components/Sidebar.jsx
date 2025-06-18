import React, { useState, useEffect } from 'react';
import './Sidebar.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faUsers, faBriefcase, faBuilding, faUserShield, faMoneyCheckAlt,
  faCalendarAlt, faBars, faChevronLeft, faChevronDown, faChevronUp,
  faLandmark, faClock, faUser, faUniversity, faGift, faFileInvoiceDollar,
  faLayerGroup, faSignOutAlt
} from '@fortawesome/free-solid-svg-icons';

function Sidebar({ onSelect }) {
  const [open, setOpen] = useState(true);
  const [mantenimientosOpen, setMantenimientosOpen] = useState(false);
  const [nominaOpen, setNominaOpen] = useState(false);
  const [role, setRole] = useState('');

  useEffect(() => {
    const storedRole = localStorage.getItem('role');
    console.log('Rol desde localStorage:', storedRole);
    setRole(storedRole);
  }, []);

  const handleClick = (option) => {
    onSelect(option);
    setOpen(false);
  };

  const handleLogout = () => {
    localStorage.clear();
    window.location.reload();
  };

  return (
    <div className={`sidebar ${open ? 'open' : 'closed'}`}>
      <div className="toggle-button" onClick={() => setOpen(!open)}>
        {open ? <FontAwesomeIcon icon={faChevronLeft} /> : <FontAwesomeIcon icon={faBars} />}
      </div>

      {open && (
        <ul className="menu">
          
          <li className="info-message">
          Has iniciado sesión como: <strong>{role}</strong>
          </li>

          {(role === 'Admin' || role === 'RRHH') && (
            <>
              <li onClick={() => setMantenimientosOpen(!mantenimientosOpen)}>
                <FontAwesomeIcon icon={faLayerGroup} /> Mantenimientos {mantenimientosOpen ? <FontAwesomeIcon icon={faChevronUp} /> : <FontAwesomeIcon icon={faChevronDown} />}
              </li>
              {mantenimientosOpen && (
                <ul className="submenu">
                  <li onClick={() => handleClick('empleado')}><FontAwesomeIcon icon={faUsers} /> Empleados</li>
                  {role === 'Admin' && (
                    <>
                      <li onClick={() => handleClick('usuario')}><FontAwesomeIcon icon={faUser} /> Usuarios</li>
                      <li onClick={() => handleClick('empresa')}><FontAwesomeIcon icon={faUniversity}/> Empresa</li>
                      <li onClick={() => handleClick('puesto')}><FontAwesomeIcon icon={faBriefcase} /> Puestos</li>
                      <li onClick={() => handleClick('departamento')}><FontAwesomeIcon icon={faBuilding} /> Departamentos</li>
                      <li onClick={() => handleClick('rol')}><FontAwesomeIcon icon={faUserShield} /> Roles</li>
                      <li onClick={() => handleClick('anticipo')}><FontAwesomeIcon icon={faFileInvoiceDollar} /> Anticipos</li>
                      <li onClick={() => handleClick('Igss')}><FontAwesomeIcon icon={faLandmark} /> IGSS P</li>
                      <li onClick={() => handleClick('bonificacion')}><FontAwesomeIcon icon={faGift} /> Bonificaciones</li>
                      <li onClick={() => handleClick('asistencia')}><FontAwesomeIcon icon={faClock} /> Asistencias</li>
                      <li onClick={() => handleClick('nominas')}><FontAwesomeIcon icon={faMoneyCheckAlt} /> Nóminas</li>
                      <li onClick={() => handleClick('permisos')}><FontAwesomeIcon icon={faUserShield} /> Permisos</li>
                      <li onClick={() => handleClick('isr')}><FontAwesomeIcon icon={faUserShield} /> Calculo ISR</li>
                      <li onClick={() => handleClick('vacaciones')}><FontAwesomeIcon icon={faClock} /> Vacaciones</li>
                    </>
                  )}
                </ul>
              )}
            </>
          )}

          {(role === 'Admin' || role === 'RRHH') && (
            <>
              <li onClick={() => setNominaOpen(!nominaOpen)}>
                <FontAwesomeIcon icon={faMoneyCheckAlt} /> Nómina {nominaOpen ? <FontAwesomeIcon icon={faChevronUp} /> : <FontAwesomeIcon icon={faChevronDown} />}
              </li>
              {nominaOpen && (
                <ul className="submenu">
                  <li onClick={() => handleClick('detallenominaM')}><FontAwesomeIcon icon={faFileInvoiceDollar} /> Nómina Mensual</li>
                  <li onClick={() => handleClick('detallenominaQ')}><FontAwesomeIcon icon={faFileInvoiceDollar} /> Nómina Quincenal</li>
                  <li onClick={() => handleClick('detallenominaS')}><FontAwesomeIcon icon={faFileInvoiceDollar} /> Nómina Semanal</li>
                  <li onClick={() => handleClick('periodo')}><FontAwesomeIcon icon={faCalendarAlt} /> Periodos de Nómina</li>
              <li onClick={() => handleClick('planilla')}><FontAwesomeIcon icon={faUserShield} /> Planilla Aguinaldo</li>
              <li onClick={() => handleClick('planilla14')}><FontAwesomeIcon icon={faUserShield} /> Planilla Bono 14</li>
              <li onClick={() => handleClick('finiquito')}><FontAwesomeIcon icon={faUserShield} /> Finiquito</li>
         
                </ul>
              )}
            </>
          )}

          <li onClick={handleLogout}>
            <FontAwesomeIcon icon={faSignOutAlt} /> Cerrar sesión
          </li>
        </ul>
      )}
    </div>
  );
}

export default Sidebar;
