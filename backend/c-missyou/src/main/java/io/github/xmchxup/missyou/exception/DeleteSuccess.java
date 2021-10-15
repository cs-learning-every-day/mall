package io.github.xmchxup.missyou.exception;

import io.github.xmchxup.missyou.exception.http.HttpException;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class DeleteSuccess extends HttpException {
	public DeleteSuccess(int code) {
		this.httpStatusCode = 200;
		this.code = code;
	}
//	Create: 201 资源本身
//	Get: 200
//	Put: 200
//	Delete: 200 (204 Not Content)
}
