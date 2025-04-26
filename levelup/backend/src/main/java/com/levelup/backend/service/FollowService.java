package com.levelup.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levelup.backend.entity.Follow;
import com.levelup.backend.entity.FollowStatus;
import com.levelup.backend.entity.User;
import com.levelup.backend.repository.FollowRepository;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    // Method to follow a user
    // If the user is already following, return null

    public Follow follow(User follower, User following) {
        if (!followRepository.existsByFollowerAndFollowing(follower, following)) {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowing(following);
            return followRepository.save(follow);
        }
        return null; // already following
    }

    public List<Follow> getFollowing(User follower) {
        return followRepository.findByFollower(follower);
    }

    // Method to request follow a user
    // If the user is already following, return null

    public Follow requestFollow(User follower, User following) {
        if (!followRepository.existsByFollowerAndFollowing(follower, following)) {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowing(following);
            follow.setStatus(FollowStatus.PENDING);
            return followRepository.save(follow);
        }
        return null;
}
    // Method to accept follow request
    // If the user is already following, return null

    public Follow acceptFollow(Long followId) {
        Follow follow = followRepository.findById(followId).orElseThrow();
        follow.setStatus(FollowStatus.ACCEPTED);
        return followRepository.save(follow);
    }

    // Method to reject follow request
    // If the user is already following, return null

    public List<Follow> getAcceptedFollowing(User user) {
        return followRepository.findByFollowerAndStatus(user, FollowStatus.ACCEPTED);
    }

    // Method to get all followers of a user
    public List<Follow> getPendingRequests(User user) {
        return followRepository.findByFollowingAndStatus(user, FollowStatus.PENDING);
    }
    // Method to get all followers of a user
    public FollowStatus getStatus(User follower, User following) {
        return followRepository.findByFollowerAndFollowing(follower, following)
                .map(Follow::getStatus).orElse(null);
    }
    // Method to get all followers of a user
    public void unfollow(User follower, User following) {
        followRepository.findByFollowerAndFollowing(follower, following)
                .ifPresent(followRepository::delete);
    }
    // Method to get all followers of a user
    public int getFollowerCount(User user) {
        return (int) followRepository.findAll().stream()
                .filter(f -> f.getFollowing().equals(user) && f.getStatus() == FollowStatus.ACCEPTED)
                .count();
    }

    public int getFollowingCount(User user) {
        return (int) followRepository.findAll().stream()
                .filter(f -> f.getFollower().equals(user) && f.getStatus() == FollowStatus.ACCEPTED)
                .count();
    }

}
