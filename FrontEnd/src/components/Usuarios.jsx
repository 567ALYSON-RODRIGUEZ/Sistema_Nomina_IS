import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Usuarios.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faTrash } from '@fortawesome/free-solid-svg-icons';

function UsuariosPanel() {
  const [usuarios, setUsuarios] = useState([]);
  const [nuevoUsuario, setNuevoUsuario] = useState({
    username: '',
    password_hash: '',
    nombre_completo: '',
    estado: 'Activo'
  });
  const [mostrarModal, setMostrarModal] = useState(false);
  const [editandoUsuario, setEditandoUsuario] = useState(null);
  const [idBuscar, setIdBuscar] = useState('');

  useEffect(() => {
    cargarTodos();
  }, []);

  const cargarTodos = () => {
    const token = localStorage.getItem('token');

    axios.get('http://localhost:8095/usuario/obtenerTodos',{
      headers: {
      Authorization: `Bearer ${token}`
    }
  })
      .then(res => {
        console.log("Respuesta de usuarios:", res.data); 
        setUsuarios(res.data);
      })
      .catch(err => console.error("Error al cargar usuarios:", err));
  };

  const eliminarUsuario = (id) => {
    if (!window.confirm("¿Deseas eliminar este usuario?")) return;

    axios.delete(`http://localhost:8095/usuario/eliminar/${id}`,{
      headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
    }
  })
      .then(() => {
        alert("Usuario eliminado correctamente.");
        setUsuarios(usuarios.filter(u => u.id_usuario !== id));
      })
      .catch(err => {
        console.error("Error al eliminar:", err);
        alert("Error al eliminar usuario.");
      });
  };

  const crearUsuario = () => {
    axios.post('http://localhost:8095/usuario/crear', nuevoUsuario,{
      headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
    }
  })
      .then(() => {
        alert("Usuario creado.");
        setMostrarModal(false);
        setNuevoUsuario({
          username: '', password_hash: '', nombre_completo: '', estado: 'Activo'
        });
        cargarTodos();
      })
      .catch(err => {
        console.error("Error al crear usuario:", err);
        alert("No se pudo crear el usuario.");
      });
  };

  const actualizarUsuario = () => {
    axios.put(`http://localhost:8095/usuario/reemplazar/${editandoUsuario.id_usuario}`, editandoUsuario,{
      headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
    }
  })
      .then(() => {
        alert("Usuario actualizado.");
        setMostrarModal(false);
        setEditandoUsuario(null);
        cargarTodos();
      })
      .catch(err => {
        console.error("Error al actualizar:", err);
        alert("No se pudo actualizar el usuario.");
      });
  };

  const buscarUsuarioPorId = () => {
    axios.get(`http://localhost:8095/usuario/obtenerPorId/${idBuscar}`,{
      headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
    }
  })
      .then(res => setUsuarios([res.data]))
      .catch(err => {
        console.error("Error al buscar:", err);
        alert("Usuario no encontrado.");
      });
  };

  const abrirModalEditar = (u) => {
    setEditandoUsuario(u);
    setMostrarModal(true);
  };

  return (
    <div className="contenedor-panel">
      <div className="barra-superioru">
        <h2>Gestión de Usuarios</h2>
        <div className="acciones-barra">
          <button className="btn-nuevo" onClick={() => { setEditandoUsuario(null); setMostrarModal(true); }}>+ Nuevo</button>
          <input
            type="text"
            value={idBuscar}
            onChange={e => setIdBuscar(e.target.value)}
            placeholder="Buscar usuario por ID"
          />
          <button className="btn-buscar" onClick={buscarUsuarioPorId}>Buscar</button>
          <button className="btn-buscar" onClick={cargarTodos}>Regresar</button>
        </div>
      </div>

      {mostrarModal && (
        <div className="modal">
          <div className="modal-contenido">
            <h3>{editandoUsuario ? 'Editar Usuario' : 'Crear Usuario'}</h3>
            {['username', 'password_hash', 'nombre_completo'].map(campo => (
              <input
                key={campo}
                type={campo === 'password_hash' ? 'password' : 'text'}
                placeholder={campo.replace('_', ' ')}
                value={editandoUsuario ? editandoUsuario[campo] : nuevoUsuario[campo]}
                onChange={e =>
                  editandoUsuario
                    ? setEditandoUsuario({ ...editandoUsuario, [campo]: e.target.value })
                    : setNuevoUsuario({ ...nuevoUsuario, [campo]: e.target.value })
                }
              />
            ))}
            <select
              value={editandoUsuario ? editandoUsuario.estado : nuevoUsuario.estado}
              onChange={e =>
                editandoUsuario
                  ? setEditandoUsuario({ ...editandoUsuario, estado: e.target.value })
                  : setNuevoUsuario({ ...nuevoUsuario, estado: e.target.value })
              }
            >
              <option value="Activo">Activo</option>
              <option value="Inactivo">Inactivo</option>
            </select>
            <div className="modal-botones">
              <button onClick={editandoUsuario ? actualizarUsuario : crearUsuario}>Guardar</button>
              <button onClick={() => { setMostrarModal(false); setEditandoUsuario(null); }}>Cancelar</button>
            </div>
          </div>
        </div>
      )}

      <table className="tabla-usuarios">
        <thead>
          <tr>
            <th>Acción</th>
            <th>ID</th>
            <th>Usuario</th>
            <th>Nombre Completo</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {usuarios.map(u => (
            <tr key={u.id_usuario}>
              <td>
                <button className="btn-editar" onClick={() => abrirModalEditar(u)}>
                  <FontAwesomeIcon icon={faPen} />
                </button>
                <button className="btn-eliminar" onClick={() => eliminarUsuario(u.id_usuario)}>
                  <FontAwesomeIcon icon={faTrash} />
                </button>
              </td>
              <td>{u.idUsuario}</td>
              <td>{u.username}</td>
              <td>{u.nombreCompleto}</td>
              <td>{u.estado}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default UsuariosPanel;
