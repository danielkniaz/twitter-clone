package io.proxyseller.tw.security;

import io.proxyseller.tw.repository.UserRepository;
import io.proxyseller.tw.repository.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static io.proxyseller.tw.security.ApplicationSecurity.ROLE_USER;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = repository.findByLogin(username);
        if (entity == null || entity.isDeleted()) {
            throw new UsernameNotFoundException(username);
        }
        return new User(username, entity.getPassword(), AuthorityUtils.createAuthorityList(ROLE_USER));
    }
}
