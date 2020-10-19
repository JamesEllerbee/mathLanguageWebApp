import java.io.*;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import csu.mathapp.*;

@WebServlet("/application") //configure the request url for this servlet 
public class MathApp extends HttpServlet {

    final String head = "<head>"
        + "<title>Math Tutoring System</title>"
        + "<link rel=\"stylesheet\" href=\"../../assets/style.css\">"
        + "<link rel=\"stylesheet\" href=\"../../assets/bootstrap.css\""
    + "</head>";

    final String body = "<body class=\"theme\">"
                + "<div class=\"text-center\">"
                    + "<span class=\"title\">MathApp.xyz!</span>"
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
            userCookie = new Cookie("JSSESSIONID", sessionId);
            response.addCookie(userCookie);
        }
        response.setContentType("text/html");
        String sessionId = session.getId();
        CoreManager cm = CoreManager.getCoreManagerInstance(userCookie.getValue());
        String commandString = request.getParameter("command");
        cm.appendToBody("> " + commandString);
        Command cmd = CommandDirectory.getCommand(commandString);
        if(cmd != null) {
            cmd.performAction(commandString, sessionId);
        }
        else{
            cm.appendToBody("<font color=\"red\">Error.</font> No such command!");
        }
        PrintWriter out = response.getWriter();
        out.println(head + String.format(body, cm.render()));
        out.close();
    }
}
