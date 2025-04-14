package model;

public class Funcionario implements Comparable<Funcionario> {
    private String nome;
    private double salario;
    
    public Funcionario(String nome, double salario) {
        this.nome = nome;
        this.salario = salario;
    }
    
    public String getNome() {
        return nome;
    }
    
    public double getSalario() {
        return salario;
    }
    
    public void setSalario(double salario) {
        this.salario = salario;
    }
    
    @Override
    public int compareTo(Funcionario outro) {
        return this.nome.compareTo(outro.getNome());
    }
    
    @Override
    public String toString() {
        return String.format("Funcionario{nome='%s', salario=%.2f}", nome, salario);
    }
    
    @Override
    public int hashCode() {
        // Utiliza o salário como chave para o hashing
        return Double.valueOf(salario).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Funcionario outro = (Funcionario) obj;
        // Considera iguais se o nome e o salário forem iguais
        return nome.equals(outro.nome) && Double.compare(salario, outro.salario) == 0;
    }
}

