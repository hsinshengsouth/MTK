package idv.po.mtk_src.movie.domain.query.repositary;

import idv.po.mtk_src.movie.domain.query.readmodel.MovieReadModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MovieReadRepository  extends ElasticsearchRepository<MovieReadModel,String> {





}
