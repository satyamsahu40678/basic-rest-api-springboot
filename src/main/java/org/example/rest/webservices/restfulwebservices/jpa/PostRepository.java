package org.example.rest.webservices.restfulwebservices.jpa;

import org.example.rest.webservices.restfulwebservices.user.Post;
import org.example.rest.webservices.restfulwebservices.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
