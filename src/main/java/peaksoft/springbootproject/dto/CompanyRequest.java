package peaksoft.springbootproject.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequest {
    private Long id;
    private String companyName;
    private String locatedCountry;
}
