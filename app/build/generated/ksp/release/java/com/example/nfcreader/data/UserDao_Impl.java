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
import java.lang.Long;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityInsertionAdapter<SecondaryRfidTag> __insertionAdapterOfSecondaryRfidTag;

  private final EntityDeletionOrUpdateAdapter<User> __deletionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<SecondaryRfidTag> __deletionAdapterOfSecondaryRfidTag;

  private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `users` (`id`,`name`,`surname`,`idNumber`,`phoneNumber`,`primaryRfidTag`,`balance`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getSurname() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSurname());
        }
        if (value.getIdNumber() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getIdNumber());
        }
        if (value.getPhoneNumber() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPhoneNumber());
        }
        if (value.getPrimaryRfidTag() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPrimaryRfidTag());
        }
        stmt.bindDouble(7, value.getBalance());
      }
    };
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
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `users` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getId());
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
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `users` SET `id` = ?,`name` = ?,`surname` = ?,`idNumber` = ?,`phoneNumber` = ?,`primaryRfidTag` = ?,`balance` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getSurname() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSurname());
        }
        if (value.getIdNumber() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getIdNumber());
        }
        if (value.getPhoneNumber() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPhoneNumber());
        }
        if (value.getPrimaryRfidTag() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPrimaryRfidTag());
        }
        stmt.bindDouble(7, value.getBalance());
        stmt.bindLong(8, value.getId());
      }
    };
  }

  @Override
  public Object insertUser(final User user, final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfUser.insertAndReturnId(user);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
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
  public Object deleteUser(final User user, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfUser.handle(user);
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
  public Object updateUser(final User user, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUser.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<User>> getAllUsers() {
    final String _sql = "SELECT * FROM users";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"users"}, new Callable<List<User>>() {
      @Override
      public List<User> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSurname = CursorUtil.getColumnIndexOrThrow(_cursor, "surname");
          final int _cursorIndexOfIdNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "idNumber");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfPrimaryRfidTag = CursorUtil.getColumnIndexOrThrow(_cursor, "primaryRfidTag");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final List<User> _result = new ArrayList<User>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final User _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpSurname;
            if (_cursor.isNull(_cursorIndexOfSurname)) {
              _tmpSurname = null;
            } else {
              _tmpSurname = _cursor.getString(_cursorIndexOfSurname);
            }
            final String _tmpIdNumber;
            if (_cursor.isNull(_cursorIndexOfIdNumber)) {
              _tmpIdNumber = null;
            } else {
              _tmpIdNumber = _cursor.getString(_cursorIndexOfIdNumber);
            }
            final String _tmpPhoneNumber;
            if (_cursor.isNull(_cursorIndexOfPhoneNumber)) {
              _tmpPhoneNumber = null;
            } else {
              _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            }
            final String _tmpPrimaryRfidTag;
            if (_cursor.isNull(_cursorIndexOfPrimaryRfidTag)) {
              _tmpPrimaryRfidTag = null;
            } else {
              _tmpPrimaryRfidTag = _cursor.getString(_cursorIndexOfPrimaryRfidTag);
            }
            final double _tmpBalance;
            _tmpBalance = _cursor.getDouble(_cursorIndexOfBalance);
            _item = new User(_tmpId,_tmpName,_tmpSurname,_tmpIdNumber,_tmpPhoneNumber,_tmpPrimaryRfidTag,_tmpBalance);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getUserById(final long userId, final Continuation<? super User> continuation) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSurname = CursorUtil.getColumnIndexOrThrow(_cursor, "surname");
          final int _cursorIndexOfIdNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "idNumber");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfPrimaryRfidTag = CursorUtil.getColumnIndexOrThrow(_cursor, "primaryRfidTag");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final User _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpSurname;
            if (_cursor.isNull(_cursorIndexOfSurname)) {
              _tmpSurname = null;
            } else {
              _tmpSurname = _cursor.getString(_cursorIndexOfSurname);
            }
            final String _tmpIdNumber;
            if (_cursor.isNull(_cursorIndexOfIdNumber)) {
              _tmpIdNumber = null;
            } else {
              _tmpIdNumber = _cursor.getString(_cursorIndexOfIdNumber);
            }
            final String _tmpPhoneNumber;
            if (_cursor.isNull(_cursorIndexOfPhoneNumber)) {
              _tmpPhoneNumber = null;
            } else {
              _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            }
            final String _tmpPrimaryRfidTag;
            if (_cursor.isNull(_cursorIndexOfPrimaryRfidTag)) {
              _tmpPrimaryRfidTag = null;
            } else {
              _tmpPrimaryRfidTag = _cursor.getString(_cursorIndexOfPrimaryRfidTag);
            }
            final double _tmpBalance;
            _tmpBalance = _cursor.getDouble(_cursorIndexOfBalance);
            _result = new User(_tmpId,_tmpName,_tmpSurname,_tmpIdNumber,_tmpPhoneNumber,_tmpPrimaryRfidTag,_tmpBalance);
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

  @Override
  public Object getUserByIdNumber(final String idNumber,
      final Continuation<? super User> continuation) {
    final String _sql = "SELECT * FROM users WHERE idNumber = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (idNumber == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, idNumber);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSurname = CursorUtil.getColumnIndexOrThrow(_cursor, "surname");
          final int _cursorIndexOfIdNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "idNumber");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfPrimaryRfidTag = CursorUtil.getColumnIndexOrThrow(_cursor, "primaryRfidTag");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final User _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpSurname;
            if (_cursor.isNull(_cursorIndexOfSurname)) {
              _tmpSurname = null;
            } else {
              _tmpSurname = _cursor.getString(_cursorIndexOfSurname);
            }
            final String _tmpIdNumber;
            if (_cursor.isNull(_cursorIndexOfIdNumber)) {
              _tmpIdNumber = null;
            } else {
              _tmpIdNumber = _cursor.getString(_cursorIndexOfIdNumber);
            }
            final String _tmpPhoneNumber;
            if (_cursor.isNull(_cursorIndexOfPhoneNumber)) {
              _tmpPhoneNumber = null;
            } else {
              _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            }
            final String _tmpPrimaryRfidTag;
            if (_cursor.isNull(_cursorIndexOfPrimaryRfidTag)) {
              _tmpPrimaryRfidTag = null;
            } else {
              _tmpPrimaryRfidTag = _cursor.getString(_cursorIndexOfPrimaryRfidTag);
            }
            final double _tmpBalance;
            _tmpBalance = _cursor.getDouble(_cursorIndexOfBalance);
            _result = new User(_tmpId,_tmpName,_tmpSurname,_tmpIdNumber,_tmpPhoneNumber,_tmpPrimaryRfidTag,_tmpBalance);
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

  @Override
  public Object getUserByPrimaryRfid(final String rfidTag,
      final Continuation<? super User> continuation) {
    final String _sql = "SELECT * FROM users WHERE primaryRfidTag = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (rfidTag == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, rfidTag);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSurname = CursorUtil.getColumnIndexOrThrow(_cursor, "surname");
          final int _cursorIndexOfIdNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "idNumber");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfPrimaryRfidTag = CursorUtil.getColumnIndexOrThrow(_cursor, "primaryRfidTag");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final User _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpSurname;
            if (_cursor.isNull(_cursorIndexOfSurname)) {
              _tmpSurname = null;
            } else {
              _tmpSurname = _cursor.getString(_cursorIndexOfSurname);
            }
            final String _tmpIdNumber;
            if (_cursor.isNull(_cursorIndexOfIdNumber)) {
              _tmpIdNumber = null;
            } else {
              _tmpIdNumber = _cursor.getString(_cursorIndexOfIdNumber);
            }
            final String _tmpPhoneNumber;
            if (_cursor.isNull(_cursorIndexOfPhoneNumber)) {
              _tmpPhoneNumber = null;
            } else {
              _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            }
            final String _tmpPrimaryRfidTag;
            if (_cursor.isNull(_cursorIndexOfPrimaryRfidTag)) {
              _tmpPrimaryRfidTag = null;
            } else {
              _tmpPrimaryRfidTag = _cursor.getString(_cursorIndexOfPrimaryRfidTag);
            }
            final double _tmpBalance;
            _tmpBalance = _cursor.getDouble(_cursorIndexOfBalance);
            _result = new User(_tmpId,_tmpName,_tmpSurname,_tmpIdNumber,_tmpPhoneNumber,_tmpPrimaryRfidTag,_tmpBalance);
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

  @Override
  public Object getSecondaryRfidTag(final String rfidTag,
      final Continuation<? super SecondaryRfidTag> continuation) {
    final String _sql = "SELECT * FROM secondary_rfid_tags WHERE tagId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (rfidTag == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, rfidTag);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
