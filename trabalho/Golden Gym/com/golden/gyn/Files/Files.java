package com.golden.gyn.Files;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import com.golden.gyn.Aluno.Alunos;
import com.golden.gyn.Treinos.Treino;
import com.golden.gyn.equipameentos.Equipamento;
import com.golden.gyn.instrutor.Instrutor;
import com.golden.gyn.reservas.Reservas;

public class Files {


/*metodo reponsavel poer cadastrar novos alunos no arquivo file ele receb como parametro
 * o hashMap de alunos e o metodo cria tambem a pasta alunos e o arquivo alunos caso nao exista e entao
 * escreve os alunos atraves do hashamp usando um foreach
 */

    public void writeAlunoIntoFile(Map<UUID, Alunos> alunoMap) throws IOException{

        File dir = new File("C:\\Users\\carlo\\Desktop\\Goldem Gym");

        if(!dir.exists());
           dir.mkdir();

           File newDir = new File(dir, "Alunos");
           if(!newDir.exists()){
               boolean created = newDir.mkdir();

               if(created) {
                   System.out.println("Folder Created successfully");
               }else{
                   System.out.println("Failled to Create");
               }
           }else{
               System.out.println("Dirrectorry already Exists");
           }
          
       

           File alunoFile = new File(newDir, "AlunosFile.txt");
           if(!alunoFile.exists()) {
               boolean FileCreated = alunoFile.createNewFile();

               if(FileCreated){
                   System.out.println("File Alunos Created Successfully");
               }else{
                   System.out.println("Failled to Creat Alunos Fille");
               }

           }else{
               System.out.println("File Already Exists: " + alunoFile.getAbsolutePath());
           }

            FileWriter file = new FileWriter(alunoFile, true);
            PrintWriter write = new PrintWriter(file);

            /* forEach para reistrar no aqruivo os alunos com base na estrutura da classe Alunos */
           for (Map.Entry<UUID, Alunos> entry : alunoMap.entrySet()) {
            write.println("Id: " + entry.getKey() + " "
             + "Nome: " +entry.getValue().getNome() + " " 
             + "Peso: " + entry.getValue().getPeso() + " " 
             + "Altura: " + entry.getValue().getAltura());
             System.out.println("Sucesfully in Wrinting Data");
        }

           write.flush();
           write.close();

   }


   /* metodo responsavel por listar os treinos ele le o arquivo de exercicios e lista eles atraves
    * do metodo readAndPrintWorkou pois cada exercicico possui seu proprio arquivo separadamente nao 
        sendo uma lista unica de exercicios
    */

  public void creatWorkoutPlan( String FileName, Treino treino, ArrayList<Treino> listarTreino) throws IOException{
    File dir = new File("C:\\Users\\carlo\\Desktop\\Goldem Gym\\Treinos");
    if(!dir.exists()){
        System.out.println("File directory Not Founded");
    }else{
        System.out.println("File Directory Founded");
        System.out.println();
    }


    File[] files = dir.listFiles();

    boolean targetFileFounded = false;

    for(File file : files){
        if(file.isFile() && file.getName(). equals(FileName)) {
            targetFileFounded = true;
            //metodo reponsavel por ler e imprimir o treino baseado no arquivo, por isso passamos o fileName
            readAndPrintWork(FileName, listarTreino);
            break;
        }
    }

    if(!targetFileFounded){
        System.out.println("File" + " " + FileName +  " " + "not Founded");
    }

  }

/* esse metodo e o responsvael por criar o aqruivo de treinos, esse metodo cria arquivos individuais para cada
 * um dos Alunos por isso ele recebeo nome e o id desses alunos para que seja possivel criar esses arquivos
 * separadamente, para criar foi passado os paramentros do tipo string id do Alunos, esse id e passado 
 * no metodo criarPalno de treino, foi passado tambem o parametros da classe aluno para coletar o nome do aluno
 */
  public void creatWorkoutFile(String idAluno, Alunos aluno, ArrayList<Treino> criarTreino) throws IOException{
    File newWorkOutDir = new File("C:\\Users\\carlo\\Desktop\\Goldem Gym");


    if(!newWorkOutDir.exists());
    newWorkOutDir.mkdirs();

    File newWorkOutFolder = new File(newWorkOutDir, "Tabel-Treinos");
    if(!newWorkOutFolder.exists()){
        boolean created = newWorkOutFolder.mkdirs();

        if(created) {
            System.out.println("Folder Created Sucessfully");
        }else{
            System.out.println("Failed to Created Folder");
        }
    }else {
        System.out.println("Folder already exists");
    }


    //Cria arquivos com o nome do alunos cada aluno tem seu proprio arquivo nao havendo duplicas
    File workOutPlan = new File(newWorkOutFolder,  aluno.getNome() + "-" + idAluno + "-Tabela.txt");
    if(!workOutPlan.exists()){
        boolean treinoFileCreated = workOutPlan.createNewFile();

        if(treinoFileCreated){
            System.out.println("Workout File Created");
        }else{
            System.out.println("Failled to Create WorkoutFile");
        }
    }else{
        System.out.println("Workout File  Already Exists");
    }



    /* parte do codigo reponsvael por escrever os exercicios no arquivo utilizando a classe treino */
    FileWriter file = new FileWriter(workOutPlan, true);
    PrintWriter write = new PrintWriter(file);

    Set<String> processedDays = new HashSet<>();

    for (Treino t : criarTreino) {
        String currentDay = t.getData();

        if(!processedDays.contains(currentDay)){
            processedDays.add(currentDay);
            write.println("Dia: " + t.getData());
        }
       
        write.println("Exercico: " + t.getExercicio());
        write.println("Repeticoes: " + t.getRepeticoesIn() + " " + "a" + " " + t.getRepeticoesOut() + " ");
        System.out.println("Write Sucesssfully");
    }

    write.println();

    write.flush();
    write.close();


  }


  /* metodo para ler um arquivo do tipo Alunos e popular o hashmapo de alunos com essa informacoes */
public void readAndPopulateAlunoMap(String fileName, Map<UUID, Alunos> alunosMap) throws IOException{
    File alunosDir = new File("C:\\Users\\carlo\\Desktop\\Goldem Gym\\Alunos");
    File[] alunosFiles = alunosDir.listFiles();

    for(File file : alunosFiles){
        if(file.isFile() && file.getName().equals(fileName)){
            try(BufferedReader reader = new BufferedReader(new FileReader(file))){
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("Id:") && line.contains("Nome:")) {
                        String id = line.substring(line.indexOf("Id:") + 3, line.indexOf(" Nome:")).trim();
                        String nome = line.substring(line.indexOf("Nome:") + 5, line.indexOf(" Peso:")).trim();
                        double peso = Double.parseDouble(line.substring(line.indexOf("Peso:") + 5, line.indexOf(" Altura:")).trim());
                        double altura = Double.parseDouble(line.substring(line.indexOf("Altura:") + 7).trim());
                        alunosMap.put(UUID.fromString(id), new Alunos(nome, peso, altura));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }
    }
}

// Metodo para ler o arquivo treino e popular o arrayList de listar treinos com estes treinos
public void readAndPopulateWork(String FileName, ArrayList<Treino> listarTreino){
   File workOutDir = new File("C:\\Users\\carlo\\Desktop\\Goldem Gym\\Treinos");
   File workOutFile = new File(workOutDir, FileName);

    if(workOutFile.exists() && workOutFile.isFile()){
        System.out.println("Workout Fle Founded: " + workOutFile.getAbsolutePath());
    } else{
    System.out.println("WorkoutFile not Founded");
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(workOutFile))) {
        String exercicioLine;
        while ((exercicioLine = reader.readLine()) != null) {
            if (exercicioLine.startsWith("Exercicio:")) {
                String repetitionsLine = reader.readLine(); 
                if (repetitionsLine != null && repetitionsLine.startsWith("repeticoes:")) {
                    String Exercicio = exercicioLine.substring("Exercicio:".length()).trim();
                    String repeticoes = repetitionsLine.substring("repeticoes:".length())

                    .trim().replace(";", ""); 
    
                 
                    String[] repetitionsRange = repeticoes.split("a");
                    if (repetitionsRange.length == 2) {
                        int startRepetitions = Integer.parseInt(repetitionsRange[0].trim());
                        int endRepetitions = Integer.parseInt(repetitionsRange[1].trim());
                      
                        Treino treino = new Treino(Exercicio, startRepetitions, endRepetitions, repeticoes);
                        listarTreino.add(treino);
                    } 
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Error Reading File: " + e.getMessage());
    }
    
}

    // metodo para ler e printar os alunos da classe file
    public void readAndPrintAluno(String fileName, Map<UUID, Alunos> alunosMap) throws IOException{
    
      File alunosDir = new File("C:\\Users\\carlo\\Desktop\\Goldem Gym\\Alunos");

      File[] alunosFile = alunosDir.listFiles();
        
      for(File file : alunosFile){
        if(file.isFile() && file.getName().equals(fileName)){
           System.out.println("Reading File: " + file.getName());

           try(BufferedReader reader = new BufferedReader(new FileReader(file))){

          for (Map.Entry<UUID, Alunos> entry : alunosMap.entrySet()) {
               System.out.println("Id: " + entry.getKey() + " " + "Nome: " + entry.getValue().getNome());
          }
                

            }catch (IOException e){
                    System.out.println("Erro reading File: " + e.getMessage());
            }

        }else {
            System.out.println("File: " + file.getName() + " Not Founded");
        }

      }
        
    }

    // metodo para coletar um aluno baseado em seu id 
    public Alunos findAlunoById(Map<UUID, Alunos> alunosMap, String idAluno){
        UUID alunoId = UUID.fromString(idAluno);

        System.out.println("Searching for aluno Id: " + alunoId);


        if(alunosMap.isEmpty()){
            System.out.println("Alunos map is empty");
            return null;
        }


        for(Map.Entry<UUID, Alunos> entry : alunosMap.entrySet()){
            if(entry.getKey().equals(alunoId)){
                System.out.println("Aluno: " + entry.getValue().getNome() + " Founded");
                return entry.getValue();
            }
        }


        return null;
    }

/*
    metodo para remover um aluno baseado em seu id do arquivo alunos esse metodo primeiramente remove
    do hashMap entao passa a variavel que esta recebendo essa opca ode remover para o metodo que realizada
    a remocao da linha correpondete ao id do arquivo
 */  
    public Alunos removeAlunoById(String AlunoId, Map<UUID, Alunos> alunosMap){
         UUID alunoId = UUID.fromString(AlunoId);
         if(alunosMap.isEmpty()){
            System.out.println("Alunos Map Is Empty");
            return null;
         }

         Alunos removedAlunos = null;

         for(Map.Entry<UUID, Alunos> entry : alunosMap.entrySet()){
            if(entry.getKey().equals(alunoId)){
                removedAlunos = alunosMap.remove(alunoId);
                if(removedAlunos != null){
                    // metodo para remover a linha correpondete ao id
                     removeLineFromFile(AlunoId, "C:\\Users\\carlo\\Desktop\\Goldem Gym\\Alunos\\AlunosFile.txt");
                break;
                } 
            }
         }

        return null;
    }

/* Metodod para exluir uma linha correpondete ao id passado no metodo acima esse metodo cria uma novo 
    arquivo chama storage esse arquivo recebe os dados do arquivo original menos a linha correpondete ao id
    que sera removido esse arquivo entao e renomeado para o arquivo original e o arquivo origina e deletado
 */
    public void removeLineFromFile(String lineToRemove, String filePath) {
        File foulder = new File("C:\\Users\\carlo\\Desktop\\Goldem Gym\\Alunos");
        File file = new File(filePath);
        File storage = new File(foulder, "storage.txt");

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
             try (BufferedWriter writer = new BufferedWriter(new FileWriter(storage))) {
                    String curretLine;

                   while ((curretLine = reader.readLine()) != null) {
                        if(curretLine.contains(lineToRemove)){
                           
                            continue;
                        }

                       writer.write(curretLine);
                       writer.newLine();
                    }

                    writer.close();
                    reader.close();


                   if(!file.delete()){
                        System.out.println("could Not Delet the file");
                    }

                    if(!storage.renameTo(file)){
                        System.out.println("Could not rename to storage");
                    }
                }

            } catch (FileNotFoundException e){
                System.out.println("File Not Founded");
            }

        }catch( IOException e){
            System.out.println("Erro Readin/Writing File");
        }
    }

/* Metodo para editar o aluno, esse metodo recebe o id do e atraves desse aluno realziamos 
   a substituicao do peso passando uma variavel do tipo Aluno chamada edit peso que recebe
   um getValue da varaivel entry no froEach map  e entao editPeso passa essa variavel para o metodo
   setPeso da classe Aluno  e entao o alunoMap recebe esse valor fazemos entao a chamada do metod
   responsavel por editar a linha dentro do arquivo;
 */
    public void editAlunos(String idAlunos, Map<UUID, Alunos> alunosMap, Double peso ){
        UUID AlunoId = UUID.fromString(idAlunos);  

        System.out.println("Searching for ID: " + AlunoId);

        if (alunosMap.isEmpty()) {
            System.out.println("Alunos map is Empty");
            return;
        }

        Alunos editedPeso = null;

        for (Map.Entry<UUID, Alunos> entry : alunosMap.entrySet()) {
            if(entry.getKey().equals(AlunoId)){
              Alunos editPeso = entry.getValue();
                editPeso.setPeso(peso);
                editedPeso = alunosMap.put(entry.getKey(), editPeso);

                if(editedPeso != null){
                    // metodo que realiza a edicao do peso dentro do arquvio passamso como parametro o peso o id e o diretorio de do aluno
                    editLineFromFile(idAlunos, peso, "C:\\Users\\carlo\\Desktop\\Goldem Gym\\Alunos\\AlunosFile.txt");
                }
                
            }
        }


    }


    /* Metodo reponsavel por relaizar a edicao do peso do aluno dentro do arquivo de alunos esse metodo
        criar um arquivo do tipo storage que recebe os dados do arquivo original e o valor de peso 
        editado deleta o arquivo original e renomea para que ele fique com o nome do arquivo original
     */
    public void editLineFromFile(String idAluno, Double peso, String filePath) {
        File file = new File(filePath);
        File storage = new File(file.getParentFile(), "Storage.txt");
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(storage))) {
    
            String currentLine;
            boolean found = false;
    
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains(idAluno)) {
                    /* linha reponsaverl por editar o valor de peso recebendo o id de currend line
                      que compara se essa current line e igual ao id passado e entao passamos para essa linha
                      o valor de peso atraves de new line, essa variavel altera apenas o valor de peso
                     */
                    String newLine = currentLine.replaceAll("Peso: \\d+\\.\\d+", "Peso: " + peso);
                    writer.write(newLine);
                    found = true;
                } else {
                    writer.write(currentLine);
                }
                writer.newLine();
            }
    
            if (!found) {
                System.out.println("Aluno with id: " + idAluno + " not found in the file.");
            }
  
            writer.close();
            reader.close();
    

            if(!file.delete()){
                System.out.println("Could not delete the file");
            }

            if (!storage.renameTo(file)) {
                System.out.println("Could not rename to storage");
            }
    
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    /* Metodo para listar e imprimir os treinos e puxa o arquivo do treino que e passado atraves
     do fileName pelo metodo creatWorkoutPlane ele imprime os exercicios com base na 
     esturutra da classe Treino*/
    public void readAndPrintWork(String fileName, ArrayList<Treino> listarTreino) {
        System.out.println("Printing exercises from file: " + fileName);
        System.out.println();
        for (Treino treino : listarTreino) {
            System.out.println("Exercicio: " + treino.getExercicio());
            System.out.println(""); 
        }
    }

/* Metodo para criar uma pasta e arquivo intrutor responsavel por tambem escrever no arquivo 
\de instrutores cada instrutor, passado  */
    public void creatInstrutor(Map<UUID, Instrutor> instrutorMap) throws IOException{
        File dir = new File("C:\\Users\\carlo\\Desktop\\Goldem Gym");

        if(!dir.exists());
        dir.mkdir();

        File newDir = new File(dir, "Instrutor");

        if(!newDir.exists()){
            Boolean created = newDir.mkdir();

            if(created) {
                System.out.println("Folder Crerated Suceefully");
            }else{
                System.out.println("Failled to Created Foulder");
            }
        }else{
            System.out.println("File Already Exists");
        }


        File InstrutorFille = new File(newDir, "Instrutores.txt");

        if(!InstrutorFille.exists()){
            Boolean fileCreated = InstrutorFille.createNewFile();

            if(fileCreated){
                System.out.println("File Created Sucessfully");
            }else{
                System.out.println("Failled to create Instrutor File");
            }
        }else {
            System.out.println("File already Exists");
        }


        FileWriter file = new FileWriter(InstrutorFille, true);
        PrintWriter write = new PrintWriter(file);


        for (Map.Entry<UUID, Instrutor> entry : instrutorMap.entrySet()) {
            write.println("Id: " + entry.getKey() + " "
            + "Nome: " + entry.getValue().getNome() + " "
            + "Area-De-Atuacao: " + entry.getValue().getAreaDeAtuacao() + " "
            + "Horario-disponivel-para-Reserva: " + entry.getValue().getHorarioInicio() + 
            " as " + entry.getValue().getHorarioFim());
            System.out.println("Sucesfully in Wrinting Data");
        }

        write.flush();
        write.close();
    }



    /*Metodo reponsavel por ler e popular o array de instrutores, 
     esse metodod le o arquivo converte linhas em variaves e empurra essas
      variaveis para o arraylist de instrutores
     */
    public void readAndPopulateInstructor(Map<UUID, Instrutor> instrutorMap) {
        File instructorFolder = new File("C:\\Users\\carlo\\Desktop\\Goldem Gym\\Instrutor");
        File instructorFile = new File(instructorFolder, "Instrutores.txt");

        if (instructorFolder.exists()) {
            System.out.println("Instructor Folder Found");
        } else {
            System.out.println("Instructor Folder Not Found");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(instructorFile))) {
            String instructorLine;

            while ((instructorLine = reader.readLine()) != null) {
                String[] parts = instructorLine.split(" ");
                if (parts.length > 1) {
                    String id = parts[1].trim();
                    String nome = parts[3].trim();
                    String atuacao = parts[5].trim();
                    String horarioIn = parts[7].trim();
                    String horarioOut = parts[9].trim();
                    instrutorMap.put(UUID.fromString(id), new Instrutor(nome, atuacao, horarioIn, horarioOut));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading instructor file: " + e.getMessage());
        }
    }


    //metodo para printar os dados da array de intrutores
    public void readAndPrintInstructor(Map<UUID, Instrutor> instrutorMap){
        for (Map.Entry<UUID, Instrutor> entry : instrutorMap.entrySet()) {
            System.out.println("Id: " + entry.getKey() +  " " + "Instrutor: " + entry.getValue().getNome() + " " 
            + "Area de Atuacao: " +  entry.getValue().getAreaDeAtuacao() + " "
            + "Horario Disponivel: " + entry.getValue().getHorarioInicio() + " as " + entry.getValue().getHorarioFim() );
        }   
    }

    // Metodo para buscar um instrutor baseado em seu id esse metodo retorna um valor do tiipo nome do instrutor
    public Instrutor findInstructorId(Map<UUID, Instrutor> instrutorMap, String idInstrutor){
        UUID idInst = UUID.fromString(idInstrutor);

        if(instrutorMap.isEmpty()){
            System.out.println("Instrutores map is Empty");
            return null;
        }

        for (Map.Entry<UUID,Instrutor> entry : instrutorMap.entrySet()) {
            if(entry.getKey().equals(idInst)){
                System.out.println("Instrutor: " + entry.getValue().getNome() + " Founded");
                return entry.getValue();
            }
        }

        return null;


    }

    /*Metodo responsavel por ler o arquivo de equipamentos e empurrar esse equipamentos para um 
    array de equipamentos */
    public void lerEquipamentoFile(ArrayList<Equipamento> equipamentos) {
        File dirFile = new File("C:\\Users\\carlo\\Desktop\\Goldem Gym\\Equipamentos");
        File equipamentsFile = new File(dirFile, "Equipamentos.txt");
    
        if (equipamentsFile.exists() && equipamentsFile.isFile()) {
            System.out.println("Equipamentos file found");
        } else {
            System.out.println("Equipamentos file not found");
            return;
        }
    
        try (BufferedReader reader = new BufferedReader(new FileReader(equipamentsFile))) {
            String equipamentoLine;
    
            while ((equipamentoLine = reader.readLine()) != null) {
                if (equipamentoLine.startsWith("Equipamento:")) {
                    // Remove the "Equipamento:" label and trim the whitespace
                    String equipamentoData = equipamentoLine.substring("Equipamento:".length()).trim();
                    
                    // Split the remaining data based on the other attribute labels
                    String[] parts = equipamentoData.split("Categoria:|Reserva:|Horario:");
    
                    // Extract attribute values
                    String Equipamento = parts[0].trim();
                    String Categoria = parts[1].trim();
                    String Reserva = parts[2].trim();
                    String Horario = parts[3].trim();
    
                    // Create the Equipamento object with extracted attribute values
                    Equipamento equipamento = new Equipamento(Equipamento, Categoria, Reserva, Horario);
                    equipamentos.add(equipamento);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Metodod para listar os dados do array de equipamento
    public void listarEquipament(ArrayList<Equipamento> equipamentos){
        for (Equipamento equipamento : equipamentos) {
            System.out.println("Equipamento: " + equipamento.getEquipamento() + " "
            + "Reserva: " +  equipamento.getReserva() + " " 
            + "Horario: " + equipamento.getHorario());
        }
    }

    /* metodo para editar o equipamento  nesse metodo consigo passar um parametro do tipo aluno e horario
     * podendo assim reservar um equipamentoatraves do nome de um aluno e o horario da reserva esse metodo lida
     * com as reservas do equipamento atraves da edicao desses dados, a lista de equipamento e estatica ou seja
     * todos os equipamentos foram pre cadastrados
     */
    public void editEquipamentsList(int selectedIndex, Alunos newReserva, String newHorario, ArrayList<Equipamento> equipamentos){
        if(selectedIndex < 0 || selectedIndex >= equipamentos.size()){
            System.out.println("Ivalid equipament index");
            return;
        }

        String alunoName = newReserva.getNome();
        String reservaHorario = newHorario;
        Equipamento equipamento = equipamentos.get(selectedIndex);

        equipamento.setReserva(alunoName);
        equipamento.setHorario(reservaHorario);

        if(equipamento != null){

            editEquipamentFile(selectedIndex, alunoName, newHorario, "C:\\Users\\carlo\\Desktop\\Goldem Gym\\Equipamentos\\Equipamentos.txt");
        }
        
        
        
    }

/*Esse metodo realiza a edicao de equipamentos dentro do arquivo esse metodo e chamado dentro do metodo anterior e
 * recebe as variaves e entao cria uma novo arquivo chamado storage, esse arquivo contem um as linhas do arquivo original
 * inalteradas e a linha alterada ja com as alterracoes entao o arquivo original e deletado e o arquivo storage renomeado
 * para  o arquivo oringinal
 */
    public void editEquipamentFile(int index, String alunoNameReserva, String horarioReserva, String FilePath ){
        File file = new File(FilePath);
        File storage = new File(file.getParentFile(),"Storage.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
         BufferedWriter writer = new BufferedWriter(new FileWriter(storage))) {

            String currentLine;
            int curretIndex = 0;
            Boolean found = false;

            while ((currentLine = reader.readLine()) != null){
                if (curretIndex == index && currentLine.contains("Equipamento:")) {
                    String newLine = currentLine.replaceAll("Reserva: [^ ]+", "Reserva: " + alunoNameReserva)
                    .replaceAll("Horario: [^;]+", "Horario: " + horarioReserva);
                    writer.write(newLine);
                    found = true;
                }else{
                    writer.write(currentLine);
                }
                writer.newLine();
                curretIndex++;
            }

            if(!found){
                System.out.println("Line not Founded");
            }

            writer.close();
            reader.close();


            if(!file.delete()){
                System.out.println("could Not Delet the file");
            }

            if(!storage.renameTo(file)){
                System.out.println("Could not rename to storage");
            }

        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    
    /* Metodo para criar um reserva de treino esse metodo cria uma pasta e um arquivo chamado reservas
     * essas reservas sao concatenadas com outros arquivos atreaves de array ou seja e possivel escrever com base no
     * aluno quais equipamentos foram reservados em seu nomee tambem coletar o seu plano de treino referete ao dia exemplo
     * segunda peito. Ele ira registrar dentro desse arquivo o dia e os exertcicios registrados nesse dia 
     */
    public void createWorkoutReserv(Alunos aluno, Instrutor instructor,
    Map<UUID, Alunos> alunoMap, Map<UUID, Instrutor> instrutorMap, 
    List<Equipamento> equipReserved, List<Treino> addTreino, String data) throws IOException {

File dir = new File("C:\\Users\\carlo\\Desktop\\Goldem Gym");
if (!dir.exists()) {
    dir.mkdir();
}

File newDir = new File(dir, "Reservas");
if (!newDir.exists()) {
    boolean created = newDir.mkdir();
    if (created) {
        System.out.println("Folder Created Successfully");
    } else {
        System.out.println("Failed to create Folder");
    }
} else {
    System.out.println("Folder already exists");
}

File reservasFile = new File(newDir, "Reservas.txt");
boolean fileCreated = reservasFile.createNewFile();
if (fileCreated) {
    System.out.println("File created Successfully");
} else {
    System.out.println("Failed to create File");
}

try (FileWriter file = new FileWriter(reservasFile, true);
        PrintWriter writer = new PrintWriter(file)) {
    StringBuilder lineBuilder = new StringBuilder();

    lineBuilder.append("Aluno: ").append(aluno.getNome())
                .append(" Instrutor: ").append(instructor.getNome())
                .append(" Horario: ").append(instructor.getHorarioInicio())
                .append(" as ").append(instructor.getHorarioFim());
    
                boolean firstEquipamento = true;
                for (Equipamento equipamento : equipReserved) {
                    if (equipamento.getReserva().contains(aluno.getNome())) {
                        if (firstEquipamento) {
                            lineBuilder.append(" Equipamentos: ");
                            firstEquipamento = false;
                        } else {
                            lineBuilder.append(", ");
                        }
                        lineBuilder.append(equipamento.getEquipamento());
                    }
                }

    Set<String> processedDays = new HashSet<>();

   boolean firstTreino = true;
for (Treino treino : addTreino) {
    if (treino.getData().contains(data)) {
        String currentDay = treino.getData();
        if (!processedDays.contains(currentDay)) {
            processedDays.add(currentDay);
            lineBuilder.append(" Dia: ").append(treino.getData());
        }
        if (firstTreino) {
            lineBuilder.append(" Treino: ");
            firstTreino = false;
        } else {
            lineBuilder.append(", ");
        }
        lineBuilder.append(treino.getExercicio())
                   .append(" ").append(treino.getRepeticoesIn())
                   .append(" a ").append(treino.getRepeticoesOut());
    }
}

    writer.println(lineBuilder.toString());
}
}


    /*Metodo responsavel por ler o arquivo de treino de cada um dos usuario, cada ususario possui seu proprio 
     * arquivo de treino por isso e passado o id, esse id e usado para identificar  arquivo do usuario, enta oso dados do
     * arquivo sao passados para variaves e empurrados para um array.
     */
    public void readWorkotuPlanofAlunoById(String idAluno, 
    Files file, Map<UUID, Alunos> alunosMap, Alunos aluno, List<Treino> planoDeTtreino ){
        File dir = new File("C:\\Users\\carlo\\Desktop\\Goldem Gym\\Tabel-Treinos");

        Alunos alunoById = alunosMap.get(UUID.fromString(idAluno));
    
        if (alunoById != null) {
            File filePath = new File(dir, alunoById.getNome() + "-" + idAluno + "-Tabela.txt");
            System.out.println(filePath.getAbsolutePath());
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                String dia = null;
                String exercicio = null;
                String repeticoes = null;
    
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Dia:")) {
                     
                        dia = line.substring("Dia:".length()).trim();
                    } else if (line.startsWith("Exercico:")) {
                      
                        exercicio = line.substring("Exercico:".length()).trim();
                    } else if (line.startsWith("Repeticoes:")) {
                        
                        repeticoes = line.substring("Repeticoes:".length()).trim().replace(";", "");
    
                        String[] repetitionsRange = repeticoes.split("a");
                        if (repetitionsRange.length == 2) {
                            int startRepetitions = Integer.parseInt(repetitionsRange[0].trim());
                            int endRepetitions = Integer.parseInt(repetitionsRange[1].trim());
    
                            Treino treino = new Treino(exercicio, startRepetitions, endRepetitions, dia);
                            planoDeTtreino.add(treino);
                        }
                    }
                }
    
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
       }
  }


  /*Metodo para ler o arquvio de plano de treino e preencher a o array de treinos */
  public void readAndPopulateTrainingPlan(String idAluno, Files file, 
  Map<UUID, Alunos> alunosMap, Alunos aluno, ArrayList<Treino> planoTreino) throws IOException{

    File dir = new File("C:\\Users\\carlo\\Desktop\\Goldem Gym\\Tabel-Treinos");

    Alunos alunoById = alunosMap.get(UUID.fromString(idAluno));

    if(alunoById != null){
        File filePath = new File(dir, alunoById.getNome() + "-" + idAluno + "-Tabela.txt");
        System.out.println("File: " + filePath.getAbsolutePath());

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String dia = null;
            String exercicio = null;
            int repIn = 0;
            int repOut = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.startsWith("Dia:")) {
                    dia = line.substring("Dia:".length()).trim();
                } else if(line.startsWith("Exercico:")) {
                    exercicio = line.substring("Exercico:".length()).trim();
                } else if(line.startsWith("Repeticoes:")) {
                    String repetition = line.substring("Repeticoes:".length()).trim();
                    String[] repRange = repetition.split("a");
                    if(repRange.length == 2){
                        repIn = Integer.parseInt(repRange[0].trim());
                        repOut = Integer.parseInt(repRange[1].trim());
                    }
                    Treino treino = new Treino(exercicio, repIn, repOut, dia);
                    planoTreino.add(treino);
                }
            }
        }
    } else {
        System.out.println("Plano de treino not found");
    }
}



/*Metodo que lista os dados dentro do arquivo Reservas e preenche o array um array para maranipulacao */

public void listReservations(Alunos aluno, Instrutor instrutor, Map<UUID, Alunos> alunosMap,
                              Map<UUID, Instrutor> instrutorMap, List<Equipamento> equipReserved, Treino treino, Reservas reservas,
                              ArrayList<Reservas> rerserves) throws IOException {

    File path = new File("C:\\Users\\carlo\\Desktop\\Goldem Gym\\Reservas\\Reservas.txt");

    if (path.exists()) {
        System.out.println(path.getAbsolutePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String alunos = "";
            String instru = "";
            String horario = "";
            String dia = "";
            String train = "";
            String equip = "";
            String line;



            while ((line = reader.readLine()) != null) {
                if (line.contains("Aluno:")) {
                   int start = line.indexOf("Aluno:") + "Aluno:".length();
                   int end = line.indexOf("Instrutor:");
                   alunos = line.substring(start, end);
                } 

                if(line.contains("Instrutor:")){
                    try {
                        int start = line.indexOf("Instrutor:") + "Instrutor:".length();
                    int end = line.indexOf("Horario");

                    instru = line.substring(start, end);
                    } catch(DateTimeParseException e){
                        System.out.println(e.getMessage());
                    }
                    
                }

                if(line.contains("Horario:")){
                    int start = line.indexOf("Horario:") + "Horario:".length();
                    int end = line.indexOf("Equipamentos");
                     horario = line.substring(start, end).trim();
                }

                if(line.contains("Equipamentos:")){
                    int start = line.indexOf("Equipamentos:") + "Equipamentos:".length();
                    int end = line.indexOf("Dia:");
                    equip = line.substring(start, end);
                }

                if(line.contains("Dia:")){
                    int start = line.indexOf("Dia:") + "Dia:".length();
                    int end = line.indexOf("Treino:");
                    dia = line.substring(start, end);
                }

                if(line.contains("Treino:")){
                    int start = line.indexOf("Treino:") + "Treino:".length();

                    train = line.substring(start);
                }


                /* cada um dos if's serve para cehcar se a linha contain
                 tal infromacao e dai copiar os dados que estao entre informacao tal ate tal
                */

                Reservas reservation = new Reservas(alunos, instru,  horario, equip, train,  dia);
                rerserves.add(reservation);
               
            }
        }
    } else {
        System.out.println("Path not found");
    }
}

}