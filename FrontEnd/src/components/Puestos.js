import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Puestos.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faTrash } from '@fortawesome/free-solid-svg-icons';

function PuestosPanel() {
  const [puestos, setPuestos] = useState([]);
  const [nuevoPuesto, setNuevoPuesto] = useState({ nombre: '', salario_base: '', nivel_jerarquico: '', estado_puesto: 'Activo' });
  const [mostrarModal, setMostrarModal] = useState(false);
  const [editandoPuesto, setEditandoPuesto] = useState(null);
  const [idBuscar, setIdBuscar] = useState('');

  useEffect(() => {
    cargarTodos();
  }, []);

  const cargarTodos = () => {
    axios.get('http://192.168.0.23:8095/puesto/obtenerTodos')
      .then(res => setPuestos(res.data))
      .catch(err => console.error("Error de axios:", err));
  };

  const eliminarPuesto = (id) => {
    const confirmacion = window.confirm("¿Estás seguro de que deseas eliminar este puesto?");
    if (!confirmacion) return;

    axios.patch(`http://192.168.0.23:8095/puesto/eliminar/${id}`)
      .then(() => {
        alert("Puesto eliminado correctamente.");
        setPuestos(puestos.filter(p => p.id_puesto !== id));
      })
      .catch(err => {
        console.error("Error al eliminar:", err);
        alert("Error al eliminar el puesto.");
      });
  };

  const crearPuesto = () => {
    axios.post('http://192.168.0.23:8095/puesto/crear', nuevoPuesto)
      .then(() => {
        alert("Puesto creado.");
        setMostrarModal(false);
        setNuevoPuesto({ nombre: '', salario_base: '', nivel_jerarquico: '', estado_puesto: 'Activo' });
        cargarTodos();
      })
      .catch(err => {
        console.error("Error al crear:", err);
        alert("No se pudo crear el puesto.");
      });
  };

  const actualizarPuesto = () => {
    axios.put(`http://192.168.0.23:8095/puesto/actualizar/${editandoPuesto.id_puesto}`, editandoPuesto)
      .then(() => {
        alert("Puesto actualizado correctamente.");
        setMostrarModal(false);
        setEditandoPuesto(null);
        cargarTodos();
      })
      .catch(err => {
        console.error("Error al actualizar:", err);
        alert("No se pudo actualizar el puesto.");
      });
  };

  const buscarPuestoPorId = () => {
    axios.get(`http://192.168.0.23:8095/puesto/obtenerPorId/${idBuscar}`)
      .then(res => setPuestos([res.data]))
      .catch(err => {
        console.error("Error al buscar:", err);
        alert("Puesto no encontrado.");
      });
  };

  const abrirModalEditar = (puesto) => {
    setEditandoPuesto(puesto);
    setMostrarModal(true);
  };

  return (
    <div className="contenedor-panel">
      <div className="barra-superior">
        <h2>Gestión de Puestos</h2>
        <div className="acciones-barra">
          <button className="btn-nuevo" onClick={() => { setEditandoPuesto(null); setMostrarModal(true); }}>+ Nuevo</button>
          <input
            type="text"
            value={idBuscar}
            onChange={e => setIdBuscar(e.target.value)}
            placeholder="Buscar puesto por ID"
          />
          <button className="btn-buscar" onClick={buscarPuestoPorId}>Buscar</button>
          <button className="btn-buscar" onClick={cargarTodos}>Regresar</button>
        </div>
      </div>

      {mostrarModal && (
        <div className="modal">
          <div className="modal-contenido">
            <h3>{editandoPuesto ? 'Editar Puesto' : 'Crear Puesto'}</h3>
            <input type="text" placeholder="Nombre"
              value={editandoPuesto ? editandoPuesto.nombre : nuevoPuesto.nombre}
              onChange={e => editandoPuesto ? setEditandoPuesto({ ...editandoPuesto, nombre: e.target.value }) : setNuevoPuesto({ ...nuevoPuesto, nombre: e.target.value })} />
            <input type="text" placeholder="Salario Base"
              value={editandoPuesto ? editandoPuesto.salario_base : nuevoPuesto.salario_base}
              onChange={e => editandoPuesto ? setEditandoPuesto({ ...editandoPuesto, salario_base: e.target.value }) : setNuevoPuesto({ ...nuevoPuesto, salario_base: e.target.value })} />
            <input type="text" placeholder="Nivel Jerárquico"
              value={editandoPuesto ? editandoPuesto.nivel_jerarquico : nuevoPuesto.nivel_jerarquico}
              onChange={e => editandoPuesto ? setEditandoPuesto({ ...editandoPuesto, nivel_jerarquico: e.target.value }) : setNuevoPuesto({ ...nuevoPuesto, nivel_jerarquico: e.target.value })} />
            <select value={editandoPuesto ? editandoPuesto.estado_puesto : nuevoPuesto.estado_puesto}
              onChange={e => editandoPuesto ? setEditandoPuesto({ ...editandoPuesto, estado_puesto: e.target.value }) : setNuevoPuesto({ ...nuevoPuesto, estado_puesto: e.target.value })}>
              <option value="Activo">Activo</option>
              <option value="Inactivo">Inactivo</option>
            </select>
            <div className="modal-botones">
              <button onClick={editandoPuesto ? actualizarPuesto : crearPuesto}>Guardar</button>
              <button onClick={() => { setMostrarModal(false); setEditandoPuesto(null); }}>Cancelar</button>
            </div>
          </div>
        </div>
      )}

      <table className="tabla-puestos">
        <thead>
          <tr>
            <th>Acción</th>
            <th>ID</th>
            <th>Nombre</th>
            <th>Salario Base</th>
            <th>Nivel Jerárquico</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {puestos.map(puesto => (
            <tr key={puesto.id_puesto}>
              <td>
                <button className="btn-editar" onClick={() => abrirModalEditar(puesto)}>
                  <FontAwesomeIcon icon={faPen} />
                </button>
                <button className="btn-eliminar" onClick={() => eliminarPuesto(puesto.id_puesto)}>
                  <FontAwesomeIcon icon={faTrash} />
                </button>
              </td>
              <td>{puesto.id_puesto}</td>
              <td>{puesto.nombre}</td>
              <td>{puesto.salario_base}</td>
              <td>{puesto.nivel_jerarquico}</td>
              <td>{puesto.estado_puesto}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default PuestosPanel;
