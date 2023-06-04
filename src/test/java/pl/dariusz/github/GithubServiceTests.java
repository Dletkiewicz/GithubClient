package pl.dariusz.github;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import pl.dariusz.github.exception.UserNotFoundException;
import pl.dariusz.github.github_details.GithubDetails;
import pl.dariusz.github.github_details.GithubOwner;
import pl.dariusz.github.github_service.GithubService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;


@SpringBootTest
class GithubServiceTests {

	@Test
	public void should_Pass_WhenUserExists() {

		String username = "dletkiewicz";
		String header = "application/json";
		GithubService githubService = new GithubService(new GithubOwner(), new RestTemplate(), new GithubDetails());
		List<GithubDetails> githubDetailsList = githubService.getAllGithubData(username, header);

		assertNotNull(githubDetailsList);
		assertEquals(1, githubDetailsList.size());
	}

	@Test
	public void should_ThrowException_When_UserDoesNotExist() {

		String username = "nonexistensuser97954674";
		String header = "application/json";
		GithubService githubService = new GithubService(new GithubOwner(), new RestTemplate(), new GithubDetails());
		List<GithubDetails> githubDetailsList = githubService.getAllGithubData(username, header);

		assertFalse(githubDetailsList.isEmpty());
	}

	@Test
	public void shouldPassWhenOwnerLoginMatches() {

		String username = "Dletkiewicz";
		String header = "application/json";
		GithubService githubService = new GithubService(new GithubOwner(), new RestTemplate(), new GithubDetails());

		List<GithubDetails> githubDetailsList = githubService.getAllGithubData(username, header);

		assertNotNull(githubDetailsList);
		assertEquals(1, githubDetailsList.size());

		GithubDetails githubDetails = githubDetailsList.get(0);
		assertEquals("Dletkiewicz", githubDetails.getGithubOwner().getLogin());

	}

	@Test
	public void should_ThrowException_When_APIRequestFails() {
		String username = "nonexistennuserngng";
		String header = "application/json";
		RestTemplate restTemplate = new RestTemplate();
		GithubService githubService = new GithubService(new GithubOwner(), restTemplate, new GithubDetails());

		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		mockServer.expect(requestTo("https://api.github.com/users/" + username))
				.andRespond(withServerError());

		List<GithubDetails> githubDetailsList = githubService.getAllGithubData(username, header);

		assertThrows(UserNotFoundException.class, () -> githubService.getAllGithubData(username, header));
	}

	@Test
	public void should_Return200OK_When_APIRequestIsSuccessful() {
		String username = "existinguser";
		String header = "application/json";
		RestTemplate restTemplate = new RestTemplate();
		GithubService githubService = new GithubService(new GithubOwner(), restTemplate, new GithubDetails());

		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		mockServer.expect(requestTo("https://api.github.com/users/" + username))
				.andExpect(header("Accept", header))
				.andRespond(withStatus(HttpStatus.OK));

		mockServer.expect(requestTo("https://api.github.com/users/" + username + "/repos"))
				.andExpect(header("Accept", header))
				.andRespond(withStatus(HttpStatus.OK));

		List<GithubDetails> githubDetailsList = githubService.getAllGithubData(username, header);

		assertNotNull(githubDetailsList);
		assertEquals(0, githubDetailsList.size());

		mockServer.verify();

}}
