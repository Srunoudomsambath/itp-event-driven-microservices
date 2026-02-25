    package co.istad.sambath.customer_service.data.entity;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.util.UUID;
    @Getter
    @Setter
    @NoArgsConstructor
    @Entity
    @Table(name = "addresses")
    public class AddressEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID addressId;
        private String line;
        private String city;
        private String zipCode;
        private String country;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "customer_id")
        private CustomerEntity customer;
    }
