package main.Statistics;

import java.util.ArrayList;
import java.util.List;

/*
 * 
 * Klasa - kontener pomocniczy do przechowywania i zliczania wartoœci false/true dla poszczególnych picków.
 * 
 */

public class Results {
	
	//listy do danych oryginalnych i filtrów
	
	private List<Boolean> hitOrg;
	private List<Boolean> hit60;
	private List<Boolean> hit80;
    
    /*  Konstruktor */
	
	public Results() {
		
		hitOrg = new ArrayList<Boolean>(); 
		hit60 = new ArrayList<Boolean>();
		hit80 = new ArrayList<Boolean>();
	}

	/*  Dodawanie trafieñ */
	
	public void AddData(Boolean hOrg, Boolean h60, Boolean h80) {
		hitOrg.add(hOrg);
		hit60.add(h60);
		hit80.add(h80);
	}
	
	//niewykorzystana
	
	public void Pokaz()
	{
		System.out.println("Rozmiar listy: "+hitOrg.size());
		for(int i=0;i<hitOrg.size(); i++)
		{
			System.out.println(hitOrg.get(i));
		}
	}
	
	/* narzedzie do zliczania trafieñ - wartoœci True w liœcie - */
	
	public Integer trueCounter(List<Boolean> list)
	{
		int temp=0;
		for (int i=0;i<list.size();i++)
		{
			if (list.get(i)==true)
			{
				temp++;
			}
		}
		return temp;
	}
	
	
		
	/*
	 * 
	 * Gettery, Settery
	 * 
	 */

	public List<Boolean> getHitOrg() {
		return hitOrg;
	}

	public void setHitOrg(List<Boolean> hitOrg) {
		this.hitOrg = hitOrg;
	}

	public List<Boolean> getHit60() {
		return hit60;
	}

	public void setHit60(List<Boolean> hit60) {
		this.hit60 = hit60;
	}

	public List<Boolean> getHit80() {
		return hit80;
	}

	public void setHit80(List<Boolean> hit80) {
		this.hit80 = hit80;
	}

}
