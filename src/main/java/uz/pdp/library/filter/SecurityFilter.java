package uz.pdp.library.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uz.pdp.library.enums.Role;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;


@WebFilter(filterName = "SecurityFilter", value = "/*")
public class SecurityFilter implements Filter {
    /*
     /auth/login
     /auth/register

     */
    private static final List<String> WHITE_LIST = List.of(
            "/auth/login",
            "/auth/register"
    );

    private static final Predicate<String> isOpen = WHITE_LIST::contains;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        String requestURI = request.getRequestURI();
        System.out.println(requestURI);

        if (isOpen.test(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");


        if (session.getAttribute("id") == null) {
            response.sendRedirect("/auth/login");
        }

        if (requestURI.startsWith("/admin") && !role.equals(Role.ADMIN)) {
            response.sendError(403, "Permission Dined");
            return;
        }

        chain.doFilter(request, response);

    }
}
