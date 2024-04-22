package br.com.alura.service.impl;

import br.com.alura.service.EmailSenderService;
import br.com.alura.vo.RatingVO;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    public static final String EVALUATION = "Course Evaluation: ";

    @Override
    public void send(RatingVO ratingVO) {

        String body = generateNotificationEmail(ratingVO.getCourse().getInstructor().getName(), ratingVO.getCourse().getName(), ratingVO.getComment());

        System.out.printf("Simulating sending email to [%s]:\n%n", ratingVO.getCourse().getInstructor().getEmail());
        System.out.printf("""
                Subject: %s
                Body: %s
                %n""", EVALUATION, body);
    }

    private String generateNotificationEmail(String instructorName, String courseName, String reason) {
        return "Hello " + instructorName + ",\n\n" +
                "We would like to inform you about a recent evaluation of the course " + courseName + " that may require your attention.\n\n" +
                "A student rated the course below 6 and shared the following reason: " + reason + ".\n\n" +
                "Please consider reviewing the course content and ensuring that students are receiving the best possible experience.\n\n" +
                "Sincerely,\n" +
                "Alura Team";
    }
}
