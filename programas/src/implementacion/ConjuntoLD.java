package implementacion;
import tda.ConjuntoTDA;

public class ConjuntoLD implements ConjuntoTDA{
    Nodo c;
    public void inicializarConjunto(){
        c = null;
    }
    public boolean conjuntoVacio(){
        return (c == null);
    }
    public void agregar(int x){
        if (!this.pertenece(x)){
            Nodo aux = new Nodo();
            aux.info = x;
            aux.sig = c;
            c = aux;
        }
    }

    public int elegir(){
        return c.info;
    }

    public void sacar(int x){
        if(c!=null){
            if(c.info == x){
                c = c.sig;
            }else{
                Nodo aux = c;
                while(aux.sig!= null && aux.sig.info != x){
                    aux = aux.sig;
                }
                if (aux.sig != null){
                    aux.sig = aux.sig.sig;
                }
            }
        }
    }
    public boolean pertenece(int x){
        Nodo aux = c;
        while ((aux != null) && (aux.info != x)){
            aux = aux.sig;
        }
        return (aux!=null);
    } 
}
