/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package murach.download;

import java.io.*;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import murach.business.User;
import murach.data.UserIO;
import murach.until.CookieUtil;

public class DownloadServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewAlbums"; // default action
        }

        // perform action and set URL to appropriate page
        String url = "/index.jsp";
        if (action.equals("viewAlbums")) {
            url = "/index.jsp";
        } else if (action.equals("checkUser")) {
            url = checkUser(request, response);

        } else if (action.equals("viewCookies")) {
            url = "/viewCookies.jsp"; // Gọi hàm xem cookies
        } else if (action.equals("deleteCookies")) {
            url = deleteCookies(request, response);
        }

        // forward to the view
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");

        // perform action and set URL to appropriate page
        String url = "/index.jsp";
        if (action.equals("registerUser")) {
            url = registerUser(request, response);
        }

        // forward to the view
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String checkUser(HttpServletRequest request,
                             HttpServletResponse response) {

        String productCode = request.getParameter("productCode");
        HttpSession session = request.getSession();
        session.setAttribute("productCode", productCode);
        User user = (User) session.getAttribute("user");

        String url;
        // if User object doesn't exist, check email cookie
        if (user == null) {
            Cookie[] cookies = request.getCookies();
            String emailAddress = CookieUtil.getCookieValue(cookies, "emailCookie");

            // if cookie doesn't exist, go to Registration page
            if (emailAddress == null || emailAddress.equals("")) {
                url = "/register.jsp";
            }
            // if cookie exists, create User object and go to Downloads page
            else {
                try {
                    emailAddress = URLDecoder.decode(emailAddress, StandardCharsets.UTF_8.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ServletContext sc = getServletContext();
                String path = sc.getRealPath("/WEB-INF/EmailList.txt");
                user = UserIO.getUser(emailAddress, path);
                session.setAttribute("user", user);
                url = "/" + productCode + "_download.jsp";
            }
        }
        // if User object exists, go to Downloads page
        else {
            url = "/" + productCode + "_download.jsp";
        }
        return url;
    }

    private String registerUser(HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        // get the user data
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        // store the data in a User object
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        // write the User object to a file
        ServletContext sc = getServletContext();
        String path = sc.getRealPath("/WEB-INF/EmailList.txt");
        UserIO.add(user, path);

        // store the User object as a session attribute
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // Encode email and firstName to remove invalid characters
        String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString());
        String encodedFirstName = URLEncoder.encode(firstName, StandardCharsets.UTF_8.toString());

        // add a cookie that stores the encoded user's email to browser
        Cookie c = new Cookie("emailCookie", encodedEmail);
        c.setMaxAge(2 * 365 * 24 * 60 * 60); // set age to 2 years
        c.setPath("/");
        response.addCookie(c);

        // add a cookie that stores the encoded user's first name as a cookie
        Cookie c2 = new Cookie("firstNameCookie", encodedFirstName);
        c2.setMaxAge(60 * 60 * 24 * 365 * 2); // set age to 2 years
        c2.setPath("/");                      // allow entire app to access it
        response.addCookie(c2);

        // create and return a URL for the appropriate Download page
        String productCode = (String) session.getAttribute("productCode");
        String url = "/" + productCode + "_download.jsp";
        return url;
    }

    private String deleteCookies(HttpServletRequest request,
                                 HttpServletResponse response) {
        Cookie[] cookies = request.getCookies(); // Lấy cookies từ request
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0); // delete the cookie
            cookie.setPath("/"); // allow the download application to access it
            response.addCookie(cookie);
        }
        String url = "/deleteCookies.jsp";
        return url;
    }
}
