/*
 * Copyright 2004-2014 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package com.javameta.value;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.javameta.util.New;

/**
 * This class contains meta data information about data types,
 * and can convert between Java objects and Values.
 */
public class DataType {

	/**
	 * This constant is used to represent the type of a ResultSet. There is no
	 * equivalent java.sql.Types value, but Oracle uses it to represent a
	 * ResultSet (OracleTypes.CURSOR = -10).
	 */
	public static final int TYPE_RESULT_SET = -10;

	/**
	 * The list of types. An ArrayList so that Tomcat doesn't set it to null
	 * when clearing references.
	 */
	private static final ArrayList<DataType> TYPES = New.arrayList();
	private static final HashMap<String, DataType> TYPES_BY_NAME = New.hashMap();
	private static final ArrayList<DataType> TYPES_BY_VALUE_TYPE = New.arrayList();

	/**
	 * The value type of this data type.
	 */
	public int type;

	/**
	 * The data type name.
	 */
	public String name;

	/**
	 * The SQL type.
	 */
	public int sqlType;

	/**
	 * The Java class name.
	 */
	public String jdbc;

	/**
	 * How closely the data type maps to the corresponding JDBC SQL type (low is
	 * best).
	 */
	public int sqlTypePos;

	/**
	 * The maximum supported precision.
	 */
	public long maxPrecision;

	/**
	 * The lowest possible scale.
	 */
	public int minScale;

	/**
	 * The highest possible scale.
	 */
	public int maxScale;

	/**
	 * If this is a numeric type.
	 */
	public boolean decimal;

	/**
	 * The prefix required for the SQL literal representation.
	 */
	public String prefix;

	/**
	 * The suffix required for the SQL literal representation.
	 */
	public String suffix;

	/**
	 * The list of parameters used in the column definition.
	 */
	public String params;

	/**
	 * If this is an autoincrement type.
	 */
	public boolean autoIncrement;

	/**
	 * If this data type is an autoincrement type.
	 */
	public boolean caseSensitive;

	/**
	 * If the precision parameter is supported.
	 */
	public boolean supportsPrecision;

	/**
	 * If the scale parameter is supported.
	 */
	public boolean supportsScale;

	/**
	 * The default precision.
	 */
	public long defaultPrecision;

	/**
	 * The default scale.
	 */
	public int defaultScale;

	/**
	 * The default display size.
	 */
	public int defaultDisplaySize;

	/**
	 * If this data type should not be listed in the database meta data.
	 */
	public boolean hidden;

	/**
	 * The number of bytes required for an object.
	 */
	public int memory;

	static {
		for (int i = 0; i < Value.TYPE_COUNT; i++) {
			TYPES_BY_VALUE_TYPE.add(null);
		}
		add(Value.NULL, Types.NULL, "Null", new DataType(), new String[] { "NULL" },
		// the value is always in the cache
				0);
		add(Value.STRING, Types.VARCHAR, "String", createString(true), new String[] { "VARCHAR", "VARCHAR2", "NVARCHAR", "NVARCHAR2", "VARCHAR_CASESENSITIVE", "CHARACTER VARYING",
				"TID" },
		// 24 for ValueString, 24 for String
				48);
		add(Value.STRING, Types.LONGVARCHAR, "String", createString(true), new String[] { "LONGVARCHAR", "LONGNVARCHAR" }, 48);
		add(Value.SHORT, Types.SMALLINT, "Short", createDecimal(ValueShort.PRECISION, ValueShort.PRECISION, 0, ValueShort.DISPLAY_SIZE, false, false), new String[] { "SMALLINT",
				"YEAR", "INT2" },
		// in many cases the value is in the cache
				20);
		add(Value.INT, Types.INTEGER, "Int", createDecimal(ValueInt.PRECISION, ValueInt.PRECISION, 0, ValueInt.DISPLAY_SIZE, false, false), new String[] { "INTEGER", "INT",
				"MEDIUMINT", "INT4", "SIGNED" },
		// in many cases the value is in the cache
				20);
		add(Value.INT, Types.INTEGER, "Int", createDecimal(ValueInt.PRECISION, ValueInt.PRECISION, 0, ValueInt.DISPLAY_SIZE, false, true), new String[] { "SERIAL" }, 20);
		add(Value.LONG, Types.BIGINT, "Long", createDecimal(ValueLong.PRECISION, ValueLong.PRECISION, 0, ValueLong.DISPLAY_SIZE, false, false), new String[] { "BIGINT", "INT8",
				"LONG" }, 24);
		add(Value.LONG, Types.BIGINT, "Long", createDecimal(ValueLong.PRECISION, ValueLong.PRECISION, 0, ValueLong.DISPLAY_SIZE, false, true), new String[] { "IDENTITY",
				"BIGSERIAL" }, 24);
		add(Value.DECIMAL, Types.DECIMAL, "BigDecimal",
				createDecimal(Integer.MAX_VALUE, ValueDecimal.DEFAULT_PRECISION, ValueDecimal.DEFAULT_SCALE, ValueDecimal.DEFAULT_DISPLAY_SIZE, true, false), new String[] {
						"DECIMAL", "DEC" },
				// 40 for ValueDecimal,
				64);
		add(Value.DECIMAL, Types.NUMERIC, "BigDecimal",
				createDecimal(Integer.MAX_VALUE, ValueDecimal.DEFAULT_PRECISION, ValueDecimal.DEFAULT_SCALE, ValueDecimal.DEFAULT_DISPLAY_SIZE, true, false), new String[] {
						"NUMERIC", "NUMBER" }, 64);
		add(Value.FLOAT, Types.REAL, "Float", createDecimal(ValueFloat.PRECISION, ValueFloat.PRECISION, 0, ValueFloat.DISPLAY_SIZE, false, false),
				new String[] { "REAL", "FLOAT4" }, 24);
		add(Value.DOUBLE, Types.DOUBLE, "Double", createDecimal(ValueDouble.PRECISION, ValueDouble.PRECISION, 0, ValueDouble.DISPLAY_SIZE, false, false), new String[] { "DOUBLE",
				"DOUBLE PRECISION" }, 24);
		add(Value.DOUBLE, Types.FLOAT, "Double", createDecimal(ValueDouble.PRECISION, ValueDouble.PRECISION, 0, ValueDouble.DISPLAY_SIZE, false, false), new String[] { "FLOAT",
				"FLOAT8" }, 24);
		add(Value.TIME, Types.TIME, "Time", createDate(ValueTime.PRECISION, "TIME", 0, ValueTime.DISPLAY_SIZE), new String[] { "TIME" },
		// 24 for ValueTime, 32 for java.sql.Time
				56);
		add(Value.DATE, Types.DATE, "Date", createDate(ValueDate.PRECISION, "DATE", 0, ValueDate.DISPLAY_SIZE), new String[] { "DATE" },
		// 24 for ValueDate, 32 for java.sql.Data
				56);
		add(Value.TIMESTAMP, Types.TIMESTAMP, "Timestamp", createDate(ValueTimestamp.PRECISION, "TIMESTAMP", ValueTimestamp.DEFAULT_SCALE, ValueTimestamp.DISPLAY_SIZE),
				new String[] { "TIMESTAMP", "DATETIME", "DATETIME2", "SMALLDATETIME" },
				// 24 for ValueTimestamp, 32 for java.sql.Timestamp
				56);
		add(Value.BYTES, Types.VARBINARY, "Bytes", createString(false), new String[] { "VARBINARY" }, 32);
		add(Value.BYTES, Types.BINARY, "Bytes", createString(false), new String[] { "BINARY", "RAW", "BYTEA", "LONG RAW" }, 32);
		add(Value.BYTES, Types.LONGVARBINARY, "Bytes", createString(false), new String[] { "LONGVARBINARY" }, 32);
		for (int i = 0, size = TYPES_BY_VALUE_TYPE.size(); i < size; i++) {
			DataType dt = TYPES_BY_VALUE_TYPE.get(i);
			if (dt == null) {
				throw new RuntimeException("unmapped type " + i);
			}
			Value.getOrder(i);
		}
	}

	private static void add(int type, int sqlType, String jdbc, DataType dataType, String[] names, int memory) {
		for (int i = 0; i < names.length; i++) {
			DataType dt = new DataType();
			dt.type = type;
			dt.sqlType = sqlType;
			dt.jdbc = jdbc;
			dt.name = names[i];
			dt.autoIncrement = dataType.autoIncrement;
			dt.decimal = dataType.decimal;
			dt.maxPrecision = dataType.maxPrecision;
			dt.maxScale = dataType.maxScale;
			dt.minScale = dataType.minScale;
			dt.params = dataType.params;
			dt.prefix = dataType.prefix;
			dt.suffix = dataType.suffix;
			dt.supportsPrecision = dataType.supportsPrecision;
			dt.supportsScale = dataType.supportsScale;
			dt.defaultPrecision = dataType.defaultPrecision;
			dt.defaultScale = dataType.defaultScale;
			dt.defaultDisplaySize = dataType.defaultDisplaySize;
			dt.caseSensitive = dataType.caseSensitive;
			dt.hidden = i > 0;
			dt.memory = memory;
			for (DataType t2 : TYPES) {
				if (t2.sqlType == dt.sqlType) {
					dt.sqlTypePos++;
				}
			}
			TYPES_BY_NAME.put(dt.name, dt);
			if (TYPES_BY_VALUE_TYPE.get(type) == null) {
				TYPES_BY_VALUE_TYPE.set(type, dt);
			}
			TYPES.add(dt);
		}
	}

	private static DataType createDecimal(int maxPrecision, int defaultPrecision, int defaultScale, int defaultDisplaySize, boolean needsPrecisionAndScale, boolean autoInc) {
		DataType dataType = new DataType();
		dataType.maxPrecision = maxPrecision;
		dataType.defaultPrecision = defaultPrecision;
		dataType.defaultScale = defaultScale;
		dataType.defaultDisplaySize = defaultDisplaySize;
		if (needsPrecisionAndScale) {
			dataType.params = "PRECISION,SCALE";
			dataType.supportsPrecision = true;
			dataType.supportsScale = true;
		}
		dataType.decimal = true;
		dataType.autoIncrement = autoInc;
		return dataType;
	}

	private static DataType createDate(int precision, String prefix, int scale, int displaySize) {
		DataType dataType = new DataType();
		dataType.prefix = prefix + " '";
		dataType.suffix = "'";
		dataType.maxPrecision = precision;
		dataType.supportsScale = scale != 0;
		dataType.maxScale = scale;
		dataType.defaultPrecision = precision;
		dataType.defaultScale = scale;
		dataType.defaultDisplaySize = displaySize;
		return dataType;
	}

	private static DataType createString(boolean caseSensitive) {
		DataType dataType = new DataType();
		dataType.prefix = "'";
		dataType.suffix = "'";
		dataType.params = "LENGTH";
		dataType.caseSensitive = caseSensitive;
		dataType.supportsPrecision = true;
		dataType.maxPrecision = Integer.MAX_VALUE;
		dataType.defaultPrecision = Integer.MAX_VALUE;
		dataType.defaultDisplaySize = Integer.MAX_VALUE;
		return dataType;
	}

	private static DataType createLob() {
		DataType t = createString(true);
		t.maxPrecision = Long.MAX_VALUE;
		t.defaultPrecision = Long.MAX_VALUE;
		return t;
	}

	/**
	 * Get the list of data types.
	 *
	 * @return the list
	 */
	public static ArrayList<DataType> getTypes() {
		return TYPES;
	}

	/**
	 * Read a value from the given result set.
	 *
	 * @param session the session
	 * @param rs the result set
	 * @param columnIndex the column index (1 based)
	 * @param type the data type
	 * @return the value
	 */
	public static Value readValue(ResultSet rs, int columnIndex, int type) {
		try {
			Value v;
			switch (type) {
			case Value.NULL: {
				return ValueNull.INSTANCE;
			}
			case Value.BYTES: {
				byte[] buff = rs.getBytes(columnIndex);
				v = buff == null ? (Value) ValueNull.INSTANCE : ValueBytes.getNoCopy(buff);
				break;
			}
			case Value.DATE: {
				Date value = rs.getDate(columnIndex);
				v = value == null ? (Value) ValueNull.INSTANCE : ValueDate.get(value);
				break;
			}
			case Value.TIME: {
				Time value = rs.getTime(columnIndex);
				v = value == null ? (Value) ValueNull.INSTANCE : ValueTime.get(value);
				break;
			}
			case Value.TIMESTAMP: {
				Timestamp value = rs.getTimestamp(columnIndex);
				v = value == null ? (Value) ValueNull.INSTANCE : ValueTimestamp.get(value);
				break;
			}
			case Value.DECIMAL: {
				BigDecimal value = rs.getBigDecimal(columnIndex);
				v = value == null ? (Value) ValueNull.INSTANCE : ValueDecimal.get(value);
				break;
			}
			case Value.DOUBLE: {
				double value = rs.getDouble(columnIndex);
				v = rs.wasNull() ? (Value) ValueNull.INSTANCE : ValueDouble.get(value);
				break;
			}
			case Value.FLOAT: {
				float value = rs.getFloat(columnIndex);
				v = rs.wasNull() ? (Value) ValueNull.INSTANCE : ValueFloat.get(value);
				break;
			}
			case Value.INT: {
				int value = rs.getInt(columnIndex);
				v = rs.wasNull() ? (Value) ValueNull.INSTANCE : ValueInt.get(value);
				break;
			}
			case Value.LONG: {
				long value = rs.getLong(columnIndex);
				v = rs.wasNull() ? (Value) ValueNull.INSTANCE : ValueLong.get(value);
				break;
			}
			case Value.SHORT: {
				short value = rs.getShort(columnIndex);
				v = rs.wasNull() ? (Value) ValueNull.INSTANCE : ValueShort.get(value);
				break;
			}
			case Value.STRING: {
				String s = rs.getString(columnIndex);
				v = (s == null) ? (Value) ValueNull.INSTANCE : ValueString.get(s);
				break;
			}
			default:
				throw new RuntimeException("type=" + type);
			}
			return v;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get the name of the Java class for the given value type.
	 *
	 * @param type the value type
	 * @return the class name
	 */
	public static String getTypeClassName(int type) {
		switch (type) {
		case Value.SHORT:
			// "java.lang.Short";
			return Short.class.getName();
		case Value.INT:
			// "java.lang.Integer";
			return Integer.class.getName();
		case Value.LONG:
			// "java.lang.Long";
			return Long.class.getName();
		case Value.DECIMAL:
			// "java.math.BigDecimal";
			return BigDecimal.class.getName();
		case Value.TIME:
			// "java.sql.Time";
			return Time.class.getName();
		case Value.DATE:
			// "java.sql.Date";
			return Date.class.getName();
		case Value.TIMESTAMP:
			// "java.sql.Timestamp";
			return Timestamp.class.getName();
		case Value.BYTES:
			// "[B", not "byte[]";
			return byte[].class.getName();
		case Value.STRING:
			// "java.lang.String";
			return String.class.getName();
		case Value.DOUBLE:
			// "java.lang.Double";
			return Double.class.getName();
		case Value.FLOAT:
			// "java.lang.Float";
			return Float.class.getName();
		case Value.NULL:
			return null;
		case Value.UNKNOWN:
			// anything
			return Object.class.getName();
		default:
			throw new RuntimeException("type=" + type);
		}
	}

	/**
	 * Get the data type object for the given value type.
	 *
	 * @param type the value type
	 * @return the data type object
	 */
	public static DataType getDataType(int type) {
		if (type == Value.UNKNOWN) {
			throw new RuntimeException("UNKNOWN_DATA_TYPE:" + type);
		}
		DataType dt = TYPES_BY_VALUE_TYPE.get(type);
		if (dt == null) {
			dt = TYPES_BY_VALUE_TYPE.get(Value.NULL);
		}
		return dt;
	}

	/**
	 * Convert a value type to a SQL type.
	 *
	 * @param type the value type
	 * @return the SQL type
	 */
	public static int convertTypeToSQLType(int type) {
		return getDataType(type).sqlType;
	}

	/**
	 * Get the SQL type from the result set meta data for the given column. This
	 * method uses the SQL type and type name.
	 *
	 * @param meta the meta data
	 * @param columnIndex the column index (1, 2,...)
	 * @return the value type
	 */
	public static int getValueTypeFromResultSet(ResultSetMetaData meta, int columnIndex) throws SQLException {
		return convertSQLTypeToValueType(meta.getColumnType(columnIndex));
	}

	/**
	 * Convert a SQL type to a value type.
	 *
	 * @param sqlType the SQL type
	 * @return the value type
	 */
	public static int convertSQLTypeToValueType(int sqlType) {
		switch (sqlType) {
		case Types.CHAR:
		case Types.VARCHAR:
		case Types.LONGVARCHAR:
		case Types.NVARCHAR:
		case Types.LONGNVARCHAR:
			return Value.STRING;
		case Types.NUMERIC:
		case Types.DECIMAL:
			return Value.DECIMAL;
		case Types.BIT:
		case Types.BOOLEAN:
			throw new RuntimeException("unsupport type:" + sqlType);
		case Types.INTEGER:
			return Value.INT;
		case Types.SMALLINT:
			return Value.SHORT;
		case Types.TINYINT:
			throw new RuntimeException("unsupport type:" + sqlType);
		case Types.BIGINT:
			return Value.LONG;
		case Types.REAL:
			return Value.FLOAT;
		case Types.DOUBLE:
		case Types.FLOAT:
			return Value.DOUBLE;
		case Types.BINARY:
		case Types.VARBINARY:
		case Types.LONGVARBINARY:
			return Value.BYTES;
		case Types.OTHER:
		case Types.JAVA_OBJECT:
			throw new RuntimeException("unsupport type:" + sqlType);
		case Types.DATE:
			return Value.DATE;
		case Types.TIME:
			return Value.TIME;
		case Types.TIMESTAMP:
			return Value.TIMESTAMP;
		case Types.BLOB:
			throw new RuntimeException("unsupport type:" + sqlType);
		case Types.CLOB:
		case Types.NCLOB:
			throw new RuntimeException("unsupport type:" + sqlType);
		case Types.NULL:
			return Value.NULL;
		case Types.ARRAY:
			throw new RuntimeException("unsupport type:" + sqlType);
		case DataType.TYPE_RESULT_SET:
			throw new RuntimeException("unsupport type:" + sqlType);
		default:
			throw new RuntimeException("UNKNOWN_DATA_TYPE:" + sqlType);
		}
	}

	/**
	 * Convert a Java object to a value.
	 *
	 * @param session the session
	 * @param x the value
	 * @return the value
	 */
	public static Value convertToValue(Object x) {
		if (x == null) {
			return ValueNull.INSTANCE;
		}
		if (x instanceof String) {
			return ValueString.get((String) x);
		} else if (x instanceof Value) {
			return (Value) x;
		} else if (x instanceof Long) {
			return ValueLong.get(((Long) x).longValue());
		} else if (x instanceof Integer) {
			return ValueInt.get(((Integer) x).intValue());
		} else if (x instanceof BigDecimal) {
			return ValueDecimal.get((BigDecimal) x);
		} else if (x instanceof Boolean) {
			throw new RuntimeException("unsupport boolean");
		} else if (x instanceof Byte) {
			throw new RuntimeException("unsupport byte");
		} else if (x instanceof Short) {
			return ValueShort.get(((Short) x).shortValue());
		} else if (x instanceof Float) {
			return ValueFloat.get(((Float) x).floatValue());
		} else if (x instanceof Double) {
			return ValueDouble.get(((Double) x).doubleValue());
		} else if (x instanceof byte[]) {
			return ValueBytes.get((byte[]) x);
		} else if (x instanceof Date) {
			return ValueDate.get((Date) x);
		} else if (x instanceof Time) {
			return ValueTime.get((Time) x);
		} else if (x instanceof Timestamp) {
			return ValueTimestamp.get((Timestamp) x);
		} else if (x instanceof java.util.Date) {
			return ValueTimestamp.fromMillis(((java.util.Date) x).getTime());
		} else if (x instanceof java.io.Reader) {
			throw new RuntimeException("unsupport java.io.Reader");
		} else if (x instanceof java.sql.Clob) {
			throw new RuntimeException("unsupport java.sql.Clob");
		} else if (x instanceof java.io.InputStream) {
			throw new RuntimeException("unsupport java.io.InputStream");
		} else if (x instanceof java.sql.Blob) {
			throw new RuntimeException("unsupport java.sql.Blob");
		} else if (x instanceof ResultSet) {
			throw new RuntimeException("unsupport ResultSet");
		} else if (x instanceof UUID) {
			throw new RuntimeException("unsupport UUID");
		} else if (x instanceof Object[]) {
			throw new RuntimeException("unsupport Object[]");
		} else if (x instanceof Character) {
			throw new RuntimeException("unsupport Character");
		} else {
			throw new RuntimeException("unsupport type:" + x.getClass().getName());
		}
	}

	/**
	 * Get a data type object from a type name.
	 *
	 * @param s the type name
	 * @return the data type object
	 */
	public static DataType getTypeByName(String s) {
		return TYPES_BY_NAME.get(s);
	}

	/**
	 * Check if the given value type is a String (VARCHAR,...).
	 *
	 * @param type the value type
	 * @return true if the value type is a String type
	 */
	public static boolean isStringType(int type) {
		if (type == Value.STRING) {
			return true;
		}
		return false;
	}

}
