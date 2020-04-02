package com.routerating.api.common.utils;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

public class Annotations {
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public @interface AuthGetMapping {

		@AliasFor(annotation = RequestMapping.class)
		String name() default "";

		@AliasFor(annotation = RequestMapping.class)
		String[] value() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] path() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] params() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] headers() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] consumes() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] produces() default {};
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("permitAll()")
	public @interface UnauthGetMapping {

		@AliasFor(annotation = RequestMapping.class)
		String name() default "";

		@AliasFor(annotation = RequestMapping.class)
		String[] value() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] path() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] params() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] headers() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] consumes() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] produces() default {};
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public @interface AdminGetMapping {

		@AliasFor(annotation = RequestMapping.class)
		String name() default "";

		@AliasFor(annotation = RequestMapping.class)
		String[] value() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] path() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] params() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] headers() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] consumes() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] produces() default {};
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@RequestMapping(method = RequestMethod.PUT)
	@PreAuthorize("isAuthenticated()")
	public @interface AuthPutMapping {

		@AliasFor(annotation = RequestMapping.class)
		String name() default "";

		@AliasFor(annotation = RequestMapping.class)
		String[] value() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] path() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] params() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] headers() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] consumes() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] produces() default {};
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@RequestMapping(method = RequestMethod.PUT)
	@PreAuthorize("permitAll()")
	public @interface UnauthPutMapping {

		@AliasFor(annotation = RequestMapping.class)
		String name() default "";

		@AliasFor(annotation = RequestMapping.class)
		String[] value() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] path() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] params() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] headers() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] consumes() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] produces() default {};
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@RequestMapping(method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public @interface AdminPutMapping {

		@AliasFor(annotation = RequestMapping.class)
		String name() default "";

		@AliasFor(annotation = RequestMapping.class)
		String[] value() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] path() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] params() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] headers() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] consumes() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] produces() default {};
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public @interface AuthPostMapping {

		@AliasFor(annotation = RequestMapping.class)
		String name() default "";

		@AliasFor(annotation = RequestMapping.class)
		String[] value() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] path() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] params() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] headers() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] consumes() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] produces() default {};
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("permitAll()")
	public @interface UnuthPostMapping {

		@AliasFor(annotation = RequestMapping.class)
		String name() default "";

		@AliasFor(annotation = RequestMapping.class)
		String[] value() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] path() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] params() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] headers() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] consumes() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] produces() default {};
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN')")
	public @interface AdminPostMapping {

		@AliasFor(annotation = RequestMapping.class)
		String name() default "";

		@AliasFor(annotation = RequestMapping.class)
		String[] value() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] path() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] params() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] headers() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] consumes() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] produces() default {};
	}


	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@RestController("")
	@RequestMapping
	@PreAuthorize("isAuthenticated()")
	public @interface Controller {
		@AliasFor(annotation = RequestMapping.class)
		String name() default "";

		@AliasFor(annotation = RequestMapping.class)
		String[] value() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] path() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] params() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] headers() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] consumes() default {};

		@AliasFor(annotation = RequestMapping.class)
		String[] produces() default {};
	}
}
