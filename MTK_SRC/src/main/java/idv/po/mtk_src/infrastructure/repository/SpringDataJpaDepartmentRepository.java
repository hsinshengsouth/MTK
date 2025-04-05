package idv.po.mtk_src.infrastructure.repository;

import idv.po.mtk_src.management.domain.department.Department;
import idv.po.mtk_src.management.domain.department.DepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;
@EnableJpaRepositories
public interface SpringDataJpaDepartmentRepository extends JpaRepository<Department, Integer>, DepartmentRepository {
    List<Department> findAll();
    Optional<Department>findByDeptId( Integer deptId);
    Department save(Department dept);

}
