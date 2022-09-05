package peaksoft.springbootproject.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springbootproject.dto.CompanyRequest;
import peaksoft.springbootproject.dto.CompanyResponse;
import peaksoft.springbootproject.service.CompanyService;
import peaksoft.springbootproject.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/companies")
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Course Api", description = "User with role admin, update,delete or get all Company")
public class CompanyController {

    private final CompanyService companyService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "create company", description = "we can create Company")
    public ResponseEntity<CompanyResponse> create(@RequestBody CompanyRequest request) {
        try {

            return new ResponseEntity<>(companyService.created(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "update company", description = "we can update company")
    public CompanyResponse update(@PathVariable long id, @RequestBody CompanyRequest request) {
        return companyService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete company", description = "we can delete company")
    public void deleteById(@PathVariable long id) {
        companyService.deleteById(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get company by id", description = "we can get company by id")
    public CompanyResponse getById(@PathVariable long id) {
        return companyService.getById(id);
    }

    @GetMapping
    @Operation(summary = "get all company", description = "we can get all company")
    public List<CompanyResponse> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}/studentSize")
    @Operation(summary = "size student", description = "we can to know number of students in this company")
    public ResponseEntity<Integer> getSizeStudent(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.getSizeStudentInCompany(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
