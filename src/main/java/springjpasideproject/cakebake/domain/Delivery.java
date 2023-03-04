package springjpasideproject.cakebake.domain;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Builder(builderClassName = "createOrderBuilder", builderMethodName = "createOrderBuilder")
    public Delivery(Order order) {
        this.order = order;
    }

    @Builder(builderClassName = "createAddressAndStatusBuilder", builderMethodName = "createAddressAndStatusBuilder")
    public Delivery(Address address, DeliveryStatus status) {
        this.address = address;
        this.status = status;
    }

}
