package com.socialmeli.socialmeli.utils;

import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.entities.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserUtils {
    private final User USER_1465 = new User(
            1465,
            "usuario1",
            new ArrayList<>(
                    List.of(
                            new User(
                                    4698,
                                    "usuario2",
                                    null,
                                    null
                            )
                    )
            ),
            new ArrayList<>(
                    List.of(
                            new User(
                                    4698,
                                    "usuario2",
                                    null,
                                    null
                            )
                    )
            )
    );
    @Getter
    private final User USER_1115 = new User(
            1115,
            "usuario3",
            new ArrayList<>(),
            new ArrayList<>(
                    List.of(
                            new User(
                                    4698,
                                    "usuario2",
                                    null,
                                    null
                            )
                    )
            )
    );
    private final User USER_4698 = new User(
            4698,
            "usuario2",
            List.of(new User(1115,
                            "usuario3",null,null),
                    new User(1465,
                            "usuario1",null,null),
                    new User( 123,
                            "usuario5",null,null)),
            List.of(new User( 234,
                            "usuario4", null,null),
                    new User(1465,
                            "usuario1",null,null),
                    new User(123,
                            "usuario5",null,null)));

    private final User NEW_USER = new User(
            1117,
            "usuario1",
            new ArrayList<>(),
            new ArrayList<>()
    );

}
