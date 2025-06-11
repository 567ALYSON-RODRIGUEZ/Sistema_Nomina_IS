import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Asistencias.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faTrash } from '@fortawesome/free-solid-svg-icons';

function AsistenciaCrud() {
  const [asistencias, setAsistencias] = useState([]);
  const [nuevaAsistencia, setNuevaAsistencia] = useState({
    idEmpleado: '',
    fecha: '',
    horaEntrada: '',
    horaSalida: '',
    tipo: 'Presente',
    estado: null
  });
  const [editandoAsistencia, setEditandoAsistencia] = useState(null);
  const [mostrarModal, setMostrarModal] = useState(false);
  const [idBuscar, setIdBuscar] = useState('');

  useEffect(() => {
    cargarAsistencias();
  }, []);

  const cargarAsistencias = () => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8095/asistencia/obtenerTodos', {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(res => setAsistencias(res.data))
    .catch(err => console.error("Error al cargar asistencias:", err));
  };

  const formatearHora = (hora) => {
    if (!hora) return '';
    return hora.length === 5 ? hora + ':00' : hora;
  };

  const crearAsistencia = () => {
    const token = localStorage.getItem('token');
    const asistenciaEnviar = {
      idEmpleado: Number(nuevaAsistencia.idEmpleado),
      fecha: nuevaAsistencia.fecha,
      horaEntrada: formatearHora(nuevaAsistencia.horaEntrada),
      horaSalida: formatearHora(nuevaAsistencia.horaSalida),
      tipo: nuevaAsistencia.tipo,
      estado: null
    };

    axios.post('http://localhost:8095/asistencia/crear', asistenciaEnviar, {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(() => {
      alert("Asistencia creada exitosamente.");
      setMostrarModal(false);
      cargarAsistencias();
      setNuevaAsistencia({ idEmpleado: '', fecha: '', horaEntrada: '', horaSalida: '', tipo: 'Presente', estado: null });
    })
    .catch(err => {
      console.error("Error al crear asistencia:", err);
      alert("Error al crear asistencia.");
    });
  };

  const actualizarAsistencia = () => {
    const token = localStorage.getItem('token');
    const asistenciaEnviar = {
      idAsistencia: editandoAsistencia.idAsistencia,
      idEmpleado: Number(editandoAsistencia.idEmpleado),
      fecha: editandoAsistencia.fecha,
      horaEntrada: formatearHora(editandoAsistencia.horaEntrada),
      horaSalida: formatearHora(editandoAsistencia.horaSalida),
      tipo: editandoAsistencia.tipo,
      estado: null
    };

    axios.put(`http://localhost:8095/asistencia/reemplazar/${editandoAsistencia.idAsistencia}`, asistenciaEnviar, {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(() => {
      alert("Asistencia actualizada correctamente.");
      setMostrarModal(false);
      setEditandoAsistencia(null);
      cargarAsistencias();
    })
    .catch(err => {
      console.error("Error al actualizar asistencia:", err);
      alert("No se pudo actualizar la asistencia.");
    });
  };

  const eliminarAsistencia = (id) => {
    if (!window.confirm("¿Estás seguro de eliminar esta asistencia?")) return;
    const token = localStorage.getItem('token');
    axios.delete(`http://localhost:8095/asistencia/eliminar/${id}`, {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(() => {
      alert("Asistencia eliminada.");
      setAsistencias(asistencias.filter(a => a.idAsistencia !== id));
    })
    .catch(err => {
      console.error("Error al eliminar asistencia:", err);
      alert("No se pudo eliminar la asistencia.");
    });
  };

  const buscarPorId = () => {
    if (!idBuscar) return cargarAsistencias();
    const token = localStorage.getItem('token');
    axios.get(`http://localhost:8095/asistencia/obtenerPorId/${idBuscar}`, {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(res => setAsistencias([res.data]))
    .catch(err => {
      console.error("Asistencia no encontrada:", err);
      alert("Asistencia no encontrada.");
    });
  };

  const abrirModal = (asistencia = null) => {
    setEditandoAsistencia(asistencia);
    if (asistencia) {
      setNuevaAsistencia({ ...asistencia });
    } else {
      setNuevaAsistencia({ idEmpleado: '', fecha: '', horaEntrada: '', horaSalida: '', tipo: 'Presente', estado: null });
    }
    setMostrarModal(true);
  };

  const handleChange = (e, esEdicion = false) => {
    const { name, value } = e.target;
    if (esEdicion) {
      setEditandoAsistencia(prev => ({ ...prev, [name]: value }));
    } else {
      setNuevaAsistencia(prev => ({ ...prev, [name]: value }));
    }
  };

  return (
    <div className="asistencia-panel">
      <div className="asistencia-barra">
        <h2>Gestión de Asistencias</h2>
        <div className="asistencia-acciones">
          <button className="asistencia-btn-nuevo" onClick={() => abrirModal()}>+ Nuevo</button>
          <input
            type="text"
            placeholder="Buscar por ID"
            value={idBuscar}
            onChange={e => setIdBuscar(e.target.value)}
            style={{ padding: '5px', borderRadius: '5px', border: '1px solid #ddd' }}
          />
          <button className="asistencia-btn-buscar" onClick={buscarPorId}>Buscar</button>
          <button className="asistencia-btn-buscar" onClick={cargarAsistencias}>Regresar</button>
        </div>
      </div>

      {mostrarModal && (
        <div className="asistencia-modal">
          <div className="asistencia-modal-contenido">
            <h3>{editandoAsistencia ? 'Editar Asistencia' : 'Nueva Asistencia'}</h3>

            <input
              type="number"
              name="idEmpleado"
              placeholder="ID Empleado"
              value={editandoAsistencia ? editandoAsistencia.idEmpleado : nuevaAsistencia.idEmpleado}
              onChange={e => handleChange(e, !!editandoAsistencia)}
            />
            <input
              type="date"
              name="fecha"
              placeholder="Fecha"
              value={editandoAsistencia ? editandoAsistencia.fecha : nuevaAsistencia.fecha}
              onChange={e => handleChange(e, !!editandoAsistencia)}
            />
            <input
              type="time"
              name="horaEntrada"
              placeholder="Hora Entrada"
              value={editandoAsistencia ? editandoAsistencia.horaEntrada?.slice(0,5) : nuevaAsistencia.horaEntrada}
              onChange={e => handleChange(e, !!editandoAsistencia)}
            />
            <input
              type="time"
              name="horaSalida"
              placeholder="Hora Salida"
              value={editandoAsistencia ? editandoAsistencia.horaSalida?.slice(0,5) : nuevaAsistencia.horaSalida}
              onChange={e => handleChange(e, !!editandoAsistencia)}
            />
            <select
              name="tipo"
              value={editandoAsistencia ? editandoAsistencia.tipo : nuevaAsistencia.tipo}
              onChange={e => handleChange(e, !!editandoAsistencia)}
            >
              <option value="Presente">Presente</option>
              <option value="Ausente">Ausente</option>
              <option value="Justificado">Justificado</option>
              <option value="Vacaciones">Vacaciones</option>
              <option value="IGSS">IGSS</option>
              <option value="Permiso sin goce">Permiso sin goce</option>
            </select>

            <div className="asistencia-modal-botones">
              <button onClick={editandoAsistencia ? actualizarAsistencia : crearAsistencia}>Guardar</button>
              <button onClick={() => { setMostrarModal(false); setEditandoAsistencia(null); }}>Cancelar</button>
            </div>
          </div>
        </div>
      )}

      <div className="asistencia-tabla-wrapper">
        <table className="asistencia-tabla">
          <thead>
            <tr>
              <th>Acción</th>
              <th>ID</th>
              <th>ID Empleado</th>
              <th>Fecha</th>
              <th>Hora Entrada</th>
              <th>Hora Salida</th>
              <th>Tipo</th>
            </tr>
          </thead>
          <tbody>
            {asistencias.map(a => (
              <tr key={a.idAsistencia}>
                <td>
                  <button className="asistencia-btn-editar" onClick={() => abrirModal(a)}>
                    <FontAwesomeIcon icon={faPen} />
                  </button>
                  <button className="asistencia-btn-eliminar" onClick={() => eliminarAsistencia(a.idAsistencia)}>
                    <FontAwesomeIcon icon={faTrash} />
                  </button>
                </td>
                <td>{a.idAsistencia}</td>
                <td>{a.idEmpleado}</td>
                <td>{a.fecha}</td>
                <td>{a.horaEntrada}</td>
                <td>{a.horaSalida}</td>
                <td>{a.tipo}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default AsistenciaCrud;
