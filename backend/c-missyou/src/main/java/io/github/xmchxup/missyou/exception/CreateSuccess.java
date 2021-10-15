package io.github.xmchxup.missyou.exception;

import io.github.xmchxup.missyou.exception.http.HttpException;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class CreateSuccess extends HttpException {
	public CreateSuccess(int code) {
		this.httpStatusCode = 201;
		this.code = code;
	}
}
