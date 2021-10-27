package co.woody.order;

import co.woody.annotation.MainDiscountPolicy;
import co.woody.discount.DiscountPolicy;
import co.woody.member.Member;
import co.woody.member.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository;

    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(member.getId(), itemName, itemPrice, discountPrice);
    }

    //TEST 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
