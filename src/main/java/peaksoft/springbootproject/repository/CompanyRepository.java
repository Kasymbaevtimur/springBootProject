package peaksoft.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.springbootproject.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {


}
