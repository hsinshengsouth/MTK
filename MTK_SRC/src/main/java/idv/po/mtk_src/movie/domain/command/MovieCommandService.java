package idv.po.mtk_src.movie.domain.command;

import idv.po.mtk_src.movie.domain.model.Movie;
import idv.po.mtk_src.movie.domain.model.Screen;
import idv.po.mtk_src.movie.domain.model.ShowTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieCommandService {

    private final MovieCommandRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final ShowtimeRepository showtimeRepository;


    public void addMovie(AddMovieCommand command){

        Movie movie =Movie.createFromCommand(command);
        movieRepository.addMovie(movie);

        // Save Showtimes
        List<ShowTime> showtime = command.showtimes().stream().map(cmdShowtime -> {
            Screen screen = screenRepository.findByTheaterIdAndTheaterName(
                    cmdShowtime.theaterId(), cmdShowtime.theaterName()
            ).orElseThrow(() -> new IllegalArgumentException("Screen not found"));

            return ShowTime.builder()
                    .movie(movie)
                    .screen(screen)
                    .dateTime(cmdShowtime.dateTime())
                    .price(cmdShowtime.price())
                    .build();
        }).toList();


        showtimeRepository.saveAll(showtime);


    }
}