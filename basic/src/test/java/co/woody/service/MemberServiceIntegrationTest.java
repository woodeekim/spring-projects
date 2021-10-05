package co.woody.service;

import co.woody.domain.Member;
import co.woody.repository.MemberRepository;
import co.woody.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired
    MemberService service;
    @Autowired
    MemberRepository repository;

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("woody");

        //when
        Long memberId = service.join(member);

        //then
        Member findMember = service.findOne(memberId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member = new Member();
        member.setName("woody");

        Member member2 = new Member();
        member2.setName("woody");

        //when
        service.join(member);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> service.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}
