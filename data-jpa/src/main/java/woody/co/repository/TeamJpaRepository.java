package woody.co.repository;

import org.springframework.stereotype.Repository;
import woody.co.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamJpaRepository {
    @PersistenceContext
    EntityManager em;

    public Team save(Team team) {
        em.persist(team);
        return team;
    }

    public void delete(Team team) {
        em.remove(team);
    }

    public Optional<Team> findById(Long id) {
        Team team = em.find(Team.class, id);
        return Optional.ofNullable(team);
    }

    public List<Team> findAll() {
        return em.createQuery(
                "select t from Team t", Team.class)
                .getResultList();
    }

    public Team findByOne(Long id) {
        return em.find(Team.class, id);
    }

    public long count() {
        return em.createQuery("select count(t) from Team t", Long .class)
                .getSingleResult();
    }
}
