package reactiv.end.to.end.shared.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import reactiv.end.to.end.shared.util.SpringDocUtil;

@OpenAPIDefinition(info = @Info(title = "Spring Boot Reactive Stack", description = "Microservice to get customer details, orders and payments."
                          ,version = "v0.1", license = @License(name = "Copyright © 2013 XYZ, Inc.", url = "https://google.com"))
                   )
@SecuritySchemes(value = {@SecurityScheme(name = SpringDocUtil.AUTH_BEARER_TOKEN, type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", in = SecuritySchemeIn.HEADER),
                          @SecurityScheme(name = SpringDocUtil.LOGIN_ID, type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, paramName = "login-id")})
public class SpringDocConfig {

}
