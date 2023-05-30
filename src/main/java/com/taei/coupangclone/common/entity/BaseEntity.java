package com.taei.coupangclone.common.entity;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass // 엔티티가 아님. 테이블과 매핑 안된다. / 상속관계 매핑 아님 / 단순히 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공
@EntityListeners(AuditingEntityListener.class) // 해당 클래스에 Auditing 기능을 포함
public abstract class BaseEntity { // 직접 생성해서 사용할 일 없으므로 추상클래스 권장

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
