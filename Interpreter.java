/* 	------------------------------------------------
 * 	Nome: Aline Barbosa Vidal 
 * 	RA: 10721348
 * 	------------------------------------------------
 * 	Nome: Antonio Costa Satiro de Souza 
 * 	RA: 10723636
 * 	------------------------------------------------
 * 	Nome: Fabyani Tiva Yan 
 * 	RA: 10431835
 * 	------------------------------------------------
 */

//interpretador
public class Interpreter {

    //registradores inteiros de A ate Z
    private final int[] reg = new int[26];

    //marca se cada registrador ja recebeu valor
    private final boolean[] hasValue = new boolean[26];

    //indice do registrador
    //converte letra (a..z ou A..Z) em posicao 0..25
    private static int idx(char c){ 
        return Character.toUpperCase(c) - 'A'; 
    }

    //verifica se string e nome de registrador (1 letra)
    private static boolean isRegisterName(String s){
        return s != null && s.length()==1 && Character.isLetter(s.charAt(0));
    }

    //obter valor de y (constante inteira ou registrador inicializado)
    //retorna null se nao for valido ou registrador sem valor
    private Integer valueOf(String y) {
        if (isRegisterName(y)) {
            int i = idx(y.charAt(0));
            if (!hasValue[i]) return null;
            return reg[i];
        }
        try { 
            return Integer.parseInt(y); 
        }
        catch (NumberFormatException e){ 
            return null; 
        }
    }

    //executa desde a menor linha; jnz altera o ponteiro atual
    public void run(CodeListAdapter prog){
        if (prog == null || prog.getHead() == null){
            System.out.println("erro: nenhum codigo carregado.");
            return;
        }

        //reset registradores a cada execucao
        for (int i=0;i<26;i++){ 
            reg[i]=0; 
            hasValue[i]=false; 
        }

        //comeca pela primeira linha do programa
        Node<CodeLine> cur = prog.getHead();

        //loop principal, percorre as linhas na ordem
        while (cur != null) {
            CodeLine cl = cur.getDado();

            //texto da instrucao, sem espacos extras
            String txt = cl.instr == null ? "" : cl.instr.trim();
            if (txt.isEmpty()){ 
                cur = cur.getProx(); 
                continue; 
            }

            //separa opcode e argumentos
            String[] p = txt.split("\\s+");
            String op = p[0].toLowerCase();
            
            //operacoes
            try {
                switch (op) {
                    case "mov": {
                        //mov x y  -> x recebe y (registrador ou constante)
                        if (p.length != 3) 
                            throw new IllegalArgumentException("mov requer 2 argumentos");

                        String x = p[1], y = p[2];
                        if (!isRegisterName(x)) 
                            throw new IllegalArgumentException("registrador x invalido");

                        Integer vy = valueOf(y);
                        if (vy == null) 
                            throw new IllegalArgumentException("valor/registrador y invalido ou nao inicializado");

                        int ix = idx(x.charAt(0));
                        reg[ix] = vy; 
                        hasValue[ix] = true;
                        break;
                    }
                    case "inc": {
                        //inc x  -> incrementa registrador x
                        if (p.length != 2) 
                            throw new IllegalArgumentException("inc requer 1 argumento");

                        String x = p[1];
                        if (!isRegisterName(x)) 
                            throw new IllegalArgumentException("registrador x invalido");

                        int ix = idx(x.charAt(0));
                        if (!hasValue[ix]) 
                            throw new IllegalArgumentException("registrador x nao inicializado");

                        reg[ix]++; 
                        break;
                    }
                    case "dec": {
                        //dec x  -> decrementa registrador x
                        if (p.length != 2) 
                            throw new IllegalArgumentException("dec requer 1 argumento");

                        String x = p[1];
                        if (!isRegisterName(x)) 
                            throw new IllegalArgumentException("registrador x invalido");

                        int ix = idx(x.charAt(0));
                        if (!hasValue[ix]) 
                            throw new IllegalArgumentException("registrador x nao inicializado");

                        reg[ix]--; 
                        break;
                    }
                    case "add": {
                        //add x y  -> x = x + y
                        if (p.length != 3) 
                            throw new IllegalArgumentException("add requer 2 argumentos");

                        String x = p[1], y = p[2];
                        if (!isRegisterName(x)) 
                            throw new IllegalArgumentException("registrador x invalido");

                        int ix = idx(x.charAt(0));
                        if (!hasValue[ix]) 
                            throw new IllegalArgumentException("registrador x nao inicializado");

                        Integer vy = valueOf(y);
                        if (vy == null) 
                            throw new IllegalArgumentException("valor/registrador y invalido ou nao inicializado");

                        reg[ix] += vy; 
                        break;
                    }
                    case "sub": {
                        //sub x y  -> x = x - y
                        if (p.length != 3) 
                            throw new IllegalArgumentException("sub requer 2 argumentos");

                        String x = p[1], y = p[2];
                        if (!isRegisterName(x)) 
                            throw new IllegalArgumentException("registrador x invalido");

                        int ix = idx(x.charAt(0));
                        if (!hasValue[ix]) 
                            throw new IllegalArgumentException("registrador x nao inicializado");

                        Integer vy = valueOf(y);
                        if (vy == null) 
                            throw new IllegalArgumentException("valor/registrador y invalido ou nao inicializado");

                        reg[ix] -= vy; 
                        break;
                    }
                    case "mul": {
                        //mul x y  -> x = x * y
                        if (p.length != 3) 
                            throw new IllegalArgumentException("mul requer 2 argumentos");

                        String x = p[1], y = p[2];
                        if (!isRegisterName(x)) 
                            throw new IllegalArgumentException("registrador x invalido");

                        int ix = idx(x.charAt(0));
                        if (!hasValue[ix]) 
                            throw new IllegalArgumentException("registrador x nao inicializado");

                        Integer vy = valueOf(y);
                        if (vy == null) 
                            throw new IllegalArgumentException("valor/registrador y invalido ou nao inicializado");

                        reg[ix] *= vy; 
                        break;
                    }
                    case "div": {
                        //div x y  -> x = x / y
                        if (p.length != 3) 
                            throw new IllegalArgumentException("div requer 2 argumentos");

                        String x = p[1], y = p[2];
                        if (!isRegisterName(x)) 
                            throw new IllegalArgumentException("registrador x invalido");

                        int ix = idx(x.charAt(0));
                        if (!hasValue[ix]) 
                            throw new IllegalArgumentException("registrador x nao inicializado");

                        Integer vy = valueOf(y);
                        if (vy == null) 
                            throw new IllegalArgumentException("valor/registrador y invalido ou nao inicializado");

                        if (vy == 0) 
                            throw new IllegalArgumentException("divisao por zero");

                        reg[ix] /= vy; 
                        break;
                    }
                    case "jnz": {
                        //jnz x linha  -> se x != 0, faz salto para numero de linha
                        if (p.length != 3) 
                            throw new IllegalArgumentException("jnz requer 2 argumentos");

                        String x = p[1], y = p[2];
                        if (!isRegisterName(x)) 
                            throw new IllegalArgumentException("registrador x invalido");

                        int ix = idx(x.charAt(0));
                        if (!hasValue[ix]) 
                            throw new IllegalArgumentException("registrador x nao inicializado");

                        int vx = reg[ix];
                        int target;
                        try { 
                            target = Integer.parseInt(y); 
                        }
                        catch (NumberFormatException e){ 
                            throw new IllegalArgumentException("segundo argumento de jnz deve ser numero de linha"); 
                        }

                        if (vx != 0) {
                            //procura linha alvo e muda o ponteiro de execucao
                            Node<CodeLine> jump = prog.findNodeByLine(target);
                            if (jump == null) 
                                throw new IllegalArgumentException("linha de salto inexistente: " + target);

                            cur = jump;
                            continue; //nao avanca para a proxima linha
                        }
                        break;
                    }
                    case "out": {
                        //out x  -> imprime valor do registrador x
                        if (p.length != 2) 
                            throw new IllegalArgumentException("out requer 1 argumento");

                        String x = p[1];
                        if (!isRegisterName(x)) 
                            throw new IllegalArgumentException("registrador x invalido");

                        int ix = idx(x.charAt(0));
                        if (!hasValue[ix]) 
                            throw new IllegalArgumentException("registrador x nao inicializado");

                        System.out.println(reg[ix]); 
                        break;
                    }
                    default:
                        //tratamento de erro
                        throw new IllegalArgumentException("instrucao invalida: " + p[0]);
                }
            } catch (IllegalArgumentException ex){
                //erro de execucao: mostra mensagem e a linha que causou erro
                System.out.println("erro: " + ex.getMessage());
                System.out.println("linha: " + cl.line + " " + cl.instr);
                return;
            }

            //avanca para a proxima linha
            cur = cur.getProx();
        }
    }
}
