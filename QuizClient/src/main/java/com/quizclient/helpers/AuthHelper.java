package com.quizclient.helpers;

import com.quizclient.enums.UserRoleEnum;
import com.quizclient.model.auth.UserData;

public class AuthHelper {

    public static boolean isLogged(UserData user) {
        return user != null;
    }

    public static boolean isAdmin(UserData user) {
        return user != null && UserRoleEnum.ADMIN.equals(user.getRole());
    }

    public static boolean isAdmin(UserRoleEnum userRole) {
        return UserRoleEnum.ADMIN.equals(userRole);
    }

    public static boolean isUser(UserData user) {
        return user != null && UserRoleEnum.USER.equals(user.getRole());
    }

    public static boolean isUser(UserRoleEnum userRole) {
        return UserRoleEnum.USER.equals(userRole);
    }

}
