package com.er7system.er7bank.domain.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private static PasswordEncoder passwordEncoder = null;

    public SecurityUtils(PasswordEncoder passwordEncoder) {
        SecurityUtils.passwordEncoder = passwordEncoder;
    }

    public static boolean confirmaSenha(String senha, String senhaEcondada ) {
        return passwordEncoder.matches(senha, senhaEcondada);
    }

    public static String encodeSenha(String senhaNova) {
        return passwordEncoder.encode(senhaNova);
    }

}
