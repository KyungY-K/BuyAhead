package com.back.BuyAhead.Dto.Follow;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowRequestDto {
    private int follower_id;
    private int following_id;
    private boolean friend = true;
}
