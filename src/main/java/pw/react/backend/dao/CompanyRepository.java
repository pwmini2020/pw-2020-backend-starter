package pw.react.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pw.react.backend.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
