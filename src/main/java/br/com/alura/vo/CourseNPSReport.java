package br.com.alura.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseNPSReport {

    private String courseName;
    private double nps;

}
