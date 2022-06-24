package com.reddit.service;//package com.reddit.app.service;

import com.reddit.config.JwtUtil;
import com.reddit.content.NotificationEmail;
import com.reddit.dto.AuthenticationResponse;
import com.reddit.dto.LoginRequest;
import com.reddit.dto.RegisterRequestDto;
import com.reddit.exception.AccountDisabledException;
import com.reddit.exception.InvalidCredentialsException;
import com.reddit.exception.SpringRedditException;
import com.reddit.exception.UnAuthorizeException;
import com.reddit.model.User;
import com.reddit.model.VerificationToken;
import com.reddit.repository.UserRepository;
import com.reddit.repository.VerificationTokenRepository;
import com.reddit.utils.AuthUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtils;

    @Transactional
    public void signup(RegisterRequestDto registerRequestDto){
        System.out.println("HERERERERER 4");

        User user = new User();
        user.setUserName(registerRequestDto.getUserName());
        user.setEmail(registerRequestDto.getEmail());
        user.setCreatedDate(Instant.now());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setEnable(false);
        userRepository.save(user);
        String token = this.generateVerificationToken(user);
        mailService.sendSimpleMessage(new NotificationEmail("Please Activate Your Accoubt", user.getEmail(), "Thank you for signing up to DSpring Reddit" +
                ", please click on the below url link to activate your account:" +
                "http://localhost:9000/api/auth/acountVerification/"+token));
    }
    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        log.info("token generated: " + token);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        log.info("Verification token saved: ");
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = this.verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUserName();
        User user = userRepository.findByUserName(username).orElseThrow(()-> new SpringRedditException("user with the name " + username+ " not found "));
        user.setEnable(true);
        userRepository.save(user);
    }

//    public AuthenticationResponse login(LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserame(), loginRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = jwtProvider.generateToken(authentication);
//        return new AuthenticationResponse(token, loginRequest.getUserame() );
//    }


    //@Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        try {
            System.out.println("AUTHENTICATION MANAGER CALL USERNAME PASSWORD AUTH TO AUTHENTCATE.....");
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
            System.out.println("AUTHENTICATION SUCCESSFULL");
            System.out.println("LOGIN USENAM " + loginRequest.getUserName());
            //User user = this.userRepository.findByEmail(loginRequest.getUserame()).orElseThrow(() -> new InvalidCredentialsException("User with supplied credential does not exist"));
            System.out.println("usename " +  authentication.getName());
            String token = jwtTokenUtils.generateToken(authentication.getName());
            if (isEmptyToken(token)) {
                throw new UnAuthorizeException("invalid username/password:");
            }
            System.out.println("TOKEN " + token);
//            CustomerRefreshToken refreshToken = this.createRefreshTokenModel(user);
//            refreshToken = (CustomerRefreshToken) this.saveRefreshToken(refreshToken);
            //create response
            SecurityContextHolder.getContext().setAuthentication(authentication);
            AuthenticationResponse authResponse = new AuthenticationResponse(); //this.createLoginResponse(user, token);
            authResponse.setAuthenticationToken(token);
            authResponse.setUsername(loginRequest.getUserName());
            return authResponse;

        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new InvalidCredentialsException("invalid username/password:");
            } else if (e instanceof DisabledException) {
                throw new AccountDisabledException("Account disabled");
            } else {
                throw e;
            }
        }
    }

    private boolean isEmptyToken(String token) {
        return AuthUtils.isEmptyToken(token);
    }
}
