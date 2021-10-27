package co.woody.order;

import co.woody.discount.FixDiscountPolicy;
import co.woody.member.Grade;
import co.woody.member.Member;
import co.woody.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceImplTest {

    @Test
    void createOrderServiceImpli() {
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));
        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "name", 2000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
