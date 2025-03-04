package idv.po.mtk_src.movie.domain.query.controller;


import idv.po.mtk_src.movie.domain.query.readmodel.MovieInfo;
import idv.po.mtk_src.movie.domain.query.readmodel.MovieReadModel;
import idv.po.mtk_src.movie.domain.query.service.MovieReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/query")
public class MovieQueryController {
    @Autowired
    private MovieReadService queryService;

    @GetMapping("/movieInfo")
    public ResponseEntity<MovieInfo> queryMovie(@RequestBody MovieReadModel query){
        var info =queryService.queryMovie(query);
        return info==null?
                    ResponseEntity.notFound().build():
                    ResponseEntity.status(HttpStatus.OK).body(info);
    }






}
