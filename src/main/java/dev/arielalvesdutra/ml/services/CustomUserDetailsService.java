package dev.arielalvesdutra.ml.services;

import dev.arielalvesdutra.ml.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// 1 UsuarioService.java
// 2 Usuario.java
// 3 if (usuario.isEmpty())
@Service
public class CustomUserDetailsService  implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioService.buscaPeloLogin(login);

        if (usuario.isEmpty())
            throw new UsernameNotFoundException("Login ou senha inválidos");

        return usuario.get();
    }
}
