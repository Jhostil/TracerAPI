package co.edu.uniquindio.Microservicios_API_PF.servicios;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Usuario;
import co.edu.uniquindio.Microservicios_API_PF.excepciones.UsuarioRegistradoException;

import java.util.Optional;

public interface UsuarioServicio {
    Usuario crearUsuario(Usuario usuario) throws UsuarioRegistradoException;
    Optional<Usuario> obtenerUsuarioPorId(String id);
    Optional<Usuario> obtenerUsuarioPorCorreo(String correo);
    void eliminarUsuario(String id);
    Usuario actualizarUsuario(Usuario usuario);
}