package my.java.spring.jpa.multi.repositories.source1;

import my.java.spring.jpa.multi.repositories.config.MySqlSource1Config;
import my.java.spring.jpa.multi.repositories.interfaces.OrganizationRepository;
import org.springframework.stereotype.Repository;

@Repository(MySqlSource1Config.RepositoryBeanName)
public interface Source1OrganizationRepository extends OrganizationRepository {
}
