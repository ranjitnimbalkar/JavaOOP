package reactiv.end.to.end.query.service;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactiv.end.to.end.query.bean.BaseServiceBean;
import reactiv.end.to.end.query.model.ListResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.function.Function;

public abstract class BaseService extends BaseServiceBean {

    protected static final int DEFAULT_PAGE_NUM = 0;
    protected static final int DEFAULT_PAGE_SIZE = 25;
    protected static final int MAX_PAGE_NUM = 1_00_000;
    protected static final int MAX_PAGE_SIZE = 100;

    protected <T> Function<Mono<ImmutableList<T>>, Mono<ListResponse<T>>> toListResponse(Pageable page) {
        return items -> items
                         .zipWith(defaultPageableMono(page))
                         .map(toListResponse());
    }

    @NotNull
    protected <T> Function<Tuple2<ImmutableList<T>, Pageable>, ListResponse<T>> toListResponse() {
        return tuple2 -> {
            ImmutableList<T> items = tuple2.getT1();
            Pageable page = tuple2.getT2();

            int pageNumber = page.getPageNumber();
            int pageSize = page.getPageSize();
            int totalElements = items.size();

            int fromIndex = pageNumber * pageSize;
            int toIndex = Math.min(totalElements, (pageNumber + 1) * pageSize);

            ImmutableList<T> content = fromIndex >= toIndex ? Lists.immutable.<T>empty() : items.subList(fromIndex, toIndex);

            return ListResponse.of(content, page, totalElements);
        };
    }

    private Mono<Pageable> defaultPageableMono(Pageable page) {
        return Mono.just(defaultPageable(page));
    }

    private Pageable defaultPageable(Pageable page) {
        if(page == null) {
            return PageRequest.of(DEFAULT_PAGE_NUM, DEFAULT_PAGE_SIZE);
        }
        int pageNumber = Math.min(Math.max(page.getPageNumber(), DEFAULT_PAGE_NUM), MAX_PAGE_NUM);
        int pageSize = Math.min(Math.max(page.getPageSize(), DEFAULT_PAGE_SIZE), MAX_PAGE_SIZE);
        return PageRequest.of(pageNumber, pageSize);
    }

}
