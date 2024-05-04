package com.golden.gyn.reservas;


/*classe de reservas com um contrutyor padrao
 para reservas para ser posssivel passar um reserva no formato desejado*/
public class Reservas {
    
    private String aluno;
    private String intrutor;
    private String horario;
    private String equipament;
    private String treino;
    private String dia;

    public Reservas(String aluno, String intrutor, String horario, 
    String equipament, String treino, String dia) {
        this.aluno = aluno;
        this.intrutor = intrutor;
        this.horario = horario;
        this.equipament = equipament;
        this.treino = treino;
        this.dia = dia;
    }
    public String getAluno() {
        return aluno;
    }
    public String getIntrutor() {
        return intrutor;
    }
    public String getHorario() {
        return horario;
    }
    public String getEquipament() {
        return equipament;
    }
    public String getTreino() {
        return treino;
    }
    public String getDia() {
        return dia;
    }
  

    
    

}
