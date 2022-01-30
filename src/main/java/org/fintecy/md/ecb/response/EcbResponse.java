package org.fintecy.md.ecb.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;


@JsonIgnoreProperties(ignoreUnknown = true)
public class EcbResponse {
    @JacksonXmlElementWrapper(localName = "Cube")
    @JacksonXmlProperty(localName = "Cube")
    private List<TimePoint> timeCubes;

    public Map<LocalDate, TimePoint> getTimePoints() {
        return timeCubes.stream().collect(Collectors.toMap(TimePoint::getDate, identity()));
    }

    public void setTimePoints(List<TimePoint> timeCubes) {
        this.timeCubes = timeCubes;
    }
}
