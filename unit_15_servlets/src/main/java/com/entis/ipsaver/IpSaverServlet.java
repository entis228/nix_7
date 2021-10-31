package com.entis.ipsaver;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "ipsaver-servlet", urlPatterns = "/")
public class IpSaverServlet extends HttpServlet {

    private static final ConcurrentHashMap<String, String>loggedUsers=new ConcurrentHashMap<>();

    private static final long serialVersionUID = -8948379822734246922L;

    private static final Logger LOGGER = LoggerFactory.getLogger(IpSaverServlet.class);

    @Override
    public void init() {
        LOGGER.info(getServletName() + " initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter responseBody = resp.getWriter();
        String clientIp=req.getRemoteHost();
        String userAgent=req.getHeader("User-agent");
        loggedUsers.put(clientIp,userAgent);
        resp.setContentType("text/html");
        responseBody.println("<h1 align=\"center\">All visited users</h1>");
        loggedUsers.forEach((x,y)->{
            if(x.equals(clientIp))
            responseBody.println("<p align=\"center\"><b>%s :: %s</b></p>".formatted(x,y));
            else responseBody.println("<p align=\"center\">%s :: %s</p>".formatted(x,y));
        });
    }

    @Override
    public void destroy() {
        LOGGER.info(getServletName() + " destroyed");
    }
}
