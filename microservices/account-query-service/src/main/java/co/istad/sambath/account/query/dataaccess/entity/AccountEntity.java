    package co.istad.sambath.account.query.dataaccess.entity;


    import co.istad.sambath.common.domain.valueObject.AccountStatus;
    import co.istad.sambath.common.domain.valueObject.AccountTypeCode;
    import co.istad.sambath.common.domain.valueObject.Money;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import lombok.ToString;
    import org.jspecify.annotations.Nullable;
    import org.springframework.data.annotation.Id;
    import org.springframework.data.annotation.Transient;
    import org.springframework.data.domain.Persistable;
    import org.springframework.data.relational.core.mapping.Embedded;
    import org.springframework.data.relational.core.mapping.Table;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;
    import java.time.ZonedDateTime;
    import java.util.UUID;

    @Getter
    @Setter
    @NoArgsConstructor
    @Table(name = "accounts")
    @ToString
    public class AccountEntity implements Persistable<UUID> {

        @Id
        private UUID accountId;

        private UUID customerId;
        private UUID branchId;

        private String accountNumber;
        private String accountHolder;

        private AccountTypeCode accountTypeCode;
        private UUID accountTypeId;

    //    @Embedded(onEmpty = Embedded.OnEmpty.USE_EMPTY)
    //    private Money initialBalance;

        private BigDecimal balance;
        private String currency;

        private AccountStatus status;

        private LocalDateTime createdAt;
        private LocalDateTime  updatedAt;
        private String createdBy;
        private String updatedBy;

        @Transient
        private boolean isNew;

        @Override
        public @Nullable UUID getId() {
            return accountId;
        }

        @Override
        public boolean isNew() {
            return isNew;
        }
    }
