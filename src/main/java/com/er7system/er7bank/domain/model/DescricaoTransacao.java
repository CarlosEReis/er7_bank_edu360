package com.er7system.er7bank.domain.model;

public enum DescricaoTransacao {

        TRANSFERENCIA_RECEBIDA("Transferência Recebida"),
        PAGAMENTO_DE_FATURA("Pagamento de Fatura"),
        COMPRA_NO_DEBITO("Compra no Débito"),
        TRANSFERENCIA_ENVIADA("Transferência Enviada"),
        PAGAMENTO_EFETUADO("Pagamento Efetuado"),
        DEPOSITO_RECEBIDO("Depósito Recebido"),
        SAQUE_REALIZADO("Saque Realizado");

        private String descricao;

        DescricaoTransacao(String descricao) {
                this.descricao = descricao;
        }

        public String getDescricao() {
                return descricao;
        }
}
