package co.istad.sambath.customer_service.data.entity;

import co.istad.sambath.customer_service.domain.valueObject.CustomerSegmentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customer-segments")
public class CustomerSegmentEntity {

    @Id
    private UUID customerSegmentId;

    private CustomerSegmentType  customerSegmentType;

    @OneToMany(mappedBy = "customerSegment",cascade = CascadeType.ALL)
//    @JoinColumn(name = "customer_id")
    private List<CustomerEntity> customers;

}
