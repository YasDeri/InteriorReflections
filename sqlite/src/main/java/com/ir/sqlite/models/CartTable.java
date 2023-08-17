package com.ir.sqlite.models;

import android.provider.BaseColumns;

public class CartTable
    {   public static final class FavoritesEntry implements BaseColumns {
        public static final String DATABASE_NAME = "cart";
        public static final String TABLE_NAME = "items";
        public static final String COL_1 = "item_name";
        public static final String COL_2 = "item_price";
        public static final String COL_3 = "item_category";
        public static final String COL_4 = "item_vendor";
    }
    }
