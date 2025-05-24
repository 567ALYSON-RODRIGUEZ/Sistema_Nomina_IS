import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './PlanillaBono14.css';

function Bono14Panel() {
  const [planillas, setPlanillas] = useState([]);
  const [anio, setAnio] = useState(new Date().getFullYear());

  useEffect(() => {
    cargarPlanillas();
  }, []);

  const cargarPlanillas = () => {
    axios.get(`http://localhost:8095/bono14/obtenerPorAnio/${anio}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setPlanillas(res.data))
      .catch(err => console.error("Error al cargar planillas de bono 14:", err));
  };

  const generarPlanilla = () => {
    axios.post(`http://localhost:8095/bono14/generar/${anio}`, {}, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => {
        alert(res.data);
        cargarPlanillas();
      })
      .catch(err => {
        alert("Error al generar la planilla de Bono 14.");
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

    axios.post('http://localhost:8095/bono14/pagarLote', idsPendientes, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => {
        alert(res.data);
        cargarPlanillas();
      })
      .catch(err => {
        alert("Error al realizar el pago del Bono 14.");
        console.error(err);
      });
  };

  return (
    <div className="aguinaldo-container">
      <div className="filtros">
        <h2>Planilla de Bono 14</h2>
        <input
          type="number"
          placeholder="Año"
          value={anio}
          onChange={(e) => setAnio(e.target.value)}
        />
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

export default Bono14Panel;
