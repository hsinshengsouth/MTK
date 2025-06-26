package idv.po.mtk_src.management.domain.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

  private final DepartmentRepository deptRepository;

  @Autowired
  public DepartmentService(
      @Qualifier("departmentRepositoryImpl") DepartmentRepository deptRepository) {
    this.deptRepository = deptRepository;
  }

  public List<Department> getAllDept() {
    return deptRepository.findAll();
  }

  public Optional<Department> getByDeptId(Integer deptId) {
    return deptRepository.findByDeptId(deptId);
  }

  public void saveDept(Department dept) {
    deptRepository.saveDept(dept);
  }
}
