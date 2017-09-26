package java8playground;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class RepeatableAnnotations {

    @MultiAuthorized({
            @Authorized(permissionsRequired = "root", securedParam = "operatingSystem"),
            @Authorized(permissionsRequired = "owner", securedParam = "homeDirectory")}
    )
    public void securedOperation(String operatingSystem, String homeDirectory) {
    }

    @Target({METHOD, TYPE})
    @Retention(RUNTIME)
    public @interface Authorized {
        String[] permissionsRequired() default {};

        String securedParam() default "";
    }

    @Target({METHOD, TYPE})
    @Retention(RUNTIME)
    public @interface MultiAuthorized {
        Authorized[] value() default {};
    }

}
