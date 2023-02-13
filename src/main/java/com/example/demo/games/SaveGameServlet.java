package com.example.demo.games;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet("/saveGameServlet")
public class SaveGameServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Game game = new Game();
        game.setName(request.getParameter("name"));
        game.setDeveloper(request.getParameter("developer"));
        game.setReleaseDate(Date.valueOf(request.getParameter("releaseDate")));
        game.setGenre(request.getParameter("genre"));
        game.setAvailable(Boolean.parseBoolean(request.getParameter("available")));

        int status = 0;
        if (!game.getGenre().equals("R18")) {
            status = GameRepository.save(game);
        } else {
            out.println("Banned rating");
        }
        if (status > 0) {
            out.print("Record saves successfully!");
        } else {
            out.print("Sorry! Unable to save record!");
        }
        out.close();
    }
}
