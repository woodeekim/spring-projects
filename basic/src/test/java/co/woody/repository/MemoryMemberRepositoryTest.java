package co.woody.repository;

import co.woody.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {
    private final MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    void afterEach() {
        repository.clearStore();
    }

    @Test
    void findMember() {
        repository.findById(1L);
    }

    @Test
    void save() {
        Member member = new Member();
        member.setName("woody");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    void findByName() {
        Member member1 = new Member();
        member1.setName("woody");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("buzz");
        repository.save(member2);

        Member result = repository.findByName("woody").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    void findAll() {
        Member member1 = new Member();
        member1.setName("woody");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("buzz");
        repository.save(member2);

        List<Member> members = repository.findAll();
        assertThat(members.size()).isEqualTo(2);
    }
}
