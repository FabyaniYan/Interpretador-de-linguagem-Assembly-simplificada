public class TesteProjeto { 

	 

    public static void main(String[] args) { 

        System.out.println("=============================================="); 

        System.out.println("   INÍCIO DOS TESTES - ValidadorAssembly"); 

        System.out.println("==============================================\n"); 

 

        // ------------------------------------------------- 

        // CASOS VÁLIDOS SIMPLES 

        // ------------------------------------------------- 

        testar("10 mov a 5");        // MOV com número 

        testar("20 mov b a");        // MOV com registrador 

        testar("30 add a b");        // ADD 

        testar("40 sub a 2");        // SUB 

        testar("50 mul a b");        // MUL 

        testar("60 div a 2");        // DIV 

        testar("70 inc a");          // INC 

        testar("80 dec b");          // DEC 

        testar("90 out a");          // OUT 

        testar("100 jnz a 30");      // JNZ 

 

        // ------------------------------------------------- 

        // CASOS INVÁLIDOS DE FORMATAÇÃO 

        // ------------------------------------------------- 

        System.out.println("\n---- FORMATAÇÃO INVÁLIDA ----"); 

        testar("");                        // linha vazia 

        testar("mov a 5");                 // falta número da linha 

        testar("10mov a 5");               // sem espaço 

        testar("20  ");                    // só número 

        testar("30 add");                  // sem argumentos 

 

        // ------------------------------------------------- 

        // CASOS DE REGISTRADOR INVÁLIDO 

        // ------------------------------------------------- 

        System.out.println("\n---- REGISTRADOR INVÁLIDO ----"); 

        testar("10 mov aa 5");             // dois caracteres 

        testar("20 mov 1a 3");             // número inválido 

        testar("30 mov @ 2");              // caractere especial 

        testar("40 inc 10");               // inc em número 

        testar("50 add x 5");              // x não inicializado 

 

        // ------------------------------------------------- 

        // CASOS DE ARGUMENTO INVÁLIDO 

        // ------------------------------------------------- 

        System.out.println("\n---- ARGUMENTOS INVÁLIDOS ----"); 

        testar("10 mov a b c");            // argumentos demais 

        testar("20 add a");                // argumentos de menos 

        testar("30 jnz a linha20");        // jnz com texto 

        testar("40 div a 0");              // sintaticamente ok (divisão lógica será no interpretador) 

        testar("50 mul a b c");            // 3 argumentos 

        testar("60 out 99");               // out com número 

 

        // ------------------------------------------------- 

        // CASOS DE USO DE REGISTRADOR SEM MOV PRÉVIO 

        // ------------------------------------------------- 

        System.out.println("\n---- REGISTRADOR NÃO INICIALIZADO ----"); 

        testar("10 add c 5"); 

        testar("20 sub d c"); 
        
        testar("30 inc e"); 

        testar("40 out f"); 

        testar("50 jnz g 10"); 

 

        // ------------------------------------------------- 

        // CASOS DE INSTRUÇÕES DESCONHECIDAS 

        // ------------------------------------------------- 

        System.out.println("\n---- INSTRUÇÕES DESCONHECIDAS ----"); 

        testar("10 mvo a 5"); 

        testar("20 jump a 10"); 

        testar("30 print a"); 

        testar("40 move a b"); 

        testar("50 nop"); 

 

        // ------------------------------------------------- 

        // CASOS COMPLEXOS E CORRETOS 

        // ------------------------------------------------- 

        System.out.println("\n---- CÓDIGO COMPLEXO VÁLIDO ----"); 

        testar("10 mov a 5"); 

        testar("20 mov b 7"); 

        testar("30 add a b"); 

        testar("40 inc a"); 

        testar("50 dec b"); 

        testar("60 jnz b 30"); 

        testar("70 mov c a"); 

        testar("80 sub c 2"); 

        testar("90 out c"); 

 

        System.out.println("\n=============================================="); 

        System.out.println("   FIM DOS TESTES - ValidadorAssembly"); 

        System.out.println("=============================================="); 
        
        

    } 

 

    // ------------------------------------------------- 

    // Método auxiliar para chamar o ValidadorAssembly 

    // ------------------------------------------------- 

    private static void testar(String linha) { 

        String resultado = ValidadorAssembly.validarLinha(linha); 

        if (resultado == null) 

            System.out.println("[OK]   " + linha); 

        else 

            System.out.println("[ERRO] " + linha + " -> " + resultado); 

    } 

} 