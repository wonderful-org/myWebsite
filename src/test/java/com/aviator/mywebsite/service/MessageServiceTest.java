package com.aviator.mywebsite.service;

import com.aviator.mywebsite.TestUtils;
import com.aviator.mywebsite.Testable;
import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.entity.dto.req.MessageReq;
import com.aviator.mywebsite.entity.dto.req.PageReq;

/**
 * @Description TODO
 * @ClassName MessageServiceTest
 * @Author aviator_ls
 * @Date 2019/4/29 19:57
 */
public class MessageServiceTest implements Testable {

    MessageService messageService = new MessageService();

    MessageReq messageReq = new MessageReq();

    PageReq pageReq = new PageReq();

    public static void main(String[] args) {
        TestUtils.test(MessageServiceTest.class);
    }

    @Override
    public void before() {
        messageReq.setContent("testMsg");
    }

    @Override
    public void doTest() {
        insertErrorAuthorId(messageReq);
        long id = insert(messageReq);
        update(id, messageReq);
        findPage();
        delete(id);
    }

    @Override
    public void after() {

    }

    private void insertErrorAuthorId(MessageReq messageReq){
        Result result = messageService.insertMessage(99999999L, messageReq);
        System.out.println("insertErrorAuthorId:" + result);
    }

    private long insert(MessageReq messageReq){
        Result result = messageService.insertMessage(1, messageReq);
        System.out.println("insert:" + result);
        return (long) result.getData();
    }

    private void update(long id, MessageReq messageReq){
        messageReq.setId(id);
        Result result = messageService.updateMessageById(messageReq);
        System.out.println("update:" + result);
    }

    private void findPage(){
        Result result = messageService.findMessagePage(pageReq);
        System.out.println("findPage:" + result);
    }

    private void delete(long id){
        Result result = messageService.deleteMessageById(id);
        System.out.println("delete:" + result);
    }
}
