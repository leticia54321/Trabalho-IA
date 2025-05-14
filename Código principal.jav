package Trabalho_IA_NP2;
import java.io.*;
import java.util.*;
public class BuscaDeCaminhos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //leitura dos dados da planilha CSV - feita em excel
        String arquivo = "cidades.csv";
        Map<String, Cidade> cidades = new HashMap<>();
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length != 2) continue;

                String nomeOrigem = partes[0].trim();
                String nomeVizinho = partes[1].trim();

                Cidade origem = cidades.computeIfAbsent(nomeOrigem, Cidade::new);
                Cidade vizinho = cidades.computeIfAbsent(nomeVizinho, Cidade::new);

                origem.vizinhos.add(vizinho);
            }
        // Tratamento de erro caso o arquivo não seja lido corretamente
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
            e.printStackTrace();
            return;
        }
        // Cidade de origem ja pré-definida como Ribeirão Preto
        System.out.println("Cidade de origem: Ribeirao Preto");
        System.out.println("");
        // Lista de possíveis cidades de destino
        System.out.println("LISTA DE CIDADES:");
        System.out.println("Altinopolis, Araraquara, Barretos, Barrinha, Batatais, Bebedouro, Brodowski, Cajobi, Cajuru, Candia, Colina, Cravinhos, Cruz das Posses, Dumont, "
                + "Franca, Guariba, Ibitiuva, Jaborandi, Jaboticabal, \nJardinopolis, Luis Antonio, Matao, Mococa, Monte Alto, Monte Azul Paulista, Morro Agudo, Nuporanga, Orlandia,"
                + " Pedregulho, Pirangi, Pitangueiras, Pontal, Pradopolis, Ribeirao Preto, Sales Oliveira, \nSanta Cruz da Esperanca, Santa Cruz das Palmeiras, Santa Rosa de Viterbo, "
                + "Santo Antonio da Alegria, Sao Joaquim da Barra, Sao Jose da Bela Vista, Sao Simao, Serra Azul, Serrana, Sertaozinho, Severinia, \nTaiacu, Tambau, Taquaritinga, "
                + "Terra Roxa, Viradouro, Vista Alegre do Alto\n");
        // Campo para o usuário digitar a cidade
        System.out.println("Escreva o nome sem acentos!");
        System.out.print("Cidade de destino: ");
        String destino = sc.nextLine().trim();

        Cidade cidadeOrigem = cidades.get("Ribeirao Preto");
        Cidade cidadeDestino = cidades.get(destino);

        // Caso a cidade não for encontrada
        if (cidadeOrigem == null || cidadeDestino == null) {
            System.out.println("Uma das cidades não foi encontrada.");
            return;
        }

        // Criação da listas de caminho percorrido e das cidades visitidas
        List<String> caminho = new ArrayList<>();
        Set<String> visitados = new HashSet<>();

        // Verificação de true ou false da função, retornando ou não o caminho encontrado
        if (BuscaProfundidade(cidadeOrigem, cidadeDestino, caminho, visitados)) {
            System.out.println("Caminho encontrado: " + caminho);
        } else {
            System.out.println("Caminho não encontrado.");
        }
    }

  
    static class Cidade {
        String nome;
        List<Cidade> vizinhos;

        // Construtor da classe Cidade
        Cidade(String nome) {
            this.nome = nome;
            this.vizinhos = new ArrayList<>();
        }
    }

    // Função que realiza a Busca em Profundidade verificando o caminho possível
    private static boolean BuscaProfundidade(Cidade atual, Cidade destino, List<String> caminho, Set<String> visitados) {
        caminho.add(atual.nome);
        visitados.add(atual.nome);

        if (atual == destino) {
            return true;
        }
        for (int i = 0; i < atual.vizinhos.size(); i++) {
            Cidade vizinho = atual.vizinhos.get(i);

            // Verifica se a cidade não foi visitada
            if (!visitados.contains(vizinho.nome)) {
                if (BuscaProfundidade(vizinho, destino, caminho, visitados)) {
                    return true;
                }
            }
        }
        // Remove a cidade da lista do caminho
        caminho.remove(caminho.size() - 1); 
        return false;
    }
}
