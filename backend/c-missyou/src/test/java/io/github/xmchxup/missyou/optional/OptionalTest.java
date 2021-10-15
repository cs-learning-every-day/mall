package io.github.xmchxup.missyou.optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class OptionalTest {
	@Test
	public void testOptional() {
		Optional<String> empty = Optional.empty();
		Optional<String> t1 = Optional.of("xxx");
		Optional<String> t2 = Optional.ofNullable(null);
		t1.ifPresent(System.out::println);

		String s = t1.orElse("默认值");
		System.out.println(s);
	}
}
