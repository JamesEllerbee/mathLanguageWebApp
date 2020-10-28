import java.io.*;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import csu.mathapp.*;
import java.io.File;

@WebServlet("/application") //configure the request url for this servlet 
public class MathApp extends HttpServlet {

    final String head = "<head>"
        + "<title>Math Tutoring System</title>"
        + "<link rel=\"stylesheet\" href=\"../../assets/style.css\">"
        + "<link rel=\"stylesheet\" href=\"../../assets/bootstrap.css\""
    + "</head>";

    final String body = "<body class=\"theme\">"
                + "<div class=\"jumbotron text-center\">"
                    + "<span class=\"display-4\">MathApp.xyz!</span>"
                + "</div>"
                + "<div class=\"main\">"
                    + "%s"
                    + "<br><hr>"
                    + "<div class=\"d-flex justify-content-center\">"
                        + "<form class=\"form-inline\" action=\"application\" method=\"post\">"
                        + "<div class=\"form-group mb-2\">"
                        + "<input class=\"form-control\" type=\"text\" id=\"command\" name=\"command\" </input>"
                    + "</div>"
                        +    "<button class=\"btn btn-primary mb-2\" type=\"submit\">Submit</button>"
                        + "</form>" 
                + "</div>"
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
        cm.appendToBody(ALERT_TYPE.INFORMATION, "The root is " + cm.getRoot());
        cm.appendToBody(ALERT_TYPE.INFORMATION, "The current working directory is " + System.getProperty("user.dir"));
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
        cm.appendToBody("> " + commandString);
        Command cmd = CommandDirectory.getCommand(commandString, userCookie.getValue());
        if(cmd != null) {   
            if(cm.getCurrentMode() == MODE.INTERACTIVE) {
                cm.appendToBody("<img src=\"./assets/img/pencil-square.svg\" alt=\"\" width=\"32\" height=\"32\" title=\"Your Input\">> " + commandString);
                cm.checkStep(commandString);
            } else {
                cmd.performAction(commandString, sessionId);
            }
            
        }
        PrintWriter out = response.getWriter();
        out.println(head + String.format(body, cm.render()));
        out.close();
    }
}
