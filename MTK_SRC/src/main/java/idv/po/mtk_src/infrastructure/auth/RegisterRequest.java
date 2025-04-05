package idv.po.mtk_src.infrastructure.auth;

import idv.po.mtk_src.management.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String userName;
    private String userEmail;
    private String password;
    private String userStatus;
    private Integer deptId;
    private Role role;

}
