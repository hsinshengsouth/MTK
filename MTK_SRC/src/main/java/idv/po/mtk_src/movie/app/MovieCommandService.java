package idv.po.mtk_src.movie.app;

import idv.po.mtk_src.infrastructure.utils.KafkaEventPublisher;
import idv.po.mtk_src.movie.domain.command.*;
import idv.po.mtk_src.movie.domain.model.Movie;
import idv.po.mtk_src.movie.domain.model.Screen;
import idv.po.mtk_src.movie.domain.model.ShowTime;
import idv.po.mtk_src.movie.domain.model.Theater;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieCommandService {

    private final MovieCommandRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;
    private final ShowtimeRepository showtimeRepository;
    private final KafkaEventPublisher kafkaPublisher;

    /*
    * 新增電影資訊和場次
    * @param AddMovieCommand command
    * */
    @Transactional
    public void addMovie(AddMovieCommand command){
        //1.建立Movie 持久層 和 事件 到 query side
        Movie movie =Movie.createFromCommand(command);
        movieRepository.addMovie(movie);
        kafkaPublisher.publish(
                "movie-created",
                movie.getMovieId().toString(),
                movie.createMovieCreatedEvent()
        );

        // 2.建立ShowTime 持久層
        List<ShowTime> showtimes = new ArrayList();
        //3.ShowTime事件 到 query side
        List<ProducerRecord<String, Object>> kafkaRecords = new ArrayList<>();

        for(AddMovieCommand.Showtime cmdShowtime :command.showtimes()){
            Theater theater =theaterRepository.findByTheaterId(
                    cmdShowtime.theaterId()
            ).orElseThrow(() -> new IllegalArgumentException("Theater not found"));

            Screen screen = screenRepository.findByScreenId(
                    cmdShowtime.screenId()
            ).orElseThrow(() -> new IllegalArgumentException("Screen not found"));

            ShowTime show = ShowTime.builder()
                    .movie(movie)
                    .theater(theater)
                    .screen(screen)
                    .dateTime(cmdShowtime.dateTime())
                    .price(cmdShowtime.price())
                    .build();

            showtimes.add(show);
        }

        if(!CollectionUtils.isEmpty(showtimes)) {
            showtimeRepository.saveAll(showtimes);

            //3-1 事件觸發
            showtimes.forEach(show -> {
                ProducerRecord<String, Object> record =
                        new ProducerRecord<>(
                                "showtime-created",
                                show.getShowtimeId().toString(),
                                show.createShowtimeCreatedEvent()
                        );
                kafkaRecords.add(record);
            });

            kafkaPublisher.publishAll(kafkaRecords);

        }

    }



}