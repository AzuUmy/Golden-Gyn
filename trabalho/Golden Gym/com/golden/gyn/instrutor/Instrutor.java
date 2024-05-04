package com.golden.gyn.instrutor;

import java.time.LocalTime;

public class Instrutor {

    private String nome;
    private String areaDeAtuacao;
    private String horarioInicio;
    private String horarioFim;


    public Instrutor(){}


    public Instrutor(String nome){
        this.nome = nome;
    }

    public Instrutor(LocalTime horarioInicio, LocalTime horarioFim){
        this.horarioInicio = horarioInicio.toString();
        this.horarioFim = horarioFim.toString();
    }

    public Instrutor(String nome, String areaDeAtuacao, String horarioInicio,
     String horarioFim) {
        this.nome = nome;
        this.areaDeAtuacao = areaDeAtuacao;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }


    public String getNome() {
        return nome;
    }


    public String getAreaDeAtuacao() {
        return areaDeAtuacao;
    }


    public String getHorarioInicio() {
        return horarioInicio;
    }


    public String getHorarioFim() {
        return horarioFim;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public void setAreaDeAtuacao(String areaDeAtuacao) {
        this.areaDeAtuacao = areaDeAtuacao;
    }


    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }


    public void setHorarioFim(String horarioFim) {
        this.horarioFim = horarioFim;
    }


    

    
}
