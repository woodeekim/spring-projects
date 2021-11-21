package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {
    @Autowired
    EntityManager em;
    
    @Test
    void updateTest () throws Exception {
        Book book = em.find(Book.class, 1L);

        //Tx
        book.setName("name");
        //변경 감지(dirty checking) / Tx commit 시점에 변경된 게 있으면 JPA 가 자동으로 update query 를 날린다
        //flush 할 때 dirty checking 일어난다
        //JPA 관리하는 건 영속 상태에 있는 애들만 관리한다. 준영속 상태에 있는 건 JPA 관리하지 않는다
        //Tx commit
    }

}
