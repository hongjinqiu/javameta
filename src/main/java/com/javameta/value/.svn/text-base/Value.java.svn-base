/*
 * Copyright 2004-2014 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package com.javameta.value;

import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import javax.activation.DataHandler;

import com.javameta.constants.SysProperties;
import com.javameta.util.DateTimeUtils;
import com.javameta.util.StringUtils;

/**
 * This is the base class for all value classes.
 * It provides conversion and comparison methods.
 *
 * @author Thomas Mueller
 * @author Noel Grandin
 * @author Nicolas Fortin, Atelier SIG, IRSTV FR CNRS 24888
 */
public abstract class Value {

	/**
	 * The data type is unknown at this time.
	 */
	public static final int UNKNOWN = -1;

	/**
	 * The value type for NULL.
	 */
	public static final int NULL = 0;

	/**
	 * The value type for SHORT values.
	 */
	public static final int SHORT = 3;

	/**
	 * The value type for INT values.
	 */
	public static final int INT = 4;

	/**
	 * The value type for LONG values.
	 */
	public static final int LONG = 5;

	/**
	 * The value type for DECIMAL values.
	 */
	public static final int DECIMAL = 6;

	/**
	 * The value type for DOUBLE values.
	 */
	public static final int DOUBLE = 7;

	/**
	 * The value type for FLOAT values.
	 */
	public static final int FLOAT = 8;

	/**
	 * The value type for TIME values.
	 */
	public static final int TIME = 9;

	/**
	 * The value type for DATE values.
	 */
	public static final int DATE = 10;

	/**
	 * The value type for TIMESTAMP values.
	 */
	public static final int TIMESTAMP = 11;

	/**
	 * The value type for BYTES values.
	 */
	public static final int BYTES = 12;

	/**
	 * The value type for STRING values.
	 */
	public static final int STRING = 13;

	/**
	 * The number of value types.
	 */
	public static final int TYPE_COUNT = 12;

	private static SoftReference<Value[]> softCache = new SoftReference<Value[]>(null);
	private static final BigDecimal MAX_LONG_DECIMAL = BigDecimal.valueOf(Long.MAX_VALUE);
	private static final BigDecimal MIN_LONG_DECIMAL = BigDecimal.valueOf(Long.MIN_VALUE);

	/**
	 * Get the SQL expression for this value.
	 *
	 * @return the SQL expression
	 */
	public abstract String getSQL();

	/**
	 * Get the value type.
	 *
	 * @return the type
	 */
	public abstract int getType();

	/**
	 * Get the precision.
	 *
	 * @return the precision
	 */
	public abstract long getPrecision();

	/**
	 * Get the display size in characters.
	 *
	 * @return the display size
	 */
	public abstract int getDisplaySize();

	/**
	 * Get the memory used by this object.
	 *
	 * @return the memory used in bytes
	 */
	public int getMemory() {
		return DataType.getDataType(getType()).memory;
	}

	/**
	 * Get the value as a string.
	 *
	 * @return the string
	 */
	public abstract String getString();

	/**
	 * Get the value as an object.
	 *
	 * @return the object
	 */
	public abstract Object getObject();

	/**
	 * Set the value as a parameter in a prepared statement.
	 *
	 * @param prep the prepared statement
	 * @param parameterIndex the parameter index
	 */
	public abstract void set(PreparedStatement prep, int parameterIndex) throws SQLException;

	/**
	 * Compare the value with another value of the same type.
	 *
	 * @param v the other value
	 * @param mode the compare mode
	 * @return 0 if both values are equal, -1 if the other value is smaller, and
	 *         1 otherwise
	 */
	protected abstract int compareSecure(Value v, CompareMode mode);

	@Override
	public abstract int hashCode();

	/**
	 * Check if the two values have the same hash code. No data conversion is
	 * made; this method returns false if the other object is not of the same
	 * class. For some values, compareTo may return 0 even if equals return
	 * false. Example: ValueDecimal 0.0 and 0.00.
	 *
	 * @param other the other value
	 * @return true if they are equal
	 */
	@Override
	public abstract boolean equals(Object other);

	/**
	 * Get the order of this value type.
	 *
	 * @param type the value type
	 * @return the order number
	 */
	static int getOrder(int type) {
		switch (type) {
		case UNKNOWN:
			return 1;
		case NULL:
			return 2;
		case STRING:
			return 10;
		case SHORT:
			return 22;
		case INT:
			return 23;
		case LONG:
			return 24;
		case DECIMAL:
			return 25;
		case FLOAT:
			return 26;
		case DOUBLE:
			return 27;
		case TIME:
			return 30;
		case DATE:
			return 31;
		case TIMESTAMP:
			return 32;
		case BYTES:
			return 40;
		default:
			throw new RuntimeException("type:" + type);
		}
	}

	/**
	 * Get the higher value order type of two value types. If values need to be
	 * converted to match the other operands value type, the value with the
	 * lower order is converted to the value with the higher order.
	 *
	 * @param t1 the first value type
	 * @param t2 the second value type
	 * @return the higher value type of the two
	 */
	public static int getHigherOrder(int t1, int t2) {
		if (t1 == Value.UNKNOWN || t2 == Value.UNKNOWN) {
			if (t1 == t2) {
				throw new RuntimeException("UNKNOWN_DATA_TYPE:" + t1 + "," + t2);
			} else if (t1 == Value.NULL) {
				throw new RuntimeException("UNKNOWN_DATA_TYPE:null," + t2);
			} else if (t2 == Value.NULL) {
				throw new RuntimeException("UNKNOWN_DATA_TYPE:" + t1 + ",null");
			}
		}
		if (t1 == t2) {
			return t1;
		}
		int o1 = getOrder(t1);
		int o2 = getOrder(t2);
		return o1 > o2 ? t1 : t2;
	}

	/**
	 * Check if a value is in the cache that is equal to this value. If yes,
	 * this value should be used to save memory. If the value is not in the
	 * cache yet, it is added.
	 *
	 * @param v the value to look for
	 * @return the value in the cache or the value passed
	 */
	static Value cache(Value v) {
		if (SysProperties.OBJECT_CACHE) {
			int hash = v.hashCode();
			if (softCache == null) {
				softCache = new SoftReference<Value[]>(null);
			}
			Value[] cache = softCache.get();
			if (cache == null) {
				cache = new Value[SysProperties.OBJECT_CACHE_SIZE];
				softCache = new SoftReference<Value[]>(cache);
			}
			int index = hash & (SysProperties.OBJECT_CACHE_SIZE - 1);
			Value cached = cache[index];
			if (cached != null) {
				if (cached.getType() == v.getType() && v.equals(cached)) {
					// cacheHit++;
					return cached;
				}
			}
			// cacheMiss++;
			// cache[cacheCleaner] = null;
			// cacheCleaner = (cacheCleaner + 1) &
			//     (Constants.OBJECT_CACHE_SIZE - 1);
			cache[index] = v;
		}
		return v;
	}

	/**
	 * Clear the value cache. Used for testing.
	 */
	public static void clearCache() {
		softCache = null;
	}

	public java.util.Date getDate() {
		return ((ValueDate) convertTo(Value.DATE)).getDate();
	}

	public Time getTime() {
		return ((ValueTime) convertTo(Value.TIME)).getTime();
	}

	public Timestamp getTimestamp() {
		return ((ValueTimestamp) convertTo(Value.TIMESTAMP)).getTimestamp();
	}

	public byte[] getBytes() {
		return ((ValueBytes) convertTo(Value.BYTES)).getBytes();
	}

	public byte[] getBytesNoCopy() {
		return ((ValueBytes) convertTo(Value.BYTES)).getBytesNoCopy();
	}

	public short getShort() {
		return ((ValueShort) convertTo(Value.SHORT)).getShort();
	}

	public BigDecimal getBigDecimal() {
		return ((ValueDecimal) convertTo(Value.DECIMAL)).getBigDecimal();
	}

	public double getDouble() {
		return ((ValueDouble) convertTo(Value.DOUBLE)).getDouble();
	}

	public float getFloat() {
		return ((ValueFloat) convertTo(Value.FLOAT)).getFloat();
	}

	public int getInt() {
		return ((ValueInt) convertTo(Value.INT)).getInt();
	}

	public long getLong() {
		return ((ValueLong) convertTo(Value.LONG)).getLong();
	}

	/**
	 * Add a value and return the result.
	 *
	 * @param v the value to add
	 * @return the result
	 */
	public Value add(Value v) {
		throw throwUnsupportedExceptionForType("+");
	}

	public int getSignum() {
		throw throwUnsupportedExceptionForType("SIGNUM");
	}

	/**
	 * Return -value if this value support arithmetic operations.
	 *
	 * @return the negative
	 */
	public Value negate() {
		throw throwUnsupportedExceptionForType("NEG");
	}

	/**
	 * Subtract a value and return the result.
	 *
	 * @param v the value to subtract
	 * @return the result
	 */
	public Value subtract(Value v) {
		throw throwUnsupportedExceptionForType("-");
	}

	/**
	 * Divide by a value and return the result.
	 *
	 * @param v the value to divide by
	 * @return the result
	 */
	public Value divide(Value v) {
		throw throwUnsupportedExceptionForType("/");
	}

	/**
	 * Multiply with a value and return the result.
	 *
	 * @param v the value to multiply with
	 * @return the result
	 */
	public Value multiply(Value v) {
		throw throwUnsupportedExceptionForType("*");
	}

	/**
	 * Take the modulus with a value and return the result.
	 *
	 * @param v the value to take the modulus with
	 * @return the result
	 */
	public Value modulus(Value v) {
		throw throwUnsupportedExceptionForType("%");
	}

	/**
	 * Compare a value to the specified type.
	 *
	 * @param targetType the type of the returned value
	 * @return the converted value
	 */
	public Value convertTo(int targetType) {
		// converting NULL is done in ValueNull
		// converting BLOB to CLOB and vice versa is done in ValueLob
		if (getType() == targetType) {
			return this;
		}
		try {
			// decimal conversion
			switch (targetType) {
			case SHORT: {
				switch (getType()) {
				case INT:
					return ValueShort.get(convertToShort(getInt()));
				case LONG:
					return ValueShort.get(convertToShort(getLong()));
				case DECIMAL:
					return ValueShort.get(convertToShort(convertToLong(getBigDecimal())));
				case DOUBLE:
					return ValueShort.get(convertToShort(convertToLong(getDouble())));
				case FLOAT:
					return ValueShort.get(convertToShort(convertToLong(getFloat())));
				case BYTES:
					return ValueShort.get((short) Integer.parseInt(getString(), 16));
				}
				break;
			}
			case INT: {
				switch (getType()) {
				case SHORT:
					return ValueInt.get(getShort());
				case LONG:
					return ValueInt.get(convertToInt(getLong()));
				case DECIMAL:
					return ValueInt.get(convertToInt(convertToLong(getBigDecimal())));
				case DOUBLE:
					return ValueInt.get(convertToInt(convertToLong(getDouble())));
				case FLOAT:
					return ValueInt.get(convertToInt(convertToLong(getFloat())));
				case BYTES:
					return ValueInt.get((int) Long.parseLong(getString(), 16));
				}
				break;
			}
			case LONG: {
				switch (getType()) {
				case SHORT:
					return ValueLong.get(getShort());
				case INT:
					return ValueLong.get(getInt());
				case DECIMAL:
					return ValueLong.get(convertToLong(getBigDecimal()));
				case DOUBLE:
					return ValueLong.get(convertToLong(getDouble()));
				case FLOAT:
					return ValueLong.get(convertToLong(getFloat()));
				case BYTES: {
					return ValueLong.get(Long.parseLong(getString(), 16));
				}
				}
				break;
			}
			case DECIMAL: {
				switch (getType()) {
				case SHORT:
					return ValueDecimal.get(BigDecimal.valueOf(getShort()));
				case INT:
					return ValueDecimal.get(BigDecimal.valueOf(getInt()));
				case LONG:
					return ValueDecimal.get(BigDecimal.valueOf(getLong()));
				case DOUBLE: {
					double d = getDouble();
					if (Double.isInfinite(d) || Double.isNaN(d)) {
						throw new RuntimeException("DATA_CONVERSION_ERROR:" + d);
					}
					return ValueDecimal.get(BigDecimal.valueOf(d));
				}
				case FLOAT: {
					float f = getFloat();
					if (Float.isInfinite(f) || Float.isNaN(f)) {
						throw new RuntimeException("DATA_CONVERSION_ERROR:" + f);
					}
					// better rounding behavior than BigDecimal.valueOf(f)
					return ValueDecimal.get(new BigDecimal(Float.toString(f)));
				}
				}
				break;
			}
			case DOUBLE: {
				switch (getType()) {
				case SHORT:
					return ValueDouble.get(getShort());
				case INT:
					return ValueDouble.get(getInt());
				case LONG:
					return ValueDouble.get(getLong());
				case DECIMAL:
					return ValueDouble.get(getBigDecimal().doubleValue());
				case FLOAT:
					return ValueDouble.get(getFloat());
				}
				break;
			}
			case FLOAT: {
				switch (getType()) {
				case SHORT:
					return ValueFloat.get(getShort());
				case INT:
					return ValueFloat.get(getInt());
				case LONG:
					return ValueFloat.get(getLong());
				case DECIMAL:
					return ValueFloat.get(getBigDecimal().floatValue());
				case DOUBLE:
					return ValueFloat.get((float) getDouble());
				}
				break;
			}
			case DATE: {
				switch (getType()) {
				case TIME:
					// because the time has set the date to 1970-01-01,
					// this will be the result
					return ValueDate.fromDateValue(DateTimeUtils.dateValue(1970, 1, 1));
				case TIMESTAMP:
					return ValueDate.fromDateValue(((ValueTimestamp) this).getDateValue());
				}
				break;
			}
			case TIME: {
				switch (getType()) {
				case DATE:
					// need to normalize the year, month and day because a date
					// has the time set to 0, the result will be 0
					return ValueTime.fromNanos(0);
				case TIMESTAMP:
					return ValueTime.fromNanos(((ValueTimestamp) this).getTimeNanos());
				}
				break;
			}
			case TIMESTAMP: {
				switch (getType()) {
				case TIME:
					return DateTimeUtils.normalizeTimestamp(0, ((ValueTime) this).getNanos());
				case DATE:
					return ValueTimestamp.fromDateValueAndNanos(((ValueDate) this).getDateValue(), 0);
				}
				break;
			}
			case BYTES: {
				switch (getType()) {
				case SHORT: {
					int x = getShort();
					return ValueBytes.getNoCopy(new byte[] { (byte) (x >> 8), (byte) x });
				}
				case INT: {
					int x = getInt();
					return ValueBytes.getNoCopy(new byte[] { (byte) (x >> 24), (byte) (x >> 16), (byte) (x >> 8), (byte) x });
				}
				case LONG: {
					long x = getLong();
					return ValueBytes.getNoCopy(new byte[] { (byte) (x >> 56), (byte) (x >> 48), (byte) (x >> 40), (byte) (x >> 32), (byte) (x >> 24), (byte) (x >> 16),
							(byte) (x >> 8), (byte) x });
				}
				}
				break;
			}
			}
			// conversion by parsing the string value
			String s = getString();
			switch (targetType) {
			case NULL:
				return ValueNull.INSTANCE;
			case SHORT:
				return ValueShort.get(Short.parseShort(s.trim()));
			case INT:
				return ValueInt.get(Integer.parseInt(s.trim()));
			case LONG:
				return ValueLong.get(Long.parseLong(s.trim()));
			case DECIMAL:
				return ValueDecimal.get(new BigDecimal(s.trim()));
			case TIME:
				return ValueTime.parse(s.trim());
			case DATE:
				return ValueDate.parse(s.trim());
			case TIMESTAMP:
				return ValueTimestamp.parse(s.trim());
			case BYTES:
				return ValueBytes.getNoCopy(StringUtils.convertHexToBytes(s.trim()));
			case STRING:
				return ValueString.get(s);
			case DOUBLE:
				return ValueDouble.get(Double.parseDouble(s.trim()));
			case FLOAT:
				return ValueFloat.get(Float.parseFloat(s.trim()));
				//                return ValueGeometry.get(s);
			default:
				throw new RuntimeException("type=" + targetType);
			}
		} catch (NumberFormatException e) {
			throw new RuntimeException("DATA_CONVERSION_ERROR:" + getString(), e);
		}
	}

	/**
	 * Compare this value against another value given that the values are of the
	 * same data type.
	 *
	 * @param v the other value
	 * @param mode the compare mode
	 * @return 0 if both values are equal, -1 if the other value is smaller, and
	 *         1 otherwise
	 */
	public final int compareTypeSave(Value v, CompareMode mode) {
		if (this == v) {
			return 0;
		} else if (this == ValueNull.INSTANCE) {
			return -1;
		} else if (v == ValueNull.INSTANCE) {
			return 1;
		}
		return compareSecure(v, mode);
	}

	/**
	 * Compare this value against another value using the specified compare
	 * mode.
	 *
	 * @param v the other value
	 * @param mode the compare mode
	 * @return 0 if both values are equal, -1 if the other value is smaller, and
	 *         1 otherwise
	 */
	public final int compareTo(Value v, CompareMode mode) {
		if (this == v) {
			return 0;
		}
		if (this == ValueNull.INSTANCE) {
			return v == ValueNull.INSTANCE ? 0 : -1;
		} else if (v == ValueNull.INSTANCE) {
			return 1;
		}
		if (getType() == v.getType()) {
			return compareSecure(v, mode);
		}
		int t2 = Value.getHigherOrder(getType(), v.getType());
		return convertTo(t2).compareSecure(v.convertTo(t2), mode);
	}

	public int getScale() {
		return 0;
	}

	/**
	 * Convert the scale.
	 *
	 * @param onlyToSmallerScale if the scale should not reduced
	 * @param targetScale the requested scale
	 * @return the value
	 */
	public Value convertScale(boolean onlyToSmallerScale, int targetScale) {
		return this;
	}

	/**
	 * Convert the precision to the requested value. The precision of the
	 * returned value may be somewhat larger than requested, because values with
	 * a fixed precision are not truncated.
	 *
	 * @param precision the new precision
	 * @param force true if losing numeric precision is allowed
	 * @return the new value
	 */
	public Value convertPrecision(long precision, boolean force) {
		return this;
	}

	private static short convertToShort(long x) {
		if (x > Short.MAX_VALUE || x < Short.MIN_VALUE) {
			throw new RuntimeException("NUMERIC_VALUE_OUT_OF_RANGE:" + Long.toString(x));
		}
		return (short) x;
	}

	private static int convertToInt(long x) {
		if (x > Integer.MAX_VALUE || x < Integer.MIN_VALUE) {
			throw new RuntimeException("NUMERIC_VALUE_OUT_OF_RANGE:" + Long.toString(x));
		}
		return (int) x;
	}

	private static long convertToLong(double x) {
		if (x > Long.MAX_VALUE || x < Long.MIN_VALUE) {
			// TODO document that +Infinity, -Infinity throw an exception and
			// NaN returns 0
			throw new RuntimeException("NUMERIC_VALUE_OUT_OF_RANGE:" + Double.toString(x));
		}
		return Math.round(x);
	}

	private static long convertToLong(BigDecimal x) {
		if (x.compareTo(MAX_LONG_DECIMAL) > 0 || x.compareTo(Value.MIN_LONG_DECIMAL) < 0) {
			throw new RuntimeException("NUMERIC_VALUE_OUT_OF_RANGE:" + x.toString());
		}
		return x.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
	}

	/**
	 * Check if the precision is smaller or equal than the given precision.
	 *
	 * @param precision the maximum precision
	 * @return true if the precision of this value is smaller or equal to the
	 *         given precision
	 */
	public boolean checkPrecision(long precision) {
		return getPrecision() <= precision;
	}

	/**
	 * Get a medium size SQL expression for debugging or tracing. If the
	 * precision is too large, only a subset of the value is returned.
	 *
	 * @return the SQL expression
	 */
	public String getTraceSQL() {
		return getSQL();
	}

	@Override
	public String toString() {
		return getTraceSQL();
	}

	/**
	 * Throw the exception that the feature is not support for the given data
	 * type.
	 *
	 * @param op the operation
	 * @return never returns normally
	 * @throws DbException the exception
	 */
	protected RuntimeException throwUnsupportedExceptionForType(String op) {
		throw new RuntimeException(DataType.getDataType(getType()).name + " " + op);
	}

	/**
	 * Get the byte array.
	 *
	 * @return the byte array
	 */
	public byte[] getSmall() {
		return null;
	}

	/**
	 * Copy this value to a temporary file if necessary.
	 *
	 * @return the new value
	 */
	public Value copyToTemp() {
		return this;
	}

	/**
	 * Create an independent copy of this value if needed, that will be bound to
	 * a result. If the original row is removed, this copy is still readable.
	 *
	 * @return the value (this for small objects)
	 */
	public Value copyToResult() {
		return this;
	}

}
