package io.github.xmchxup.missyou.exception.http;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class ForbiddenException extends HttpException {
	public ForbiddenException(int code) {
		this.code = code;
		this.httpStatusCode = 403;
	}
}
