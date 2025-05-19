import React from 'react';
import './Nomina.css';

function GenerarNomina() {
  return (
    <div className="contenedor-nomina">
      <div className="barra-superior">
        <span className="titulo">Generación de Nómina</span>
        <button className="btn-generar">Generar nómina</button>
      </div>

      <div className="formulario-grid">
        <div className="campo">
          <label>Carátula:</label>
          <input type="text" />
        </div>

        <div className="campo doble">
          <label>Sucursal:</label>
          <input type="text" defaultValue="0001" />
          <span>MATRIZ</span>
        </div>

        <div className="campo doble">
          <label>Configuración:</label>
          <input type="text" defaultValue="NSS1" />
          <span>NOMINA SUELDOS Y SALARIOS</span>
        </div>

        <div className="campo doble">
          <label>Periodo:</label>
          <input type="text" defaultValue="000000002" />
          <span>SEGUNDA QUINCENA MARZO 2020</span>
        </div>

        <div className="campo">
          <label>Fecha de pago:</label>
          <input type="date" defaultValue="2020-03-31" />
        </div>

        <div className="campo completo">
          <label>Comentario:</label>
          <input type="text" defaultValue="PAGO NOMINA 2A QUINCENA MARZO 2020" />
        </div>

        <div className="campo">
          <label>Serie para timbrado:</label>
          <input type="text" defaultValue="NOM" />
        </div>

        <div className="campo">
          <label>Tipo de nómina:</label>
          <input type="text" defaultValue="Normal" />
        </div>

        <div className="campo">
          <label>Fecha inicial:</label>
          <input type="text" defaultValue="16/03/2020" />
        </div>

        <div className="campo">
          <label>Fecha final:</label>
          <input type="text" defaultValue="31/03/2020" />
        </div>

        <div className="campo">
          <label>Referencia:</label>
          <input type="text" />
        </div>

        <div className="campo check">
          <label><input type="checkbox" /> Modo depuración</label>
          <label><input type="checkbox" /> Mostrar log</label>
          <button className="btn-carga">Cargar empleados</button>
        </div>

      
      </div>

      <table className="tabla-empleados">
        <thead>
          <tr>
            <th>Sucursal</th>
            <th>Clave</th>
            <th>RFC</th>
            <th>Nombre</th>
            <th>Puesto</th>
            <th>Departamento</th>
            <th>Percepción</th>
            <th>Deducción</th>
            <th>Neto a pagar</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td colSpan="9" style={{ height: '300px' }}></td>
          </tr>
        </tbody>
      </table>

      <div className="resumen">
        <div>Empleados: 0</div>
        <div>Percepciones: Q 0.00</div>
        <div>Deducciones: Q 0.00</div>
        <div>Neto a pagar: Q 0.00</div>
      </div>
    </div>
  );
}

export default GenerarNomina;