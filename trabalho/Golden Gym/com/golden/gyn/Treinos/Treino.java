package com.golden.gyn.Treinos;

public class Treino {
    
    private String Exercicio;
    private int repeticoesIn;
    private int repeticoesOut;
    private String data;

    public Treino() {

    }


    public Treino(String Exercicio, int repeticoesIn, int repeticoesOut, String data){
         this.Exercicio = Exercicio;
         this.repeticoesIn = repeticoesIn;
         this.repeticoesOut = repeticoesOut;
         this.data = data;
   }    


    public String getExerciceAsString(){
      return Exercicio;
    }


    public String getExercicio() {
        return Exercicio;
    }



    public int getRepeticoesIn() {
        return repeticoesIn;
    }


    public int getRepeticoesOut() {
        return repeticoesOut;
    }

    
 public String getData() {
        return data;
    }

    public void setExercicio(String exercicio) {
        this.Exercicio = exercicio;
    }


    public void setRepeticoes(int repeticoesIn, int repeticoesOut) {
        this.repeticoesIn = repeticoesIn;
        this.repeticoesOut = repeticoesOut;
    }


    public void setData(String data) {
        this.data = data;
    }


   

}
