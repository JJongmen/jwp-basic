package core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

public class JsonView implements View {

    @Override
    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
}
