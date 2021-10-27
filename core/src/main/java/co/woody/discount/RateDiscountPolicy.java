package co.woody.discount;

import co.woody.annotation.MainDiscountPolicy;
import co.woody.member.Grade;
import co.woody.member.Member;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{
    private int discountPercent = 10;
    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return price;
        }
    }
}
