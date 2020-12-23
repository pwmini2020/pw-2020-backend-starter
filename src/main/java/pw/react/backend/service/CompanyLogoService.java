package pw.react.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pw.react.backend.appException.InvalidFileException;
import pw.react.backend.appException.ResourceNotFoundException;
import pw.react.backend.dao.CompanyLogoRepository;
import pw.react.backend.model.CompanyLogo;

import java.io.IOException;
import java.util.Objects;

@Service
class CompanyLogoService implements LogoService {

    private final Logger logger = LoggerFactory.getLogger(CompanyLogoService.class);

    private final CompanyLogoRepository repository;

    @Autowired
    public CompanyLogoService(CompanyLogoRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompanyLogo storeLogo(long companyId, MultipartFile file) {
        if (file == null) {
            throw new InvalidFileException("Attachment [MultipartFile] is null.");
        }
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new InvalidFileException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            CompanyLogo newCompanyLogo = new CompanyLogo(fileName, file.getContentType(), companyId, file.getBytes());
            repository.findByCompanyId(companyId).ifPresent(companyLogo -> newCompanyLogo.setId(companyLogo.getId()));
            return repository.save(newCompanyLogo);
        } catch (IOException ex) {
            throw new InvalidFileException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public CompanyLogo getCompanyLogo(long companyId) {
        return repository.findByCompanyId(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with companyId " + companyId));
    }

    @Override
    public void deleteCompanyLogo(long companyId) {
        repository.deleteByCompanyId(companyId);
        logger.info("Logo for the company with id {} deleted.", companyId);
    }
}
