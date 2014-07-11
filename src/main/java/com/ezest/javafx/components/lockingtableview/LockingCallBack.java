package com.ezest.javafx.components.lockingtableview;

public abstract interface LockingCallBack<T, R>
{
  public abstract R call(LockingTableColumn<T, ?> c, Object paramT);
}
