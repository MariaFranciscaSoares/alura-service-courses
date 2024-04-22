package br.com.alura.record;


import br.com.alura.vo.CourseVO;

import java.util.List;

public record GetCourseResponse(List<CourseVO> courses, String message) {
}
