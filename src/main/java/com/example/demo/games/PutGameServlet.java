package com.example.demo.games;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet("/putGameServlet")
public class PutGameServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Game game = new Game();
        game.setId(Integer.parseInt(request.getParameter("id")));
        game.setName(request.getParameter("name"));
        game.setDeveloper(request.getParameter("developer"));
        game.setReleaseDate(Date.valueOf(request.getParameter("releaseDate")));
        game.setGenre(request.getParameter("genre"));
        game.setAvailable(Boolean.parseBoolean(request.getParameter("available")));

        int status = GameRepository.update(game);
        if (status > 0) {
            response.sendRedirect("viewGameServlet");
        } else {
            out.print("Sorry! Unable to update record!");
        }
        out.close();
    }
}
