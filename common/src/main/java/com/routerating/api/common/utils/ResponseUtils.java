package com.routerating.api.common.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

	private static Gson gson = new GsonBuilder()
			.disableHtmlEscaping()
			.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
			.serializeNulls()
			.setLenient()
			.enableComplexMapKeySerialization()
			.setPrettyPrinting()
			.create();

	public static <T> ResponseEntity<?> ok(T body) {
		return httpJsonResponse(HttpStatus.OK, body);
	}

	public static <T> ResponseEntity<?> notFound(T body) {
		return httpJsonResponse(HttpStatus.NOT_FOUND, body);
	}

	public static <T> ResponseEntity<?> badRequest(T body) {
		return httpJsonResponse(HttpStatus.BAD_REQUEST, body);
	}

	public static <T> ResponseEntity<?> unauthorized(T body) {
		return httpJsonResponse(HttpStatus.UNAUTHORIZED, body);
	}

	public static ResponseEntity<?> unauthorized() {
		return httpJsonResponse(HttpStatus.UNAUTHORIZED, null);
	}

	public static <T> ResponseEntity<?> internalServerError(T body) {
		return httpJsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, body);
	}

	private static <T> ResponseEntity<?> httpJsonResponse(HttpStatus status, T body) {
		return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(gson.toJson(body));
	}

	private static <T> ResponseEntity<?> httpJsonResponseOfType(HttpStatus status, T body) {
		return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(gson.toJson(body));
	}
}
