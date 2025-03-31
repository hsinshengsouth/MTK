package idv.po.mtk_src.management.domain.repository;

import idv.po.mtk_src.management.domain.department.Department;
import idv.po.mtk_src.management.domain.department.DepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaDepartmentRepository extends JpaRepository<Department, Integer>, DepartmentRepository {
    List<Department> findAll();
    Optional<Department>findByDeptId( Integer deptId);
    Department save(Department dept);
    Department update(Department dept);
}
