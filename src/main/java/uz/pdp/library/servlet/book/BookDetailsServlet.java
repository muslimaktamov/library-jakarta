package uz.pdp.library.servlet.book;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.library.dao.BookDAO;
import uz.pdp.library.entity.Book;

import java.io.IOException;

@WebServlet("/book/details/*")
public class BookDetailsServlet extends HttpServlet {

    private final BookDAO bookDAO = new BookDAO();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Book book = bookDAO.findById(id);
        if (book != null) req.setAttribute("book", book);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/book/book_details.jsp");
        dispatcher.forward(req, resp);
    }
}
