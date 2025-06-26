package idv.po.mtk_src.member.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegister {

  private String memberName;
  private String memberEmail;
  private String memberPhone;
  private String password;
  private ZonedDateTime birthday;
  private String gender;
  private String marketingSource;
  private String cardLast4;
  private String cardType;
  private String billingAdd;
}
