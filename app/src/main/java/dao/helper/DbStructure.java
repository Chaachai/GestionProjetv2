package dao.helper;

import android.provider.BaseColumns;

public final class DbStructure {

    public static final String dbName = "gestion_projet";
    public static final int DB_VERSION = 4;

    public static abstract class User implements BaseColumns {

        public static final String T_NAME = "user";
        public static final String C_ID = "id";
        public static final String C_PASSWORD = "password";
        public static final String SQL_CREATE = "create table " + T_NAME + "("
                + C_ID + " TEXT PRIMARY KEY, "
                + C_PASSWORD + " TEXT )";
        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + T_NAME;
    }

    public static abstract class Societe implements BaseColumns {

        public static final String T_NAME = "societe";
        public static final String C_ID = "id";
        public static final String C_RAISONSOCIALE = "raisonsociale";
        public static final String C_DATE = "dateFondation";
        public static final String C_ID_MANAGER = "id_manager";

        public static final String SQL_CREATE = "create table " + T_NAME + "("
                + C_ID + " INTEGER PRIMARY KEY  NOT NULL ,"
                + C_RAISONSOCIALE + " TEXT , "
                + C_DATE + " DATE,"
                + C_ID_MANAGER + " INTEGER, FOREIGN KEY( " + C_ID_MANAGER + " ) REFERENCES " + Manager.T_NAME + "(" + Manager.C_ID + ") )";
        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + T_NAME;
    }

    public static abstract class Manager implements BaseColumns {

        public static final String T_NAME = "manager";
        public static final String C_ID = "id";
        public static final String C_NOM = "nom";
        public static final String C_PRENOM = "prenom";

        public static final String SQL_CREATE = "create table " + T_NAME + "("
                + C_ID + " TEXT PRIMARY KEY, "
                + C_NOM + " TEXT,"
                + C_PRENOM + " TEXT )";
        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + T_NAME;
    }

    public static abstract class Tache implements BaseColumns {
        public static final String T_NAME = "tache";
        public static final String C_ID = "id";
        public static final String C_DATE = "date";
        public static final String C_NBRHEURES = "nbr_heures";
        public static final String C_COMMENTAIRE = "commentaire";

        public static final String SQL_CREATE = "create table " + T_NAME + "("
                + C_ID + " INTEGER PRIMARY KEY  NOT NULL ,"
                + C_DATE + " DATE,"
                + C_NBRHEURES + " INTEGER,"
                + C_COMMENTAIRE + " TEXT)";
        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + T_NAME;

    }

    public static abstract class Depense implements BaseColumns {

        public static final String T_NAME = "depense";
        public static final String C_ID = "id";
        public static final String C_MONTANT = "montant";
        public static final String C_DATE = "date";
        public static final String C_COMMENTAIRE = "commentaire";

        public static final String SQL_CREATE = "create table " + T_NAME + "("
                + C_ID + " INTEGER PRIMARY KEY  NOT NULL,"
                + C_MONTANT + " INTEGER,"
                + C_DATE + " DATE,"
                + C_COMMENTAIRE + " TEXT)";
        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + T_NAME;
    }

    public static abstract class Projet implements BaseColumns {

        public static final String T_NAME = "projet";
        public static final String C_ID = "id";
        public static final String C_NOM = "nom";
        public static final String C_DESCRIPTION = "description";
        public static final String C_DATE = "date";
        public static final String C_BUDGET = "budget";
        public static final String C_ID_SOCIETE = "id_societe";

        public static final String SQL_CREATE = "create table " + T_NAME + "("
                + C_ID + " INTEGER PRIMARY KEY  NOT NULL,"
                + C_NOM + " TEXT,"
                + C_DESCRIPTION + " TEXT,"
                + C_DATE + " DATE,"
                + C_BUDGET + " INTEGER,"
                + C_ID_SOCIETE + " INTEGER, FOREIGN KEY( " + C_ID_SOCIETE + ") REFERENCES " + Societe.T_NAME + "( " + Societe.C_ID + " ) )";
        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + T_NAME;
    }

}
