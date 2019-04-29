package Connect5Game;

import java.util.ArrayList;
import java.util.Random;


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
        eval(Grille);

        int choix = random.nextInt(casesvides.size());
        choix = casesvides.get(choix);
        return new Position(choix / nbcol, choix % nbcol);

        private int getJoueurCourant(ArrayList j1, ArrayList j2){
            if(j1.size() > j2.size()){
                return 1;
            } else if(j1.size() == j2.size()){
                return 0;
            }
        }


        private int eval(Grille grille){
        int pointsJ1 = 0;
        // Parcours des colonnes
        for(int l=0;l<grille.getData().length;l++){
            for(int c=0;c<grille.getData()[0].length;c++){
             if(grille.getData()[l][c]==0){
                if (grille.geData()[l][c+1] == 0 && grille.getData()[l][c+2] == 0 && grille.getData()[l][c+3] == 0 {
                    pointsJ1 = pointsJ1 + 1000;
                    c=c+3;
                }else if(grille.getData()[l][c+1]==0 && grille.getData()[l][c+2]==0){
                  pointsJ1 = pointsJ1 + 100;
                  c=c+2;
                }else if(grille.getData()[l][c+1]==0){
                 pointsJ1 = pointsJ1 + 10;
                 c = c+1;
                }else{
                    pointsJ1= pointsJ1 + 1 ;
                }      
             }   
            }
        }       
        // Parcours des lignes
    for(int c=0;c<grille.getData()[0].length;c++){
          for(int l=0;l<grille.getData().length;l++){
             if(grille.getData()[l][c]==0){
                if (grille.geData()[l+1][c] == 0 && grille.getData()[l+2][c] == 0 && grille.getData()[l+3][c] == 0 {
                    pointsJ1 = pointsJ1 + 1000;
                    c=c+3;
                }else if(grille.getData()[l+1][c]==0 && grille.getData()[l+2][c]==0){
                  pointsJ1 = pointsJ1 + 100;
                  c=c+2;
                }else if(grille.getData()[l+1][c]==0){
                 pointsJ1 = pointsJ1 + 10;
                 c = c+1;
                }else{
                    pointsJ1= pointsJ1 + 1 ;
                }
            }
          }
        }             
        
        // Parcours des diagonales  
            //todo       
               
       }

    }
   


}
