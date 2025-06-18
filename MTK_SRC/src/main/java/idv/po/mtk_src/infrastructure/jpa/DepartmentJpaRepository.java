package idv.po.mtk_src.infrastructure.jpa;

import idv.po.mtk_src.management.domain.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentJpaRepository extends JpaRepository<Department, Integer> {

}
