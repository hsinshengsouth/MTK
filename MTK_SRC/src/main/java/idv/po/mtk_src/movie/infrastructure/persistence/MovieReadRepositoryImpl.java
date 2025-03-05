package idv.po.mtk_src.movie.infrastructure.persistence;

import idv.po.mtk_src.movie.domain.query.readmodel.MovieReadModel;
import idv.po.mtk_src.movie.domain.query.repositary.MovieReadRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieReadRepositoryImpl implements MovieReadRepository {


    private static final Logger logger = LoggerFactory.getLogger(MovieReadRepositoryImpl.class);


    private final RestHighLevelClient client;


    public MovieReadRepositoryImpl(RestHighLevelClient client) {
        this.client = client;
        logger.info("Elasticsearch Connected: {}", client);
    }


    @Override
    public List<MovieReadModel> searchMovies(String keyword) throws IOException {
        SearchRequest searchRequest = new SearchRequest("movie");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery(keyword, "enMovieName", "chMovieName", "description"));
        searchRequest.source(sourceBuilder);

        return executeSearch(searchRequest);
    }

    @Override
    public List<MovieReadModel> findByEnMovieName(String enMovieName) throws IOException {
        return searchByField("enMovieName", enMovieName);
    }

    @Override
    public List<MovieReadModel> findByChMovieName(String chMovieName) throws IOException {
        return searchByField("chMovieName", chMovieName);
    }

    @Override
    public List<MovieReadModel> findByDescriptionContaining(String description) throws IOException {
        return searchByField("description", description);
    }


    private List<MovieReadModel> searchByField(String field, String value) throws IOException {
        SearchRequest searchRequest = new SearchRequest("movie");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.matchQuery(field, value));
        searchRequest.source(sourceBuilder);

        return executeSearch(searchRequest);
    }

    private List<MovieReadModel> executeSearch(SearchRequest searchRequest) throws IOException {
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        List<MovieReadModel> movies = new ArrayList<>();
        response.getHits().forEach(hit -> movies.add(
                MovieReadModel.builder()
                        .movieID(hit.getId())
                        .enMovieName((String) hit.getSourceAsMap().get("enMovieName"))
                        .chMovieName((String) hit.getSourceAsMap().get("chMovieName"))
                        .description((String) hit.getSourceAsMap().get("description"))
                        .genres((List<String>) hit.getSourceAsMap().get("genres"))
                        .actors((List<String>) hit.getSourceAsMap().get("actors"))
                        .rating(((Number) hit.getSourceAsMap().get("rating")).floatValue())
                        .runtimeMinutes((Integer) hit.getSourceAsMap().get("runtimeMinutes"))
                        .posterUrl((String) hit.getSourceAsMap().get("posterUrl"))
                        .movieStatus((String) hit.getSourceAsMap().get("movieStatus"))
                        .build()
        ));
        return movies;
    }


}
