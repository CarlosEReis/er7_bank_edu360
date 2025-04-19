package com.er7system.er7bank.api.model.request;

public record AtualizacaoSenhaRequest(
    String senhaAtual,
    String novaSenha
) { }
