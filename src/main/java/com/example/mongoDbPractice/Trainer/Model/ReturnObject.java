package com.example.mongoDbPractice.Trainer.Model;

public class ReturnObject {
    Boolean statementExecuted;
    String reason;
    Object modelObject;

    public ReturnObject(Boolean statementExecuted, String reason, Object modelObject) {
        this.statementExecuted = statementExecuted;
        this.reason = reason;
        this.modelObject = modelObject;
    }

    public ReturnObject() {
    }

    public Boolean getStatementExecuted() {
        return statementExecuted;
    }

    public void setStatementExecuted(Boolean statementExecuted) {
        this.statementExecuted = statementExecuted;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getModelObject() {
        return modelObject;
    }

    public void setModelObject(Object modelObject) {
        this.modelObject = modelObject;
    }
}
