import java.io.*;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import csu.mathapp.*;

@WebServlet("/v1.0")
public class MathApp extends HttpServlet {

    String webAppPath = "/v1.0";

    final String head = "<head>"
        + "<meta charset=\"UTF-8\">"
        + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
        + "<title>MathApp</title>"
        + "<link rel=\"stylesheet\" href=\"../../assets/style.css\">"
        + "<link rel=\"stylesheet\" href=\"../../assets/bootstrap.css\""
    + "</head>";

    final String body = 
                  "<body class=\"theme\">"
                + "<div class=\"jumbotron text-center\">"
                    + "<span class=\"display-4\">MathApp.xyz!</span>"
                + "</div>"
                + "<div class=\"main overflow-auto\" id=\"mainDiv\">"
                    + "%s"
                    + "<br><hr>"
                    + "<div class=\"d-flex justify-content-center\">"
                        + "<form class=\"form-inline\" action=\"" + webAppPath +"\" method=\"post\">"
                            + "<div class=\"form-group mb-2\">"
                                + "<input class=\"form-control\" type=\"text\" id=\"command\" name=\"command\" </input>"
                            + "</div>"
                            + "<button class=\"btn btn-primary mb-2\" type=\"submit\">Submit</button>"
                        + "</form>" 
                    + "</div>"
                + "</div>"
                + "<script src=\"./assets/js/autoScroll.js\"></script>"
            + "</body>";
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Cookie userCookie;
        if(request.getParameter("JSESSIONID")!=null) {
            userCookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
            response.addCookie(userCookie);
        } else {
            String sessionId = session.getId();
            userCookie = new Cookie("JSESSIONID", sessionId);
            response.addCookie(userCookie);
        }
        response.setContentType("text/html");
        CoreManager cm = CoreManager.getCoreManagerInstance(userCookie.getValue());
        PrintWriter out = response.getWriter();
        out.println(head+String.format(body, cm.render()));     
        out.close();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Cookie userCookie;
        if(request.getParameter("JSESSIONID")!=null) {
            userCookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
            response.addCookie(userCookie);
        } else {
            String sessionId = session.getId();
            userCookie = new Cookie("JSESSIONID", sessionId);
            response.addCookie(userCookie);
        }
        response.setContentType("text/html");
        String sessionId = session.getId();
        CoreManager cm = CoreManager.getCoreManagerInstance(userCookie.getValue());
        String commandString = request.getParameter("command").toLowerCase();
        if(cm.getCurrentMode() == MODE.INTERACTIVE && cm.getExpectedInputs().size() > 0) {
            cm.appendToBody("<img src=\"./assets/img/pencil-square.svg\" alt=\"\" width=\"32\" height=\"32\" title=\"Your Input\">> " + commandString);
            cm.checkStep(commandString);
        } else {
            cm.appendToBody("> " + commandString);
            Command cmd = CommandDirectory.getCommand(commandString, userCookie.getValue());
            if(cmd != null) {   
                    cmd.performAction(commandString, sessionId);           
            }
        }
        PrintWriter out = response.getWriter();
        out.println(head + String.format(body, cm.render()));
        out.close();
    }
}
