package Connect5Game;

import java.util.ArrayList;
import java.util.Random;

import Connect5Game.Grille;
import Connect5Game.Joueur;
import Connect5Game.Position;


public class JoueurArtificiel implements Joueur {

    private final Random random = new Random();

    /**
     * Voici la fonction à modifier.
     * Évidemment, vous pouvez ajouter d'autres fonctions dans JoueurArtificiel.
     * Vous pouvez aussi ajouter d'autres classes, mais elles doivent être
     * ajoutées dans le package planeteH_2.ia.
     * Vous ne pouvez pas modifier les fichiers directement dans planeteH_2., car ils seront écrasés.
     * 
     * @param grille Grille reçu (état courrant). Il faut ajouter le prochain coup.
     * @param delais Délais de rélexion en temps réel.
     * @return Retourne le meilleur coup calculé.
     */

    @Override
    public Position getProchainCoup(Grille grille, int delais) {
        ArrayList<Integer> casesvides = new ArrayList<Integer>();
        ArrayList<Integer> pionsJ1 = new ArrayList<Integer>();
        ArrayList<Integer> pionsJ2 = new ArrayList<Integer>();
        int nbcol = grille.getData()[0].length;
        for(int l=0;l<grille.getData().length;l++){
            for(int c=0;c<nbcol;c++) {
                if(grille.getData()[l][c]==0){
                    casesvides.add(l*nbcol+c);
                } else if (grille.getData()[l][c]==1){
                    pionsJ1.add(l*nbcol+c);
                } else if (grille.getData()[l][c]==2){
                    pionsJ2.add(l*nbcol+c);
                }
            }
        }

        int choix = random.nextInt(casesvides.size());
        choix = casesvides.get(choix);
        return new Position(choix / nbcol, choix % nbcol);
    }
    private int getJoueurCourant(ArrayList j1, ArrayList j2){
        if(j1.size() > j2.size()){
            return 2;
        } else if(j1.size() == j2.size()){
            return 1;
        }
    }
    //TODO
    private int getPoints(ArrayList pions){
        ArrayList visites;
        int points = 0;
        for (int pion : pions){
            ArrayList voisins = getVoisins(pion, "ALL");
            for (int v : voisins){
                while (pions.contains(v)){
                    String direction = getDirection(pion, voisin);
                    v = getVoisins(v, direction);

                }
            }
        }
    }
    private ArrayList getAllVoisins(int case_c){
        ArrayList voisins;
        //TODO
        return voisins;
    }
    //TODO
    private int getVoisins(int case_c, String direction){
        switch(direction){
            case "NORD":
            case "SUD":
            case "EST":
            case "OUEST":
            case "NORD-OUEST":
            case "SUD-OUEST":
            case "NORD-EST":
            case "SUD-EST":
        }

    }
    private String getDirection(int source, int destination){
        //TODO
    }
 


}
