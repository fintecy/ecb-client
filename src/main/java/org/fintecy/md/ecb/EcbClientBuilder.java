package org.fintecy.md.ecb;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.failsafe.Policy;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

class EcbClientBuilder {
    private final List<Policy<Object>> policies = new ArrayList<>();
    private HttpClient client = HttpClient.newHttpClient();
    private String rootPath = EcbApi.ROOT_PATH;
    private XmlMapper mapper = defaultMapper();

    private static XmlMapper defaultMapper() {
        var mapper = new XmlMapper();
        final var customModule = new SimpleModule();
        mapper.registerModule(customModule);
        return mapper;
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
