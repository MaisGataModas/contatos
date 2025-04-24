package br.com.maisgata.agenda.dto;

import java.util.List;

public class WhatsappRequestDTO {
    private List<Long> ids;
    private String mensagem;
    
    // Getters e setters
    public List<Long> getIds() {
        return ids;
    }
    
    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
    
    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}