package org.belen.generics;

import java.io.BufferedReader;
import java.io.FileReader;

public interface Day {
    String getFilePath();
    void initialize();
    void handlePart1();
    void handlePart2();

    default String getInputFile()  {
        try {
            StringBuilder resultStringBuilder = new StringBuilder();
            try (BufferedReader br
                         = new BufferedReader(new FileReader(getFilePath()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    resultStringBuilder.append(line).append("\n");
                }
            }
            return resultStringBuilder.toString();
        } catch (Exception e) {
            return "Something went wrong";
        }

    }
}
