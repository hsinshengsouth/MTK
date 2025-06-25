package idv.po.mtk_src.infrastructure.listener;

import idv.po.mtk_src.booking.vo.BookingSuccessEvent;
import idv.po.mtk_src.infrastructure.message.MessageService;
import idv.po.mtk_src.movie.domain.command.ScreenRepository;
import idv.po.mtk_src.movie.domain.command.ShowtimeRepository;
import idv.po.mtk_src.movie.domain.command.TheaterRepository;
import idv.po.mtk_src.movie.domain.event.MovieCreatedEvent;
import idv.po.mtk_src.movie.domain.event.ShowtimeCreatedEvent;
import idv.po.mtk_src.movie.domain.model.Screen;
import idv.po.mtk_src.movie.domain.model.ShowTime;
import idv.po.mtk_src.movie.domain.model.Theater;
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
    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;
    private final MessageService messageService;

    @KafkaListener(
            topics = {"movie-created"},
            groupId = "movie-group",
            containerFactory = "movieKafkaListenerContainerFactory"
    )
    public void handleMovieCreated(MovieCreatedEvent event) {
        MovieReadModel readModel=
                MovieReadModel.builder()
                        .movieID(event.movieId().toString())
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
                movieReadRepository.findById(event.movieId());

       Optional<ShowTime> showTime=
               showtimeRepository.findByShowTimeId(event.showtimeId());


        if (movieRead.isPresent()&&showTime.isPresent()) {
            MovieReadModel model = movieRead.get();
            ShowTime show=showTime.get();

            Theater theater = theaterRepository.findByTheaterId(show.getTheater().getTheaterId())
                    .orElseThrow(() -> new IllegalStateException("Theater not found"));

            Screen screen = screenRepository.findByScreenId(show.getScreen().getScreenId())
                    .orElseThrow(() -> new IllegalStateException("Screen not found"));


            List<MovieReadModel.Showtime> updatedShowtimes = new ArrayList<>(model.getShowtime());
            updatedShowtimes.add(
                    MovieReadModel.Showtime.builder()
                        .screenName(screen.getScreenName())
                        .theaterName(theater.getTheaterName())
                        .dateTime(event.dateTime())
                        .price(event.price())
                        .build()
            );

            model.setShowtime(updatedShowtimes);
            movieReadRepository.saveMovie(model);

        } else {
            logger.warn("Movie ID [{}] not found for showtime. Might be eventual consistency issue.",
                    event.movieId()
            );
        }
    }



    @KafkaListener(topics = "booking-success",
                   groupId = "email-group",
                   containerFactory ="bookingKafkaListenerContainerFactory")
    public void onBookingSuccess(BookingSuccessEvent event) {
        messageService.sendBookingSuccessEmail(event);
    }





}
