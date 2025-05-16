import React, { useState } from 'react';
import './DetalleConceptos.css';

const DetalleConceptos = () => {
  const [visible, setVisible] = useState(true);
  const [minimizado, setMinimizado] = useState(false);

  if (!visible) return null;

  return (
    <div className="detalle-conceptos">
      <div className="header">
        Detalle de conceptos
        <div className="botones-header">
          <button className="minimizar-btn" onClick={() => setMinimizado(!minimizado)}>🗕</button>
          <button className="cerrar-btn" onClick={() => setVisible(false)}>✖</button>
        </div>
      </div>

      {!minimizado && (
        <>
          <div className="info-empleado">
            <div><label>Empleado:</label> <input type="text" className="input-empleado" /></div>
            <div><label>Fecha alta:</label> <input type="text" className="input-empleado" /></div>
            <div><label>Código:</label> <input type="text" className="input-empleado" /></div>
            <div><label>Fecha baja:</label> <input type="text" className="input-empleado" /></div>
            <div><label>Nombre:</label> <input type="text" className="input-empleado" /></div>
            <div><label>Departamento:</label> <input type="text" className="input-empleado" /></div>
            <div><label>Puesto:</label> <input type="text" className="input-empleado" /></div>
            <div><label>Sueldo diario:</label> <input type="text" className="input-empleado" /></div>
          </div>

          <button className="copiar-btn">Copiar información</button>

          <table className="tabla-conceptos">
            <thead>
              <tr>
                <th>Clave</th>
                <th>Concepto</th>
                <th>Tipo</th>
                <th>Cantidad</th>
                <th>Percepciones</th>
                <th>Deducciones</th>
                <th>Estadístico</th>
              </tr>
            </thead>
            <tbody>
              <tr><td colSpan="7">Sin datos por ahora</td></tr>
            </tbody>
          </table>

          <div className="totales">
            <span>Percepciones:</span>
            <span>Deducciones:</span>
            <span>Neto a pagar:</span>
          </div>
        </>
      )}
    </div>
  );
};

export default DetalleConceptos;
