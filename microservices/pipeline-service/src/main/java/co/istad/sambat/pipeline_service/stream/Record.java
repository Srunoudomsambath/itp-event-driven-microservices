package co.istad.sambat.pipeline_service.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EnableWebSecurity
public class Record {

    @JsonProperty("RECID")
    private String recid;

    @JsonProperty("XMLDATA")
    @JsonDeserialize(using = DataXmlDeserializer.class)
    private Data xmldata;
}
