package co.woody.service;

import co.woody.domain.Member;
import co.woody.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {
    MemberService service;
    MemoryMemberRepository repository;

    public MemberServiceTest(MemberService service) {
        this.service = service;
    }

    @BeforeEach
    void beforeEach() {
        repository = new MemoryMemberRepository();
        service = new MemberService(repository);
    }

    @AfterEach
    void afterEach() {
        repository.clearStore();
    }

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
/*        service.join(member);
        try {
            service.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
    }


    @Test
    void findMembers() {
        Member member = new Member();
        member.setName("woody");
        service.join(member);

        Member member2 = new Member();
        member2.setName("buzz");
        service.join(member2);

        List<Member> members = service.findMembers();
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void findOne() {
        Member member = new Member();
        member.setName("woody");
        service.join(member);

        Member result = service.findOne(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }
}
