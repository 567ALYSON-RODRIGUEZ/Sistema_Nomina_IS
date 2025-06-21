import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Vacaciones.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faTrash, faFileAlt, faHistory } from '@fortawesome/free-solid-svg-icons';

function VacacionesPanel() {
  const [vacaciones, setVacaciones] = useState([]);
  const [nuevaVacacion, setNuevaVacacion] = useState({
    idEmpleado: '',
    fechaInicio: '',
    fechaFin: '',
    dias: '',
    estado: 'Pendiente'
  });
  const [mostrarModal, setMostrarModal] = useState(false);
  const [editandoVacacion, setEditandoVacacion] = useState(null);
  const [idBuscar, setIdBuscar] = useState('');
  const [mostrarConstancia, setMostrarConstancia] = useState(false);
  const [vacacionSeleccionada, setVacacionSeleccionada] = useState(null);
  const [diasDisponibles, setDiasDisponibles] = useState(15);
  const [historialModal, setHistorialModal] = useState(false);
  const [historialVacaciones, setHistorialVacaciones] = useState([]);
  const [anioHistorial, setAnioHistorial] = useState(new Date().getFullYear());

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

  const cargarHistorialEmpleado = (idEmpleado, anio) => {
    axios.get(`http://localhost:8095/vacaciones/porEmpleadoAnio/${idEmpleado}/${anio}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
    .then(res => {
      setHistorialVacaciones(res.data);
      setHistorialModal(true);
    })
    .catch(err => {
      console.error("Error al obtener historial:", err);
      alert("Error al cargar historial de vacaciones");
    });
  };

  const obtenerDiasDisponibles = (idEmpleado, fecha) => {
    if (!idEmpleado || !fecha) return;
    
    const anio = new Date(fecha).getFullYear();
    axios.get(`http://localhost:8095/vacaciones/diasDisponibles/${idEmpleado}/${anio}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
    .then(res => {
      setDiasDisponibles(res.data.diasDisponibles);
    })
    .catch(err => {
      console.error("Error al obtener días disponibles:", err);
    });
  };

  const crearVacacion = () => {
    if (!nuevaVacacion.idEmpleado || !nuevaVacacion.fechaInicio || !nuevaVacacion.dias) {
      alert("Complete todos los campos obligatorios");
      return;
    }

    const anio = new Date(nuevaVacacion.fechaInicio).getFullYear();
    
    // Validar días disponibles primero
    axios.get(`http://localhost:8095/vacaciones/diasDisponibles/${nuevaVacacion.idEmpleado}/${anio}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
    .then(res => {
      const diasDisp = res.data.diasDisponibles;
      
      if (nuevaVacacion.dias > diasDisp) {
        alert(`Días insuficientes. Disponibles: ${diasDisp}`);
        return;
      }

      // Si hay días suficientes, crear la vacación
      axios.post('http://localhost:8095/vacaciones/crear', {
        ...nuevaVacacion,
        periodoAnio: anio
      }, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      })
      .then(() => {
        alert("Vacación registrada exitosamente");
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
        console.error("Error al crear vacación:", err);
        alert("Error al registrar vacación");
      });
    })
    .catch(err => {
      console.error("Error al validar días:", err);
      alert("Error al verificar días disponibles");
    });
  };

  const actualizarVacacion = () => {
    if (!editandoVacacion) return;

    axios.put(`http://localhost:8095/vacaciones/actualizar/${editandoVacacion.idVacacion}`, editandoVacacion, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
    .then(() => {
      alert("Vacación actualizada exitosamente");
      setMostrarModal(false);
      setEditandoVacacion(null);
      cargarTodas();
    })
    .catch(err => {
      console.error("Error al actualizar:", err);
      alert("Error al actualizar vacación");
    });
  };

  const eliminarVacacion = (id) => {
    if (!window.confirm("¿Está seguro de rechazar esta solicitud de vacaciones?")) return;
    
    axios.delete(`http://localhost:8095/vacaciones/eliminar/${id}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
    .then(() => {
      alert("Vacación rechazada");
      cargarTodas();
    })
    .catch(err => {
      console.error("Error al eliminar:", err);
      alert("Error al rechazar vacación");
    });
  };

  const buscarPorId = () => {
    if (!idBuscar) {
      alert("Ingrese un ID para buscar");
      return;
    }
    
    axios.get(`http://localhost:8095/vacaciones/obtenerPorId/${idBuscar}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
    .then(res => {
      if (res.data) setVacaciones([res.data]);
      else alert("No se encontró la vacación");
    })
    .catch(err => {
      console.error("Error:", err);
      alert("Error al buscar vacación");
    });
  };

  const abrirModalEditar = (vacacion) => {
    setEditandoVacacion({
      ...vacacion,
      fechaInicio: vacacion.fechaInicio ? vacacion.fechaInicio.split('T')[0] : '',
      fechaFin: vacacion.fechaFin ? vacacion.fechaFin.split('T')[0] : ''
    });
    setMostrarModal(true);
    
    // Cargar días disponibles al editar
    if (vacacion.idEmpleado && vacacion.fechaInicio) {
      obtenerDiasDisponibles(vacacion.idEmpleado, vacacion.fechaInicio);
    }
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
          <button className="btn-nuevo" onClick={() => { 
            setEditandoVacacion(null); 
            setMostrarModal(true);
            setDiasDisponibles(15);
          }}>
            + Nueva
          </button>
          <input
            type="text"
            value={idBuscar}
            onChange={e => setIdBuscar(e.target.value)}
            placeholder="Buscar por ID"
          />
          <button className="btn-buscar" onClick={buscarPorId}>Buscar</button>
          <button className="btn-buscar" onClick={cargarTodas}>Mostrar Todos</button>
        </div>
      </div>

      {/* Resumen de días disponibles */}
      {nuevaVacacion.idEmpleado && (
        <div className="resumen-vacaciones">
          <h3>Resumen de Días</h3>
          <div className="dias-info">
            <p><strong>Días por derecho:</strong> 15</p>
            <p><strong>Días usados:</strong> {15 - diasDisponibles}</p>
            <p><strong>Días disponibles:</strong> {diasDisponibles}</p>
          </div>
          <button 
            className="btn-historial"
            onClick={() => cargarHistorialEmpleado(nuevaVacacion.idEmpleado, anioHistorial)}
          >
            <FontAwesomeIcon icon={faHistory} /> Ver Historial
          </button>
        </div>
      )}

      {/* Modal Crear/Editar */}
      {mostrarModal && (
        <div className="modal">
          <div className="modal-contenido">
            <h3>{editandoVacacion ? 'Editar Vacación' : 'Registrar Vacación'}</h3>

            <div className="dias-disponibles">
              <strong>Días disponibles:</strong> {diasDisponibles}
              {diasDisponibles <= 0 && <span className="texto-error"> (No hay días disponibles)</span>}
            </div>

            <div className="form-group">
              <label>ID Empleado *</label>
              <input
                type="number"
                value={editandoVacacion?.idEmpleado || nuevaVacacion.idEmpleado}
                onChange={e => {
                  const val = e.target.value;
                  if (editandoVacacion) {
                    setEditandoVacacion({ ...editandoVacacion, idEmpleado: val });
                  } else {
                    setNuevaVacacion({ ...nuevaVacacion, idEmpleado: val });
                  }
                }}
              />
            </div>

            <div className="form-group">
              <label>Fecha Inicio *</label>
              <input
                type="date"
                value={editandoVacacion?.fechaInicio || nuevaVacacion.fechaInicio}
                onChange={e => {
                  const val = e.target.value;
                  if (editandoVacacion) {
                    setEditandoVacacion({ ...editandoVacacion, fechaInicio: val });
                    obtenerDiasDisponibles(editandoVacacion.idEmpleado, val);
                  } else {
                    setNuevaVacacion({ ...nuevaVacacion, fechaInicio: val });
                    obtenerDiasDisponibles(nuevaVacacion.idEmpleado, val);
                  }
                }}
              />
            </div>

            <div className="form-group">
              <label>Fecha Fin</label>
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
            </div>

            <div className="form-group">
              <label>Días *</label>
              <input
                type="number"
                value={editandoVacacion?.dias || nuevaVacacion.dias}
                onChange={e => {
                  const val = e.target.value;
                  editandoVacacion
                    ? setEditandoVacacion({ ...editandoVacacion, dias: val })
                    : setNuevaVacacion({ ...nuevaVacacion, dias: val });
                }}
                min="1"
                max={diasDisponibles}
              />
            </div>

            <div className="form-group">
              <label>Estado</label>
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
            </div>

            <div className="modal-botones">
              <button onClick={editandoVacacion ? actualizarVacacion : crearVacacion}>
                {editandoVacacion ? 'Actualizar' : 'Guardar'}
              </button>
              <button className="btn-cancelar" onClick={() => { 
                setMostrarModal(false); 
                setEditandoVacacion(null); 
              }}>
                Cancelar
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Modal Historial */}
      {historialModal && (
        <div className="modal">
          <div className="modal-contenido historial-modal">
            <h3>Historial de Vacaciones</h3>
            <div className="historial-header">
              <span>Empleado: {historialVacaciones[0]?.nombreCompleto || 'N/A'}</span>
              <select 
                value={anioHistorial} 
                onChange={e => {
                  setAnioHistorial(parseInt(e.target.value));
                  cargarHistorialEmpleado(nuevaVacacion.idEmpleado, parseInt(e.target.value));
                }}
              >
                {Array.from({length: 5}, (_, i) => new Date().getFullYear() - i).map(year => (
                  <option key={year} value={year}>{year}</option>
                ))}
              </select>
            </div>
            
            <table className="tabla-historial">
              <thead>
                <tr>
                  <th>Periodo</th>
                  <th>Días</th>
                  <th>Estado</th>
                </tr>
              </thead>
              <tbody>
                {historialVacaciones.map(v => (
                  <tr key={v.idVacacion}>
                    <td>{v.fechaInicio?.split('T')[0]} a {v.fechaFin?.split('T')[0]}</td>
                    <td>{v.dias}</td>
                    <td>{v.estado}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            
            <div className="modal-botones">
              <button onClick={() => setHistorialModal(false)}>Cerrar</button>
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

            <p>Centro o Unidad donde presta servicios el empleado a nivel</p>
            <p>***{vacacionSeleccionada.nivelJerarquico}***</p>
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
                    <td>{diasDisponibles - vacacionSeleccionada.dias} días</td>
                    <td>del Periodo</td>
                    <td>{new Date(vacacionSeleccionada.fechaInicio).getFullYear()}</td>
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
              <td className="acciones">
                <button className='btn-editar' onClick={() => abrirModalEditar(v)}>
                  <FontAwesomeIcon icon={faPen} />
                </button>
                <button className='btn-eliminar' onClick={() => eliminarVacacion(v.idVacacion)}>
                  <FontAwesomeIcon icon={faTrash} />
                </button>
                <button className='btn-constancia' onClick={() => generarConstancia(v)}>
                  <FontAwesomeIcon icon={faFileAlt} />
                </button>
                <button 
                  className='btn-historial'
                  onClick={() => cargarHistorialEmpleado(v.idEmpleado, new Date(v.fechaInicio).getFullYear())}
                >
                  <FontAwesomeIcon icon={faHistory} />
                </button>
              </td>
              <td>{v.idVacacion}</td>
              <td>{v.idEmpleado}</td>
              <td>{v.nombreCompleto}</td>
              <td>{v.fechaInicio?.split('T')[0]}</td>
              <td>{v.fechaFin?.split('T')[0]}</td>
              <td>{v.dias}</td>
              <td className={`estado-${v.estado.toLowerCase()}`}>{v.estado}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default VacacionesPanel;