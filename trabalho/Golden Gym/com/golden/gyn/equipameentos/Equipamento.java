package com.golden.gyn.equipameentos;

public class Equipamento{
    private String Equipamento;
    private String categoria;
    private String reserva;
    private String horario;
    
    public Equipamento() {}


    public Equipamento(String Equipamento){
        this.Equipamento = Equipamento;
    }

    public Equipamento(String equipamento, String categoria, String reserva, String horario) {
        Equipamento = equipamento;
        this.categoria = categoria;
        this.reserva = reserva;
        this.horario = horario;
    }


    //Getters
    public String getEquipamento() {
        return Equipamento;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getReserva() {
        return reserva;
    }

    public String getHorario() {
        return horario;
    }


    //Setters
    public void setEquipamento(String equipamento) {
        Equipamento = equipamento;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setReserva(String reserva) {
        this.reserva = reserva;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }


    

}


