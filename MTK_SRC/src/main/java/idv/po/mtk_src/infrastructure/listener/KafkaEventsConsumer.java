package idv.po.mtk_src.infrastructure.listener;

import idv.po.mtk_src.movie.domain.command.ShowtimeRepository;
import idv.po.mtk_src.movie.domain.event.MovieCreatedEvent;
import idv.po.mtk_src.movie.domain.event.ShowtimeCreatedEvent;
import idv.po.mtk_src.movie.domain.model.ShowTime;
import idv.po.mtk_src.movie.domain.query.MovieReadModel;
import idv.po.mtk_src.movie.domain.query.MovieReadRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KafkaEventsConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEventsConsumer.class);

    private final MovieReadRepository movieReadRepository;
    private final ShowtimeRepository showtimeRepository;


    @KafkaListener(
            topics = {"movie-created"},
            groupId = "movie-group",
            containerFactory = "movieKafkaListenerContainerFactory"
    )
    public void handleMovieCreated(MovieCreatedEvent event) {
        MovieReadModel readModel=
                MovieReadModel.builder()
                        .movieName(
                                new MovieReadModel.MovieName(
                                        event.enTitle(),
                                        event.chTitle(),
                                        event.keyword()
                                )
                        )
                        .genres(event.genres())
                        .director(event.director())
                        .actors(event.actors())
                        .description(event.description())
                        .rating(event.rating())
                        .runtimeMinutes(event.runtimeMinutes())
                        .posterUrl(event.posterUrl())
                        .movieStatus(event.movieStatus())
                        .startDate(event.startDate())
                        .endDate(event.endDate())
                        .showtime(new ArrayList<>())
                        .build();

        movieReadRepository.saveMovie(readModel);

    }



    @KafkaListener(
            topics = "showtime-created",
            groupId = "movie-group",
            containerFactory = "showtimeKafkaListenerContainerFactory"
    )
    public void handleShowtimeCreated(ShowtimeCreatedEvent event) {
        Optional<MovieReadModel> movieRead =
                movieReadRepository.findById(event.movie().getMovieId());

       Optional<ShowTime> showTime=
               showtimeRepository.findByShowTimeId(event.showtimeId());


        if (movieRead.isPresent()&&showTime.isPresent()) {
            MovieReadModel model = movieRead.get();
            ShowTime show=showTime.get();

            List<MovieReadModel.Showtime> updatedShowtimes = new ArrayList<>(model.getShowtime());
            updatedShowtimes.add(
                    MovieReadModel.Showtime.builder()
                        .screenName(show.getScreen().getScreenName())
                        .theaterName(show.getTheater().getTheaterName())
                        .dateTime(event.dateTime())
                        .price(event.price())
                        .build()
            );

            model.setShowtime(updatedShowtimes);
            movieReadRepository.saveMovie(model);

        } else {
            logger.warn("Movie ID [{}] not found for showtime. Might be eventual consistency issue.",
                    event.movie().getMovieId()
            );
        }
    }



}
