package com.example.kandyloginwithretrofit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * Instance
     */
    private static DatabaseHelper sInstance;

    /**
     * Database Info
     */
    private static final String DATABASE_NAME = "Tokens";
    private static final int DATABASE_VERSION = 1;

    /**
     * Table Name
     */
    private static final String TABLE_TOKENS = "tokens";

    /**
     * tokens Table Column
     */
    private static final String KEY_TOKENS_ID = "id";
    private static final String KEY_TOKEN_ACCESS_TOKEN = "access_token";
    private static final String KEY_TOKEN_EXPIRES_IN = "expires_in";
    private static final String KEY_TOKEN_REFRESH_EXPIRES_IN = "refresh_expires_in";
    private static final String KEY_TOKEN_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_TOKEN_TOKEN_TYPE = "token_type";
    private static final String KEY_TOKEN_ID_TOKEN = "id_token";
    private static final String KEY_TOKEN_NOT_BEFORE_POLICY = "not_before_policy";
    private static final String KEY_TOKEN_SESSION_STATE = "session_state";
    private static final String KEY_TOKEN_SCOPE = "scope";

    /**
     * Status
     */
    public String status = "Result Of The Action";

    /**
     * Setting Status after a crud operation.
     *
     * @param status is a success or fail message which received from crud operations.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getting status
     *
     * @return status which is last one.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Synchronize Database Helper class
     *
     * @param context Context which will be used.
     * @return Instance
     */
    public static synchronized DatabaseHelper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**
     * Called when the database connection is being configured.
     *
     * @param db Database which will be configured.
     */
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    /**
     * Create database if not created before.
     *
     * @param sqLiteDatabase Database which will be created.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CITIES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TOKENS +
                "("
                + KEY_TOKENS_ID + " INTEGER PRIMARY KEY, "
                + KEY_TOKEN_ACCESS_TOKEN + " VARCHAR, "
                + KEY_TOKEN_EXPIRES_IN + " INTEGER, "
                + KEY_TOKEN_REFRESH_EXPIRES_IN + " INTEGER, "
                + KEY_TOKEN_REFRESH_TOKEN + " VARCHAR, "
                + KEY_TOKEN_TOKEN_TYPE + " VARCHAR, "
                + KEY_TOKEN_ID_TOKEN + " VARCHAR, "
                + KEY_TOKEN_NOT_BEFORE_POLICY + "INTEGER, "
                + KEY_TOKEN_SESSION_STATE + " VARCHAR, "
                + KEY_TOKEN_SCOPE + " VARCHAR "
                + ")";
        sqLiteDatabase.execSQL(CREATE_CITIES_TABLE);
    }

    /**
     * Upgrade Database version.
     *
     * @param sqLiteDatabase SQLiteDatabase which is used.
     * @param oldVersion     old version number which version of database used.
     * @param newVersion     new version number which version of database will be used.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TOKENS);
            onCreate(sqLiteDatabase);
        }
    }

    /**
     * Add token into the database
     * @param token which will be added into the database.
     */

    public void addToken(Token token) {
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            String sqlString = "INSERT INTO " + TABLE_TOKENS
                    + " ("
                    + KEY_TOKEN_ACCESS_TOKEN + ","
                    + KEY_TOKEN_EXPIRES_IN + ","
                    + KEY_TOKEN_REFRESH_EXPIRES_IN + ","
                    + KEY_TOKEN_REFRESH_TOKEN + ","
                    + KEY_TOKEN_TOKEN_TYPE + ","
                    + KEY_TOKEN_ID_TOKEN + ","
                    + KEY_TOKEN_NOT_BEFORE_POLICY + ","
                    + KEY_TOKEN_SESSION_STATE + ","
                    + KEY_TOKEN_SCOPE
                    + ") "
                    + "VALUES(?,?,?,?,?,?,?,?,?)";
            SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sqlString);
            sqLiteStatement.bindString(1, token.access_token);
            sqLiteStatement.bindLong(2, token.expires_in);
            sqLiteStatement.bindLong(3, token.refresh_expires_in);
            sqLiteStatement.bindString(4, token.refresh_token);
            sqLiteStatement.bindString(5, token.token_type);
            sqLiteStatement.bindString(6, token.id_token);
            sqLiteStatement.bindLong(7, token.not_before_policy);
            sqLiteStatement.bindString(8, token.session_state);
            sqLiteStatement.bindString(9, token.scope);
            sqLiteStatement.execute();
            setStatus(token.access_token + " Successfully Saved");
        } catch (Exception e) {
            e.printStackTrace();
            setStatus("Error while trying to add token into the database");
        }
    }

    /**
     * Delete a Token from database
     *
     * @param token Token which will be deleted.
     */
    public void deleteToken(Token token) {

        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            String sqlString = "DELETE FROM " + TABLE_TOKENS + " WHERE " + KEY_TOKEN_ID_TOKEN + " = ? ";
            SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sqlString);
            sqLiteStatement.bindString(1, token.id_token);
            sqLiteStatement.execute();
            setStatus(token.access_token + " Successfully deleted!");
        } catch (Exception e) {
            e.printStackTrace();
            setStatus("Error while trying to delete token from database");
        }

    }

}
