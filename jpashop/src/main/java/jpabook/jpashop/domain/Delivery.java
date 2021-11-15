package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    // enum 할 때 주의사항은 기본 EnumType.ORDINAL 이기 때문에 DB에는 숫자로 들어간다
    // 만약 중간에 다른 타입이 생기면 다 꼬이기 때문에 String 으로 지정
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
