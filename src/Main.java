import java.util.Scanner;
import model.HashTable;
import service.HashList;
import model.Node;
import model.Funcionario;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Cria a tabela de hash para funcionários com tamanho 20
        HashList<Funcionario> hashFuncionario = new HashList<>();
        HashTable<Funcionario> tabela = hashFuncionario.createHashTable(20);
        
        int opcao = 0;
        do {
            System.out.println("\nMenu:");
            System.out.println("1- Cadastrar funcionário");
            System.out.println("2- Conceder aumento percentual para todos os funcionários");
            System.out.println("3- Consultar a soma salarial dos funcionários com salário superior a 500");
            System.out.println("4- Consultar todos os funcionários");
            System.out.println("5- Excluir por nome");
            System.out.println("6- Sair");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Opção inválida! Digite um número.");
                continue;
            }
            
            switch(opcao) {
                case 1:
                    System.out.print("Digite o nome do funcionário: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o salário do funcionário: ");
                    double salario = Double.parseDouble(scanner.nextLine());
                    Funcionario funcionario = new Funcionario(nome, salario);
                    hashFuncionario.insert(tabela, funcionario);
                    System.out.println("Funcionário cadastrado.");
                    break;
                    
                case 2:
                    System.out.print("Digite o percentual de aumento: ");
                    double percentual = Double.parseDouble(scanner.nextLine());
                    aplicarAumento(tabela, percentual);
                    System.out.println("Aumento aplicado.");
                    break;
                    
                case 3:
                    double soma = somaSalariosAcima(tabela, 500.0);
                    System.out.println("Soma salarial dos funcionários com salário > 500: " + soma);
                    break;
                    
                case 4:
                    System.out.println("Funcionários cadastrados:");
                    System.out.println(hashFuncionario.toString(tabela));
                    break;
                    
                case 5:
                    System.out.print("Digite o nome do funcionário a ser excluído: ");
                    String nomeExcluir = scanner.nextLine();
                    boolean removido = removerPorNome(tabela, nomeExcluir);
                    if(removido) {
                        System.out.println("Funcionário removido com sucesso.");
                    } else {
                        System.out.println("Funcionário não encontrado.");
                    }
                    break;
                    
                case 6:
                    System.out.println("Saindo...");
                    break;
                    
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 6);
        
        scanner.close();
    }
    
    /**
     * Aplica um aumento percentual a todos os funcionários presentes na tabela.
     */
    private static void aplicarAumento(HashTable<Funcionario> tabela, double percentual) {
        for (int i = 0; i < tabela.getItems().size(); i++){
            Node<Funcionario> node = tabela.getItems().get(i);
            while (node != null && node.getValue() != null){
                Funcionario f = node.getValue();
                double novoSalario = f.getSalario() * (1 + percentual / 100);
                f.setSalario(novoSalario);
                node = node.getNext();
            }
        }
    }
    
    /**
     * Retorna a soma dos salários dos funcionários com salário superior ao limite informado.
     */
    private static double somaSalariosAcima(HashTable<Funcionario> tabela, double limite) {
        double soma = 0;
        for (int i = 0; i < tabela.getItems().size(); i++){
            Node<Funcionario> node = tabela.getItems().get(i);
            while (node != null && node.getValue() != null){
                Funcionario f = node.getValue();
                if (f.getSalario() > limite) {
                    soma += f.getSalario();
                }
                node = node.getNext();
            }
        }
        return soma;
    }
    
    /**
     * Remove da tabela o(s) funcionário(s) cujo nome corresponda ao informado.
     * Percorre todos os buckets da tabela.
     * Retorna true se algum funcionário for removido.
     */
    private static boolean removerPorNome(HashTable<Funcionario> tabela, String nome) {
        boolean removido = false;
        // Itera por todos os buckets da tabela
        for (int i = 0; i < tabela.getItems().size(); i++){
            Node<Funcionario> head = tabela.getItems().get(i);
            // Remove ocorrências no início da lista
            while (head != null && head.getValue() != null &&
                   head.getValue().getNome().equalsIgnoreCase(nome)) {
                head = head.getNext();
                removido = true;
            }
            // Atualiza o bucket com o novo head
            tabela.getItems().set(i, head);
            if (head == null) {
                continue;
            }
            // Remove ocorrências a partir do segundo nó
            Node<Funcionario> current = head;
            while (current.getNext() != null) {
                if (current.getNext().getValue() != null &&
                    current.getNext().getValue().getNome().equalsIgnoreCase(nome)) {
                    current.setNext(current.getNext().getNext());
                    removido = true;
                } else {
                    current = current.getNext();
                }
            }
        }
        return removido;
    }
}
