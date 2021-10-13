package co.woody.order;

import co.woody.discount.DiscountPolicy;
import co.woody.member.Member;
import co.woody.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository;

    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
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
