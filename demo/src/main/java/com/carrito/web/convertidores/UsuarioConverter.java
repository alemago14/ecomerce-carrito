package com.carrito.web.convertidores;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.carrito.web.entidades.Usuario;
import com.carrito.web.excepciones.WebException;
import com.carrito.web.modelos.UsuarioModel;
import com.carrito.web.repositorios.UsuarioRepository;

@Component
public class UsuarioConverter extends Convertidor<UsuarioModel, Usuario>{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario modeloToEntidad(UsuarioModel m) throws WebException {
        Usuario usuario = new Usuario();

        try {
            if(m.getId() != null){
                usuario = usuarioRepository.getReferenceById(m.getId());
                return usuario;
            }else{
                BeanUtils.copyProperties(m, usuario);
                return usuario;
            }
        } catch (Exception e) {
            throw new WebException("Error al convertir el modelo a entidad");
        }
    }

    @Override
    public UsuarioModel entidadToModelo(Usuario e) throws WebException {
        UsuarioModel usuarioModel = new UsuarioModel();
        try {
            BeanUtils.copyProperties(e, usuarioModel);
            return usuarioModel;
        } catch (Exception er) {
            throw new WebException("Error al convertir el modelo a entidad");
        }
    }
    
}
