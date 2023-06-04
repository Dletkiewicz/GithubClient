package pl.dariusz.github.github_details;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Data
@Component
@JsonPropertyOrder({
        "name",
        "commit"
})
public class GithubBranch {

    @JsonProperty("name")
    private String name;

    @JsonProperty("commit")
    private GithubCommit commit;

}
