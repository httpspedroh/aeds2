import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// ----------------------------------------------------------------------------------------------------------------- //

class Equipamento {

    private String codigo;
    private String descricao;

    public Equipamento(String codigo, String descricao) {

        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }
}

// ----------------------------------------------------------------------------------------------------------------- //

class Aluguel {

    // Setar DateFormatter
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Cliente cliente;
    private Equipamento equipamento;
    private String dataInicio;
    private String dataTermino;
    private double valorDiario;

    public Aluguel(Cliente cliente, Equipamento equipamento, String dataInicio, String dataTermino, double valorDiario) {

        this.cliente = cliente;
        this.equipamento = equipamento;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.valorDiario = valorDiario;
    }

    public int calcularNumeroDias() {

        LocalDate inicio = LocalDate.parse(dataInicio, df);
        LocalDate termino = LocalDate.parse(dataTermino, df);
        return (int) (termino.toEpochDay() - inicio.toEpochDay()) + 1;
    }

    public LocalDate getDataInicio() { return LocalDate.parse(dataInicio, df); }
    public LocalDate getDataTermino() { return LocalDate.parse(dataTermino, df); }
    public Cliente getCliente() { return cliente; }
    public Equipamento getEquipamento() { return equipamento; }
    public double getValorDiario() { return valorDiario; }
    public boolean isVencido() { return LocalDate.parse(dataTermino, df).isBefore(LocalDate.now()); }

    public void mostrarInfoAluguel() {

        System.out.println("[i] Cliente: " + cliente.getNome());
        System.out.println("[i] Equipamento: " + equipamento.getDescricao() + " (" + equipamento.getCodigo() + ")");
        System.out.println("[i] Período: " + dataInicio + " - " + dataTermino + " (Status: " + (isVencido() ? "Finalizado" : "Ativo") + ")");
        System.out.println("[i] Valor diário: R$" + valorDiario);
        System.out.println("[i] Valor total: R$" + calcularNumeroDias() * valorDiario);
    }
}

// ----------------------------------------------------------------------------------------------------------------- //

class Cliente {

    private String nome;

    public Cliente(String nome) { this.nome = nome; }

    public String getNome() { return nome; }
}

// ----------------------------------------------------------------------------------------------------------------- //

class BD_Manager {

    private List<Aluguel> alugueis;

    public BD_Manager() { alugueis = new ArrayList<>(); }

    public void registrarAluguel(Cliente cliente, Equipamento equipamento, String dataInicio, String dataTermino, double valorDiario) {

        Aluguel aluguel = new Aluguel(cliente, equipamento, dataInicio, dataTermino, valorDiario);
        alugueis.add(aluguel);
    }

    public List<Aluguel> getAlugueis() { return alugueis; }

    public List<Aluguel> getAlugueisPassados(Cliente cliente) {

        List<Aluguel> alugueisPassados = new ArrayList<>();
  
        for (Aluguel aluguel : alugueis) {

            if(aluguel.getCliente() == cliente) {

                if (aluguel.isVencido()) { alugueisPassados.add(aluguel); }
            }
        }
        return alugueisPassados;
    }

    public List<Aluguel> getAlugueisAtivos(Cliente cliente) {

        List<Aluguel> alugueisAtivos = new ArrayList<>();

        for (Aluguel aluguel : alugueis) {

            if(aluguel.getCliente() == cliente) {

                if (!aluguel.isVencido()) { alugueisAtivos.add(aluguel); }
            }
        }
        return alugueisAtivos;
    }

    public double calcularFaturamentoMensal() {

        double faturamento = 0.0;

        for (Aluguel aluguel : alugueis) {

            // Validar se o aluguel não tem data inicial ou final no mês atual
            if (aluguel.getDataInicio().getMonthValue() != LocalDate.now().getMonthValue() && aluguel.getDataTermino().getMonthValue() != LocalDate.now().getMonthValue()) { continue; }

            // Calcular quantos dias o aluguel durou no mês atual
            int diasAluguel = 0;

            if (aluguel.getDataInicio().getMonthValue() == LocalDate.now().getMonthValue() && aluguel.getDataTermino().getMonthValue() == LocalDate.now().getMonthValue()) {

                diasAluguel = aluguel.calcularNumeroDias();
            }
            else if (aluguel.getDataInicio().getMonthValue() == LocalDate.now().getMonthValue()) {

                diasAluguel = aluguel.getDataInicio().lengthOfMonth() - aluguel.getDataInicio().getDayOfMonth() + 1;
            }
            else if (aluguel.getDataTermino().getMonthValue() == LocalDate.now().getMonthValue()) {

                diasAluguel = aluguel.getDataTermino().getDayOfMonth();
            }
            
            // Adicionar valor total do aluguel ao faturamento
            faturamento += diasAluguel * aluguel.getValorDiario();
        }
        return faturamento;
    }
}

// ----------------------------------------------------------------------------------------------------------------- //

public class Main {

    public static void main(String[] args) {

        // Setar DateFormatter
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    
        // ----------------------------------------------------------------------------------------------- //

        // Criar novos equipamentos
        List<Equipamento> equipamentos = new ArrayList<>();

        equipamentos.add(new Equipamento("001", "Betoneira 150L"));
        equipamentos.add(new Equipamento("002", "Andaime 1,5m"));
        equipamentos.add(new Equipamento("003", "Furadeira 650W"));
        equipamentos.add(new Equipamento("004", "Escada 5m"));

        // ----------------------------------------------------------------------------------------------- //

        // Iniciliazar o scanner e o gerenciador de aluguéis
        Scanner scr = new Scanner(System.in);

        BD_Manager bd_alugueis = new BD_Manager();

        // ----------------------------------------------------------------------------------------------- //

        // MENU
        System.out.println("BEM-VINDO À BETONADO ALUGUEL DE EQUIPAMENTOS LTDA.");

        do {
            
            System.out.println("\n#1 - Listar equipamentos disponíveis");
            System.out.println("#2 - Registrar novo aluguel");
            System.out.println("#3 - Mostrar informações de todos os aluguéis");
            System.out.println("#4 - Mostrar informações de alugueis de um cliente");
            System.out.println("#5 - Mostrar faturamento mensal");
            System.out.println("#6 - Sair");

            System.out.print("\nDigite o número da opção desejada: ");

            int opcao = scr.nextInt();

            scr.nextLine(); // Limpa o buffer do teclado

            switch (opcao) {

                // ------------------------------------------------------ //

                case 1:
            
                    // Mostrar equipamentos disponíveis
                    System.out.println("\n> Equipamentos disponíveis:");

                    for (Equipamento equipamento : equipamentos) {

                        System.out.println("Código: " + equipamento.getCodigo() + " | Descrição: " + equipamento.getDescricao());
                    }
                    break;

                // ------------------------------------------------------ //

                case 2:

                    // Pegar nome do cliente
                    System.out.print("\nDigite o nome do cliente: ");
                    String nomeCliente = scr.nextLine();

                    // ------------------------------------------------------ //

                    // Procurar pelo equipamento no banco de dados
                    System.out.print("Digite o código do equipamento: ");
                    String codigoEquipamento = scr.nextLine();

                    Equipamento equipamento = null;

                    for (Equipamento eq_obj : equipamentos) {

                        if (eq_obj.getCodigo().equals(codigoEquipamento)) { equipamento = eq_obj; break; }
                    }

                    // Validar equipamento
                    if (equipamento == null) {

                        System.out.println("x Equipamento não encontrado. Por favor, tente novamente.");
                        break;
                    }

                    // ------------------------------------------------------ //
                    
                    // Data de início e término do aluguel
                    System.out.print("Digite a data de início do aluguel (DD/MM/AAAA): ");
                    String dataInicio = scr.nextLine();

                    System.out.print("Digite a data de término do aluguel (DD/MM/AAAA): ");
                    String dataTermino = scr.nextLine();

                    // Validar datas
                    if(LocalDate.parse(dataInicio, df) == null || LocalDate.parse(dataTermino, df) == null) {

                        System.out.println("x Data inválida. Por favor, tente novamente.");
                        break;
                    }
                    else if(LocalDate.parse(dataInicio, df).isAfter(LocalDate.parse(dataTermino, df))) {

                        System.out.println("x Data de início maior que data de término. Por favor, tente novamente.");
                        break;
                    }

                    // ------------------------------------------------------ //
                    
                    // Valor diário do aluguel
                    System.out.print("Digite o valor diário do aluguel: ");
                    double valorDiario = scr.nextDouble();

                    // Validar valor diário
                    if (valorDiario <= 0) {

                        System.out.println("x Valor inválido. Por favor, tente novamente.");
                        break;
                    }
                    else if (valorDiario > 1000) {

                        System.out.println("x Valor diário muito alto. Por favor, tente novamente.");
                        break;
                    }

                    // ------------------------------------------------------ //
                    
                    // Registrar aluguel
                    Cliente cliente = new Cliente(nomeCliente);

                    bd_alugueis.registrarAluguel(cliente, equipamento, dataInicio, dataTermino, valorDiario);

                    System.out.println("> Aluguel registrado com sucesso!");
                    break;

                case 3:

                    // Mostrar informações de todos os aluguéis
                    System.out.println("\n> Informações de todos os aluguéis:");

                    if(bd_alugueis.getAlugueis().size() == 0) {

                        System.out.println("x Nenhum aluguel registrado.");
                        break;
                    }

                    for (Aluguel aluguel : bd_alugueis.getAlugueis()) {

                        aluguel.mostrarInfoAluguel();
                        System.out.println();
                    }
                    break;

                case 4:

                    // Mostrar informações de alugueis de um cliente
                    System.out.print("\nDigite o nome (EXATO) do cliente: ");
                    String nomeCliente_busca = scr.nextLine();

                    Cliente cliente_busca = null;

                    for (Aluguel aluguel : bd_alugueis.getAlugueis()) {

                        if (aluguel.getCliente().getNome().equals(nomeCliente_busca)) cliente_busca = aluguel.getCliente(); break;
                    }

                    // Validar cliente
                    if (cliente_busca == null) {

                        System.out.println("x Cliente não encontrado. Por favor, tente novamente.");
                        break;
                    }

                    // Mostrar informações do cliente
                    System.out.println("\n> (1) Aluguéis ativos\n> (2) Aluguéis passados");
                    System.out.print("\nDigite o número da opção desejada: ");

                    int opcao_cliente = scr.nextInt();

                    scr.nextLine(); // Limpa o buffer do teclado

                    switch (opcao_cliente) {

                        case 1:

                            System.out.println("\n> [CLIENTE: " + cliente_busca.getNome() + "]");

                            // Mostrar aluguéis ativos
                            System.out.println("\n> Aluguéis ativos:");

                            List<Aluguel> alugueisAtivos = bd_alugueis.getAlugueisAtivos(cliente_busca);

                            if(alugueisAtivos.size() == 0) {

                                System.out.println("x Nenhum aluguel ativo.");
                                break;
                            }

                            for (Aluguel aluguel : alugueisAtivos) {

                                aluguel.mostrarInfoAluguel();
                                System.out.println();
                            }
                            break;

                        case 2:

                            System.out.println("\n> [CLIENTE: " + cliente_busca.getNome() + "]");

                            // Mostrar aluguéis passados
                            System.out.println("\n> Aluguéis passados:");

                            List<Aluguel> alugueisPassados = bd_alugueis.getAlugueisPassados(cliente_busca);

                            if(alugueisPassados.size() == 0) {

                                System.out.println("x Nenhum aluguel passado.");
                                break;
                            }

                            for (Aluguel aluguel : alugueisPassados) {

                                aluguel.mostrarInfoAluguel();
                                System.out.println();
                            }
                            break;

                        default: System.out.println("x Opção inválida. Por favor, escolha uma opção válida.");
                    }
                    break;

                case 5:

                    // Mostrar faturamento mensal no formato R$XXXX,XX
                    System.out.println("\n> Faturamento mensal: R$" + String.format("%.2f", bd_alugueis.calcularFaturamentoMensal()));
                    break;

                case 6:

                    System.out.println("> Saindo do programa. Obrigado!");

                    scr.close(); // Fecha o scanner

                    // Fechar o programa
                    System.exit(0);

                default: System.out.println("x Opção inválida. Por favor, escolha uma opção válida.");
            }
        } 
        while (true);

        // ----------------------------------------------------------------------------------------------- //
    }
}
