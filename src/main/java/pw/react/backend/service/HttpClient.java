package pw.react.backend.service;

import pw.react.backend.model.Company;

import java.util.List;

public interface HttpClient {
    List<Company> getCompanies();
    String createCompanies(List<Company> companies);
}
