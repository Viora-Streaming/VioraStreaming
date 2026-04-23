package org.viora.viorastreamingcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.TimeZone;

@SpringBootApplication
public class VioraStreamingCoreApplication {

  public static void main(String[] args) {
    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Kyiv"));
    SpringApplication.run(VioraStreamingCoreApplication.class, args);
  }

}
