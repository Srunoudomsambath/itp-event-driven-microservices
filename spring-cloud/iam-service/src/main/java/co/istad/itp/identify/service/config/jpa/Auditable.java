package co.istad.itp.identify.service.config.jpa;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass // extend jpa
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<T> {
    @CreatedBy
    @Column(updatable = false, nullable = false)
    protected T createdBy;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    protected LocalDateTime createdDate;

    @LastModifiedBy
    protected T lastModifiedBy;
    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;
}
