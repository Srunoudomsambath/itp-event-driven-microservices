package co.istad.sambath.common.domain.valueObject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

// global value object use together
//public record CustomerId(
//        UUID value
//) {
//}

@Getter
@EqualsAndHashCode
public class CustomerId {
    private final UUID value;

    @JsonCreator
    public CustomerId(@JsonProperty("value") UUID value) {
        this.value = value;
    }

    // Keep the String constructor for HTTP deserialization
    public CustomerId(String value) {
        this.value = UUID.fromString(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}



//public record CustomerId(UUID customerId) {
//
//}
