package com.back.BuyAhead.Dto.Update;

import com.back.BuyAhead.Domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestDto {
    private String name;
    private String profile_image;
    private String greeting;
    private String password;
}
