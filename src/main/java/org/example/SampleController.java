package org.example;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class SampleController {

	private static final byte[] BYTES = "Hello, World!\n".getBytes(StandardCharsets.UTF_8);

	@GetMapping(value = "/sync", produces = MediaType.TEXT_PLAIN_VALUE)
	public InputStreamResource getSync() {
		return new InputStreamResource(new ByteArrayInputStream(BYTES));
	}

	@GetMapping(value = "/async", produces = MediaType.TEXT_PLAIN_VALUE)
	public StreamingResponseBody getAsync() {
		return outputStream -> outputStream.write(BYTES);
	}

	@GetMapping(value = "/async-workaround", produces = MediaType.TEXT_PLAIN_VALUE)
	public StreamingResponseBody getAsyncWorkaround(HttpServletResponse response) throws IOException {
		response.setContentType(MediaType.TEXT_PLAIN_VALUE);
		response.flushBuffer();
		return outputStream -> outputStream.write(BYTES);
	}
}
