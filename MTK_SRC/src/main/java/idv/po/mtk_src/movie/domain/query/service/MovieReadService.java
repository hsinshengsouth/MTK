package idv.po.mtk_src.movie.domain.query.service;

import idv.po.mtk_src.movie.domain.query.readmodel.MovieInfo;
import idv.po.mtk_src.movie.domain.query.readmodel.MovieReadModel;
import idv.po.mtk_src.movie.domain.query.repositary.MovieReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;




@Service
@Component
@RequiredArgsConstructor
public class MovieReadService {

    private final MovieReadRepository movieReadRepository;

    public List<MovieReadModel> findByEnMovieName(String enMovieName) throws IOException {
        return movieReadRepository.findByEnMovieName(enMovieName);
    }


    public List<MovieReadModel> findByChMovieName(String chMovieName) throws IOException {
        return movieReadRepository.findByChMovieName(chMovieName);
    }


    public List<MovieReadModel> findByDescriptionContaining(String keyword) throws IOException {
        return movieReadRepository.findByDescriptionContaining(keyword);
    }


    public List<MovieReadModel> searchMovies(String keyword) throws IOException {
        return movieReadRepository.searchMovies(keyword);
    }



    public MovieInfo queryMovie(MovieReadModel query){
        MovieInfo info = new MovieInfo();



        return info;
    }



}
