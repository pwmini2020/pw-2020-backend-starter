package pw.react.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pw.react.backend.service.HttpService;

@SpringBootTest
@ActiveProfiles(profiles = {"dev"})
class SampleBackendApplicationTests {

	@Autowired
	HttpService httpService;

	@Test
	void contextLoads() {
	}

	@Test
	void name() {
		httpService.consume("");
	}
}
