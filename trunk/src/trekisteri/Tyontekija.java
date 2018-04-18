package trekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Työntekijä tietää omat tietonsa ja id-numeronsa (eri kuin henkilönumero).
 * @author Marko Moilanen
 * @version 18.4.2018
 */
public class Tyontekija implements Cloneable, Comparable<Tyontekija> {
    
    private int tyolainenId;
    private String nimi = "";
    private int hlonumero;
    private int aloitusvuosi;
    private String koulutus = "";
    private String lisatietoja = "";
    
    private static int seuraavaId = 1;
    
    
    /**
     * Tarkastaa henkilönumeron oikeellisuuden.
     * @param hlonro tarkastettava henkilönumero
     * @return true, jos henkilönumero on oikea
     * @example
     * <pre name="test">
     *   tarkastaHlonro(5) === false;
     *   tarkastaHlonro(-1000) === false;
     *   tarkastaHlonro(1000) === true;
     *   tarkastaHlonro(999) === false;
     *   tarkastaHlonro(1001) === true;
     *   tarkastaHlonro(-1001) === false;
     *   tarkastaHlonro(7508) === true;
     *   tarkastaHlonro(-7508) === false;
     *   tarkastaHlonro(9999) === true;
     *   tarkastaHlonro(-9999) === false;
     *   tarkastaHlonro(10000) === false;
     *   tarkastaHlonro(-10000) === false;
     *   tarkastaHlonro(10001) === false;
     *   tarkastaHlonro(-10001) === false;
     * </pre>
     */
    public static boolean tarkastaHlonro(int hlonro) {
        return (1000 <= hlonro && hlonro <= 9999);
    }
        
         
    /**
     * Tulostaa työntekijän tiedot.
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.tyolainenId + " " + this.nimi + " " + this.hlonumero);
        out.println("aloittanut " + this.aloitusvuosi);
        out.println("koulutus: " + this.koulutus);
        out.println("lisätietoja: " + this.lisatietoja);
    }
    
    
    /**
     * Tulostaa työntekijän tiedot.
     * @param os tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Antaa työntekijälle id:n.
     * @example
     * <pre name="test">
     *   Tyontekija virtanen = new Tyontekija();
     *   virtanen.getId() === 0;
     *   virtanen.rekisteroi();
     *   Tyontekija mottonen = new Tyontekija();
     *   mottonen.getId() === 0;
     *   mottonen.rekisteroi();
     *   
     *   // Peräkkäin rekisteröityjen työntekijöiden id:t ovat peräkkäisiä.
     *   int idV = virtanen.getId();
     *   int idM = mottonen.getId();
     *   idV === idM - 1;
     *   
     *   // Jo rekisteröidyn työntekijän id ei muutu.
     *   virtanen.rekisteroi();
     *   virtanen.getId() === idV;
     * </pre>
     */
    public void rekisteroi() {
        if (this.getId() != 0) return;  // Jos id on jo annettu, ei tehdä mitään.
        this.tyolainenId = seuraavaId;
        seuraavaId++;
    }
    
    
    /**
     * Palauttaa työntekijän id:n.
     * @return työntekijän id-numero
     */
    public int getId() {
        return this.tyolainenId;
    }

    
    /**
     * Asettaa työntekijän id-numeron. Samalla varmistetaan, että
     * seuraavaksi annettava id on ajan tasalla.
     * @param numero
     */
    private void setId(int numero) {
        this.tyolainenId = numero;
        if (this.tyolainenId >= seuraavaId) seuraavaId = this.tyolainenId + 1;
    }
    
    
    /**
     * Selvittää ja tallentaa työntekijän tiedot tolppamerkein erotellusta merkkijonosta.
     * @param tiedot työntekijän tiedot tolppamerkein erotettuna
     */
    public void parse(String tiedot) {
        StringBuilder rivi = new StringBuilder(tiedot);
        
        // Luetaan tiedot. String-muuttujista ylimääräiset välilyönnit pois,
        // int-muuttujien oletusarvoiksi tämänhetkinen arvo (eli luultavasti 0).
        this.setId(Mjonot.erota(rivi, '|', this.tyolainenId));
        this.nimi = Mjonot.erota(rivi, '|').trim();
        this.hlonumero = Mjonot.erota(rivi, '|', this.hlonumero);
        this.aloitusvuosi = Mjonot.erota(rivi, '|', this.aloitusvuosi);
        this.koulutus = Mjonot.erota(rivi, '|').trim();
        this.lisatietoja = Mjonot.erota(rivi, '|').trim();
    }
    
    
    /**
     * Palauttaa työntekijän tiedot tolppamerkein eroteltuna merkkijonona.
     * @return työntekijän tiedot
     * @example 
     * <pre name="test">
     *   Tyontekija virtanen = new Tyontekija();
     *   virtanen.parse(" 18 | Virtanen Matti |");
     *   virtanen.toString() === "18|Virtanen Matti|0|0||";
     *   
     *   Tyontekija mottonen = new Tyontekija();
     *   mottonen.parse("71  | Mottonen Mikko | 2012 | 1995  | ei   ole | ei ole");
     *   mottonen.toString() === "71|Mottonen Mikko|2012|1995|ei   ole|ei ole"; 
     * </pre>
     */
    @Override
    public String toString() {
        return "" + this.getId() + "|"
                  + this.getNimi() + "|"
                  + this.hlonumero + "|"
                  + this.aloitusvuosi + "|"
                  + this.koulutus + "|"
                  + this.lisatietoja;
    }
    
    
    /**
     * Palauttaa työntekijän kloonin.
     * @return työntekijän klooni
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     *   Tyontekija tyol = new Tyontekija();
     *   tyol.parse("21 | Virtanen Petteri  | 2013 | ei ole |  ");
     *   Tyontekija kopio = tyol.clone();
     *   
     *   tyol.toString() === kopio.toString();
     *   kopio.parse("24 | Saarinen Sanna | 2011 | laitoshuoltaja 2014 | ");
     *   tyol.toString().equals(kopio.toString()) === false;
     * </pre>
     */
    @Override
    public Tyontekija clone() throws CloneNotSupportedException {
        return (Tyontekija) super.clone();
    }

    
    /**
     * @example
     * <pre name="test">
     *   Tyontekija t1 = new Tyontekija(); t1.parse("1|Virtanen");
     *   Tyontekija t2 = new Tyontekija(); t2.parse("2|Makkonen");
     *   Tyontekija t3 = new Tyontekija(); t3.parse("3|Virtanen");
     *   
     *   t1.compareTo(t1) === 0;
     *   (t1.compareTo(t2) > 0) === true;
     *   (t2.compareTo(t1) < 0) === true;
     *   (t2.compareTo(t3) < 0) === true;
     *   t1.compareTo(t3) === 0;
     * </pre>
     */
    @Override
    public int compareTo(Tyontekija verrattava) {
        return this.getNimi().compareTo(verrattava.getNimi());
    }
    
    
    // Saanti- ja muokkausmetodit käyttöliittymässä näytettäville attribuuteille. 
    //********************************************************************************
    
    
    /**
     * Palauttaa työntekijän nimen.
     * @return työntekijän nimi merkkijonona
     */
    public String getNimi() {
        return this.nimi;
    }

    
    /**
     * Palauttaa työntekijän henkilönumeron.
     * @return työntekijän henkilönumero
     */
    public int getHlonumero() {
        return this.hlonumero;
    }
    
    
    /**
     * Palauttaa työntekijän aloitusvuoden.
     * @return työntekijän aloitusvuosi
     */
    public int getAloitusvuosi() {
        return this.aloitusvuosi;
    }
    
    
    /**
     * Palauttaa tiedot työntekijän koulutuksesta.
     * @return työntekijän koulutustiedot
     */
    public String getKoulutus() {
        return this.koulutus;
    }
    
    
    /**
     * Palauttaa muut mahdolliset työntekijää koskevat tiedot.
     * @return lisätietoja-kentän sisältö
     */
    public String getLisatietoja() {
        return this.lisatietoja;
    }
    
        
    /**
     * Asettaa työntekijän nimen.
     * @param uusiNimi työntekijälle annettava nimi
     * @return virheilmoitus. Jos virhettä ei ole, palautetaan null.
     * @example
     * <pre name="test">
     *   Tyontekija t = new Tyontekija();
     *   t.setNimi("   ") === "pakollinen tieto";
     *   t.setNimi("") === "pakollinen tieto";
     *   t.setNimi("   Jake") === null;
     *   t.getNimi() === "Jake";
     * </pre>
     */
    public String setNimi(String uusiNimi) {
        String syote = uusiNimi.trim();
        
        if (syote.equals(""))
            return "pakollinen tieto";
        this.nimi = syote;
        return null;
    }
    
    
    /**
     * Asettaa työntekijän henkilönumeron.
     * @param uusiNumero työntekijälle annettava henkilönumero
     * @return virheilmoitus. Jos virhettä ei ole, palautetaan null.
     * @example
     * <pre name="test">
     *   Tyontekija t = new Tyontekija();
     *   t.setHlonumero("  ") === "pakollinen tieto";
     *   t.setHlonumero("") === "pakollinen tieto";
     *   t.setHlonumero("asdf") === "ei ole kokonaisluku";
     *   t.setHlonumero("1154.8 ") === "ei ole kokonaisluku";
     *   t.setHlonumero("  2241") === null;
     *   t.getHlonumero() === 2241;
     * </pre>
     */
    public String setHlonumero(String uusiNumero) {
        String syote = uusiNumero.trim();
        
        if (syote.length() == 0)
            return "pakollinen tieto";
        
        int uusi;
        try {
            uusi = Integer.parseInt(syote);
        } catch (NumberFormatException e) {
            return "ei ole kokonaisluku";
        }
        
        if (!tarkastaHlonro(uusi))
            return "numeron pitää olla nelinumeroinen";
        this.hlonumero = uusi;
        return null;
    }
    
    
    /**
     * Asettaa työntekijän aloitusvuoden.
     * @param uusiAloitusvuosi työntekijälle annettava aloitusvuosi
     * @return virheilmoitus. Jos virhettä ei ole, palautetaan null.
     * @example
     * <pre name="test">
     *   Tyontekija t = new Tyontekija();
     *   t.setAloitusvuosi("") === "pakollinen tieto";
     *   t.setAloitusvuosi("  ") === "pakollinen tieto";
     *   t.setAloitusvuosi("luku") === "ei ole kokonaisluku";
     *   t.setAloitusvuosi(" 1995 ") === null;
     *   t.getAloitusvuosi() === 1995;
     * </pre>
     */
    public String setAloitusvuosi(String uusiAloitusvuosi) {
        String syote = uusiAloitusvuosi.trim();
        
        if (syote.length() == 0) 
            return "pakollinen tieto";
        
        int uusi;
        try {
            uusi = Integer.parseInt(syote);
        } catch (NumberFormatException e) {
            return "ei ole kokonaisluku";
        }        
        
        this.aloitusvuosi = uusi;
        return null;
    }

    
    /**
     * Asettaa työntekijän koulutustiedot.
     * @param uusiKoulutus työntekijän koulutustiedot
     * @return virheilmoitus. Jos virhettä ei ole, palautetaan null.
     * @example
     * <pre name="test">
     *   Tyontekija t = new Tyontekija();
     *   t.setKoulutus("") === null;
     *   t.getKoulutus() === "";
     *   
     *   t.setKoulutus("  ") === null;
     *   t.getKoulutus() === "";
     *   
     *   t.setKoulutus("   joo ") === null;
     *   t.getKoulutus() === "joo";
     * </pre>
     */
    public String setKoulutus(String uusiKoulutus) {
        this.koulutus = uusiKoulutus.trim();
        return null;
    }

    
    /**
     * Asettaa muut mahdolliset työntekijää koskevat tiedot.
     * @param uusiLisatietoja lisätietoja-kentän teksti
     * @return virheilmoitus. Jos virhettä ei ole, palautetaan null.
     * @example
     * <pre name="test">
     *   Tyontekija t = new Tyontekija();
     *   t.setLisatietoja("  ") === null;
     *   t.getLisatietoja() === "";
     *   
     *   t.setLisatietoja("  vain viikonloput  ") === null;
     *   t.getLisatietoja() === "vain viikonloput";
     * </pre>
     */
    public String setLisatietoja(String uusiLisatietoja) {
        this.lisatietoja = uusiLisatietoja.trim();
        return null;
    }
    
            
    // Viivan yläpuolella saanti- ja muokkausmetodit käyttöliittymässä näytettäville attribuuteille.
    //********************************************************************************    
}
