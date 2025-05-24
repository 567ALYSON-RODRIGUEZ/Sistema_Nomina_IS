import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Roles.css'; // Puedes cambiar el nombre si lo deseas

function RolesPanel() {
  const [roles, setRoles] = useState([]);
  const [nuevoRol, setNuevoRol] = useState({ nombre_rol: '', descripcion: '' });
  const [idBuscar, setIdBuscar] = useState('');

  useEffect(() => {
    cargarTodos();
  }, []);

  const cargarTodos = () => {
    const token = localStorage.getItem('token');

    axios.get('http://localhost:8095/rol/obtenerTodos',{
      headers: {
      Authorization: `Bearer ${token}`
    }
  })
      .then(res => setRoles(res.data))
      .catch(err => console.error("Error al cargar roles:", err));
  };

  const crearRol = () => {
    axios.post('http://localhost:8095/rol/crear', nuevoRol,{
      headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
    }
  })
      .then(() => {
        alert("Rol creado correctamente.");
        setNuevoRol({ nombre_rol: '', descripcion: '' });
        cargarTodos();
      })
      .catch(err => {
        console.error("Error al crear rol:", err);
        alert("No se pudo crear el rol.");
      });
  };

  const buscarPorId = () => {
    axios.get(`http://localhost:8095/rol/obtenerPorId/${idBuscar}`,{
      headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
    }
  })
      .then(res => setRoles([res.data]))
      .catch(err => {
        console.error("Error al buscar rol:", err);
        alert("Rol no encontrado.");
      });
  };

  return (
    <div className="contenedor-panel">
      <div className="barra-superiorr">
        <h2>Gestión de Roles</h2>
        <div className="acciones-barra">
          <input
            type="text"
            value={idBuscar}
            onChange={e => setIdBuscar(e.target.value)}
            placeholder="Buscar rol por ID"
          />
          <button className="btn-buscar" onClick={buscarPorId}>Buscar</button>
          <button className="btn-buscar" onClick={cargarTodos}>Regresar</button>
        </div>
      </div>

      <div className="modal-contenido">
        <h3>Crear Rol</h3>
        <input
          type="text"
          placeholder="Nombre del Rol"
          value={nuevoRol.nombre_rol}
          onChange={e => setNuevoRol({ ...nuevoRol, nombre_rol: e.target.value })}
        />
        <input
          type="text"
          placeholder="Descripción"
          value={nuevoRol.descripcion}
          onChange={e => setNuevoRol({ ...nuevoRol, descripcion: e.target.value })}
        />
        <div className="modal-botones">
          <button onClick={crearRol} className="btn-guardar">Guardar</button>
          <button onClick={() => setNuevoRol({ nombre_rol: '', descripcion: '' })} className="btn-cancelar">Cancelar</button>
        </div>
      </div>

      <table className="tabla-puestos">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre del Rol</th>
            <th>Descripción</th>
          </tr>
        </thead>
        <tbody>
          {roles.map((rol) => (
            <tr key={rol.id_rol}>
              <td>{rol.id_rol}</td>
              <td>{rol.nombre_rol}</td>
              <td>{rol.descripcion}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default RolesPanel;
