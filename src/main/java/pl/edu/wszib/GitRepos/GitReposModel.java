package pl.edu.wszib.GitRepos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GitReposModel {
    private String ownerName;
    private String repositoryName;
    List<Branch> branches;
}
