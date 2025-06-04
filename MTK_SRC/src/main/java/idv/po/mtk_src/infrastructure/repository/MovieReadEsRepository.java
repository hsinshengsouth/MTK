package idv.po.mtk_src.infrastructure.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import idv.po.mtk_src.movie.domain.query.MovieReadModel;
import idv.po.mtk_src.movie.domain.query.MovieReadRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MovieReadEsRepository implements MovieReadRepository {


    private static final Logger logger = LoggerFactory.getLogger(MovieReadEsRepository.class);
    private static final String index = "movie";

    private final RestHighLevelClient client;
    private final SearchRequest searchRequest;
    private final SearchSourceBuilder sourceBuilder;
    private final MovieReadJpaRepository readJpaRepository;




    public MovieReadEsRepository(@Qualifier("customElasticsearchClient") RestHighLevelClient client, MovieReadJpaRepository repo) {
        this.client = client;
        this.searchRequest = new SearchRequest(index);
        this.sourceBuilder = new SearchSourceBuilder();
        this.readJpaRepository=repo;
        logger.info("Elasticsearch Connected: {}", client);
    }


    @Override
    public List<MovieReadModel> searchMovies(String keyword) throws IOException {
        sourceBuilder.query(QueryBuilders.multiMatchQuery(keyword, "enMovieName", "chMovieName", "description"));
        searchRequest.source(sourceBuilder);
        return executeSearch(searchRequest);
    }

    @Override
    public List<MovieReadModel> findByEnMovieName(String enMovieName) throws IOException {
        return multiMatchQuery(enMovieName, "movieName.enMovieName");
    }

    @Override
    public List<MovieReadModel> findByChMovieName(String chMovieName) throws IOException {
        return multiMatchQuery(chMovieName, "movieName.chMovieName");
    }

    @Override
    public List<MovieReadModel> findByDescriptionContaining(String description) throws IOException {
        return searchByField("description", description);
    }




    private List<MovieReadModel> multiMatchQuery(String value, String field) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery(value, field));
        searchRequest.source(sourceBuilder);
        return executeSearch(searchRequest);
    }


    private List<MovieReadModel> searchByField(String field, String value) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.matchQuery(field, value));
        searchRequest.source(sourceBuilder);
        return executeSearch(searchRequest);
    }


    private List<MovieReadModel> executeSearch(SearchRequest searchRequest) throws IOException {
        List<MovieReadModel> movies = new ArrayList<>();
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        response.getHits().forEach(hit -> {
            MovieReadModel movie = objectMapper().convertValue(hit.getSourceAsMap(), MovieReadModel.class);
            movie.setMovieID(hit.getId());
            movies.add(movie);
        });

        return movies;
    }

    @Override
    public void saveMovie(MovieReadModel movie) {
        readJpaRepository.save(movie);
    }

    @Override
    public Optional<MovieReadModel> findById(UUID movieId) {
        return readJpaRepository.findById(movieId);
    }


    private ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

}
