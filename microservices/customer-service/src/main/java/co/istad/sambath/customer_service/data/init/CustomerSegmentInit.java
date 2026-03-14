package co.istad.sambath.customer_service.data.init;

import co.istad.sambath.customer_service.data.entity.CustomerSegmentEntity;
import co.istad.sambath.customer_service.data.repository.CustomerSegmentRepository;
import co.istad.sambath.customer_service.domain.valueObject.CustomerSegmentType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerSegmentInit {

    private final CustomerSegmentRepository customerSegmentRepository;

    @PostConstruct
    public void initCustomerSegment(){
        if (customerSegmentRepository.count() == 0) {

            CustomerSegmentEntity business = new CustomerSegmentEntity();
            business.setCustomerSegmentId(UUID.randomUUID());
            business.setCustomerSegmentType(CustomerSegmentType.BUSINESS);

            CustomerSegmentEntity student = new CustomerSegmentEntity();
            student.setCustomerSegmentId(UUID.randomUUID());
            student.setCustomerSegmentType(CustomerSegmentType.STUDENT);

            CustomerSegmentEntity normal = new CustomerSegmentEntity();
            normal.setCustomerSegmentId(UUID.randomUUID());
            normal.setCustomerSegmentType(CustomerSegmentType.REGULAR);

            CustomerSegmentEntity vip = new CustomerSegmentEntity();
            vip.setCustomerSegmentId(UUID.randomUUID());
            vip.setCustomerSegmentType(CustomerSegmentType.VIP);

            customerSegmentRepository.saveAll(List.of(normal,student,vip,business));
        }

    }
}
