package com.wk.projects.common.log;

import android.os.Build;

import com.wk.projects.common.constant.WkSuppressConstants;
import com.wk.projects.common.log.disk.IDiskPrintStrategy;
import com.wk.projects.common.log.local.AndroidLogPrintStrategy;
import com.wk.projects.common.log.local.ILocalPrintStrategy;
import com.wk.projects.common.log.local.SystemPrintStrategy;
import com.wk.projects.common.log.service.IServicePrintStrategy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");
    private static final int MAX_LOG_LENGTH = 4000;
    private static final int MAX_TAG_LENGTH = 23;
    private static final int CALL_STACK_INDEX = 3;

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
        tag=checkTag(tag);
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
        tag=checkTag(tag);
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

    private static String[] checkTag(String... tag){
        if(tag.length==0){
            return new String[]{getDefaultTag()};
        }
        return tag;
    }


    private static String getDefaultTag(){
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace.length <= CALL_STACK_INDEX) {
            throw new IllegalStateException(
                    "Synthetic stacktrace didn't have enough elements: are you using proguard?");
        }
        return createStackElementTag(stackTrace[CALL_STACK_INDEX]);
    }

    @Nullable
    protected static String createStackElementTag(@NotNull StackTraceElement element) {
        String tag = element.getClassName();
        Matcher m = ANONYMOUS_CLASS.matcher(tag);
        if (m.find()) {
            tag = m.replaceAll("");
        }
        tag = tag.substring(tag.lastIndexOf('.') + 1);
        // Tag length limit was removed in API 24.
        if (tag.length() <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return tag;
        }
        return tag.substring(0, MAX_TAG_LENGTH);
    }
}
