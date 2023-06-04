package pl.dariusz.github.github_controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/github-details/{username}")
    public List<GithubDetails> getAllRepositories(@RequestHeader("Accept") String header, @PathVariable String username){
        return githubService.getAllGithubData(username, header);
    }

}
