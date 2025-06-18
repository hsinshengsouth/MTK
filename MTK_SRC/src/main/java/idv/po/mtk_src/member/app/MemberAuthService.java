package idv.po.mtk_src.member.app;

import idv.po.mtk_src.management.domain.user.AuthenticationResponse;
import idv.po.mtk_src.infrastructure.redis.RedisTokenService;
import idv.po.mtk_src.infrastructure.utils.JwtUtils;
import idv.po.mtk_src.member.domain.member.Member;
import idv.po.mtk_src.member.domain.member.MemberRepository;
import idv.po.mtk_src.member.web.MemberRegister;
import idv.po.mtk_src.member.web.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAuthService {

    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final RedisTokenService redisTokenService;

    public AuthenticationResponse register(MemberRegister request) {
        Member member = new Member();

        BeanUtils.copyProperties(request,member,"password");

        if(request.getPassword()!=null){
            member.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        var rtnMember=memberRepository.persistMember(member);
        var jwtToken=jwtUtils.generateToken(rtnMember);

        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .build();
    }




    public AuthenticationResponse login(MemberRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getMemberEmail(),
                        request.getPassword()
                )
        );


        var rtnMember=memberRepository.findMemberByMemberEmail(request.getMemberEmail()).orElseThrow();
        var jwtToken=jwtUtils.generateToken(rtnMember);
        redisTokenService.cacheToken(jwtToken, rtnMember.getMemberEmail(), jwtUtils.getExpirationMs());



        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .build();
    }


}
