package com.example.osirisgateapi.service;
import com.example.osirisgateapi.api.exception.SenhaInvalidaException;
import com.example.osirisgateapi.model.entity.Credential;
import com.example.osirisgateapi.model.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CredentialService implements UserDetailsService{
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private CredentialRepository repository;

    @Transactional
    public Credential salvar(Credential credential){
        return repository.save(credential);
    }

    public UserDetails autenticar(Credential credential){
        UserDetails user = loadUserByUsername(credential.getLogin());
        boolean senhasBatem = encoder.matches(credential.getSenha(), user.getPassword());

        if (senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Credential credential = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String[] roles = credential.isAdmin()
                ? new String[]{"ADMIN", "USER"}
                : new String[]{"USER"};

        return User
                .builder()
                .username(credential.getLogin())
                .password(credential.getSenha())
                .roles(roles)
                .build();
    }
}
