package com.example.demo.games;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;

@WebServlet("/viewByIDGameServlet")
public class ViewByIDGameServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Enumeration<String> parameterNames = request.getParameterNames();
        String[] parameters = new String[] {"id"};

        try {
            while (parameterNames.hasMoreElements()) {
                String param = parameterNames.nextElement();
                if (Arrays.stream(parameters).noneMatch(param::equals)) {
                    throw new Exception();
                }
            }

            int id = Integer.parseInt(request.getParameter("id"));
            Game game = GameRepository.getGameById(id);
            if (game.getId() == 0) {
                response.setStatus(404);
                out.print("Game with id - " + id + " doesn't exist.");
            } else {
                out.print(game);
            }
        } catch (Exception e) {
            response.setStatus(400);
            out.print("Parameters are invalid!");
        } finally {
            out.close();
        }
    }
}
