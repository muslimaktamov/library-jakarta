package uz.pdp.library.servlet.auth;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.library.dao.AuthUserDAO;
import uz.pdp.library.entity.AuthUser;
import uz.pdp.library.enums.Role;
import uz.pdp.library.enums.Status;

import java.io.IOException;
import java.util.Objects;

import static uz.pdp.library.utils.PasswordUtils.encode;
import static uz.pdp.library.utils.StringUtils.emailValidator;
import static uz.pdp.library.utils.StringUtils.passwordValidator;

@WebServlet("/auth/register")
public class AuthRegisterServlet extends HttpServlet {

    private final AuthUserDAO authUserDAO = new AuthUserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/auth/register.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        String error = null;

        if (!emailValidator(email)) {
            error = "Not Valid email";
        /*} else if (authUserDAO.findByEmail(email) != null) {
            error = "this email already taken";*/
        } else if (!passwordValidator(password)) {
            error = "wrong password type";
        } else if (!Objects.equals(password, confirmPassword)) {
            error = "password and conform password are not equal ";
        }

        if (error != null) {
            req.setAttribute("errorMessage", error);
            req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
            return;
        }


        AuthUser authUser = AuthUser
                .childBuilder()
                .email(email)
                .password(encode(password))
                .role(Role.USER)
                .status(Status.ACTIVE)
                .build();
        authUserDAO.save(authUser);

        req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);

    }
}
