import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './EmpleadoCrud.css'; 
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faTrash } from '@fortawesome/free-solid-svg-icons';

function EmpleadoCrud() {
  const [empleados, setEmpleados] = useState([]);
  const [nuevoEmpleado, setNuevoEmpleado] = useState({
    nombres: '',
    dpi: '',
    nit: '',
    fechaIngreso: '',
    fechaEgreso: '',
    estado: 'Activo',
    tipoPago: 'Mensual',
    tipoContrato: 'Indefinido',
    idPuesto: '',
    idDepartamento: ''
  });
  const [editandoEmpleado, setEditandoEmpleado] = useState(null);
  const [mostrarModal, setMostrarModal] = useState(false);
  const [idBuscar, setIdBuscar] = useState('');

  useEffect(() => {
    cargarEmpleados();
  }, []);

  const cargarEmpleados = () => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8095/empleado/obtenerTodos',{
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    .then(res => setEmpleados(res.data))
    .catch(err => console.error("Error al cargar empleados:", err));
  };
  

  const crearEmpleado = () => {
    axios.post('http://localhost:8095/empleado/crear', nuevoEmpleado, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
      .then(() => {
        alert("Empleado creado exitosamente.");
        setMostrarModal(false);
        cargarEmpleados();
        setNuevoEmpleado({ nombres: '', dpi: '', nit: '', fechaIngreso: '', fechaEgreso: '', estado: 'Activo', tipoPago: 'Mensual', tipoContrato: 'Indefinido', idPuesto: '', idDepartamento: '' });
      })
      .catch(err => {
        console.error("Error al crear empleado:", err);
        alert("Error al crear empleado.");
      });
  };

  const actualizarEmpleado = () => {
    axios.put(`http://localhost:8095/empleado/reemplazar/${editandoEmpleado.idEmpleado}`, editandoEmpleado, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
      .then(() => {
        alert("Empleado actualizado correctamente.");
        setMostrarModal(false);
        setEditandoEmpleado(null);
        cargarEmpleados();
      })
      .catch(err => {
        console.error("Error al actualizar:", err);
        alert("No se pudo actualizar el empleado.");
      });
  };

  const eliminarEmpleado = (id) => {
    const confirmar = window.confirm("¿Estás seguro de eliminar este empleado?");
    if (!confirmar) return;

    axios.delete(`http://localhost:8095/empleado/eliminar/${id}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
      .then(() => {
        alert("Empleado eliminado.");
        setEmpleados(empleados.filter(e => e.idEmpleado !== id));
      })
      .catch(err => {
        console.error("Error al eliminar:", err);
        alert("No se pudo eliminar el empleado.");
      });
  };

  const buscarPorId = () => {
    axios.get(`http://localhost:8095/empleado/obtenerPorId/${idBuscar}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
      .then(res => setEmpleados([res.data]))
      .catch(err => {
        console.error("Empleado no encontrado:", err);
        alert("Empleado no encontrado.");
      });
  };

  const abrirModal = (empleado = null) => {
    setEditandoEmpleado(empleado);
    setMostrarModal(true);
  };

  const handleChange = (e, esEdicion = false) => {
    const { name, value } = e.target;
    if (esEdicion) {
      setEditandoEmpleado(prev => ({ ...prev, [name]: value }));
    } else {
      setNuevoEmpleado(prev => ({ ...prev, [name]: value }));
    }
  };

  return (
    <div className="contenedor-panel">
      <div className="barra-superiorec">
        <h2>Gestión de Empleados</h2>
        <div className="acciones-barra">
          <button className="btn-nuevo" onClick={() => abrirModal()}>+ Nuevo</button>
          <input type="text" value={idBuscar} onChange={e => setIdBuscar(e.target.value)} placeholder="Buscar por ID" />
          <button className="btn-buscar" onClick={buscarPorId}>Buscar</button>
          <button className="btn-buscar" onClick={cargarEmpleados}>Regresar</button>
        </div>
      </div>

      {mostrarModal && (
        <div className="modal">
          <div className="modal-contenido">
            <h3>{editandoEmpleado ? 'Editar Empleado' : 'Nuevo Empleado'}</h3>
            {['nombres', 'dpi', 'nit', 'fechaIngreso', 'fechaEgreso', 'idPuesto', 'idDepartamento'].map(campo => (
              <input key={campo} type={campo.includes('fecha') ? 'date' : 'text'} name={campo}
                placeholder={campo}
                value={editandoEmpleado ? editandoEmpleado[campo] : nuevoEmpleado[campo]}
                onChange={e => handleChange(e, !!editandoEmpleado)}
              />
            ))}
            <select name="estado" value={editandoEmpleado ? editandoEmpleado.estado : nuevoEmpleado.estado} onChange={e => handleChange(e, !!editandoEmpleado)}>
              <option value="Activo">Activo</option>
              <option value="Inactivo">Inactivo</option>
              <option value="Suspendido">Suspendido</option>
              <option value="Despedido">Despedido</option>
              <option value="Renunciado">Renunciado</option>
            </select>
            <select name="tipoPago" value={editandoEmpleado ? editandoEmpleado.tipoPago : nuevoEmpleado.tipoPago} onChange={e => handleChange(e, !!editandoEmpleado)}>
              <option value="Semanal">Semanal</option>
              <option value="Quincenal">Quincenal</option>
              <option value="Mensual">Mensual</option>
            </select>
            <select name="tipoContrato" value={editandoEmpleado ? editandoEmpleado.tipoContrato : nuevoEmpleado.tipoContrato} onChange={e => handleChange(e, !!editandoEmpleado)}>
              <option value="Indefinido">Indefinido</option>
              <option value="Temporal">Temporal</option>
              <option value="Servicios">Servicios</option>
              <option value="Honorarios">Honorarios</option>
              <option value="Prueba">Prueba</option>
            </select>
            <div className="modal-botones">
              <button onClick={editandoEmpleado ? actualizarEmpleado : crearEmpleado}>Guardar</button>
              <button onClick={() => { setMostrarModal(false); setEditandoEmpleado(null); }}>Cancelar</button>
            </div>
          </div>
        </div>
      )}

      <table className="tabla-empleadosc">
        <thead>
          <tr>
            <th>Acción</th>
            <th>ID</th>
            <th>Nombres</th>
            <th>DPI</th>
            <th>NIT</th>
            <th>Ingreso</th>
            <th>Egreso</th>
            <th>Estado</th>
            <th>Pago</th>
            <th>Contrato</th>
            <th>Puesto</th>
            <th>Departamento</th>
          </tr>
        </thead>
        <tbody>
          {empleados.map(emp => (
            <tr key={emp.idEmpleado}>
              <td>
                <button className='btn-editar' onClick={() => abrirModal(emp)}><FontAwesomeIcon icon={faPen} /></button>
                <button className='btn-eliminar' onClick={() => eliminarEmpleado(emp.idEmpleado)}><FontAwesomeIcon icon={faTrash} /></button>
              </td>
              <td>{emp.idEmpleado}</td>
              <td>{emp.nombres}</td>
              <td>{emp.dpi}</td>
              <td>{emp.nit}</td>
              <td>{emp.fechaIngreso}</td>
              <td>{emp.fechaEgreso}</td>
              <td>{emp.estado}</td>
              <td>{emp.tipoPago}</td>
              <td>{emp.tipoContrato}</td>
              <td>{emp.idPuesto}</td>
              <td>{emp.idDepartamento}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default EmpleadoCrud;
