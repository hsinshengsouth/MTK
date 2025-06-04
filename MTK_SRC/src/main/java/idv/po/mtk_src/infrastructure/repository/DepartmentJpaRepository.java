package idv.po.mtk_src.infrastructure.repository;

import idv.po.mtk_src.management.domain.department.Department;
import idv.po.mtk_src.management.domain.department.DepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface DepartmentJpaRepository extends JpaRepository<Department, Integer> {

}
