import React, { useState } from 'react';
import axios from 'axios';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import './Finiquito.css';

function FiniquitoPanel() {
  const [idEmpleado, setIdEmpleado] = useState('');
  const [resumen, setResumen] = useState([]);
  const [detalleFiniquito, setDetalleFiniquito] = useState(null);

  const generarFiniquito = () => {
    if (!idEmpleado) {
      alert("Debe ingresar el ID del empleado.");
      return;
    }

    axios.post(
      `http://localhost:8095/finiquito/generar/${idEmpleado}`,
      null,
      {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      }
    )
    .then(res => {
      const partes = res.data.split(',');

      const detalle = {
        nombre: partes[0],
        puesto: partes[1],
        departamento: partes[2],
        sueldoPendiente: parseFloat(partes[3]),
        aguinaldo: parseFloat(partes[4]),
        bono14: parseFloat(partes[5]),
        vacaciones: parseFloat(partes[6]),
        indemnizacion: parseFloat(partes[7]),
        bonificacion: parseFloat(partes[8]),
        total: parseFloat(partes[9]),
        fecha: partes[10]
      };

      setDetalleFiniquito(detalle);
    
    })
    .catch(err => {

      alert("Error al generar el finiquito.");
    });
  };

  const generarPDF = () => {
  if (!detalleFiniquito) {
    alert("No hay finiquito generado para exportar.");
    return;
  }

  const data = detalleFiniquito;
  const doc = new jsPDF();

  // Título
  doc.setFontSize(12);
  doc.text("Resumen de Finiquito", 14, 20);

  // Datos generales
  doc.setFontSize(12);
  doc.text(`ID Empleado: ${idEmpleado}`, 14, 30);
  doc.text(`Nombre: ${data.nombre ?? 'N/D'}`, 14, 38);
  doc.text(`Puesto: ${data.puesto ?? 'N/D'}`, 14, 46);
  doc.text(`Departamento: ${data.departamento ?? 'N/D'}`, 14, 54);

  // Tabla con montos
  autoTable(doc, {
    head: [[
      'Fecha', 'Sueldo', 'Aguinaldo', 'Bono 14', 'Vacaciones',
      'Indemnización', 'Bonificación', 'Total'
    ]],
    body: [[
      data.fecha,
      `Q. ${data.sueldoPendiente.toFixed(2)}`,
      `Q. ${data.aguinaldo.toFixed(2)}`,
      `Q. ${data.bono14.toFixed(2)}`,
      `Q. ${data.vacaciones.toFixed(2)}`,
      `Q. ${data.indemnizacion.toFixed(2)}`,
      `Q. ${data.bonificacion.toFixed(2)}`,
      `Q. ${data.total.toFixed(2)}`
    ]],
    startY: 60,
    styles: { fillColor: [41, 128, 185], fillColor: [200, 230, 255] }, // azul con texto blanco
    alternateRowStyles: { fillColor: [240, 240, 240] }
  });

  doc.save(`finiquito_empleado_${idEmpleado}.pdf`);
};
  return (
    <div className="contenedor-panel">
      <div className="barra-superiorr">
        <h2>Liquidacion</h2>
        <div className="acciones-barra">
          <input
            type="number"
            value={idEmpleado}
            onChange={e => setIdEmpleado(e.target.value)}
            placeholder="ID del Empleado"
          />
          <button className="btn-nuevo" onClick={generarFiniquito}>Generar Finiquito</button>
          <button className="btn-buscar" onClick={generarPDF}>Descargar PDF</button>
        </div>
      </div>

      {detalleFiniquito && (
        <div className="resumen-finiquito">
          <h3>Finiquito generado</h3>
          <table className="tabla-puestos">
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Puesto</th>
                <th>Departamento</th>
                <th>Sueldo</th>
                <th>Aguinaldo</th>
                <th>Bono 14</th>
                <th>Vacaciones</th>
                <th>Indemnización</th>
                <th>Bonificación</th>
                <th>Total</th>
                <th>Fecha</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{detalleFiniquito.nombre}</td>
                <td>{detalleFiniquito.puesto}</td>
                <td>{detalleFiniquito.departamento}</td>
                <td>Q. {detalleFiniquito.sueldoPendiente.toFixed(2)}</td>
                <td>Q. {detalleFiniquito.aguinaldo.toFixed(2)}</td>
                <td>Q. {detalleFiniquito.bono14.toFixed(2)}</td>
                <td>Q. {detalleFiniquito.vacaciones.toFixed(2)}</td>
                <td>Q. {detalleFiniquito.indemnizacion.toFixed(2)}</td>
                <td>Q. {detalleFiniquito.bonificacion.toFixed(2)}</td>
                <td><strong>Q. {detalleFiniquito.total.toFixed(2)}</strong></td>
                <td>{detalleFiniquito.fecha}</td>
              </tr>
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default FiniquitoPanel;
