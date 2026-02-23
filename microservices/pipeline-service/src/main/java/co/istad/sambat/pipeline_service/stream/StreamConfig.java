package co.istad.sambat.pipeline_service.stream;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import t2.CORE_BANKING.RECORD_XML.Envelope;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StreamConfig {

    private final XmlMapper xmlMapper = new XmlMapper();
    private final ObjectMapper objectMapper;

    // Supplier for producing message into kafka topic
    // Function for processing message and send to destination kafka topic
    // Consumer for consuming message from kafka topic

    @Bean
    public Consumer<Message<Envelope>> captureEnvelope(){
        return record-> {
            System.out.println("Dbz envelope: " + record);
        };
    }


    @Bean
    public Consumer<Message<Object>> consumeDbzEvent(ObjectMapper objectMapper) {
        return record -> {
            try {
                DebeziumEnvelope<Record> capturedRecord =
                        objectMapper.readValue(record.getPayload().toString(),
                                new TypeReference<>(){});
                 switch (capturedRecord.getOp()) {
                    case "r", "c" -> {
                        System.out.println("Prepare to insert new record");
                        Record after = capturedRecord.getAfter();
                        System.out.println(after.getXmldata().getName());
                    }
                    case "u" -> {
                        System.out.println("Prepare to update existing record");
                        Record after = capturedRecord.getAfter();
                        System.out.println("Updated: " + after.getXmldata().getName());
                    }
                    case "d" -> {
                        System.out.println("Prepare to delete existing record");
                        System.out.println("Delete ID = " + capturedRecord.getBefore().getRecid());
                    }
                    default -> throw new IllegalStateException("Invalid Operation..!");
                };
            } catch (JsonProcessingException e) {
                System.out.println("Error deserialized");
                throw new RuntimeException("Error deserialized");
            }
        };
    }



    @Bean
    public Consumer<GenericRecord> processOrder() {
        return record -> {
            try {
                log.info("=== CDC Event Received ===");

                // Get operation type
                String operation = record.get("op").toString();
                log.info("Operation: {}", operation);

                // Get the "after" data
                GenericRecord after = (GenericRecord) record.get("after");

                if (after != null) {
                    log.info("Order ID: {}", after.get("order_id"));
                    log.info("Customer: {}", after.get("customer_name"));
                    log.info("Product: {}", after.get("product_name"));
                    log.info("Quantity: {}", after.get("quantity"));
                    log.info("Price: {}", after.get("price"));
                    log.info("Created At: {}", after.get("created_at"));
                }

                // Get the "before" data (for updates/deletes)
                GenericRecord before = (GenericRecord) record.get("before");
                if (before != null) {
                    log.info("Before - Customer: {}", before.get("customer_name"));
                }

                log.info("==========================");

            } catch (Exception e) {
                log.error("Error processing CDC event: ", e);
                log.error("Record schema: {}", record.getSchema());
            }
        };
    }

    @Bean
    public Function<GenericRecord,Object> processOrderDetail() {
        return record -> {
            try {
                log.info("=== CDC Event Received ===");

                // Get operation type
                String operation = record.get("op").toString();
                log.info("Operation: {}", operation);

                // Get the "after" data
                GenericRecord after = (GenericRecord) record.get("after");

                if (after != null) {
                    log.info("Order ID: {}", after.get("order_id"));
                    log.info("Customer: {}", after.get("customer_name"));
                    log.info("Product: {}", after.get("product_name"));
                    log.info("Quantity: {}", after.get("quantity"));
                    log.info("Price: {}", after.get("price"));
                    log.info("Created At: {}", after.get("created_at"));
                }

                // Get the "before" data (for updates/deletes)
                GenericRecord before = (GenericRecord) record.get("before");
                if (before != null) {
                    log.info("Before - Customer: {}", before.get("customer_name"));
                }

                log.info("==========================");
                return after.get("order_id");

            } catch (Exception e) {
                log.error("Error processing CDC event: ", e);
                log.error("Record schema: {}", record.getSchema());
            }
        return record;
        };
    }

    @Bean
    public Function<Product,Product> processProductDetail(){
        return product -> {
            System.out.println("Old Product: " + product.getCode());
            System.out.println("Old Product: " + product.getQty());
            // Processing
            product.setCode("ISTAD-" + product.getCode());
            return product;
        };
    }

    @Bean
    public Consumer<Product> processProduct() {
        return product -> {
            System.out.println("obj product: " + product.getCode());
            System.out.println("obj product: " + product.getQty());
        };
    }

    // A simple processor: Takes a string, makes it uppercase, and sends it on
    @Bean
    public Consumer<String> processMessage() {
        return input -> {
            System.out.println("Processing: " + input);
        };
    }
    @Bean
    public Consumer<GenericRecord> processOracle() {
        return record -> {
            log.info("RECEIVED RECORD: {}", record);
            DebeziumEnvelope<RecordXml> recordXmlDebeziumEnvelope = null;
            try {
                recordXmlDebeziumEnvelope = objectMapper.readValue(
                        record.toString(), new TypeReference<>() {}
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            log.info("MAPPED: {}", recordXmlDebeziumEnvelope);
        };
    }

//    @Bean
//    public Consumer<GenericRecord> processOracle() {
//        return record -> {
//            try {
//                GenericRecord after = (GenericRecord) record.get("after");
//                if (after == null) return;
//
//                Object xmlValue = after.get("XMLDATA");
//
//                if (xmlValue != null) {
//                    String str = xmlValue.toString();
//
//                    XmlData xmlData = xmlMapper.readValue(str, XmlData.class);
//                    log.info("XML POJO: {}", xmlData);
//                }
//            } catch (Exception e) {
//                log.error("Error processing Oracle event: ", e);
//            }
//        };
//    }
}
