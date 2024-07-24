package com.example.mrdoob;

import io.github.jspinak.brobot.database.services.AllStatesInProjectService;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class TestInterfaceServlet extends HttpServlet implements Servlet {

    private final AllStatesInProjectService allStatesInProjectService;

    public TestInterfaceServlet(AllStatesInProjectService allStatesInProjectService) {
        this.allStatesInProjectService = allStatesInProjectService;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //Get allStateNames dynamically
        Set<String> allStateNames = allStatesInProjectService.getAllStateNames();
        String[] allStateNamesArray = allStateNames.toArray(new String[0]);
        List<String> listStates = new ArrayList<>(allStateNames.size());
        int length = allStateNamesArray.length;
        if (length > 0) {
            listStates.add(allStateNamesArray[length - 1]);
            for (int i = 0; i < length - 1; i++) {
                if (!allStateNamesArray[i].equals("unknown")) {
                    listStates.add(allStateNamesArray[i]);
                }
            }
        }

        //List<String> listStates = Arrays.asList("Homepage", "Harmony", "About", "Source");

        // Create App
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>"
            + "<html lang='en'>"
            + "<head>"
            + "<meta charset='UTF-8'>"
            + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
            + "<title>Test Interface</title>"
            + "<link rel='stylesheet' href='styles.css'>"
            + "</head>"
            + "<body>"
            + "<div class='container'>"
            + "<h1>Test Interface</h1>"
            + "<div class='test-list'>"
            + "<table>"
            + "<tr>"
            + "<th>Action Order</th>"
            + "<th>Test Name</th>"
            + "<th>Only this step</th>"
            + "<th>Check</th>"
            + "<th>From start to this step</th>"
            + "<th>Check</th>"
            + "</tr>");

        for (int testIndex = 0; testIndex < listStates.size(); testIndex++) {
            String test = listStates.get(testIndex);
            int actionNumber = testIndex + 1;
            out.println("<tr>"
                + "<td>" + "Step " + actionNumber + "</td>"
                + "<td>" + test + "</td>"
                + "<td>"
                + "<button onclick=\"startTest('" + test + "', " + actionNumber + ")\">Start Test</button>"
                + "</td>"
                + "<td id='check-only-" + actionNumber + "'>-</td>"
                + "<td>");
            if (testIndex == 0) {
                out.println("<button disabled>Start Test</button>");
            } else {
                out.println("<button onclick=\"startTestsFromStart('" + test + "', " + actionNumber + ", " + "'" + String.join(",", listStates) + "'" + ")\">Start Test</button>");
            }
            out.println("</td>"
                + "<td id='check-from-start-" + actionNumber + "'>-</td>"
                + "</tr>");
        }

        out.println("</table>"
            + "</div>"
            + "</div>"
            + "<script src='scripts.js'></script>"
            + "</body>"
            + "</html>");
    }
}
