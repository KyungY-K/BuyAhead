package com.back.BuyAhead.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "follower_id", nullable = false)
    private int follower_id;

    @Column(name = "following_id", nullable = false)
    private int following_id;

    @Column(name = "friend", nullable = false)
    private boolean friend;

    @Builder
    public Follow(int follower_id, int following_id, boolean friend) {
        this.follower_id = follower_id;
        this.following_id = following_id;
        this.friend = friend;
    }

    public void setFollowerId(Long followerId) {
    }
}
