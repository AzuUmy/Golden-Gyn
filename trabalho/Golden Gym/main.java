import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sound.midi.MidiDevice.Info;

import com.golden.gyn.Aluno.Alunos;
import com.golden.gyn.Files.Files;
import com.golden.gyn.Treinos.Treino;
import com.golden.gyn.equipameentos.Equipamento;
import com.golden.gyn.instrutor.Instrutor;
import com.golden.gyn.reservas.Reservas;

import java.util.Scanner;
import java.util.Set;



public class main {
    public static void main(String[] arg) throws IOException{
        //invocacoes da classes com contrutores default para serem passados aos metodos chamados no menu
        Alunos aluno = new Alunos();
        Files file = new Files();
        Treino treino = new Treino();
        Instrutor instrutor = new Instrutor();
        Equipamento equipamento = new Equipamento();
       // Equipamento equipament = new Equipamento();
        
        //Populando a array alunos map com os dados do arquivo atraves do metodo na classe file
        file.readAndPopulateAlunoMap("AlunosFile.txt", alunoMap);
        file.lerEquipamentoFile(equipamentos);
        file.readAndPopulateInstructor(instrutorMap);
        System.out.println(equipamentos);

        //menu basico de opcoes
        Scanner scanner= new Scanner(System.in);
        for(int i = 0; i< 100; i++){
            
            System.out.println("1: cadastrar Aluno");
            System.out.println("2: Listar Aluno");
            System.out.println("3: Remover Aluno");
            System.out.println("4: Editar Aluno");
            System.out.println("5: Criar plano de Treino");
            System.out.println("6: Cadastrar Instrutor");
            System.out.println("7: Reservar Equipamento");
            System.out.println("8: Remover reserva do Equipamento");
            System.out.println("9: Listar Equipamento e Reservas");
            System.out.println("10: Editar Reserva de Equipamento");
            System.out.println("11: Reservar Treino");
            System.out.println("12: Listar planos de Treino");
       
            int menu = scanner.nextInt();

            switch (menu) {
                case 1:
                      cadastrarAluno(aluno);
                    break;

                    case 2:
                    listarAlunos();
                    break;

                    case 3:
                    removerAlunos(aluno, file);

                    break;

                    case 4:
                    editarAlunos(aluno, file);
                    break;

                    case 5:
                    criarPlanoDeTreino(file, treino, aluno);
                    break;

                    case 6:
                    CadastrarInstrutor(instrutor);
                    break;

                    case 7:
                    reservarEquipamento(file, aluno, equipamento);
                    break;

                    case 8:
                    removerReservEquipamento(file, equipamento, aluno);
                    break;

                    case 9:
                    listarEquipamento();
                    break;

                    case 10:
                    EditarReservaEquipamento(file, equipamento, aluno);
                    break;

                    case 11:
                    reservarTreino(file, treino, equipamento); 
                    break;

                    case 12:
                    listarPlanosDeTreino(file);
                    break;

                    case 13:
                    listarReservas(file);
                    break;
            
                default:
                    break;
            }
            
        }
       
    }



// hashMap de alunos
   static Map<UUID, Alunos> alunoMap = new HashMap<>();
   static Map<UUID, Instrutor> instrutorMap = new HashMap<>();
   static ArrayList<Equipamento> equipamentos = new ArrayList<>();
   static ArrayList<Treino> planoTreino = new ArrayList<>();

  /* Metodod para cadastrar novos alunos nesse metodo eu coleto as infromacoes passo elas para 
    meu hashmap de alunos e depois impurro esse hashmap para o meu metodo file the realiza o 
    cadastro desses alunos no arquivo
*/
    public static void cadastrarAluno(Alunos aluno) throws IOException{
        Files file = new Files();;

        Scanner scanner= new Scanner(System.in);
        
        System.out.println("Informe o nome do Aluno");
        String nome = scanner.nextLine();

        System.out.println("Informe o peso do Aluno");
        double peso = scanner.nextDouble();

        System.out.println("Informe a altura do Aluno");
        double altura = scanner.nextDouble();



        alunoMap.put(UUID.randomUUID(), new Alunos(nome, peso, altura));

        file.writeAlunoIntoFile(alunoMap);


        System.out.println("Deseja Calcular o Imc 1: Sim 2: Nao ");
        int escolha = scanner.nextInt(); 

        if(escolha == 1){
            aluno.calculoDeImc(peso, altura);
        }
        
    }
    

    // metodo basico para listar alunos com base no array de alunos sera alterfado para conter metodos mais complexos
    public static void listarAlunos(){
        Scanner scanner= new Scanner(System.in);
        System.out.println("selecione a opcao que deseja listar");
        System.out.println("1: Buscar aluno especifico");
        System.out.println("2: buscar todos os alunos");
        int op = scanner.nextInt();

        scanner.nextLine();

        switch (op) {
            case 1:
            System.out.println("Insira o nome do aluno");
            String alunoName = scanner.nextLine();

            Alunos singleAluno = null;
    
            for (Map.Entry<UUID, Alunos> entry : alunoMap.entrySet()) {
                if(entry.getValue().getNome().equals(alunoName)){
                 
                    singleAluno = entry.getValue();
    
                        if (singleAluno != null) {
                            System.out.println("Nome: " + entry.getValue().getNome() + 
                            " " + "Peso: " + entry.getValue().getPeso() + 
                            " " + "Altura: " + entry.getValue().getAltura());
                        }
    
                    }
                }
                break;

                case 2:
                  for (Map.Entry<UUID, Alunos> entry : alunoMap.entrySet()) {
                         System.out.println("Id: " + entry.getKey() + " "
                            + "Nome: " +entry.getValue().getNome() + " " 
                            + "Peso: " + entry.getValue().getPeso() + " " 
                            + "Altura: " + entry.getValue().getAltura());
                     }
                break;
        
            default:
                break;
        }
     
    }

    /*Metodo para editar o aluno esse metodo pede o id do aluno que deseja e editar
        e o peso salva esses dados em variavesi dfiferentes e depois empura eles para o 
        metodo de edxitar meus alunos
     */
    public static void editarAlunos(Alunos aluno, Files file){
        Scanner scanner= new Scanner(System.in);
        listarAlunos();

        System.out.println("Selecione o aluno pelo Id");
        String alunoId = scanner.nextLine();

        System.out.println("Altere o peso do Aluno");
        Double peso = scanner.nextDouble();


        
        file.editAlunos(alunoId, alunoMap, peso);

    }


    /* Metodo para remover os alunos esse metodo  pede o id do aluno que deseja remover
        coleta esse id em uma variavel e depois chama meu metodod do arquivo file para
        remover ele do file
     */
    public static void removerAlunos(Alunos aluno, Files file){
        Scanner scanner= new Scanner(System.in);
            listarAlunos();

            System.out.println("Selecione o aluno que deseja remover");
            String alunoId = scanner.nextLine();

            
            file.removeAlunoById(alunoId, alunoMap);
            
    }



    public static Void CadastrarInstrutor(Instrutor instrutor) throws IOException{
        Files file = new Files();

        Scanner scaneer = new Scanner(System.in);
        System.out.println("Informe o nome do Instrutor");
        String isntructorName = scaneer.nextLine();

        System.out.println("Area de atuacao");
        String instgructorField = scaneer.nextLine();

        System.out.println("Horario de inicio");
        String horarioIn = scaneer.nextLine();

        System.out.println("Horario de Fim");
        String horarioOut = scaneer.nextLine();
        instrutorMap.put(UUID.randomUUID(), new Instrutor(isntructorName, instgructorField, horarioIn, horarioOut));

        file.creatInstrutor(instrutorMap);

        return null;
    }

    
    public static void reservarEquipamento(Files file, Alunos aluno, Equipamento equipamento) throws IOException{
        Scanner scaneer = new Scanner(System.in);

        boolean reservaEquipamento = false;

        while (reservaEquipamento != true) {
            file.listarEquipament(equipamentos);
            System.out.println("Selecione o equipamento");
            int index = scaneer.nextInt();
           
           if(equipamentos.get(index).getReserva().contains("Disponivel")){
            reservaEquipamento = true;
                scaneer.nextLine();
                file.readAndPrintAluno("AlunosFile.txt", alunoMap);
                System.out.println("Selecione o aluno");
                String alunoId = scaneer.nextLine();
    
                System.out.println("Informe o horario");
                String horarioReserva = scaneer.nextLine();
    
                Alunos alunoName = file.findAlunoById(alunoMap, alunoId);
    
            
                file.editEquipamentsList(index, alunoName, horarioReserva, equipamentos);
                System.out.println("Equipamento reservado com sucesso");
           }else{
                reservaEquipamento = false;
              System.out.println("Equipamento ja reservado em nome de: " + equipamentos.get(index).getReserva());
           }
        }
    }


    public static void removerReservEquipamento(Files file, Equipamento equipamento, Alunos aluno) throws IOException{
        Scanner scanner = new Scanner(System.in);

        file.listarEquipament(equipamentos);
        System.out.println("Selecione um equipamento");
        int index = scanner.nextInt();
        
       // Equipamento selectedEqui = equipamentos.get(index);
        if(equipamentos.get(index).getReserva() != "Disponive;") {
             Alunos reservation = new Alunos("Disponivel");
            String horarioReserva = "00:00";
            file.editEquipamentsList(index, reservation, horarioReserva, equipamentos);
        }
    }

    public static void EditarReservaEquipamento(Files file, Equipamento equipamento, Alunos aluno)  throws IOException{
        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe o nome do Aluno");
        String alunosName = scanner.nextLine();
        List<Integer> filteredIndexes = new ArrayList<>();
         // Display only the equipment reserved by the input name and collect their original indexes
            for (int i = 0; i < equipamentos.size(); i++) {
                Equipamento equi = equipamentos.get(i);
                if (equi.getReserva().contains(alunosName)) {
                    System.out.println(i + " - Equipamento: " + equi.getEquipamento() + 
                                    ", Reserva: " + equi.getReserva() + 
                                    ", Horário: " + equi.getHorario());
                    filteredIndexes.add(i); // Add the original index to the list
                }
            }

            if(filteredIndexes.isEmpty()){
                System.out.println("Nenhum equipamento reservado para: " + alunosName);
                return;
            }





        System.out.println("Selecione o equipamento");
        int index = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Informe o horario para edicao da reserva pelo numero listado");
        String novoHorario = scanner.nextLine();

        Alunos reservation = new Alunos(alunosName);

        file.editEquipamentsList(index, reservation, novoHorario, equipamentos);
        System.out.println("Reserva Efetuada com sucesso");
    }


    public static void listarEquipamento(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("1: Listar equipamentos reservados");
        System.out.println("2 Listar todos os equipamentos");
        int op = scanner.nextInt();

        scanner.nextLine();

        switch (op) {
            case 1:
            System.out.println("Informe o nome do Aluno");
            String nome = scanner.nextLine();
    
            for (Equipamento equip : equipamentos) {
                if(equip.getReserva().contains(nome)){
                    System.out.println("Equipamento :" + equip.getEquipamento() + " "
                    + "Reserva: " + equip.getReserva());
                }
            }
                break;

                case 2:
            for (Equipamento equi2 : equipamentos) {
                System.out.println("Equipamento :" + equi2.getEquipamento() + " "
                    + "Reserva: " + equi2.getReserva());
            }
                break;
        
            default:
                break;
        }

       

    }

    //declaracao da array que lista os alunos esse array esta sendo prenchido por um metodo na classe file
   static ArrayList<Treino> listarTreino = new ArrayList<>();
    //Array responsavel por crair um novo treino empurro as informacoes para esse array e depos empurro ele para a classe file
   static ArrayList<Treino> criarTreino = new ArrayList<>();


   /*Metodo reponmsavel por criar nova listas detreino esse metodo solicita 
   ao usuario a as informacoes da classe treino  ele possui tambem a chamada de metodos
   para listar os treinos disponiveis e o aluno no qual eu desejo vincular esse treino
   todos eles vem de um metodo na classe file criado para listar mes alunos ou seja nao soa listados 
   pela array alunosMap mais por uma metodod diferente na classe file. */

    public static void criarPlanoDeTreino(Files file, Treino treino, Alunos aluno) throws IOException{
            Scanner scanner= new Scanner(System.in);

            file.readAndPrintAluno("AlunosFile.txt", alunoMap);
            System.out.println("Selecione o aluno pelo ID");
            String idAluno = scanner.nextLine();

            Alunos alunoName = file.findAlunoById(alunoMap, idAluno);

            System.out.println("Selecione os dias");
            int dia = scanner.nextInt();
            String formatWeak = null;
            switch (dia) {
                case 1:
                    formatWeak = "Segunda";
                    break;
                case 2:
                    formatWeak = "Terca";
                    break;
                case 3:
                    formatWeak = "Quarta";
                    break;

                    case 4:
                    formatWeak = "Quinta";
                    break;

                    case 5:
                    formatWeak = "Sexta";
                    break;
            
                default:
                    break;
            }


           
            System.out.println("1: Peito");
            System.out.println("2: Costas");
            System.out.println("3: Pernas");
            System.out.println("4: Biceps");
            System.out.println("5: Triceps");

            int op = scanner.nextInt();
            
            switch (op) {
                    case 1:
                    /*chamada dos metodos de file para popular o arrayde treino com os dados do arquivo */
                        file.readAndPopulateWork("Peito.txt", listarTreino);
                        /* chamada poara criar um novo treino chamando o metodo da classe file */
                        file.creatWorkoutPlan("Peito.txt", treino, listarTreino);
                        /* chamada do metodo que cria o arquivo que cria o arquivo contendo os treinos 
                         * selecionados*/
                        file.creatWorkoutFile(idAluno, alunoName,  criarTreino);
                    break;

                    case 2:
                       file.readAndPopulateWork("Costas.txt", listarTreino);
                       file.creatWorkoutPlan("Costas.txt", treino, listarTreino);
                       file.creatWorkoutFile(idAluno, alunoName, criarTreino);
                    break;
            
                default:
                    break;
            }

            scanner.nextLine();
            System.out.println("Selecione o Exercicio desejados (Numeros Separados por Espaco)");
            String index = scanner.nextLine();
            String[] exercicesArray = index.split(" ");

            /* codigo reponsael por popular o array list novo treino com a posicao do arraylist listar treino
             * esse codigo recolhe nome do treino do array listar treino a quantidade maxima a minima e 
             * passa essas infromacoes para o a classe treino e ai preenche a array criar treino com essas
             * informacoes, entao essa informacoes sao passadas para o metodo creatWorkoutFile que escre elas em 
             * um arquivo
             */

            //Set<String> processedDays = new HashSet<>();

             for (String indexStr : exercicesArray) {
               
                if(!indexStr.isEmpty()){
                     int exer = Integer.parseInt(indexStr);
                     if(exer >= 0 && exer < listarTreino.size()){
                        Treino  selectedExercise = listarTreino.get(exer);
                        String exerciseString = selectedExercise.getExerciceAsString();
                        treino.setExercicio(exerciseString);
        
                        System.out.println("Seleciona a quantidade maxima de minimas");
                        int repIni = scanner.nextInt();
             
                        System.out.println("Selecione a quantidade de repeticoes maximas");
                        int repOut = scanner.nextInt();
             
             
                        treino.setRepeticoes(repIni, repOut);
        
                        Treino newTreino = new Treino(exerciseString, repIni, repOut, formatWeak);
        
                        criarTreino.add(newTreino);
        
                    }else{
                        System.out.println("Index out of Boundes");
                    }
                 }
             }

             file.creatWorkoutFile(idAluno, alunoName, criarTreino);
      }


      /*metodo para criacao de reserva de treino esse metodo coleta infromacoes como o aluno treino o horartio e a semana para q ele 
      possa um encontrar o arquivo de equipamentos reservados e de plano de treino e para que possa copiar os dados referentes ao aluno com o id unico passado */

      public static void reservarTreino(Files file,Treino treino, Equipamento equipamento ) throws IOException{
        Scanner scanner = new Scanner(System.in);

        file.readAndPrintAluno("AlunosFile.txt", alunoMap);
        System.out.println("Selecione o Aluno pelo Id");
        String idAluno = scanner.nextLine();
    
        file.readAndPrintInstructor(instrutorMap);
        System.out.println("Selecione o Instrutor pelo Id");
        String IdInstrutor = scanner.nextLine();
    
        System.out.println("Selecione o Horario de inicio");
        String HorarioIni = scanner.nextLine();
    
        System.out.println("Selecione o Horario de fim");
        String HorarioOut = scanner.nextLine();


        System.out.println("Escreva o dia da semana");
        String dia = scanner.nextLine();
    
        Instrutor instructorName = instrutorMap.get(UUID.fromString(IdInstrutor));

        List <Equipamento> equipReserved = new ArrayList<>();
        List <Treino> planoDeTtreino = new ArrayList<>(); 
        file.readWorkotuPlanofAlunoById(idAluno, file, alunoMap, null, planoDeTtreino);
    
        
        if (instructorName != null) {
            
            String originalStart = instructorName.getHorarioInicio();
            String originalEnd = instructorName.getHorarioFim();
    

            instructorName.setHorarioInicio(HorarioIni);
            instructorName.setHorarioFim(HorarioOut);
    
            Alunos alunoId = file.findAlunoById(alunoMap, idAluno);

            List<Treino> addTreino = new ArrayList<>();

          for (Equipamento equip : equipamentos) {
                if(equip.getReserva().contains(alunoId.getNome())){
                    equipReserved.add(equip);
                }
          }

       
         
        for (Treino treino2 : planoDeTtreino) {
               if(treino2.getData().contains(dia)){
                addTreino.add(treino2);
               }
            
               
          }

           file.createWorkoutReserv(alunoId, instructorName, alunoMap, instrutorMap, equipamentos, addTreino, dia);
    
            instructorName.setHorarioInicio(originalStart);
            instructorName.setHorarioFim(originalEnd);
        } else {
            System.out.println("Instrutor não encontrado.");
        }

      }


      //metodo para litsar os planos de treino
      public static void listarPlanosDeTreino(Files file) throws IOException{
        Scanner scanner = new Scanner(System.in);
        file.readAndPrintAluno("AlunosFile.txt", alunoMap);
        System.out.println("Sleecione o Id do Aluno");
        String idAluno = scanner.nextLine();

        file.readAndPopulateTrainingPlan(idAluno, file, alunoMap, null, planoTreino);


        System.out.println("1: Segunda");
        System.out.println("2: Terca");
        System.out.println("3: Quarta");
        System.out.println("4: Quinta");
        System.out.println("5: Sexta");
        System.out.println("Selecione o dia Desejado");  
        int op = scanner.nextInt();
        String dia = null;

        switch (op) {
            case 1:
             dia = "Segunda";
                break;

            case 2:
            dia = "Terca";
                break;
                
            case 3:
            dia = "Quarta";
                break;

            case 4:
            dia = "Quinta";
                break;

            case 5:
            dia = "Sexta";
                break;
        
            default:
                break;
        }
          

      Boolean entryFound = false;

        for (Treino treino : planoTreino) {
            if(treino.getData().contains(dia)){
            System.out.println("Dia: " + treino.getData() + " " + "Exercicio: " + treino.getExercicio() + " "
            + "Rpeticoes: " + treino.getRepeticoesIn() + " a " + treino.getRepeticoesOut() );
            entryFound = true;
            }
        }

        if(!entryFound){
            System.out.println("Nenhuma treino encontrado para o aluno no dia informado");
        }

      }



      //Metodo para listar as reservas tanto todas as reservas como com base nos alunos
      public static void listarReservas(Files file) throws IOException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Reservas> rerserves = new ArrayList<>();
        file.listReservations(null, null, alunoMap, instrutorMap, equipamentos, null, null, rerserves);


        System.out.println("1: listar por aluno");
        System.out.println("2: istart todos");
        int op = scanner.nextInt();
        scanner.nextLine();


        switch (op) {
            case 1:
            file.readAndPrintAluno("AlunosFile.txt", alunoMap);
            System.out.println("Informe o nome do aluno");
            String nome = scanner.nextLine();
            for (Reservas reservas : rerserves) {
                if(reservas.getAluno().contains(nome)){
                    System.out.println("Aluno: " + reservas.getAluno() + " "
                    + "Instrutor:  " + reservas.getIntrutor()  + " " 
                    + "Dia: " + reservas.getDia() + " " 
                    + "Horario: " + reservas.getHorario() + " " 
                    + "Equipamento-Reservado: " + reservas.getEquipament() + " "
                    + "Treino: " + reservas.getTreino());  
                    }
            }    
            break;


            case 2:
            for (Reservas reservas : rerserves) {
                    System.out.println("Aluno: " + reservas.getAluno() + " "
                    + "Instrutor:  " + reservas.getIntrutor()  + " " 
                    + "Dia: " + reservas.getDia() + " " 
                    + "Horario: " + reservas.getHorario() + " " 
                    + "Equipamento-Reservado: " + reservas.getEquipament() + " "
                    + "Treino: " + reservas.getTreino());  
            } 
            break;


            default:
                break;
        }
      
    
      
      
        

    }

}