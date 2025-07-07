package idv.po.mtk_src.movie.web;

import idv.po.mtk_src.movie.domain.query.MovieReadModel;
import idv.po.mtk_src.movie.app.MovieReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movies/query")
@RequiredArgsConstructor
public class MovieQueryController {

  private final MovieReadService readService;

  @GetMapping("/ch/{name}")
  public ResponseEntity<List<? extends MovieReadModel>> queryMovieByChName(
      @PathVariable("name") String chMovieName) throws IOException {
    return resultBack(readService.findByChMovieName(chMovieName));
  }

  @GetMapping("/en/{name}")
  public ResponseEntity<List<? extends MovieReadModel>> queryMovieByEnName(
      @PathVariable("name") String enMovieName) throws IOException {
    return resultBack(readService.findByEnMovieName(enMovieName));
  }

  @GetMapping("/description")
  public ResponseEntity<List<? extends MovieReadModel>> queryMovieByDesc(
      @RequestParam String description) throws IOException {
    return resultBack(readService.findByDescriptionContaining(description));
  }

  @GetMapping("/keyword")
  public ResponseEntity<List<? extends MovieReadModel>> queryMovieByKeyword(
      @RequestParam String keyword) throws IOException {
    return resultBack(readService.searchMovies(keyword));
  }

  private ResponseEntity<List<? extends MovieReadModel>> resultBack(
      List<? extends MovieReadModel> result) {
    return CollectionUtils.isEmpty(result)
        ? ResponseEntity.notFound().build()
        : ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
