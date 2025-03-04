package idv.po.mtk_src.movie.domain.query.service;

import idv.po.mtk_src.movie.domain.query.readmodel.MovieInfo;
import idv.po.mtk_src.movie.domain.query.readmodel.MovieReadModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class MovieReadService {





    public MovieInfo queryMovie(MovieReadModel query){
        MovieInfo info = new MovieInfo();



        return info;
    }



}
