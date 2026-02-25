package co.istad.sambath.customer_service.data.entity;

import co.istad.sambath.customer_service.domain.valueObject.*;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
@Transactional
public class CustomerEntity {

    @Id
    private UUID customerId;
    private String phoneNumber;

    @Embedded
    private CustomerName name;

    private CustomerGender gender;

    @Embedded
    private CustomerEmail email;
    private LocalDate dob;


//    Kyc kyc,
//    Address address,
//    Contact contact,
//    CustomerSegmentId customerSegmentId


//    @ManyToOne
//    private CustomerSegmentType  customerSegmentType;


    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    private AddressEntity address;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    private ContactEntity contact;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    private KycEntity kyc;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_segment_id")
    private CustomerSegmentEntity customerSegment;

    private List<String> failureMessages;


}
