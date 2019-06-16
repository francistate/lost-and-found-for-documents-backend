package tech.ftchekure.documentslostandfound.service.security;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.ftchekure.documentslostandfound.daos.user.UserRepository;

@Service("customUserDetailsService")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        val user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User was not found"));
        log.info("---> User was found for : {}", username);
        return user;
    }
}
