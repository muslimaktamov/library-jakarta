package uz.pdp.library.servlet.book;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.library.dao.BookDAO;

import java.io.IOException;

@WebServlet("/book/list")
public class BookListServlet extends HttpServlet {

    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("books", bookDAO.findAll());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/book/book_list.jsp");
        dispatcher.forward(req, resp);
    }
}
