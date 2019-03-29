package com.shivaraj.mysqlparser.web.rest.vm;

/**
 * Created by shivaraj on 3/4/2019.
 */
public class ConnectionVM {

    String connectionStatus;
    String errorDescription;


    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
