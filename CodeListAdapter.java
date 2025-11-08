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

//adaptador: opera por numero de linha sobre a linkedlist<CodeLine>
import java.io.*;
import java.nio.charset.StandardCharsets;

public class CodeListAdapter {
    private final LinkedList<CodeLine> list = new LinkedList<>();

    public boolean isEmpty(){ return list.isEmpty(); }

    //no cabeca (menor linha)
    public Node<CodeLine> getHead(){ return list.getHead(); }

    //insere mantendo ordenacao por numero de linha ou atualiza se ja existir
    public boolean insertOrUpdate(int line, String instr){
        if (line < 0) throw new IllegalArgumentException("linha negativa");
        if (list.isEmpty()){
            list.insertHead(new CodeLine(line, instr));
            return true;
        }
        Node<CodeLine> p = list.getHead(), ant = null;
        int pos = 1;
        while (p != null && p.getDado().line < line){
            ant = p; p = p.getProx(); pos++;
        }
        if (p != null && p.getDado().line == line){
            p.getDado().instr = instr; //atualiza
            return false;
        }
        if (ant == null){
            list.insertHead(new CodeLine(line, instr));        //cabeca
        } else if (p == null){
            list.insertTail(new CodeLine(line, instr));        //fim
        } else {
            list.insert(new CodeLine(line, instr), pos);       //meio (antes de p)
        }
        return true;
    }

    //remove linha exata, retorna elemento removido ou null
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

    //remove intervalo [li, lf], devolve lista com removidos ou null
    public CodeListAdapter removeRange(int li, int lf){
        if (li > lf) return null;
        CodeListAdapter removed = new CodeListAdapter();
        Node<CodeLine> p = list.getHead();
        while (p != null){
            CodeLine cur = p.getDado();
            p = p.getProx(); // avanca antes de remover
            if (cur.line >= li && cur.line <= lf){
                list.remove(cur);
                removed.insertOrUpdate(cur.line, cur.instr);
            }
        }
        return removed.isEmpty() ? null : removed;
    }

    //busca no por numero de linha
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

    //carrega arquivo .ed1 no formato "LINHA espaco INSTRUCAO..."
    public void loadFromFile(File file) throws IOException {
        while (!list.isEmpty()) list.pollFirst(); // limpa
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String s;
            while ((s = br.readLine()) != null) {
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
        }
    }

    //salva no formato "LINHA espaco INSTRUCAO"
    public void saveToFile(File file) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            Node<CodeLine> p = list.getHead();
            while (p != null) {
                bw.write(p.getDado().line + " " + p.getDado().instr);
                bw.newLine();
                p = p.getProx();
            }
        }
    }
}
