package springjpasideproject.cakebake;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;

    private String password;

    private String name;

    private String phone;

    private String email;

}
