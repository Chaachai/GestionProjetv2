package dao.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moulaYounes on 19/08/2015.
 */
public abstract class AbstractDao<T> {

    protected SQLiteDatabase db;
    protected DbConnect dbHelper;
    protected String[] columns;
    protected String tableName;
    protected String idName;


    public abstract long create(T t);
    public abstract long edit(T t);
    protected abstract T transformeCursorToBean(Cursor cursor);



    public  long remove(Object id){
        return db.delete(tableName,idName + " = " + id,null);
    }

    public T find(Object id){
        open();
        Cursor cursor = db.query(tableName, columns,idName+"='"+id+"'" ,null,null,null,null);
        return splitCursor(cursor);
    }
    public List<T> findAll(){
            return loadAll(null);
        }

    public List<T> findAll(String constraint){
        return loadAll(constraint);
    }
    private List<T> loadAll(String constraint){
        open();
        Cursor cursor = db.query(tableName, columns,constraint ,null,null,null,null);
        List<T> beans = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            T bean = transformeCursorToBean(cursor);
            beans.add(bean);
            cursor.moveToNext();
        }
        return beans;
    }

    public AbstractDao(Context context) {
        dbHelper =  new DbConnect(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public DbConnect getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DbConnect dbHelper) {
        this.dbHelper = dbHelper;
    }
    public   T splitCursor(Cursor c) {
        if (c.getCount()==0)
            return null;
        c.moveToFirst();
        T bean = transformeCursorToBean(c);
        c.close();
        return bean;
    }

}
