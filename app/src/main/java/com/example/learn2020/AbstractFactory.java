package com.example.learn2020;

import android.text.style.IconMarginSpan;

public class AbstractFactory {

    public static void main(String[] args) {
        IDatabase database = new SqlDatabase();
        database.getCommend().commend();
        database.getConnect().connect();
    }
}

interface IDatabase {
    IConnect getConnect();
    ICommend getCommend();
}

interface IConnect {
    void connect();
}

interface ICommend {
    void commend();
}

class SqlConnect implements IConnect {

    @Override
    public void connect() {
        System.out.println("sqlsqlsql");
    }
}

class SqlCommend implements ICommend {

    @Override
    public void commend() {
        System.out.println("sqlCommend");
    }
}

class SqlDatabase implements IDatabase {

    @Override
    public IConnect getConnect() {
         return new SqlConnect();
    }

    @Override
    public ICommend getCommend() {
        return new SqlCommend();
    }
}

class OracleCommend implements ICommend {

    @Override
    public void commend() {
        System.out.println("");
    }
}
