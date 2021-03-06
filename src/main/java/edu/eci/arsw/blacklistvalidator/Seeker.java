package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class Seeker extends Thread {
	
	private int occurrences;
	private int limit;
	private int alarm;
	private String ipaddress;
	private HostBlacklistsDataSourceFacade skds;
	private LinkedList<Integer> blackListOccurrences;
	private Integer checkedListsCount;
	private int begin;
	
	public Seeker(HostBlacklistsDataSourceFacade skds, int limit, int begin, int alarm, String ipaddress) {
		this.skds = skds;
		occurrences = 0;
		this.limit = limit;
		this.alarm = alarm;
		this.ipaddress = ipaddress;
		blackListOccurrences = new LinkedList<>();
		this.begin = begin;
		checkedListsCount = 0;
	}
	
	public void run() {
		for (int i=begin;i<limit ;i++){
            checkedListsCount+=1;
            if (skds.isInBlackListServer(i, ipaddress)) {
            	blackListOccurrences.add(i);            	
                occurrences+=1;
            }
        }		
	}
	public int getCheckedListsCount() {
		return checkedListsCount;
	}
	public LinkedList<Integer> getListOccurrences(){
		return blackListOccurrences;
	}
	public Integer getOccurrences() {
		
		return occurrences;
	}
}
