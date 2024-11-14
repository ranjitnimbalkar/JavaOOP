package reactiv.end.to.end.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.jose4j.lang.JoseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactiv.end.to.end.query.service.JwtService;
import reactiv.end.to.end.shared.util.SpringDocUtil;
import reactor.core.publisher.Mono;

import static reactiv.end.to.end.shared.util.SpringDocUtil.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JwtCreateController {

    private final JwtService jwtService;

    @Operation(summary = "JWT Token", description = "JWT token for authentication and authorization",
               tags = SECURITY, operationId = "generateJwtToken", extensions = {
               @Extension(name = X_ORDER, properties = @ExtensionProperty(name = ORDER, value = "1"))
    })
    @ApiResponse(content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(implementation = String.class)))
    @GetMapping(value = "/token")
    public Mono<String> jwtToken() throws JoseException {
        return  jwtService.generateJwt();
    }

}
