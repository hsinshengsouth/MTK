package idv.po.mtk_src.management.app;

import idv.po.mtk_src.management.web.UserRequest;
import idv.po.mtk_src.management.domain.user.AuthenticationResponse;
import idv.po.mtk_src.infrastructure.redis.RedisService;
import idv.po.mtk_src.management.web.UserRegister;
import idv.po.mtk_src.infrastructure.utils.JwtUtils;
import idv.po.mtk_src.management.domain.user.User;
import idv.po.mtk_src.management.domain.user.UserRepository;
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
public class UserAuthService {

  private final UserRepository manageUserRepository;
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final RedisService redisService;

  private final RoleRepository roleRepository;

  public UserAuthService(
      UserRepository manageUserRepository,
      JwtUtils jwtUtils,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder,
      RedisService redisService,
      @Qualifier("roleJpaRepository") RoleRepository roleRepository) {
    this.manageUserRepository = manageUserRepository;
    this.jwtUtils = jwtUtils;
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
    this.redisService = redisService;
    this.roleRepository = roleRepository;
  }

  @Transactional
  public AuthenticationResponse register(UserRegister request) {
    User registerUser = new User();
    BeanUtils.copyProperties(request, registerUser, "password");

    if (request.getRoleId() != null) {
      Role role =
          roleRepository
              .findByRoleId(request.getRoleId())
              .orElseThrow(() -> new RuntimeException("Role not found"));
      Set<Role> roles = new HashSet<>();
      roles.add(role);
      registerUser.setRoles(roles);
    }

    if (request.getPassword() != null) {
      registerUser.setPassword(passwordEncoder.encode(request.getPassword()));
    }

    var savedUser = manageUserRepository.persistUser(registerUser);
    var jwtToken = jwtUtils.generateToken(savedUser);

    return AuthenticationResponse.builder().accessToken(jwtToken).build();
  }

  public AuthenticationResponse login(UserRequest request) {

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUserEmail(), request.getPassword()));

    var manageUser = manageUserRepository.findByUserEmail(request.getUserEmail()).orElseThrow();
    var jwtToken = jwtUtils.generateToken(manageUser);
    redisService.cacheToken(jwtToken, manageUser.getUserEmail(), jwtUtils.getExpirationMs());

    return AuthenticationResponse.builder().accessToken(jwtToken).build();
  }
}
