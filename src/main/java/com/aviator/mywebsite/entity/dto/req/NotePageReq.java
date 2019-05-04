package com.aviator.mywebsite.entity.dto.req;

/**
 * @Description TODO
 * @ClassName NotePageReq
 * @Author aviator_ls
 * @Date 2019/5/4 22:20
 */
public class NotePageReq extends PageReq{

    private String betweenDate;

    public String getBetweenDate() {
        return betweenDate;
    }

    public void setBetweenDate(String betweenDate) {
        this.betweenDate = betweenDate;
    }
}
