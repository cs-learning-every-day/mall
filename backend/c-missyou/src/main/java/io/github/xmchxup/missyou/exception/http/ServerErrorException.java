package io.github.xmchxup.missyou.exception.http;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class ServerErrorException extends HttpException {
	public ServerErrorException(int code) {
		this.code = code;
		this.httpStatusCode = 500;
	}
}
