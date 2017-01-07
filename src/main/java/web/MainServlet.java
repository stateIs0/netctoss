package web;
/**
 * @author Administratordhskjadhskhdskldjlskhfjdhkfh fd fdkj���ӿռ�Ļظ���ݷѴ�ַ������������������η�
 * @since �ſռ��  ���Ļ��ɼ��ʺ�򿪷����Ŀռ䷢�ŵؿ鷢������Ʒ�
 */
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDaao;
import dao.CostDao;
import entity.Admin;
import entity.Cost;
import util.ImageUtil;

public class MainServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String path=req.getServletPath();
		if("/findCost.do".equals(path)){
			findCost(req,res);
		}else if("/toAddCost.do".equals(path)){
			toAddCost(req,res);
		}else if("/addCost.do".equals(path)){
			addCost(req,res);
		}else if("/toUpdateCost.do".equals(path)){
			toUpdateCost(req,res);
		}else if("/updateCost.do".equals(path)){
			updateCost(req,res);
		}else if("/deleteCost.do".equals(path)){
			deleteCost(req,res);
		}else if("/toLogin.do".equals(path)){
			toLogin(req,res);
		}else if("/login.do".equals(path)){
			login(req,res);
		}else if("/toIndex.do".equals(path)){
			index(req,res);
		}else if("/toPage.do".equals(path)){
			toPage(req,res);
		}else if("/logout.do".equals(path)){
			logout(req,res);
		}else if("/creatImg.do".equals(path)){
			creatImg(req,res);
		}else{
			throw new RuntimeException("·��������");
		}
	 }
	//������֤��.
	protected void creatImg(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//1.�����������֤�뼰ͼƬ
		Object[] objs=ImageUtil.createImage();
		//2.����֤�����session
		String imgcode=(String)objs[0];
		
		HttpSession session=req.getSession();
		session.setAttribute("imgcode", imgcode);
		//3.��ͼƬ����������.
		BufferedImage img=(BufferedImage)objs[1];
		res.setContentType("image/png");
		//�������Զ����������,Ŀ��ָ�������.
		OutputStream os=res.getOutputStream();
		ImageIO.write(img, "png", os);
		os.close();
	}
	//�˳���½.
	protected void logout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session=req.getSession();
		session.invalidate();
		res.sendRedirect("toLogin.do");
/*		req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
*/	}
	protected void toPage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	/**	String page=req.getParameter("page");
		System.out.println(page);
		if(page!=null){
		List<Cost>list=new CostDao().toPage(page);
		req.setAttribute("costs",list);
		req.getRequestDispatcher("WEB-INF/cost/find.jsp").forward(req, res);*/
		//��ȡ�������
				String page = req.getParameter("page");
				if(page == null || page.equals("")) {
					page = "1";
				}
				//��ȡ����
				String size = getServletContext().getInitParameter("size");
				//��ѯ�ʷ�
				CostDao dao = new CostDao();
				List<Cost> list = dao.findByPage(new Integer(page),new Integer(size));
				//��ѯ���������������ҳ��
				int rows = dao.findRows();
				int total = rows/new Integer(size);
				if(rows%new Integer(size) != 0) {
					total++;
				}
				//ת������ѯҳ��
				req.setAttribute("costs", list);
				req.setAttribute("total", total);
				req.setAttribute("page", page);
				//��ǰ��/netctoss/findCost.do
				//Ŀ�꣺/netctoss/WEB-INF/cost/find.jsp
				req.getRequestDispatcher("WEB-INF/cost/find.jsp").forward(req, res);
		}
	
	protected void index(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/index.jsp").forward(req, res);
	}
	protected void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String adminCode=req.getParameter("adminCode");
		String password=req.getParameter("password");
		System.out.println(adminCode);
		Admin a=new AdminDaao().findBuCode(adminCode);
		String code=req.getParameter("code");
		//��session�л�ȡ���ɵ���֤��.
		HttpSession session=req.getSession();
		String imgcode=(String)session.getAttribute("imgcode");
		//�����֤��
		if(code==null || !code.equalsIgnoreCase(imgcode)){
			req.setAttribute("error", "��֤�����");
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);;
			return;
		}
		System.out.println(a);
		if(a.getAdminCode()==null){
			req.setAttribute("error", "�˺Ŵ���"); 
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);;
		}else if(!a.getPassword().equals(password)){
			req.setAttribute("error", "�������");
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);;
		}else{
			//���˺Ŵ���cookie,���͸������.������Զ����������ҳ��ʹ��.
			Cookie cookie=new Cookie("admin",adminCode);
			res.addCookie(cookie);//�����ǽ�cookie�󶨵���response������.
			//Ҳ����ʹ��session�������˺�.((����һ��session))
			session.setAttribute("admin", adminCode);
			
			//����,�ض�����ҳ.
			req.getRequestDispatcher("WEB-INF/main/index.jsp").forward(req, res);
		}
	}
	protected void toLogin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
	}
	protected void deleteCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String id=req.getParameter("id");
		new CostDao().delete(id);
		res.sendRedirect("findCost.do");
		
	}
	protected void updateCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Cost c=parameterCost(req);
		new CostDao().update(c);
		res.sendRedirect("findCost.do");
	}
	protected void toUpdateCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String id=req.getParameter("id");
		System.out.println(id);
		Cost c=new CostDao().findByid(id);
		req.setAttribute("cost", c);
		req.getRequestDispatcher("WEB-INF/cost/update.jsp").forward(req, res);
	}

	
	protected void addCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Cost c = parameterCost(req);
		new CostDao().save(c);
		res.sendRedirect("findCost.do");
	}
	public Cost parameterCost(HttpServletRequest req) throws UnsupportedEncodingException {
		req.setCharacterEncoding("utf-8");
		String id=req.getParameter("id");
		String name=req.getParameter("name");
		String costType=req.getParameter("costType");
		String baseDuration=req.getParameter("baseDuration");
		String baseCost=req.getParameter("baseCost");
		String unitCost=req.getParameter("unitCost");
		String descr=req.getParameter("descr");
		Cost c=new Cost();
		if(id!=null && id.equals("")){
			c.setCostID(new Integer(id));
		}
		c.setName(name);
		c.setCostType(costType);
		c.setDescr(descr);
		c.setStatus("0");
		if(baseDuration!=null && !baseDuration.equals("")){
			c.setBaseDuration(new Integer(baseDuration));
		}
		if(baseCost!=null && !baseCost.equals("")){
			c.setBaseCost(new Double(baseCost));
		}
		if(unitCost!=null && !unitCost.equals("")){
			c.setUnitCost(new Double(unitCost));
		}
		return c;
	}
	protected void toAddCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/cost/add.jsp").forward(req, res);
	}
	/**
	 * fjdlfjdjfkldjfkldjfkldsjflkdjflkdjflkjldfjlkd
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void findCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	/*		List<Cost>list=new CostDao().findAll();
			req.setAttribute("costs",list);
			req.getRequestDispatcher("WEB-INF/cost/find.jsp").forward(req, res);*/
		//��ȡ�������
		String page = req.getParameter("page");
		if(page == null || page.equals("")) {
			page = "1";
		}
		//��ȡ����
		String size = getServletContext().getInitParameter("size");
		//��ѯ�ʷ�
		CostDao dao = new CostDao();
		List<Cost> list = dao.findByPage(new Integer(page),new Integer(size));
		//��ѯ���������������ҳ��
		int rows = dao.findRows();
		int total = rows/new Integer(size);
		if(rows%new Integer(size) != 0) {
			total++;
		}
		//ת������ѯҳ��
		req.setAttribute("costs", list);
		req.setAttribute("total", total);
		req.setAttribute("page", page);
		//��ǰ��/netctoss/findCost.do
		//Ŀ�꣺/netctoss/WEB-INF/cost/find.jsp
		req.getRequestDispatcher("WEB-INF/cost/find.jsp").forward(req, res);
		
	}

}
