package org.fintecy.md.ecb.serialization;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.fintecy.md.ecb.response.TimePoint;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EcbModule extends SimpleModule {
    public EcbModule() {
        super(EcbModule.class.getSimpleName());
        addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        addDeserializer(TimePoint.class, new TimePointDeserializer());
    }
}
