package io.github.xmchxup.missyou.exception.http;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class NotFoundException extends HttpException {
	public NotFoundException(int code) {
		this.httpStatusCode = 404;
		this.code = code;
	}
}
