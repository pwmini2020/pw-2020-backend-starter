package pw.react.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pw.react.backend.model.CompanyLogo;

import java.util.Optional;

@Repository
@Transactional
public interface CompanyLogoRepository extends JpaRepository<CompanyLogo, String> {
    Optional<CompanyLogo> findByCompanyId(long companyId);
    void deleteByCompanyId(long companyId);
}
