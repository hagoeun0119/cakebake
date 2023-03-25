package springjpasideproject.cakebake.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    private String userId;
    private String name;
    private String phone;
    private String email;

    public MemberDto(String userId, String name, String phone, String email) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
