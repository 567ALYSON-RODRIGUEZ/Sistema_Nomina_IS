import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './Login.css';

function Login({ setIsAuthenticated, setRole }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const isLoginValid = username.trim() !== '' && password.length >= 6;

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8095/usuario/login', {
                username: username,
                password: password
            });
            console.log("Respuesta del login:", response.data);

            const { token, id_usuario, rol } = response.data;
            localStorage.setItem('token', token);
            localStorage.setItem('userId', id_usuario);
            localStorage.setItem('role', rol);
            //console.log('Token, ID y usuario almacenados:', token, id_usuario, rol);
            //console.log('Rol almacenado en localStorage:', localStorage.getItem('role'));

            setIsAuthenticated(true);
      setRole(rol);

      // Redirección según el rol
      if (rol === 'admin') {
        navigate('/usuario'); 
      } else if (rol === 'rrhh') {
        navigate('/empleado'); 
      } else {
        navigate('/'); 
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Error al iniciar sesión');
    }
  };

    return (
        <form onSubmit={handleLogin}>
            <h1 className="titulo">Iniciar sesión</h1>

            <input
                type="text"
                placeholder="Nombre de usuario"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
            />

            <input
                type="password"
                placeholder="Contraseña"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
            />

            <button type="submit" className="mostrar-boton" disabled={!isLoginValid}>
                Iniciar sesión
            </button>

            {error && <p className="error-message">{error}</p>}
        </form>
    );
}

export default Login;
