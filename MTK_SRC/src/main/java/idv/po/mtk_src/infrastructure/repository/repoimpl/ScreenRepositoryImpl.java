package idv.po.mtk_src.infrastructure.repository.repoimpl;

import idv.po.mtk_src.infrastructure.repository.ScreenJpaRepository;
import idv.po.mtk_src.movie.domain.command.ScreenRepository;
import idv.po.mtk_src.movie.domain.model.Screen;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ScreenRepositoryImpl implements ScreenRepository {


    private  final ScreenJpaRepository screenRepo;

    @Override
    public Optional<Screen> findByScreenId(UUID screenId) {
        return screenRepo.findByScreenId(screenId);
    }


}
