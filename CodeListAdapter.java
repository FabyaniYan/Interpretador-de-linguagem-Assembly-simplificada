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

//adaptador que opera por numero de linha sobre linkedlist
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

    //lista encadeada que armazena as linhas de codigo
public class CodeListAdapter {
    private final LinkedList<CodeLine> list = new LinkedList<>();

    public boolean isEmpty(){ return list.isEmpty(); }

    //no cabeca (menor linha)
    public Node<CodeLine> getHead(){ return list.getHead(); }

    //insere mantendo ordenacao por numero de linha; atualiza se ja existir
   public boolean insertOrUpdate(int line, String instr){
        if (line < 0) throw new IllegalArgumentException("linha negativa");

        //primeiro elemento
        if (list.isEmpty()){
            list.insertHead(new CodeLine(line, instr));
            return true;
        }

        //procura posicao de insercao
        Node<CodeLine> p = list.getHead(), ant = null;
        int pos = 1;
        while (p != null && p.getDado().line < line){
            ant = p; 
            p = p.getProx(); 
            pos++;
        }

        //se ja existe, atualiza instrucao
        if (p != null && p.getDado().line == line){
            p.getDado().instr = instr;
            return false;
        }

        //insercao no inicio
        if (ant == null){
            list.insertHead(new CodeLine(line, instr));
        }
        //insercao no final
        else if (p == null){
            list.insertTail(new CodeLine(line, instr));
        }
        //insercao no meio
        else {
            list.insert(new CodeLine(line, instr), pos);
        }

        return true;
    }

    //remove linha exata; retorna elemento removido ou null
    public CodeLine remove(int line){
        Node<CodeLine> p = list.getHead();
        while (p != null){
            if (p.getDado().line == line){
                CodeLine val = p.getDado();
                list.remove(val);
                return val;
            }
            p = p.getProx();
        }
        return null;
    }

    //remove intervalo [li, lf]; devolve lista com removidos ou null
    public CodeListAdapter removeRange(int li, int lf){
        if (li > lf) return null;
        CodeListAdapter removed = new CodeListAdapter();
        Node<CodeLine> p = list.getHead();
        while (p != null){
            CodeLine cur = p.getDado();
            p = p.getProx(); //avanca antes de remover
            if (cur.line >= li && cur.line <= lf){
                list.remove(cur);
                removed.insertOrUpdate(cur.line, cur.instr);
            }
        }
        return removed.isEmpty() ? null : removed;
    }

    //busca no por numero de linha (para jnz)
    public Node<CodeLine> findNodeByLine(int line){
        Node<CodeLine> p = list.getHead();
        while (p != null){
            if (p.getDado().line == line) return p;
            p = p.getProx();
        }
        return null;
    }

    //imprime 20 por pagina
    public void printPaged(java.io.PrintStream out){
        Node<CodeLine> p = list.getHead();
        int c = 0;
        while (p != null){
            out.println(p.getDado().line + " " + p.getDado().instr);
            c++; if (c % 20 == 0) out.flush();
            p = p.getProx();
        }
    }

    //pra carrega arquivo no formato
    public void loadFromFile(File file) throws IOException {
        //limpa a lista
        while (!list.isEmpty()) list.pollFirst();

        Scanner sc = new Scanner(file);
        try {
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                if (s == null) break;
                s = s.trim();
                if (s.isEmpty()) continue;
                int sp = s.indexOf(' ');
                if (sp <= 0) throw new IOException("linha sem numero: " + s);
                int ln;
                try { ln = Integer.parseInt(s.substring(0, sp)); }
                catch (NumberFormatException e){ throw new IOException("numero de linha invalido: " + s); }
                String instr = s.substring(sp+1).trim();
                insertOrUpdate(ln, instr);
            }
        } finally {
            sc.close();
        }
    }

    //salva no formato "LINHA espaco INSTRUCAO"
    public void saveToFile(File file) throws IOException {
        PrintWriter pw = new PrintWriter(file);
        try {
            Node<CodeLine> p = list.getHead();
            while (p != null) {
                pw.println(p.getDado().line + " " + p.getDado().instr);
                p = p.getProx();
            }
        } finally {
            pw.close();
        }
    }
}
