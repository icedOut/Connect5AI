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
        int nbcol = grille.getData()[0].length;

        for(int l=0;l<grille.getData().length;l++) {

            for(int c=0;c<nbcol;c++) {

                if(grille.getData()[l][c]==0)
                    casesvides.add(l*nbcol+c);
            }

        }

        int choix = random.nextInt(casesvides.size());
        choix = casesvides.get(choix);
        return new Position(choix / nbcol, choix % nbcol);
    }


    public int evalMeilleurCoup(Grille grille ) {

        int result  = 0 ;

        // Iteration horizontal
        for (int l = 0; l < grille.data.length; l++) {
            for (int c = 0; c < grille.data[0].length; c++) {

                // logique ici
            }

        }

        // Iteration vertical
        for (int c = 0; c < grille.data[0].length; c++) {
            for (int l = 0; l < grille.data.length; l++) {

                // logique ici

            }

        }


        // iteration diagonal \\\\\ de gauche a droite
        for (int l = 4; l < grille.getData().length; l++) {
            for (int c = 4; c < grille.getData()[l].length; c++) {

                // logique ici

            }
        }

        // iteration diagonal ///// de droite a gauche
        for (int l = grille.getData().length - 5; l >= 0; l--) {
            for (int c = 4; c < grille.getData()[l].length; c++) {

                // logique ici

            }
        }


        return result ;
    }
}
