import React, { useState } from 'react';
import './PeriodoNomina.css';

export default function PeriodosNomina() {
  const [visible, setVisible] = useState(true);
  const [minimizado, setMinimizado] = useState(false);
  const [maximizado, setMaximizado] = useState(false);
  const [mostrarNuevaVentana, setMostrarNuevaVentana] = useState(false);

  // Estado de los camposo
  const [clave, setClave] = useState('');
  const [tipo, setTipo] = useState('');
  const [config, setConfig] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [fechaIni, setFechaIni] = useState('');
  const [fechaFin, setFechaFin] = useState('');
  const [dias, setDias] = useState('');

  const camposCompletos = clave && tipo && config && descripcion && fechaIni && fechaFin && dias;

  const handleCrear = () => {
    if (camposCompletos) {
      setMostrarNuevaVentana(true);
    }
  };

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
                title="Crear nueva nómina"
                onClick={handleCrear}
                disabled={!camposCompletos}
                style={{ opacity: camposCompletos ? 1 : 0.5 }}
              >
                📁
              </button>
            </div>


            <div className="formulario">
              <div className="campo">
                <label>Clave:</label>
                <input value={clave} onChange={e => setClave(e.target.value)} type="text" />
              </div>
              <div className="campo">
                <label>Tipo periodo:</label>
                <input value={tipo} onChange={e => setTipo(e.target.value)} type="text" />
              </div>
              <div className="campo">
                <label>Configuración:</label>
                <input value={config} onChange={e => setConfig(e.target.value)} type="text" />
              </div>
              <div className="campo descripcion">
                <label>Descripción:</label>
                <input value={descripcion} onChange={e => setDescripcion(e.target.value)} type="text" />
              </div>
              <div className="campo">
                <label>Fecha inicial:</label>
                <input value={fechaIni} onChange={e => setFechaIni(e.target.value)} type="date" />
              </div>
              <div className="campo">
                <label>Fecha final:</label>
                <input value={fechaFin} onChange={e => setFechaFin(e.target.value)} type="date" />
              </div>
              <div className="campo">
                <label>Días a pagar:</label>
                <input value={dias} onChange={e => setDias(e.target.value)} type="number" />
              </div>
            </div>
          </>
            )}
      </div>

     
      {mostrarNuevaVentana && (
        <div className="ventana nueva-ventana">
          <div className="header">
            <span>Nueva Nómina</span>
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
