package idv.po.mtk_src.movie.domain.query.repository;

import idv.po.mtk_src.movie.domain.query.readmodel.MovieReadModel;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface MovieReadRepository  {

    /*
     * @param keyword
     * @method searchMovies
     * */
    List<MovieReadModel> searchMovies(String keyword) throws IOException;

    /*
     * @param String enMovieName
     * @method findByEnMovieName
     * */
    List<MovieReadModel> findByEnMovieName(String enMovieName) throws IOException;

    /*
     * @param String chMovieName
     * @method findByChMovieName
     * */
    List<MovieReadModel>findByChMovieName(String chMovieName) throws IOException;

    /*
     * @param String description
     * @method findByDescriptionContaining
     * */
    List<MovieReadModel>findByDescriptionContaining(String description) throws IOException;








}
