package idv.po.mtk_src.movie.domain.command.repoimpl;

import idv.po.mtk_src.infrastructure.repository.ScreenJpaRepository;
import idv.po.mtk_src.movie.domain.command.ScreenRepository;
import idv.po.mtk_src.movie.domain.model.Screen;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScreenRepositoryImpl implements ScreenRepository {


    private  final ScreenJpaRepository screenRepo;

    @Override
    public Optional<Screen> findByTheaterIdAndTheaterName(String theaterId, String theaterName) {
        return screenRepo.findByTheaterIdAndTheaterName(theaterId,theaterName);
    }
}
