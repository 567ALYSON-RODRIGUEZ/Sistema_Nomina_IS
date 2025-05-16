import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Departamentos.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faTrash } from '@fortawesome/free-solid-svg-icons';

function DepartamentosPanel() {
  const [departamentos, setDepartamentos] = useState([]);
  const [nuevoDep, setNuevoDep] = useState({ nombre: '', descripcion: '', estado: 'Activo' });
  const [mostrarModal, setMostrarModal] = useState(false);
  const [editandoDep, setEditandoDep] = useState(null);
  const [idBuscar, setIdBuscar] = useState('');

  useEffect(() => {
    cargarTodos();
  }, []);

  const cargarTodos = () => {
    axios.get('http://192.168.0.23:8095/departamento/obtenerTodos')
      .then(res => setDepartamentos(res.data))
      .catch(err => console.error("Error de axios:", err));
  };

  const eliminarDepartamento = (id) => {
    const confirmacion = window.confirm("¿Estás seguro de que deseas eliminar este departamento?");
    if (!confirmacion) return;

    axios.patch(`http://192.168.0.23:8095/departamento/eliminar/${id}`)
      .then(() => {
        alert("Departamento eliminado correctamente.");
        setDepartamentos(departamentos.filter(dep => dep.id_departamento !== id));
      })
      .catch(err => {
        console.error("Error al eliminar:", err);
        alert("Error al eliminar el departamento.");
      });
  };

  const crearDepartamento = () => {
    axios.post('http://192.168.0.23:8095/departamento/crear', nuevoDep)
      .then(() => {
        alert("Departamento creado.");
        setMostrarModal(false);
        setNuevoDep({ nombre: '', descripcion: '', estado: 'Activo' });
        cargarTodos();
      })
      .catch(err => {
        console.error("Error al crear:", err);
        alert("No se pudo crear el departamento.");
      });
  };

  const actualizarDepartamento = () => {
    axios.put(`http://192.168.0.23:8095/departamento/actualizar/${editandoDep.id_departamento}`, editandoDep)
      .then(() => {
        alert("Departamento actualizado correctamente.");
        setMostrarModal(false);
        setEditandoDep(null);
        cargarTodos();
      })
      .catch(err => {
        console.error("Error al actualizar:", err);
        alert("No se pudo actualizar el departamento.");
      });
  };

  const buscarDepartamentoPorId = () => {
    axios.get(`http://192.168.0.23:8095/departamento/obtenerPorId/${idBuscar}`)
      .then(res => setDepartamentos([res.data]))
      .catch(err => {
        console.error("Error al buscar:", err);
        alert("Departamento no encontrado.");
      });
  };

  const abrirModalEditar = (dep) => {
    setEditandoDep(dep);
    setMostrarModal(true);
  };

  return (
    <div className="contenedor-panel">
      <div className="barra-superior">
        <h2>Gestión de Departamentos</h2>
        <div className="acciones-barra">
          <button className="btn-nuevo" onClick={() => { setEditandoDep(null); setMostrarModal(true); }}>+ Nuevo</button>
          <input
            type="text"
            value={idBuscar}
            onChange={e => setIdBuscar(e.target.value)}
            placeholder="Buscar departamento por ID"
          />
          <button className="btn-buscar" onClick={buscarDepartamentoPorId}>Buscar</button>
          <button className="btn-buscar" onClick={cargarTodos}>Regresar</button>
        </div>
      </div>

      {mostrarModal && (
        <div className="modal">
          <div className="modal-contenido">
            <h3>{editandoDep ? 'Editar Departamento' : 'Crear Departamento'}</h3>
            <input type="text" placeholder="Nombre"
              value={editandoDep ? editandoDep.nombre : nuevoDep.nombre}
              onChange={e => editandoDep ? setEditandoDep({ ...editandoDep, nombre: e.target.value }) : setNuevoDep({ ...nuevoDep, nombre: e.target.value })} />
            <input type="text" placeholder="Descripción"
              value={editandoDep ? editandoDep.descripcion : nuevoDep.descripcion}
              onChange={e => editandoDep ? setEditandoDep({ ...editandoDep, descripcion: e.target.value }) : setNuevoDep({ ...nuevoDep, descripcion: e.target.value })} />
            <select value={editandoDep ? editandoDep.estado : nuevoDep.estado}
              onChange={e => editandoDep ? setEditandoDep({ ...editandoDep, estado: e.target.value }) : setNuevoDep({ ...nuevoDep, estado: e.target.value })}>
              <option value="Activo">Activo</option>
              <option value="Inactivo">Inactivo</option>
            </select>
            <div className="modal-botones">
              <button onClick={editandoDep ? actualizarDepartamento : crearDepartamento}>Guardar</button>
              <button onClick={() => { setMostrarModal(false); setEditandoDep(null); }}>Cancelar</button>
            </div>
          </div>
        </div>
      )}

      <table className="tabla-departamentos">
        <thead>
          <tr>
            <th>Acción</th>
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {departamentos.map(dep => (
            <tr key={dep.id_departamento}>
              <td>
                <button className="btn-editar" onClick={() => abrirModalEditar(dep)}>
                  <FontAwesomeIcon icon={faPen} />
                </button>
                <button className="btn-eliminar" onClick={() => eliminarDepartamento(dep.id_departamento)}>
                  <FontAwesomeIcon icon={faTrash} />
                </button>
              </td>
              <td>{dep.id_departamento}</td>
              <td>{dep.nombre}</td>
              <td>{dep.descripcion}</td>
              <td>{dep.estado}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default DepartamentosPanel;
