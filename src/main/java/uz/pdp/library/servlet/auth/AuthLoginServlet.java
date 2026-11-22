package uz.pdp.library.servlet.auth;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uz.pdp.library.dao.AuthUserDAO;
import uz.pdp.library.entity.AuthUser;
import uz.pdp.library.enums.Status;

import java.io.IOException;

import static uz.pdp.library.utils.PasswordUtils.check;

@WebServlet("/auth/login")
public class AuthLoginServlet extends HttpServlet {
    private final AuthUserDAO authUserDAO = new AuthUserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/auth/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        AuthUser authUser = authUserDAO.findByEmail(email);

        if (checkErrors(req, resp, email, password, authUser)) return;

        HttpSession session = req.getSession();
        session.setAttribute("id", authUser.getId());
        session.setAttribute("email", email);
        session.setAttribute("role", authUser.getRole());

        resp.sendRedirect("/book/list");
    }


    private static boolean checkErrors(HttpServletRequest req, HttpServletResponse resp, String email, String password, AuthUser authUser) throws ServletException, IOException {
        String error = null;

        if (email == null || password == null) {
            error = "Username or password cannot be null";
        } else if (!check(password, authUser.getPassword())) {
            error = "Invalid credentials";
        } else if (authUser.getStatus() == Status.BLOCK) {
            error = "User is not active";
        }

        if (error != null) {
            req.setAttribute("errorMessage", error);
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
            return true;
        }
        return false;
    }
}
