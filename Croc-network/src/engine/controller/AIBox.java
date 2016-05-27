package engine.controller;


import java.io.File;
import java.util.ArrayList;

import engine.models.Pirate;

public class AIBox {
	Pirate p;
	public ArrayList<Integer> val;
	public AIBox(Pirate p_){
		p = p_;
		val = new ArrayList<Integer>();
	}

	public AIBox(Pirate p_, File f){
		p = p_;
		val = new ArrayList<Integer>();
		/**
		 * not yet implemented
		 */
	}
	public void GetValuationFromFile(){
		/**
		 * not yet implemented
		 */
	}
	
	public void randomValuation(){
		int size = p.availableCards.size();
		for(int i = 0; i < size; i++){
			val.add(1);
		}
	}
	
	public void increasingValuation(){
		int size = p.availableCards.size();
		for(int i = 0; i < size; i++){
			val.add(i);
		}
	}
}
