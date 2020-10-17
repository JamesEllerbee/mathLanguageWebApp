import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import csu.mathapp.CoreManager;

@WebServlet("/application") //configure the request url for this servlet 
public class MathApp extends HttpServlet {

    //todo add html head stuff
    final String head = "<head>"
    + "<link rel=\"stylesheet\" href=\"../../assets/style.css\">"
    + "</head>";
    
    // The doGet() runs once per HTTP GET request to this servlet.
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Set the response MIME type of the response message
        response.setContentType("text/html");
        // Allocate a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();
        // Write the response message, in an HTML page
        CoreManager cm = CoreManager.getCoreManagerInstance();
        String body = "<body>"
            + "<div class=\"main\">"
            + cm.render()
            + "<div>"
            + "<form action=\"application\" method=\"post\">"
            + "<input type=\"text\" id=\"command\" name=\"command\" </input>"
            + "<input type=\"submit\" value=\"Submit\"</input>"
            + "</form>"
            + "</div>"
            + "</div>"
            + "</body>";
        out.println(head+body);
        out.close();  // Always close the output writer
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("yes");
        out.close();
    }
}
