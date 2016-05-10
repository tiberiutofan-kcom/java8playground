package java8playground;

import org.junit.Test;

import java.lang.annotation.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class AnnotationsTest {

	@Test
	public void notNull() {
		@NotEmpty String s = "";
		//this could result in a compilation error
	}

	@Authorized(permissionsRequired = "root", securedParamName = "operatingSystem")
	@Authorized(permissionsRequired = "owner", securedParamName = "homeDirectory")
	public void securedOperation(String operatingSystem, String homeDirectory) {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
	public @interface NotEmpty {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
	public @interface NotNull {
	}

	@Target({METHOD, TYPE})
	@Retention(RUNTIME)
	@Repeatable(MultiAuthorized.class)
	public @interface Authorized {
		String[] permissionsRequired() default {};

		String securedParamName() default "";
	}

	@Target({METHOD, TYPE})
	@Retention(RUNTIME)
	public @interface MultiAuthorized {
		Authorized[] value() default {};
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
