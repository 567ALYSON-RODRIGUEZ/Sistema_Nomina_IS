import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Vacaciones.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faTrash, faFileAlt } from '@fortawesome/free-solid-svg-icons';

function VacacionesPanel() {
  const [vacaciones, setVacaciones] = useState([]);
  const [nuevaVacacion, setNuevaVacacion] = useState({
    id_empleado: '',
    fecha_inicio: '',
    fecha_fin: '',
    dias: '',
    estado: 'Pendiente'
  });
  const [mostrarModal, setMostrarModal] = useState(false);
  const [editandoVacacion, setEditandoVacacion] = useState(null);
  const [idBuscar, setIdBuscar] = useState('');
  const [mostrarConstancia, setMostrarConstancia] = useState(false);
  const [vacacionSeleccionada, setVacacionSeleccionada] = useState(null);
  const [empleados, setEmpleados] = useState([]);

  useEffect(() => {
    cargarTodas();
    cargarEmpleados();
  }, []);

  const cargarEmpleados = async () => {
  const token = localStorage.getItem('token');
  try {
    const response = await axios.get('http://localhost:8095/empleados/conPuestos', {
      headers: { Authorization: `Bearer ${token}` }
    });
    console.log("Datos recibidos del backend:", response.data);
    setEmpleados(response.data);
  } catch (err) {
    console.error("Error al cargar empleados:", err);
    setEmpleados([]);
  }
};

const obtenerNombreEmpleado = (id) => {
  const empleado = empleados.find(e => e.idEmpleado == id || e.id_empleado == id);
  if (!empleado) return 'No encontrado';
  
  // Maneja ambos casos: datos nuevos (nombres + apellidos) y viejos (solo nombres)
  return `${empleado.nombres || ''} ${empleado.apellidos || ''}`.trim();
};

const obtenerPuestoEmpleado = (id) => {
  const empleado = empleados.find(e => e.idEmpleado == id || e.id_empleado == id);
  if (!empleado) return 'No especificado';
  
  // Maneja ambos casos: datos nuevos (nombrePuesto) y viejos (puesto.nombre)
  return empleado.nombrePuesto || empleado.puesto?.nombre || 'No especificado';
};

  /*const cargarTodas = () => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8095/vacaciones/obtenerTodos', {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => setVacaciones(res.data))
      .catch(err => console.error("Error:", err));
  };*/

  const cargarTodas = () => {
  const token = localStorage.getItem('token');
  axios.get('http://localhost:8095/vacaciones/obtenerTodos', {
    headers: { Authorization: `Bearer ${token}` }
  })
    .then(res => {
      setVacaciones(res.data);
      // No necesitamos cargar empleados por separado
    })
    .catch(err => console.error("Error:", err));
  };

  const eliminarVacacion = (id) => {
    if (!window.confirm("¿Marcar como eliminadas/no permitidas estas vacaciones?")) return;
    axios.patch(`http://localhost:8095/vacaciones/eliminar/${id}`, null, {
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
          id_empleado: '',
          fecha_inicio: '',
          fecha_fin: '',
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
    axios.put(`http://localhost:8095/vacaciones/actualizar/${editandoVacacion.id_vacacion}`, editandoVacacion, {
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
    axios.get(`http://localhost:8095/vacaciones/obtenerPorId/${idBuscar}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setVacaciones([res.data]))
      .catch(err => {
        console.error("Error:", err);
        alert("No se encontró la vacación.");
      });
  };

  const abrirModalEditar = (vacacion) => {
    setEditandoVacacion(vacacion);
    setMostrarModal(true);
  };

  const generarConstancia = (vacacion) => {
    setVacacionSeleccionada(vacacion);
    setMostrarConstancia(true);
  };

  // Funciones auxiliares para formato
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return `${date.getDate()} Día ${date.toLocaleString('es-ES', { month: 'short' })} ${date.getFullYear()} Año`;
  };

  const formatReincorporacionDate = (dateString) => {
    const date = new Date(dateString);
    date.setDate(date.getDate() + 1); // Sumar un día para la reincorporación
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

      {mostrarModal && (
        <div className="modal">
          <div className="modal-contenido">
            <h3>{editandoVacacion ? 'Editar Vacación' : 'Registrar Vacación'}</h3>
            
            <input
              type="number"
              placeholder="ID Empleado"
              value={editandoVacacion?.id_empleado || nuevaVacacion.id_empleado}
              onChange={e => {
                const val = e.target.value;
                editandoVacacion
                  ? setEditandoVacacion({ ...editandoVacacion, id_empleado: val })
                  : setNuevaVacacion({ ...nuevaVacacion, id_empleado: val });
              }}
            />
            
            <input
              type="date"
              value={editandoVacacion?.fecha_inicio || nuevaVacacion.fecha_inicio}
              onChange={e => {
                const val = e.target.value;
                editandoVacacion
                  ? setEditandoVacacion({ ...editandoVacacion, fecha_inicio: val })
                  : setNuevaVacacion({ ...nuevaVacacion, fecha_inicio: val });
              }}
            />
            <input
              type="date"
              value={editandoVacacion?.fecha_fin || nuevaVacacion.fecha_fin}
              onChange={e => {
                const val = e.target.value;
                editandoVacacion
                  ? setEditandoVacacion({ ...editandoVacacion, fecha_fin: val })
                  : setNuevaVacacion({ ...nuevaVacacion, fecha_fin: val });
              }}
            />
            <input
              type="number"
              placeholder="Días disfrutados"
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
                  <td>{obtenerNombreEmpleado(vacacionSeleccionada.id_empleado)}</td>
                  <td>{obtenerPuestoEmpleado(vacacionSeleccionada.id_empleado)}</td>
                </tr>
              </tbody>
            </table>
            
            <p>{obtenerPuestoEmpleado(vacacionSeleccionada.id_empleado)}</p>
            <p>Centro o Unidad Administrativa/Operativa donde presta servicios el empleado</p>
            
            <p><strong>Por este medio solicito me sea autorizado el goce de vacaciones, que solicito a partir del</strong></p>
            
            <p>1. <strong>día(s) hábil(es) del año a cuenta de</strong></p>
            
            <div className="fecha-vacaciones">
              <div className="fecha-item">
                <p>{new Date(vacacionSeleccionada.fecha_inicio).getDate()} Día</p>
                <p>{new Date(vacacionSeleccionada.fecha_inicio).toLocaleString('es-ES', { month: 'short' })}</p>
                <p>{new Date(vacacionSeleccionada.fecha_inicio).getFullYear()} Año</p>
              </div>
              <div className="texto-centrado">al</div>
              <div className="fecha-item">
                <p>{new Date(vacacionSeleccionada.fecha_fin).getDate()} Día</p>
                <p>{new Date(vacacionSeleccionada.fecha_fin).toLocaleString('es-ES', { month: 'short' })}</p>
                <p>{new Date(vacacionSeleccionada.fecha_fin).getFullYear()} Año</p>
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
            
            <div className="linea-divisoria"></div>
            
            <div className="uso-exclusivo">
              <h2>PARA USO EXCLUSIVO DE CONTROL DE PERSONAL</h2>
              
              <table>
                <tbody>
                  <tr>
                    <td>Días otorgados a la fecha</td>
                    <td>{vacacionSeleccionada.dias}</td>
                    <td>Formulario No.</td>
                    <td>{vacacionSeleccionada.id_vacacion}</td>
                  </tr>
                  <tr>
                    <td colSpan="4">
                      Correspondientes al año laborado del: {formatDate(vacacionSeleccionada.fecha_inicio)} al {formatDate(vacacionSeleccionada.fecha_fin)}
                    </td>
                  </tr>
                  <tr>
                    <td colSpan="4">
                      Se disfrutarán: {vacacionSeleccionada.dias} día(s) de vacaciones, a partir del: {formatDate(vacacionSeleccionada.fecha_inicio)} al {formatDate(vacacionSeleccionada.fecha_fin)}
                    </td>
                  </tr>
                </tbody>
              </table>
              
              <div className="fecha-reincorporacion">
                <p>Se presenta a sus labores nuevamente el día: {formatReincorporacionDate(vacacionSeleccionada.fecha_fin)}</p>
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
            
            <div className="linea-divisoria"></div>
            
            <div className="notas">
              <ol>
                <li>El servidor no podrá disfrutar vacaciones, sin previa notificación de autorización.</li>
                <li>El Periodo de vacaciones deberá ser disfrutado en las fechas autorizadas para el efecto.</li>
                <li>Cualquier modificación será resuelto por la Gerencia Administrativa.</li>
                <li>En obligación del servidor darle copia de la notificación de vacaciones a su Jefe inmediato para su conocimiento.</li>
              </ol>
            </div>
            
            <div className="linea-divisoria"></div>
            
            <div className="vo-bo">
              <p>Cálculos efectuados por:</p>
              <p>Vo.Bo. Gerencia Administrativa</p>
            </div>
            
            <div className="modal-botones">
              <button onClick={() => setMostrarConstancia(false)}>Cerrar</button>
            </div>
          </div>
        </div>
      )}

      <table className="tabla-puestos">
        <thead>
          <tr>
            <th>Acción</th>
            <th>ID Vacacion</th>
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
            <tr key={v.id_vacacion}>
              <td>
                <button className="btn-editar" onClick={() => abrirModalEditar(v)}>
                  <FontAwesomeIcon icon={faPen} />
                </button>
                <button className="btn-eliminar" onClick={() => eliminarVacacion(v.id_vacacion)}>
                  <FontAwesomeIcon icon={faTrash} />
                </button>
                <button className="btn-constancia" onClick={() => generarConstancia(v)}>
                  <FontAwesomeIcon icon={faFileAlt} />
                </button>
              </td>
              <td>{v.id_vacacion}</td>
              <td>{v.id_empleado}</td>
              <td>{obtenerNombreEmpleado(v.id_empleado)}</td>
              <td>{v.fecha_inicio.split('T')[0]}</td>
              <td>{v.fecha_fin.split('T')[0]}</td>
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