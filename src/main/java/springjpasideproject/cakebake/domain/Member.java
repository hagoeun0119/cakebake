package springjpasideproject.cakebake.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    private String userId;

    private String password;

    private String name;

    private String phone;

    private String email;

    public void updateMember(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
