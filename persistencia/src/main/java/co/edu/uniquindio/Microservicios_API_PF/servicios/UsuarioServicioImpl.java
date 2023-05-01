package co.edu.uniquindio.Microservicios_API_PF.servicios;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Credential;
import co.edu.uniquindio.Microservicios_API_PF.entidades.Usuario;
import co.edu.uniquindio.Microservicios_API_PF.excepciones.UsuarioRegistradoException;
import co.edu.uniquindio.Microservicios_API_PF.repositorios.UsuarioRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioRepo usuarioRepo;

    private final CredentialServicio credentialServicio;

    public UsuarioServicioImpl(UsuarioRepo usuarioRepo, CredentialServicio credentialServicio) {
        this.usuarioRepo = usuarioRepo;
        this.credentialServicio = credentialServicio;
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) throws UsuarioRegistradoException {
        Optional<Usuario> optionalUsuario = usuarioRepo.findByCorreo(usuario.getCorreo());
        if (optionalUsuario.isPresent()) {
            throw new UsuarioRegistradoException("El correo ya se encuentra registrado");
        }
        Credential credential = credentialServicio.crearCredential(usuario.getCredential());
        usuario.setCredential(credential);
        return usuarioRepo.save(usuario);
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorId(String id) {
        return usuarioRepo.findById(id);
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepo.findByCorreo(correo);
    }

    @Override
    public void eliminarUsuario(String id) {
        usuarioRepo.deleteById(id);
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }
}