package co.woody;

import co.woody.discount.DiscountPolicy;
import co.woody.discount.FixDiscountPolicy;
import co.woody.discount.RateDiscountPolicy;
import co.woody.member.MemberRepository;
import co.woody.member.MemberService;
import co.woody.member.MemberServiceImpl;
import co.woody.member.MemoryMemberRepository;
import co.woody.order.OrderService;
import co.woody.order.OrderServiceImpl;


public class AppConfig {
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    private DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
