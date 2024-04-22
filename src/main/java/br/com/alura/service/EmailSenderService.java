package br.com.alura.service;

import br.com.alura.vo.RatingVO;

public interface EmailSenderService {

    void send(RatingVO ratingVO);
}
