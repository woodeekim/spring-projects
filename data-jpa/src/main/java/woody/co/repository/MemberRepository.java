package woody.co.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woody.co.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
