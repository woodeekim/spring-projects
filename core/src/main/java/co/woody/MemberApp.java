package co.woody;

import co.woody.member.Grade;
import co.woody.member.Member;
import co.woody.member.MemberService;

public class MemberApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();

        Member member = new Member(1L, "woody", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(member.getId());
        System.out.println("member = " + member);
        System.out.println("findMember = " + findMember);

    }
}
