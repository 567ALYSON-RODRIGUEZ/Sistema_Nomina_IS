import React, { useState } from 'react';
import axios from 'axios';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import './Finiquito.css';

function FiniquitoPanel() {
  const [idEmpleado, setIdEmpleado] = useState('');
  const [resumen, setResumen] = useState([]);

  const generarFiniquito = () => {
    if (!idEmpleado) {
      alert("Debe ingresar el ID del empleado.");
      return;
    }

    axios.post(`http://localhost:8095/finiquito/generar/${idEmpleado}`, {}, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => {
        alert(res.data);
        cargarResumen();
      })
      .catch(err => {
        console.error("Error al generar finiquito:", err);
        alert("Error al generar el finiquito.");
      });
  };

  const cargarResumen = () => {
    if (!idEmpleado) {
      alert("Debe ingresar el ID del empleado.");
      return;
    }

    axios.get(`http://localhost:8095/finiquito/resumen/${idEmpleado}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setResumen(res.data))
      .catch(err => {
        console.error("Error al obtener resumen:", err);
        alert("No se encontró un finiquito para ese empleado.");
        setResumen([]);
      });
  };

  const generarPDF = () => {
    if (resumen.length === 0) {
      alert("No hay resumen para exportar.");
      return;
    }

    const data = resumen[0];
    const doc = new jsPDF();

    doc.setFontSize(16);
    doc.text("Resumen de Finiquito", 14, 20);

    doc.setFontSize(12);
    doc.text(`ID Empleado: ${idEmpleado}`, 14, 30);
    doc.text(`Nombre: ${data.nombreEmpleado}`, 14, 38);
    doc.text(`Puesto: ${data.nombrePuesto}`, 14, 46);
    doc.text(`Departamento: ${data.nombreDepartamento}`, 14, 54);

    autoTable(doc, {
      head: [['Fecha', 'Sueldo', 'Aguinaldo', 'Bono 14', 'Vacaciones', 'Indemnización', 'Bonificación', 'Total']],
      body: [[
        data.fechaLiquidacion,
        `Q. ${Number(data.sueldoPendiente).toFixed(2)}`,
        `Q. ${Number(data.aguinaldoProporcional).toFixed(2)}`,
        `Q. ${Number(data.bono14Proporcional).toFixed(2)}`,
        `Q. ${Number(data.vacacionesNoGozadas).toFixed(2)}`,
        `Q. ${Number(data.indemnizacion).toFixed(2)}`,
        `Q. ${Number(data.bonificacionIncentivo).toFixed(2)}`,
        `Q. ${Number(data.totalLiquidacion).toFixed(2)}`
      ]],
      startY: 65
    });

    doc.save(`finiquito_empleado_${idEmpleado}.pdf`);
  };

  return (
    <div className="contenedor-panel">
      <div className="barra-superiorr">
        <h2>Finiquito</h2>
        <div className="acciones-barra">
          <input
            type="number"
            value={idEmpleado}
            onChange={e => setIdEmpleado(e.target.value)}
            placeholder="ID del Empleado"
          />
          <button className="btn-buscar" onClick={generarFiniquito}>Generar Finiquito</button>
          <button className="btn-buscar" onClick={cargarResumen}>Ver Resumen</button>
          <button className="btn-buscar" onClick={generarPDF}>Descargar PDF</button>
        </div>
      </div>

      {resumen.length > 0 && (
        <div className="resumen-finiquito">
          <h3>Resumen de Finiquito</h3>
          <p><strong>Empleado:</strong> {resumen[0].nombreEmpleado}</p>
          <p><strong>Puesto:</strong> {resumen[0].nombrePuesto}</p>
          <p><strong>Departamento:</strong> {resumen[0].nombreDepartamento}</p>

          <table className="tabla-puestos">
            <thead>
              <tr>
                <th>Fecha</th>
                <th>Sueldo</th>
                <th>Aguinaldo</th>
                <th>Bono 14</th>
                <th>Vacaciones</th>
                <th>Indemnización</th>
                <th>Bonificación</th>
                <th>Total</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{resumen[0].fechaLiquidacion}</td>
                <td>Q. {Number(resumen[0].sueldoPendiente ?? 0).toFixed(2)}</td>
                <td>Q. {Number(resumen[0].aguinaldoProporcional ?? 0).toFixed(2)}</td>
                <td>Q. {Number(resumen[0].bono14Proporcional ?? 0).toFixed(2)}</td>
                <td>Q. {Number(resumen[0].vacacionesNoGozadas ?? 0).toFixed(2)}</td>
                <td>Q. {Number(resumen[0].indemnizacion ?? 0).toFixed(2)}</td>
                <td>Q. {Number(resumen[0].bonificacionIncentivo ?? 0).toFixed(2)}</td>
                <td><strong>Q. {Number(resumen[0].totalLiquidacion ?? 0).toFixed(2)}</strong></td>
              </tr>
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default FiniquitoPanel;
