package org.fintecy.md.ecb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.failsafe.Policy;
import org.fintecy.md.ecb.serialization.EcbModule;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

class EcbClientBuilder {
    private final List<Policy<Object>> policies = new ArrayList<>();
    private HttpClient client = HttpClient.newHttpClient();
    private String rootPath = EcbApi.ROOT_PATH;
    private ObjectMapper mapper = defaultMapper();

    private static ObjectMapper defaultMapper() {
        return new XmlMapper().registerModule(new EcbModule());
    }

    public EcbClientBuilder useClient(HttpClient client) {
        this.client = client;
        return this;
    }

    public EcbClientBuilder rootPath(String rootPath) {
        this.rootPath = rootPath;
        return this;
    }

    public EcbClientBuilder mapper(XmlMapper mapper) {
        this.mapper = mapper;
        return this;
    }

    public EcbClientBuilder with(Policy<Object> policy) {
        this.policies.add(policy);
        return this;
    }

    public EcbApi build() {
        return new EcbClient(rootPath, client, mapper, policies);
    }
}
