package co.istad.sambat.pipeline_service.stream;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
public class RecordXml {

    @JsonProperty("RECID")
    private String recId;

    @JsonProperty("XMLDATA")
    @JsonDeserialize(using = XmlDataXmlDeserializer.class)
    private XmlData xmlData;


}
