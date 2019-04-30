package Connect5Game;

import java.util.*;


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

        // evalMeilleurCoup(grille);

        // liste cases vides
        List<Position> casesvides = new ArrayList<>();

        // liste cases jouer 1
        List<Position> casesJouer1 = new ArrayList<>();

        // liste cases jouer 2
        List<Position> casesJouer2 = new ArrayList<>();


        // iteration diagonal ///// de gauche a droite
        // Diagonal //////

        System.out.println( "GRILLE\n" + grille.toString() + "\n");






/*
        System.out.println("\nIteration en diagonale \\\\");

        for (int c = -grille.data.length; c < grille.data[0].length; c++) {
            int c2 = c;
            int l = 0;
            if (c2 < 0) {
                l = -c2;
                c2 = 0;
            }
            for (; c2 < grille.data[0].length && l < grille.data.length; c2++, l++) {
                System.out.print( grille.get(l,c2) + " " );
            }
            System.out.println();
        }
*/






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


        // plus sécuritaire pour déterminer quel joueur ( A cause des cases obstruées)
        numeroJouer = ( casesJouer1.size() < casesJouer2.size() ) ? 1 : 2  ;

        int nbcol = grille.getData()[0].length ;
        int choix = random.nextInt(casesvides.size());
        Position  choix1 = casesvides.get(0);
        return new Position(choix / nbcol, choix % nbcol);
    }



    private int calculPoints (int suitePion){
        return (int)Math.pow(10, suitePion - 1);
    }



    public int evalMeilleurCoup(Grille grille ) {

        int pointsJoueur = 0;
        int pointsAdv = 0;
        int suitePionJoueur = 0;
        int suitePionAdv = 0;

        // Iteration horizontal
        for (int l = 0; l < grille.data.length; l++) {
            for (int c = 0; c < grille.data[0].length; c++) {
                while(grille.get(l,c) == numeroJouer && c < grille.data[0].length ){
                    suitePionJoueur += 1;
                    c += 1;
                }
                pointsJoueur += calculPoints(suitePionJoueur);

                while(grille.get(l,c) != numeroJouer &&
                        grille.get(l,c) != 0 && c < grille.data[0].length){
                    suitePionAdv += 1;
                    c += 1;
                }
                pointsAdv += calculPoints(suitePionAdv);

            }
        }

        suitePionJoueur = 0;
        suitePionAdv = 0;

        // Iteration vertical
        for (int c = 0; c < grille.data[0].length; c++) {
            for (int l = 0; l < grille.data.length; l++) {
                while(grille.get(l,c) == numeroJouer && l < grille.data.length){
                    suitePionJoueur += 1;
                    l += 1;
                }
                pointsJoueur += calculPoints(suitePionJoueur);

                while(grille.get(l,c) != numeroJouer
                        && grille.get(l,c) != 0 && l <grille.data.length){
                    suitePionAdv += 1;
                    l += 1;
                }
                pointsAdv += calculPoints(suitePionAdv);

            }
        }
        suitePionJoueur = 0;
        suitePionAdv = 0;

        System.out.println("Iteration en diagonale ///");
        for( int l = 0 ; l < grille.data.length * 2 ; l++ ) {
            for( int c = 0 ; c <= l ; c++ ) {
                int i = l - c;
                if( i < grille.data.length && c < grille.data[0].length ) {
                    System.out.print( grille.get(i,c) + " " );

                    while (grille.get(i,c) == numeroJouer &&
                            i < grille.data.length - 1  && c < grille.data[0].length - 1){
                        suitePionJoueur += 1;
                        c += 1;
                        i += 1;
                    }
                    pointsJoueur += calculPoints(suitePionJoueur);

                    while (grille.get(i,c) != numeroJouer && grille.get(i,c) != 0 &&
                            i < grille.data.length - 1  && c < grille.data[0].length - 1) {
                        suitePionAdv +=1 ;
                        c += 1;
                        i += 1;
                    }
                    pointsAdv += calculPoints(suitePionAdv);
                }
            }
        }

        suitePionJoueur = 0;
        suitePionAdv = 0;

        // iteration diagonal \\\\ de droite a gauche
        for (int c = -grille.data.length; c < grille.data[0].length; c++) {
            int c2 = c;
            int l = 0;
            if (c2 < 0) {
                l = -c2;
                c2 = 0;
            }
            for (; c2 < grille.data[0].length && l < grille.data.length; c2++, l++) {
                System.out.print( grille.get(l,c2) + " " );

                while (grille.get(l,c2) == numeroJouer &&
                        l < grille.data.length - 1  && c < grille.data[0].length - 1){
                    suitePionJoueur += 1;
                    System.out.println("\nINNER WHILE");
                    System.out.println( grille.get(l,c2) + " " + "\n-------------\n" );
                    c += 1;
                    l += 1;
                }

                pointsJoueur += calculPoints(suitePionJoueur);

                while (grille.get(l,c2) != numeroJouer && grille.get(l,c2) != 0 &&
                        l < grille.data.length - 1  && c < grille.data[0].length - 1) {
                    suitePionAdv +=1 ;
                    c += 1;
                    l += 1;
                }
                pointsJoueur += calculPoints(suitePionAdv);
            }
        }

        return pointsJoueur - pointsAdv ;
    }



    //public int minMax (Grille grille, int profondeur, int alpha,
    //                   int beta, boolean max, List<Position> casesVides ) {

    public int minMax (Grille grille, List<Position> casesVides ) {

        Map<Integer,Position> meilleurCoup =  new TreeMap<>() ;



        // Partie nulle
        if (casesVides.size() == 0 ) {
            return 0 ;
        }

        return  0 ;
    }















}



