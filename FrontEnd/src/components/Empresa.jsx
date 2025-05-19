import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Empresa.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faTrash } from '@fortawesome/free-solid-svg-icons';

function EmpresasPanel() {
  const [empresas, setEmpresas] = useState([]);
  const [nuevaEmpresa, setNuevaEmpresa] = useState({
    nombreLegal: '',
    sigla: '',
    nit: '',
    domicilioFiscal: '',
    correo: '',
    telefono: '',
    estado: 'Activo'
  });
  const [mostrarModal, setMostrarModal] = useState(false);
  const [editandoEmpresa, setEditandoEmpresa] = useState(null);
  const [idBuscar, setIdBuscar] = useState('');

  useEffect(() => {
    cargarTodas();
  }, []);

  const cargarTodas = () => {
    const token = localStorage.getItem('token');

    axios.get('http://localhost:8095/empresa/obtenerTodos', {
      headers: {
      Authorization: `Bearer ${token}`
      }
    })
      .then(res => setEmpresas(res.data))
      .catch(err => console.error("Error al cargar empresas:", err));
  };

  const eliminarEmpresa = (id) => {
    const confirmacion = window.confirm("¿Estás seguro de que deseas eliminar esta empresa?");
    if (!confirmacion) return;

    axios.delete(`http://localhost:8095/empresa/eliminar/${id}`,{
      headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
      .then(() => {
        alert("Empresa eliminada correctamente.");
        setEmpresas(empresas.filter(emp => emp.idEmpresa !== id));
      })
      .catch(err => {
        console.error("Error al eliminar:", err);
        alert("Error al eliminar la empresa.");
      });
  };

  const crearEmpresa = () => {
    axios.post('http://localhost:8095/empresa/crear', nuevaEmpresa, {
      headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
    }
  })
      .then(() => {
        alert("Empresa creada.");
        setMostrarModal(false);
        setNuevaEmpresa({
          nombreLegal: '', sigla: '', nit: '',
          domicilioFiscal: '', correo: '', telefono: '', estado: 'Activo'
        });
        cargarTodas();
      })
      .catch(err => {
        console.error("Error al crear empresa:", err);
        alert("No se pudo crear la empresa.");
      });
  };

  const actualizarEmpresa = () => {
    axios.put(`http://localhost:8095/empresa/reemplazar/${editandoEmpresa.idEmpresa}`, editandoEmpresa,{
      headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
    }
  })
      .then(() => {
        alert("Empresa actualizada.");
        setMostrarModal(false);
        setEditandoEmpresa(null);
        cargarTodas();
      })
      .catch(err => {
        console.error("Error al actualizar empresa:", err);
        alert("No se pudo actualizar la empresa.");
      });
  };

  const buscarEmpresaPorId = () => {
    axios.get(`http://localhost:8095/empresa/obtenerPorId/${idBuscar}`,{
      headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
    }
  })
      .then(res => setEmpresas([res.data]))
      .catch(err => {
        console.error("Error al buscar:", err);
        alert("Empresa no encontrada.");
      });
  };

  const abrirModalEditar = (emp) => {
    setEditandoEmpresa(emp);
    setMostrarModal(true);
  };

  return (
    <div className="contenedor-panel">
      <div className="barra-superiore">
        <h2>Gestión de Empresas</h2>
        <div className="acciones-barra">
          <button className="btn-nuevo" onClick={() => { setEditandoEmpresa(null); setMostrarModal(true); }}>+ Nuevo</button>
          <input
            type="text"
            value={idBuscar}
            onChange={e => setIdBuscar(e.target.value)}
            placeholder="Buscar empresa por ID"
          />
          <button className="btn-buscar" onClick={buscarEmpresaPorId}>Buscar</button>
          <button className="btn-buscar" onClick={cargarTodas}>Regresar</button>
        </div>
      </div>

      {mostrarModal && (
        <div className="modal">
          <div className="modal-contenido">
            <h3>{editandoEmpresa ? 'Editar Empresa' : 'Crear Empresa'}</h3>
            {['nombreLegal', 'sigla', 'nit', 'domicilioFiscal', 'correo', 'telefono'].map(campo => (
              <input
                key={campo}
                type="text"
                placeholder={campo}
                value={editandoEmpresa ? editandoEmpresa[campo] : nuevaEmpresa[campo]}
                onChange={e =>
                  editandoEmpresa
                    ? setEditandoEmpresa({ ...editandoEmpresa, [campo]: e.target.value })
                    : setNuevaEmpresa({ ...nuevaEmpresa, [campo]: e.target.value })
                }
              />
            ))}
            <select
              value={editandoEmpresa ? editandoEmpresa.estado : nuevaEmpresa.estado}
              onChange={e =>
                editandoEmpresa
                  ? setEditandoEmpresa({ ...editandoEmpresa, estado: e.target.value })
                  : setNuevaEmpresa({ ...nuevaEmpresa, estado: e.target.value })
              }
            >
              <option value="Activo">Activo</option>
              <option value="Inactivo">Inactivo</option>
            </select>
            <div className="modal-botones">
              <button onClick={editandoEmpresa ? actualizarEmpresa : crearEmpresa}>Guardar</button>
              <button onClick={() => { setMostrarModal(false); setEditandoEmpresa(null); }}>Cancelar</button>
            </div>
          </div>
        </div>
      )}

      <table className="tabla-empresas">
        <thead>
          <tr>
            <th>Acción</th>
            <th>ID</th>
            <th>Nombre Legal</th>
            <th>Sigla</th>
            <th>NIT</th>
            <th>Domicilio Fiscal</th>
            <th>Correo</th>
            <th>Teléfono</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {empresas.map(emp => (
            <tr key={emp.idEmpresa}>
              <td>
                <button className="btn-editar" onClick={() => abrirModalEditar(emp)}>
                  <FontAwesomeIcon icon={faPen} />
                </button>
                <button className="btn-eliminar" onClick={() => eliminarEmpresa(emp.idEmpresa)}>
                  <FontAwesomeIcon icon={faTrash} />
                </button>
              </td>
              <td>{emp.idEmpresa}</td>
              <td>{emp.nombreLegal}</td>
              <td>{emp.sigla}</td>
              <td>{emp.nit}</td>
              <td>{emp.domicilioFiscal}</td>
              <td>{emp.correo}</td>
              <td>{emp.telefono}</td>
              <td>{emp.estado}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default EmpresasPanel;
