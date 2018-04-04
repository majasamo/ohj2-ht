package trekisteri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Työntekijat-luokka hallinnoi yksittäisiä työntekijöitä.
 * @author Marko Moilanen
 * @version 3.4.2018
 */
public class Tyontekijat implements Iterable<Tyontekija> {
    
    private int lkm;
    private Tyontekija[] alkiot = new Tyontekija[LKM_MAX];
    private static final int LKM_MAX = 5;
    
    private String perusnimi = "tyolaiset";
    
        
    /**
     * Lisää uuden työntekijän.
     * @param lisattava työntekijä, joka lisätään
     * @throws SailoException jos tietorakenne on täynnä
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
        if (this.lkm >= LKM_MAX) throw new SailoException("Tietorakenteeseen ei mahdu!"); // TODO: korvaa tämä.        
        this.alkiot[this.lkm] = lisattava;
        this.lkm++;
    }
        
    
    /**
     * Palauttaa työntekijöiden lukumäärän.
     * @return työntekijöiden lukumäärä
     */
    public int getLkm() {
        return this.lkm;
    }
    
    
    /**
     * Palauttaa i:nnen työntekijän.
     * TODO: tämä on rakennusteline. ... vai onko?
     * @param i luku,joka kertoo, kuinka mones työntekijä halutaan
     * @return i:s työntekijä
     * @throws IndexOutOfBoundsException jos i on sallitun alueen ulkopuolella 
     */
    public Tyontekija anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || this.lkm <= i) 
            throw new IndexOutOfBoundsException("Laiton indeksi " + i);
        return this.alkiot[i];
    }
    
    
    /**
     * Luetaan tiedostosta, jonka nimi on annettu aiemmin.
     * @throws SailoException jos tiedoston lukeminen ei onnistu 
     */
    public void lueTiedostosta() throws SailoException {
        this.lueTiedostosta(this.getTiedostonPerusnimi());
    }
    
    
    /** 
     * Lukee työntekijöiden tiedot tiedostosta.
     * @param tiedosto luettavan tiedoston nimi ilman tiedostopäätettä
     * @throws SailoException jos tiedoston lukeminen ei onnistu
     * TODO testit
     */
    public void lueTiedostosta(String tiedosto) throws SailoException {
        setTiedostonPerusnimi(tiedosto);
        try (Scanner lukija = new Scanner(new FileInputStream(new File(this.getTiedostonNimi())))) {
            
            while (lukija.hasNextLine()) {
                String rivi = lukija.nextLine();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Tyontekija tyontekija = new Tyontekija();
                tyontekija.parse(rivi);
                this.lisaa(tyontekija);
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + this.getTiedostonNimi() + " ei aukea.");
        }
    }
    
    
    /**
     * Asettaa tiedoston perusnimen (ilman tiedostopäätettä).
     * @param nimi asetettava nimi
     */
    public void setTiedostonPerusnimi(String nimi) {
        this.perusnimi = nimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen (ilman tiedostopäätettä).
     * @return tiedoston nimi
     */
    public String getTiedostonPerusnimi() {
        return this.perusnimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen (tiedostonpääte mukana).
     * @return tallennettavan tiedoston nimi
     */
    public String getTiedostonNimi() {
        return this.perusnimi + ".dat";
    }
    

    /**
     * Palauttaa iteraattorin, jolla voi käydä läpi tietorakenteeseen tallennetut
     * työntekijät.
     * @return iteraattori
     */
    @Override
    public Iterator<Tyontekija> iterator() {
        return new TyontekijatIter();
    }
    
    
    /**
     * Luokka työntekijöiden iteroimista varten.
     * @author Marko Moilanen
     * @version 2.4.2018
     * @example 
     * <pre name="test">
     * #THROWS SailoException
     * #THROWS NoSuchElementException
     * #import java.util.Iterator;
     * #import java.util.NoSuchElementException;
     * 
     *   Tyontekijat tyontekijat = new Tyontekijat();
     *   Tyontekija tyol1 = new Tyontekija(); tyol1.rekisteroi();
     *   Tyontekija tyol2 = new Tyontekija(); tyol2.rekisteroi();
     *   Tyontekija tyol3 = new Tyontekija(); tyol3.rekisteroi(); 
     *   tyontekijat.lisaa(tyol1);
     *   tyontekijat.lisaa(tyol2);
     *   tyontekijat.lisaa(tyol3);
     *   tyontekijat.lisaa(tyol2);

     *   // foreach-silmukka:
     *   StringBuilder idt = new StringBuilder();
     *   for (Tyontekija tyol : tyontekijat) {
     *     idt.append(tyol.getId());
     *   }
     *   idt.toString() === "" + tyol1.getId() + tyol2.getId() + tyol3.getId() + tyol2.getId();
     *   
     *   // Iteraattori:
     *   Iterator<Tyontekija> vipellin = tyontekijat.iterator();
     *   vipellin.next() === tyol1;
     *   vipellin.next() === tyol2;
     *   vipellin.next() === tyol3;
     *   vipellin.next() === tyol2;
     *   
     *   vipellin.next();  #THROWS NoSuchElementException
     * </pre>
     */
    public class TyontekijatIter implements Iterator<Tyontekija> {
        
        private int kohdalla = 0;
        
        
        /**
         * Palauttaa tiedon siitä, onko seuraavaa työntekijää.
         * @return true, jos seuraava työntekijä on olemassa
         */
        @Override
        public boolean hasNext() {
            return (this.kohdalla < getLkm());
        }

        
        /**
         * Palauttaa seuraavan työntekijän.
         * @return seuraava työntekijä
         * @thows NoSuchElementException jos seuraavaa työntekijää ei ole
         */
        @Override
        public Tyontekija next() {
            if (!hasNext()) throw new NoSuchElementException("Ei enää alkioita.");
            return anna(this.kohdalla++);
        }
    }
    
    
    /**
     * Pääohjelma testaamista varten.
     * @param args ei käytössä
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
            System.out.println("Työntekijä nro " + i);
            tyontekija.tulosta(System.out);
        }
    }
}
