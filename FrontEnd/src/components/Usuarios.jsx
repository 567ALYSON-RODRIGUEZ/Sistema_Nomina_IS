import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Usuarios.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faTrash } from '@fortawesome/free-solid-svg-icons';

function UsuariosPanel() {
  const [usuarios, setUsuarios] = useState([]);
  const [nuevoUsuario, setNuevoUsuario] = useState({
    username: '',
    passwordHash: '',
    nombreCompleto: '',
    estado: 'Activo'
  });
  const [mostrarModal, setMostrarModal] = useState(false);
  const [editandoUsuario, setEditandoUsuario] = useState(null);
  const [idBuscar, setIdBuscar] = useState('');

  useEffect(() => {
    cargarTodos();
  }, []);

  const cargarTodos = () => {
  axios.get('http://localhost:8095/usuario/obtenerTodos')
    .then(res => {
      // Excluye usuarios con estado "Eliminado"
      const usuariosValidos = res.data.filter(u => u.estado !== 'Eliminado');
      setUsuarios(usuariosValidos);
    })
    .catch(err => console.error("Error al cargar usuarios:", err));
};

const buscarUsuarioPorId = () => {
  axios.get(`http://localhost:8095/usuario/obtenerPorId/${idBuscar}`)
    .then(res => {
      if (res.data.estado !== 'Eliminado') {
        setUsuarios([res.data]);
      } else {
        alert("Usuario no encontrado o está eliminado.");
        setUsuarios([]);
      }
    })
    .catch(err => {
      console.error("Error al buscar:", err);
      alert("Usuario no encontrado.");
      setUsuarios([]);
    });
};

  const eliminarUsuario = (id) => {
    if (!window.confirm("¿Deseas eliminar este usuario?")) return;

    axios.delete(`http://localhost:8095/usuario/eliminar/${id}`)
      .then(() => {
        alert("Usuario eliminado correctamente.");
        setUsuarios(usuarios.filter(u => u.idUsuario !== id));
      })
      .catch(err => {
        console.error("Error al eliminar:", err);
        alert("Error al eliminar usuario.");
      });
  };

  const crearUsuario = () => {
    axios.post('http://localhost:8095/usuario/crear', nuevoUsuario)
      .then(() => {
        alert("Usuario creado.");
        setMostrarModal(false);
        setNuevoUsuario({
          username: '', passwordHash: '', nombreCompleto: '', estado: 'Activo'
        });
        cargarTodos();
      })
      .catch(err => {
        console.error("Error al crear usuario:", err);
        alert("No se pudo crear el usuario.");
      });
  };

  const actualizarUsuario = () => {
    axios.put(`http://localhost:8095/usuario/reemplazar/${editandoUsuario.idUsuario}`, editandoUsuario)
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
            {['username', 'passwordHash', 'nombreCompleto'].map(campo => (
              <input
                key={campo}
                type={campo === 'passwordHash' ? 'password' : 'text'}
                placeholder={campo.replace(/([A-Z])/g, ' $1')}
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
            <tr key={u.idUsuario}>
              <td>
                <button className="btn-editar" onClick={() => abrirModalEditar(u)}>
                  <FontAwesomeIcon icon={faPen} />
                </button>
                <button className="btn-eliminar" onClick={() => eliminarUsuario(u.idUsuario)}>
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
