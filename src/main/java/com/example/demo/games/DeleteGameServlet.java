package com.example.demo.games;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteGameServlet")
public class DeleteGameServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        int status = GameRepository.delete(Integer.parseInt(request.getParameter("id")));
        if (status > 0) {
            response.sendRedirect("viewGameServlet");
        } else {
            out.print("Sorry! Unable to delete record");
        }
        out.close();
    }
}
