package Connect5Game;

import java.util.*;


public class JoueurArtificiel implements Joueur {

    private int numeroJoueur;
    private long tempsExec;
    private int numeroAdv;

    /**
     * Voici la fonction à modifier.
     * Évidemment, vous pouvez ajouter d'autres fonctions dans JoueurArtificiel.
     * Vous pouvez aussi ajouter d'autres classes, mais elles doivent être
     * ajoutées dans le package connect5.ia.
     * Vous de pouvez pas modifier les fichiers directement dans connect., car ils seront écrasés.
     *
     * @param grille Grille reçu (état courrant). Il faut ajouter le prochain coup.
     * @param delais Délais de rélexion en temps réel.
     * @return Retourne le meilleur coup calculé.
     */
    @Override
    public Position getProchainCoup(Grille grille, int delais) {

        tempsExec = System.currentTimeMillis() + delais;

        // plus sécuritaire pour déterminer quel joueur ( A cause des cases obstruées)
        numeroJoueur = initialiserJouer(grille) ;
        numeroAdv =  (numeroJoueur == 1) ? 2 : 1 ;


        // liste cases jouer 1
        List<Position> casesJouer1 = new ArrayList<>();

        // liste cases jouer 2
        List<Position> casesJouer2 = new ArrayList<>();


        for(int l=0;l<grille.getData().length;l++) {
            for(int c=0;c<grille.getData()[0].length;c++) {
                Position pos = new Position(l,c);

                if (grille.getData()[l][c]==1){
                    casesJouer1.add(pos);
                } else if (grille.getData()[l][c]==2) {
                    casesJouer2.add(pos);
                }
            }
        }

        Position nextMove ;
        Position currentMove = null;

        int profondeur = 1;

        do {
            nextMove = currentMove;
            currentMove = meilleurCoupMinMax(grille, profondeur);
            profondeur++;
        } while (currentMove != null && profondeur <= grille.nbLibre());

        return nextMove;
    }



    public int initialiserJouer (Grille grille)  {
        int casesJouer1 =  0 ;
        int casesJouer2 = 0 ;

        for(int l=0;l<grille.getData().length;l++) {
            for(int c=0;c<grille.getData()[0].length;c++) {
                if (grille.getData()[l][c]==1){
                    casesJouer1 += 1;
                } else if (grille.getData()[l][c]==2) {
                    casesJouer2 += 1 ;
                }
            }
        }
        return ( casesJouer1 < casesJouer2 ) ? 1 : 2  ;
    }





    public Position meilleurCoupMinMax(final Grille grille, int profondeur) {

        int bestValue = Integer.MIN_VALUE ;

        PriorityQueue<Position> coupDisponibles = coupDisponibles(grille);
        Map<Integer,Position> meilleurCoup =  new TreeMap<>() ;


        while (!coupDisponibles.isEmpty() )  {

            Position coup = coupDisponibles.poll();
            Grille nextMove = grille.clone();
            nextMove.set(coup, numeroJoueur);

            int currentVal = miniMax(nextMove, profondeur - 1, bestValue, Integer.MAX_VALUE, false);


            if(tempsExec > System.currentTimeMillis() ) {
                if (currentVal > bestValue) {
                    bestValue = currentVal;
                    meilleurCoup.put(bestValue, coup) ;
                }
            } else {
                return null ;
            }
        }

        return ((TreeMap<Integer, Position>) meilleurCoup).lastEntry().getValue() ;
    }


    private int determinerJouerTour (Grille grille) {
        boolean condition = (grille.getSize() - grille.nbLibre())% 2 == 0 ;
        return condition  ? 1 : 2 ;
    }


    public int miniMax(final Grille grille, int profondeur, int alpha, int beta, final boolean maximum) {
        GrilleVerificateur verif = new GrilleVerificateur();

        int jouerCourant = determinerJouerTour(grille) ;

        // On vérifie s'il y a un gagnant
        int gagnant = verif.determineGagnant(grille);
        if (gagnant != 0) {
            if (gagnant == numeroJoueur) {
                return 10000;
            } else {
                return -1;
            }
        }

        // On a atteint la profondeur max
        if (profondeur == 0) {
            return evalMeilleurCoup (grille) ;
        }

        if (grille.nbLibre() == 0) {
            //partie nulle
            return 0;
        }


        PriorityQueue<Position> coupPossibles = coupDisponibles(grille) ;


        if (maximum) { // max
            // Parcours des successeurs (cases vides)

            while (!coupPossibles.isEmpty()) {
                Position coup = coupPossibles.poll();
                Grille nextMove = grille.clone();
                nextMove.set(coup.ligne, coup.colonne, jouerCourant);
                if (tempsExec > System.currentTimeMillis()) {
                    alpha = Math.max(alpha, miniMax(nextMove, profondeur - 1, alpha, beta, false));
                    if (beta <= alpha) {
                        break;
                    }
                }
            }

            System.out.println("--alpha : " +  alpha);
            return alpha;

        } else { // min
            while (!coupPossibles.isEmpty()) {
                Position coup = coupPossibles.poll();
                Grille nextMove = grille.clone();
                nextMove.set(coup.ligne, coup.colonne, jouerCourant);
                if (tempsExec > System.currentTimeMillis()) {
                    beta = Math.min(beta, miniMax(nextMove, profondeur - 1, alpha, beta, true));
                    if (beta <= alpha) {
                        break;
                    }
                }
            }

            System.out.println("--beta : " +  beta);

            return beta;
        }
    }



    private int calculPoints (int suitePion){
        return (int)Math.pow(10, suitePion - 1);
    }







    private PriorityQueue <Position> coupDisponibles(Grille grille) {

        // using lambda expression instead of Comparator
        PriorityQueue<Position> casesVides = new PriorityQueue<>((o1, o2) -> {
            int valeurPos1 = evalVoisin(grille, o1);
            int valeurPos2 = evalVoisin(grille, o2);
            if (valeurPos1 < valeurPos2) {
                return -1;
            } else if (valeurPos1 > valeurPos2) {
                return 1;
            } else {
                return 0;
            }
        });

        // charger les cases vides
        for(int l=0;l<grille.getData().length;l++) {
            for(int c=0;c<grille.getData()[0].length;c++) {
                Position pos = new Position(l,c);
                if(grille.getData()[l][c]==0) {
                    casesVides.add(pos);
                }
            }
        }

        return casesVides ;

    }




    private int evalVoisin (Grille grille, Position position) {

        int pos_x = position.colonne ;
        int pos_y = position.ligne ;
        int lignes = grille.data.length ;
        int colonnes = grille.data[0].length ;
        int currentVal ;
        int nbrVoisins = 0 ;

        for ( int i =  Math.max( 0, pos_x-1 )  ; i <= Math.min (pos_x+1, lignes-1) ; ++i ){

            for ( int j =  Math.max(0, pos_y-1) ; j <= Math.min (pos_y+1, colonnes-1); ++j ){

                if ( ! ( i== pos_x && j== pos_y ) ) {
                    currentVal = grille.get(position) ;
                    if (currentVal == numeroJoueur){
                        nbrVoisins += 1;
                    }
                }
            }
        }
        return nbrVoisins;
    }




    public int evalMeilleurCoup(Grille grille) {

        int pointsJoueur = 0;
        int pointsAdv = 0;
        int suitePionJoueur = 0;
        int suitePionAdv = 0;

        // Iteration horizontal
        for (int l = 0; l < grille.data.length; l++) {
            int c = 0;
            while (c < grille.data[0].length) {
                while (grille.get(l, c) == numeroJoueur && c < grille.data[0].length - 1) {
                    suitePionJoueur += 1;
                    c += 1;
                }
                pointsJoueur += calculPoints(suitePionJoueur);

                while (grille.get(l, c) == numeroAdv && c < grille.data[0].length - 1) {
                    suitePionAdv += 1;
                    c += 1;
                }
                pointsAdv += calculPoints(suitePionAdv);
                suitePionJoueur = 0;
                suitePionAdv = 0;
                c++;
            }
        }

        suitePionJoueur = 0;
        suitePionAdv = 0;

        // Iteration vertical
        for (int c = 0; c < grille.data[0].length; c++) {
            int l = 0;
            while (l < grille.data.length) {
                while (grille.get(l, c) == numeroJoueur && l < grille.data.length - 1) {
                    suitePionJoueur += 1;
                    l += 1;
                }
                pointsJoueur += calculPoints(suitePionJoueur);
                while (grille.get(l, c) == numeroAdv && l < grille.data.length - 1) {
                    suitePionAdv += 1;
                    l += 1;
                }
                pointsAdv += calculPoints(suitePionAdv);
                l++;
                suitePionJoueur = 0;
                suitePionAdv = 0;
            }
        }
        suitePionJoueur = 0;
        suitePionAdv = 0;
        //System.out.println("Iteration en diagonale ///");
        for (int l = 0; l < grille.data.length * 2; l++) {
            for (int c = 0; c <= l; c++) {
                int i = l - c;
                if (i < grille.data.length && c < grille.data[0].length) {
                    // System.out.print( grille.get(i,c) + " " );

                    while (grille.get(i, c) == numeroJoueur
                            && i < grille.data.length - 1 && c < grille.data[0].length - 1) {
                        suitePionJoueur += 1;
                        c += 1;
                        i += 1;
                    }
                    pointsJoueur += calculPoints(suitePionJoueur);

                    while (grille.get(i, c) == numeroAdv
                            && i < grille.data.length - 1 && c < grille.data[0].length - 1) {
                        suitePionAdv += 1;
                        c += 1;
                        i += 1;
                    }
                    pointsAdv += calculPoints(suitePionAdv);
                    suitePionJoueur = 0;
                    suitePionAdv = 0;
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
                // System.out.print( grille.get(l,c2) + " " );

                while (grille.get(l, c2) == numeroJoueur
                        && l < grille.data.length - 1 && c < grille.data[0].length - 1) {
                    suitePionJoueur += 1;
                    c += 1;
                    l += 1;
                }

                pointsJoueur += calculPoints(suitePionJoueur);

                while (grille.get(l, c2) == numeroAdv
                        && l < grille.data.length - 1 && c < grille.data[0].length - 1) {
                    suitePionAdv += 1;
                    c += 1;
                    l += 1;
                }
                pointsAdv += calculPoints(suitePionAdv);
                suitePionJoueur = 0;
                suitePionAdv = 0;
            }
        }
        //System.out.println("joueur : " + pointsJoueur);
        //System.out.println("adversaire : " + pointsAdv);
        System.out.println("retour Eval : " + (pointsJoueur - pointsAdv));
        return pointsJoueur - pointsAdv;
    }






}