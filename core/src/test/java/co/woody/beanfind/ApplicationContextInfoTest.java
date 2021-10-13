package co.woody.beanfind;

import co.woody.AppConfig;
import co.woody.discount.DiscountPolicy;
import co.woody.discount.FixDiscountPolicy;
import co.woody.discount.RateDiscountPolicy;
import co.woody.member.MemberRepository;
import co.woody.member.MemberService;
import co.woody.member.MemberServiceImpl;
import co.woody.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationContextInfoTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.err.println(beanDefinitionName);
        }

    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findAllApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.err.println(beanDefinitionName);
            }
        }
    }

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeenByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        System.err.println(memberService.getClass());
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeenByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        System.err.println(memberService.getClass());
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeenByName2() {
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        System.err.println(memberService.getClass());
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeenByNameX() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("memberService2", MemberServiceImpl.class));
    }

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이 있으면, 중복 오류가 발생한다")
    void findBeanByTypeDuplicate() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (Map.Entry<String, MemberRepository> stringMemberRepositoryEntry : beansOfType.entrySet()) {
            System.err.println(stringMemberRepositoryEntry.getValue());
        }
    }

    @Configuration
    static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }

    @Test
    @DisplayName("부모 타입으로 조회시 자식이 두개 이상 있을시 중복 오류가 난다")
    void findBeanByParentTypeDuplicate() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
    }

    @Configuration

    static class TestConfig {
        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy FixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
