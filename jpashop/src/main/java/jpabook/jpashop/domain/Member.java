package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    // 컨트롤러에서 해당 엔티티로 받으면서 validate 하기 위해 추가한 코드
    // 컨트롤러에서 바로 받기 보다 request 용 dto 만들어서 사용하자 !
    @NotEmpty
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // orders.member
    private List<Order> orders = new ArrayList<>();
}
