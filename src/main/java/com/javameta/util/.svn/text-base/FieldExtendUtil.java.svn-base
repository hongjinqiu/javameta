package com.javameta.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.javameta.JavametaException;
import com.javameta.model.fieldpool.Field;

public class FieldExtendUtil {
	private static Logger logger = Logger.getLogger(FieldExtendUtil.class);

	public static void extendFieldPoolField(com.javameta.model.datasource.Field field, List<Field> fieldLi) {
		com.javameta.model.datasource.Field outField = field;
		if (StringUtils.isNotEmpty(outField.getExtends())) {
			for (Field innerField : fieldLi) {
				if (outField.getExtends().equals(innerField.getId())) {
					Method[] methods = innerField.getClass().getMethods();
					for (Method method : methods) {
						if (method.getName().startsWith("set") && method.getParameterTypes().length == 1) {
							Method outMethod = null;
							String getMethodName = method.getName().replaceFirst("set", "get");
							Method innerGetMethod = null;
							Method outGetMethod = null;
							try {
								innerGetMethod = getMethod(innerField.getClass(), getMethodName);
								outGetMethod = getMethod(outField.getClass(), getMethodName);
								Class<?> parameterCls = method.getParameterTypes()[0];
								if (isMatchClass(parameterCls)) {
									outMethod = getMethod(outField.getClass(), method.getName(), method.getParameterTypes());
								}
							} catch (JavametaException e) {
								logger.error(e.getMessage(), e);
								getMethodName = method.getName().replaceFirst("set", "is");
								try {
									innerGetMethod = getMethod(innerField.getClass(), getMethodName);
									outGetMethod = getMethod(outField.getClass(), getMethodName);
									Class<?> parameterCls = method.getParameterTypes()[0];
									if (isMatchClass(parameterCls)) {
										outMethod = getMethod(outField.getClass(), method.getName(), method.getParameterTypes());
									}
								} catch (JavametaException e1) {
									logger.error("NoSuchMethodException:" + getMethodName, e1);
								}
							}
							if (innerGetMethod == null) {
								continue;
							}
							if (innerGetMethod != null && outGetMethod == null) {
								continue;
							}
							if (outMethod == null) {
								continue;
							}
							try {
								Object innerValue = innerGetMethod.invoke(innerField);
								if (innerValue instanceof String) {
									String outValue = (String) outGetMethod.invoke(outField);
									if (StringUtils.isEmpty(outValue) && StringUtils.isNotEmpty((String) innerValue)) {
										outMethod.invoke(outField, innerValue);
									}
								} else if (innerValue instanceof Integer) {
									Integer outValue = (Integer) outGetMethod.invoke(outField);
									if ((outValue == null || outValue == 0) && (innerValue != null && (Integer) innerValue != 0)) {
										outMethod.invoke(outField, innerValue);
									}
								} else if (innerValue instanceof Boolean) {
									Boolean outValue = (Boolean) outGetMethod.invoke(outField);
									if ((outValue == null) && (innerValue != null)) {
										outMethod.invoke(outField, innerValue);
									}
								} else if (innerValue instanceof Short) {
									Short outValue = (Short) outGetMethod.invoke(outField);
									if ((outValue == null || outValue == 0) && (innerValue != null && (Short) innerValue != 0)) {
										outMethod.invoke(outField, innerValue);
									}
								} else if (innerValue instanceof Long) {
									Long outValue = (Long) outGetMethod.invoke(outField);
									if ((outValue == null || outValue == 0) && (innerValue != null && (Long) innerValue != 0)) {
										outMethod.invoke(outField, innerValue);
									}
								} else if (innerValue instanceof Float) {
									Float outValue = (Float) outGetMethod.invoke(outField);
									if ((outValue == null || outValue == 0) && (innerValue != null && (Float) innerValue != 0)) {
										outMethod.invoke(outField, innerValue);
									}
								} else if (innerValue instanceof Double) {
									Double outValue = (Double) outGetMethod.invoke(outField);
									if ((outValue == null || outValue == 0) && (innerValue != null && (Double) innerValue != 0)) {
										outMethod.invoke(outField, innerValue);
									}
								} else if (innerValue instanceof BigDecimal) {
									BigDecimal outValue = (BigDecimal) outGetMethod.invoke(outField);
									if ((outValue == null || outValue.intValue() == 0) && (innerValue != null && ((BigDecimal) innerValue).intValue() != 0)) {
										outMethod.invoke(outField, innerValue);
									}
								}
							} catch (IllegalArgumentException e) {
								throw new JavametaException(e);
							} catch (IllegalAccessException e) {
								throw new JavametaException(e);
							} catch (InvocationTargetException e) {
								throw new JavametaException(e);
							}
						}
					}
					outField.setDefaultValueExpr(getDefaultValueExpr(outField.getDefaultValueExpr(), innerField.getDefaultValueExpr()));
					outField.setCalcValueExpr(getCalcValueExpr(outField.getCalcValueExpr(), innerField.getCalcValueExpr()));
					outField.setFormatExpr(getFormatExpr(outField.getFormatExpr(), innerField.getFormatExpr()));
					outField.setRelationDS(getRelationDS(outField.getRelationDS(), innerField.getRelationDS()));
				}
			}
		}
	}

	private static boolean isMatchClass(Class<?> cls) {
		boolean result = false;
		result = result || cls == String.class;
		result = result || cls == Integer.class;
		result = result || cls == Boolean.class;
		result = result || cls == Short.class;
		result = result || cls == Long.class;
		result = result || cls == Float.class;
		result = result || cls == Double.class;
		result = result || cls == BigDecimal.class;
		return result;
		/*
		 */
	}

	public static void extendFieldPoolField(Field field, List<Field> fieldLi) {
		Field outField = field;
		if (StringUtils.isNotEmpty(outField.getExtends())) {
			for (Field innerField : fieldLi) {
				if (outField.getExtends().equals(innerField.getId())) {
					Method[] methods = innerField.getClass().getMethods();
					for (Method method : methods) {
						if (method.getName().startsWith("set")) {
							String getMethodName = method.getName().replaceFirst("set", "get");
							Method getMethod = null;
							try {
								getMethod = getMethod(innerField.getClass(), getMethodName);
							} catch (JavametaException e) {
								getMethodName = method.getName().replaceFirst("set", "is");
								getMethod = getMethod(innerField.getClass(), getMethodName);
							}
							try {
								Object innerValue = getMethod.invoke(innerField);
								if (innerValue instanceof String) {
									String outValue = (String) getMethod.invoke(outField);
									if (StringUtils.isEmpty(outValue) && StringUtils.isNotEmpty((String) innerValue)) {
										method.invoke(outField, innerValue);
									}
								} else if (innerValue instanceof Integer) {
									Integer outValue = (Integer) getMethod.invoke(outField);
									if ((outValue == null || outValue == 0) && (innerValue != null && (Integer) innerValue != 0)) {
										method.invoke(outField, innerValue);
									}
								} else if (innerValue instanceof Boolean) {
									Boolean outValue = (Boolean) getMethod.invoke(outField);
									if ((outValue == null) && (innerValue != null)) {
										method.invoke(outField, innerValue);
									}
								} else if (innerValue instanceof Short) {
									Short outValue = (Short) getMethod.invoke(outField);
									if ((outValue == null || outValue == 0) && (innerValue != null && (Short) innerValue != 0)) {
										method.invoke(outField, innerValue);
									}
								} else if (innerValue instanceof Long) {
									Long outValue = (Long) getMethod.invoke(outField);
									if ((outValue == null || outValue == 0) && (innerValue != null && (Long) innerValue != 0)) {
										method.invoke(outField, innerValue);
									}
								} else if (innerValue instanceof Float) {
									Float outValue = (Float) getMethod.invoke(outField);
									if ((outValue == null || outValue == 0) && (innerValue != null && (Float) innerValue != 0)) {
										method.invoke(outField, innerValue);
									}
								} else if (innerValue instanceof Double) {
									Double outValue = (Double) getMethod.invoke(outField);
									if ((outValue == null || outValue == 0) && (innerValue != null && (Double) innerValue != 0)) {
										method.invoke(outField, innerValue);
									}
								} else if (innerValue instanceof BigDecimal) {
									BigDecimal outValue = (BigDecimal) getMethod.invoke(outField);
									if ((outValue == null || outValue.intValue() == 0) && (innerValue != null && ((BigDecimal) innerValue).intValue() != 0)) {
										method.invoke(outField, innerValue);
									}
								}
							} catch (IllegalArgumentException e) {
								throw new JavametaException(e);
							} catch (IllegalAccessException e) {
								throw new JavametaException(e);
							} catch (InvocationTargetException e) {
								throw new JavametaException(e);
							}
						}
					}
					outField.setDefaultValueExpr(getDefaultValueExpr(outField.getDefaultValueExpr(), innerField.getDefaultValueExpr()));
					outField.setCalcValueExpr(getCalcValueExpr(outField.getCalcValueExpr(), innerField.getCalcValueExpr()));
					outField.setFormatExpr(getFormatExpr(outField.getFormatExpr(), innerField.getFormatExpr()));
					outField.setRelationDS(getRelationDS(outField.getRelationDS(), innerField.getRelationDS()));
				}
			}
		}
	}

	private static com.javameta.model.datasource.Field.DefaultValueExpr getDefaultValueExpr(com.javameta.model.datasource.Field.DefaultValueExpr leftDefaultValueExpr,
			Field.DefaultValueExpr defaultValueExpr) {
		if (leftDefaultValueExpr == null) {
			if (defaultValueExpr != null) {
				com.javameta.model.datasource.Field.DefaultValueExpr expr = new com.javameta.model.datasource.Field.DefaultValueExpr();
				try {
					BeanUtils.copyProperties(expr, defaultValueExpr);
				} catch (IllegalAccessException e) {
					throw new JavametaException(e);
				} catch (InvocationTargetException e) {
					throw new JavametaException(e);
				}
				return expr;
			}
			return null;
		}
		if (defaultValueExpr != null) {
			if (StringUtils.isEmpty(leftDefaultValueExpr.getMode()) && StringUtils.isNotEmpty(defaultValueExpr.getMode())) {
				leftDefaultValueExpr.setMode(defaultValueExpr.getMode());
			}
			if (StringUtils.isEmpty(leftDefaultValueExpr.getValue()) && StringUtils.isNotEmpty(defaultValueExpr.getValue())) {
				leftDefaultValueExpr.setValue(defaultValueExpr.getValue());
			}
		}
		return leftDefaultValueExpr;
	}

	private static Field.DefaultValueExpr getDefaultValueExpr(Field.DefaultValueExpr leftDefaultValueExpr, Field.DefaultValueExpr defaultValueExpr) {
		if (leftDefaultValueExpr == null) {
			if (defaultValueExpr != null) {
				return SerializationUtils.clone(defaultValueExpr);
			}
			return null;
		}
		if (defaultValueExpr != null) {
			if (StringUtils.isEmpty(leftDefaultValueExpr.getMode()) && StringUtils.isNotEmpty(defaultValueExpr.getMode())) {
				leftDefaultValueExpr.setMode(defaultValueExpr.getMode());
			}
			if (StringUtils.isEmpty(leftDefaultValueExpr.getValue()) && StringUtils.isNotEmpty(defaultValueExpr.getValue())) {
				leftDefaultValueExpr.setValue(defaultValueExpr.getValue());
			}
		}
		return leftDefaultValueExpr;
	}

	private static com.javameta.model.datasource.Field.CalcValueExpr getCalcValueExpr(com.javameta.model.datasource.Field.CalcValueExpr leftCalcValueExpr,
			Field.CalcValueExpr defaultValueExpr) {
		if (leftCalcValueExpr == null) {
			if (defaultValueExpr != null) {
				com.javameta.model.datasource.Field.CalcValueExpr expr = new com.javameta.model.datasource.Field.CalcValueExpr();
				try {
					BeanUtils.copyProperties(expr, defaultValueExpr);
				} catch (IllegalAccessException e) {
					throw new JavametaException(e);
				} catch (InvocationTargetException e) {
					throw new JavametaException(e);
				}
				return expr;
			}
			return null;
		}
		if (defaultValueExpr != null) {
			if (StringUtils.isEmpty(leftCalcValueExpr.getMode()) && StringUtils.isNotEmpty(defaultValueExpr.getMode())) {
				leftCalcValueExpr.setMode(defaultValueExpr.getMode());
			}
			if (StringUtils.isEmpty(leftCalcValueExpr.getValue()) && StringUtils.isNotEmpty(defaultValueExpr.getValue())) {
				leftCalcValueExpr.setValue(defaultValueExpr.getValue());
			}
		}
		return leftCalcValueExpr;
	}

	private static Field.CalcValueExpr getCalcValueExpr(Field.CalcValueExpr leftCalcValueExpr, Field.CalcValueExpr defaultValueExpr) {
		if (leftCalcValueExpr == null) {
			if (defaultValueExpr != null) {
				return SerializationUtils.clone(defaultValueExpr);
			}
			return null;
		}
		if (defaultValueExpr != null) {
			if (StringUtils.isEmpty(leftCalcValueExpr.getMode()) && StringUtils.isNotEmpty(defaultValueExpr.getMode())) {
				leftCalcValueExpr.setMode(defaultValueExpr.getMode());
			}
			if (StringUtils.isEmpty(leftCalcValueExpr.getValue()) && StringUtils.isNotEmpty(defaultValueExpr.getValue())) {
				leftCalcValueExpr.setValue(defaultValueExpr.getValue());
			}
		}
		return leftCalcValueExpr;
	}

	private static com.javameta.model.datasource.Field.FormatExpr getFormatExpr(com.javameta.model.datasource.Field.FormatExpr leftFormatExpr, Field.FormatExpr defaultValueExpr) {
		if (leftFormatExpr == null) {
			if (defaultValueExpr != null) {
				com.javameta.model.datasource.Field.FormatExpr expr = new com.javameta.model.datasource.Field.FormatExpr();
				try {
					BeanUtils.copyProperties(expr, defaultValueExpr);
				} catch (IllegalAccessException e) {
					throw new JavametaException(e);
				} catch (InvocationTargetException e) {
					throw new JavametaException(e);
				}
				return expr;
			}
			return null;
		}
		if (defaultValueExpr != null) {
			if (StringUtils.isEmpty(leftFormatExpr.getMode()) && StringUtils.isNotEmpty(defaultValueExpr.getMode())) {
				leftFormatExpr.setMode(defaultValueExpr.getMode());
			}
			if (StringUtils.isEmpty(leftFormatExpr.getValue()) && StringUtils.isNotEmpty(defaultValueExpr.getValue())) {
				leftFormatExpr.setValue(defaultValueExpr.getValue());
			}
		}
		return leftFormatExpr;
	}

	private static Field.FormatExpr getFormatExpr(Field.FormatExpr leftFormatExpr, Field.FormatExpr defaultValueExpr) {
		if (leftFormatExpr == null) {
			if (defaultValueExpr != null) {
				return SerializationUtils.clone(defaultValueExpr);
			}
			return null;
		}
		if (defaultValueExpr != null) {
			if (StringUtils.isEmpty(leftFormatExpr.getMode()) && StringUtils.isNotEmpty(defaultValueExpr.getMode())) {
				leftFormatExpr.setMode(defaultValueExpr.getMode());
			}
			if (StringUtils.isEmpty(leftFormatExpr.getValue()) && StringUtils.isNotEmpty(defaultValueExpr.getValue())) {
				leftFormatExpr.setValue(defaultValueExpr.getValue());
			}
		}
		return leftFormatExpr;
	}

	private static Method getMethod(Class<?> cls, String methodName, Class<?>... parameterTypes) {
		try {
			return cls.getMethod(methodName, parameterTypes);
		} catch (SecurityException e) {
			throw new JavametaException(e);
		} catch (NoSuchMethodException e) {
			throw new JavametaException(e);
		}
	}

	private static com.javameta.model.datasource.Field.RelationDS getRelationDS(com.javameta.model.datasource.Field.RelationDS leftRelationDS, Field.RelationDS relationDS) {
		if (leftRelationDS == null) {
			if (relationDS != null) {
				com.javameta.model.datasource.Field.RelationDS ds = copyRelationDS(relationDS);
				return ds;
			}
			return null;
		}
		if (relationDS != null) {
			if (leftRelationDS.getRelationItem().size() == 0 && relationDS.getRelationItem().size() > 0) {
				com.javameta.model.datasource.Field.RelationDS ds = copyRelationDS(relationDS);
				return ds;
			} else if (leftRelationDS.getRelationItem().size() > 0 && relationDS.getRelationItem().size() > 0) {
				// 1.处理继承,
				for (Field.RelationDS.RelationItem relationItem : relationDS.getRelationItem()) {
					com.javameta.model.datasource.Field.RelationDS.RelationItem columnRelationItem = null;
					for (com.javameta.model.datasource.Field.RelationDS.RelationItem leftRelationItem : leftRelationDS.getRelationItem()) {
						if (leftRelationItem.getName().equals(relationItem.getName())) {
							columnRelationItem = leftRelationItem;
							break;
						}
					}
					if (columnRelationItem != null) {
						if (StringUtils.isEmpty(columnRelationItem.getId()) && StringUtils.isNotEmpty(relationItem.getId())) {
							columnRelationItem.setId(relationItem.getId());
						}
						if (StringUtils.isEmpty(columnRelationItem.getRelationModelId()) && StringUtils.isNotEmpty(relationItem.getRelationModelId())) {
							columnRelationItem.setRelationModelId(relationItem.getRelationModelId());
						}
						if (StringUtils.isEmpty(columnRelationItem.getRelationDataSetId()) && StringUtils.isNotEmpty(relationItem.getRelationDataSetId())) {
							columnRelationItem.setRelationDataSetId(relationItem.getRelationDataSetId());
						}
						if (StringUtils.isEmpty(columnRelationItem.getDisplayField()) && StringUtils.isNotEmpty(relationItem.getDisplayField())) {
							columnRelationItem.setDisplayField(relationItem.getDisplayField());
						}
						if (StringUtils.isEmpty(columnRelationItem.getValueField()) && StringUtils.isNotEmpty(relationItem.getValueField())) {
							columnRelationItem.setValueField(relationItem.getValueField());
						}
						columnRelationItem.setRelationExpr(getRelationExpr(columnRelationItem.getRelationExpr(), relationItem.getRelationExpr()));
					}
				}
				// 2.处理新增,leftRelationDS不存在relationDS中的relationItem,直接添加,
				for (Field.RelationDS.RelationItem relationItem : relationDS.getRelationItem()) {
					boolean isInherit = false;
					for (com.javameta.model.datasource.Field.RelationDS.RelationItem leftRelationItem : leftRelationDS.getRelationItem()) {
						if (leftRelationItem.getName().equals(relationItem.getName())) {
							isInherit = true;
							break;
						}
					}
					if (!isInherit) {
						leftRelationDS.getRelationItem().add(copyRelationItem(relationItem));
					}
				}
			}
		}
		return leftRelationDS;
	}

	private static com.javameta.model.datasource.Field.RelationDS copyRelationDS(Field.RelationDS relationDS) {
		com.javameta.model.datasource.Field.RelationDS ds = new com.javameta.model.datasource.Field.RelationDS();
		for (Field.RelationDS.RelationItem relationItem : relationDS.getRelationItem()) {
			ds.getRelationItem().add(copyRelationItem(relationItem));
		}
		return ds;
	}

	private static com.javameta.model.datasource.Field.RelationDS.RelationItem copyRelationItem(Field.RelationDS.RelationItem relationItem) {
		com.javameta.model.datasource.Field.RelationDS.RelationItem dRelationItem = new com.javameta.model.datasource.Field.RelationDS.RelationItem();
		dRelationItem.setId(relationItem.getId());
		dRelationItem.setName(relationItem.getName());
		dRelationItem.setDisplayField(relationItem.getDisplayField());
		dRelationItem.setRelationDataSetId(relationItem.getRelationDataSetId());
		com.javameta.model.datasource.Field.RelationDS.RelationItem.RelationExpr expr = new com.javameta.model.datasource.Field.RelationDS.RelationItem.RelationExpr();
		try {
			BeanUtils.copyProperties(expr, relationItem.getRelationExpr());
		} catch (IllegalAccessException e) {
			throw new JavametaException(e);
		} catch (InvocationTargetException e) {
			throw new JavametaException(e);
		}
		dRelationItem.setRelationExpr(expr);
		dRelationItem.setRelationModelId(relationItem.getRelationModelId());
		dRelationItem.setValueField(relationItem.getValueField());
		return dRelationItem;
	}

	private static Field.RelationDS getRelationDS(Field.RelationDS leftRelationDS, Field.RelationDS relationDS) {
		if (leftRelationDS == null) {
			if (relationDS != null) {
				return SerializationUtils.clone(relationDS);
			}
			return null;
		}
		if (relationDS != null) {
			if (leftRelationDS.getRelationItem().size() == 0 && relationDS.getRelationItem().size() > 0) {
				return SerializationUtils.clone(relationDS);
			} else if (leftRelationDS.getRelationItem().size() > 0 && relationDS.getRelationItem().size() > 0) {
				// 1.处理继承,
				for (Field.RelationDS.RelationItem relationItem : relationDS.getRelationItem()) {
					Field.RelationDS.RelationItem columnRelationItem = null;
					for (Field.RelationDS.RelationItem leftRelationItem : leftRelationDS.getRelationItem()) {
						if (leftRelationItem.getName().equals(relationItem.getName())) {
							columnRelationItem = leftRelationItem;
							break;
						}
					}
					if (columnRelationItem != null) {
						if (StringUtils.isEmpty(columnRelationItem.getId()) && StringUtils.isNotEmpty(relationItem.getId())) {
							columnRelationItem.setId(relationItem.getId());
						}
						if (StringUtils.isEmpty(columnRelationItem.getRelationModelId()) && StringUtils.isNotEmpty(relationItem.getRelationModelId())) {
							columnRelationItem.setRelationModelId(relationItem.getRelationModelId());
						}
						if (StringUtils.isEmpty(columnRelationItem.getRelationDataSetId()) && StringUtils.isNotEmpty(relationItem.getRelationDataSetId())) {
							columnRelationItem.setRelationDataSetId(relationItem.getRelationDataSetId());
						}
						if (StringUtils.isEmpty(columnRelationItem.getDisplayField()) && StringUtils.isNotEmpty(relationItem.getDisplayField())) {
							columnRelationItem.setDisplayField(relationItem.getDisplayField());
						}
						if (StringUtils.isEmpty(columnRelationItem.getValueField()) && StringUtils.isNotEmpty(relationItem.getValueField())) {
							columnRelationItem.setValueField(relationItem.getValueField());
						}
						columnRelationItem.setRelationExpr(getRelationExpr(columnRelationItem.getRelationExpr(), relationItem.getRelationExpr()));
					}
				}
				// 2.处理新增,leftRelationDS不存在relationDS中的relationItem,直接添加,
				for (Field.RelationDS.RelationItem relationItem : relationDS.getRelationItem()) {
					boolean isInherit = false;
					for (Field.RelationDS.RelationItem leftRelationItem : leftRelationDS.getRelationItem()) {
						if (leftRelationItem.getName().equals(relationItem.getName())) {
							isInherit = true;
							break;
						}
					}
					if (!isInherit) {
						leftRelationDS.getRelationItem().add(SerializationUtils.clone(relationItem));
					}
				}
			}
		}
		return leftRelationDS;
	}

	private static com.javameta.model.datasource.Field.RelationDS.RelationItem.RelationExpr getRelationExpr(
			com.javameta.model.datasource.Field.RelationDS.RelationItem.RelationExpr leftRelationExpr, Field.RelationDS.RelationItem.RelationExpr defaultRelationExpr) {
		if (leftRelationExpr == null) {
			if (defaultRelationExpr != null) {
				com.javameta.model.datasource.Field.RelationDS.RelationItem.RelationExpr expr = new com.javameta.model.datasource.Field.RelationDS.RelationItem.RelationExpr();
				try {
					BeanUtils.copyProperties(expr, defaultRelationExpr);
				} catch (IllegalAccessException e) {
					throw new JavametaException(e);
				} catch (InvocationTargetException e) {
					throw new JavametaException(e);
				}
				return expr;
			}
			return null;
		}
		if (defaultRelationExpr != null) {
			if (StringUtils.isEmpty(leftRelationExpr.getMode()) && StringUtils.isNotEmpty(defaultRelationExpr.getMode())) {
				leftRelationExpr.setMode(defaultRelationExpr.getMode());
			}
			if (StringUtils.isEmpty(leftRelationExpr.getValue()) && StringUtils.isNotEmpty(defaultRelationExpr.getValue())) {
				leftRelationExpr.setValue(defaultRelationExpr.getValue());
			}
		}
		return leftRelationExpr;
	}

	private static Field.RelationDS.RelationItem.RelationExpr getRelationExpr(Field.RelationDS.RelationItem.RelationExpr leftRelationExpr,
			Field.RelationDS.RelationItem.RelationExpr defaultRelationExpr) {
		if (leftRelationExpr == null) {
			if (defaultRelationExpr != null) {
				return SerializationUtils.clone(defaultRelationExpr);
			}
			return null;
		}
		if (defaultRelationExpr != null) {
			if (StringUtils.isEmpty(leftRelationExpr.getMode()) && StringUtils.isNotEmpty(defaultRelationExpr.getMode())) {
				leftRelationExpr.setMode(defaultRelationExpr.getMode());
			}
			if (StringUtils.isEmpty(leftRelationExpr.getValue()) && StringUtils.isNotEmpty(defaultRelationExpr.getValue())) {
				leftRelationExpr.setValue(defaultRelationExpr.getValue());
			}
		}
		return leftRelationExpr;
	}

	public static void main(String[] args) {
		Method m = getMethod(Field.class, "getDictionary");
	}
}
