package com.levelup.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.levelup.backend.entity.Follow;
import com.levelup.backend.entity.FollowStatus;
import com.levelup.backend.entity.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findByFollower(User user);

    boolean existsByFollowerAndFollowing(User follower, User following);

    List<Follow> findByFollowingAndStatus(User user, FollowStatus status);

    List<Follow> findByFollowerAndStatus(User user, FollowStatus status);

    Optional<Follow> findByFollowerAndFollowing(User follower, User following);

    // ✅ Add these for safe user deletion
    void deleteByFollower(User user);

    void deleteByFollowing(User user);
}
