package com.automazing.constants;

public enum HttpStatus {
	OK_200(200, "OK"),
	CREATED_201(201, "HTTP/1.1 201 Created"),
	NO_CONTENT_204(204, "HTTP/1.1 204 No Content"),
	BAD_REQUEST_400(400, "HTTP/1.1 404 Bad Request"),
	UNAUTHORIZED_401(401, "HTTP/1.1 401 Unauthorized"),
	FORBIDDEN_403(403, "HTTP/1.1 403 Forbidden"),
	NOT_FOUND_404(404, "HTTP/1.1 404 Not Found"),
	INTERNAL_SERVER_ERROR_500(500, "HTTP/1.1 500 Internal Server Error");
	
	private final int code;
	private final String message;
	

	HttpStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	public String getmessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return code + " " + message;
	}

}
