import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './PeriodoNomina.css';

export default function PeriodosNomina() {
  const [visible, setVisible] = useState(true);
  const [minimizado, setMinimizado] = useState(false);
  const [maximizado, setMaximizado] = useState(false);
  const [mostrarNuevaVentana, setMostrarNuevaVentana] = useState(false);

  const [clave, setClave] = useState('');
  const [tipo, setTipo] = useState('');
  const [config, setConfig] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [fechaIni, setFechaIni] = useState('');
  const [fechaFin, setFechaFin] = useState('');
  const [dias, setDias] = useState('');
  const [periodos, setPeriodos] = useState([]);
  const [editandoId, setEditandoId] = useState(null);

  const camposCompletos = clave && tipo && config && descripcion && fechaIni && fechaFin && dias;

  const headers = {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
    }
  };

  const obtenerPeriodos = async () => {
    try {
      const res = await axios.get('http://localhost:8095/periodo/obtenerTodos', headers);
      setPeriodos(res.data);
    } catch (err) {
      console.error("Error al obtener períodos:", err);
    }
  };

  const limpiarCampos = () => {
    setClave('');
    setTipo('');
    setConfig('');
    setDescripcion('');
    setFechaIni('');
    setFechaFin('');
    setDias('');
    setEditandoId(null);
  };

  const handleCrear = async () => {
    if (camposCompletos) {
      try {
        const nuevoPeriodo = {
          tipo_periodo: tipo,
          descripcion: descripcion,
          fecha_inicio: fechaIni,
          fecha_fin: fechaFin,
          dias_a_pagar: parseInt(dias),
          numero_pago: clave,
          codigo_pago: config,
          estado: "Activo"
        };

        await axios.post('http://localhost:8095/periodo/crear', nuevoPeriodo, headers);
        alert("Periodo creado correctamente.");
        setMostrarNuevaVentana(true);
        obtenerPeriodos();
        limpiarCampos();
      } catch (error) {
        console.error("Error al crear el periodo:", error);
        alert("Error al crear el período.");
      }
    }
  };

  const handleEditar = (periodo) => {
    setEditandoId(periodo.id_periodo);
    setClave(periodo.numero_pago);
    setTipo(periodo.tipo_periodo);
    setConfig(periodo.codigo_pago);
    setDescripcion(periodo.descripcion);
    setFechaIni(periodo.fecha_inicio);
    setFechaFin(periodo.fecha_fin);
    setDias(periodo.dias_a_pagar);
    setVisible(true);
    setMaximizado(true);
    setMinimizado(false);
  };

  const handleActualizar = async () => {
    if (editandoId && camposCompletos) {
      try {
        const actualizado = {
          id_periodo: editandoId,
          tipo_periodo: tipo,
          descripcion: descripcion,
          fecha_inicio: fechaIni,
          fecha_fin: fechaFin,
          dias_a_pagar: parseInt(dias),
          numero_pago: clave,
          codigo_pago: config,
          estado: "Activo"
        };

        await axios.put(`http://localhost:8095/periodo/actualizar/${editandoId}`, actualizado, headers);
        alert("Periodo actualizado correctamente.");
        obtenerPeriodos();
        limpiarCampos();
        setMostrarNuevaVentana(false);
      } catch (error) {
        console.error("Error al actualizar:", error);
        alert("Error al actualizar el período.");
      }
    }
  };

  const handleCerrar = async (id) => {
    try {
      await axios.patch(`http://localhost:8095/periodo/cerrar/${id}`, null, headers);
      alert("Periodo cerrado correctamente.");
      obtenerPeriodos();
    } catch (error) {
      console.error("Error al cerrar:", error);
      alert("Error al cerrar el período.");
    }
  };

  useEffect(() => {
    obtenerPeriodos();
  }, []);

  if (!visible) return null;

  return (
    <>
      <div className={`ventana ${maximizado ? 'maximizada' : ''}`}>
        <div className="header">
          <span>Períodos de nómina</span>
          <div className="acciones">
            <button onClick={() => setMinimizado(!minimizado)} title="Minimizar">🗕</button>
            <button onClick={() => setMaximizado(!maximizado)} title="Maximizar / Restaurar">
              {maximizado ? '🗗' : '🗖'}
            </button>
            <button onClick={() => setVisible(false)} title="Cerrar">✖</button>
          </div>
        </div>

        {!minimizado && (
          <>
            <div className="barra-superiorb">
              <button
                className="boton-nuevo"
                title={editandoId ? "Actualizar periodo" : "Crear nueva nómina"}
                onClick={editandoId ? handleActualizar : handleCrear}
                disabled={!camposCompletos}
                style={{ opacity: camposCompletos ? 1 : 0.5 }}
              >
                {editandoId ? '💾' : '📁'}
              </button>
            </div>

            <div className="formulario">
              <div className="campo"><label>Clave:</label><input value={clave} onChange={e => setClave(e.target.value)} type="text" /></div>
              <div className="campo"><label>Tipo periodo:</label><input value={tipo} onChange={e => setTipo(e.target.value)} type="text" /></div>
              <div className="campo"><label>Configuración:</label><input value={config} onChange={e => setConfig(e.target.value)} type="text" /></div>
              <div className="campo descripcion"><label>Descripción:</label><input value={descripcion} onChange={e => setDescripcion(e.target.value)} type="text" /></div>
              <div className="campo"><label>Fecha inicial:</label><input value={fechaIni} onChange={e => setFechaIni(e.target.value)} type="date" /></div>
              <div className="campo"><label>Fecha final:</label><input value={fechaFin} onChange={e => setFechaFin(e.target.value)} type="date" /></div>
              <div className="campo"><label>Días a pagar:</label><input value={dias} onChange={e => setDias(e.target.value)} type="number" /></div>
            </div>

            <div className="lista-periodos">
              <h3>Períodos existentes</h3>
              <ul>
                {periodos.map((p) => (
                  <li key={p.id_periodo}>
                    <strong>{p.tipo_periodo}</strong> | {p.fecha_inicio} - {p.fecha_fin} | {p.dias_a_pagar} días
                    <button onClick={() => handleEditar(p)} title="Editar">✏️</button>
                    <button onClick={() => handleCerrar(p.id_periodo)} title="Cerrar">🔒</button>
                  </li>
                ))}
              </ul>
            </div>
          </>
        )}
      </div>

      {mostrarNuevaVentana && (
        <div className="ventana nueva-ventana">
          <div className="header">
            <span>{editandoId ? "Editar Nómina" : "Nueva Nómina"}</span>
            <div className="acciones">
              <button onClick={() => setMostrarNuevaVentana(false)} title="Cerrar">✖</button>
            </div>
          </div>
          <div className="formulario">
            <p><strong>Clave:</strong> {clave}</p>
            <p><strong>Tipo periodo:</strong> {tipo}</p>
            <p><strong>Configuración:</strong> {config}</p>
            <p><strong>Descripción:</strong> {descripcion}</p>
            <p><strong>Fecha inicial:</strong> {fechaIni}</p>
            <p><strong>Fecha final:</strong> {fechaFin}</p>
            <p><strong>Días a pagar:</strong> {dias}</p>
          </div>
        </div>
      )}
    </>
  );
}
