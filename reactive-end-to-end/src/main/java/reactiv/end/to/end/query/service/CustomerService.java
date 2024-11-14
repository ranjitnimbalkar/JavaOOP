package reactiv.end.to.end.query.service;

import lombok.RequiredArgsConstructor;
import org.eclipse.collections.impl.factory.Lists;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactiv.end.to.end.query.bean.CustomerBean;
import reactiv.end.to.end.query.data.repository.CustomerRepository;
import reactiv.end.to.end.query.model.CustomerListRequest;
import reactiv.end.to.end.query.model.ListResponse;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class CustomerService extends BaseService {

    private final CustomerRepository customerRepository;

    public Mono<ListResponse<CustomerBean>> customers(CustomerListRequest customerListRequest, Pageable page) {

        return  customerRepository.findAll()
                .map(entity -> copyProperties(entity, CustomerBean::new))
                .collectList()
                .flatMap(l -> Mono.just(l.stream().sorted(Comparator.comparing(CustomerBean::getCustomerNumber)).toList()))
                .map(Lists.immutable::ofAll)
                .transform(toListResponse(page));
    }

}
