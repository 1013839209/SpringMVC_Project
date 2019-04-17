package org.dong.springmvc.converter;

import org.dong.springmvc.entity.Student;
import org.springframework.core.convert.converter.Converter;

/**
 *  类型转换器
 */
public class MyConverter implements Converter<String, Student> {
    @Override
    public Student convert(String source) {  //source接受前端传来的String:2-zs-23
        String[] studentStr = source.split("-");
        Student student = new Student();
        student.setId(Integer.parseInt(studentStr[0]));
        student.setName(studentStr[1]);
        student.setAge(Integer.parseInt(studentStr[2]));
        return student;
    }



}
