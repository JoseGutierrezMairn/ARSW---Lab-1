/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

import java.util.ArrayList;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    
    public static void main(String a[]){
        CountThread first = new CountThread (new ArrayList<Integer>() {{ add(0); add(99);}} );
        CountThread second = new CountThread (new ArrayList<Integer>() {{ add(99); add(199);}} );
        CountThread third = new CountThread (new ArrayList<Integer>() {{ add(200); add(299);}} );
        first.run();
        second.run();
        third.run();
    }
    
}
