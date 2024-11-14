package reactiv.end.to.end.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactiv.end.to.end.query.bean.CustomerBean;
import reactiv.end.to.end.query.model.CustomerListRequest;
import reactiv.end.to.end.query.model.ListResponse;
import reactiv.end.to.end.query.model.response.ListResponseCustomer;
import reactiv.end.to.end.query.service.CustomerService;
import reactiv.end.to.end.shared.annotation.PageableAsQueryParam2;
import reactor.core.publisher.Mono;

import static reactiv.end.to.end.shared.util.SpringDocUtil.*;

@RestController
@RequestMapping(value = "/customer", //
            consumes = MediaType.ALL_VALUE, //
            produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "List", description = "List all the customers.", tags = CUSTOMER, operationId = "listCustomers",
            extensions = @Extension(name = X_ORDER, properties = {@ExtensionProperty(name = ORDER, value = "1")}))
    @ApiResponse(responseCode = "200", content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ListResponseCustomer.class)) })
    @SecurityRequirements({@SecurityRequirement(name = AUTH_BEARER_TOKEN), @SecurityRequirement(name = LOGIN_ID)})
    @GetMapping("/list")
    @PageableAsQueryParam2
    public Mono<ListResponse<CustomerBean>> list(@ParameterObject CustomerListRequest customerListRequest, //
                                                 Pageable page //
    ) {
        return customerService.customers(customerListRequest, PageRequest.of(page.getPage(), page.getSize()));
    }

}
