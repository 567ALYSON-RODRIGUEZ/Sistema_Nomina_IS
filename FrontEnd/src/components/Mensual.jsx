import React, { useState } from "react";
import axios from "axios";
import './Mensual.css';
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";

const Mensual = () => {
  const [idPeriodo, setIdPeriodo] = useState("");
  const [mensaje, setMensaje] = useState("");
  const [detalle, setDetalle] = useState([]);
  const [resumen, setResumen] = useState([]);
  const [empleadoSeleccionado, setEmpleadoSeleccionado] = useState(null);

  const generarNomina = async () => {
    try {
      const response = await axios.post(`http://localhost:8095/detallenominaM/generar/${idPeriodo}`);
      setMensaje(response.data);
    } catch (error) {
      if (error.response && error.response.data) {
        setMensaje(error.response.data);
      } else {
        setMensaje("Error al generar la nómina mensual.");
      }
    }
  };

  const finalizarPeriodo = async () => {
    try {
      const response = await axios.post(`http://localhost:8095/detallenominaM/finalizar/${idPeriodo}`);
      setMensaje(response.data);
    } catch (error) {
      setMensaje("Error al finalizar el periodo mensual.");
    }
  };

  const obtenerResumen = async () => {
    try {
      const response = await axios.get(`http://localhost:8095/detallenominaM/resumen/${idPeriodo}`);
      setResumen(response.data);
      setMensaje("Resumen mensual cargado.");
    } catch (error) {
      setMensaje("Error al obtener resumen mensual.");
    }
  };

  const obtenerDetalle = async (idEmp) => {
    try {
      const response = await axios.get(`http://localhost:8095/detallenominaM/detalle/${idPeriodo}`);
      const detalleEmpleado = response.data.find((item) => item.idEmpleado === idEmp);
      setDetalle([detalleEmpleado]);
      setEmpleadoSeleccionado(idEmp);
      setMensaje("Detalle mensual cargado.");
    } catch (error) {
      setMensaje("Error al obtener detalle mensual.");
    }
  };

  const generarPDF = () => {
    if (!detalle.length) return;

    const empleado = detalle[0];
    const doc = new jsPDF();

    doc.setFontSize(16);
    doc.text("Detalle de Nómina Mensual", 14, 20);

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

    doc.save(`Detalle_Mensual_Empleado_${empleado.idEmpleado}.pdf`);
  };

  const empleado = detalle.length > 0 ? detalle[0] : null;

  return (
    <div className="mensual-panel">
      <div className="mensual-barra">
        <h2>Nómina Mensual</h2>
        <div className="mensual-acciones">
          <input
            type="text"
            placeholder="ID del Periodo Mensual"
            value={idPeriodo}
            onChange={(e) => setIdPeriodo(e.target.value)}
          />
          <button className="mensual-btn verde" onClick={generarNomina}>Generar</button>
          <button className="mensual-btn verde" onClick={finalizarPeriodo}>Finalizar</button>
          <button className="mensual-btn gris" onClick={obtenerResumen}>Resumen</button>
        </div>
      </div>

      {mensaje && <p><strong>{mensaje}</strong></p>}

      {resumen.length > 0 && (
        <>
          <h3>Resumen Mensual</h3>
          <table className="mensual-tabla">
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
          <h3>Detalle del Empleado #{empleadoSeleccionado} - Nómina Mensual</h3>
          <button className="mensual-btn cerrar" onClick={() => setEmpleadoSeleccionado(null)}>Cerrar Detalle</button>
          <button className="mensual-btn pdf" onClick={generarPDF}>Descargar PDF</button>
          <table className="mensual-tabla">
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

export default Mensual;
