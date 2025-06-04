package idv.po.mtk_src.infrastructure.auth;

import idv.po.mtk_src.infrastructure.utils.JwtUtils;
import idv.po.mtk_src.management.domain.user.ManageUser;
import idv.po.mtk_src.management.domain.user.ManageUserRepository;
import idv.po.mtk_src.management.domain.user.Role;
import idv.po.mtk_src.management.domain.user.RoleRepository;
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
    private final RedisTokenService redisTokenService;
    
    private final RoleRepository roleRepository;
    public AuthenticationService(
            ManageUserRepository manageUserRepository,
            JwtUtils jwtUtils,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder, RedisTokenService redisTokenService,
            @Qualifier("roleJpaRepository") RoleRepository roleRepository) {
        this.manageUserRepository = manageUserRepository;
        this.jwtUtils=jwtUtils;
        this.authenticationManager=authenticationManager;
        this.passwordEncoder=passwordEncoder;
        this.redisTokenService = redisTokenService;
        this.roleRepository = roleRepository;
    }


    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        ManageUser registerUser = new ManageUser();
        BeanUtils.copyProperties(request, registerUser, "password");

        if (request.getRoleId() != null) {
            Role role = roleRepository.findByRoleId(request.getRoleId() )
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            registerUser.setRoles(roles);
        }

        if(request.getPassword()!=null){
            registerUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        var savedUser = manageUserRepository.persistUser(registerUser);
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
        redisTokenService.cacheToken(jwtToken, manageUser.getUserEmail(), jwtUtils.getExpirationMs());


        return AuthenticationResponse
                    .builder()
                    .accessToken(jwtToken)
                    .build();
    }



}
