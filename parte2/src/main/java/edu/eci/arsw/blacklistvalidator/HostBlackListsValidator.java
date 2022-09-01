/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que realiza la descomposición del problema entre los hilos indicados y genera la respuesta al problema
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    private boolean isALive=true;

    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress,int N) throws InterruptedException{
        ArrayList<Integer> blacklisted=new ArrayList<>();
        ArrayList<BlackListThread> threads = new ArrayList<BlackListThread>();
        int blacklistedCount=0;
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        int count = skds.getRegisteredServersCount()/N;
        int mod = skds.getRegisteredServersCount()%N;
        for (int i = 0;i<N;i++) {
            if(i == N-1) {
                BlackListThread h = new BlackListThread((i*count),(i*count) + count + mod ,ipaddress);
                threads.add(h);
                h.start();
            }else {
                BlackListThread h = new BlackListThread((i*count),(i*count)+count,ipaddress);
                threads.add(h);
                h.start();
            }
        }
        int checkedListsCount=0;
        while (blacklistedCount <BLACK_LIST_ALARM_COUNT && isALive ) {

            isALive=false;
            checkedListsCount=0;
            blacklistedCount=0;
            blacklisted.clear();
            for (BlackListThread thread : threads) {
                checkedListsCount += thread.getRevisedBlackListsCount();
                blacklistedCount += thread.getBlacklistedCount();
                blacklisted.addAll(thread.getBlacklisted());
                if (thread.isAlive()) {
                    isALive=true;
                }
            }
        }


        if (blacklistedCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});

        return blacklisted;
    }


    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());



}
