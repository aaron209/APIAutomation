package config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailConfig {

    public final String reportTitle = "API";
    public final String projectName = "API";
    public final String emailSubject = "API" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
}
