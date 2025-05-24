import React, { useState } from "react";
import axios from "axios";
import './Quincenal.css';
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";

const Quincenal = () => {
  const [idPeriodo, setIdPeriodo] = useState("");
  const [mensaje, setMensaje] = useState("");
  const [detalle, setDetalle] = useState([]);
  const [resumen, setResumen] = useState([]);
  const [empleadoSeleccionado, setEmpleadoSeleccionado] = useState(null);

  const generarNomina = async () => {
    try {
      const response = await axios.post(`http://localhost:8095/detallenominaQ/generar/${idPeriodo}`);
      setMensaje(response.data);
    } catch (error) {
      setMensaje(error.response?.data || "Error al generar la nómina.");
    }
  };

  const finalizarPeriodo = async () => {
    try {
      const response = await axios.post(`http://localhost:8095/detallenominaQ/finalizar/${idPeriodo}`);
      setMensaje(response.data);
    } catch {
      setMensaje("Error al finalizar el periodo.");
    }
  };

  const obtenerResumen = async () => {
    try {
      const response = await axios.get(`http://localhost:8095/detallenominaQ/resumen/${idPeriodo}`);
      setResumen(response.data);
      setMensaje("Resumen cargado.");
    } catch {
      setMensaje("Error al obtener resumen.");
    }
  };

  const obtenerDetalle = async (idEmp) => {
    try {
      const response = await axios.get(`http://localhost:8095/detallenominaQ/detalle/${idPeriodo}`);
      const detalleEmpleado = response.data.find((item) => item.idEmpleado === idEmp);
      setDetalle([detalleEmpleado]);
      setEmpleadoSeleccionado(idEmp);
      setMensaje("Detalle cargado.");
    } catch {
      setMensaje("Error al obtener detalle.");
    }
  };

  const generarPDF = () => {
    if (!detalle.length) return;

    const empleado = detalle[0];
    const doc = new jsPDF();

    doc.setFontSize(16);
    doc.text("Detalle de Nómina Quincenal", 14, 20);

    doc.setFontSize(12);
    doc.text(`ID Empleado: ${empleado.idEmpleado}`, 14, 30);
    doc.text(`Nombre: ${empleado.nombreEmpleado}`, 14, 38);
    doc.text(`Cargo: ${empleado.cargoEmpleado}`, 14, 46);
    doc.text(`ID Nómina: ${empleado.idNomina || 'N/A'}`, 14, 54);
    doc.text(`ID Periodo: ${idPeriodo}`, 14, 62);

    autoTable(doc, {
      startY: 70,
      head: [[
        "Días Laborados", "Sueldo Asignado", "Ordinario", "Horas Extra", "Extra",
        "Bonificación", "Vacaciones", "IGSS", "ISR", "Anticipos", "Ingresos", "Descuentos", "Neto", "Aguinaldo", "Bono 14"
      ]],
      body: [[
        empleado.diasLaborados,
        empleado.sueldoAsignado,
        empleado.sueldoOrdinario,
        empleado.horaExtra,
        empleado.sueldoExtraordinario,
        empleado.bonificacionIncentivo,
        empleado.vacaciones,
        empleado.igss,
        empleado.isr,
        empleado.anticipos,
        empleado.totalIngresos,
        empleado.totalDescuentos,
        empleado.salarioNeto,
        empleado.aguinaldo || '0.00',
        empleado.bono14 || '0.00',
      ]],
    });

    doc.save(`Detalle_Empleado_${empleado.idEmpleado}.pdf`);
  };

  const empleado = detalle.length > 0 ? detalle[0] : null;

  return (
    <div className="quincenal-panel">
      <div className="quincenal-barra">
        <h2>Nómina Quincenal</h2>
        <div className="quincenal-acciones">
          <input
            type="text"
            placeholder="ID del Periodo"
            value={idPeriodo}
            onChange={(e) => setIdPeriodo(e.target.value)}
          />
          <button className="quincenal-btn verde" onClick={generarNomina}>Generar</button>
          <button className="quincenal-btn verde" onClick={finalizarPeriodo}>Finalizar</button>
          <button className="quincenal-btn gris" onClick={obtenerResumen}>Resumen</button>
        </div>
      </div>

      {mensaje && <p><strong>{mensaje}</strong></p>}

      {resumen.length > 0 && (
        <>
          <h3>Resumen</h3>
          <table className="quincenal-tabla">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Puesto</th>
                <th>Total Ingresos</th>
                <th>Total Descuentos</th>
                <th>Salario Neto</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {resumen.map((item) => (
                <tr key={item.idEmpleado}>
                  <td>{item.idEmpleado}</td>
                  <td>{item.nombreCompleto}</td>
                  <td>{item.puesto}</td>
                  <td>{item.totalIngresos}</td>
                  <td>{item.totalDescuentos}</td>
                  <td>{item.salarioNeto}</td>
                  <td>
                    <button onClick={() => obtenerDetalle(item.idEmpleado)}>Ver Detalle</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </>
      )}

      {empleado && (
        <>
          <h3>Detalle del Empleado #{empleadoSeleccionado}</h3>
          <button className="quincenal-btn cerrar" onClick={() => setEmpleadoSeleccionado(null)}>Cerrar Detalle</button>
          <button className="quincenal-btn pdf" onClick={generarPDF}>Descargar PDF</button>
          <table className="quincenal-tabla">
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Cargo</th>
                <th>Días Laborados</th>
                <th>Sueldo Asignado</th>
                <th>Ordinario</th>
                <th>Horas Extra</th>
                <th>Extra</th>
                <th>Bonificación</th>
                <th>Vacaciones</th>
                <th>IGSS</th>
                <th>ISR</th>
                <th>Anticipos</th>
                <th>Ingresos</th>
                <th>Descuentos</th>
                <th>Neto</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{empleado.nombreEmpleado}</td>
                <td>{empleado.cargoEmpleado}</td>
                <td>{empleado.diasLaborados}</td>
                <td>{empleado.sueldoAsignado}</td>
                <td>{empleado.sueldoOrdinario}</td>
                <td>{empleado.horaExtra}</td>
                <td>{empleado.sueldoExtraordinario}</td>
                <td>{empleado.bonificacionIncentivo}</td>
                <td>{empleado.vacaciones}</td>
                <td>{empleado.igss}</td>
                <td>{empleado.isr}</td>
                <td>{empleado.anticipos}</td>
                <td>{empleado.totalIngresos}</td>
                <td>{empleado.totalDescuentos}</td>
                <td>{empleado.salarioNeto}</td>
              </tr>
            </tbody>
          </table>
        </>
      )}
    </div>
  );
};

export default Quincenal;
