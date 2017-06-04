package com.i2nexted.mvpframe.mvp.basefrag;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/18.
 * Activity 与 Fragment 通信框架
 */

public class Functions {
    private HashMap<String, FunctionWithoutResultAndParam> mFunctionsWithoutResultAndParam;
    private HashMap<String, FunctionWithResult> mFunctionsWithResult;
    private HashMap<String, FunctionWithResultAndParam> mFunctionsWithResultAndParam;
    private HashMap<String, FunctionWithParam> mFunctionsWithParam;


    /**
     * 各种方法的父类
     * */
    public static abstract class Function{
        public String funtionName;

        public Function(String funtionName) {
            this.funtionName = funtionName;
        }
    }

    /**
     * 带参数和返回值的方法
     * */
    public static abstract class FunctionWithResultAndParam<RESULT, PARAM> extends Function{
        public FunctionWithResultAndParam(String funtionName) {
            super(funtionName);
        }
        public abstract RESULT function(PARAM param);
    }

    /**
     * 不带参数和返回值的方法
     * */
    public static abstract class FunctionWithResult<RESULT> extends Function{
        public FunctionWithResult(String funtionName) {
            super(funtionName);
        }
        public abstract RESULT function();
    }

    /**
     * 带参数和不带返回值的方法
     * */
    public static abstract class FunctionWithParam< PARAM> extends Function{
        public FunctionWithParam(String funtionName) {
            super(funtionName);
        }
        public abstract void function(PARAM param);
    }

    /**
     * 不带参数和不带返回值的方法
     * */
    public static abstract class FunctionWithoutResultAndParam extends Function{
        public FunctionWithoutResultAndParam(String funtionName) {
            super(funtionName);
        }

        public abstract void function();
    }

    /**
     * 添加不带参数和返回值的方法
     * */
    public Functions addFuncton(FunctionWithoutResultAndParam function) throws FunctionNullException {
        if (function == null){
            throw  new FunctionNullException("function cannot be null");
        }
        if (mFunctionsWithoutResultAndParam == null){
            mFunctionsWithoutResultAndParam = new HashMap<>(1);
        }
        mFunctionsWithoutResultAndParam.put(function.funtionName, function);
        return this;
    }

    /**
     * 添加带参数和不带返回值的方法
     * */
    public Functions addFuncton(FunctionWithParam function) throws FunctionNullException {
        if (function == null){
            throw  new FunctionNullException("function cannot be null");
        }
        if (mFunctionsWithParam == null){
            mFunctionsWithParam = new HashMap<>(1);
        }
        mFunctionsWithParam.put(function.funtionName, function);
        return this;
    }
    /**
     * 添加不带参数和带返回值的方法
     * */
    public Functions addFuncton(FunctionWithResult function) throws FunctionNullException {
        if (function == null){
            throw  new FunctionNullException("function cannot be null");
        }
        if (mFunctionsWithResult == null){
            mFunctionsWithResult = new HashMap<>(1);
        }
        mFunctionsWithResult.put(function.funtionName, function);
        return this;
    }
    /**
     * 添加带参数和返回值的方法
     * */
    public Functions addFuncton(FunctionWithResultAndParam function) throws FunctionNullException {
        if (function == null){
            throw  new FunctionNullException("function cannot be null");
        }
        if (mFunctionsWithResultAndParam == null){
            mFunctionsWithResultAndParam = new HashMap<>(1);
        }
        mFunctionsWithResultAndParam.put(function.funtionName, function);
        return this;
    }

    /**
     * 调用不带参数和不带返回值的方法
     * */
    public void invokeFunc(String functionName) throws FunctionNullException {
        FunctionWithoutResultAndParam functionWithoutResultAndParam = null;
        if (mFunctionsWithoutResultAndParam != null){
            functionWithoutResultAndParam = mFunctionsWithoutResultAndParam.get(functionName);
        }

        if (functionWithoutResultAndParam != null){
            functionWithoutResultAndParam.function();
        }else {
            throw new FunctionNullException(FunctionWithoutResultAndParam.class.getSimpleName() + "  not found");
        }
    }

    /**
     * 调用带参数和不带返回值的方法
     * */
    public <PARAM> void invokeFuncWithParam(String functionName, PARAM param) throws FunctionNullException {
        FunctionWithParam functionWithParam = null;
        if (mFunctionsWithParam != null){
            functionWithParam = mFunctionsWithParam.get(functionName);
        }
        if (functionWithParam != null){
            functionWithParam.function(param);
        }else {
            throw new FunctionNullException(FunctionWithParam.class.getSimpleName() + "  not found");
        }
    }


    /**
     * 调用不带参数和带返回值的方法
     * */
    public <RESULT> RESULT invokeFuncWithResult(String functionName, Class<RESULT> clazz) throws FunctionNullException {
        FunctionWithResult functionWithResult = null;
        if (mFunctionsWithResult != null){
            functionWithResult = mFunctionsWithResult.get(functionName);
        }
        if (mFunctionsWithResult != null){
            if (clazz != null){
                return clazz.cast(functionWithResult.function());
            }else {
                return (RESULT) functionWithResult.function();
            }
        }else {
            throw  new FunctionNullException(FunctionWithResult.class.getSimpleName() + "  not found");
        }
    }

    /**
     * 调用带参数和带返回值的方法
     * */
    public <RESULT, PARAM> RESULT invokeFuncWithResultAndParam(String functionName, Class<RESULT> clazz, PARAM param) throws FunctionNullException {
        FunctionWithResultAndParam functionWithResultAndParam = null;
        if (mFunctionsWithResultAndParam != null){
            functionWithResultAndParam = mFunctionsWithResultAndParam.get(functionName);
        }
        if (functionWithResultAndParam != null){
            if (clazz == null){
                return (RESULT) functionWithResultAndParam.function(param);
            }else {
                return clazz.cast(functionWithResultAndParam.function(param));
            }
        }else {
            throw new FunctionNullException(FunctionWithResultAndParam.class.getSimpleName() + " not found");
        }
    }


}
