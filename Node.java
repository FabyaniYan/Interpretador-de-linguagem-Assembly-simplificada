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

public class Node <T>{
	private T dado;
	private Node<T> prox;
	
	// Construtor Vazio
	
	public Node() {
		this(null, null);
	}
	
	// Construtor com Parâmtetros
	// Elemento da Lista, Ponteiro do Próximo Elemento
	public Node(T dado, Node<T>prox) {
		this.dado = dado;
		this.prox = prox;
	}
	
	// Obtém o Ponteiro Para o Próximo Nó (Elemento) da Lista
	public Node<T> getProx(){
		return prox;
	}
	
	// Obtém o Elemento da Lista de um Determinado Nó
	public T getDado(){
		return dado;
	}
	
	// Define o Endereço do Próximo Nó da Lista Ligada
	public void setProx(Node<T> prox) {
		this.prox = prox;
	}
	
	// Define o Elemento Passado como Parâmetro como Dado a ser Armazenado no Nó
	public void setDado(T dado) {
		this.dado = dado;
	}
	
}