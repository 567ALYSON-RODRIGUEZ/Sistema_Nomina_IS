package com.api.nomina.controlador;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.api.nomina.datos.DUsuario;
import com.api.nomina.modelo.EUsuario;
import com.api.nomina.repositorio.UsuarioRepositorio;
import com.api.nomina.repositorio.UsuarioRolRepositorio;

import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuario")
public class resUsuario {

    @Autowired
    private DUsuario dusuario;
    
    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @Autowired
    private UsuarioRolRepositorio rolUsuRepo;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> datos) {
        String username = datos.get("username");
        String password = datos.get("password");

        try {
        	Optional<EUsuario> usuarioOpt = usuarioRepo.findByUsername(username);

            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado o inactivo");
            }

            EUsuario usuario = usuarioOpt.get();

            if (!usuario.getPasswordHash().equals(password)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
            }

            String rol = rolUsuRepo.findRolByUsuario(usuario.getIdUsuario());

            String token = Jwts.builder()
            	    .setSubject(username)
            	    .claim("id", usuario.getIdUsuario())
            	    .claim("rol", rol)
            	    .setIssuedAt(new Date())
            	    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
            	    .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
            	    .compact();

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("token", token);
            respuesta.put("rol", rol);
            respuesta.put("id_usuario", usuario.getIdUsuario());

            return ResponseEntity.ok(respuesta);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al iniciar sesión");
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody EUsuario usuario) {
        try {
            dusuario.crearUsuario(
                null,
                usuario.getUsername(),
                usuario.getPasswordHash(),
                usuario.getNombreCompleto(),
                usuario.getEstado()
            );
            return ResponseEntity.ok(usuario);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear usuario");
        }
    }

    @PutMapping("/reemplazar/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Integer id, @RequestBody EUsuario usuario) {
        try {
            if (!id.equals(usuario.getIdUsuario())) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "El ID en la URL no coincide con el del cuerpo."));
            }

            String estado = usuario.getEstado();
            if (estado != null && !estado.equalsIgnoreCase("Activo") && !estado.equalsIgnoreCase("Inactivo")) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Estado inválido."));
            }

            dusuario.actualizarUsuario(
                id,
                usuario.getUsername(),
                usuario.getPasswordHash(),
                usuario.getNombreCompleto(),
                estado
            );

            return ResponseEntity.ok(usuario);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar usuario");
        }
    }

    @PatchMapping("/actualizarParcial/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> campos) {
        try {
            Map<String, Object> actualizados = dusuario.actualizarParcial(id, campos);
            return ResponseEntity.ok(actualizados);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error al actualizar parcialmente."));
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer id) {
        try {
            dusuario.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario.");
        }
    }

    @GetMapping("/obtenerTodos")
    public List<EUsuario> listarUsuarios() {
        try {
            return dusuario.listarUsuarios();
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Integer id) {
        try {
            EUsuario usuario = dusuario.buscarUsuarioPorId(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el usuario.");
        }
    }
}
