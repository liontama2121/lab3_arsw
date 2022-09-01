package edu.eci.arsw.blacklistvalidator;
import java.util.ArrayList;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

/**
 *
 * @author hcadavid
 */
public class BlackListThread extends Thread{
    private static final int LIMIT_BLACK_LIST=5;
    private int Limit_INF;
    private int Limit_SUP;
    private String Incoming_Host;
    HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
    ArrayList<Integer> blacklisted=new ArrayList<>();
    private int revisedBlackListsCount;
    private int blacklistedCount;

    /**
     * Constructor que inicializa ciertas variables
     * @param Limit_INF Parametro el cual indica el limite inferior a revisar entre las listas negras
     * @param Limit_SUP Parametro el cual indica el limite superior a revisar entre las listas negras
     * @param Incoming_Host Es el Host que se va a buscar entre las listas del rango
     */
    public BlackListThread(int Limit_INF,int Limit_SUP,String Incoming_Host) {
        this.Limit_INF=Limit_INF;
        this.Limit_SUP=Limit_SUP;
        this.Incoming_Host=Incoming_Host;
        this.revisedBlackListsCount = 0;
        this.blacklistedCount = 0;
    }

    /**
     * Es el metodo que se ejecuta cada vez que un Thread se corre, revisando si el Host indicado ser encuentra entre
     * las Black Lists que va a revisar
     */
    public void run() {

        for (int i=Limit_INF;i<Limit_SUP && blacklistedCount<LIMIT_BLACK_LIST && !this.isInterrupted();i++){
            revisedBlackListsCount++;
            if (skds.isInBlackListServer(i, Incoming_Host)) {
                blacklisted.add(i);
                blacklistedCount++;
            }
        }
    }

    /**
     * Retorna el numero de las listas en las cuales se encontro el hos
     * @return Lista de enteros que representan el numero de la Black List en la que se encontro el host consultado
     */
    public ArrayList<Integer> getBlacklisted() {
        return blacklisted;
    }

    /**
     * Retorna la cantidad de black lists que fueron revisadas
     * @return entero que representa la cantidad de listas revisadas en el thread
     */
    public int getRevisedBlackListsCount() {
        return revisedBlackListsCount;
    }

    /**
     * Retorna la cantidad de ocurrencias encontradas entre el host y las black lists que fueron revisadas
     * @return entero que representa la ocurrencias encontradas en el thread
     */
    public int getBlacklistedCount() {
        return blacklistedCount;
    }
}