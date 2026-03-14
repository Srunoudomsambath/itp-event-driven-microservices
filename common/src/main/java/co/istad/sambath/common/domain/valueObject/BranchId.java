package co.istad.sambath.common.domain.valueObject;

import java.util.UUID;

public record BranchId(
        UUID branchId

) {
    @Override
    public String toString() {
        return branchId.toString();
    }
}
