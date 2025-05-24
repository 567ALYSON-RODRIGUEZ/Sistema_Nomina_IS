import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Bonificacion.css'; 
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faTrash } from '@fortawesome/free-solid-svg-icons';

function BonificacionCrud() {
  const [bonificaciones, setBonificaciones] = useState([]);
  const [nuevoParametro, setNuevoParametro] = useState({
    montoFijo: '',
    vigenteDesde: '',
    vigenteHasta: '',
    estado: 'Activo'
  });
  const [editandoParametro, setEditandoParametro] = useState(null);
  const [mostrarModal, setMostrarModal] = useState(false);
  const [idBuscar, setIdBuscar] = useState('');

  useEffect(() => {
    cargarBonificaciones();
  }, []);

  const cargarBonificaciones = () => {
    axios.get('http://localhost:8095/bonificacion/obtenerTodos', {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setBonificaciones(res.data))
      .catch(err => console.error("Error al cargar bonificaciones:", err));
  };

  const crearBonificacion = () => {
    axios.post('http://localhost:8095/bonificacion/crear', nuevoParametro, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Bonificación creada.");
        setMostrarModal(false);
        cargarBonificaciones();
        setNuevoParametro({ montoFijo: '', vigenteDesde: '', vigenteHasta: '', estado: 'Activo' });
      })
      .catch(err => {
        console.error("Error al crear bonificación:", err);
        alert("Error al crear bonificación.");
      });
  };

  const actualizarBonificacion = () => {
    axios.put(`http://localhost:8095/bonificacion/reemplazar/${editandoParametro.idParametro}`, editandoParametro, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Bonificación actualizada.");
        setMostrarModal(false);
        setEditandoParametro(null);
        cargarBonificaciones();
      })
      .catch(err => {
        console.error("Error al actualizar bonificación:", err);
        alert("No se pudo actualizar.");
      });
  };

  const eliminarBonificacion = (id) => {
    if (!window.confirm("¿Estás seguro de eliminar esta bonificación?")) return;

    axios.delete(`http://localhost:8095/bonificacion/eliminar/${id}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Bonificación eliminada.");
        setBonificaciones(bonificaciones.filter(b => b.idParametro !== id));
      })
      .catch(err => {
        console.error("Error al eliminar bonificación:", err);
        alert("No se pudo eliminar.");
      });
  };

  const buscarPorId = () => {
    axios.get(`http://localhost:8095/bonificacion/obtenerPorId/${idBuscar}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setBonificaciones([res.data]))
      .catch(err => {
        console.error("Bonificación no encontrada:", err);
        alert("Bonificación no encontrada.");
      });
  };

  const abrirModal = (bonificacion = null) => {
    if (bonificacion) {
      setEditandoParametro(bonificacion);
    } else {
      setNuevoParametro({ montoFijo: '', vigenteDesde: '', vigenteHasta: '', estado: 'Activo' });
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
    <div className="parametros-panel">
      <div className="parametros-barra">
        <h2>Gestión de Bonificaciones</h2>
        <div className="parametros-acciones">
          <button className="parametros-btn-nuevo" onClick={() => abrirModal()}>+ Nuevo</button>
          <input 
            type="text" 
            value={idBuscar} 
            onChange={e => setIdBuscar(e.target.value)} 
            placeholder="Buscar por ID" 
          />
          <button className="parametros-btn-buscar" onClick={buscarPorId}>Buscar</button>
          <button className="parametros-btn-buscar" onClick={cargarBonificaciones}>Regresar</button>
        </div>
      </div>

      {mostrarModal && (
        <div className="parametros-modal">
          <div className="parametros-modal-contenido">
            <h3>{editandoParametro ? 'Editar Bonificación' : 'Nueva Bonificación'}</h3>
            <input
              type="number"
              step="0.01"
              name="montoFijo"
              placeholder="Monto fijo (Q)"
              value={editandoParametro ? editandoParametro.montoFijo : nuevoParametro.montoFijo}
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

            <div className="parametros-modal-botones">
              <button onClick={editandoParametro ? actualizarBonificacion : crearBonificacion}>Guardar</button>
              <button onClick={() => { setMostrarModal(false); setEditandoParametro(null); }}>Cancelar</button>
            </div>
          </div>
        </div>
      )}

      <table className="parametros-tabla">
        <thead>
          <tr>
            <th>Acción</th>
            <th>ID</th>
            <th>Monto Fijo (Q)</th>
            <th>Vigente Desde</th>
            <th>Vigente Hasta</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {bonificaciones.map(b => (
            <tr key={b.idParametro}>
              <td>
                <button className="parametros-btn-editar" onClick={() => abrirModal(b)}>
                  <FontAwesomeIcon icon={faPen} />
                </button>
                <button className="parametros-btn-eliminar" onClick={() => eliminarBonificacion(b.idParametro)}>
                  <FontAwesomeIcon icon={faTrash} />
                </button>
              </td>
              <td>{b.idParametro}</td>
              <td>Q{parseFloat(b.montoFijo).toFixed(2)}</td>
              <td>{b.vigenteDesde}</td>
              <td>{b.vigenteHasta}</td>
              <td>{b.estado}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default BonificacionCrud;
