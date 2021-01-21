/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     * @throws InterruptedException 
     */
    public List<Integer> checkHost(String ipaddress, int n) throws InterruptedException{
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        
        int ocurrencesCount=0;
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        List<Seeker> seekers = new ArrayList<Seeker>();
        List<Integer> alives = new ArrayList<Integer>();
        int checkedListsCount=0;
        int segment = (int) Math.ceil(skds.getRegisteredServersCount()/n);
        int b = 0;
        int e = b + segment;
        for (int i = 0; i<n; i++) {
        	Seeker s = new Seeker(skds, e, b, BLACK_LIST_ALARM_COUNT, ipaddress);
        	s.start();
        	seekers.add(s);
        	b = e + 1;
        	e+=segment+1;
        }
        
        for (Seeker s : seekers) {
        	s.join();
        }
        for (Seeker s : seekers) {
        	checkedListsCount+= s.getCheckedListsCount();
        	ocurrencesCount+= s.getOccurrences();
        	for (Integer inte :  s.getListOccurrences()) {
        		 blackListOcurrences.add(inte);
        	}
        }
        
        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
