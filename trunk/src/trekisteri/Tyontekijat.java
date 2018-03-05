package trekisteri;

/**
 * Ty�ntekijat-luokka pit�� huolta ty�ntekij�iden poistamisesta, lis��misest�,
 * lajittelusta ja niin edelleen. 
 * @author Marko Moilanen
 * @version 5.3.2018
 */
public class Tyontekijat {
    
    private int lkm;
    private Tyontekija[] alkiot = new Tyontekija[LKM_MAX];
    private static final int LKM_MAX = 5;
    
    
    /**
     * Lis�� uuden ty�ntekij�n.
     * @param lisattava ty�ntekij�, joka lis�t��n
     * @throws SailoException jos tietorakenne on t�ynn�
     * @example
     * <pre name="test">
     *   #THROWS SailoException
     *   Tyontekijat tyontekijat = new Tyontekijat();
     *   Tyontekija virtanen1 = new Tyontekija();
     *   Tyontekija virtanen2 = new Tyontekija();
     *   tyontekijat.getLkm() === 0;
     *   tyontekijat.lisaa(virtanen1); tyontekijat.getLkm() === 1;
     *   tyontekijat.lisaa(virtanen2); tyontekijat.getLkm() === 2;
     *   tyontekijat.lisaa(virtanen1); tyontekijat.getLkm() === 3;
     *   tyontekijat.anna(0) === virtanen1;
     *   tyontekijat.anna(1) === virtanen2;
     *   tyontekijat.anna(2) === virtanen1;
     *   (tyontekijat.anna(1) == virtanen1) === false;
     *   (tyontekijat.anna(1) == virtanen2) === true;
     *   tyontekijat.anna(3) === virtanen1; #THROWS IndexOutOfBoundsException
     *   tyontekijat.lisaa(virtanen1); tyontekijat.getLkm() === 4;
     *   tyontekijat.lisaa(virtanen1); tyontekijat.getLkm() === 5;
     *   tyontekijat.lisaa(virtanen1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Tyontekija lisattava) throws SailoException {
        if (this.lkm >= LKM_MAX) throw new SailoException("Tietorakenteeseen ei mahdu!"); // TODO: korvaa t�m�.        
        this.alkiot[this.lkm] = lisattava;
        this.lkm++;
    }

    
    /**
     * Palauttaa ty�ntekij�iden lukum��r�n.
     * @return ty�ntekij�iden lukum��r�
     */
    public int getLkm() {
        return this.lkm;
    }
    
    
    /**
     * Palauttaa i:nnen ty�ntekij�n.
     * TODO: t�m� on rakennusteline.
     * @param i luku,joka kertoo, kuinka mones ty�ntekij� halutaan
     * @return i:s ty�ntekij�
     * @throws IndexOutOfBoundsException jos i on sallitun alueen ulkopuolella 
     */
    public Tyontekija anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || this.lkm <= i) 
            throw new IndexOutOfBoundsException("Laiton indeksi " + i);
        return this.alkiot[i];
    }
    
    
    /**
     * P��ohjelma testaamista varten.
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Tyontekijat tyontekijat = new Tyontekijat();
        
        Tyontekija virtanen = new Tyontekija();
        virtanen.rekisteroi();
        virtanen.taytaTiedot();
        
        Tyontekija virtanen2 = new Tyontekija();
        virtanen2.rekisteroi();
        virtanen2.taytaTiedot();
               
        try {
            tyontekijat.lisaa(virtanen);
            tyontekijat.lisaa(virtanen2);
            tyontekijat.lisaa(virtanen2);
            tyontekijat.lisaa(virtanen2);
            tyontekijat.lisaa(virtanen2);
            tyontekijat.lisaa(virtanen2);
        } catch (SailoException e) {
            e.printStackTrace();
        }        
        
        for (int i = 0; i < tyontekijat.getLkm(); i++) {
            Tyontekija tyontekija = tyontekijat.anna(i);
            System.out.println("Ty�ntekij� nro " + i);
            tyontekija.tulosta(System.out);
        }
    }
}
