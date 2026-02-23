package co.istad.sambat.pipeline_service.stream;


import lombok.Data;

@Data
public class DebeziumEnvelope<T> {
    private String op;  // "c", "r", "u", "d"
    private T before;
    private T after;
}
