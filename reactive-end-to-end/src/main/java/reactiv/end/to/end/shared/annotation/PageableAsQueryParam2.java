package reactiv.end.to.end.shared.annotation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Parameter(in = ParameterIn.QUERY
        , description = "Zero-based page index (0..N)"
        , name = "page"
        , schema = @Schema(type = "integer", defaultValue = "0"))
@Parameter(in = ParameterIn.QUERY
        , description = "The size of the page to be returned"
        , name = "size"
        , schema = @Schema(type = "integer", defaultValue = "25"))
public @interface PageableAsQueryParam2 {
}
