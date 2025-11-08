/*  ------------------------------------------------
 *  Nome: Aline Barbosa Vidal 
 *  RA: 10721348
 *  ------------------------------------------------
 *  Nome: Antonio Costa Satiro de Souza 
 *  RA: 10723636
 *  ------------------------------------------------
 *  Nome: Fabyani Tiva Yan 
 *  RA: 10431835
 *  ------------------------------------------------
 *
 *  Classe: ValidadorAssembly.java
 *
 *  Descrição: Responsável por validar instruções,
 *  argumentos, registradores e linhas da 
 *  linguagem Assembly simplificada.
 *  ------------------------------------------------
 */

public class ValidadorAssembly {

	// Lista de Comandos Válidos
    private static final String[] COMANDOS = {
            "mov", "inc", "dec", "add", "sub",
            "mul", "div", "jnz", "out"
    };
    
    // Lista de Registradores Válidos (A-Z)
    private static final char[] REGISTRADORES = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'
    };
    
    // Armazena se o Registrador foi Inicializado ou Não (0-1)
    private static int[] inicializado = new int[26];
    
    // Armazena o Valor do Registrador
    private static int[] valorReg = new int[26];

    // Reinicia os Registradores
    public static void resetState() {
        inicializado = new int[26];
        valorReg = new int[26];
    }
    
    // Calcula o Índice Correspondente do Registrador na Lista
    private static int indiceRegistrador(char r) {
        return Character.toUpperCase(r) - 'A';
    }

    // Verifica se o Registrador foi Inicializado (true-false)
    private static boolean isRegistradorInicializado(String reg) {
        if (reg == null || reg.length() != 1) return false;
        char c = Character.toUpperCase(reg.charAt(0));
        if (c < 'A' || c > 'Z') return false;
        return inicializado[indiceRegistrador(c)] == 1;
    }
    
    // Registra o Valor Recebido num Determinado Registrador
    private static void registrarMov(String reg, int valor) {
        if (reg == null || reg.length() != 1) return;
        char c = Character.toUpperCase(reg.charAt(0));
        if ((c < 'A') || (c > 'Z')) return;
        int index = indiceRegistrador(c);
        inicializado[index] = 1;
        valorReg[index] = valor;
    }
    
    
    public static boolean validarInstrucao(String instrucao) {
        if (instrucao == null) {
            return false;
        }
        instrucao = instrucao.toLowerCase(); // Case Insensitive
        for (String s : COMANDOS) {
            if (s.equals(instrucao)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validarRegistrador(String registrador) {
        if (registrador == null || registrador.length() != 1) {
            return false;
        }
        char c = Character.toUpperCase(registrador.charAt(0));
        return (c >= 'A' && c <= 'Z');
    }

    public static boolean ehNumero(String texto) {
        if (texto == null) {
            return false;
        }
        try {
            // Verifica se a string representa um número.
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // Valida a Instrução, Retornando As Mensagens Adequadas de Erro
    // Não Retorna Nenhuma Mensagem (null) Caso a Instrução Esteja Correta
    // Processo Feito Após Identificar se o comando na String está na Lista COMANDOS
    public static String validarArgumentos(String comando, String[] args) {
        if (comando == null || args == null) {
            return "Erro: Comando Nulo.";
        }
        comando = comando.toLowerCase();
        switch (comando) {
            case "mov":
                if (args.length != 2) return "Erro: 'MOV' Requer Dois Argumentos.";
                if (!validarRegistrador(args[0])) return "Erro: Primeiro Argumento de 'MOV' Deve ser um Registrador Válido (A-Z)";
                
                
                boolean isSourceReg = validarRegistrador(args[1]);
                boolean isSourceNum = ehNumero(args[1]);
                
                if (!isSourceReg && !isSourceNum) {
                    return "Erro: Segundo Argumento Inválido. Deve ser Registrador ou Número.";
                }

                
                if (isSourceReg && !isRegistradorInicializado(args[1])) {
                     return "Erro: Registrador '" + args[1].toUpperCase() + "' (Origem) Ainda Não Inicializado.";
                }
                
              
                int valor = 0;
                if (isSourceNum) {
                    valor = Integer.parseInt(args[1]);
                }
                registrarMov(args[0], valor);
                return null;

            case "add":
            case "sub":
            case "mul":
            case "div":
                if (args.length != 2) return "Erro: '" + comando + "' Requer 2 Argumentos.";
                if (!validarRegistrador(args[0])) return "Erro: Primeiro Argumento Deve Ser um Registrador Válido.";

                if (!isRegistradorInicializado(args[0])) {
                    return "Erro: Registrador '" + args[0].toUpperCase() + "' (Destino) Ainda Não Inicializado.";
                }

                isSourceReg = validarRegistrador(args[1]);
                isSourceNum = ehNumero(args[1]);

                if (!isSourceReg && !isSourceNum) {
                    return "Erro: Segundo Argumento de '" + comando + "' Deve Ser um Registrador ou Número Válido.";
                }

                
                if (isSourceReg && !isRegistradorInicializado(args[1])) {
                    return "Erro: Registrador '" + args[1].toUpperCase() + "' Ainda Não Inicializado.";
                }
                
                
                return null;

            case "inc":
            case "dec":
            case "out":
                if (args.length != 1) return "Erro: '" + comando + "' Requer 1 Argumento.";
                if (!validarRegistrador(args[0])) return "Erro: Argumento Inválido. Deve ser um Registrador Válido (A-Z).";

                if (!isRegistradorInicializado(args[0])) return "Erro: Registrador '" + args[0].toUpperCase() + "' Ainda Não Inicializado.";
                return null;

            case "jnz":
                if (args.length != 2) return "Erro: 'jnz' Requer 2 Argumentos.";
                if (!validarRegistrador(args[0])) return "Erro: Primeiro Argumento de 'jnz' Deve Ser um Registrador Válido(A-Z)";
                if (!isRegistradorInicializado(args[0])) return "Erro: Registrador '" + args[0].toUpperCase() + "' Ainda Não Inicializado.";
                if (!ehNumero(args[1])) return "Erro: Segundo Argumento de 'jnz' Deve Ser Número de Linha.";
                return null;

            default:
                return "Erro: Instrução Desconhecida";
        }
    }
    
    // Verifica se há Comandos Válidos na String Fornecida pelo Usuário
    public static String validarLinha(String linha) {
        if ((linha == null) || linha.trim().isEmpty()) return "Erro: Linha Vazia.";
        String[] partes = linha.trim().split("\\s+");

        if (partes.length < 2) return "Erro: Formato Inválido (<LINHA> <INSTRUÇÃO> [ARGUMENTOS]).";

        if (!ehNumero(partes[0])) return "Erro: Número de Linha Inválido.";

        String comando = partes[1];
        if (!validarInstrucao(comando)) return "Erro: Instrução Desconhecida.";

        String[] args = new String[partes.length - 2];
        for (int i = 2; i < partes.length; i++) {
            args[i - 2] = partes[i];
        }

        String Erro = validarArgumentos(comando, args);
        if (Erro != null) return Erro;
        return null;
    }
}

	
