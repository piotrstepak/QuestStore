package handler.student;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.elements.Artifact;
import model.elements.Quest;
import model.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import service.ArtifactService;
import service.QuestService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class StudentPagesHandler implements HttpHandler {
    User user = null;
    //CookieHandler cookieHandler = new CookieHandler();

    String modelName;

    public StudentPagesHandler(String modelName){
        this.modelName = modelName;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
//        user = cookieHandler.cookieChecker(httpExchange);
//        if(user == null || user.getUserType() != 1){
//            httpExchange.getResponseHeaders().set("Location", "/login");
//            httpExchange.sendResponseHeaders(303, 0);
//        }

//        String method = httpExchange.getRequestMethod();
//        StudentDAO studentDAO = new StudentDAO();, "store"
//        int coins = studentDAO.getStudentCoins(user.getId());
        String method = httpExchange.getRequestMethod();
        String response = "";

        if (method.equals("GET")){
            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/" + modelName + "page.twig");
            JtwigModel model = JtwigModel.newModel();
            switch (modelName) {
                case "quest":
                    List<Quest> questList = QuestService.getInstance().getAllQuest();
                    model.with("entryList", questList);
                    break;
                case "artifact":
                    List<Artifact> artifactList = ArtifactService.getInstance().getAllArtifact();
                    model.with("entryList", artifactList);
                    break;
            }

            response = template.render(model);

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

