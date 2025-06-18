package idv.po.mtk_src.movie.web;


import idv.po.mtk_src.movie.domain.command.AddMovieCommand;
import idv.po.mtk_src.movie.app.MovieCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies/command")
public class MovieCommandController {

    private final MovieCommandService commandService;

    @PostMapping("/addMovie")
    public ResponseEntity<?> addMovie(
            @RequestBody AddMovieCommand movieCommand
    ){
        commandService.addMovie(movieCommand);
        return ResponseEntity.status(HttpStatus.OK).body("Add a new movie successfully");
    }


}
