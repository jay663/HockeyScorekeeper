package com.scoreit.hockeyscorekeeper;

public interface IAsyncTaskResults<T> {
    void onItemAdded(T result);
}
