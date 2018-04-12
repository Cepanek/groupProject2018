package main.Tools;

/**
 * Otwieranie plików
 *
 * @TODO
 * - otwórz plik
 * - zrzut danych do listy obiektów typu DataFrame
 */
public class OpenFile {
	
	private Double[] oryginalne;
    private Double[] bessel60;
    private Double[] bessel80;
    
    public void wczytaj(String sciezka) throws FileNotFoundException, IOException
    {
        BufferedReader plik;
        
        plik = new BufferedReader(new FileReader(sciezka));
        plik.readLine();    // pomijam zawsze 1-szy wiersz
        oryginalne = konwertujWierszNaTablice(plik);    // 2-gi wiersz
        bessel60 = konwertujWierszNaTablice(plik);    // 3-ci wiersz
        bessel80 = konwertujWierszNaTablice(plik);    // 4-ty wiersz
    }
    
    public Double[] pobierzOryginalne() { return oryginalne; }
    public Double[] pobierzBessel60() { return bessel60; }
    public Double[] pobierzBessel80() { return bessel80; }
    
    private Double[] konwertujWierszNaTablice(BufferedReader plik) throws IOException
    {
        ArrayList<Double> lista;
        Double[] tablica;
        Double wartosc;
        String linia;
        String[] eksplozja;
        
        linia = plik.readLine();
        eksplozja = linia.split("\\s+", -1);
        lista = new ArrayList<>();
      
        for (String tekst : eksplozja)
        {
            if (!tekst.equals(""))
            {
                wartosc = Double.parseDouble(tekst);    // pojedyncza wartosc
                lista.add(wartosc);    // dodaje do listy
            }
        }
        
        tablica = new Double[lista.size()];
        
        return lista.toArray(tablica);
    }
	
}
