package idv.po.mtk_src.movie.domain.model;

import idv.po.mtk_src.movie.domain.valueobject.Actor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Movie {

    private Long movieID;

    private String movieName;

    private String  brief;

    private String director;

    private List<Actor> actors;

    private char movieStatus;

    private BigDecimal playTime;

    private Date startTime;

    private Date endTime;


    public Movie() {
    }
}
