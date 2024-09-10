package com.devkduck.duckshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;


/*
등록자, 수정자를 넣지 않고
등록일, 수정일을 넣는 테이블 클래스
 */

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public class BaseTimeEntity {
    //엔티티가 생성되어 저장될 때 시간을 자동 저장
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;

    ////엔티티값을 변경할 때 시간을 자동 저장
    @LastModifiedDate
    private LocalDateTime updateTime;
}
