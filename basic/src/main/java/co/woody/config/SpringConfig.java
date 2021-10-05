package co.woody.config;

import co.woody.aop.TimeTraceAop;
import co.woody.repository.MemberRepository;
import co.woody.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

//    @Bean
//    public TimeTraceAop timeTraceAop() {
//        return new TimeTraceAop();
//    }

    /*
    private EntityManager em;

    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    private final DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource);
        return new JpaRepository(em);
    }
    */
}
