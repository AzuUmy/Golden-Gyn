package com.golden.gyn.Aluno;
public class Alunos {
    private String nome;
    private Double peso;
    private Double altura;

public Alunos(){

}



public Alunos(String nome) {
    this.nome = nome;
}



public Alunos(String nome, Double peso, Double altura) {
    this.nome = nome;
    this.peso = peso;
    this.altura = altura;
}


public Double calculoDeImc(Double Peso, Double Altura){
        this.peso = Peso;
        this.altura = Altura;
        
        Double resultadoAltura = Altura * Altura;
        Double resultado = Peso / resultadoAltura;  
        
        System.out.println("Imc atual: " + resultado);
        
        if(resultado < 18.5 ){
             System.out.println("Abaixo do Peso");
        }
        
        if(resultado > 25 && resultado < 30){
            System.out.println("Sobre Peso");
        }
        
        if(resultado >= 30){
            System.out.println("Obesidade");
        }
        
        if(resultado > 18.5 && resultado < 25){
            System.out.println("Peso Ideal");
        }
        
        return resultado;
    }
    

    public String getNome() {
        return nome;
    }
    
    
    public Double getPeso() {
        return peso;
    }
    
    
    public Double getAltura() {
        return altura;
    }
    
    
    
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    public void setPeso(Double peso) {
        this.peso = peso;
    }
    
    
    public void setAltura(Double altura) {
        this.altura = altura;
    }



}
