package com.levelup.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.levelup.backend.entity.Comment;
import com.levelup.backend.entity.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);
}
