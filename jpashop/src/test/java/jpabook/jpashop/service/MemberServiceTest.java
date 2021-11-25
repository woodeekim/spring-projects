package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired
    MemberRepositoryOld memberRepositoryOld;
    @Autowired EntityManager entityManager;

    @Test
//    @Rollback(value = false)
    void 회원가입 () throws Exception {
        // given
        Member member = new Member();
        member.setName("mamberA");
        // when
        Long savedId = memberService.join(member);
        // then
        entityManager.flush();
        assertEquals(member, memberRepositoryOld.findOne(savedId));
    }

    @Test
    void 중복_회원_예제 () throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("memberA");
        Member member2 = new Member();
        member2.setName("memberA");

        // when
        memberService.join(member1);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        // then
        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 회원입니다");
    }
}
