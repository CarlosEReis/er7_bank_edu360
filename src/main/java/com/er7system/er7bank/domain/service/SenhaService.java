package com.er7system.er7bank.domain.service;

import com.er7system.er7bank.domain.model.Cartao;
import com.er7system.er7bank.domain.utils.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.er7system.er7bank.domain.utils.SecurityUtils.confirmaSenha;

@Service
public class SenhaService {

    private static PasswordEncoder passwordEncoder = null;

    public SenhaService(PasswordEncoder passwordEncoder) {
        SenhaService.passwordEncoder = passwordEncoder;
    }

    public static String geraSenhaAleatoria() {
        Random random = new Random();
        int senha = random.nextInt(9999 - 1000) + 1000;
        System.out.println("Senha do cartão gerada: " + senha);
        return passwordEncoder.encode(String.valueOf(senha));
    }

    public void alterarSenha(Cartao cartao, String senhaAtual, String senhaNova) {
        if (!confirmaSenha(senhaAtual, cartao.getSenha()))
            throw new RuntimeException("Senha atual não confere.");

        if (senhaAtual.equals(senhaNova))
            throw new RuntimeException("A nova senha não pode ser igual a atual.");

        cartao.setSenha(SecurityUtils.encodeSenha(senhaNova));
    }
}
