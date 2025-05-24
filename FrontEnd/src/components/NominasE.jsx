import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './NominasE.css'

function NominasE() {
  const [nominas, setNominas] = useState([]);
  const [mostrarModal, setMostrarModal] = useState(false);
  const [nuevaNomina, setNuevaNomina] = useState({
    tipo: 'Semanal',
    periodo_inicio: '',
    periodo_fin: '',
    fecha_generacion: '',
    estado: 'Generada',
    id_periodo: ''
  });
  const [editandoNomina, setEditandoNomina] = useState(null);

  useEffect(() => {
    cargarNominas();
  }, []);

  const cargarNominas = () => {
    axios.get('http://localhost:8095/nomina/obtenerTodos')
      .then(res => setNominas(res.data))
      .catch(err => console.error("Error al cargar nóminas:", err));
  };

  const crearNomina = () => {
    axios.post('http://localhost:8095/nomina/crear', nuevaNomina)
      .then(() => {
        alert("Nómina creada.");
        cerrarModal();
        cargarNominas();
      })
      .catch(() => alert("Error al crear nómina."));
  };

  const actualizarNomina = () => {
    axios.put(`http://localhost:8095/nomina/actualizar/${editandoNomina.id_nomina}`, editandoNomina)
      .then(() => {
        alert("Nómina actualizada.");
        cerrarModal();
        cargarNominas();
      })
      .catch(() => alert("Error al actualizar nómina."));
  };

  const eliminarNomina = (id) => {
    if(window.confirm("¿Seguro que deseas eliminar esta nómina?")) {
      axios.delete(`http://localhost:8095/nomina/eliminar/${id}`)
        .then(() => {
          alert("Nómina eliminada.");
          cargarNominas();
        })
        .catch(() => alert("Error al eliminar nómina."));
    }
  };

  const abrirModalEditar = (n) => {
    setEditandoNomina(n);
    setMostrarModal(true);
  };

  const cerrarModal = () => {
    setMostrarModal(false);
    setEditandoNomina(null);
    setNuevaNomina({
      tipo: 'Semanal',
      periodo_inicio: '',
      periodo_fin: '',
      fecha_generacion: '',
      estado: 'Generada',
      id_periodo: ''
    });
  };

  return (
    <div className="contenedor-panel">
      <div className="barra-superior-nominas">
        <h2>Gestión de Nóminas</h2>
        <button 
          className="btn-nueva-nomina" 
          onClick={() => { setEditandoNomina(null); setMostrarModal(true); }}
        >
          + Nueva Nómina
        </button>
      </div>

      {mostrarModal && (
        <div className="modal">
          <div className="modal-contenido">
            <h3>{editandoNomina ? 'Editar Nómina' : 'Crear Nómina'}</h3>
            <select
              value={editandoNomina ? editandoNomina.tipo : nuevaNomina.tipo}
              onChange={e =>
                editandoNomina
                  ? setEditandoNomina({ ...editandoNomina, tipo: e.target.value })
                  : setNuevaNomina({ ...nuevaNomina, tipo: e.target.value })
              }
            >
              <option value="Semanal">Semanal</option>
              <option value="Quincenal">Quincenal</option>
              <option value="Mensual">Mensual</option>
            </select>
            <input
              type="date"
              value={editandoNomina ? editandoNomina.periodo_inicio : nuevaNomina.periodo_inicio}
              onChange={e =>
                editandoNomina
                  ? setEditandoNomina({ ...editandoNomina, periodo_inicio: e.target.value })
                  : setNuevaNomina({ ...nuevaNomina, periodo_inicio: e.target.value })
              }
            />
            <input
              type="date"
              value={editandoNomina ? editandoNomina.periodo_fin : nuevaNomina.periodo_fin}
              onChange={e =>
                editandoNomina
                  ? setEditandoNomina({ ...editandoNomina, periodo_fin: e.target.value })
                  : setNuevaNomina({ ...nuevaNomina, periodo_fin: e.target.value })
              }
            />
            <input
              type="date"
              value={editandoNomina ? editandoNomina.fecha_generacion : nuevaNomina.fecha_generacion}
              onChange={e =>
                editandoNomina
                  ? setEditandoNomina({ ...editandoNomina, fecha_generacion: e.target.value })
                  : setNuevaNomina({ ...nuevaNomina, fecha_generacion: e.target.value })
              }
            />
            <select
              value={editandoNomina ? editandoNomina.estado : nuevaNomina.estado}
              onChange={e =>
                editandoNomina
                  ? setEditandoNomina({ ...editandoNomina, estado: e.target.value })
                  : setNuevaNomina({ ...nuevaNomina, estado: e.target.value })
              }
            >
              <option value="Generada">Generada</option>
              <option value="Pagada">Pagada</option>
              <option value="Anulada">Anulada</option>
            </select>
            <input
              type="number"
              placeholder="ID Periodo"
              value={editandoNomina ? editandoNomina.id_periodo || '' : nuevaNomina.id_periodo}
              onChange={e =>
                editandoNomina
                  ? setEditandoNomina({ ...editandoNomina, id_periodo: e.target.value })
                  : setNuevaNomina({ ...nuevaNomina, id_periodo: e.target.value })
              }
            />
            <div className="modal-botones">
              <button onClick={editandoNomina ? actualizarNomina : crearNomina}>Guardar</button>
              <button onClick={cerrarModal}>Cancelar</button>
            </div>
          </div>
        </div>
      )}

      <table className="tabla-nominas">
        <thead>
          <tr>
            <th>ID</th>
            <th>Tipo</th>
            <th>Inicio</th>
            <th>Fin</th>
            <th>Generación</th>
            <th>Estado</th>
            <th>ID Periodo</th>
            <th>Acción</th>
          </tr>
        </thead>
        <tbody>
          {nominas.map(n => (
            <tr key={n.id_nomina}>
              <td>{n.id_nomina}</td>
              <td>{n.tipo}</td>
              <td>{n.periodo_inicio}</td>
              <td>{n.periodo_fin}</td>
              <td>{n.fecha_generacion}</td>
              <td>{n.estado}</td>
              <td>{n.id_periodo}</td>
              <td>
                <button 
                  className="btn-editar" 
                  onClick={() => abrirModalEditar(n)}
                >
                  Editar
                </button>
                <button 
                  className="btn-eliminar" 
                  onClick={() => eliminarNomina(n.id_nomina)}
                >
                  Eliminar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default NominasE;
