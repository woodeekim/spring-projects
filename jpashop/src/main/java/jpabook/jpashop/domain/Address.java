package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 어딘가에 내장될 수 있다라고 정의해줘야 함
@Getter // 값 타입이기 때문에 변경 불가능하게 설계하는 것이 좋다. Setter 닫아주고 생성할 때 만들어주는 게 베스트
public class Address {

    private String city;

    private String street;

    private String zipcode;

    // JPA 리플렉션이나 프록시 기술을 사용해야 되기 때문에 기본 생성자가 필요한데 JPA 에서 protected 까지 허용된다 (JPA 필요하구나 알 수 있음)
    protected Address() {

    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
