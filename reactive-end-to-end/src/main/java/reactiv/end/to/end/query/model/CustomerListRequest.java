package reactiv.end.to.end.query.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "CustomerRequest")
public class CustomerListRequest {

    @Parameter(name = "customerCountry", required = false, description = "Customer Country", //
               in = ParameterIn.QUERY)
    private String customerCountry;

    @Getter
    @Setter
    @Schema(title = "SingleCustomerRequest")
    public static class SingleCustomerRequest {

        @Parameter(name = "customerNumber", required = true, description = "Customer Number", //
                   in = ParameterIn.PATH)
        private String customerNumber;
    }

}
