package co.istad.sambat.pipeline_service.stream;

public class DataXmlDeserializer extends XmlStringDeserializer<Data>{
    public DataXmlDeserializer(){
        super(Data.class);
    }
}