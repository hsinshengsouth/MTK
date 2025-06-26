package idv.po.mtk_src.management.web;

import idv.po.mtk_src.infrastructure.redis.*;
import idv.po.mtk_src.management.app.UserAuthService;
import idv.po.mtk_src.management.domain.user.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/admin")
@RequiredArgsConstructor
public class UserAuthController {

  private final UserAuthService service;
  private final RedisService redisService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRegister request) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody UserRequest request) {
    return ResponseEntity.ok(service.login(request));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      redisService.removeToken(token);
    }
    return ResponseEntity.ok("Logout successful");
  }
}
