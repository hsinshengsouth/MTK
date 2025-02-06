package idv.po.mtk_src.movie.domain.query.controller;


import idv.po.mtk_src.movie.domain.query.queryobject.MovieInfo;
import idv.po.mtk_src.movie.domain.query.queryobject.MovieQuery;
import idv.po.mtk_src.movie.domain.query.service.MovieQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/queryMovie")
public class MovieQueryController {
    @Autowired
    private MovieQueryService queryService;

    @GetMapping("/movieInfo")
    public ResponseEntity<MovieInfo> queryMovie(@RequestBody MovieQuery query){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(queryService.queryMovie(query));
    }






}
