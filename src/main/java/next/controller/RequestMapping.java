package next.controller;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static Map<String, Controller> controllers = new HashMap<>();
    static {
        controllers.put("", new HomeController());
        controllers.put("/users/login", new LoginController());
        controllers.put("/users/loginForm", new LoginController());
        controllers.put("/users/update", new UpdateUserController());
        controllers.put("/users/updateForm", new UpdateUserController());
        controllers.put("/users", new ListUserController());
        controllers.put("/users/create", new CreateUserController());
        controllers.put("/users/form", new CreateUserController());
        controllers.put("/users/logout", new LogoutController());
        controllers.put("/users/profile", new ProfileController());
    }

    static Controller getController(String url) {
        return controllers.get(url);
    }
}
