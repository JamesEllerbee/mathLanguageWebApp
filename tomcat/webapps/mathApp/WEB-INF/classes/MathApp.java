import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import csu.mathapp.*;

@WebServlet("/application") //configure the request url for this servlet 
public class MathApp extends HttpServlet {

    //todo add html head stuff
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
            + "<button class=\"btn btn-primary mb-2\" type=\"submit\">Submit</button>"
            + "</form>"
            + "</div>"
            + "</div>"
            + "</body>";
    
    // The doGet() runs once per HTTP GET request to this servlet.
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Set the response MIME type of the response message
        response.setContentType("text/html");
        CoreManager cm = CoreManager.getCoreManagerInstance();
        // Allocate a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();
        // Write the response message, in an HTML page
        out.println(head+String.format(body, cm.render()));
        out.close();  // Always close the output writer
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        CoreManager cm = CoreManager.getCoreManagerInstance();
        String commandString = request.getParameter("command");
        cm.appendToBody("> " + commandString);
        Command cmd = CommandDirectory.getCommand(commandString);
        if(cmd != null) {
            cmd.performAction(commandString);
        }
        else{
            cm.appendToBody("<font color=\"red\">Error.</font> No such command!");
        }
        
        PrintWriter out = response.getWriter();
        //out.println("You entered:" + command);
        out.println(head + String.format(body, cm.render()));
        out.close();
    }
}
