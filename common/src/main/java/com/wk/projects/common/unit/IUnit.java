package com.wk.projects.common.unit;

import kotlin.Pair;

/**
 * @author : wk <br/>
 * e-mail : 1226426603@qq.com<br/>
 * time   : 2020/11/16<br/>
 * desc   :   <br/>
 * GitHub : https://github.com/wk1995 <br/>
 * CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 */
public interface IUnit<T extends IUnit,R extends Number> {

     R conversion(R t, T start, T end);

     R getConversionUnit(T start, T end);

}
