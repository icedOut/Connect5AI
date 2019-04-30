package Connect5Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class JoueurArtificiel implements Joueur {

    private final Random random = new Random();
    private int pointage ;
    private int numeroJouer ;

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

        int alpha = Integer.MAX_VALUE ;
        int beta =  Integer.MIN_VALUE ;

        numeroJouer = (grille.nbLibre() % 2 == 0) ? 1 : 2  ;

        evalMeilleurCoup(grille);

        // Extraire liste cases vides
        List<Position> casesvides = new ArrayList<>();

        // Extraire liste cases jouer 1
        List<Position> casesJouer1 = new ArrayList<>();

        // Extraire liste cases jouer 2
        List<Position> casesJouer2 = new ArrayList<>();


        for(int l=0;l<grille.getData().length;l++) {
            for(int c=0;c<grille.getData()[0].length;c++) {

                Position pos = new Position(l,c);

                if(grille.getData()[l][c]==0) {
                    casesvides.add(pos);
                } else if (grille.getData()[l][c]==1){
                    casesJouer1.add(pos);
                } else if (grille.getData()[l][c]==2) {
                    casesJouer2.add(pos);
                }
            }
        }

        int nbcol = grille.getData()[0].length ;
        int choix = random.nextInt(casesvides.size());
        Position  choix1 = casesvides.get(0);
        return new Position(choix / nbcol, choix % nbcol);
    }



    private int calculPoints (int suitePion){
        int reponse;
        switch (suitePion){
            case 1 :
                reponse = 1;
                break;
            case 2 :
                reponse = 10;
                break;
            case 3 :
                reponse = 100;
                break;
            case 4 :
                reponse = 1000;
                break;
            case 5 :
                reponse = 10000;
                break;
            default:
                reponse = -1;

        }
        return reponse;
    }

    public int evalMeilleurCoup(Grille grille ) {

        int PointsJoueur = 0;
        int PointsAdv = 0;
        int suitePionJoueur = 0;
        int suitePionAdv = 0;

        // Iteration horizontal
        for (int l = 0; l < grille.data.length; l++) {
            for (int c = 4; c < grille.data[0].length; c++) {
                    while(grille.get(l,c) == numeroJouer){
                       suitePionJoueur += 1;
                       c += 1;
                   }
                   PointsJoueur += calculPoints(suitePionJoueur);

                    while(grille.get(l,c) != numeroJouer && grille.get(l,c) != 0){
                        suitePionAdv += 1;
                        c += 1;
                    }
                    PointsAdv += calculPoints(suitePionAdv);
                

                /*
                System.out.println("---Iteration horizontale---");
                System.out.println("ligne : " + l);
                System.out.println("colonne : " + c );
                System.out.println( "Value : " + grille.data[l][c]);
                */
            }
        }
        suitePionJoueur = 0;
        suitePionAdv = 0;
        // Iteration vertical
        for (int c = 4; c < grille.data[0].length; c++) {
            for (int l = 0; l < grille.data.length; l++) {
                    while(grille.get(l,c) == numeroJouer){
                        suitePionJoueur += 1;
                        l += 1;
                    }
                    PointsJoueur += calculPoints(suitePionJoueur);

                    while(grille.get(l,c) != numeroJouer && grille.get(l,c) != 0){
                         suitePionAdv += 1;
                         l += 1;
                     }
                     PointsAdv += calculPoints(suitePionAdv);
             /*   
                System.out.println("---Iteration verticale---");
                System.out.println("ligne : " + l);
                System.out.println("colonne : " + c );
              */    
        }
    }   
        suitePionJoueur = 0;
        suitePionAdv = 0;

        // iteration diagonal \\\\\ de gauche a droite
        for (int l = 4; l < grille.getData().length; l++) {
            for (int c = 4; c < grille.getData()[l].length; c++) {
                while(grille.get(l,c) == numeroJouer){
                    suitePionJoueur += 1;
                    l += 1;
                    c += 1;
                }
                PointsJoueur += calculPoints(suitePionJoueur);

                while(grille.get(l,c) != numeroJouer && grille.get(l,c) != 0){
                     suitePionAdv += 1;
                     l += 1;
                     c += 1;
                 }
                 PointsAdv += calculPoints(suitePionAdv);

                /*
                System.out.println("- DIAGONALE \\\\  ---");
                System.out.println("ligne : " + l);
                System.out.println("colonne : " + c );
                */
            }
        }

        suitePionJoueur = 0;
        suitePionAdv = 0;

        // iteration diagonal ///// de droite a gauche
        for (int l = grille.getData().length - 5; l >= 0; l--) {
            for (int c = 4; c < grille.getData()[l].length; c++) {
                while(grille.get(l,c) == numeroJouer){
                    suitePionJoueur += 1;
                    l -= 1;
                    c += 1;
                }
                PointsJoueur += calculPoints(suitePionJoueur);

                while(grille.get(l,c) != numeroJouer && grille.get(l,c) != 0){
                     suitePionAdv += 1;
                     l -= 1;
                     c += 1;
                 }
                 PointsAdv += calculPoints(suitePionAdv);

                /*
                System.out.println("--- DIAGONALE /// ---");
                System.out.println("ligne : " + l);
                System.out.println("colonne : " + c );
                */

            }
        }
        return pointsJoueur - PointsAdv ;
    }



    public int minMax (Grille grille, int profondeur, int alpha,
                       int beta, boolean max, List<Position> casesVides ) {

        if (casesVides.size() == 0 ) {
            return 0 ;
        }

        return  0 ;
    }
}
