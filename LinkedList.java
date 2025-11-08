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

public class LinkedList<T> {
	private Node<T> head; // Ponteiro cabeça de Lista
	private int size; // Quantidade de Elementos da Lista Ligada
	
	// LinkedList(): Construtor da Lista Encadeada
	public LinkedList() {
		head = null;
		size = 0;
	}
	
	// isEmpty(): Verifica se a Lista está Vazia
	public boolean isEmpty() {
		return getHead() == null;
	}
	
	// isFull(): Verifica Se a Lista Está Cheia
	public boolean isFull() {
		Node<T> aux = new Node<T>();
		return aux == null;
	}
	
	// getSize(): Retorna o Tamanho da Lista
	public int getSize() {
		return size;
	}
	
	// getHead(): Retorna o "Node" Cabeça de Lista	
	public Node<T> getHead() {
		return head;
	}
	
	// get(int pos): Retorna o "Node" Que Se Encontra na Posição "pos" da Lista.	
	public Node<T> get(int pos) {
		if (isEmpty()) return null;
		if (pos <= 0 || pos > size) return null;
		int cont = 1;
		Node<T> pAnda = head;
		while (cont != pos){
			pAnda = pAnda.getProx();
			cont++;
		}
		return pAnda;
	}
	
    // insert(T id, int pos): Insere o Elemento "id" Na Posição "pos" Passada Como Parâmetro
	public boolean insert(T id, int pos) {
		Node<T> aux; 
		Node<T> pAnda; 
		Node<T> pAnt = null;  
		if (pos <= 0) return false; 
	    if (!isFull()){ 
	      aux = new Node<T>(id, null);
	      if (isEmpty()){ 
	    	  head = aux;
	    	 
	      } else if (pos >= size+1) {
	    		insertTail(id);
	    		size--;
	      } else {
	    	int cont = 1;
	        pAnda = head;  
	        while (pAnda.getProx() != null && cont != pos){
	           pAnt = pAnda;
	           pAnda = pAnda.getProx();
	           cont++;
	        }
        	aux.setProx(pAnda);
	        if (cont == 1) { 
	        	head = aux;
	        } else { 
	        	pAnt.setProx(aux);
	        }
	      }
	      size++;
		  return true; 
	    }
	    else return false; 
	};
	
	// insertHead(T id): Insere o "id" Passado como Parâmetro no Começo da Lista
	public boolean insertHead(T id){
		Node<T> aux; 
	    if (!isFull()){ 
	      aux = new Node<T>(id, null);
	      if (isEmpty()){
	        head = aux;
	      }else { 
	      	aux.setProx(head);
	      	head = aux;
	      }
    	  size++;
	      return true; 
	    }
	    else return false;   
	};
	
	// insertTail(T id): Insere o "id" Passado Como Parâmetro no Final da Lista
	public boolean insertTail(T id){
		Node<T> aux; 
		Node<T> pAnda; 
	    if (!isFull()){ 
	      aux = new Node<T>(id, null);
	      if (isEmpty()){ 
	        head = aux;
	      }else { 
	        pAnda = head;  
	        while (pAnda.getProx() != null)
	           pAnda = pAnda.getProx();
	        pAnda.setProx( aux );
	      }
    	  size++;
		  return true;  
	    }
	    else return false;  
	};
	
	// search(T id): Procura o elemento "id" Dentro da Lista
	// se "id" Não Existir ou Lista Vazia Retorna Null
	// Caso Contrário, Retorna o "Node"
	public Node<T> search(T id){
		Node<T> pAnda; 
	    if (isEmpty()) {
			return null; 
	    }else{
	      pAnda = head;
	      
	      while ((pAnda != null) && (pAnda.getDado().equals(id) != true))
	        pAnda = pAnda.getProx();
	      return pAnda; 
	    }
	}
	
	// remove(T id): Remove a Primeira Ocorrência do "id" na Lista
	public boolean remove(T id){
		Node<T> pAnda; 	
		Node<T> pAnt = null; 
	    if (isEmpty()) return false; 
	    else{ 
	      pAnda = head;
	      while ((pAnda != null) && (pAnda.getDado().equals(id) != true)){
	        pAnt = pAnda;
	        pAnda = pAnda.getProx();
	      }
	      if (pAnda == null) return false; 
	      else { 
	      	if ((head == pAnda)) {
			  head = pAnda.getProx();
		    } else{ 
		    	pAnt.setProx(pAnda.getProx());
			}
	      	pAnda = null;
	    	size--;
	      	return true;    
	      }
	    }
	}
	
	// pollFirst(): Remove e Retorna o Primeiro Elemento da Lista
	public T pollFirst(){
	    if (isEmpty()) return null; 
	    else{  
		  Node<T> pAux = head;
	      head = head.getProx();
    	  size--;
	      return pAux.getDado();
	    }
	}	
	
	// pollLast(): Remove e Retorna o Último Elemento da Lista
	public T pollLast(){
	    if (isEmpty()) return null; 
	    else{  
		  Node<T> pAnda = head, pAnt = null;
	      
	      while ((pAnda.getProx() != null)){
	        pAnt = pAnda;
	        pAnda = pAnda.getProx();
	      }  
	      pAnt.setProx(null);
    	  size--;
	      return pAnt.getDado();
	    }
	}
	
	// print(): Percorre a Lista e Imprime Todo o Seu Conteúdo
	public void print(){
		Node<T> pAnda; 
	    pAnda = head;
	    while (pAnda != null) {
	      System.out.println(pAnda.getDado());
	      pAnda = pAnda.getProx();
	    }
	}
	
	// clear(): Limpa a Lista Ligada Original
	public void clear(){
		Node<T> pAnt;
		Node<T> pAnda = head;  
		while(pAnda != null){
			pAnt = pAnda;  
			pAnda = pAnda.getProx();
			pAnt.setProx(null);
			pAnt = null;
		}
		size = 0;
		head = null; 	 
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		int qtde = 0;
		sb.append("\n[Lista]\n");
	
	    sb.append("L: [ ");
	    Node<T> pAnda = head;
	    while (pAnda != null) {
	      sb.append(pAnda.getDado()+" ");
	      qtde++;
	      pAnda = pAnda.getProx();
	    }
	    sb.append("]\n");
	    
	    sb.append("Qtde.: " + qtde);
	    sb.append("\n");
	    
	    return sb.toString();
	}
	
}

