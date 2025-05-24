import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Isr.css';

function Isr() {
  const [parametros, setParametros] = useState([]);
  const [nuevoParametro, setNuevoParametro] = useState({
    rangoMin: '', rangoMax: '', cuotaFija: '', porcentaje: '', vigenteDesde: '', vigenteHasta: '', tipoPeriodo: 'mensual'
  });
  const [editandoParametro, setEditandoParametro] = useState(null);
  const [mostrarModal, setMostrarModal] = useState(false);
  const [idBuscar, setIdBuscar] = useState('');

  useEffect(() => {
    cargarTodos();
  }, []);

  const cargarTodos = () => {
    axios.get('http://localhost:8095/parametroIsr/obtenerTodos', {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setParametros(res.data))
      .catch(err => console.error("Error al cargar parámetros:", err));
  };

  const crearParametro = () => {
    axios.post('http://localhost:8095/parametroIsr/crear', nuevoParametro, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Parámetro creado correctamente.");
        setMostrarModal(false);
        setNuevoParametro({ rangoMin: '', rangoMax: '', cuotaFija: '', porcentaje: '', vigenteDesde: '', vigenteHasta: '', tipoPeriodo: 'mensual' });
        cargarTodos();
      })
      .catch(err => {
        console.error("Error al crear parámetro:", err);
        alert("No se pudo crear el parámetro.");
      });
  };

  const actualizarParametro = () => {
    axios.put(`http://localhost:8095/parametroIsr/actualizar/${editandoParametro.id}`, editandoParametro, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Parámetro actualizado correctamente.");
        setMostrarModal(false);
        setEditandoParametro(null);
        cargarTodos();
      })
      .catch(err => {
        console.error("Error al actualizar parámetro:", err);
        alert("No se pudo actualizar el parámetro.");
      });
  };

  const eliminarParametro = (id) => {
    if (!window.confirm("¿Estás seguro de eliminar este parámetro?")) return;
    axios.patch(`http://localhost:8095/parametroIsr/eliminar/${id}`, null, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Parámetro eliminado.");
        cargarTodos();
      })
      .catch(err => {
        console.error("Error al eliminar parámetro:", err);
        alert("No se pudo eliminar.");
      });
  };

  const buscarPorId = () => {
    axios.get(`http://localhost:8095/parametroIsr/obtenerPorId/${idBuscar}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setParametros([res.data]))
      .catch(err => {
        console.error("Error al buscar:", err);
        alert("Parámetro no encontrado.");
      });
  };

  const abrirModalEditar = (param) => {
    setEditandoParametro(param);
    setMostrarModal(true);
  };

  return (
    <div className="contenedor-panel">
      <div className="barra-superiorr">
        <h2>Gestión Parámetros ISR</h2>
        <div className="acciones-barra">
          <button className="btn-nuevo" onClick={() => { setEditandoParametro(null); setMostrarModal(true); }}>+ Nuevo</button>
          <input type="text" placeholder="Buscar por ID" value={idBuscar} onChange={e => setIdBuscar(e.target.value)} />
          <button className="btn-buscar" onClick={buscarPorId}>Buscar</button>
          <button className="btn-buscar" onClick={cargarTodos}>Regresar</button>
        </div>
      </div>

      {mostrarModal && (
        <div className="modal">
          <div className="modal-contenido">
            <h3>{editandoParametro ? 'Editar' : 'Crear'} Parámetro</h3>
            {['rangoMin', 'rangoMax', 'cuotaFija', 'porcentaje', 'vigenteDesde', 'vigenteHasta', 'tipoPeriodo'].map(campo => (
              <input
                key={campo}
                type={campo.includes('vigente') ? 'date' : campo === 'tipoPeriodo' ? 'text' : 'number'}
                placeholder={campo}
                value={editandoParametro ? editandoParametro[campo] : nuevoParametro[campo]}
                onChange={e => editandoParametro ? setEditandoParametro({ ...editandoParametro, [campo]: e.target.value }) : setNuevoParametro({ ...nuevoParametro, [campo]: e.target.value })}
              />
            ))}
            <div className="modal-botones">
              <button onClick={editandoParametro ? actualizarParametro : crearParametro}>Guardar</button>
              <button onClick={() => { setMostrarModal(false); setEditandoParametro(null); }}>Cancelar</button>
            </div>
          </div>
        </div>
      )}

      <table className="tabla-isr">
        <thead>
          <tr>
            <th>ID</th><th>Rango Min</th><th>Rango Max</th><th>Cuota Fija</th><th>%</th><th>Desde</th><th>Hasta</th><th>Periodo</th><th>Acción</th>
          </tr>
        </thead>
        <tbody>
          {parametros.map(param => (
            <tr key={param.id}>
              <td>{param.id}</td>
              <td>{param.rangoMin}</td>
              <td>{param.rangoMax}</td>
              <td>{param.cuotaFija}</td>
              <td>{param.porcentaje}</td>
              <td>{param.vigenteDesde}</td>
              <td>{param.vigenteHasta || '-'}</td>
              <td>{param.tipoPeriodo}</td>
              <td>
                <button className="btn-editar" onClick={() => abrirModalEditar(param)}>✏️</button>
                <button className="btn-eliminar" onClick={() => eliminarParametro(param.id)}>🗑️</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Isr;
