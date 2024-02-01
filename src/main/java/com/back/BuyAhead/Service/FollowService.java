package com.back.BuyAhead.Service;

import com.back.BuyAhead.Domain.Follow;
import com.back.BuyAhead.Domain.User;
import com.back.BuyAhead.Dto.Follow.FollowRequestDto;
import com.back.BuyAhead.Repository.FollowRepository;
import com.back.BuyAhead.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserService userService;

    @Autowired
    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    // 팔로우 추가
    public void addFollow(FollowRequestDto followRequestDto) {
        int follower_id = followRequestDto.getFollower_id();
        int following_id = followRequestDto.getFollowing_id();

        if (userService.friend((long) follower_id) && userService.friend((long) following_id)) {
            Follow follow = new Follow();
            follow.setFollower_id(follower_id);
            follow.setFollowing_id(following_id);
            follow.setFriend(followRequestDto.isFriend());
            followRepository.save(follow);
        } else {
            throw new IllegalArgumentException("존재하지 않는 사용입니다.");
        }
    }
}