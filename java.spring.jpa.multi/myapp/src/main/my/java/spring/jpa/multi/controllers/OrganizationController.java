package my.java.spring.jpa.multi.controllers;

import my.java.spring.jpa.multi.models.Organization;
import my.java.spring.jpa.multi.repositories.interfaces.OrganizationRepository;
import my.java.spring.jpa.multi.repositories.config.MySqlSource1Config;
import my.java.spring.jpa.multi.repositories.config.MySqlSource2Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/organization")
public class OrganizationController {

    private final OrganizationRepository organizationRepositoryFromSource1;
    private final OrganizationRepository organizationRepositoryFromSource2;

    @Autowired
    public OrganizationController(
            @Qualifier(MySqlSource1Config.RepositoryBeanName) OrganizationRepository organizationRepositoryFromSource1,
            @Qualifier(MySqlSource2Config.RepositoryBeanName) OrganizationRepository organizationRepositoryFromSource2) {

        this.organizationRepositoryFromSource1 = organizationRepositoryFromSource1;
        this.organizationRepositoryFromSource2 = organizationRepositoryFromSource2;
    }

    @GetMapping("src1")
    public Iterable<Organization> getFromSource1() {
        List<Organization> organizations = this.organizationRepositoryFromSource1.findAll();
        return organizations;
    }

    @PostMapping("src1")
    public Organization postToSource1(@RequestBody() Organization org) {
        return this.organizationRepositoryFromSource1.save(org);
    }

    @GetMapping("src2")
    public Iterable<Organization> getFromSource2() {
        List<Organization> organizations = this.organizationRepositoryFromSource2.findAll();
        return organizations;
    }

    @PostMapping("src2")
    public Organization postToSource2(@RequestBody() Organization org) {
        return this.organizationRepositoryFromSource2.save(org);
    }
}