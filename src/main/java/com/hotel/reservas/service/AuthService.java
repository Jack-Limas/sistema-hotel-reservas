package com.hotel.reservas.service;

import com.hotel.reservas.dto.LoginRequest;
import com.hotel.reservas.dto.LoginResponse;
import com.hotel.reservas.dto.RegisterRequest;
import com.hotel.reservas.model.Huesped;
import com.hotel.reservas.model.Usuario;
import com.hotel.reservas.model.enums.Rol;
import com.hotel.reservas.repository.HuespedRepository;
import com.hotel.reservas.repository.UsuarioRepository;
import com.hotel.reservas.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// MODULARIDAD: AuthService gestiona exclusivamente autenticación y registro,
// delegando la persistencia a los repositorios y la generación del token a JwtUtil.
// ENCAPSULAMIENTO: Las reglas de creación de usuarios y huéspedes están protegidas aquí.
@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HuespedRepository huespedRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol().name());
        return new LoginResponse(token, usuario.getRol().name(), usuario.getUsername());
    }

    // Valida el JWT actual y genera uno nuevo con la misma identidad — sin necesitar contraseña.
    public LoginResponse refreshToken(String token) {
        String username = jwtUtil.extractUsername(token);
        String rol = jwtUtil.extractRol(token);
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String newToken = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol().name());
        return new LoginResponse(newToken, rol, username);
    }

    public LoginResponse registrar(RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El username ya está en uso");
        }

        // ENCAPSULAMIENTO: Solo se crea un Huesped cuando el rol es HUESPED,
        // manteniendo la coherencia del dominio.
        Huesped huesped = null;
        if (Rol.HUESPED.equals(request.getRol())) {
            huesped = new Huesped(
                    request.getNombre(),
                    request.getCorreo(),
                    request.getTelefono(),
                    request.getTipoDocumento(),
                    request.getNumeroDocumento()
            );
            huesped = huespedRepository.save(huesped);
        }

        // ENCAPSULAMIENTO — password nunca se almacena en texto plano
        // BCryptPasswordEncoder.encode() genera hash irreversible
        // La verificación se hace con BCrypt.matches() nunca comparando directo
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(request.getRol());
        usuario.setHuesped(huesped);
        usuarioRepository.save(usuario);

        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol().name());
        return new LoginResponse(token, usuario.getRol().name(), usuario.getUsername());
    }
}
