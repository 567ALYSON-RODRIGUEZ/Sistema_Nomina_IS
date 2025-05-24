import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Anticipos.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faTrash } from '@fortawesome/free-solid-svg-icons';

function AnticipoCrud() {
  const [anticipos, setAnticipos] = useState([]);
  const [nuevoAnticipo, setNuevoAnticipo] = useState({
    fechaAnticipo: '',
    monto: '',
    descripcion: '',
    idEmpleado: '',
    estado: 'Pendiente', // Estado inicial para nuevo anticipo
  });
  const [editandoAnticipo, setEditandoAnticipo] = useState(null);
  const [mostrarModal, setMostrarModal] = useState(false);
  const [idBuscar, setIdBuscar] = useState('');

  useEffect(() => {
    cargarAnticipos();
  }, []);

const cargarAnticipos = () => {
  const token = localStorage.getItem('token');
  axios.get('http://localhost:8095/anticipo/obtenerTodos', {
    headers: { Authorization: `Bearer ${token}` }
  })
  .then(res => {
    // Filtrar anticipos que NO estén en estado "Eliminado"
    const anticiposFiltrados = res.data.filter(a => a.estado !== 'Eliminado');
    setAnticipos(anticiposFiltrados);
  })
  .catch(err => console.error("Error al cargar anticipos:", err));
};


  const crearAnticipo = () => {
    axios.post('http://localhost:8095/anticipo/crear', nuevoAnticipo, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Anticipo creado exitosamente.");
        setMostrarModal(false);
        cargarAnticipos();
        setNuevoAnticipo({ fechaAnticipo: '', monto: '', descripcion: '', idEmpleado: '', estado: 'Pendiente' });
      })
      .catch(err => {
        console.error("Error al crear anticipo:", err);
        alert("Error al crear anticipo.");
      });
  };

  const actualizarAnticipo = () => {
    axios.put(`http://localhost:8095/anticipo/reemplazar/${editandoAnticipo.idAnticipo}`, editandoAnticipo, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Anticipo actualizado.");
        setMostrarModal(false);
        setEditandoAnticipo(null);
        cargarAnticipos();
      })
      .catch(err => {
        console.error("Error al actualizar:", err);
        alert("No se pudo actualizar el anticipo.");
      });
  };

  const eliminarAnticipo = (id) => {
    if (!window.confirm("¿Estás seguro de eliminar este anticipo?")) return;

    axios.delete(`http://localhost:8095/anticipo/eliminar/${id}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Anticipo eliminado.");
        setAnticipos(anticipos.filter(a => a.idAnticipo !== id));
      })
      .catch(err => {
        console.error("Error al eliminar:", err);
        alert("No se pudo eliminar el anticipo.");
      });
  };

  const buscarPorId = () => {
    axios.get(`http://localhost:8095/anticipo/obtenerPorId/${idBuscar}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setAnticipos([res.data]))
      .catch(err => {
        console.error("Anticipo no encontrado:", err);
        alert("Anticipo no encontrado.");
      });
  };

  const abrirModal = (anticipo = null) => {
    if (anticipo) {
      setEditandoAnticipo(anticipo);
    } else {
      setNuevoAnticipo({ fechaAnticipo: '', monto: '', descripcion: '', idEmpleado: '', estado: 'Pendiente' });
      setEditandoAnticipo(null);
    }
    setMostrarModal(true);
  };

  const handleChange = (e, esEdicion = false) => {
    const { name, value } = e.target;
    if (esEdicion) {
      setEditandoAnticipo(prev => ({ ...prev, [name]: value }));
    } else {
      setNuevoAnticipo(prev => ({ ...prev, [name]: value }));
    }
  };

  return (
    <div className="anticipo-panel">
      <div className="anticipo-barra">
        <h2>Gestión de Anticipos</h2>
        <div className="anticipo-acciones">
          <button className="anticipo-btn-nuevo" onClick={() => abrirModal()}>+ Nuevo</button>
          <input type="text" value={idBuscar} onChange={e => setIdBuscar(e.target.value)} placeholder="Buscar por ID" />
          <button className="anticipo-btn-buscar" onClick={buscarPorId}>Buscar</button>
          <button className="anticipo-btn-buscar" onClick={cargarAnticipos}>Regresar</button>
        </div>
      </div>

      {mostrarModal && (
        <div className="anticipo-modal">
          <div className="anticipo-modal-contenido">
            <h3>{editandoAnticipo ? 'Editar Anticipo' : 'Nuevo Anticipo'}</h3>
            <input
              type="date"
              name="fechaAnticipo"
              value={editandoAnticipo ? editandoAnticipo.fechaAnticipo : nuevoAnticipo.fechaAnticipo}
              onChange={e => handleChange(e, !!editandoAnticipo)}
            />
            <input
              type="number"
              name="monto"
              placeholder="Monto"
              value={editandoAnticipo ? editandoAnticipo.monto : nuevoAnticipo.monto}
              onChange={e => handleChange(e, !!editandoAnticipo)}
            />
            <input
              type="text"
              name="descripcion"
              placeholder="Descripción"
              value={editandoAnticipo ? editandoAnticipo.descripcion : nuevoAnticipo.descripcion}
              onChange={e => handleChange(e, !!editandoAnticipo)}
            />
            <input
              type="text"
              name="idEmpleado"
              placeholder="ID Empleado"
              value={editandoAnticipo ? editandoAnticipo.idEmpleado : nuevoAnticipo.idEmpleado}
              onChange={e => handleChange(e, !!editandoAnticipo)}
            />
            {}
            <label htmlFor="estado">Estado:</label>
            <select
              name="estado"
              id="estado"
              value={editandoAnticipo ? editandoAnticipo.estado : nuevoAnticipo.estado}
              onChange={e => handleChange(e, !!editandoAnticipo)}
            >
              <option value="Activo">Activo</option>
              <option value="Descontado">Descontado</option>
              <option value="Cancelado">Cancelado</option>
            </select>

            <div className="anticipo-modal-botones">
              <button onClick={editandoAnticipo ? actualizarAnticipo : crearAnticipo}>Guardar</button>
              <button onClick={() => { setMostrarModal(false); setEditandoAnticipo(null); }}>Cancelar</button>
            </div>
          </div>
        </div>
      )}

      <table className="anticipo-tabla">
        <thead>
          <tr>
            <th>Acción</th>
            <th>ID Anticipo</th>
            <th>ID Empleado</th>
            <th>Monto</th>
            <th>Fecha</th>
            <th>Estado</th>
            <th>Descripción</th>
          </tr>
        </thead>
        <tbody>
          {anticipos.map(a => (
            <tr key={a.idAnticipo}>
              <td>
                <button className="anticipo-btn-editar" onClick={() => abrirModal(a)}><FontAwesomeIcon icon={faPen} /></button>
                <button className="anticipo-btn-eliminar" onClick={() => eliminarAnticipo(a.idAnticipo)}><FontAwesomeIcon icon={faTrash} /></button>
              </td>
              <td>{a.idAnticipo}</td>
              <td>{a.idEmpleado}</td>
              <td>{a.monto}</td>
              <td>{a.fechaAnticipo || a.fecha}</td>
              <td>{a.estado}</td>
              <td>{a.descripcion}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AnticipoCrud;
