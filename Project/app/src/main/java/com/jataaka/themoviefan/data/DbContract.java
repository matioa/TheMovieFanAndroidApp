package com.jataaka.themoviefan.data;

import android.provider.BaseColumns;

/**
 * Created by Martin on 11/01/2016.
 */
public final class DbContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DbContract() {}

    // Table FanNotes
    public static abstract class FanNotesEntry implements BaseColumns {
        public static final String TABLE_NAME = "autocomplete";
        public static final String COLUMN_NAME_USER = "username";
        public static final String COLUMN_NAME_ENTRY = "entry";
    }
}
