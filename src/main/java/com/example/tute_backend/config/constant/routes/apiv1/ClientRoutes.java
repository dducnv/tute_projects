package com.example.tute_backend.config.constant.routes.apiv1;

import static com.example.tute_backend.config.constant.routes.apiv1.MainRoutes.PREFIX_API_V1;

public class ClientRoutes {
    public static final String PREFIX_USER_DETAILS = "/user/{username}";
    public static final String USER_DETAILS_PAM = PREFIX_API_V1.concat("/user/**");
}
