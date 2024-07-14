package org.example.rest.webservices.restfulwebservices.user;

import jakarta.validation.Valid;
import org.example.rest.webservices.restfulwebservices.jpa.PostRepository;
import org.example.rest.webservices.restfulwebservices.jpa.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {


    private UserRepository repository;
    private PostRepository postRepository;

    public UserJpaResource(UserRepository repository, PostRepository postRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
    }

    // GET /users
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){

        return repository.findAll();
    }

    //EntityModel
    //WebMvcLinkBuilder

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        Optional<User> user = repository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException("id: " + id);
        }

        EntityModel<User> entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        repository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id) {
        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id);
        }

        return user.get().getPosts();
    }


    //POST /users
    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = repository.save(user);
        // /users/4 => /users/{id},  user.getID ||-> here ServletUri will give the current request url "/users" and add id to it then and get id then adn make it a url
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build(); //send created response which is 201
    }


    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostsForUser(@PathVariable int id, @Valid @RequestBody Post post) {
        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id);
        }

        post.setUser(user.get());

        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
