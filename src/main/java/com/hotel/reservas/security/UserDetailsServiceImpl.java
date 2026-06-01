package com.hotel.reservas.security;

import com.hotel.reservas.model.Usuario;
import com.hotel.reservas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// ENCAPSULAMIENTO: Expone solo el contrato de UserDetailsService,
// ocultando el acceso a UsuarioRepository del resto de la aplicación.
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // ENCAPSULAMIENTO: Authority con prefijo ROLE_ explícito para evitar
        // el doble prefijo ROLE_ROLE_ que produce el método .roles().
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities("ROLE_" + usuario.getRol().name())
                .build();
    }
}
