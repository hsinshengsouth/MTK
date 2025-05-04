package idv.po.mtk_src.infrastructure.repository;

import idv.po.mtk_src.movie.domain.query.MovieReadModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface MovieReadJpaRepository extends ElasticsearchRepository<MovieReadModel, UUID> {
}
