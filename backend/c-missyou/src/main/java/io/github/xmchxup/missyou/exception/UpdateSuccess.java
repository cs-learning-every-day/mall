package io.github.xmchxup.missyou.exception;

import io.github.xmchxup.missyou.exception.http.HttpException;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class UpdateSuccess extends HttpException {
	public UpdateSuccess(int code) {
		this.httpStatusCode = 200;
		this.code = code;
	}
}
