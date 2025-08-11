import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LogInServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    
	    //System.out.println("Input username: " + username);
	    //System.out.println("Input password: " + password);

	    // Get user from database
	    com.bookstore.models.User user = com.bookstore.data.UserDB.findUser(username);
	    
	    if (user != null && user.getPassword().equals(password)) {
	        // Valid login - store user in session
	        //System.out.println("DB Password: " + user.getPassword());

	        request.getSession().setAttribute("user", user);
	        request.getRequestDispatcher("results.jsp").forward(request, response);

	        //response.sendRedirect("admin"); // Redirect to admin page
	    } else {
	        // Invalid login - show error
	        request.setAttribute("message", "Invalid username or password");
	        request.getRequestDispatcher("login.jsp").forward(request, response);
	    }
	}
}