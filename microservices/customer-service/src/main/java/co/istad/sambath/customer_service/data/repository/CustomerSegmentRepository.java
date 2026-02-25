package co.istad.sambath.customer_service.data.repository;

import co.istad.sambath.customer_service.data.entity.CustomerSegmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface CustomerSegmentRepository extends JpaRepository<CustomerSegmentEntity, UUID> {
}
