package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public static void readFromFile(String filename)
	{
		int first = 0;		// zmienna tymczasowa dla 1-szej kolumny
		double second = 0.0;  // zmienna tymczasowa dla 2-ej kolumny
		//double third = 0.0;
		
		List<Integer> frscolumn = new ArrayList<Integer>();   // pierwsza kolumna
		List<Double> seccolumn = new ArrayList<Double>();   // druga kolumna
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = null;
			
			while((line = br.readLine()) != null) {
				// if(line.length() == 0)
				// {
				//	System.out.println();	// pusta linia w pliku, robie ENTER
				// }
					
				if(line.length() > 0)    // czytam tylko niepuste linie
				{			
				String tmp[] = line.split("\t");
				first = Integer.parseInt(tmp[0]);	// 1-sza kolumna
				frscolumn.add(first);
				second = Double.parseDouble(tmp[1]);	// 2-ga kolumna
				seccolumn.add(second);
				//third = Double.parseDouble(tmp[2]);    // 3-cia pomijamy, to beda nasze wyniki
				//System.out.println(first + "\t" + second + "  " + third);
				}
			}
			
			br.close();
			
		} 
		catch (Exception e) {
            System.err.println("Plik nie istnieje");
		}
		
		for(int i = 0; i < frscolumn.size(); i+=5)    // czytamy/modyfikujemy co 5-ta krotke
		{
			// frscolumn.set(i, frscolumn.get(i)*5);   przyklad modyfikowania
			System.out.println(frscolumn.get(i) + "  " + seccolumn.get(i));
		}
	}

	public static void main(String[] args) {

		Test.readFromFile("66_70_1E7_500_0_i-038bc89930887720c_r1_MER.txt");
		
	}

}
