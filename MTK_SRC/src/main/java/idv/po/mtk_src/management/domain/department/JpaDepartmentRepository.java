package idv.po.mtk_src.management.domain.department;

import idv.po.mtk_src.infrastructure.repository.SpringDataJpaDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaDepartmentRepository implements DepartmentRepository{

    private final SpringDataJpaDepartmentRepository departmentRepo;

    @Override
    public List<Department> findAll() {
        return departmentRepo.findAll();
    }

    @Override
    public Optional<Department> findByDeptId(Integer deptId) {
        return departmentRepo.findByDeptId(deptId);
    }

    @Override
    public Department save(Department dept) {
        return departmentRepo.save(dept);
    }
}
