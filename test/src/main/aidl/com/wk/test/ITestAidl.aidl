// ITestAidl.aidl
package com.wk.test;

// Declare any non-default types here with import statements
import com.wk.test.TestBean;

interface ITestAidl {

    List<TestBean> getTestBeanList();

    void addTestBean(in TestBean testBean);

}
