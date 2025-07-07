package idv.po.mtk_src.infrastructure.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.erhlc.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "idv.po.mtk_src.infrastructure.jpa")
public class ElasticsearchConfiguration extends AbstractElasticsearchConfiguration {

  @Value("${mtk.elasticsearch.hostname}")
  private String elasticsearchUrl;

  @Value("${mtk.elasticsearch.port}")
  private Integer port;

  private static final String scheme = "http";

  @Bean(name = "customElasticsearchClient")
  public RestHighLevelClient elasticsearchClient() {
    return new RestHighLevelClient(
        RestClient.builder(new HttpHost(elasticsearchUrl, port, scheme)));
  }
}
