import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Vacaciones.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faTrash, faFileAlt } from '@fortawesome/free-solid-svg-icons';

function VacacionesPanel() {
  const [vacaciones, setVacaciones] = useState([]);
  const [nuevaVacacion, setNuevaVacacion] = useState({
    idEmpleado: '',
    fechaInicio: '',
    fechaFin: '',
    dias: '',
    estado: 'Pendiente',
    // Puedes agregar puesto si lo necesitas en creación
  });
  const [mostrarModal, setMostrarModal] = useState(false);
  const [editandoVacacion, setEditandoVacacion] = useState(null);
  const [idBuscar, setIdBuscar] = useState('');
  const [mostrarConstancia, setMostrarConstancia] = useState(false);
  const [vacacionSeleccionada, setVacacionSeleccionada] = useState(null);

  useEffect(() => {
    cargarTodas();
  }, []);

  const cargarTodas = () => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8095/vacaciones/obtenerTodos', {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(res => setVacaciones(res.data))
    .catch(err => console.error("Error:", err));
  };

  const eliminarVacacion = (id) => {
    if (!window.confirm("¿Marcar como eliminadas/no permitidas estas vacaciones?")) return;
    axios.delete(`http://localhost:8095/vacaciones/eliminar/${id}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
    .then(() => {
      alert("Vacaciones finalizadas.");
      cargarTodas();
    })
    .catch(err => {
      console.error("Error:", err);
      alert("No se pudieron finalizar las vacaciones.");
    });
  };

  const crearVacacion = () => {
    axios.post('http://localhost:8095/vacaciones/crear', nuevaVacacion, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
    .then(() => {
      alert("Vacación registrada.");
      setMostrarModal(false);
      setNuevaVacacion({
        idEmpleado: '',
        fechaInicio: '',
        fechaFin: '',
        dias: '',
        estado: 'Pendiente'
      });
      cargarTodas();
    })
    .catch(err => {
      console.error("Error:", err);
      alert("Error al registrar vacaciones.");
    });
  };

  const actualizarVacacion = () => {
    axios.put(`http://localhost:8095/vacaciones/actualizar/${editandoVacacion.idVacacion}`, editandoVacacion, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
    .then(() => {
      alert("Vacación actualizada.");
      setMostrarModal(false);
      setEditandoVacacion(null);
      cargarTodas();
    })
    .catch(err => {
      console.error("Error:", err);
      alert("No se pudo actualizar.");
    });
  };

  const buscarPorId = () => {
    if(!idBuscar) {
      alert("Ingrese un ID para buscar");
      return;
    }
    axios.get(`http://localhost:8095/vacaciones/obtenerPorId/${idBuscar}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
    .then(res => {
      if(res.data) setVacaciones([res.data]);
      else alert("No se encontró la vacación.");
    })
    .catch(err => {
      console.error("Error:", err);
      alert("No se encontró la vacación.");
    });
  };

  const abrirModalEditar = (vacacion) => {
    setEditandoVacacion({
      ...vacacion,
      fechaInicio: vacacion.fechaInicio ? vacacion.fechaInicio.split('T')[0] : '',
      fechaFin: vacacion.fechaFin ? vacacion.fechaFin.split('T')[0] : ''
    });
    setMostrarModal(true);
  };

  const generarConstancia = (vacacion) => {
    setVacacionSeleccionada(vacacion);
    setMostrarConstancia(true);
  };

  const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return `${date.getDate()} Día ${date.toLocaleString('es-ES', { month: 'short' })} ${date.getFullYear()} Año`;
  };

  const formatReincorporacionDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    date.setDate(date.getDate() + 1);
    return date.toLocaleDateString('es-ES', { day: 'numeric', month: 'long', year: 'numeric' });
  };

  return (
    <div className="contenedor-panel">
      <div className="barra-superiorp">
        <h2>Gestión de Vacaciones</h2>
        <div className="acciones-barra">
          <button className="btn-nuevo" onClick={() => { setEditandoVacacion(null); setMostrarModal(true); }}>
            + Nueva
          </button>
          <input
            type="text"
            value={idBuscar}
            onChange={e => setIdBuscar(e.target.value)}
            placeholder="Buscar por ID"
          />
          <button className="btn-buscar" onClick={buscarPorId}>Buscar</button>
          <button className="btn-buscar" onClick={cargarTodas}>Regresar</button>
        </div>
      </div>

      {/* Modal Crear/Editar */}
      {mostrarModal && (
        <div className="modal">
          <div className="modal-contenido">
            <h3>{editandoVacacion ? 'Editar Vacación' : 'Registrar Vacación'}</h3>

            <input
              type="number"
              placeholder="ID Empleado"
              value={editandoVacacion?.idEmpleado || nuevaVacacion.idEmpleado}
              onChange={e => {
                const val = e.target.value;
                editandoVacacion
                  ? setEditandoVacacion({ ...editandoVacacion, idEmpleado: val })
                  : setNuevaVacacion({ ...nuevaVacacion, idEmpleado: val });
              }}
            />
            <input
              type="date"
              value={editandoVacacion?.fechaInicio || nuevaVacacion.fechaInicio}
              onChange={e => {
                const val = e.target.value;
                editandoVacacion
                  ? setEditandoVacacion({ ...editandoVacacion, fechaInicio: val })
                  : setNuevaVacacion({ ...nuevaVacacion, fechaInicio: val });
              }}
            />
            <input
              type="date"
              value={editandoVacacion?.fechaFin || nuevaVacacion.fechaFin}
              onChange={e => {
                const val = e.target.value;
                editandoVacacion
                  ? setEditandoVacacion({ ...editandoVacacion, fechaFin: val })
                  : setNuevaVacacion({ ...nuevaVacacion, fechaFin: val });
              }}
            />
            <input
              type="number"
              placeholder="Días"
              value={editandoVacacion?.dias || nuevaVacacion.dias}
              onChange={e => {
                const val = e.target.value;
                editandoVacacion
                  ? setEditandoVacacion({ ...editandoVacacion, dias: val })
                  : setNuevaVacacion({ ...nuevaVacacion, dias: val });
              }}
            />
            <select
              value={editandoVacacion?.estado || nuevaVacacion.estado}
              onChange={e => {
                const val = e.target.value;
                editandoVacacion
                  ? setEditandoVacacion({ ...editandoVacacion, estado: val })
                  : setNuevaVacacion({ ...nuevaVacacion, estado: val });
              }}
            >
              <option value="Pendiente">Pendiente</option>
              <option value="Aprobado">Aprobado</option>
              <option value="Rechazado">Rechazado</option>
            </select>
            <div className="modal-botones">
              <button onClick={editandoVacacion ? actualizarVacacion : crearVacacion}>
                Guardar
              </button>
              <button onClick={() => { setMostrarModal(false); setEditandoVacacion(null); }}>
                Cancelar
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Modal Constancia */}
      {mostrarConstancia && vacacionSeleccionada && (
        <div className="modal">
          <div className="modal-contenido constancia-modal">
            <h1>CONSTANCIA DE VACACIONES</h1>
            <table>
              <thead>
                <tr>
                  <th>Nombres y Apellidos del Empleado</th>
                  <th>Puesto que Ocupa</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{vacacionSeleccionada.nombreCompleto}</td>
                  <td>{vacacionSeleccionada.puesto}</td>
                </tr>
              </tbody>
            </table>

            <p>Centro o Unidad Administrativa/Operativa donde presta servicios el empleado</p>

            <p><strong>Por este medio solicito me sea autorizado el goce de vacaciones, que solicito a partir del</strong></p>

            <div className="fecha-vacaciones">
              <div className="fecha-item">
                <p>{new Date(vacacionSeleccionada.fechaInicio).getDate()} Día</p>
                <p>{new Date(vacacionSeleccionada.fechaInicio).toLocaleString('es-ES', { month: 'short' })}</p>
                <p>{new Date(vacacionSeleccionada.fechaInicio).getFullYear()} Año</p>
              </div>
              <div className="texto-centrado">al</div>
              <div className="fecha-item">
                <p>{new Date(vacacionSeleccionada.fechaFin).getDate()} Día</p>
                <p>{new Date(vacacionSeleccionada.fechaFin).toLocaleString('es-ES', { month: 'short' })}</p>
                <p>{new Date(vacacionSeleccionada.fechaFin).getFullYear()} Año</p>
              </div>
            </div>

            <div className="linea-divisoria"></div>

            <div className="firma-section">
              <div className="firma-box">
                <p>Firma del Solicitante</p>
              </div>
              <div className="firma-box">
                <p>Firma Gerencia Administrativa (sello)</p>
              </div>
            </div>

            <div className="uso-exclusivo">
              <h2>PARA USO EXCLUSIVO DE CONTROL DE PERSONAL</h2>
              <table>
                <tbody>
                  <tr>
                    <td>Días otorgados a la fecha</td>
                    <td>{vacacionSeleccionada.dias}</td>
                    <td>Formulario No.</td>
                    <td>{vacacionSeleccionada.idVacacion}</td>
                  </tr>
                  <tr>
                    <td colSpan="4">
                      Correspondientes al año laborado del: {formatDate(vacacionSeleccionada.fechaInicio)} al {formatDate(vacacionSeleccionada.fechaFin)}
                    </td>
                  </tr>
                  <tr>
                    <td colSpan="4">
                      Se disfrutarán: {vacacionSeleccionada.dias} día(s) de vacaciones, a partir del: {formatDate(vacacionSeleccionada.fechaInicio)} al {formatDate(vacacionSeleccionada.fechaFin)}
                    </td>
                  </tr>
                </tbody>
              </table>

              <div className="fecha-reincorporacion">
                <p>Se presenta a sus labores nuevamente el día: {formatReincorporacionDate(vacacionSeleccionada.fechaFin)}</p>
              </div>

              <table>
                <tbody>
                  <tr>
                    <td>Días pendientes</td>
                    <td>{15 - vacacionSeleccionada.dias} días</td>
                    <td>del Periodo</td>
                    <td>{new Date().getFullYear()}</td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div className="modal-botones">
              <button onClick={() => setMostrarConstancia(false)}>Cerrar</button>
            </div>
          </div>
        </div>
      )}

      {/* Tabla Principal */}
      <table className="tabla-puestos">
        <thead>
          <tr>
            <th>Acción</th>
            <th>ID Vacación</th>
            <th>ID Empleado</th>
            <th>Nombre</th>
            <th>Inicio</th>
            <th>Fin</th>
            <th>Días</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {vacaciones.map(v => (
            <tr key={v.idVacacion}>
              <td>
                <button className='btn-editar' onClick={() => abrirModalEditar(v)}><FontAwesomeIcon icon={faPen} /></button>
                <button  className='btn-eliminar' onClick={() => eliminarVacacion(v.idVacacion)}><FontAwesomeIcon icon={faTrash} /></button>
                <button onClick={() => generarConstancia(v)}><FontAwesomeIcon icon={faFileAlt} /></button>
              </td>
              <td>{v.idVacacion}</td>
              <td>{v.idEmpleado}</td>
              <td>{v.nombreCompleto}</td>
              <td>{v.fechaInicio?.split('T')[0]}</td> {/* ?. para evitar error si undefined */}
              <td>{v.fechaFin?.split('T')[0]}</td>
              <td>{v.dias}</td>
              <td>{v.estado}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default VacacionesPanel;
