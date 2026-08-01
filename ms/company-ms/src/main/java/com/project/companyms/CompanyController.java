package com.project.companyms;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;


    @GetMapping()
    public ResponseEntity<List<Company>> findAll() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @PreAuthorize("hasAuthority('ADMIN' or hasAuthority('EMPLOYER'))")
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Company company) {
        companyService.create(company);
        return ResponseEntity.ok("Company added successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getById(id);
        if(company != null) {
            return ResponseEntity.ok(company);
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        boolean deleteSuccessful = companyService.deleteById(id);
        if(deleteSuccessful) {
            return ResponseEntity.ok("Company successfully deleted");
        }
        return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateById(@PathVariable Long id, @RequestBody Company company) {
        boolean companyUpdated = companyService.updateById(id, company);
        if(companyUpdated) {
            return ResponseEntity.ok("Company updated successfully");
        }
        return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
    }

}
