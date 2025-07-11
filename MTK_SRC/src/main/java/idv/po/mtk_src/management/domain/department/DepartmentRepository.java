package idv.po.mtk_src.management.domain.department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
  List<Department> findAll();

  Optional<Department> findByDeptId(Integer deptId);

  Department saveDept(Department dept);
}
