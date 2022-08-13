package Esame;

import java.util.Random;

public class Esercizio12 {
    public static void main(String[] args) {
    int matricola = 890823;
    int seme = 570049;
    //Funzioni random
    Random rand = new Random();
    Random randMatricola = new Random(matricola);
    Random randSeme = new Random(seme);

    
    //Inizializzo il dizionario
    dizionario dict = new dizionario();
    
    //K sono il numero di elementi dentro il dizionario
    int K = rand.nextInt(16)+11;

    //If per controllare per controllare che sia dispari, aggiunto +1 se è pari
    if(K%2==0){
        K++;    
    }

    binarySearchTree array[] = new binarySearchTree[K];
    int chiave = 0;
    char valore = 0;
    boolean esci;

    /*
     * Popolo il dizionario con i valori randomici da 0 a 100 e da 0 a 26 + 'a', cosi vale un carattere in minuscolo
     * faccio un controllo con while per evitare di inserire dei doppioni, richiamo la funzione lookup di dizionario 
     * per controllare se è già presente
     */
    
    for(int i = 0; i < K; i++){
        esci = false;

        valore = (char)(randMatricola.nextInt(26) + 'a');
        while(!esci){
            chiave = randMatricola.nextInt(100);
            if(dict.lookup(chiave) == null){
                esci = true;
            } else {
                System.out.println(chiave + " già inserito");
            }
        }

        //binarySearchTree nodo = ;
        array[i] = new binarySearchTree(chiave, valore);
    }

    array = ordinaArray(array);
    //System.out.println("Lung:" + array.length);
    
    //System.out.println((array.length/2) + ":" + array[array.length/2].key );
    int index = array.length/2;
    //dict.stampa();
}

public static binarySearchTree[] ordinaArray(binarySearchTree[] array){
    binarySearchTree[] arrayOrdinato = new binarySearchTree[array.length];
    int min;
    int minId = 0;

    for(int i=0; i<array.length; i++){
        min = 101;
        for(int j=0; j<array.length; j++){
            if(array[j].key < min){
                min = array[j].key;
                minId = j;
            }
                
        }
       arrayOrdinato[i] = new binarySearchTree(array[minId].key, array[minId].info);
       array[minId].key = 101;
    }
   
    
    return arrayOrdinato;
}

}

class dizionario {
    binarySearchTree tree;

    public dizionario(){
        tree = null;
    }

    public void insert(int key, char info){
        //tree = binarySearchTree.insertChild(tree, key, info);
    }

    public binarySearchTree lookup(int key){
        return tree.lookupNode(tree, key);
    }

    public void stampa(){
        tree.dfsIn(tree);
    }

    public void aggiungiFigli(){
        binarySearchTree[] array = new binarySearchTree[tree.key];
        int index=array.length/2;
        for(int i=index; i<array.length; i+=index){
            if(array[i] != null){
                System.out.println("INDEX:" + index + "    i:" + i);
                tree.insertChild(tree, array[i].key, array[i].info);
                array[i] = null;
            }
        }
    }
}

class binarySearchTree {
    binarySearchTree parent;
    binarySearchTree rightChild;
    binarySearchTree leftChild;
    public int key;
    char info;
    boolean color;

    public binarySearchTree(int key, char info){
        this.key = key;
        this.info = info;
        parent = null;
        rightChild = null;
        leftChild = null;
        this.color = false;     //Setto a nero

    }

    //Getter
    public int getKey(){
        return key;
    }

    public int getValue(){
        return info;
    }


    //Inserisci un albero come figlio sinistro
    public void insertLeft(binarySearchTree tree){
        if(leftChild == null){
            this.leftChild = tree;
            tree.parent = this;
        }
    }

    //Inserisci un albero come figlio destro
    public void insertRight(binarySearchTree tree){
        if(rightChild == null){
            this.rightChild = tree;
            tree.parent = this;
        }
    }

    /*
     * Nella prima parte del metodo cerca il posto giusto in cui inserire il nuovo figlio
     * salvando nella variabile p il padre e in u i dati dell'albero che riceve in input,
     * esegue un controllo con il primo ciclo while dove esce se il nodo che stiamo guardando è null
     * oppure se il nodo ha la stessa chiave del nuovo nodo che dobbiamo aggiungere.
     * Alla fine ritorna nel caso l'albero fosse vuoto il primo nodo dell'albero oppure l'albero
     * alterato
     */
    public binarySearchTree insertChild(binarySearchTree tree, int key, char info){
        binarySearchTree p = null;
        binarySearchTree u = tree;
        while(u != null && u.key != key){
            p = u;
            if(key < u.key){
                u = u.leftChild;
            } else {
                u = u.rightChild;
            }
        }

        if(u != null && u.key == key){
            u.info = tree.info;
        } else {
            binarySearchTree newTree = new binarySearchTree(key, info);
            link(newTree, p, key);
            //balanceInsert(newTree);
            if(p == null) {
                tree = newTree;
            }
        }
        return tree;
    }

    /*
     * Metodo che serve per aggiungere il nuovo nodo alla fine, prima effettua un controllo
     * su che node non sia vuoto, nel caso non fosse vuoto assegna il parent
     * Poi controlla se il nodo sia più grande o più piccolo, per assegnarlo correttamente 
     * come figlio destro o figlio sinistro
     */
    public static void link(binarySearchTree node, binarySearchTree parent, int info){
        if(node != null){
            node.parent = parent;
        }

        if(parent != null){
            if(info < parent.key){
                parent.leftChild = node;
            } else {
                parent.rightChild = node;
            }
        }
    }

    public static void balanceInsert(binarySearchTree tree){
        tree.color = true;
        while(tree != null){
            binarySearchTree p = tree.parent;
            binarySearchTree n = null;
            binarySearchTree z = null;
            //controllo per l'esistenza del nonno
            if(p != null){
                n = p.parent;
            }
            
            //controllo per l'esistenza dello zio
            if(n != null){
                if(n.rightChild == p){
                    z = n.leftChild;
                } else {
                    z = n.rightChild;
                }
            }

            //Vari casi possibili per l'inserimento
            if(p == null){                          //Caso 1
                tree.color = false;
                tree = null;
            } else if (p.color == false){           //Caso 2
                tree = null;
            } else if (z != null && z.color == true){            //Caso 3
                    p.color = false;
                    z.color = false;
                    n.color = true;
                    tree = n;
            }
            else {
                if(tree == p.rightChild && p == n.leftChild){           //Caso 4
                    rotateLeft(p);
                    tree = p;
                } else if(tree == p.leftChild && p == n.rightChild){    //Caso 4.b
                    rotateRight(p); 
                    tree = p;
                } else {
                    if(tree == p.leftChild && p == n.leftChild){            //Caso 5
                        rotateRight(n);
                    } else if(tree == p.rightChild && p == n.rightChild){   //Caso 5.b
                        rotateLeft(n);
                    }
                    p.color = false;
                    n.color = true;
                    tree = null;
                }
            }
        }
    }

    /*
     * Metodo ricorsivo per controllare la presenta di un nodo con chiave key 
     */
    public static binarySearchTree lookupNode(binarySearchTree tree, int key){
        if(tree == null || tree.key == key){
            return tree;
        } else if (key < tree.key){
            return lookupNode(tree.leftChild, key);
        } else {
            return lookupNode(tree.rightChild, key);
        }
    }


    /*
     * Metodo per la ricerca in profondita con stampa IN, quindi controlla prima i figli sinistri
     * poi stampa poi controlla tutti i nodi
     */
    public void dfsIn(binarySearchTree tree){
        if(tree != null){
            System.out.println("Nodo: "+tree.key+" Info: "+tree.info);
            dfsIn(tree.leftChild);
            dfsIn(tree.rightChild);
        }
    }
    
    public static binarySearchTree rotateLeft(binarySearchTree tree){
        binarySearchTree y = tree.rightChild;
        binarySearchTree p = tree.parent;
        tree.rightChild = y.leftChild;
        if(y.leftChild != null){
            y.leftChild.parent = tree;
        }
        y.leftChild = tree;
        tree.parent = y;
        y.parent = p;
        if(p != null){
            if(p.leftChild == tree){
                p.leftChild = y;
            } else {
                p.rightChild = y;
            }
        }
        return y;
    }


    public static binarySearchTree rotateRight(binarySearchTree tree){
        binarySearchTree y = tree.leftChild;
        binarySearchTree p = tree.parent;
        tree.leftChild = y.rightChild;
        if(y.rightChild != null){
            y.rightChild.parent = tree;
        }
        y.rightChild = tree;
        tree.parent = y;
        y.parent = p;
        if(p != null){
            if(p.leftChild == tree){
                p.leftChild = y;
            } else {
                p.rightChild = y;
            }
        }
        return y;
    }
}
