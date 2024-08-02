package org.example;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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
}
