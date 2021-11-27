package woody.co.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woody.co.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
