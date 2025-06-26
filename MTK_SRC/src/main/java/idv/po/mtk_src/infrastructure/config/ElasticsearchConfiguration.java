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

  @Value("${spring.elasticsearch.uris}")
  private String elasticsearchUrl;

  /* @Override
      @Bean(name = "customElasticsearchClient")
      public RestHighLevelClient elasticsearchClient() {
          final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                  .connectedTo(elasticsearchUrl)
                  .withConnectTimeout(5000)    // 5 seconds
                  .withSocketTimeout(30000)    // 30 seconds
                  .build();
          return RestClients.create(clientConfiguration).rest();
      }    ;
  */

  @Bean(name = "customElasticsearchClient")
  public RestHighLevelClient elasticsearchClient() {
    return new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
  }
}
