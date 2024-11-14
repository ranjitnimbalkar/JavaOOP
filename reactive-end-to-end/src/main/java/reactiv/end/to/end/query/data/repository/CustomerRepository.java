package reactiv.end.to.end.query.data.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactiv.end.to.end.query.data.entity.CustomerEntity;

public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, Integer> {
}
