package core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class JsonView implements View {

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = createModel(request);
        convertJson(response, model);
    }

    private static void convertJson(HttpServletResponse response, Map<String, Object> model) throws IOException {
        Collection<Object> values = model.values();
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        for (Object value : values) {
            out.print(mapper.writeValueAsString(value));
        }
    }

    private static Map<String, Object> createModel(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String key = attributeNames.nextElement();
            Object value = request.getAttribute(key);
            model.put(key, value);
        }
        return model;
    }
}
