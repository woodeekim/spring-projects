package woody.co.repository;

import woody.co.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Member> findMemberCustom() {

        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
