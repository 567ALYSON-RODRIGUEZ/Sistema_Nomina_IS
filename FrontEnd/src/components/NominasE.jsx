import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './NominasE.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faTrash } from '@fortawesome/free-solid-svg-icons';

function NominasE() {
  const [nominas, setNominas] = useState([]);
  const [mostrarModal, setMostrarModal] = useState(false);
  const [nuevaNomina, setNuevaNomina] = useState({
    tipo: 'Semanal',
    periodoInicio: '',
    periodoFin: '',
    fechaGeneracion: '',
    estado: 'Generada',
    idPeriodo: ''
  });
  const [editandoNomina, setEditandoNomina] = useState(null);
  const [idBuscar, setIdBuscar] = useState('');

  useEffect(() => {
    cargarNominas();
  }, []);

  const cargarNominas = () => {
    axios.get('http://localhost:8095/nomina/obtenerTodos', {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setNominas(res.data))
      .catch(err => console.error("Error al cargar nóminas:", err));
  };

  const buscarPorId = () => {
    if (!idBuscar) {
      alert("Ingrese un ID para buscar.");
      return;
    }
    axios.get(`http://localhost:8095/nomina/obtenerPorId/${idBuscar}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setNominas([res.data]))
      .catch(err => {
        console.error("Nómina no encontrada:", err);
        alert("Nómina no encontrada.");
      });
  };

  const crearNomina = () => {
    axios.post('http://localhost:8095/nomina/crear', nuevaNomina, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Nómina creada.");
        cerrarModal();
        cargarNominas();
      })
      .catch(() => alert("Error al crear nómina."));
  };

  const actualizarNomina = () => {
    axios.put(`http://localhost:8095/nomina/reemplazar/${editandoNomina.idNomina}`, editandoNomina, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Nómina actualizada.");
        cerrarModal();
        cargarNominas();
      })
      .catch(() => alert("Error al actualizar nómina."));
  };

  const eliminarNomina = (id) => {
    if (window.confirm("¿Seguro que deseas eliminar esta nómina?")) {
      axios.delete(`http://localhost:8095/nomina/eliminar/${id}`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      })
        .then(() => {
          alert("Nómina eliminada.");
          cargarNominas();
        })
        .catch(() => alert("Error al eliminar nómina."));
    }
  };

  const abrirModalEditar = (n) => {
    setEditandoNomina(n);
    setMostrarModal(true);
  };

  const cerrarModal = () => {
    setMostrarModal(false);
    setEditandoNomina(null);
    setNuevaNomina({
      tipo: 'Semanal',
      periodoInicio: '',
      periodoFin: '',
      fechaGeneracion: '',
      estado: 'Generada',
      idPeriodo: ''
    });
  };

  const manejarCambio = (campo, valor) => {
    if (editandoNomina) {
      setEditandoNomina({ ...editandoNomina, [campo]: valor });
    } else {
      setNuevaNomina({ ...nuevaNomina, [campo]: valor });
    }
  };

  return (
    <div className="contenedor-panel">
      {/* Barra superior igual a IgssCrud */}
      <div className="igss-barra">
        <h2>Gestión de Nóminas</h2>
        <div className="igss-acciones">
          <input
            type="text"
            value={idBuscar}
            onChange={e => setIdBuscar(e.target.value)}
            placeholder="Buscar por ID"
          />
          <button className="igss-btn-buscar" onClick={buscarPorId}>Buscar</button>
          <button className="igss-btn-buscar" onClick={cargarNominas}>Regresar</button>
        </div>
      </div>

      {mostrarModal && (
        <div className="modal">
          <div className="modal-contenido">
            <h3>{editandoNomina ? 'Editar Nómina' : 'Crear Nómina'}</h3>

            <select
              value={editandoNomina ? editandoNomina.tipo : nuevaNomina.tipo}
              onChange={e => manejarCambio('tipo', e.target.value)}
            >
              <option value="Semanal">Semanal</option>
              <option value="Quincenal">Quincenal</option>
              <option value="Mensual">Mensual</option>
            </select>

            <input
              type="date"
              value={editandoNomina ? editandoNomina.periodoInicio : nuevaNomina.periodoInicio}
              onChange={e => manejarCambio('periodoInicio', e.target.value)}
            />

            <input
              type="date"
              value={editandoNomina ? editandoNomina.periodoFin : nuevaNomina.periodoFin}
              onChange={e => manejarCambio('periodoFin', e.target.value)}
            />

            <input
              type="date"
              value={editandoNomina ? editandoNomina.fechaGeneracion : nuevaNomina.fechaGeneracion}
              onChange={e => manejarCambio('fechaGeneracion', e.target.value)}
            />

            <select
              value={editandoNomina ? editandoNomina.estado : nuevaNomina.estado}
              onChange={e => manejarCambio('estado', e.target.value)}
            >
              <option value="Generada">Generada</option>
              <option value="Pagada">Pagada</option>
              <option value="Anulada">Anulada</option>
            </select>

            <input
              type="number"
              placeholder="ID Periodo"
              value={editandoNomina ? editandoNomina.idPeriodo || '' : nuevaNomina.idPeriodo}
              onChange={e => manejarCambio('idPeriodo', e.target.value)}
            />

            <div className="modal-botones">
              <button onClick={editandoNomina ? actualizarNomina : crearNomina}>Guardar</button>
              <button onClick={cerrarModal}>Cancelar</button>
            </div>
          </div>
        </div>
      )}

      <table className="tabla-nominas">
        <thead>
          <tr>
            <th>Acción</th>
            <th>ID</th>
            <th>Tipo</th>
            <th>Periodo Inicio</th>
            <th>Periodo Fin</th>
            <th>Fecha Generación</th>
            <th>Estado</th>
            <th>ID Periodo</th>
          </tr>
        </thead>
        <tbody>
          {nominas.map(n => (
            <tr key={n.idNomina}>
              <td>
                <button className="btn-editar" onClick={() => abrirModalEditar(n)}><FontAwesomeIcon icon={faPen} /></button>
                <button className="btn-eliminar" onClick={() => eliminarNomina(n.idNomina)}><FontAwesomeIcon icon={faTrash} /></button>
              </td>
              <td>{n.idNomina}</td>
              <td>{n.tipo}</td>
              <td>{n.periodoInicio}</td>
              <td>{n.periodoFin}</td>
              <td>{n.fechaGeneracion}</td>
              <td>{n.estado}</td>
              <td>{n.idPeriodo}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default NominasE;
