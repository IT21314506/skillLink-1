package com.levelup.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levelup.backend.entity.Comment;
import com.levelup.backend.entity.Post;
import com.levelup.backend.entity.User;
import com.levelup.backend.repository.CommentRepository;
import com.levelup.backend.repository.PostRepository;
import com.levelup.backend.repository.UserRepository;

@Service
public class CommentService {

    //Autowired comment repositories
    @Autowired
    private CommentRepository commentRepository;

    //Autowired services
    @Autowired
    private NotificationService notificationService;

    //Autowired post repositories
    @Autowired
    private PostRepository postRepository;

    //Autowired user repositories
    @Autowired
    private UserRepository userRepository;

    public Comment save(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        Comment saved = commentRepository.save(comment);

        Post post = saved.getPost();
        User commenter = saved.getUser(); // commenter must be set from controller
        User postOwner = post.getUser();

        if (!postOwner.getEmail().equals(commenter.getEmail())) {
            String msg = commenter.getUsername() + " commented on your post: " + post.getTitle();
            notificationService.createNotification(postOwner, msg);
        }

        return saved;
    }

    // Method to get all comments by post

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    // Method to get all comments by user
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    // Method to get all comments by user

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
