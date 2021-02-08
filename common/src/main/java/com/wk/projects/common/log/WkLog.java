package com.wk.projects.common.log;

import com.wk.projects.common.constant.WkSuppressConstants;
import com.wk.projects.common.log.disk.IDiskPrintStrategy;
import com.wk.projects.common.log.local.AndroidLogPrintStrategy;
import com.wk.projects.common.log.local.ILocalPrintStrategy;
import com.wk.projects.common.log.local.SystemPrintStrategy;
import com.wk.projects.common.log.service.IServicePrintStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *      @author : wk <br/>
 *      e-mail : 122642603@qq.com <br/>
 *      time   : 2020/6/15 <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      address:
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 *      desc   : log
 *
 */
@SuppressWarnings(WkSuppressConstants.UNUSED)
public class WkLog {


    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;

    private static final List<IWkLogPrintStrategy> priceStrategies = new ArrayList<>();

    public static ILocalPrintStrategy localPrintStrategy=new AndroidLogPrintStrategy();
    public static IDiskPrintStrategy diskPrintStrategy;
    public static IServicePrintStrategy servicePrintStrategy;

    /**
     * Info 级别日志
     *
     * @param logContent 日志内容
     * @param tag        used to identify the source of a log message
     *                   if is null ,it is the class`s name where the log call occurs
     */
    public static int i(Object logContent, String... tag) {
        int result=0;
        if(diskPrintStrategy!=null){
            result+=diskPrintStrategy.i(logContent,tag);
        }
        if(servicePrintStrategy!=null){
            result+=servicePrintStrategy.i(logContent,tag);
        }
        if(localPrintStrategy!=null){
            result+=localPrintStrategy.i(logContent,tag);
        }
        return result;
    }

    /**
     * @param logContent 日志内容
     * @param tag        used to identify the source of a log message
     *                   if is null ,it is the class`s name where the log call occurs
     * @return 无意义 为了模仿Android log返回值
     */
    public static int d(Object logContent, String... tag) {
        int result=0;
        if(diskPrintStrategy!=null){
            result+=diskPrintStrategy.i(logContent,tag);
        }
        if(servicePrintStrategy!=null){
            result+=servicePrintStrategy.i(logContent,tag);
        }
        if(localPrintStrategy!=null){
            result+=localPrintStrategy.i(logContent,tag);
        }
        return result;
    }

    /**
     * @param logContent 日志内容
     * @param tag        used to identify the source of a log message
     *                   if is null ,it is the class`s name where the log call occurs
     */
    public static int e(String tag, Object logContent) {
        int result=0;
        if(diskPrintStrategy!=null){
            result+=diskPrintStrategy.i(logContent,tag);
        }
        if(servicePrintStrategy!=null){
            result+=servicePrintStrategy.i(logContent,tag);
        }
        if(localPrintStrategy!=null){
            result+=localPrintStrategy.i(logContent,tag);
        }
        return result;
    }

    /**
     * @param logContent 日志内容
     * @param tag        used to identify the source of a log message
     *                   if is null ,it is the class`s name where the log call occurs
     */
    public static int w(String tag, Object logContent) {
        int result=0;
        if(diskPrintStrategy!=null){
            result+=diskPrintStrategy.i(logContent,tag);
        }
        if(servicePrintStrategy!=null){
            result+=servicePrintStrategy.i(logContent,tag);
        }
        if(localPrintStrategy!=null){
            result+=localPrintStrategy.i(logContent,tag);
        }
        return result;
    }

    /**
     * @param logContent 日志内容
     * @param tag        used to identify the source of a log message
     *                   if is null ,it is the class`s name where the log call occurs
     */
    public static int v(String tag, Object logContent) {
        int result=0;
        if(diskPrintStrategy!=null){
            result+=diskPrintStrategy.i(logContent,tag);
        }
        if(servicePrintStrategy!=null){
            result+=servicePrintStrategy.i(logContent,tag);
        }
        if(localPrintStrategy!=null){
            result+=localPrintStrategy.i(logContent,tag);
        }
        return result;
    }

}
