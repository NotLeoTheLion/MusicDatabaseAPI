package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import lt.viko.eif.groupproject.musicapi.MusicApiApplication;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@CucumberContextConfiguration
@SpringBootTest(classes = MusicApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "spring.config.name=application-test")
@ActiveProfiles("test")
public class AudioManagementSteps {

    @Autowired
    private RestTemplate restTemplate;

    private ResponseEntity<String> response;

    @Given("I send a GET request to {string}")
    public void iSendAGETRequestTo(String url) {
        response = restTemplate.getForEntity(url, String.class);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        Assertions.assertNotNull(response, "Response is null");
        Assertions.assertEquals(statusCode, response.getStatusCodeValue());
    }

    @Then("the response should contain tracks with the keyword {string}")
    public void theResponseShouldContainTracksWithTheKeyword(String keyword) {
        String body = response.getBody();
        Assertions.assertNotNull(body, "Response body is null");
        Assertions.assertTrue(body.contains(keyword), "Response does not contain the keyword '" + keyword + "'");
    }
}
