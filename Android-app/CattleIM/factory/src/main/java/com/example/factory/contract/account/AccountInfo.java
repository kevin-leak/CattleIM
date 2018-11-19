package com.example.factory.contract.account;

import com.example.factory.contract.BaseContract;

public interface AccountInfo {

     interface presenter extends BaseContract.Presenter{

     }

     interface view extends BaseContract.View<presenter>{

     }
}
