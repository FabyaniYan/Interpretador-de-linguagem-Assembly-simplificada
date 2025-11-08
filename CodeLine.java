//representa uma linha de codigo
public class CodeLine {
    public int line;
    public String instr;

 //construtor
    public CodeLine(int line, String instr){
        this.line = line;
        this.instr = instr;
    }

    @Override
    public String toString(){
        return line + " " + instr;
    }
}
