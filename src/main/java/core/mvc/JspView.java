package core.mvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

public class JspView implements View {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private String viewName;

    public JspView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            response.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        setData(model, request);
        RequestDispatcher rd = request.getRequestDispatcher(viewName);
        rd.forward(request, response);
    }

    private void setData(Map<String, Object> model, HttpServletRequest request) {
        Set<String> keySet = model.keySet();
        for (String name : keySet) {
            request.setAttribute(name, model.get(name));
        }
    }
}
