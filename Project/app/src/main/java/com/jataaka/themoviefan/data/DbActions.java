package com.jataaka.themoviefan.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by Martin on 13/01/2016.
 */
public class DbActions extends DbInit {

    public DbActions(Context context) {
        super(context);
    }

    public void addRecord(String entry, String user){
        open();
        ContentValues values = new ContentValues();
        values.put(DbContract.FanNotesEntry.COLUMN_NAME_ENTRY, entry);
        values.put(DbContract.FanNotesEntry.COLUMN_NAME_USER, user);
        this.db.insert(DbContract.FanNotesEntry.TABLE_NAME, null, values);
        close();
    }

    public Cursor getValues(String username){
        String table = DbContract.FanNotesEntry.TABLE_NAME;
        String[] columns = new String[]{DbContract.FanNotesEntry.COLUMN_NAME_ENTRY};
        String selection = String.format("username=\"%s\"",username);
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        open();
        return this.db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
}