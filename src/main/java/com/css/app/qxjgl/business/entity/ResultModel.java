package com.css.app.qxjgl.business.entity;

import com.github.pagehelper.PageInfo;


import java.util.List;

/**
 * ＺＦ公共返回实体
 */
public class ResultModel implements java.io.Serializable {

    private Object data;
    private String msg;
    private Integer code;

    private Integer page;
    private Integer pages;
    private Integer pageSize;
    private Long count;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public static ResultModel getResult(Integer code, String msg, Object data) {
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(code);
        resultModel.setData(data);
        resultModel.setMsg(msg);
        return resultModel;
    }

    public static ResultModel getSuccess(String msg, Object data) {
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(0);
        resultModel.setData(data);
        resultModel.setMsg(msg);
        return resultModel;
    }

    public static ResultModel getError(String msg) {
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(999);
        resultModel.setData(null);
        resultModel.setMsg(msg);
        return resultModel;
    }

    public static ResultModel getPageData(String msg, Object data) {
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(0);
        resultModel.setData(data);
        resultModel.setMsg(msg);

        if (null != data && data instanceof List) {
            return orgResultPages(resultModel, (List) data);
        }
        return resultModel;
    }

    /**
     * 设置ResultModel返回页码属性
     *
     * @param result ResultModel
     * @param list   数据集
     * @return ResultModel
     */
    public static ResultModel orgResultPages(ResultModel result, List list) {
        PageInfo pageInfo = new PageInfo(list);
        result.setPages(pageInfo.getPages());
        result.setCount(pageInfo.getTotal());
        return result;
    }


}
