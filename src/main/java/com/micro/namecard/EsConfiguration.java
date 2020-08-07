package com.micro.namecard;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class EsConfiguration extends AbstractElasticsearchConfiguration {

    @Value(value="${es.bootstrap.server}")
    private String elasticBootServer;

    /**
     * Elastic Search Client Configuration and Creation.
     */
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticBootServer)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
