package idv.po.mtk_src.member.web;

import idv.po.mtk_src.infrastructure.redis.RedisService;
import idv.po.mtk_src.management.domain.user.AuthenticationResponse;
import idv.po.mtk_src.member.app.MemberAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/member")
@RequiredArgsConstructor
public class MemberController {

  private final MemberAuthService authService;
  private final RedisService redisService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody MemberRegister request) {

    return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody MemberRequest request) {

    return ResponseEntity.ok(authService.login(request));
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
