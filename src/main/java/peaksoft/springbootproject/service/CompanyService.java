package peaksoft.springbootproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.springbootproject.dto.CompanyRequest;
import peaksoft.springbootproject.dto.CompanyResponse;
import peaksoft.springbootproject.entity.Company;
import peaksoft.springbootproject.repository.CompanyRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;


    public CompanyResponse created(CompanyRequest request) {
        Company company = mapToEntity(request);
        companyRepository.save(company);
        return companyResponse(company);
    }

    public CompanyResponse update(long id, CompanyRequest request) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isEmpty()) {
            System.out.println("Company with id not found");
        }
        mapToUpdate(company.get(), request);
        return companyResponse(companyRepository.save(company.get()));
    }

    public CompanyResponse getById(long id) {
        Company company = companyRepository.findById(id).get();
        return companyResponse(company);
    }

    public void deleteById(long id) {
        Company company = companyRepository.findById(id).get();
        companyRepository.delete(company);
    }

    public Company mapToEntity(CompanyRequest request) {
        Company company = new Company();
        company.setCompanyName(request.getCompanyName());
        company.setLocatedCountry(request.getLocatedCountry());
        company.setCreated(LocalDate.now());
        company.setDelete(company.getDelete());
        company.setIsActive(company.getIsActive());
        return company;
    }

    public void mapToUpdate(Company company, CompanyRequest request) {
        company.setCompanyName(request.getCompanyName());
        company.setLocatedCountry(request.getLocatedCountry());

    }

    public CompanyResponse companyResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .locatedCountry(company.getLocatedCountry())
                .created(company.getCreated())
                .isActive(company.getIsActive()).build();

    }


    public List<CompanyResponse> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyResponse> responses = new ArrayList<>();
        for (Company com : companies) {
            responses.add(companyResponse(com));

        }
        return responses;
    }

}
