package woody.co.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import woody.co.dto.MemberDto;
import woody.co.entity.Member;
import woody.co.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    void findByUsername() {
        Member member1 = new Member("member1", 28);
        Member member2 = new Member("member2", 27);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<String> result = memberRepository.findUsernameList();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findByMemberDto() {
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

    @Test
    void findByNames() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        Member member3 = new Member("member3");
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> result = memberRepository.findByNames(Arrays.asList("member1", "member2", "member3"));

        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void returnType() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member1");
        Member member3 = new Member("member3");
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        Optional<Member> result = memberRepository.findOptionalByUsername("member1");
    }

    @Test
    void paging () throws Exception {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));
        memberRepository.save(new Member("member6", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        Page<Member> page = memberRepository.findByAge(10, pageRequest);

        //Page로 받으면 dto 로 변환하기 쉽다
        Page<MemberDto> toMap = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));

        // then
        List<Member> members = page.getContent();
        long totalElements = page.getTotalElements();
        for (Member member : members) {
            System.out.println("member = " + member);
        }
        System.out.println("totalElements = " + totalElements);

        assertThat(page.getContent().size()).isEqualTo(3);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalElements()).isEqualTo(6);
        assertThat(page.getTotalPages()).isEqualTo(2);
    }

    @PersistenceContext
    EntityManager em;

    @Test
    void bulkAgePlus () throws Exception {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 30));
        memberRepository.save(new Member("member3", 40));
        memberRepository.save(new Member("member4", 50));
        memberRepository.save(new Member("member5", 60));

        int age = 20;
        int resultCount = memberRepository.bulkAgePlus(age);

        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    void findMemberLazy () throws Exception {
        Team team1 = new Team("team1");
        Team team2 = new Team("team2");
        teamRepository.save(team1);
        teamRepository.save(team2);

        memberRepository.save(new Member("member1", 20, team1));
        memberRepository.save(new Member("member2", 20, team2));

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
            System.out.println("member.getUsername() = " + member.getTeam().getName());
        }
    }

    @Test
    void hint () throws Exception {
        // given
        Member member = new Member("member1", 20);
        memberRepository.save(member);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");

        em.flush();

        System.out.println("findMember = " + findMember.getUsername());
    }

    @Test
    void callCustom () throws Exception {
        List<Member> result = memberRepository.findMemberCustom();
    }


}
