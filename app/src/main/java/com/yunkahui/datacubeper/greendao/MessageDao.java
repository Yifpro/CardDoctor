package com.yunkahui.datacubeper.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yunkahui.datacubeper.common.bean.Message;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MESSAGE".
*/
public class MessageDao extends AbstractDao<Message, Void> {

    public static final String TABLENAME = "MESSAGE";

    /**
     * Properties of entity Message.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Sys_notice_id = new Property(0, String.class, "sys_notice_id", false, "SYS_NOTICE_ID");
        public final static Property Create_time = new Property(1, long.class, "create_time", false, "CREATE_TIME");
        public final static Property Update_time = new Property(2, long.class, "update_time", false, "UPDATE_TIME");
        public final static Property Org_number = new Property(3, String.class, "org_number", false, "ORG_NUMBER");
        public final static Property Title = new Property(4, String.class, "title", false, "TITLE");
        public final static Property Content_text = new Property(5, String.class, "content_text", false, "CONTENT_TEXT");
        public final static Property Content_img = new Property(6, String.class, "content_img", false, "CONTENT_IMG");
        public final static Property Notice_type = new Property(7, String.class, "notice_type", false, "NOTICE_TYPE");
        public final static Property Is_show = new Property(8, String.class, "is_show", false, "IS_SHOW");
        public final static Property IsSee = new Property(9, boolean.class, "isSee", false, "IS_SEE");
    }


    public MessageDao(DaoConfig config) {
        super(config);
    }
    
    public MessageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MESSAGE\" (" + //
                "\"SYS_NOTICE_ID\" TEXT," + // 0: sys_notice_id
                "\"CREATE_TIME\" INTEGER NOT NULL ," + // 1: create_time
                "\"UPDATE_TIME\" INTEGER NOT NULL ," + // 2: update_time
                "\"ORG_NUMBER\" TEXT," + // 3: org_number
                "\"TITLE\" TEXT," + // 4: title
                "\"CONTENT_TEXT\" TEXT," + // 5: content_text
                "\"CONTENT_IMG\" TEXT," + // 6: content_img
                "\"NOTICE_TYPE\" TEXT," + // 7: notice_type
                "\"IS_SHOW\" TEXT," + // 8: is_show
                "\"IS_SEE\" INTEGER NOT NULL );"); // 9: isSee
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MESSAGE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Message entity) {
        stmt.clearBindings();
 
        String sys_notice_id = entity.getSys_notice_id();
        if (sys_notice_id != null) {
            stmt.bindString(1, sys_notice_id);
        }
        stmt.bindLong(2, entity.getCreate_time());
        stmt.bindLong(3, entity.getUpdate_time());
 
        String org_number = entity.getOrg_number();
        if (org_number != null) {
            stmt.bindString(4, org_number);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(5, title);
        }
 
        String content_text = entity.getContent_text();
        if (content_text != null) {
            stmt.bindString(6, content_text);
        }
 
        String content_img = entity.getContent_img();
        if (content_img != null) {
            stmt.bindString(7, content_img);
        }
 
        String notice_type = entity.getNotice_type();
        if (notice_type != null) {
            stmt.bindString(8, notice_type);
        }
 
        String is_show = entity.getIs_show();
        if (is_show != null) {
            stmt.bindString(9, is_show);
        }
        stmt.bindLong(10, entity.getIsSee() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Message entity) {
        stmt.clearBindings();
 
        String sys_notice_id = entity.getSys_notice_id();
        if (sys_notice_id != null) {
            stmt.bindString(1, sys_notice_id);
        }
        stmt.bindLong(2, entity.getCreate_time());
        stmt.bindLong(3, entity.getUpdate_time());
 
        String org_number = entity.getOrg_number();
        if (org_number != null) {
            stmt.bindString(4, org_number);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(5, title);
        }
 
        String content_text = entity.getContent_text();
        if (content_text != null) {
            stmt.bindString(6, content_text);
        }
 
        String content_img = entity.getContent_img();
        if (content_img != null) {
            stmt.bindString(7, content_img);
        }
 
        String notice_type = entity.getNotice_type();
        if (notice_type != null) {
            stmt.bindString(8, notice_type);
        }
 
        String is_show = entity.getIs_show();
        if (is_show != null) {
            stmt.bindString(9, is_show);
        }
        stmt.bindLong(10, entity.getIsSee() ? 1L: 0L);
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public Message readEntity(Cursor cursor, int offset) {
        Message entity = new Message( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // sys_notice_id
            cursor.getLong(offset + 1), // create_time
            cursor.getLong(offset + 2), // update_time
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // org_number
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // title
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // content_text
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // content_img
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // notice_type
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // is_show
            cursor.getShort(offset + 9) != 0 // isSee
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Message entity, int offset) {
        entity.setSys_notice_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setCreate_time(cursor.getLong(offset + 1));
        entity.setUpdate_time(cursor.getLong(offset + 2));
        entity.setOrg_number(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTitle(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setContent_text(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setContent_img(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setNotice_type(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIs_show(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setIsSee(cursor.getShort(offset + 9) != 0);
     }
    
    @Override
    protected final Void updateKeyAfterInsert(Message entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(Message entity) {
        return null;
    }

    @Override
    public boolean hasKey(Message entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}