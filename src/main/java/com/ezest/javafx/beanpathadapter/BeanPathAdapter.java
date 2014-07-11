package com.ezest.javafx.beanpathadapter;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;

/**
 * An adapter that takes a POJO bean and internally and recursively
 * binds/un-binds it's fields to other {@linkplain Property} components. It
 * allows a <b><code>.</code></b> separated field path to be traversed on a bean
 * until the final field name is found (last entry in the <b><code>.</code></b>
 * separated field path). Each field will have a corresponding
 * {@linkplain Property} that is automatically generated and reused in the
 * binding process. Each {@linkplain Property} is bean-aware and will
 * dynamically update it's values and bindings as different beans are set on the
 * adapter. Bean's set on the adapter do not need to instantiate all the
 * sub-beans in the path(s) provided as long as they contain a no-argument
 * constructor they will be instantiated as path(s) are traversed.
 * 
 * <p>
 * For example, assume there is a <code>Person</code> class that has a field for
 * an <code>Address</code> class which in turn has a field for a
 * <code>city</code>.
 * <p>
 * If the <code>city</code> field needs to be bound to a JavaFX control
 * {@linkplain Property} for UI updates/viewing it can be accomplished in the
 * following manner:
 * </p>
 * <p>
 * <code>TextField tf = new TextField();<br/>
 * Person person = new Person();<br/>
 * BeanPathAdapter<Person> pbpa = new BeanPathAdapter<Person>(person);<br/> 
 * pbpa.bindBidirectional("address.city", tf.textProperty());<br/> 
 * </code>
 * </p>
 * </p>
 * 
 * @see #bindBidirectional(String, Property)
 * @param <B>
 *            the bean type
 */
public class BeanPathAdapter<B> {

	private FieldBean<Void, B> root;

	/**
	 * Constructor
	 * 
	 * @param bean
	 *            the bean the {@linkplain BeanPathAdapter} is for
	 */
	public BeanPathAdapter(final B bean) {
		setBean(bean);
	}

	/**
	 * @see #bindBidirectional(String, Property, Class)
	 */
	public void bindBidirectional(final String fieldPath,
			final BooleanProperty property) {
		bindBidirectional(fieldPath, property, Boolean.class);
	}

	/**
	 * @see #bindBidirectional(String, Property, Class)
	 */
	public void bindBidirectional(final String fieldPath,
			final StringProperty property) {
		bindBidirectional(fieldPath, property, String.class);
	}

	/**
	 * @see #bindBidirectional(String, Property, Class)
	 */
	public void bindBidirectional(final String fieldPath,
			final Property<Number> property) {
		bindBidirectional(fieldPath, property, null);
	}

	/**
	 * Binds a {@linkplain Property} by traversing the bean's field tree
	 * 
	 * @see FieldBean#bidirectionalBindOperation(String, Property, boolean)
	 * @param fieldPath
	 *            the <b><code>.</code></b> separated field paths relative to
	 *            the {@linkplain #getBean()} that will be traversed
	 * @param property
	 *            the {@linkplain Property} to bind to the field class type of
	 *            the property
	 * @param propertyType
	 *            the class type of the {@linkplain Property} value
	 */
	@SuppressWarnings("unchecked")
	public <T> void bindBidirectional(final String fieldPath,
			final Property<T> property, final Class<T> propertyType) {
		Class<T> clazz = propertyType != null ? propertyType : 
			propertyValueClass(property);
		if (clazz == null && property.getValue() != null) {
			clazz = (Class<T>) property.getValue().getClass();
		}
		if (clazz == null || clazz == Object.class) {
			throw new UnsupportedOperationException(String.format(
					"Unable to determine property value class for %1$s " + 
					"and declared type %2$s", property, propertyType));
		}
		getRoot().bidirectionalBindOperation(fieldPath, property, clazz,
				false);
	}

	/**
	 * Unbinds a {@linkplain Property} by traversing the bean's field tree
	 * 
	 * @see FieldBean#bidirectionalBindOperation(String, Property, boolean)
	 * @param fieldPath
	 *            the <b><code>.</code></b> separated field paths relative to
	 *            the {@linkplain #getBean()} that will be traversed
	 * @param property the {@linkplain Property} to bind to
	 *            the field class type of the property
	 */
	public <T> void unBindBidirectional(final String fieldPath, final Property<T> property) {
		getRoot().bidirectionalBindOperation(fieldPath, property, null, true);
	}

	/**
	 * @return the bean of the {@linkplain BeanPathAdapter}
	 */
	public B getBean() {
		return getRoot().getBean();
	}
	
	/**
	 * Sets the root bean of the {@linkplain BeanPathAdapter}. Any existing
	 * properties will be updated with the values relative to the paths within
	 * the bean.
	 * 
	 * @param bean
	 *            the bean to set
	 */
	public void setBean(final B bean) {
		if (bean == null) {
			throw new NullPointerException();
		}
		if (getRoot() == null) {
			this.root = new FieldBean<>(null, bean, null);
		} else {
			getRoot().setBean(bean);
		}
	}

	/**
	 * @return the root/top level {@linkplain FieldBean}
	 */
	protected final FieldBean<Void, B> getRoot() {
		return this.root;
	}

	/**
	 * Provides the underlying value class for a given {@linkplain Property}
	 * 
	 * @param property
	 *            the {@linkplain Property} to check
	 * @return the value class of the {@linkplain Property}
	 */
	@SuppressWarnings("unchecked")
	protected static <T> Class<T> propertyValueClass(final Property<T> property) {
		Class<T> clazz = null;
		if (property != null) {
			if (StringProperty.class.isAssignableFrom(property.getClass())) {
				clazz = (Class<T>) String.class;
			} else if (IntegerProperty.class.isAssignableFrom(property
					.getClass())) {
				clazz = (Class<T>) Integer.class;
			} else if (BooleanProperty.class.isAssignableFrom(property
					.getClass())) {
				clazz = (Class<T>) Boolean.class;
			} else if (DoubleProperty.class.isAssignableFrom(property
					.getClass())) {
				clazz = (Class<T>) Double.class;
			} else if (FloatProperty.class.isAssignableFrom(property
					.getClass())) {
				clazz = (Class<T>) Float.class;
			} else if (LongProperty.class.isAssignableFrom(property
					.getClass())) {
				clazz = (Class<T>) Long.class;
			} else if (ListProperty.class.isAssignableFrom(property
					.getClass())) {
				clazz = (Class<T>) List.class;
			} else if (MapProperty.class.isAssignableFrom(property
					.getClass())) {
				clazz = (Class<T>) Map.class;
			} else {
				clazz = (Class<T>) Object.class;
			}
		}
		return clazz;
	}

	/**
	 * A POJO bean extension that allows binding based upon a <b><code>.</code>
	 * </b> separated field path that will be traversed on a bean until the
	 * final field name is found. Each bean may contain child
	 * {@linkplain FieldBean}s when
	 * {@linkplain #bidirectionalBindOperation(String, Property, boolean)} is
	 * called with a direct descendant field that is a non-primitive type. Any
	 * primitive types are added as a {@linkplain FieldProperty} reference to
	 * the {@linkplain FieldBean}.
	 * 
	 * @see #bidirectionalBindOperation(String, Property, boolean)
	 * @param <PT>
	 *            the parent bean type
	 * @param <BT>
	 *            the bean type
	 */
	protected static class FieldBean<PT, BT> implements Serializable {

		private static final long serialVersionUID = 7397535724568852021L;
		private final Map<String, FieldBean<BT, ?>> fieldBeans = new HashMap<>();
		private final Map<String, FieldProperty<BT, ?>> fieldProperties = new HashMap<>();
		private final Map<Class<?>, FieldStringConverter<?>> stringConverters = new HashMap<>();
		private FieldHandle<PT, BT> fieldHandle;
		private final FieldBean<?, PT> parent;
		private BT bean;

		/**
		 * Creates a {@linkplain FieldBean}
		 * 
		 * @param parent
		 *            the parent {@linkplain FieldBean} (should not be null)
		 * @param fieldHandle
		 *            the {@linkplain FieldHandle} (should not be null)
		 */
		protected FieldBean(final FieldBean<?, PT> parent, final FieldHandle<PT, BT> fieldHandle) {
			this.parent = parent;
			this.fieldHandle = fieldHandle;
			this.bean = this.fieldHandle.setDerivedValueFromAccessor();
			if (getParent() != null) {
				getParent().addFieldBean(this);
			}
		}

		/**
		 * Creates a {@linkplain FieldBean} with a generated
		 * {@linkplain FieldHandle} that targets the supplied bean and is
		 * projected on the parent {@linkplain FieldBean}. It assumes that the
		 * supplied {@linkplain FieldBean} has been set on the parent
		 * {@linkplain FieldBean}.
		 * 
		 * @see #createFieldHandle(Object, Object, String)
		 * @param parent
		 *            the parent {@linkplain FieldBean} (null when it's the root)
		 * @param bean
		 *            the bean that the {@linkplain FieldBean} is for
		 * @param fieldName
		 *            the field name of the parent {@linkplain FieldBean} for
		 *            which the new {@linkplain FieldBean} is for
		 */
		protected FieldBean(final FieldBean<?, PT> parent, final BT bean, final String fieldName) {
			if (bean == null) {
				throw new NullPointerException("Bean cannot be null");
			}
			this.parent = parent;
			this.bean = bean;
			this.fieldHandle = getParent() != null ? createFieldHandle(getParent().getBean(), 
					bean, fieldName) : null;
			if (getParent() != null) {
				getParent().addFieldBean(this);
			}
		}

		/**
		 * Generates a {@linkplain FieldHandle} that targets the supplied bean
		 * and is projected on the parent {@linkplain FieldBean} that has
		 * 
		 * @param parentBean
		 *            the parent bean
		 * @param bean
		 *            the child bean
		 * @param fieldName
		 *            the field name of the child within the parent
		 * @return the {@linkplain FieldHandle}
		 */
		@SuppressWarnings("unchecked")
		protected FieldHandle<PT, BT> createFieldHandle(final PT parentBean, final BT bean, 
				final String fieldName) {
			return new FieldHandle<PT, BT>(
					parentBean, fieldName, (Class<BT>) getBean().getClass());
		}

		/**
		 * Adds a child {@linkplain FieldBean} if it doesn't already exist.
		 * NOTE: It does <b>NOT</b> ensure the child bean has been set on the
		 * parent.
		 * 
		 * @param fieldBean
		 *            the {@linkplain FieldBean} to add
		 */
		protected void addFieldBean(final FieldBean<BT, ?> fieldBean) {
			if (!getFieldBeans().containsKey(fieldBean.getFieldName())) {
				getFieldBeans().put(fieldBean.getFieldName(), fieldBean);
			}
		}

		/**
		 * Adds or updates a child {@linkplain FieldProperty}. When the child
		 * already exists it will {@linkplain FieldProperty#setTarget(Object)}
		 * using the bean of the {@linkplain FieldProperty}.
		 * 
		 * @param fieldProperty
		 *            the {@linkplain FieldProperty} to add or update
		 */
		protected void addOrUpdateFieldProperty(final FieldProperty<BT, ?> fieldProperty) {
			final String pkey = fieldProperty.getName();
			if (getFieldProperties().containsKey(pkey)) {
				getFieldProperties().get(pkey).setTarget(fieldProperty.getBean());
			} else {
				getFieldProperties().put(pkey, fieldProperty);
			}
		}

		/**
		 * @see #setParentBean(Object)
		 * @return the bean that the {@linkplain FieldBean} represents
		 */
		public BT getBean() {
			return bean;
		}
		
		/**
		 * Sets the bean of the {@linkplain FieldBean} and it's underlying
		 * {@linkplain #getFieldNodes()} and {@linkplain #getFieldProperties()}
		 * 
		 * @see #setParentBean(Object)
		 * @param bean
		 */
		public void setBean(final BT bean) {
			if (bean == null) {
				throw new NullPointerException("Bean cannot be null");
			}
			this.bean = bean;
			for (final Map.Entry<String, FieldBean<BT, ?>> fn : getFieldBeans().entrySet()) {
				fn.getValue().setParentBean(getBean());
			}
			for (final Map.Entry<String, FieldProperty<BT, ?>> fp : getFieldProperties().entrySet()) {
				fp.getValue().setTarget(getBean());
			}
		}
		
		/**
		 * Binds a parent bean to the {@linkplain FieldBean} and it's underlying
		 * {@linkplain #getFieldNodes()} and {@linkplain #getFieldProperties()}
		 * 
		 * @see #setBean(Object)
		 * @param bean
		 *            the parent bean to bind to
		 */
		public void setParentBean(final PT bean) {
			if (bean == null) {
				throw new NullPointerException("Cannot bind to a null bean");
			} else if (fieldHandle == null) {
				throw new IllegalStateException("Cannot bind to a root " + 
						FieldBean.class.getSimpleName());
			}
			fieldHandle.setTarget(bean);
			setBean(fieldHandle.setDerivedValueFromAccessor());
		}

		/**
		 * Binds/Unbinds a {@linkplain FieldProperty} based upon the supplied
		 * <b><code>.</code></b> separated path to the field by traversing the
		 * matching children of the {@linkplain FieldBean} until the
		 * corresponding {@linkplain FieldProperty} is found (target bean uses
		 * the POJO from {@linkplain FieldBean#getBean()}). If the operation is
		 * bind and the {@linkplain FieldProperty} doesn't exist all relative
		 * {@linkplain FieldBean}s in the path will be instantiated using a
		 * no-argument constructor until the {@linkplain FieldProperty} is
		 * created and bound to the supplied {@linkplain Property}. The process
		 * is reciprocated until all path {@linkplain FieldBean} and
		 * {@linkplain FieldProperty} attributes of the field path are
		 * extinguished.
		 * 
		 * @see Bindings#bindBidirectional(Property, Property)
		 * @see Bindings#unbindBidirectional(Property, Property)
		 * @param fieldPath
		 *            the <code>.</code> separated field names
		 * @param property
		 *            the {@linkplain Property} to bind/unbind
		 * @param propertyValueClass
		 *            the class of the {@linkplain Property} value type (only
		 *            needed when binding)
		 * @param unbind
		 *            true to unbind, false to bind
		 */
		@SuppressWarnings("unchecked")
		public <T> void bidirectionalBindOperation(final String fieldPath,
				final Property<T> property, final Class<T> propertyValueClass,
				final boolean unbind) {
			final String[] fieldNames = fieldPath.split("\\.");
			final boolean isProperty = fieldNames.length == 1;
			final String pkey = isProperty ? fieldNames[0] : "";
			if (isProperty && getFieldProperties().containsKey(pkey)) {
				final FieldProperty<BT, ?> fp = getFieldProperties().get(pkey);
				if (unbind) {
					Bindings.unbindBidirectional((Property<T>) fp, property);
				} else {
					// because of the inverse relationship of the bidirectional
					// bind the initial value needs to be captured and reset as
					// a dirty value or the bind operation will overwrite the
					// initial value with the value of the passed property
					final Object val = fp.getDirty();
					Bindings.bindBidirectional((Property<String>) fp, property,
							(StringConverter<T>) getFieldStringConverter(
									propertyValueClass));
					if (val != null && !val.toString().isEmpty()) {
						fp.setDirty(val);
					}
				}
			} else if (!isProperty
					&& getFieldBeans().containsKey(fieldNames[0])) {
				// progress to the next child field/bean in the path chain
				final String nextFieldPath = fieldPath.substring(fieldPath
						.indexOf(fieldNames[1]));
				getFieldBeans().get(fieldNames[0]).bidirectionalBindOperation(
						nextFieldPath, property, propertyValueClass, unbind);
			} else if (!unbind) {
				// add a new bean/property chain
				if (isProperty) {
					final FieldProperty<BT, ?> childProp = new FieldProperty<>(
							getBean(), fieldNames[0], Object.class);
					addOrUpdateFieldProperty(childProp);
					bidirectionalBindOperation(fieldNames[0], property, 
							propertyValueClass, unbind);
				} else {
					// create a handle to set the bean as a child of the current
					// bean
					// if the child bean exists on the bean it will remain
					// unchanged
					final FieldHandle<BT, Object> pfh = new FieldHandle<>(
							getBean(), fieldNames[0], Object.class);
					final FieldBean<BT, ?> childBean = new FieldBean<>(this,
							pfh);
					// progress to the next child field/bean in the path chain
					final String nextFieldPath = fieldPath.substring(fieldPath
							.indexOf(fieldNames[1]));
					childBean.bidirectionalBindOperation(nextFieldPath,
							property, propertyValueClass, unbind);
				}
			}
		}

		/**
		 * @return the field name that the {@linkplain FieldBean} represents in
		 *         it's parent (null when the {@linkplain FieldBean} is root)
		 */
		public String getFieldName() {
			return fieldHandle != null ? fieldHandle.getFieldName() : null;
		}
		
		/**
		 * Determines if the {@linkplain FieldBean} contains a field with the
		 * specified name
		 * 
		 * @param fieldName
		 *            the field name to check for
		 * @return true when the field exists
		 */
		public boolean hasField(final String fieldName) {
			return getFieldBeans().containsKey(fieldName)
					|| getFieldProperties().containsKey(
							getFieldProperties().get(fieldName));
		}

		/**
		 * @return the parent {@linkplain FieldBean} (null when the
		 *         {@linkplain FieldBean} is root)
		 */
		public FieldBean<?, PT> getParent() {
			return parent;
		}

		/**
		 * @see #getFieldProperties()
		 * @return the {@linkplain Map} of fields that belong to the
		 *         {@linkplain FieldBean} that are not a
		 *         {@linkplain FieldProperty}, but rather exist as a
		 *         {@linkplain FieldBean} that may or may not contain their own
		 *         {@linkplain FieldProperty} instances
		 */
		protected Map<String, FieldBean<BT, ?>> getFieldBeans() {
			return fieldBeans;
		}

		/**
		 * @see #getFieldBeans()
		 * @return the {@linkplain Map} of fields that belong to the
		 *         {@linkplain FieldBean} that are not {@linkplain FieldBean}s,
		 *         but rather exist as a {@linkplain FieldProperty}
		 */
		protected Map<String, FieldProperty<BT, ?>> getFieldProperties() {
			return fieldProperties;
		}

		/**
		 * Gets/Creates (if not already created) a
		 * {@linkplain FieldStringConverter}.
		 * 
		 * @param targetClass
		 *            the target class of the {@linkplain FieldStringConverter}
		 * @return the {@linkplain FieldStringConverter}
		 */
		@SuppressWarnings("unchecked")
		public <FCT> FieldStringConverter<FCT> getFieldStringConverter(final Class<FCT> targetClass) {
			if (stringConverters.containsKey(targetClass)) {
				return (FieldStringConverter<FCT>) stringConverters.get(targetClass);
			} else {
				final FieldStringConverter<FCT> fsc = new FieldStringConverter<>(targetClass);
				stringConverters.put(targetClass, fsc);
				return fsc;
			}
		}
	}

	/**
	 * Coercible {@linkplain StringConverter} that handles conversions between
	 * strings and a target class when used in the binding process
	 * {@linkplain Bindings#bindBidirectional(Property, Property, StringConverter)}
	 * 
	 * @see StringConverter
	 * @param <T>
	 *            the target class type that is used in the coercion of the
	 *            string
	 */
	protected static class FieldStringConverter<T> extends StringConverter<T> {

		private final Class<T> targetClass;

		/**
		 * Constructor
		 * 
		 * @param targetClass
		 *            the class that the {@linkplain FieldStringConverter} is
		 *            targeting
		 */
		public FieldStringConverter(final Class<T> targetClass) {
			this.targetClass = targetClass;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public T fromString(final String string) {
			return coerce(string, targetClass);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString(final T object) {
			return object != null ? object.toString() : null;
		}

		/**
		 * @return the target class that is used in the coercion of the string
		 */
		public Class<T> getTargetClass() {
			return targetClass;
		}

		/**
		 * Attempts to coerce a value into the specified class
		 * 
		 * @param v
		 *            the value to coerce
		 * @param targetClass
		 *            the class to coerce to
		 * @return the coerced value (null when value failed to be coerced)
		 */
		@SuppressWarnings("unchecked")
		public static <VT> VT coerce(final Object v, final Class<VT> targetClass) {
			if (targetClass == Object.class) {
				return (VT) v;
			}
			VT val;
			final boolean isStringType = targetClass.equals(
					String.class);
			if (v == null || (!isStringType && v.toString().isEmpty())) {
				val = (VT) FieldHandle.defaultValue(targetClass);
			} else if (isStringType
					|| (v != null && v.getClass().isAssignableFrom(
							targetClass))) {
				val = (VT) targetClass.cast(v);
			} else {
				val = FieldHandle.valueOf(targetClass, v.toString());
			}
			return val;
		}
	}
	
	/**
	 * A {@linkplain Property} extension that uses a bean's getter/setter to
	 * define the {@linkplain Property}'s value.
	 * 
	 * @param <BT>
	 *            the bean type
	 * @param <T>
	 *            the field type
	 */
	protected static class FieldProperty<BT, T> extends ObjectPropertyBase<String> {
		
		private final FieldHandle<BT, T> fieldHandle;
		private boolean isDirty;

		/**
		 * Constructor
		 * 
		 * @param bean
		 *            the bean that the path belongs to
		 * @param fieldName
		 *            the name of the field within the bean
		 * @param fieldType
		 *            the {@linkplain Class} of the field
		 */
		protected FieldProperty(final BT bean, final String fieldName,
				final Class<T> fieldType) {
			super();
			this.fieldHandle = new FieldHandle<BT, T>(bean, fieldName,
					fieldType);
			setDerived();
		}
		
		protected void setDerived() {
			final T derived = fieldHandle.deriveValueFromAccessor();
			set(derived != null ? derived.toString() : null);
		}

		/**
		 * Flags the {@linkplain Property} value as dirty and calls
		 * {@linkplain #set(String)}
		 * 
		 * @param v
		 *            the value to set
		 */
		public void setDirty(final Object v) {
			isDirty = true;
			set(v != null ? v.toString() : null);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void set(final String v) {
			try {
				// final MethodHandle mh2 = MethodHandles.insertArguments(
				// fieldHandle.getSetter(), 0, v);
				final Object cv = fieldHandle.getAccessor().invoke();
				if (!isDirty && v == cv) {
					return;
				}
				final Object val = FieldStringConverter.coerce(v, cv != null ? cv.getClass()
						: fieldHandle.getFieldType());
				fieldHandle.getSetter().invoke(val);
				invalidated();
				fireValueChangedEvent();
				isDirty = false;
			} catch (final Throwable t) {
				throw new IllegalArgumentException("Unable to set value: " + v,
						t);
			}
		};

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String get() {
			try {
				final Object dv = getDirty();
				return dv != null ? dv.toString() : null;
			} catch (final Throwable t) {
				throw new RuntimeException("Unable to get value", t);
			}
		}
		
		/**
		 * @return the dirty value before conversion takes place
		 */
		public Object getDirty() {
			try {
				return fieldHandle.getAccessor().invoke();
			} catch (final Throwable t) {
				throw new RuntimeException("Unable to get dirty value", t);
			}
		}

		/**
		 * Binds a new target to the {@linkplain FieldHandle}
		 * 
		 * @param target
		 *            the target to bind to
		 */
		public void setTarget(final BT bean) {
			isDirty = true;
			fieldHandle.setTarget(bean);
			setDerived();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public BT getBean() {
			return fieldHandle.getTarget();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return fieldHandle.getFieldName();
		}
	}

	/**
	 * Field handle to {@linkplain FieldHandle#getAccessor()} and
	 * {@linkplain FieldHandle#getSetter()} for a given
	 * {@linkplain FieldHandle#getTarget()}.
	 * 
	 * @param <T>
	 *            the {@linkplain FieldHandle#getTarget()} type
	 * @param <F>
	 *            the {@linkplain FieldHandle#getDeclaredFieldType()} type
	 */
	protected static class FieldHandle<T, F> {

		private static final Map<Class<?>, MethodHandle> VALUE_OF_MAP = new HashMap<>(1);
		private static final Map<Class<?>, Object> DFLTS = new HashMap<>();
		static {
			DFLTS.put(Boolean.class, Boolean.FALSE);
			DFLTS.put(boolean.class, false);
			DFLTS.put(Byte.class, Byte.valueOf("0"));
			DFLTS.put(byte.class, Byte.valueOf("0").byteValue());
			DFLTS.put(Number.class, 0L);
			DFLTS.put(Short.class, Short.valueOf("0"));
			DFLTS.put(short.class, Short.valueOf("0").shortValue());
			DFLTS.put(Character.class, Character.valueOf(' '));
			DFLTS.put(char.class, ' ');
			DFLTS.put(Integer.class, Integer.valueOf(0));
			DFLTS.put(int.class, 0);
			DFLTS.put(Long.class, Long.valueOf(0));
		    DFLTS.put(long.class, 0L);
		    DFLTS.put(Float.class, Float.valueOf(0F));
		    DFLTS.put(float.class, 0F);
		    DFLTS.put(Double.class, Double.valueOf(0D));
		    DFLTS.put(double.class, 0D);
		    DFLTS.put(BigInteger.class, BigInteger.valueOf(0L));
		    DFLTS.put(BigDecimal.class, BigDecimal.valueOf(0D));
		}
		private final String fieldName;
		private MethodHandle accessor;
		private MethodHandle setter;
		private final Class<F> declaredFieldType;
		private T target;

		/**
		 * Constructor
		 * 
		 * @param target
		 *            the {@linkplain #getTarget()} for the
		 *            {@linkplain MethodHandle}s
		 * @param fieldName
		 *            the field name defined in the {@linkplain #getTarget()}
		 * @param declaredFieldType
		 *            the declared field type for the
		 *            {@linkplain #getFieldName()}
		 */
		protected FieldHandle(final T target, final String fieldName, 
				final Class<F> declaredFieldType) {
			super();
			this.fieldName = fieldName;
			this.declaredFieldType = declaredFieldType;
			this.target = target;
			updateMethodHandles();
		}

		/**
		 * Updates the {@linkplain #getAccessor()} and {@linkplain #getSetter()}
		 * using the current {@linkplain #getTarget()} and
		 * {@linkplain #getFieldName()}. {@linkplain MethodHandle}s are
		 * immutable so new ones are created.
		 */
		protected void updateMethodHandles() {
			this.accessor = buildAccessorWithLikelyPrefixes(getTarget(), getFieldName());
			this.setter = buildSetter(getAccessor(), getTarget(), getFieldName());
		}
		
		/**
		 * Attempts to build a {@linkplain MethodHandle} accessor for the field
		 * name using common prefixes used for methods to access a field
		 * 
		 * @param target
		 *            the target object that the accessor is for
		 * @param fieldName
		 *            the field name that the accessor is for
		 * @return the accessor {@linkplain MethodHandle}
		 * @throws NoSuchMethodException
		 *             thrown when an accessor cannot be found for the field
		 */
		protected static MethodHandle buildAccessorWithLikelyPrefixes(final Object target, 
				final String fieldName) {
			final MethodHandle mh = buildAccessor(target, fieldName, "get", "is", "has");
			if (mh == null) {
				//throw new NoSuchMethodException(fieldName + " on " + target);
				throw new IllegalArgumentException(fieldName + " on " + target);
			}
			return mh;
		}

		/**
		 * Attempts to build a {@linkplain MethodHandle} accessor for the field
		 * name using common prefixes used for methods to access a field
		 * 
		 * @param target
		 *            the target object that the accessor is for
		 * @param fieldName
		 *            the field name that the accessor is for
		 * @return the accessor {@linkplain MethodHandle}
		 * @param fieldNamePrefix
		 *            the prefix of the method for the field name
		 * @return the accessor {@linkplain MethodHandle}
		 */
		protected static MethodHandle buildAccessor(final Object target, 
				final String fieldName, final String... fieldNamePrefix) {
			final String accessorName = buildMethodName(fieldNamePrefix[0], fieldName);
			try {
				return MethodHandles.lookup().findVirtual(target.getClass(), accessorName, 
						MethodType.methodType(
								target.getClass().getMethod(
										accessorName).getReturnType())).bindTo(target);
			} catch (final NoSuchMethodException e) {
				return fieldNamePrefix.length <= 1 ? null : 
					buildAccessor(target, fieldName, 
						Arrays.copyOfRange(fieldNamePrefix, 1, 
								fieldNamePrefix.length));
			} catch (final Throwable t) {
				throw new IllegalArgumentException(
						"Unable to resolve accessor " + accessorName, t);
			}
		}
		
		/**
		 * Builds a setter {@linkplain MethodHandle}
		 * 
		 * @param accessor
		 *            the field's accesssor that will be used as the parameter type
		 *            for the setter
		 * @param target
		 *            the target object that the setter is for
		 * @param fieldName
		 *            the field name that the setter is for
		 * @return the setter {@linkplain MethodHandle}
		 */
		protected static MethodHandle buildSetter(final MethodHandle accessor, 
				final Object target, final String fieldName) {
			try {
				final MethodHandle mh1 = MethodHandles.lookup().findVirtual(target.getClass(), 
						buildMethodName("set", fieldName), 
						MethodType.methodType(void.class, 
								accessor.type().returnType())).bindTo(target);
				return mh1;
			} catch (final Throwable t) {
				throw new IllegalArgumentException("Unable to resolve setter "
						+ fieldName, t);
			}
		}
		
		/**
		 * Puts a <code>valueOf</code> {@linkplain MethodHandle} value using the
		 * target class as a key
		 * 
		 * @param target
		 *            the target object that the <code>valueOf</code> is for
		 */
		protected static void putValueOf(final Class<?> target) {
			if (VALUE_OF_MAP.containsKey(target)) {
				return;
			}
			try {
				final MethodHandle mh1 = MethodHandles.lookup().findStatic(
						target, "valueOf",
						MethodType.methodType(target, String.class));
				VALUE_OF_MAP.put(target, mh1);
			} catch (final Throwable t) {
				// class doesn't support it- do nothing
			}
		}

		/**
		 * Attempts to invoke a <code>valueOf</code> using the
		 * {@linkplain #getDeclaredFieldType()} class
		 * 
		 * @param value
		 *            the value to invoke the <code>valueOf</code> method on
		 * @return the result (null if the operation fails)
		 */
		public F valueOf(final String value) {
			return valueOf(getDeclaredFieldType(), value);
		}
		
		/**
		 * Attempts to invoke a <code>valueOf</code> using the
		 * specified class
		 * 
		 * @param valueOfClass
		 *            the class to attempt to invoke a <code>valueOf</code>
		 *            method on
		 * @param value
		 *            the value to invoke the <code>valueOf</code> method on
		 * @return the result (null if the operation fails)
		 */
		@SuppressWarnings("unchecked")
		public static <VT> VT valueOf(final Class<VT> valueOfClass, 
				final Object value) {
			if (value != null && String.class.isAssignableFrom(valueOfClass)) {
				return (VT) value.toString();
			}
			if (!VALUE_OF_MAP.containsKey(valueOfClass)) {
				putValueOf(valueOfClass);
			}
			if (VALUE_OF_MAP.containsKey(valueOfClass)) {
				try {
					return (VT) VALUE_OF_MAP.get(valueOfClass).invoke(value);
				} catch (final Throwable t) {
					throw new IllegalArgumentException(String.format(
							"Unable to invoke valueOf on %1$s using %2$s", 
							value, valueOfClass), t);
				}
			}
			return null;
		}

		/**
		 * Gets a default value for the {@linkplain #getDeclaredFieldType()}
		 * 
		 * @return the default value
		 */
		public F defaultValue() {
			return defaultValue(getDeclaredFieldType());
		}

		/**
		 * Gets a default value for the specified class
		 * 
		 * @param clazz
		 *            the class
		 * @return the default value
		 */
		@SuppressWarnings("unchecked")
		public static <VT> VT defaultValue(final Class<VT> clazz) {
			return (VT) (DFLTS.containsKey(clazz) ? DFLTS.get(clazz) : null);
		}

		/**
		 * Builds a method name using a prefix and a field name
		 * 
		 * @param prefix
		 *            the method's prefix
		 * @param fieldName
		 *            the method's field name
		 * @return the method name
		 */
		protected static String buildMethodName(final String prefix, 
				final String fieldName) {
			return (fieldName.startsWith(prefix) ? fieldName : prefix + 
				fieldName.substring(0, 1).toUpperCase() + 
					fieldName.substring(1));
		}

		/**
		 * Sets the derived value from {@linkplain #deriveValueFromAccessor()}
		 * using {@linkplain #getSetter()}
		 * 
		 * @see #deriveValueFromAccessor()
		 * @return the accessor's return target value
		 */
		public F setDerivedValueFromAccessor() {
			F derived = null;
			try {
				derived = deriveValueFromAccessor();
				getSetter().invoke(derived);
			} catch (final Throwable t) {
				throw new RuntimeException(String.format(
						"Unable to set %1$s on %2$s", derived, 
						getTarget()), t);
			}
			return derived;
		}
		
		/**
		 * Gets an accessor's return target value obtained by calling the
		 * accessor's {@linkplain MethodHandle#invoke(Object...)} method. When
		 * the value returned is <code>null</code> an attempt will be made to
		 * instantiate it using either by using a default value from
		 * {@linkplain #DFLTS} (for primatives) or
		 * {@linkplain Class#newInstance()} on the accessor's
		 * {@linkplain MethodType#returnType()} method.
		 * 
		 * @return the accessor's return target value
		 */
		@SuppressWarnings("unchecked")
		protected F deriveValueFromAccessor() {
			F targetValue = null;
			try {
				targetValue = (F) getAccessor().invoke();
			} catch (final Throwable t) {
				targetValue = null;
			}
			if (targetValue == null) {
				try {
					if (DFLTS.containsKey(getFieldType())) {
						targetValue = (F) DFLTS.get(getFieldType());
					} else {
						targetValue = (F) getAccessor().type().returnType().newInstance();
					}
				} catch (final Exception e) {
					throw new IllegalArgumentException(
							String.format("Unable to get accessor return instance for %1$s using %2$s.", 
									getAccessor(), getAccessor().type().returnType()));
				}
			}
			return targetValue;
		}
		
		/**
		 * Binds a new target to the {@linkplain FieldHandle}
		 * 
		 * @param target
		 *            the target to bind to
		 */
		public void setTarget(final T target) {
			if (getTarget().equals(target)) {
				return;
			}
			this.target = target;
			updateMethodHandles();
		}
		
		public T getTarget() {
			return target;
		}

		public String getFieldName() {
			return fieldName;
		}

		/**
		 * @return the getter
		 */
		protected MethodHandle getAccessor() {
			return accessor;
		}

		/**
		 * @return the setter
		 */
		protected MethodHandle getSetter() {
			return setter;
		}

		/**
		 * @return the declared field type of the property value
		 */
		public Class<F> getDeclaredFieldType() {
			return declaredFieldType;
		}
		
		/**
		 * @return the field type from {@linkplain #getAccessor()} of the
		 *         property value
		 */
		public Class<?> getFieldType() {
			return getAccessor().type().returnType();
		}
	}
}

