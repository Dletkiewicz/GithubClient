
package pl.dariusz.github.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Data
@Component
public class GithubRepository {

    @JsonProperty("name")
    private String name;

    private List<GithubBranch> githubBranches;

}
