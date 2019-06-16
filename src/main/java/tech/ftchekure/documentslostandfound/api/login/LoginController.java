package tech.ftchekure.documentslostandfound.api.login;

import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;

@CrossOrigin
@RestController
public class LoginController {

    private final AuthenticationProvider authenticationProvider;

    private final UserDetailsService userDetailsService;

    public LoginController(AuthenticationProvider authenticationProvider,
                           @Qualifier("customUserDetailsService") UserDetailsService userDetailsService) {
        this.authenticationProvider = authenticationProvider;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/pm/login")
    public UserDetails login(@RequestParam("username") String username,
                             @RequestParam("password") String password) {
        val userDetails = userDetailsService.loadUserByUsername(username);
        val usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        val authentication = authenticationProvider.authenticate(usernamePasswordAuthenticationToken);
        val securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        if (isNull(authentication)) {
            throw new BadCredentialsException("Username or password is incorrect");
        }
        return userDetails;
    }

}
