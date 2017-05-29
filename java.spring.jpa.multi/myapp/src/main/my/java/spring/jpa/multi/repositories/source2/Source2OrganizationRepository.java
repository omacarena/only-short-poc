package my.java.spring.jpa.multi.repositories.source2;

import my.java.spring.jpa.multi.repositories.config.MySqlSource2Config;
import my.java.spring.jpa.multi.repositories.interfaces.OrganizationRepository;
import org.springframework.stereotype.Repository;

@Repository(MySqlSource2Config.RepositoryBeanName)
public interface Source2OrganizationRepository extends OrganizationRepository {
}
