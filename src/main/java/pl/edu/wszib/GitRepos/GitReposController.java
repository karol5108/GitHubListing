package pl.edu.wszib.GitRepos;

import org.kohsuke.github.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GitReposController {

    @GetMapping("/{username}")
    List<GitReposModel> getUserRepositories(@PathVariable String username) throws IOException {
        // create github builder with accesss token
        GitHub github = new GitHubBuilder().withOAuthToken("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").build();

        GHUser user = github.getUser(username);

        List<GitReposModel> repos = new ArrayList<>();

        try {
            List<GHRepository> repositories = user.listRepositories().toList();

            for (GHRepository repository : repositories) {
                // skip forks
                if (repository.isFork()) {
                    continue;
                }

                // add repository details
                GitReposModel repo = new GitReposModel();
                repo.setRepositoryName(repository.getName());
                repo.setOwnerName(repository.getOwnerName());

                // add branches and last commit sha
                List<GHRef> refs = repository.listRefs().toList();
                List<Branch> branches = new ArrayList<>();
                for (GHRef ref : refs) {
                    // filter only branches
                    if (ref.getRef().startsWith("refs/heads/")) {
                        // add branch details
                        Branch branch = new Branch();
                        branch.setBranchName(ref.getRef().substring("refs/heads/".length()));

                        // get the last commit in the branch
                        GHCommit lastCommit = repository.getCommit(ref.getObject().getSha());
                        branch.setLastCommitSha(lastCommit.getSHA1());

                        branches.add(branch);
                    }
                }
                repo.setBranches(branches);
                repos.add(repo);
            }
        } catch (GHFileNotFoundException e) {
            System.err.println("User not found: " + e.getMessage());
        }

        return repos;
    }

    @ExceptionHandler(GHFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, String> handleNotFoundException(GHFileNotFoundException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", e.getMessage());
        errorResponse.put("status", "404");
        return errorResponse;
    }
}