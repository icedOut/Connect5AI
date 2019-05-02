package Connect5Game;

import java.util.*;


public class JoueurArtificiel implements Joueur {

    private int joueurCourant;
    private long tempsExec;
    private int joueurAdversaire;

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

        Position nextMove ;
        Position currentMove = null;
        joueurCourant = determinerJouerTour(grille) ;
        joueurAdversaire =  (joueurCourant == 1) ? 2 : 1 ;
        tempsExec = System.currentTimeMillis() + delais;

        int profondeur = 1;

        do {
            nextMove = currentMove;
            currentMove = decisionMiniMax(grille, profondeur);
            profondeur++;
        } while (currentMove != null && profondeur <= grille.nbLibre());

        return nextMove;
    }



    public Position decisionMiniMax(final Grille grille, int profondeur) {

        int initAlpha = Integer.MIN_VALUE ;
        int initBeta  = Integer.MAX_VALUE ;

        PriorityQueue<Position> coupDisponibles = coupDisponibles(grille);
        Map<Integer,Position> meilleurCoup =  new TreeMap<>() ;

        boolean maxFlag = false;

        while (!coupDisponibles.isEmpty() )  {

            Position coup = coupDisponibles.poll();
            Grille nextMove = grille.clone();
            nextMove.set(coup, joueurCourant);

            int currentVal = miniMax(nextMove, profondeur - 1, initAlpha, initBeta, maxFlag );

            if(tempsExec > System.currentTimeMillis() ) {
                if (currentVal > initAlpha) {
                    initAlpha = currentVal;
                    meilleurCoup.put(initAlpha, coup) ;
                }
            }
            else {
                return null ;
            }
        }

        return ((TreeMap<Integer, Position>) meilleurCoup).lastEntry().getValue() ;
    }


    private int determinerJouerTour (Grille grille) {
        boolean condition = (grille.getSize() - grille.nbLibre())% 2 == 0 ;
        return condition  ? 1 : 2 ;
    }


    public int miniMax(final Grille grille, int profondeur, int alpha, int beta, boolean maxFlag) {

        GrilleVerificateur verif = new GrilleVerificateur();

        int jouerCourant = determinerJouerTour(grille) ;
        int adversaire = (joueurCourant == 1) ? 2 : 1 ;

        int gagnant = verif.determineGagnant(grille);
        if (gagnant != 0) {
            if (gagnant == joueurCourant) {
                return 100000;
            }
        }

        if (grille.nbLibre() == 0) {
            return 0;
        }

        if (profondeur == 0) {
            return evalMeilleurCoup(grille) ;
        }

        PriorityQueue<Position> coupPossibles = coupDisponibles(grille) ;


        if (maxFlag) {

            while (!coupPossibles.isEmpty()) {

                Position coup = coupPossibles.poll();
                Grille nextMove = grille.clone();

                nextMove.set(coup.ligne, coup.colonne, jouerCourant);

                if (tempsExec > System.currentTimeMillis()) {

                    alpha = Math.max(alpha, miniMax(nextMove, profondeur - 1, alpha, beta, !maxFlag));

                    if (beta <= alpha) {
                        break;
                    }
                }
            }

            return alpha;

        } else {
            while (!coupPossibles.isEmpty()) {
                Position coup = coupPossibles.poll();
                Grille nextMove = grille.clone();
                nextMove.set(coup.ligne, coup.colonne, adversaire);
                if (tempsExec > System.currentTimeMillis()) {
                    beta = Math.min(beta, miniMax(nextMove, profondeur - 1, alpha, beta, !maxFlag));
                    if (beta <= alpha) {
                        break;
                    }
                }
            }

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
                    if (currentVal == joueurCourant){
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
                while (grille.get(l, c) == joueurCourant && c < grille.data[0].length - 1) {
                    suitePionJoueur += 1;
                    c += 1;
                }
                pointsJoueur += calculPoints(suitePionJoueur);

                while (grille.get(l, c) == joueurAdversaire && c < grille.data[0].length - 1) {
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
                while (grille.get(l, c) == joueurCourant && l < grille.data.length - 1) {
                    suitePionJoueur += 1;
                    l += 1;
                }
                pointsJoueur += calculPoints(suitePionJoueur);
                while (grille.get(l, c) == joueurAdversaire && l < grille.data.length - 1) {
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

        // Iteration en diagonale ///

        for (int l = 0; l < grille.data.length * 2; l++) {
            for (int c = 0; c <= l; c++) {
                int i = l - c;
                if (i < grille.data.length && c < grille.data[0].length) {

                    while (grille.get(i, c) == joueurCourant
                            && i < grille.data.length - 1 && c < grille.data[0].length - 1) {
                        suitePionJoueur += 1;
                        c += 1;
                        i += 1;
                    }
                    pointsJoueur += calculPoints(suitePionJoueur);

                    while (grille.get(i, c) == joueurAdversaire
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

                while (grille.get(l, c2) == joueurCourant
                        && l < grille.data.length - 1 && c < grille.data[0].length - 1) {
                    suitePionJoueur += 1;
                    c += 1;
                    l += 1;
                }

                pointsJoueur += calculPoints(suitePionJoueur);

                while (grille.get(l, c2) == joueurAdversaire
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
        //System.out.println("retour Eval : " + (pointsJoueur - pointsAdv));
        return pointsJoueur - pointsAdv;
    }


    private Position utility(Grille grille) {

        int ROW_SIZE = grille.data.length;
        int COL_SIZE = grille.data[0].length ;

        // verifier si gagnant horizontal
        for (int row=0; row<ROW_SIZE; row++) {
            for (int col=0; col<COL_SIZE-4; col++) {
                if (grille.data[row][col] == grille.data[row][col+1] &&
                        grille.data[row][col] == grille.data[row][col+2] &&
                        grille.data[row][col] == grille.data[row][col+3] &&
                        grille.data[row][col+4] == 0 )
                {
                    return new Position(row,col+4) ;
                }
            }
        }

        // verifier si gagnant Vertical
        for (int row=0; row<ROW_SIZE-4; row++) { //0 to 2
            for (int col=0; col<COL_SIZE; col++) {
                if (grille.data[row][col] == grille.data[row+1][col] &&
                        grille.data[row][col] == grille.data[row+2][col] &&
                        grille.data[row][col] == grille.data[row+3][col] &&
                        grille.data[row+4][col] == 0)
                {
                    return new Position(row+4, col) ;
                }
            }
        }


        // verifier si gagnant diagonale ////
        for (int row=0; row<ROW_SIZE-4; row++) { //0 to 2
            for (int col=0; col<COL_SIZE-4; col++) { //0 to 3
                if (grille.data[row][col] == grille.data[row+1][col+1] &&
                        grille.data[row][col] == grille.data[row+2][col+2] &&
                        grille.data[row][col] == grille.data[row+3][col+3] &&
                        grille.data[row+4][col+4] == 0) {
                    return new Position(row+4,col+4) ;
                }
            }
        }

        // verifier si gagnant diagonale \\\\
        for (int row=4; row<ROW_SIZE; row++) { //3 to 5
            for (int col=0; col<COL_SIZE-4; col++) { //0 to 3
                if (grille.data[row][col] == grille.data[row-1][col+1] &&
                        grille.data[row][col] == grille.data[row-2][col+2] &&
                        grille.data[row][col] == grille.data[row-3][col+3] &&
                        grille.data[row-4][col+4] == 0) {
                    return new Position(row-4,col+4) ;
                }
            }
        }

        return null ;
    }



}