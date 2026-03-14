package co.istad.sambath.common.domain.valueObject;

import co.istad.sambath.common.domain.exception.DomainException;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

@Embeddable
public record Money(
        BigDecimal amount,
        @Enumerated(EnumType.STRING)  //
        Currency currency
){

    public boolean isLessthan(Money otherAmount){

        checkSameCurrency(otherAmount.currency);

        return this.amount.compareTo(otherAmount.amount) < 0;
    }

    public boolean isGreaterThan(Money otherAmount){
        checkSameCurrency(otherAmount.currency);
        return this.amount.compareTo(otherAmount.amount) > 0;
    }

    // check currency to be the same
    public void checkSameCurrency(Currency otherCurrency){
        if(this.currency != otherCurrency){
            throw new DomainException("Currency doesn't match");
        };
    }

    public Money add(Money other) {

        checkSameCurrency(other.currency());

        return new Money(
                this.amount.add(other.amount),
                this.currency
        );
    }

    public Money subtract(Money other) {
        checkSameCurrency(other.currency());

        return new Money(this.amount.subtract(other.amount), this.currency);
    }

}
