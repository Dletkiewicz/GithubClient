package pl.dariusz.github.github_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dariusz.github.github_details.GithubDetails;
import pl.dariusz.github.github_service.GithubService;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class GithubController {

    GithubService githubService;

    @Autowired
    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping(value = "/github-details/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GithubDetails> getAllRepositories(@PathVariable String username){
        return githubService.getAllGithubData(username);
    }

    @GetMapping(value = "/github-details/{username}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<String> handleHeader() {
            return githubService.handleHeader();
    }
}
