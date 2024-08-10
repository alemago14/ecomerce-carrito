package com.carrito.web.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carrito.web.convertidores.UsuarioConverter;
import com.carrito.web.entidades.Usuario;
import com.carrito.web.enumeraciones.Rol;
import com.carrito.web.excepciones.WebException;
import com.carrito.web.modelos.UsuarioModel;
import com.carrito.web.repositorios.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioConverter usuarioConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void validarUsuario(UsuarioModel usuarioModel) throws WebException{
        if(usuarioModel.getUsername() == null || usuarioModel.getUsername().isEmpty()){
            throw new WebException("Debe ingresar un nombre de usuario");
        }

        if(usuarioModel.getPassword() == null || usuarioModel.getPassword().isEmpty()){
            throw new WebException("Debe ingresar una contraseña");
        }

        if(usuarioModel.getConfirmPassword() == null || usuarioModel.getConfirmPassword().isEmpty()){
            throw new WebException("Debe verificar la contraseña");
        }

        if(!usuarioModel.getPassword().equals(usuarioModel.getConfirmPassword())){
            throw new WebException("Las contraseñans deben coincidir");
        }
    }

    @Transactional
    public Usuario guardarUsuario(UsuarioModel usuarioModel) throws WebException{
        Usuario usuario = new Usuario();
        Optional<Usuario> resp = usuarioRepository.findByUsername(usuarioModel.getUsername());
        validarUsuario(usuarioModel);

        if(resp.isPresent()){
            throw new WebException("Ya existe un usuario con ese nombre");
        }

        usuario = usuarioConverter.modeloToEntidad(usuarioModel);
        usuario.setRol(Rol.NORMAL);
        usuario.setPassword(passwordEncoder.encode(usuarioModel.getPassword()));

        usuario = usuarioRepository.save(usuario);

        return usuario;
    }
}
