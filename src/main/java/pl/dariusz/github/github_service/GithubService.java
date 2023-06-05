package pl.dariusz.github.github_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.dariusz.github.exception.UserNotFoundException;
import pl.dariusz.github.github_details.*;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class GithubService {

    private final static String URL = "https://api.github.com/";

    private final RestTemplate restTemplate;

    private GithubOwner githubOwner;
    private final GithubDetails githubDetails;

    @Autowired
    public GithubService(GithubOwner githubOwner,
                         RestTemplate restTemplate,
                         GithubDetails githubDetails) {
        this.githubOwner = githubOwner;
        this.restTemplate = restTemplate;
        this.githubDetails = githubDetails;
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    public ResponseEntity<String> handleHeader() {
        String xmlResponse = "<response><Status>406</Status><Message>Wrong header!</Message></response>";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).headers(headers).body(xmlResponse);
    }

    public List<GithubDetails> getAllGithubData(String username) {

        githubDetails.setGithubOwner(getGithubOwner(username));
        githubDetails.setRepositoryList(getUserRepositories(username));
        return List.of(githubDetails);
    }

    private GithubOwner getGithubOwner(String username) {
        String usernameURL = URL + "users/" + username;
        try {
            ResponseEntity<GithubOwner> githubDataResponse = restTemplate.exchange(usernameURL, HttpMethod.GET, new HttpEntity<>(setHeaders()), GithubOwner.class);
            githubOwner = githubDataResponse.getBody();
            return githubOwner;

        } catch (Exception e) {
            throw new UserNotFoundException("User does not exist!");
        }
    }

    private List<GithubRepository> getUserRepositories(String username) {
        String reposURL = URL + "users/" + username + "/repos";
        ResponseEntity<List<Map<String, String>>> githubRepositories = restTemplate.exchange(reposURL,
                HttpMethod.GET,
                new HttpEntity<>(setHeaders()),
                ParameterizedTypeReference.forType(List.class)
        );

        List<Map<String, String>> repositoryList = githubRepositories.getBody();

        if (repositoryList == null) {
            return List.of();
        }

        return repositoryList.stream()
                .map(repositoryData -> {
                    GithubRepository repository = new GithubRepository();
                    repository.setName(repositoryData.get("name"));
                    List<GithubBranch> branches = getRepositoryBranches(repository.getName(), username);
                    repository.setGithubBranches(branches);
                    return repository;
                })
                .collect(toList());
    }

    private List<GithubBranch> getRepositoryBranches(String repositoryName, String username) {
        String branchesURL = URL + "repos/" + username + "/" + repositoryName + "/branches";

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                branchesURL,
                HttpMethod.GET,
                new HttpEntity<>(setHeaders()),
                ParameterizedTypeReference.forType(List.class)
        );

        List<Map<String, Object>> branchList = response.getBody();

        if (branchList == null) {
            return List.of();
        }

        return branchList.stream()
                .map(branchData -> {
                    Map<String, Object> commitData = (Map<String, Object>) branchData.get("commit");
                    GithubCommit commit = new GithubCommit();
                    commit.setSha((String) commitData.get("sha"));

                    GithubBranch branch = new GithubBranch();
                    branch.setName((String) branchData.get("name"));
                    branch.setCommit(commit);

                    return branch;

                })
                .collect(toList());
    }

}
