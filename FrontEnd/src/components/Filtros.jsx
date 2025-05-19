import React from 'react';
import './Filtros.css';

function Filtros() {
  const cerrar = () => {
    alert("Cerrar filtros...");
  };

  return (
    <div className="contenedor-filtros">
      <div className="form-filtros">
        <div className="encabezado">
          <span>Filtros</span>
          <button onClick={cerrar}>×</button>
        </div>
        <div className="cuerpo">
          <div className="filas">
            <label>Empresa:</label>
            <input type="text" placeholder="Buscar..." />
          </div>
          <div className="filas">
            <label>Sucursal:</label>
            <input type="text" defaultValue="%" />
            <span>Todos</span>
          </div>
          <div className="filas">
            <label>Grupo empleado:</label>
            <input type="text" defaultValue="%" />
            <span>Todos</span>
          </div>
          <div className="filas">
            <label>Departamento:</label>
            <input type="text" defaultValue="%" />
            <span>Todos</span>
          </div>
          <div className="filas">
            <label>Puesto:</label>
            <input type="text" defaultValue="%" />
            <span>Todos</span>
          </div>
          <div className="filas">
            <label>Empleado:</label>
            <input type="text" defaultValue="%" />
            <span>Todos</span>
          </div>
        </div>
        <div className="botones">
          <button className="btn-aceptar">Aceptar</button>
          <button className="btn-cancelar" onClick={cerrar}>Cancelar</button>
        </div>
      </div>
    </div>
  );
}

export default Filtros;
