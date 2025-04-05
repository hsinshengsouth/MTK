package idv.po.mtk_src.infrastructure.auth;

import idv.po.mtk_src.infrastructure.AuthUtils.JwtUtils;
import idv.po.mtk_src.management.domain.user.ManageUser;
import idv.po.mtk_src.management.domain.user.ManageUserRepository;
import idv.po.mtk_src.management.domain.user.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {


    private final ManageUserRepository manageUserRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(
            @Qualifier("springDataJpaManageUserRepository") ManageUserRepository manageUserRepository,
            JwtUtils jwtUtils,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.manageUserRepository = manageUserRepository;
        this.jwtUtils=jwtUtils;
        this.authenticationManager=authenticationManager;
        this.passwordEncoder=passwordEncoder;
    }


    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        ManageUser registerUser = new ManageUser();
        BeanUtils.copyProperties(request, registerUser, "role","password");

        if (request.getRole() != null) {
            Set<Role> roles = new HashSet<>();
            roles.add(request.getRole());
            registerUser.setRoles(roles);
        }

        if(request.getPassword()!=null){
            registerUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        var savedUser = manageUserRepository.save(registerUser);
        var jwtToken = jwtUtils.generateToken(savedUser);

        return AuthenticationResponse
                     .builder()
                     .accessToken(jwtToken)
                     .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserEmail(),
                        request.getPassword()
                )
        );

        var manageUser = manageUserRepository.findByUserEmail(request.getUserEmail()).orElseThrow();
        var jwtToken =  jwtUtils.generateToken(manageUser);

        return AuthenticationResponse
                    .builder()
                    .accessToken(jwtToken)
                    .build();
    }



}
