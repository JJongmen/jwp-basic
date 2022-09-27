#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
1. ContextLoaderListener 클래스의 contextInitialized 메서드를 호출한다.  
   (@WebListener 애노테이션을 클래스 위에 붙임으로써 톰캣 서버에게 Listener클래스임을 알려 줄 수 있으며 ServletContextListener 인터페이스를 구현하여 contextInitialized 메서드와 contextDestroyed 메서드를 오버라이딩하여 사용할 수 있는 것이다.)
```
@WebListener
public class ContextLoaderListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ContextLoaderListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());

        logger.info("Completed Load ServletContext!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
```
2. contextInitialized 메서드에 의해 DB가 jwp.sql 파일의 정보들로 초기화된다.
3. ResourceFilter 클래스의 init() 메서드가 호출된다.
4. DispatcherServlet 클래스는 일반적으로 최초로 클라이언트에서 요청이 올 시점에 초기화되지만 @WebServlet 애노테이션의 loadOnStartup 속성이 지정되어 있어 톰캣 서버가 시작될 때 인스턴스가 생성된다.
5. 따라서 DispatcherServlet 클래스의 init() 메서드가 호출된다.
6. init() 메서드에 의해 RequestMapping의 initMapping()이 호출되어 url 매핑이 완료된다.
#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 클라이언트가 요청을 하면 먼저 ResourceFilter에서 처리를 하게 된다.
* 만약 /css, /js, /fonts, /images, /favicon.ico 와 같은 url로 시작하게 된다면 DispatcherServlet으로 전달하지 않고 바로 resource를 반환시킨다.
* 그 외의 url이라면 DispatcherServlet으로 전달하여 해당 URL을 처리할 수 있는 컨트롤러를 찾는다.
* 해당 컨트롤러를 실행하여 ModelAndView 형태의 반환값을 얻는다.
* 반환받은 ModelAndView 에서 View를 얻은 후 해당 View의 render 메서드를 Model을 파라미터로 넣어서 호출하여 화면을 반환한다.
```
@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Controller controller = rm.findController(req.getRequestURI());
        ModelAndView mav;
        try {
            mav = controller.execute(req, resp);
            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }
```

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
```
public class ShowController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();
    private Question question;
    private List<Answer> answers;

    @Override
    public synchronized ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        synchronized (this) {
            question = questionDao.findById(questionId);
            answers = answerDao.findAllByQuestionId(questionId);
        }
        ModelAndView mav = jspView("/qna/show.jsp");
        mav.addObject("question", question);
        mav.addObject("answers", answers);
        return mav;
    }
}
```
* 클라이언트가 요청하지 않은 질문글을 반환할 수 있으며 다른 글의 댓글이 보여질 수도 있을 것이라 추측된다.
* 멀티 쓰레드 상황에서 ShowController 클래스의 question과 answers 필드는 공유 자원이다.
* 여러 쓰레드가 동시에 question과 answers에 접근하여 값을 바꿔버려도 서로 공유되기 때문에 원치 않는 값이 반환될 수 있는 것이다.
* 위 문제는 synchronized를 통한 동기화로 공유자원에 대한 임계영역을 설정하여 해결할 수 있다.
* 한 쓰레드가 자원을 사용할 수 있으면 다른 쓰레드들은 그 자원에 동시에 접근할 수 없는 것이다.
