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
import java.util.Iterator;

@WebServlet("/putGameServlet")
public class PutGameServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Enumeration<String> parameterNames = request.getParameterNames();
        String[] parameters = new String[] {"id", "name", "developer", "releaseDate", "genre", "available"};

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
            game.setId(Integer.parseInt(request.getParameter("id")));

            int status = GameRepository.update(game);
            if (status > 0) {
                response.setStatus(300);
                response.sendRedirect("viewGameServlet");
            } else {
                response.setStatus(404);
                out.print("Game with id - " + game.getId() + " doesn't exist.");
            }
        } catch (Exception e) {
            response.setStatus(400);
            out.print("Parameters are invalid!");
        } finally {
            out.close();
        }
    }
}
