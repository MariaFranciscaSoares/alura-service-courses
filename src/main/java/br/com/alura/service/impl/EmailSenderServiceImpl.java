package br.com.alura.service.impl;

import br.com.alura.service.EmailSenderService;
import br.com.alura.vo.RatingVO;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    public static final String AVALIACAO_DE_CURSO = "Avaliação de Curso: ";

    @Override
    public void send(RatingVO ratingVO) {

        String body = generateNotificationEmail(ratingVO.getCourse().getInstructor().getName(), ratingVO.getCourse().getName(), ratingVO.getComment());

        System.out.printf("Simulating sending email to [%s]:\n%n", ratingVO.getCourse().getInstructor().getEmail());
        System.out.printf("""
                Subject: %s
                Body: %s
                %n""", AVALIACAO_DE_CURSO, body);
    }

    private String generateNotificationEmail(String instructorName, String courseName, String reason) {
        return "Olá " + instructorName + ",\n\n" +
                "Gostaríamos de informá-lo sobre uma avaliação recente do curso " + courseName + " que pode precisar da sua atenção.\n\n" +
                "Um aluno avaliou o curso com uma nota abaixo de 6 e compartilhou o seguinte motivo: " + reason + ".\n\n" +
                "Por favor, considere revisar o conteúdo do curso e assegurar que os alunos estão recebendo a melhor experiência possível.\n\n" +
                "Atenciosamente,\n" +
                "Equipe Alura";
    }
}
