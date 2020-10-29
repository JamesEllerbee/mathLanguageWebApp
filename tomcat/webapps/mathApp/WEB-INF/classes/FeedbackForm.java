import java.io.*;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import csu.mathapp.*;

@WebServlet("/SubmitFeedback")
public class MathApp extends HttpServlet {
    final String head = "<head>"
        + "<meta charset=\"UTF-8\">"
        + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
        + "<title>MathApp</title>"
        + "<link rel=\"stylesheet\" href=\"../../assets/style.css\">"
        + "<link rel=\"stylesheet\" href=\"../../assets/bootstrap.css\""
        + "</head>";

    final String body  = "";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}