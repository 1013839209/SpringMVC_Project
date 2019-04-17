package org.dong.springmvc.handler;

import org.dong.springmvc.entity.Address;
import org.dong.springmvc.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//把一个普通的类变成一个有特定功能的类有四种方法
//接口、继承、配置、注解      MVC可以通过 配置 和 注解
@Controller
@RequestMapping( value = "/handler") //映射
//@SessionAttributes( value = {"student4","student3","student2"})//使用对象名方式 如果要在request中存放student4对象, 则同时将该对象放入session作用域中
//@SessionAttributes(types = {Student.class, Address.class})//使用对象类型方式  如果要在request中存放Student类型的对象, 则同时将该 类型对象放入session作用域中
public class SpringMVCHandler {

    //通过method指定请求方式
    //name2=zs必须等于zs   age!=23不能等与23可以不写   !height请求参数不能有height
    @RequestMapping( value = "/welcome",method = RequestMethod.POST,params = {"name2=zs","age!=23","!height"}) //映射
    public String welcome(){
        return "success";//  views/success.jsp,默认使用了请求转发的 跳转方式
    }

    //@RequestMapping( value = "/welcome2",headers = {"Accept=text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8","Accept-Language: zh-CN,zh;q=0.9"}) //映射
    /*public String welcome2(){
        return "success";//  views/success.jsp,默认使用了请求转发的 跳转方式
    }*/

    /**
     *  ant风格的请求路径
     *  ？ 单字符
     *  *  任意个字符（0个或多个）
     *  ** 任意目录
     */
    @RequestMapping( value = "/welcome3/*/test")
    public String welcome3(){
        return "success";
    }

    @RequestMapping( value = "/welcome4/**/test")
    public String welcome4(){
        return "success";
    }
    @RequestMapping( value = "/welcome5/a?c/test")
    public String welcome5(){
        return "success";
    }
    //ant风格@PathVariable动态获取数据
    @RequestMapping( value = "welcome6/{name}")
    public String welcome6(@PathVariable("name") String name){
        System.out.println("---------"+name);
        return "success";
    }

    /**
     * REST风格: 软件编程风格
     *      SpringMVC:
     *          GET: 查
     *          POST: 增
     *          DELETE: 删
     *          PUT: 改
     *  普通浏览器  只支持get post 方式; 其他请求方式如:delete|put请求是通过 过滤器新加入的支持
     *  springmvc实现: put|delete请求方式的步骤
     *      1.增加过滤器
     *      2.表单
     *          1.必须时post方式
     *          2.通过隐藏域的value值 设置实际的请求方式 DELETE | PUT
     *          3.隐藏域的 name值等于 "_method"
     *      3.控制器
     *          通过 method = RequestMethod.XXX 匹配具体的请求方式
     *          当映射名相同时 (value = "/testRest") 可以通过method处理不同的请求
     */
    @RequestMapping(value = "/testRest/{id}",method = RequestMethod.POST)
    public String testPost(@PathVariable("id")Integer id){
        System.out.println("post: 增"+id);
        //Service层实现 真正的增加
        return "success";
    }
    @RequestMapping(value = "/testRest/{id}",method = RequestMethod.DELETE)
    public String testDel(@PathVariable("id")Integer id){
        System.out.println("delete: 删"+id);
        //Service层实现 真正的删除
        return "success";
    }
    @RequestMapping(value = "/testRest/{id}",method = RequestMethod.PUT)
    public String testPut(@PathVariable("id")Integer id){
        System.out.println("put: 改"+id);
        //Service层实现 真正的修改
        return "success";
    }
    @RequestMapping(value = "/testRest/{id}",method = RequestMethod.GET)
    public String testGet(@PathVariable("id")Integer id){
        System.out.println("get: 查"+id);
        //Service层实现 真正的查询
        return "success";
    }

    //required=false: 该属性不是必须的
    //defaultValue = "23": 默认值23
    @RequestMapping("/testParam")
    public String testParam( @RequestParam("name") String name,
                               @RequestParam(value = "age",required = false,defaultValue = "23")Integer age){
        System.out.println(name+"\n"+age);
        return "success";
    }

    /**
     *  获取请求头信息 @RequestHeader
     *      通过 @RequestHeader("Accept-Language")String al 获取请求头中的 Accept-Language 值,并将值保存在al变量中
     *
     */
    @RequestMapping("/requestHeader")
    public String testRequestHeader(@RequestHeader("Accept-Language")String al){
        System.out.println(al);
        return "success";
    }
    /**
     *  通过mvc获取cookie值(JSESSIONID)  @CookieValue:
     *  (前置知识:服务端在接受客户端第一次请求时,会给该客户端分配一个session(该session包含一
     *  个sessionId)),并且服务端会在第一次相应客户端时,请求该sessionId赋值给JSESSIONID
     *  并传递给客户端的cookie中
     */
    @RequestMapping("/testCookieValue")
    public String testCookieValue(@CookieValue("JSESSIONID")String jsessionId){
        System.out.println(jsessionId);
        return "success";
    }

    /**
     * 使用对象 (实体类) 接受请求参数
     * @param student
     * @return
     */
    @RequestMapping("/testObjectProperties")
    public String testObjectProperties(Student student){//student属性必须和form表单中的属性Nam值一致(支持级联属性)
        System.out.println(student.getId()+student.getName()+student.getAddress().getHomeAddress()+student.getAddress().getSchoolAddress());
        return "success";
    }

    //在SpringMVC中使用原生态的Servlet API: 直接将Servlet-api中的类,接口等 写在springMvc所映射的方法参数中即可
    @RequestMapping("/testServletAPI")
    public String testServletAPI(HttpServletRequest request, HttpServletResponse response){
//        String name = request.getParameter("name");
//        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println(request+"------"+response);
        return "success";
    }

    /**
     *  处理模型数据:
     *      如果跳转时需要带数据:V,M,则可以使用以下方式:
     *          ModelAndView  ModelMap  Map  Model  --数据放在request作用域中
     *          @SessionAttributes  @ModelAttribute
     */
    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(){//ModelAndView:既有数据又有视图
        ModelAndView mv = new ModelAndView("success");
        Student student = new Student();
        student.setId(2);
        student.setName("zs");
        mv.addObject("student",student);//相当于request.setAttribute("student",student);
        return mv;
    }
    @RequestMapping("/testModelMap")
    public String testModelMap(ModelMap mm){//ModelAndView:既有数据又有视图
        Student student = new Student();
        student.setId(2);
        student.setName("zs");
        mm.put("student2",student);//相当于request.setAttribute("student",student);
        //指定请求方式: 请求转发 forward       重定向 redirect
        //注意,此种方式,不会被视图解析器加上前缀(/views),后缀(.jsp)
        return "redirect:/views/success.jsp";
    }
    @RequestMapping("/testMap")
    public String testMap(Map<String,Object> map){//ModelAndView:既有数据又有视图
        Student student = new Student();
        student.setId(2);
        student.setName("zs");
        map.put("student3",student);//相当于request.setAttribute("student",student);
        return "success";
    }
    @RequestMapping("/testModel")
    public String testModel(Model model){//ModelAndView:既有数据又有视图
        Student student = new Student();
        student.setId(2);
        student.setName("zs");
        model.addAttribute("student4",student);//request域
        return "success";
    }

    /**
     * @ModelAttribute :
     *  1.经常在 更新时使用.
     *  2.在不改变原有代码的基础之上,插入一个新方法.
     *      通过@ModelAttribute修饰的方法,会在每次请求前先执行,并且该方法的参数map.put()可以将
     *      对象放入 即将查询的参数中;
     *      必须满足的约定:
     *          map.put(k,v) 其中的k 必须是即将查询的 方法参数的首字母小写
     *          testModelAttribute(Student xxx) ,即student 如下:
     * @param map
     */
    @ModelAttribute//在任何一次请求前,都会执行@ModelAttribute修饰的方法 使用时需要注意
    //@ModelAttribute 在请求该类的各个方法前均会被执行的设计是基于一个思想: 一个控制器只做一个功能
    public void queryStudentById(Map<String,Object> map){
        Student student = new Student();
        student.setName("zs");
        student.setId(31);
        student.setAge(23);
//        map.put("student",student); //约定: map的key 就是方法参数 Student类型的首字母小写 student
        map.put("stu",student);//方法名不一样可以使用 @ModelAttribute("stu") Student student 进行指定
    }
    @RequestMapping("/testModelAttribute")
    public String testModelAttribute(@ModelAttribute("stu") Student student){
        System.out.println(student.getName());
//        student.setName(student.getName());
        System.out.println(student.getId()+student.getName()+student.getAge());
        return  "success";
    }

    /**
     * 视图 , 视图解析器
     *    使徒的顶级接口: View
     *    视图解析器的顶级接口:ViewResolver
     *    常见的视图和解析器:
     *          InternalResourceView , InternalResourceViewResolver
     *    public class JstlView extends InternalResourceView:
     *    SpringMVC解析jsp时 会默认使用InternalResourceView,如果发现Jsp中包含了jstl语言,则自动转为JstlView
     *
     *    JstlView 可以解析jstl\实现国际化操作
     *    国际化: 针对不同地区,不同国家,进行不同的显示
     *      中国:(大陆,香港) 欢迎你
     *      美国: welcome
     *      具体实现国际化步骤:
     *          1.创建资源文件的命名规范:   基名_语言_地区.properties  基名_语言.properties
     *          2.配置SpringMVC.xml,加载资源文件
     *
     *
     */
    @RequestMapping("/testI18n")
    public String testI18n(){
        return  "success";
    }

    /**
     *
     *  动态资源: 可以与用户交互,因为时间/地点的不同 而结果不同的内容;(百度:天气)
     *  处理静态资源: html,css,图片,视频
     *      访问静态资源:404 . 原因:之前将所有的请求 通过通配符"/" 拦截,进而交给SpringMVC的入口
     *      DispatcherServlet去处理:找该请求映射对应的 @RequestMapping
     *   解决:
     *      如果是需要mvc处理的,则交给@RequsetMapping("img.jnp")处理,如果不需要mvc处理,则使用tomcat默认
     *      的Servlet去处理:如果有 对应的请求拦截,则交给相应的Servlet去处理; 如果没有对应的Servlet,则直接访问
     *      tomcat默认的Servlet在哪里?
     *          在tomcat配置文件\conf\web.xml中
     *
     *  解决静态资源方案: 如果有springmvc对应的@RequsetMapping则交给springmvc处理;如果没有对应的@RequsetMapping,
     *  则交给服务器tomcat默认的servlet去处理:实现方法只需要增加 两个注解即可
     *          <mvc:annotation-driven>    <mvc:default-servlet-handler>
     *      总结: 要让springmvc访问静态资源,只需要加入以上2个注解
     */

    /**
     *    类型转换
     *    1.SpringMVC自带一些 常见的类型转换器
     *      public String testDel(@PathVariable("id")Integer id),既可以接受int类型数据id ,也可以接受String类型的id
     *    2.可以自定义类型转换器
     *      1.编写 自定义类型转换器的(需要实现Converter接口)
     *      2.配置:将MyConverter加入到springmvc中
     *
     *     <!-- 1.将自定义转换器 纳入SpringIOC容器 -->
     *     <bean id="myConverter" class="org.dong.springmvc.converter.MyConverter"></bean>
     *     <!-- 2.将myConverter在纳入 SpringMVC提供的转换器Bean中 -->
     *     <bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
     *         <property name="converters" >
     *             <set>
     *                 <ref bean="myConverter"/>
     *             </set>
     *         </property>
     *     </bean>
     *     <!-- 3.将conversionService 注册到 annotation-driven中 -->
     *     <mvc:annotation-driven conversion-service="conversionService"></mvc:annotation-driven>
     */

   /**
    * @RequestParam("studentInfo")是触发转换器的桥梁: @RequestParam("studentInfo")接收的数据是前端传递过来的
    * :2-zs-23,但是 需要将该数据 复制给修饰的目标对象Student;因此SpringMVC可以发现 接收的数据 和目标数据不一
    * 致,并且 这两种数据分别是String,Student,正好符合 public Student convert(String source)转换器
    */
   @RequestMapping("/testConverter")
    public String testConverter(@RequestParam("studentInfo")Student student){
        System.out.println(student.getId()+","+student.getName()+","+student.getAge());
        return "success";
    }

    /**
     * 数据格式化
     *      1.配置 注解所依赖的bean
     *      2.通过注解使用
     *
     *  如果要将控制台的错误消息 传到jsp中显示，则可以将错误消息对象放入request域中，然后在jsp中从request域中获取
     */

    /**
     *  数据校验 JSR303
     *      @Null 被注释的元素必须为null
     *      @NotNull 被注释的元素必须不为null
     *      @AssertTrue 被注释的元素必须为true
     *      @AssertFalse 被注释的元素必须为false
     *      @Min(value) 被注释的元素必须是一个数字，其值必须大于或等于value
     *      @Max(value) 被注释的元素必须是一个数字，其值必须小于或等于value
     *      @DecimalMin(value) 被注释的元素必须是一个数字，其值必须大于或等于value
     *      @DecimalMax(value) 被注释的元素必须是一个数字，其值必须小于或等于value
     *      @Size(max,min) 被注释的元素的取值范围必须是介于min和max之间
     *      @Digits(integer,fraction) 被注释的元素必须是一个数字，其值必须在可接受的范围内
     *      @Past 被注释的元素必须是一个过去的日期
     *      @Futura(value) 被注释的元素必须是一个将来的日期
     *      @Pattern(value) 被注释的元素必须符合指定的正则表达式
     *
     *  Hibernate Validator 是JSR303的扩展 Hibernate Validator提供了 JSR303中所有的内置的注解，以及自身扩展的4个注解
     *      @Email 被注释的元素之必须是合法的电子邮箱地址
     *      @Length 被注释的字符串的长度必须在指定的范围内
     *      @NotEmpty 被注释的字符串必须是非空
     *      @Range 被注释的元素必须在合适的范围内
     *
     *  Hibernate Validator使用步骤：
     *      1.jar包 5个
     *      2.配置 <mvc:annotation-driven></mvc:annotation-driven>
     *      3.使用
     *          在校验的Controller中，给校验的对象前增加 @Valid
     */
    @RequestMapping("/testDateTimeFormat")
    public String testDateTimeFormat(@Valid Student student, BindingResult result, Map<String,Object> map){
        System.out.println(student.getId()+","+student.getName()+","+student.getBirthday());
        if(result.getErrorCount() > 0){ //如果错误数量大于 0
            for (FieldError error : result.getFieldErrors()){//循环遍历每个错误
                System.out.println(error.getDefaultMessage());//打印
                map.put("errors",result.getFieldErrors());//将错误信息传入request域中的errors中
            }
        }
        return "success";
    }

    /**
     *  Ajax请求SpringMVC，并且以JSON格式返回数据
     */
    @ResponseBody//告诉SpringMVC 此时的返回 不是一个View页面，而是一个Ajax调用的返回值
    @RequestMapping("/testJson")
    public List<Student> testJson(){
        Student student1 = new Student(1,"zs",23);
        Student student2 = new Student(2,"ls",24);
        Student student3 = new Student(3,"ww",25);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        return studentList;
    }

    /**
     *  SpringMVC实现文件上传
     *      1.jar包 commons-fileupload.jar / commons-in.jar
     *      2.配置CommonsMultipartResolver,将其加入SpringIOC容器
     *      3.处理方法
     *      FileOutputStream: 输出流可以指定将文件传递到哪里去
     *
     *      框架: 就是将原来自己写的1000行代码,变成框架帮你写800行,剩下200行自己写
     */
    //文件上传处理方法
    @RequestMapping("/testUpload")
    public String testUpload(@RequestParam("desc")String desc, @RequestParam("file")MultipartFile file) throws IOException {
        System.out.println("描述信息:"+desc);

        InputStream input = file.getInputStream();//输入流

        String fileName = file.getOriginalFilename();//获取上传时的文件名称

        OutputStream out = new FileOutputStream("d:\\"+fileName);

        byte[] bs = new byte[1024];//设置一个缓冲区,一次读取1024字节
        int len = -1;
        while ( (len = input.read(bs)) != -1){// input.read( bs )返回值为读取的字节数,如果有值就不会等于-1
            out.write(bs,0,len);
        }
        out.close();
        input.close();
        System.out.println("上传成功");
        return "success";
    }

    /**
     *  拦截器
     *      拦截器的原理和过滤器相同
     *      SpringMVC: 要想实现拦截器,必须实现接口 HandlerInterceptor
     *      实现步骤:
     *          1.编写拦截器 implements HandlerInterceptor
     *          2.配置: 将自己写的拦截器配置到SpringMVC中(Spring)
     */
    @RequestMapping("/testInterceptor")
    public String testInterceptor(){
        System.out.println("普通的处理请求方法.....!");
        return "success";
    }

}
