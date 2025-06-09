package implementacion;

import tdas.ColaStringTDA;

public class ColaString implements ColaStringTDA {

	class nodo{
		String valor;
		nodo siguiente;
	}
	
	private nodo primero;
	private nodo ultimo;
	
	@Override
	public void inicializarCola() {
		primero = null;
		ultimo = primero;
	}

	@Override
	public void acolar(String valor) {
		if(colaVacia()){
			primero = new nodo();
			primero.valor = valor.toLowerCase();
			ultimo = primero;
		}else{
			ultimo.siguiente = new nodo();
			ultimo.siguiente.valor = valor.toLowerCase();
			ultimo = ultimo.siguiente;
		}
	}

	@Override
	public void desacolar() {
		primero = primero.siguiente;
		if(primero == null){
			ultimo = null;
		}
	}

	@Override
	public String primero() {
		return primero.valor;
	}

	@Override
	public boolean colaVacia() {
		return primero == null;
	}

}
