package springjpasideproject.cakebake.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    private String userId;

    private String password;

    private String name;

    private String phone;

    private String email;

    protected Member() {
    }

    public Member(Basket basket, String userId, String password, String name, String phone, String email) {
        this.basket = basket;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
