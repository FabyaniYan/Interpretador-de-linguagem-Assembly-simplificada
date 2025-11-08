/* 	------------------------------------------------
 * 	NOME: Aline Barbosa Vidal 
 * 	RA: 10721348
 * 	------------------------------------------------
 * 	NOME: Antonio Costa Satiro de Souza 
 * 	RA: 10723636
 * 	------------------------------------------------
 * 	NOME: Fabyani Tiva Yan 
 * 	RA: 10431835
 * 	------------------------------------------------
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Interpreter INTERP = new Interpreter();
    private static CodeListAdapter program = new CodeListAdapter();
    private static String currentFile = null;
    private static boolean dirty = false;
    private static final Scanner IN = new Scanner(System.in);

    //funcao de mensagem
    private static void msg(String s){
        System.out.println(s);
    }

    private static void mostrarAjudaInicial() {
        msg("------------------------------------------------");
        msg("Comandos disponiveis:");
        msg("  Load <nome do arquivo> - carrega o arquivo");
        msg("  List                 - lista o codigo carregado");
        msg("  Ins <linha> <texto>  - insere ou atualiza uma linha");
        msg("  Del <linha>          - remove uma linha");
        msg("  Del <li> <lf>        - remove intervalo de linhas");
        msg("  Save                 - salva o codigo no arquivo");
        msg("  Run                  - executa o programa atual");
        msg("  Exit                 - encerra o sistema");
        msg("------------------------------------------------");
        msg("Digite um comando e pressione enter para comecar.");
        msg("");
    }
    
    //para o comando carregar
    private static void cmdLOAD(String caminho){
        if (caminho == null){
            msg("Erro: uso correto -> LOAD <ARQUIVO.ED1>");
            return;
        }
        if (dirty) {
            msg("Arquivo atual (" + (currentFile==null?"<sem arquivo>":("'"+currentFile+"'")) + ") contem alteracoes nao salvas.");
            msg("Deseja salvar? (s/n)");
            String ans = IN.nextLine();
            if (ans != null && ans.trim().equalsIgnoreCase("s")) {
                if (currentFile == null) {
                    msg("Erro: nenhum arquivo atual para salvar.");
                } else {
                    try { 
                        program.saveToFile(new File(currentFile)); 
                        msg("Arquivo '"+currentFile+"' salvo com sucesso."); 
                        dirty = false; 
                    } catch (Exception e){ msg("Erro ao salvar arquivo atual."); }
                }
            }
        }
        try {
            CodeListAdapter tmp = new CodeListAdapter();
            tmp.loadFromFile(new File(caminho));
            program = tmp;
            currentFile = caminho;
            dirty = false;
            msg("Arquivo '"+caminho+"' carregado com sucesso.");
        } catch(IOException e){
            msg("Erro ao abrir o arquivo '"+caminho+"'.");
        }
    }
    //comando para listar
    private static void cmdLIST(){ 
        if (program.getHead() == null){
            msg("Nenhum codigo carregado. use LOAD ou INS para comecar.");
        } else {
            program.printPaged(System.out); 
        }
    }
    //comando para executar
    private static void cmdRUN(){
        if (program.getHead()==null){
            msg("Erro: nenhum codigo carregado. use LOAD ou INS antes de RUN.");
            return;
        }
        INTERP.run(program);
    }
    //comando para inserir
    private static void cmdINS(String[] tok){
        if (tok.length < 3){
            msg("Erro: uso correto -> INS <LINHA> <INSTRUCAO>");
            return;
        }
        int linha;
        try { linha = Integer.parseInt(tok[1]); }
        catch (NumberFormatException e){ msg("Erro: linha "+tok[1]+" invalida."); return; }
        if (linha < 0){ msg("Erro: linha "+linha+" invalida."); return; }
        StringBuilder sb = new StringBuilder();
        for (int i=2;i<tok.length;i++){ if (i>2) sb.append(' '); sb.append(tok[i]); }
        String instr = sb.toString();
        boolean inseriu = program.insertOrUpdate(linha, instr);
        dirty = true;
        if (inseriu){
            msg("Linha inserida:");
            msg(linha + " " + instr);
        } else {
            msg("Linha atualizada:");
            msg(linha + " " + instr);
        }
    }
    //comando de deletar linha
    private static void cmdDEL(String[] tok){
        if (tok.length == 2){
            int lin;
            try { lin = Integer.parseInt(tok[1]); }
            catch (NumberFormatException e){ msg("Erro: linha "+tok[1]+" invalida."); return; }
            CodeLine rem = program.remove(lin);
            if (rem == null) msg("Erro: linha " + lin + " inexistente.");
            else { msg("Linha removida:"); msg(rem.line + " " + rem.instr); dirty = true; }
            return;
        }
        if (tok.length == 3){
            int li, lf;
            try { li = Integer.parseInt(tok[1]); lf = Integer.parseInt(tok[2]); }
            catch (NumberFormatException e){ msg("Erro: intervalo invalido."); return; }
            if (li > lf){ msg("Erro: intervalo invalido."); return; }
            CodeListAdapter removidos = program.removeRange(li, lf);
            if (removidos == null){
                msg("Erro: nenhuma linha encontrada no intervalo.");
            } else {
                msg("Linhas removidas:");
                removidos.printPaged(System.out);
                dirty = true;
            }
            return;
        }
        msg("Erro: uso correto -> DEL <LINHA> ou DEL <LINHA_I> <LINHA_F>");
    }
    //comando para salvar
    private static void cmdSAVE(String[] tok){
        String caminho = currentFile;
        if (tok.length == 2){
            caminho = tok[1];
            File f = new File(caminho);
            if (f.exists()){
                msg("Arquivo '"+caminho+"' ja existe. deseja sobrescrever? (s/n)");
                String ans = IN.nextLine();
                if (ans==null || !ans.trim().equalsIgnoreCase("s")){
                    msg("Arquivo nao salvo.");
                    return;
                }
            }
        }
        if (caminho == null){
            msg("Erro: nenhum arquivo especificado.");
            return;
        }
        try {
            program.saveToFile(new File(caminho));
            currentFile = caminho;
            dirty = false;
            msg("Arquivo '"+caminho+"' salvo com sucesso.");
        } catch(IOException e){
            msg("Erro ao salvar o arquivo.");
        }
    }
    //comando para sair
    private static boolean cmdEXIT(){
        if (dirty){
            msg("Arquivo atual ('"+(currentFile==null?"<sem arquivo>":currentFile)+"') contem alteracoes nao salvas.");
            msg("Deseja salvar? (s/n)");
            String ans = IN.nextLine();
            if (ans != null && ans.trim().equalsIgnoreCase("s")) {
                if (currentFile == null){
                    msg("Digite o nome do arquivo para salvar (ex: programa.ed1):");
                    String nome = IN.nextLine().trim();
                    if (nome.isEmpty()) {
                        msg("Operacao cancelada, arquivo nao salvo.");
                    } else {
                        try {
                            program.saveToFile(new File(nome));
                            currentFile = nome;
                            dirty = false;
                            msg("Arquivo '"+nome+"' salvo com sucesso.");
                        } catch (IOException e){
                            msg("Erro ao salvar o arquivo.");
                        }
                    }
                } else {
                    try { program.saveToFile(new File(currentFile)); msg("Arquivo '"+currentFile+"' salvo com sucesso."); dirty = false; }
                    catch (IOException e){ msg("Erro ao salvar arquivo atual."); }
                }
            }
        }
        msg("Fim.");
        return true;
    }

    private static void loop(){
        mostrarAjudaInicial();
        while (true){
            System.out.print("> ");
            System.out.flush();
            if (!IN.hasNextLine()) break;
            String linha = IN.nextLine();
            if (linha == null) break;
            linha = linha.trim();
            if (linha.isEmpty()) continue;
            String[] tok = linha.split("\\s+");//divide a linha em palavras
            String cmd = tok[0].toUpperCase();
            switch (cmd){
                case "LOAD": cmdLOAD(tok.length>1?tok[1]:null); break;
                case "LIST": cmdLIST(); break;
                case "RUN":  cmdRUN(); break;
                case "INS":  cmdINS(tok); break;
                case "DEL":  cmdDEL(tok); break;
                case "SAVE": cmdSAVE(tok); break;
                case "EXIT": if (cmdEXIT()) return; break;
                default: msg("Erro: comando invalido. digite um dos comandos listados acima.");
            }
        }
    }

    public static void main(String[] args){ loop(); }
}
