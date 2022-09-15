package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private Map<String, Object> model = new HashMap<>();
    private View view;

    public ModelAndView() {
        view = new JsonView();
    }

    public ModelAndView(String viewName) {
        view = new JspView(viewName);
    }

    public void setAttribute(String name, Object value) {
        model.put(name, value);
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
        view.render(model, request, response);
    }
}
