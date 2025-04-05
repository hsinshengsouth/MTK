package idv.po.mtk_src.movie.domain.query;


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
    public ResponseEntity<List<? extends MovieInfo>> queryMovieByChName(@PathVariable("name") String chMovieName) throws IOException {
        return resultBack(readService.findByChMovieName(chMovieName));
    }

    @GetMapping("/en/{name}")
    public ResponseEntity<List<? extends MovieInfo>> queryMovieByEnName(@PathVariable("name") String enMovieName) throws IOException {
         return resultBack(readService.findByEnMovieName(enMovieName));
    }

    @GetMapping("/description")
    public ResponseEntity<List<? extends MovieInfo>> queryMovieByDesc(@RequestParam String description) throws IOException {
        return resultBack(readService.findByDescriptionContaining(description));
    }

    @GetMapping("/keyword")
    public ResponseEntity<List<? extends MovieInfo>> queryMovieByKeyword(@RequestParam String keyword)  throws IOException {
        return resultBack(readService.searchMovies(keyword));
    }

    private ResponseEntity<List<? extends MovieInfo>> resultBack(List<? extends MovieInfo> result) {
        return CollectionUtils.isEmpty(result) ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
