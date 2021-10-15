package io.github.xmchxup.missyou.exception.http;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class ParameterException extends HttpException {
	public ParameterException(int code) {
		this.code = code;
		this.httpStatusCode = 400;
	}
}
