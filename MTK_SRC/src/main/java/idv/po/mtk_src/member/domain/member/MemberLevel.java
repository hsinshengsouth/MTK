package idv.po.mtk_src.member.domain.member;

public enum MemberLevel {
    SUPERVIP(),
    VIP(),
    GENERAL();


    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
