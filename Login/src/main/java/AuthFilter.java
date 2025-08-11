import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookstore.models.User;

@WebFilter("/*")
public class AuthFilter implements Filter {
    public void init(FilterConfig fConfig) throws ServletException {}
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        
        // Check if user exists in session
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        
        if (isLoggedIn) {
            // User is authenticated - continue request
            chain.doFilter(request, response);
        } else {
            // Not logged in - redirect to login
            ((HttpServletResponse) response).sendRedirect("login.jsp");
        }
    }    
    public void destroy() {}
}