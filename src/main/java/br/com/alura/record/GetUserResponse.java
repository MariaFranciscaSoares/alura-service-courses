package br.com.alura.record;

import br.com.alura.vo.UserVO;

public record GetUserResponse(UserVO userVO, String message) {
}