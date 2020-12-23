package pw.react.backend.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.model.Company;
import pw.react.backend.service.HttpClient;

import java.util.List;

@RestController
@RequestMapping(path = "/integration/companies")
public class IntegrationController {

    private final HttpClient httpClient;

    @Autowired
    public IntegrationController(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @GetMapping
    public List<Company> getExternalCompanies() {
        return httpClient.getCompanies();
    }

    @PostMapping
    public String createExternalCompanies(@RequestBody List<Company> companies) {
        return httpClient.createCompanies(companies);
    }
}
