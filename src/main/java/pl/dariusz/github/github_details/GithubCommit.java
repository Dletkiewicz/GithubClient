package pl.dariusz.github.github_details;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Data
@Component
public class GithubCommit {

    @JsonProperty("sha")
    private String sha;

}