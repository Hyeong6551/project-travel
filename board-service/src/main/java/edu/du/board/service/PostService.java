package edu.du.board.service;

import edu.du.board.entity.Post;
import edu.du.board.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Optional<Post> updatePost(Long id, Post postData) {
        return postRepository.findById(id).map(post -> {
            post.update(postData.getTitle(), postData.getContent());
            return postRepository.save(post);
        });
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}

