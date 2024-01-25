package com.socialmeli.socialmeli.utils;

import com.socialmeli.socialmeli.entities.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Utils {
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
            new ArrayList<>(),
            new ArrayList<>()
    );

}
