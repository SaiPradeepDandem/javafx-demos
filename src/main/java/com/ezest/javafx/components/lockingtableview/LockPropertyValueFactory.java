package com.ezest.javafx.components.lockingtableview;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;

import com.sun.javafx.property.PropertyReference;

public class LockPropertyValueFactory<S, T>
  implements Callback<LockingTableColumn<S, T>.CellDataFeatures<S, T>, ObservableValue<T>>
{
  private final String property;
  private Class columnClass;
  private String previousProperty;
  private PropertyReference<T> propertyRef;

  public LockPropertyValueFactory(String paramString)
  {
    this.property = paramString;
  }

  public ObservableValue<T> call(LockingTableColumn<S, T>.CellDataFeatures<S, T> paramCellDataFeatures)
  {
    return getCellDataReflectively(paramCellDataFeatures.getValue());
  }

  public final String getProperty()
  {
    return this.property;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private ObservableValue<T> getCellDataReflectively(S paramS)
  {
    if ((getProperty() == null) || (getProperty().isEmpty()) || (paramS == null))
      return null;
    try
    {
      if ((this.columnClass == null) || (this.previousProperty == null) || (!(this.columnClass.equals(paramS.getClass()))) || (!(this.previousProperty.equals(getProperty()))))
      {
        this.columnClass = paramS.getClass();
        this.previousProperty = getProperty();
        this.propertyRef = new PropertyReference(paramS.getClass(), getProperty());
      }
      return this.propertyRef.getProperty(paramS);
    }
    catch (IllegalStateException localIllegalStateException1)
    {
      try
      {
        Object localObject = this.propertyRef.get(paramS);
        return new ReadOnlyObjectWrapper(localObject);
      }
      catch (IllegalStateException localPlatformLogger)
      {
        System.out.println("Can not retrieve property '" + getProperty() + "' in PropertyBindingFactory: " + this + " with provided class type: " + paramS.getClass()+ localIllegalStateException1);
      }
    }
    return null;
  }

}
