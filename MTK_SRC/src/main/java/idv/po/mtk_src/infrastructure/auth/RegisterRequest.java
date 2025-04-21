package idv.po.mtk_src.infrastructure.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



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
    private Integer  roleId;

}
