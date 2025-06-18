package idv.po.mtk_src.member.domain.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
@Entity
public  class Member implements UserDetails {


    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "member_id", updatable = false, nullable = false)
    private UUID memberId;
    @Column(name = "member_name", length = 100)
    private String memberName;

    @Column(name = "member_email", length = 150)
    private String memberEmail;

    @Column(name = "member_phone", length = 100)
    private String memberPhone;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "member_status", length = 30)
    private String memberStatus = "active";

    @Enumerated(EnumType.STRING)
    @Column(name = "member_level", length = 30)
    private MemberLevel  memberLevel = MemberLevel.GENERAL;

    @Column(name = "birthday")
    private ZonedDateTime birthday;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "register_time")
    private ZonedDateTime registerTime;

    @Column(name = "last_login_time")
    private ZonedDateTime lastLoginTime;

    @Column(name = "newsletter_opt_in")
    private Boolean newsletterOptIn = false;

    @Column(name = "marketing_source", length = 100)
    private String marketingSource;


    @Column(name = "card_last4", length = 4)
    private String cardLast4;

    @Column(name = "card_type", length = 20)
    private String cardType;

    @Column(name = "payment_token", length = 255)
    private String paymentToken;

    @Column(name = "billing_address", length = 255)
    private String billingAddress;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(memberLevel.getAuthority()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.memberEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return  "ACTIVE".equalsIgnoreCase(this.memberStatus);
    }


}
