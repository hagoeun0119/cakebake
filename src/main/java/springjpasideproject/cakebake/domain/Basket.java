package springjpasideproject.cakebake.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class Basket {

    @Id @GeneratedValue
    @Column(name = "basket_id")
    private Long id;

    @OneToOne(mappedBy = "basket", fetch = LAZY)
    private Member member;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    private List<BasketProduct> basketProducts = new ArrayList<>();

}
