package co.woody;

import co.woody.discount.DiscountPolicy;
import co.woody.discount.RateDiscountPolicy;
import co.woody.member.MemberRepository;
import co.woody.member.MemberService;
import co.woody.member.MemberServiceImpl;
import co.woody.member.MemoryMemberRepository;
import co.woody.order.OrderService;
import co.woody.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
