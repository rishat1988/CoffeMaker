package com.example.coffemachine.repositories;

public interface DbConnector<T>
{
    T getSession() throws Exception;
}
