//representa uma linha de codigo
public class CodeLine {
    
    //numero da linha no programa
    public int line;
    
    //instrucao correspondente a essa linha
    public String instr;

    //construtor
    //recebe o numero da linha e o texto da instrucao
    public CodeLine(int line, String instr){
        this.line = line;
        this.instr = instr;
    }

    //retorna a representacao textual da linha para exibicao
    @Override
    public String toString(){
        return line + " " + instr;
    }
}
