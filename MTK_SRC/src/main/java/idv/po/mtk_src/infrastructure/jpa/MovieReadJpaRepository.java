package idv.po.mtk_src.infrastructure.jpa;

import idv.po.mtk_src.movie.domain.query.MovieReadModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface MovieReadJpaRepository extends ElasticsearchRepository<MovieReadModel, UUID> {
}
