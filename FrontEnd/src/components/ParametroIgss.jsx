import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ParametroIgss.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen } from '@fortawesome/free-solid-svg-icons';

function IgssCrud() {
  const [parametros, setParametros] = useState([]);
  const [nuevoParametro, setNuevoParametro] = useState({
    porcentaje: '',
    tipo: 'laboral',
    descripcion: '',
    vigenteDesde: '',
    vigenteHasta: '',
    estado: 'Activo'
  });
  const [editandoParametro, setEditandoParametro] = useState(null);
  const [mostrarModal, setMostrarModal] = useState(false);
  const [idBuscar, setIdBuscar] = useState('');

  useEffect(() => {
    cargarParametros();
  }, []);

  const cargarParametros = () => {
    axios.get('http://localhost:8095/igss/obtenerTodos', {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setParametros(res.data))
      .catch(err => console.error("Error al cargar parámetros:", err));
  };

  const crearParametro = () => {
    axios.post('http://localhost:8095/igss/crear', nuevoParametro, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Parámetro IGSS creado.");
        setMostrarModal(false);
        cargarParametros();
        setNuevoParametro({ porcentaje: '', tipo: 'laboral', descripcion: '', vigenteDesde: '', vigenteHasta: '', estado: 'Activo' });
      })
      .catch(err => {
        console.error("Error al crear parámetro:", err);
        alert("Error al crear parámetro.");
      });
  };

  const actualizarParametro = () => {
    axios.put(`http://localhost:8095/igss/reemplazar/${editandoParametro.idParametro}`, editandoParametro, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Parámetro actualizado.");
        setMostrarModal(false);
        setEditandoParametro(null);
        cargarParametros();
      })
      .catch(err => {
        console.error("Error al actualizar parámetro:", err);
        alert("No se pudo actualizar.");
      });
  };

  
  const buscarPorId = () => {
    axios.get(`http://localhost:8095/igss/obtenerPorId/${idBuscar}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setParametros([res.data]))
      .catch(err => {
        console.error("Parámetro no encontrado:", err);
        alert("Parámetro no encontrado.");
      });
  };

  const abrirModal = (parametro = null) => {
    if (parametro) {
      setEditandoParametro(parametro);
    } else {
      setNuevoParametro({ porcentaje: '', tipo: 'laboral', descripcion: '', vigenteDesde: '', vigenteHasta: '', estado: 'Activo' });
      setEditandoParametro(null);
    }
    setMostrarModal(true);
  };

  const handleChange = (e, esEdicion = false) => {
    const { name, value } = e.target;
    if (esEdicion) {
      setEditandoParametro(prev => ({ ...prev, [name]: value }));
    } else {
      setNuevoParametro(prev => ({ ...prev, [name]: value }));
    }
  };

  return (
    <div className="igss-panel">
      <div className="igss-barra">
        <h2>Gestión de Parámetros IGSS</h2>
        <div className="igss-acciones">
          <input type="text" value={idBuscar} onChange={e => setIdBuscar(e.target.value)} placeholder="Buscar por ID" />
          <button className="igss-btn-buscar" onClick={buscarPorId}>Buscar</button>
          <button className="igss-btn-buscar" onClick={cargarParametros}>Regresar</button>
        </div>
      </div>

      {mostrarModal && (
        <div className="igss-modal">
          <div className="igss-modal-contenido">
            <h3>{editandoParametro ? 'Editar Parámetro' : 'Nuevo Parámetro'}</h3>
            <input
              type="number"
              name="porcentaje"
              placeholder="Porcentaje"
              value={editandoParametro ? editandoParametro.porcentaje : nuevoParametro.porcentaje}
              onChange={e => handleChange(e, !!editandoParametro)}
            />
            <select
              name="tipo"
              value={editandoParametro ? editandoParametro.tipo : nuevoParametro.tipo}
              onChange={e => handleChange(e, !!editandoParametro)}
            >
              <option value="laboral">Laboral</option>
            </select>
            <input
              type="text"
              name="descripcion"
              placeholder="Descripción"
              value={editandoParametro ? editandoParametro.descripcion : nuevoParametro.descripcion}
              onChange={e => handleChange(e, !!editandoParametro)}
            />
            <input
              type="date"
              name="vigenteDesde"
              value={editandoParametro ? editandoParametro.vigenteDesde : nuevoParametro.vigenteDesde}
              onChange={e => handleChange(e, !!editandoParametro)}
            />
            <input
              type="date"
              name="vigenteHasta"
              value={editandoParametro ? editandoParametro.vigenteHasta : nuevoParametro.vigenteHasta}
              onChange={e => handleChange(e, !!editandoParametro)}
            />
            <select
              name="estado"
              value={editandoParametro ? editandoParametro.estado : nuevoParametro.estado}
              onChange={e => handleChange(e, !!editandoParametro)}
            >
              <option value="Activo">Activo</option>
              <option value="Eliminado">Eliminado</option>
            </select>

            <div className="igss-modal-botones">
              <button onClick={editandoParametro ? actualizarParametro : crearParametro}>Guardar</button>
              <button onClick={() => { setMostrarModal(false); setEditandoParametro(null); }}>Cancelar</button>
            </div>
          </div>
        </div>
      )}

      <table className="igss-tabla">
        <thead>
          <tr>
            <th>Acción</th>
            <th>ID</th>
            <th>Porcentaje</th>
            <th>Tipo</th>
            <th>Descripción</th>
            <th>Desde</th>
            <th>Hasta</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {parametros.map(p => (
            <tr key={p.idParametro}>
              <td>
                <button className="igss-btn-editar" onClick={() => abrirModal(p)}><FontAwesomeIcon icon={faPen} /></button>
              </td>
              <td>{p.idParametro}</td>
              <td>{p.porcentaje}%</td>
              <td>{p.tipo}</td>
              <td>{p.descripcion}</td>
              <td>{p.vigenteDesde}</td>
              <td>{p.vigenteHasta}</td>
              <td>{p.estado}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default IgssCrud;
