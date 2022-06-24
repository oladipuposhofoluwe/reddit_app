package com.reddit.config.security.admin;



import com.reddit.model.User;
import com.reddit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 *
 */
@RequiredArgsConstructor
@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    //@Cacheable("adminUserAuthInfo")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("NOW WANT TO LOAD USER");
        User user = userRepository.findAuthUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No user found with username:" + username));
        System.out.println("DONE LOADING USER... ");
        UserInfo userInfo = new UserInfo(user);
        return userInfo;
    }

}
