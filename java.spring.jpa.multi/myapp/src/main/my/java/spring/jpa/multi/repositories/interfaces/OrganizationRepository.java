package my.java.spring.jpa.multi.repositories.interfaces;

import my.java.spring.jpa.multi.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
}
