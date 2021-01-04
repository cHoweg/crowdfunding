package com.egc.crowd.bean;

import java.util.List;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2020/12/25 上午11:51
 */
public class ParamData {
    private List<Integer> array;

    public ParamData() {
    }

    public ParamData(List<Integer> array) {
        this.array = array;
    }

    public List<Integer> getArray() {
        return array;
    }

    public void setArray(List<Integer> array) {
        this.array = array;
    }
}
