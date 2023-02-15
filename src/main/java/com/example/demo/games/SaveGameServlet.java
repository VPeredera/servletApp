package com.example.demo.games;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Arrays;
import java.util.Enumeration;

@WebServlet("/saveGameServlet")
public class SaveGameServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Enumeration<String> parameterNames = request.getParameterNames();
        String[] parameters = new String[] {"name", "developer", "releaseDate", "genre", "available"};

        try {
            while (parameterNames.hasMoreElements()) {
                String param = parameterNames.nextElement();
                if (Arrays.stream(parameters).noneMatch(param::equals)) {
                    throw new Exception();
                }
            }

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
                throw new Exception();
            }
            if (status > 0) {
                response.setStatus(200);
                out.print("Record saves successfully!");
            } else {
                response.setStatus(500);
                out.print("Sorry! Unable to save record!");
            }
        } catch (Exception e) {
            response.setStatus(400);
            out.print("Parameters are invalid!");
        } finally {
            out.close();

        }
    }
}
