package com.aviator.mywebsite.db;

/**
 * @Description TODO
 * @ClassName TTest
 * @Author aviator_ls
 * @Date 2019/4/24 18:33
 */
public class TTest {
    private int id;

    private String string_field;

    private int int_field;

    private long bigint_field;

    private java.util.Date datetime_field;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getString_field() {
        return string_field;
    }

    public void setString_field(String string_field) {
        this.string_field = string_field;
    }

    public int getInt_field() {
        return int_field;
    }

    public void setInt_field(int int_field) {
        this.int_field = int_field;
    }

    public long getBigint_field() {
        return bigint_field;
    }

    public void setBigint_field(long bigint_field) {
        this.bigint_field = bigint_field;
    }

    public java.util.Date getDatetime_field() {
        return datetime_field;
    }

    public void setDatetime_field(java.util.Date datetime_field) {
        this.datetime_field = datetime_field;
    }

    @Override
    public String toString() {
        return "TTest{" +
                "id=" + id +
                ", string_field='" + string_field + '\'' +
                ", int_field=" + int_field +
                ", bigint_field=" + bigint_field +
                ", datetime_field=" + datetime_field +
                '}';
    }
}
