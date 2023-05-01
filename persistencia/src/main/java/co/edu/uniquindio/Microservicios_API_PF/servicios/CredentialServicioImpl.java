package co.edu.uniquindio.Microservicios_API_PF.servicios;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Credential;
import co.edu.uniquindio.Microservicios_API_PF.repositorios.CredentialRepo;

import java.util.Optional;

@Service
public class CredentialServicioImpl implements CredentialServicio {

    private final CredentialRepo credentialRepo;

    private final PasswordEncoder passwordEncoder;

    public CredentialServicioImpl(CredentialRepo credentialRepo, PasswordEncoder passwordEncoder) {
        this.credentialRepo = credentialRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Credential crearCredential(Credential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        return credentialRepo.save(credential);
    }

    @Override
    public Optional<Credential> obtenerCredentialPorId(String id) {
        return credentialRepo.findById(id);
    }

    @Override
    public Optional<Credential> obtenerCredentialPorUsername(String username) {
        return credentialRepo.findByUsername(username);
    }

    @Override
    public void eliminarCredential(String id) {
        credentialRepo.deleteById(id);
    }

    @Override
    public Credential actualizarCredential(Credential credential) {
        return credentialRepo.save(credential);
    }

    @Override
    public boolean validarCredential(String username, String password) {
        Optional<Credential> optionalCredential = credentialRepo.findByUsername(username);
        if (optionalCredential.isPresent()) {
            Credential credential = optionalCredential.get();
            String encryptedPassword = passwordEncoder.encode(password);
            return credential.getPassword().equals(encryptedPassword);
        }
        return false;
    }
}