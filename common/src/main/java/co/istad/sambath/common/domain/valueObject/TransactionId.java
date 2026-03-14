package co.istad.sambath.common.domain.valueObject;

import java.util.UUID;

public record TransactionId(
        UUID value
) {
    @Override
    public String toString() {
        return value.toString();
    }
}
