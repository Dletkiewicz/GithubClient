package pl.dariusz.github.entity;

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
