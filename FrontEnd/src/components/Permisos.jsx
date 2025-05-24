import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Permisos.css'; 

function Permisos() {
  const [relaciones, setRelaciones] = useState([]);
  const [asignacion, setAsignacion] = useState({ idUsuario: '', idRol: '' });
  const [busqueda, setBusqueda] = useState({ idUsuario: '', idRol: '' });

  useEffect(() => {
    cargarRelaciones();
  }, []);

  const cargarRelaciones = () => {
    axios.get('http://localhost:8095/usuarioRol/obtenerTodos', {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setRelaciones(res.data))
      .catch(err => console.error("Error al cargar relaciones:", err));
  };

  const asignarRol = () => {
    axios.post('http://localhost:8095/usuarioRol/asignar', null, {
      params: asignacion,
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Rol asignado correctamente.");
        cargarRelaciones();
      })
      .catch(err => {
        console.error("Error al asignar rol:", err);
        alert("No se pudo asignar el rol.");
      });
  };

  const eliminarRol = () => {
    axios.post('http://localhost:8095/usuarioRol/eliminar', null, {
      params: busqueda,
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(() => {
        alert("Rol eliminado correctamente.");
        cargarRelaciones();
      })
      .catch(err => {
        console.error("Error al eliminar rol:", err);
        alert("No se pudo eliminar la relación.");
      });
  };

  const buscarRelacion = () => {
    axios.get(`http://localhost:8095/usuarioRol/uno/${busqueda.idUsuario}/${busqueda.idRol}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    })
      .then(res => setRelaciones([res.data]))
      .catch(err => {
        console.error("Error al buscar relación:", err);
        alert("Relación no encontrada.");
      });
  };

  return (
    <div className="contenedor-panel">
      <h2>Gestión Rol-Usuario</h2>

      <div className="acciones-barra">
        <input type="text" placeholder="ID Usuario" value={busqueda.idUsuario} onChange={e => setBusqueda({ ...busqueda, idUsuario: e.target.value })} />
        <input type="text" placeholder="ID Rol" value={busqueda.idRol} onChange={e => setBusqueda({ ...busqueda, idRol: e.target.value })} />
        <button onClick={buscarRelacion}>Buscar</button>
        <button onClick={cargarRelaciones}>Regresar</button>
      </div>

      <div className="modal-contenido">
        <h3>Asignar Rol a Usuario</h3>
        <input type="text" placeholder="ID Usuario" value={asignacion.idUsuario} onChange={e => setAsignacion({ ...asignacion, idUsuario: e.target.value })} />
        <input type="text" placeholder="ID Rol" value={asignacion.idRol} onChange={e => setAsignacion({ ...asignacion, idRol: e.target.value })} />
        <div className="modal-botones">
          <button onClick={asignarRol} className="btn-guardar">Asignar</button>
          <button onClick={eliminarRol} className="btn-cancelar">Eliminar</button>
        </div>
      </div>

      <table className="tabla-puestos">
        <thead>
          <tr>
            <th>ID Usuario</th>
            <th>ID Rol</th>
          </tr>
        </thead>
        <tbody>
          {relaciones.map((rel, index) => (
            <tr key={index}>
              <td>{rel.id.id_usuario}</td>
              <td>{rel.id.id_rol}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Permisos;
