package trekisteri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * KohteenTekijat-luokka pit�� yll� listaa kohteen tekij�ist�.
 * @author Marko Moilanen
 * @version 19.3.2018
 */
public class KohteenTekijat {
    
    private Collection<KohteenTekija> alkiot = new LinkedList<KohteenTekija>();
    
    
    /**
     * Palauttaa listan tyontekij�lle kuuluvien kohdeiden id-numeroista. 
     * @param tyolainenId sen ty�ntekij�n id, jonka kohteita etsit��n
     * @return lista ty�ntekij�lle kuuluvien kohteiden id-numeroista
     * TODO testit
     */
    public List<Integer> getKohdeIdt(int tyolainenId) {
        List<Integer> loydetyt = new ArrayList<Integer>();
        for (KohteenTekija tekija : this.alkiot) {
            if (tekija.getTyolainenId() == tyolainenId) loydetyt.add(tekija.getKohdeId());
        }
        
        return loydetyt;
    }
    
    
    /**
     * Lis�� luetteloon uuden kohteen tekij�n.
     * @param tyolainenId lis�tt�v�nty�ntekij�n Id-numero
     * @param kohdeId lis�tt�v�nkohteen Id-numero
     */
    public void lisaa(int tyolainenId, int kohdeId) {
        KohteenTekija lisattava = new KohteenTekija(tyolainenId, kohdeId);
        this.alkiot.add(lisattava);
    }

    
    /**
     * P��ohjelma testaamista varten.
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Kohde kohde1 = new Kohde();
        kohde1.taytaTiedot();
        kohde1.rekisteroi();
        
        Kohde kohde2 = new Kohde();
        kohde2.taytaTiedot();
        kohde2.rekisteroi();
        
        Kohde kohde3 = new Kohde();
        kohde3.taytaTiedot();
        kohde3.rekisteroi();
        
        Tyontekija tyol1 = new Tyontekija();
        tyol1.rekisteroi();
        
        Tyontekija tyol2 = new Tyontekija();
        tyol2.rekisteroi();
        
        KohteenTekijat tekijat = new KohteenTekijat(); // Ty�ntekij� - kohde
        tekijat.lisaa(tyol1.getId(), kohde1.getId());  // 1 - 1
        tekijat.lisaa(tyol1.getId(), kohde2.getId());  // 1 - 2
        tekijat.lisaa(tyol1.getId(), kohde3.getId());  // 1 - 3
        tekijat.lisaa(tyol2.getId(), kohde1.getId());  // 2 - 1        
        
        List<Integer> ykkosenKohteet = tekijat.getKohdeIdt(tyol1.getId());        
        List<Integer> kakkosenKohteet = tekijat.getKohdeIdt(tyol2.getId());
        
        for (int id : ykkosenKohteet) {
            System.out.println(id);
        }
        System.out.println("-------");        
        for (int id : kakkosenKohteet) {
            System.out.println(id);
        }
        
    }
}
