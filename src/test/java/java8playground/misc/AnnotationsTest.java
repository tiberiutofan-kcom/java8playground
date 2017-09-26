package java8playground.misc;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class AnnotationsTest {

    @Test
    public void notNull() {
        @NotEmpty String s = "";
        //this could result in a compilation error
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    public @interface NotEmpty {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    public @interface NotNull {
    }

    @Target({PARAMETER})
    @Retention(RUNTIME)
    public @interface ParamName {
        String value();
    }

    public static class ProgramByAnnotations<@NotEmpty T> extends @NotEmpty Object {
        public @NotNull List<@NotEmpty String> method() throws @NotEmpty Exception {
            @NotNull List<@NotEmpty String> words = new @NotNull ArrayList<>();
            words.add(""); //this could be a compilation error
            return words;
        }
    }


}
