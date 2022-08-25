package report;

import config.EmailConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Map;


public class ReportGenerator {

    public String reportTitle;
    public String projectName;
    public String timeStamp;
    public String testDuration;
    public String reportType;
    public EmailConfig emailConfig;

    public LinkedList<ScenarioContext> scenarioContexts;

    public ReportGenerator() {
        emailConfig = new EmailConfig();
        reportTitle = emailConfig.reportTitle;
        projectName = emailConfig.projectName;
        scenarioContexts = new LinkedList<>();
    }

    public String getTestPassed() {
        long pass = 0;
        try {
            pass = scenarioContexts.stream().filter(context -> context.isScenarioPassed() != false).count();
            return String.valueOf(pass);
        } catch (NullPointerException e) {
            return "0";
        }

    }

    public String getTestFailed() {
        long fail = 0;
        try {
            fail = scenarioContexts.stream().filter(context -> context.isScenarioPassed() == false).count();
            return String.valueOf(fail);
        } catch (NullPointerException e) {
            return "0";
        }
    }

    public String getTotalTestRun() {
        long total = 0;
        try {
            total = scenarioContexts.size();
            return String.valueOf(total);
        } catch (NullPointerException e) {
            return "0";
        }
    }

    public void writeHTML(StringBuilder htmlFile) {
        String reportTimeStamp = "TestReport " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss"));
        String path = "src/test/java/report/testReport";

        File dir = new File(path);
        if (!dir.exists())
            dir.mkdir();
        File file = new File(String.format("%s/%s.html", path, reportTimeStamp));
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(String.valueOf(htmlFile));
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StringBuilder returnHTMLReport() {
        StringBuilder html = new StringBuilder();
        html.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
        html.append(String.format("<head><title>%s</title>", reportTitle));
        html.append("<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>");
        html.append("<script type=\"text/javascript\">  google.charts.load('current', {'packages':['corechart']});");
        html.append("google.charts.setOnLoadCallback(drawChart);");
        html.append("");
        html.append("function drawChart() {");
        html.append(String.format("var data = google.visualization.arrayToDataTable([['Task', 'Test Execution summary'],['Blank Tag', 0],['Test Failed', %s],['Blank Tag', 0],['Test Passed', %s],]);\n", getTestFailed(), getTestPassed()));
        html.append("var options ={title:'Execution Summary:'};");
        html.append("var chart = new google.visualization.PieChart(document.getElementById('piechart'));");
        html.append("chart.draw(data, options);");
        html.append("}</script>");
        html.append("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css'/>");
        html.append("<script src='https://code.jquery.com/jquery-3.4.1.slim.min.js'></script>");
//        html.append("<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js'></script");
        html.append("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js'></script>");
//        html.append("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'/>");
        html.append("<style>.tagFont {");
        html.append("color: black;");
        html.append("#aab5bf;");
        html.append("font-size: 20px;");
        html.append("font-weight: 500;");
        html.append("}");
        html.append("");
        html.append(".nav-pills .nav-link {");
        html.append("border-radius: 0px;");
        html.append("}");
        html.append("");
        html.append(".nav-pills .nav-link.active, .nav-pills .show > .nav-link {");
        html.append("color: black;");
        html.append("background-color: #f4f4f5;");
        html.append("border: 1px solid #dee2e6;");
        html.append("font-size: 20px;");
        html.append("font-weight: 500;");
        html.append("}");
        html.append("");
        html.append(".listHeader {");
        html.append("list-style-type: disc;");
        html.append("margin-left: 70px;");
        html.append("}");
        html.append("");
        html.append(".green {");
        html.append("color: green;");
        html.append("margin-right: 5px;");
        html.append("}");
        html.append("");
        html.append(".red {");
        html.append("color: red;");
        html.append("margin-right: 5px;");
        html.append("}");
        html.append("");
        html.append(".testCount {");
        html.append("text-align: center;");
        html.append("color: #fff;");
        html.append("margin-right: 20px;");
        html.append("}");
        html.append("");
        html.append(".txtScenario {");
        html.append("font-size: 1rem;");
        html.append("font-weight: 500");
        html.append("}");
        html.append("");
        html.append(".cssSpan {");
        html.append("word-break: break-all;");
        html.append("overflow-wrap: break-word;");
        html.append("word-wrap: break-word;");
        html.append("}</style>");
        html.append("</head>");
        html.append("<body>");
        html.append("<div class='container-fluid'>");
        html.append("<div class='row'>");
        html.append("<div class='col-sm-4'><h2>Test Execution Report:</h2><h4>Test Details:</h4>");
        html.append("<ul class='list - group'>");
        html.append(String.format("<li class='listHeader'>Project: %s</li>", projectName));
        html.append(String.format("<li class='listHeader'>Start Time: %s</li>", timeStamp));
        html.append(String.format("<li class='listHeader'>Total Duration: %s second</li>", testDuration));
        html.append("</ul>");
        html.append("<h4>Test Summary:</h4>");
        html.append("<ul class='list - group'>");
        html.append(String.format("<li class='listHeader'>Total Test Run: %s</li>", getTotalTestRun()));
        html.append(String.format("<li class='listHeader'>Total Test Passed: %s</li>", getTestPassed()));
        html.append(String.format("<li class='listHeader'>Total Test Failed: %s</li>", getTestFailed()));
        html.append("</ul>");
        html.append("</div>");
        html.append("<div class='col-sm-4'>");
        html.append("<div id=\"piechart\" style=\"width: 400px; height: 250px; float: right\"></div>");
        html.append("</div>");
        html.append("</div>");
        html.append("<div class='row' style='border-top:1px solid rgba(0,0,0,.125);'>");
        html.append("<div class='col-sm-3' style='border-right:1px solid rgba(0,0,0,.125);padding-right:0px;padding-left: 0px;'>");
        html.append("<ul class='nav nav-pills flex-column' id='myTab' role='tablist'>");
        html.append("<li class='nav-item'><a class='nav-link show active tagFont' id='Integration-tab' data-toggle='tab'");
        html.append(String.format("href='#Integration' role='tab' aria-controls='Integration' aria-selected='true'>%s<br/>", reportType));
        html.append("<div class='col-sm-3 btn btn-success testCount'><span");
        html.append("        style='font-size:16px;font-weight:500'>PASSED</span>");
        html.append(String.format("<h3>%s</h3></div>", getTestPassed()));
        html.append("<div class='col-sm-3 btn btn-danger testCount'><span");
        html.append("        style='font-size:16px;font-weight:500'>FAILED</span>");
        html.append(String.format("<h3>%s</h3></div>", getTestFailed()));
        html.append("<div class='col-sm-3 btn btn-info testCount'><span");
        html.append("        style='font-size:16px;font-weight:500'>TOTAL</span>");
        html.append(String.format("<h3>%s</h3></div>", getTotalTestRun()));
        html.append("</a></li>");
        html.append("</ul>");
        html.append("</div>");
        html.append("<div class='col-sm-9'>");
        html.append("<div class='tab-content' id='myTabContent'>");
        html.append("<div class='tab-pane fade show active' id='Integration' role='tabpanel'");
        html.append("aria-labelledby='Integration-tab'>");
        html.append("<div class='row'>");
        html.append(String.format("<div class='col-sm-12'><h4>%s: </h4>", reportType));
        html.append("");
        html.append("");
        html.append("");
        html.append("");
        html.append("");
        html.append("");
        html.append("");

        int i = 0;
        for (ScenarioContext each : scenarioContexts) {
            String step = String.valueOf(++i);
            html.append("<div style='margin-top: 5px;' class='col-sm-12 list-group-item'>");
            html.append(String.format("<div class='' data-toggle='collapse' data-target='.Step-%s'>", step));
            html.append(each.isScenarioPassed() == true ? "<i class='fa fa-check-circle green'></i>" : "<i class='fa fa-check-circle red'></i>");
            html.append(String.format("<span class='txtScenario'><b>Scenario-%s.</b> %s </span>", each.getTestId(), each.getScenarioName()));
            html.append("</div>");
            html.append("<div class=\"collapse multi-collapse Step-" + step + " \" style='margin-left: 5px;'>");
            html.append("<div>");
            html.append("<ol>");

            for (String line : each.getScenarioLines()) {
                String express = line.substring(0, line.indexOf(" "));
                String scenario = line.substring(line.indexOf(" "));
                html.append(String.format("<li><b> %s</b> %s</li>", express, scenario));
            }

            html.append("</ol>");
            html.append("</div>");

            // throw error message
            html.append(each.getErrorMessage() != null ? "<h6>Error Logs:</h6>" : "");
            html.append(each.getErrorMessage() != null ? String.format("<div style='margin-left:22px;color:red; '>%s</div><br/>", each.getErrorMessage()) : "<br/>");
            html.append("<div><h6>API Request:</h6>");
            html.append("<div style='font-size:12px; margin-left:22px;'>");
            html.append(String.format("<span class='cssSpan'><b>%s</b>: %s </span><br/>", each.getMethod(), each.getUrl()));

            // request headers
            for (Map.Entry<String, String> current : each.getRequestHeaders().entrySet()) {
                html.append(String.format("<span class='cssSpan'><b>%s</b>: %s </span><br/>", current.getKey(), current.getValue()));
            }

            html.append(String.format("<span class='cssSpan'><b>application/json</b>: %s </span><br/>", each.getRequestBody()));
            html.append("</div>");
            html.append(String.format("<h6>API Response status code: %s</h6>", each.getActualHttpCode()));
            html.append("<div style='font-size:12px; margin-left:22px;'>");

            // response headers
            for (Map.Entry<String, String> current : each.getResponseHeaders().entrySet()) {
                html.append(String.format("<span class='cssSpan'> <b>%s</b>: %s </span><br/>", current.getKey(), current.getValue()));
            }

            html.append(String.format("<span class='cssSpan'><b>application/json</b>: %s </span><br/>", each.getResponseBody()));
            html.append("</div>");
            html.append("</div>");
            html.append("</div>");
            html.append("</div>");

        }
        html.append("");
        html.append("");
        html.append("");
        html.append("");
        html.append("");
        html.append("");
        html.append("");
        html.append("");
        html.append("");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("</body>");
        html.append("</html>");

        return html;
    }

}