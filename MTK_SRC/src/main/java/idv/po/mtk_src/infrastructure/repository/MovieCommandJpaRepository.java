package idv.po.mtk_src.infrastructure.repository;

import idv.po.mtk_src.movie.domain.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface MovieCommandJpaRepository extends JpaRepository<Movie,String> {


}
