import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './PlanillaAguinaldo.css';

function AguinaldoPanel() {
  const [planillas, setPlanillas] = useState([]);
  const [anio, setAnio] = useState(new Date().getFullYear());
  const [porcentaje, setPorcentaje] = useState(50);

  useEffect(() => {
    cargarPlanillas();
  }, []);

  const cargarPlanillas = () => {
    axios.get(`http://localhost:8095/aguinaldo/obtenerPorAnio/${anio}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setPlanillas(res.data))
      .catch(err => console.error("Error al cargar planillas:", err));
  };

  const generarPlanilla = () => {
    axios.post(`http://localhost:8095/aguinaldo/generar/${anio}/${porcentaje}`, {}, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => {
        alert(res.data);
        cargarPlanillas();
      })
      .catch(err => {
        alert("Error al generar la planilla.");
        console.error(err);
      });
  };

  const pagarTodasPlanillas = () => {
    const idsPendientes = planillas
      .filter(p => p.estadoPago === 'Pendiente')
      .map(p => p.id);

    if (idsPendientes.length === 0) {
      alert("No hay planillas pendientes para pagar.");
      return;
    }

    axios.post('http://localhost:8095/aguinaldo/pagarLote', idsPendientes, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => {
        alert(res.data);
        cargarPlanillas();
      })
      .catch(err => {
        alert("Error al realizar el pago general.");
        console.error(err);
      });
  };

  return (
    <div className="aguinaldo-container">
      <div className="filtros">
        <h2>Planilla de Aguinaldo</h2>
        <input
          type="number"
          placeholder="Año"
          value={anio}
          onChange={(e) => setAnio(e.target.value)}
        />
        <select value={porcentaje} onChange={(e) => setPorcentaje(Number(e.target.value))}>
          <option value={50}>50% (Diciembre)</option>
          <option value={100}>100% (Completo)</option>
        </select>
        <button onClick={generarPlanilla}>Generar</button>
        <button onClick={cargarPlanillas}>Consultar</button>
      </div>

      <button className="btn-pago" onClick={pagarTodasPlanillas}>Realizar Pago</button>

      <table className="tabla-planilla">
        <thead>
          <tr>
            <th>ID</th>
            <th>ID Empleado</th>
            <th>Año</th>
            <th>Fecha Pago</th>
            <th>% Pago</th>
            <th>Monto Pagado</th>
            <th>Tipo Pago</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {planillas.map(planilla => (
            <tr key={planilla.id}>
              <td>{planilla.id}</td>
              <td>{planilla.idEmpleado}</td>
              <td>{planilla.anio}</td>
              <td>{planilla.fechaPago}</td>
              <td>{planilla.porcentajePago}%</td>
              <td>Q. {planilla.montoPagado.toFixed(2)}</td>
              <td>{planilla.tipoPago}</td>
              <td>{planilla.estadoPago}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AguinaldoPanel;
