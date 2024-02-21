package pl.edu.wszib.GitRepos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Branch {
    String branchName;
    String lastCommitSha;
}
