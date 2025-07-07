package idv.po.mtk_src.member.domain;

public enum MemberLevel {
  SUPERVIP(),
  VIP(),
  GENERAL();

  public String getAuthority() {
    return "ROLE_" + this.name();
  }
}
