package io.github.xmchxup.missyou.exception.http;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class UnAuthenticatedException extends HttpException {
	public UnAuthenticatedException(int code) {
		this.code = code;
		this.httpStatusCode = 401;
	}
}
