package report;

import io.cucumber.java.*;
import steps.RegressionSteps;

public class ReportHook {

    private static ReportGenerator report;
    private ScenarioContext scenarioContext = new ScenarioContext();
    static long startTime;
    static long endTime;
    static long duration;

    @BeforeAll
    public static void beforeAll() {
        startTime = System.currentTimeMillis();
    }

    @Before()
    public void startScenario(Scenario cucumberScenario){
    }

    @After()
    public void endScenario(Scenario cucumberScenario){
        report = RegressionSteps.report;
        scenarioContext = RegressionSteps.scenarioContext;
        boolean pass = !cucumberScenario.isFailed();
        scenarioContext.setScenarioPassed(pass);
        report.scenarioContexts.add(scenarioContext);
    }

    @AfterAll()
    public static void after_all(){
        StringBuilder html;
        endTime = System.currentTimeMillis();
        duration = (endTime - startTime) / 1000;
        report.testDuration = ""+ duration;
        html = report.returnHTMLReport();
        report.writeHTML(html);
        report = null;
    }
}
