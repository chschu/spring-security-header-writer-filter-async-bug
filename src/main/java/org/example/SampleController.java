package org.example;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class SampleController {

	@GetMapping("/sync")
	public InputStreamResource getSync() {
		return new InputStreamResource(new ByteArrayInputStream("Hello, World!\n".getBytes(StandardCharsets.UTF_8)));
	}

	@GetMapping("/async")
	public StreamingResponseBody getAsync() {
		return outputStream -> outputStream.write("Hello, World!\n".getBytes(StandardCharsets.UTF_8));
	}

	@GetMapping("/async-workaround")
	public StreamingResponseBody getAsyncWorkaround(HttpServletResponse response) throws IOException {
		response.flushBuffer();
		return outputStream -> outputStream.write("Hello, World!\n".getBytes(StandardCharsets.UTF_8));
	}
}
