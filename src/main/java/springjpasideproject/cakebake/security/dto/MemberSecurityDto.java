package springjpasideproject.cakebake.security.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class MemberSecurityDto extends User {

    private String userId;
    private String password;

    public MemberSecurityDto(String userId, String password, Collection<? extends GrantedAuthority> authorities) {

        super(userId, password, authorities);

        this.userId = userId;
        this.password = password;
    }
}
