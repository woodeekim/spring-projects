package woody.co.repository;

import lombok.Getter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

// JpaBaseEntity 에 속성들을 상속받은 클래스에서 사용할 수 있도록 설정
@MappedSuperclass
@Getter
public class JpaBaseEntity {
    @Column(updatable = false)
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    // 영속성 컨텍스트에 올리기 전에 (persist하기 전) 이벤트를 발생시킨다
    @PrePersist
    private void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate
    private void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

}
