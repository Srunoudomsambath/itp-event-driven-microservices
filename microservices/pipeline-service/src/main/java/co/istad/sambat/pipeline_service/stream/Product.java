package co.istad.sambat.pipeline_service.stream;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
    private String code;
    private Integer qty;
}
