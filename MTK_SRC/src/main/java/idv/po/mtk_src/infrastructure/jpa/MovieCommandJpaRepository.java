package idv.po.mtk_src.infrastructure.jpa;

import idv.po.mtk_src.movie.domain.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieCommandJpaRepository extends JpaRepository<Movie,String> {


}
