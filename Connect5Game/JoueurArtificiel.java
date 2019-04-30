package Connect5Game;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.*;
import java.util.List;


public class JoueurArtificiel implements Joueur {

    private final Random random = new Random();
    private int pointage ;
    private int numeroJouer ;
    private long tempsExec ;


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

        int profondeur = 1 ;


        // evalMeilleurCoup(grille);

        // liste cases vides
        Deque<Position> casesvides = new ArrayDeque<>() ;

        // liste cases jouer 1
        List<Position> casesJouer1 = new ArrayList<>();

        // liste cases jouer 2
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


        // plus sécuritaire pour déterminer quel joueur ( A cause des cases obstruées)
        numeroJouer = ( casesJouer1.size() < casesJouer2.size() ) ? 1 : 2  ;

        //int nbcol = grille.getData()[0].length ;
        //int choix = random.nextInt(casesvides.size());

        Position  choix = new Position(0,0) ;

        while (profondeur <= grille.nbLibre()) {
            choix = meilleurCoupMinMax(grille, profondeur) ;
            System.out.println("cases libres : " + grille.nbLibre());
        }

        //System.out.println( "position : " + "i :" + choix.ligne);
        //System.out.println( "position : " + "j :" + choix.colonne);

        return choix ;
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
                while(grille.get(l,c) == numeroJouer && c < grille.data[0].length-1 ){
                    suitePionJoueur += 1;
                    c += 1;
                }
                pointsJoueur += calculPoints(suitePionJoueur);

                while(grille.get(l,c) != numeroJouer &&
                        grille.get(l,c) != 0 && c < grille.data[0].length-1){
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
                while(grille.get(l,c) == numeroJouer && l < grille.data.length - 1){
                    suitePionJoueur += 1;
                    l += 1;
                }
                pointsJoueur += calculPoints(suitePionJoueur);

                while(grille.get(l,c) != numeroJouer
                        && grille.get(l,c) != 0 && l <grille.data.length - 1){
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
                    // System.out.print( grille.get(i,c) + " " );

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
                // System.out.print( grille.get(l,c2) + " " );

                while (grille.get(l,c2) == numeroJouer &&
                        l < grille.data.length - 1  && c < grille.data[0].length - 1){
                    suitePionJoueur += 1;
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

    public Position minMax (Grille grille, Deque<Position> casesVides, int delais ) {


        //Partie nulle
        if (grille.nbLibre() == 0 ) {
            return new Position (0,0) ;
        }

        tempsExec =  System.currentTimeMillis() + delais + (long) Math.floor(delais * 0.1);
        Map<Integer,Position> meilleurCoup =  new TreeMap<>() ;
        Grille grilleClone = grille.clone();

        int iter = 0 ;

        boolean conditionTemps = false ;
        while (!casesVides.isEmpty() && !conditionTemps ) {
            Position pos = casesVides.pollFirst();
            grilleClone.set(pos, numeroJouer);
            meilleurCoup.put(evalMeilleurCoup(grilleClone), pos);
            conditionTemps = tempsExec < System.currentTimeMillis();
        }

        return  (((TreeMap<Integer, Position>) meilleurCoup).lastEntry().getValue()) ;
    }





    private Position meilleurCoupMinMax (Grille grille, int profondeur) {

        int initAlpha = Integer.MIN_VALUE ;
        int initBeta  = Integer.MAX_VALUE ;

        Map<Integer,Position> meilleurCoup =  new TreeMap<>() ;

        //TreeSet<Position> coupDisponibles = coupDisponibles(grille) ;
        Deque<Position> coupDisponibles = coupsDisponibles(grille) ;

        System.out.println("inside MEILLEUR COUP");

        boolean condition = true ;

        while (!coupDisponibles.isEmpty() ) {
            Position currentCoup  = coupDisponibles.pollFirst() ;
           if ( tempsExec > System.currentTimeMillis()) {
               System.out.println("inside meilleur Valeur ");
               int meilleurValeur = minMax(grille, profondeur , initAlpha, initBeta, false) ;
               meilleurCoup.put(meilleurValeur, currentCoup) ;
           } else {
               return new Position(0,0) ;
           }

        }

        return ((TreeMap<Integer, Position>) meilleurCoup).lastEntry().getValue() ;
    }


    private int minMax (Grille grille, int profondeur, int alpha, int beta, boolean minFlag) {

        if (grille.nbLibre() == 0) {
            return 0;
        }

        if (profondeur == 0) {
            System.out.println("Profondeur : "  + profondeur);
            return evalMeilleurCoup(grille) ;
        }


        // using lambda expression instead of Comparator
        //TreeSet<Position> casesVides = coupDisponibles(grille) ;
            Deque<Position> casesVides = coupsDisponibles(grille) ;
        boolean condition =  false ;



        if (minFlag) {

            System.out.println("inside minFlag" );
            while (!casesVides.isEmpty() && !condition ) {
                Position currentPos = casesVides.pollFirst();
                Grille prochainCoup = grille.clone() ;
                prochainCoup.set(currentPos, numeroJouer) ;

                System.out.println(" cases dispo minFlag : " +  casesVides.size());

                if (tempsExec > System.currentTimeMillis()) {
                    alpha = Math.max(alpha, minMax(prochainCoup, profondeur -1 , alpha, beta , false)) ;
                    if (beta <= alpha) {
                        break;
                    }else {
                        return 0 ;
                    }
                }
            }

        } else  {
            System.out.println("inside else " );
            while(!casesVides.isEmpty() && !condition ) {
                Position currentPos = casesVides.pollFirst();
                System.out.println(" cases dispo else : " +  casesVides.size());
                Grille prochainCoup = grille.clone() ;
                prochainCoup.set(currentPos, numeroJouer) ;
                if (tempsExec > System.currentTimeMillis()) {
                    beta = Math.min(beta, minMax(prochainCoup, profondeur -1 , alpha, beta , true) );
                }
                if (beta <= alpha) {
                    break;
                }else {
                    return 0 ;
                }
            }
        }

        System.out.println("cases vides :" + casesVides.size());

        return beta ;
    }




    private int evalVoisin (Grille grille, Position position) {

        int pos_x = position.colonne ;
        int pos_y = position.ligne ;
        int LIGNES = grille.data.length ;
        int COLONNES = grille.data[0].length ;
        int currentVal ;
        int nbrVoisins = 0 ;

        for ( int i =  Math.max( 0, pos_x-1 )  ; i <= Math.min (pos_x+1, LIGNES-1) ; ++i ){

            for ( int j =  Math.max(0, pos_y-1) ; j <= Math.min (pos_y+1, COLONNES-1); ++j ){

                if ( ! ( i== pos_x && j== pos_y ) ) {
                    currentVal = grille.get(position) ;
                    if (currentVal == numeroJouer){
                        nbrVoisins += 1;
                    }
                }
            }
        }
        return nbrVoisins;
    }



    private TreeSet<Position> coupDisponibles(Grille grille) {

        // using lambda expression instead of Comparator
        TreeSet<Position> casesVides = new TreeSet<>((o1, o2) -> {
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



    private Deque<Position> coupsDisponibles (Grille grille) {

        Deque<Position> coups = new ArrayDeque<>() ;

        for(int l=0;l<grille.getData().length;l++) {
            for(int c=0;c<grille.getData()[0].length;c++) {
                Position pos = new Position(l,c);
                if(grille.getData()[l][c]==0) {
                    coups.add(pos);
                }
            }
        }
        return coups ;
    }



}



