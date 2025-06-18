package idv.po.mtk_src.management.infra;

import idv.po.mtk_src.infrastructure.jpa.DepartmentJpaRepository;
import idv.po.mtk_src.management.domain.department.Department;
import idv.po.mtk_src.management.domain.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final DepartmentJpaRepository departmentRepo;

    @Override
    public List<Department> findAll() {
        return departmentRepo.findAll();
    }

    @Override
    public Optional<Department> findByDeptId(Integer deptId) {
        return departmentRepo.findById(deptId);
    }

    @Override
    public Department saveDept(Department dept) {
        return departmentRepo.save(dept);
    }
}
