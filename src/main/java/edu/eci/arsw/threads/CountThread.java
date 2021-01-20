/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

import java.util.ArrayList;
import java.lang.*;
/**
 *
 * @author hcadavid
 */



public class CountThread extends Thread{
	public ArrayList<Integer> range;
    public CountThread(ArrayList<Integer> r) {
    	range = r;
    }
    
    public void run() {
    	
    	for(int i = range.get(0); i <= range.get(1); i++) {
    		System.out.println(i);
    	}
    }
}
