package co.woody.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int result1 = clientBean1.logic();
        assertThat(result1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int result2 = clientBean2.logic();
        assertThat(result2).isEqualTo(1);


    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;
        
        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }

    @Scope("singleton")
    static class ClientBean {

        // PrototypeBean은 스코프가 프로토타입이지만 ClientBean 객체가 생성하는 시점에 주입이 된다.
        // 그래서 ClientBean의 메서드를 사용해서 prototypeBean을 사용하면 계속 같은 것을 사용하게 된다.

        // 또 다른 방법으로    는 ApplicationContext ac 를 주입받아서
        // ac.getBean(PrototypeBean.class); 를 통해서 로직을 실행하는 방법도 있다.
        // 하지만 이렇게 되면 ClientBean 입장에서 ApplicationContext를 가져와야 되기 때문에
        // 지저분할 수 밖에 없다.

        // 그래서 스프링에서 제공해주는 기능을 사용하면 된다. -> ObjectProvider
        @Autowired
        //다음은 javax 에서 제공
        private Provider<PrototypeBean> prototypeBeanProvider;


        // 다음은 스프링에서 제공
//        private ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;
//        private final PrototypeBean prototypeBean;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }

    }
}
