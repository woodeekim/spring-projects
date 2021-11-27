package woody.co.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import woody.co.dto.MemberDto;
import woody.co.entity.Member;
import woody.co.entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    void basicCRUD () throws Exception {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(member1).isEqualTo(findMember1);
        assertThat(member2).isEqualTo(findMember2);

        //리스트 검증
        List<Member> findMembers = memberRepository.findAll();
        assertThat(findMembers.size()).isEqualTo(2);

        //카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long count2 = memberRepository.count();
        assertThat(count2).isEqualTo(0);
    }

    @Test
    void findByUsernameAndAgeGreaterThen() {
        Member member1 = new Member("memberA", 28);
        Member member2 = new Member("memberA", 28);
        Member member3 = new Member("memberB", 30);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> findMembers = memberRepository.findByUsernameAndAgeGreaterThan("memberA", 27);

        assertThat(findMembers.size()).isEqualTo(2);
    }

    @Test
    void findUser_By_Used_Query_Annotation() {
        Member member1 = new Member("member1", 28);
        Member member2 = new Member("member2", 27);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findUsers("member1", 28);

        assertThat(result.get(0).getUsername()).isEqualTo("member1");
        assertThat(result.get(0).getAge()).isEqualTo(28);
    }

    @Test
    void test_username() {
        Member member1 = new Member("member1", 28);
        Member member2 = new Member("member2", 27);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<String> result = memberRepository.findUsernameList();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void test_memberDto() {
        Team team = new Team("team");
        teamRepository.save(team);

        Member member1 = new Member("member1", 28);
        member1.setTeam(team);
        memberRepository.save(member1);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }
    }


}
