package idv.po.mtk_src.movie.infra;

import idv.po.mtk_src.infrastructure.jpa.MovieCommandJpaRepository;
import idv.po.mtk_src.movie.domain.command.MovieCommandRepository;
import idv.po.mtk_src.movie.domain.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MovieCommandRepositoryImpl implements MovieCommandRepository {

  private final MovieCommandJpaRepository movieJpaRepository;

  @Override
  public void addMovie(Movie movie) {
    movieJpaRepository.save(movie);
  }
}
