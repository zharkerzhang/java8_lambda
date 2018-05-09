package com.zharker.chapter8.template_method.lambdas;


import com.zharker.chapter8.template_method.ApplicationDenied;

// BEGIN Criteria
public interface Criteria {

    public void check() throws ApplicationDenied;

}
// END Criteria
