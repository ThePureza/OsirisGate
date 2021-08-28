package com.example.osirisgateapi.service;

import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Cargo;
import com.example.osirisgateapi.model.entity.Usuario;
import com.example.osirisgateapi.model.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class UsuarioService {

    private UsuarioRepository repository;

    //public UsuarioService(UsuarioRepository repository){
       // this.repository = repository;
   // }

    public List<Usuario> getUsuarios(){
        return repository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id){
        return repository.findById(id);
    }

    public List<Usuario> getUsuariosByCargo(Optional<Cargo> cargo) {
        return repository.findByCargo(cargo);
    }
    @Transactional
    public Usuario salvar(Usuario usuario){
        validar(usuario);
        return repository.save(usuario);
    }

    @Transactional public void excluir (Usuario usuario){
        Objects.requireNonNull(usuario.getId());
        repository.delete(usuario);
    }

    public void validar (Usuario usuario){
        if (usuario.getNomeUsuario() == null || usuario.getNomeUsuario().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
        if (usuario.getCargo() == null || usuario.getCargo().getId() == null || usuario.getCargo().getId() == 0) {
            throw new RegraNegocioException("Cargo inválido");
        }
    }
}
