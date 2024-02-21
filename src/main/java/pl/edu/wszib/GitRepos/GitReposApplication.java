package pl.edu.wszib.GitRepos;

import org.kohsuke.github.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class GitReposApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitReposApplication.class, args);
	}
}
