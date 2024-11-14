package reactiv.end.to.end.query.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.eclipse.collections.api.list.ImmutableList;
import org.springframework.data.domain.Pageable;

@Getter
@Schema
public class ListResponse<T> {

    @Schema(title = "Content")
    private ImmutableList<T> content;
    @Schema(title = "Info")
    private Info info;

    @Getter
    public static final class Info {
        @Schema(title = "Current page number (0 based index)")
        private int page;
        @Schema(title = "Number of items in page (Minimum: 1)")
        private int size;
        @Schema(title = "Total number of pages")
        private int totalPages;
        @Schema(title = "Total number of items")
        private int totalElements;
    }

    public static <T> ListResponse<T> of(ImmutableList<T> content, Pageable page, int totalElements){
        Info info = new Info();
        int size = page.getPageSize();
        info.size = size;
        info.page = page.getPageNumber();
        info.totalElements = totalElements;
        info.totalPages = (totalElements + size - 1) / size;

        ListResponse<T> listResponse = new ListResponse<>();
        listResponse.content = content;
        listResponse.info = info;
        return  listResponse;
    }

}
