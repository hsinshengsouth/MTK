package idv.po.mtk_src.movie.domain.query.controller;


import idv.po.mtk_src.movie.domain.query.readmodel.MovieInfo;
import idv.po.mtk_src.movie.domain.query.service.MovieReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/query")
@RequiredArgsConstructor
public class MovieQueryController {

    private final MovieReadService readService;

    @GetMapping("/movieInfo/{chMovieName}")
    public ResponseEntity<List<? extends MovieInfo>> queryMovieByChName(@PathVariable("chMovieName") String chMovieName) throws IOException {
        return resultBack(readService.findByChMovieName(chMovieName));
    }

    @GetMapping("/movieInfo/{enMovieName}")
    public ResponseEntity<List<? extends MovieInfo>> queryMovieByEnName(@PathVariable("enMovieName") String enMovieName) throws IOException {
        return resultBack(readService.findByEnMovieName(enMovieName));
    }

    @GetMapping("/movieInfo//{description}")
    public ResponseEntity<List<? extends MovieInfo>> queryMovieByDesc(@PathVariable("description") String description) throws IOException {
        return resultBack(readService.findByDescriptionContaining(description));
    }

    @GetMapping("/movieInfo/{keyword}")
    public ResponseEntity<List<? extends MovieInfo>> queryMovieByKeyword(@PathVariable("keyword") String keyword) throws IOException {
        return resultBack(readService.searchMovies(keyword));
    }

    private ResponseEntity<List<? extends MovieInfo>> resultBack(List<? extends MovieInfo> result) {
        return CollectionUtils.isEmpty(result) ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
