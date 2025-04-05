package idv.po.mtk_src.movie.domain.query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;




@Service
@Component
public class MovieReadService {

    private final MovieReadRepository movieReadRepository;


    public MovieReadService(MovieReadRepository movieReadRepository) {
        this.movieReadRepository = movieReadRepository;
    }


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
