package reactiv.end.to.end.query.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
//@Accessors(fluent = true)
@Schema(title = "Customer")
public class CustomerBean {
    @Schema(title = "Unique customer identifier.")
    Integer customerNumber;
    @Schema(title = "Customer name.")
    String customerName;

    @Override
    public String toString() {
        return "CustomerBean{" +
                "customerNumber=" + customerNumber +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
