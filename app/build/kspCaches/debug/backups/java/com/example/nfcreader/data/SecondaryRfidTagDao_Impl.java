package com.example.nfcreader.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SecondaryRfidTagDao_Impl implements SecondaryRfidTagDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SecondaryRfidTag> __insertionAdapterOfSecondaryRfidTag;

  private final EntityDeletionOrUpdateAdapter<SecondaryRfidTag> __deletionAdapterOfSecondaryRfidTag;

  public SecondaryRfidTagDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSecondaryRfidTag = new EntityInsertionAdapter<SecondaryRfidTag>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `secondary_rfid_tags` (`tagId`,`userId`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SecondaryRfidTag value) {
        if (value.getTagId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getTagId());
        }
        stmt.bindLong(2, value.getUserId());
      }
    };
    this.__deletionAdapterOfSecondaryRfidTag = new EntityDeletionOrUpdateAdapter<SecondaryRfidTag>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `secondary_rfid_tags` WHERE `tagId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SecondaryRfidTag value) {
        if (value.getTagId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getTagId());
        }
      }
    };
  }

  @Override
  public Object insertSecondaryRfidTag(final SecondaryRfidTag tag,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSecondaryRfidTag.insert(tag);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteSecondaryRfidTag(final SecondaryRfidTag tag,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSecondaryRfidTag.handle(tag);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object getSecondaryRfidTagsForUser(final long userId,
      final Continuation<? super List<SecondaryRfidTag>> continuation) {
    final String _sql = "SELECT * FROM secondary_rfid_tags WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SecondaryRfidTag>>() {
      @Override
      public List<SecondaryRfidTag> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTagId = CursorUtil.getColumnIndexOrThrow(_cursor, "tagId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final List<SecondaryRfidTag> _result = new ArrayList<SecondaryRfidTag>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final SecondaryRfidTag _item;
            final String _tmpTagId;
            if (_cursor.isNull(_cursorIndexOfTagId)) {
              _tmpTagId = null;
            } else {
              _tmpTagId = _cursor.getString(_cursorIndexOfTagId);
            }
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            _item = new SecondaryRfidTag(_tmpTagId,_tmpUserId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getSecondaryRfidTagByTagId(final String tagId,
      final Continuation<? super SecondaryRfidTag> continuation) {
    final String _sql = "SELECT * FROM secondary_rfid_tags WHERE tagId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tagId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tagId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SecondaryRfidTag>() {
      @Override
      public SecondaryRfidTag call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTagId = CursorUtil.getColumnIndexOrThrow(_cursor, "tagId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final SecondaryRfidTag _result;
          if(_cursor.moveToFirst()) {
            final String _tmpTagId;
            if (_cursor.isNull(_cursorIndexOfTagId)) {
              _tmpTagId = null;
            } else {
              _tmpTagId = _cursor.getString(_cursorIndexOfTagId);
            }
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            _result = new SecondaryRfidTag(_tmpTagId,_tmpUserId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
