package pl.dariusz.github.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Data
@Component
public class GithubDetails {

   private GithubOwner githubOwner;

   private List<GithubRepository> repositoryList;

}
