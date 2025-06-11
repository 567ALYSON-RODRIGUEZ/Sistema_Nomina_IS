import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './PeriodoNomina.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faTrash } from '@fortawesome/free-solid-svg-icons';

function PeriodosPanel() {
  const [periodos, setPeriodos] = useState([]);
  const [nuevoPeriodo, setNuevoPeriodo] = useState({
    tipo_periodo: '',
    descripcion: '',
    fecha_inicio: '',
    fecha_fin: '',
    dias_a_pagar: '',
    numero_pago: '',
    codigo_pago: '',
    estado: 'Abierto'
  });
  const [mostrarModal, setMostrarModal] = useState(false);
  const [editandoPeriodo, setEditandoPeriodo] = useState(null);
  const [idBuscar, setIdBuscar] = useState('');

  useEffect(() => {
    cargarTodos();
  }, []);

  const cargarTodos = () => {
    const token = localStorage.getItem('token');

    axios.get('http://localhost:8095/periodo/obtenerTodos', {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => setPeriodos(res.data))
      .catch(err => console.error("Error de axios:", err));
  };

    const eliminarPeriodo = (id) => {
    const confirmacion = window.confirm("¿Estás seguro de que deseas (cerrar) este periodo?");
    if (!confirmacion) return;

    axios.patch(`http://localhost:8095/periodo/cerrar/${id}`, null, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
    .then(() => {
      alert("Periodo cerrado correctamente.");
      setPeriodos(periodos.filter(p => p.id_periodo !== id));
    })
    .catch(err => {
      console.error("Error al cerrar:", err);
      alert("Error al cerrar el periodo.");
    });
  };

  const crearPeriodo = () => {
    axios.post('http://localhost:8095/periodo/crear', nuevoPeriodo, {
      headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
    }
  })
      .then(() => {
        alert("Periodo creado correctamente.");
        setMostrarModal(false);
        setNuevoPeriodo({
          tipo_periodo: '',
          descripcion: '',
          fecha_inicio: '',
          fecha_fin: '',
          dias_a_pagar: '',
          numero_pago: '',
          codigo_pago: '',
          estado: 'Abierto'
        });
        cargarTodos();
      })
      .catch(err => {
        console.error("Error al crear:", err);
        alert("No se pudo crear el periodo.");
      });
  };

  const actualizarPeriodo = () => {
    axios.put(`http://localhost:8095/periodo/actualizar/${editandoPeriodo.id_periodo}`, editandoPeriodo,{
      headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
    }
  })
      .then(() => {
        alert("Periodo actualizado correctamente.");
        setMostrarModal(false);
        setEditandoPeriodo(null);
        cargarTodos();
      })
      .catch(err => {
        console.error("Error al actualizar:", err);
        alert("No se pudo actualizar el periodo.");
      });
  };

  const buscarPeriodoPorId = () => {
    axios.get(`http://localhost:8095/periodo/obtenerPorId/${idBuscar}`,{
      headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
    }
  })
      .then(res => setPeriodos([res.data]))
      .catch(err => {
        console.error("Error al buscar:", err);
        alert("Periodo no encontrado.");
      });
  };

  const abrirModalEditar = (periodo) => {
    setEditandoPeriodo(periodo);
    setNuevoPeriodo({
      tipo_periodo: periodo.tipo_periodo,
      descripcion: periodo.descripcion,
      fecha_inicio: periodo.fecha_inicio.split('T')[0],
      fecha_fin: periodo.fecha_fin.split('T')[0],
      dias_a_pagar: periodo.dias_a_pagar,
      numero_pago: periodo.numero_pago,
      codigo_pago: periodo.codigo_pago,
      estado: periodo.estado
    });
    setMostrarModal(true);
  };

  return (
    <div className="contenedor-panel">
      <div className="barra-superiorp">
        <h2>Gestión de Periodos</h2>
        <div className="acciones-barra">
          <button className="btn-nuevo" onClick={() => { setEditandoPeriodo(null); setMostrarModal(true); }}>
            + Nuevo
          </button>
          <input
            type="text"
            value={idBuscar}
            onChange={e => setIdBuscar(e.target.value)}
            placeholder="Buscar periodo por ID"
          />
          <button className="btn-buscar" onClick={buscarPeriodoPorId}>Buscar</button>
          <button className="btn-buscar" onClick={cargarTodos}>Regresar</button>
        </div>
      </div>

             {mostrarModal && (
        <div className="modal">
          <div className="modal-contenido">
            <h3>{editandoPeriodo && editandoPeriodo.id_periodo ? 'Editar Periodo' : 'Crear Periodo'}</h3>
            <input
              type="text"
              placeholder="Tipo de periodo"
              value={editandoPeriodo?.tipo_periodo || ''}
              onChange={e => setEditandoPeriodo({ ...editandoPeriodo, tipo_periodo: e.target.value })}
            />
            <input
              type="text"
              placeholder="Descripción"
              value={editandoPeriodo?.descripcion || ''}
              onChange={e => setEditandoPeriodo({ ...editandoPeriodo, descripcion: e.target.value })}
            />
            <input
              type="date"
              value={editandoPeriodo?.fecha_inicio || ''}
              onChange={e => setEditandoPeriodo({ ...editandoPeriodo, fecha_inicio: e.target.value })}
            />
            <input
              type="date"
              value={editandoPeriodo?.fecha_fin || ''}
              onChange={e => setEditandoPeriodo({ ...editandoPeriodo, fecha_fin: e.target.value })}
            />
            <input
              type="number"
              placeholder="Días a pagar"
              value={editandoPeriodo?.dias_a_pagar || ''}
              onChange={e => setEditandoPeriodo({ ...editandoPeriodo, dias_a_pagar: e.target.value })}
            />
            <input
              type="number"
              placeholder="Número de pago"
              value={editandoPeriodo?.numero_pago || ''}
              onChange={e => setEditandoPeriodo({ ...editandoPeriodo, numero_pago: e.target.value })}
            />
            <input
              type="text"
              placeholder="Código de pago"
              value={editandoPeriodo?.codigo_pago || ''}
              onChange={e => setEditandoPeriodo({ ...editandoPeriodo, codigo_pago: e.target.value })}
            />
            <select
              value={editandoPeriodo?.estado || 'Abierto'}
              onChange={e => setEditandoPeriodo({ ...editandoPeriodo, estado: e.target.value })}
            >
              <option value="Abierto">Abierto</option>
              <option value="Cerrado">Cerrado</option>
            </select>
            <div className="modal-botones">
              <button onClick={editandoPeriodo && editandoPeriodo.id_periodo ? actualizarPeriodo : crearPeriodo}>
                Guardar
              </button>
              <button onClick={() => { setMostrarModal(false); setEditandoPeriodo(null); }}>
                Cancelar
              </button>
            </div>
          </div>
        </div>
      )}

      <table className="tabla-puestos">
        <thead>
          <tr>
            <th>Acción</th>
            <th>ID</th>
            <th>Tipo</th>
            <th>Descripción</th>
            <th>Inicio</th>
            <th>Fin</th>
            <th>Días</th>
            <th>Nº Pago</th>
            <th>Código</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {periodos.map(p => (
            <tr key={p.id_periodo}>
              <td>
                <button className="btn-editar" onClick={() => abrirModalEditar(p)}>
                  <FontAwesomeIcon icon={faPen} />
                </button>
                <button className="btn-eliminar" onClick={() => eliminarPeriodo(p.id_periodo)}>
                  <FontAwesomeIcon icon={faTrash} />
                </button>
              </td>
              <td>{p.id_periodo}</td>
              <td>{p.tipo_periodo}</td>
              <td>{p.descripcion}</td>
              <td>{p.fecha_inicio.split('T')[0]}</td>
              <td>{p.fecha_fin.split('T')[0]}</td>
              <td>{p.dias_a_pagar}</td>
              <td>{p.numero_pago}</td>
              <td>{p.codigo_pago}</td>
              <td>{p.estado}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default PeriodosPanel;
