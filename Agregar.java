package servlets;

import java.io.IOException;
import java.security.UnresolvedPermission;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.interfaces.PersonaDAO;
import dao.interfaces.UsuarioDAO;
import dao.interfaces.VehiculoDAO;
import daofactory.DAOFactory;

import beans.PersonaBean;
import beans.UsuarioBean;
import beans.VehiculoBean;

/**
 * Servlet implementation class Agregar
 */
@WebServlet("/Agregar")
public class Agregar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Agregar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = request.getParameter("accion");
		
		if(accion.equals("usuario")){
			UsuarioBean beanUsuario = null;
			PersonaBean beanPersona = null;
			String nombres = request.getParameter("nombres");
			String apeP = request.getParameter("apeP");
			String apeM = request.getParameter("apeM");
			String direccion = request.getParameter("direccion");
			String distrito = request.getParameter("distrito");
			if(distrito == null){
				distrito = "Lima";
			}
			String fec_nac = request.getParameter("fec_nac");
			String dni = request.getParameter("dni");
			String tel_c = request.getParameter("tel_c");
			String tel_m = request.getParameter("tel_m");
			String correo = request.getParameter("correo");
			
			int maxPersona = 0;
			beanPersona = new PersonaBean( maxPersona, nombres, apeP, apeM, direccion, distrito, fec_nac, dni, tel_c, tel_m, correo,0);
			
			DAOFactory dao = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
			PersonaDAO persoDao = dao.obtenerPersonaDao();
			
			PersonaBean personaAgregada;
			personaAgregada = persoDao.agregarPersona(beanPersona);
			
			UsuarioDAO usuDao = dao.obtenerUsuarioDao();
			UsuarioBean usuario = new UsuarioBean();
			usuario.setId_Trabajador(personaAgregada.getId_Persona());
			usuario.setId_TipoUsuario(Integer.parseInt(request.getParameter("id_TipoUsuario")));
			usuario.setUsuario(personaAgregada.getNombre().charAt(0)+""+personaAgregada.getApeP()+""+personaAgregada.getApeM().charAt(0));
			usuario.setClave(personaAgregada.getDni());
			usuario.setEstado(0);
			usuDao.agregarUsuario(usuario);
			
			request.setAttribute("mensaje", "Se agregó correctamente el usuario, su clave por default es su DNI");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/resultado.jsp");
			rd.forward(request, response);
			
			
			
		}else if(accion.equals("alumno")){
			
			PersonaBean beanPersona = null;
			String nombres = request.getParameter("nombres");
			String apeP = request.getParameter("apeP");
			String apeM = request.getParameter("apeM");
			String direccion = request.getParameter("direccion");
			String distrito = request.getParameter("distrito");
			if(distrito == null){
				distrito = "Lima";
			}
			String fec_nac = request.getParameter("fec_nac");			
			String dni = request.getParameter("dni");
			String tel_c = request.getParameter("tel_c");
			String tel_m = request.getParameter("tel_m");
			String correo = request.getParameter("correo");
			
			int maxPersona = 0;
			beanPersona = new PersonaBean( maxPersona, nombres, apeP, apeM, direccion, distrito, fec_nac, dni, tel_c, tel_m, correo,0);
			
			DAOFactory dao = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
			PersonaDAO persoDao = dao.obtenerPersonaDao();
			
			PersonaBean personaAgregada;
			personaAgregada = persoDao.agregarPersona(beanPersona);
			
			
			String mensaje = "Se agregó correctamente a la persona : "+nombres +", "+apeP+" "+apeM;
			
			request.setAttribute("mensaje", mensaje);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/resultado.jsp");
			rd.forward(request, response);
		}else if(accion.equals("vehiculo")){
			DAOFactory dao = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
			VehiculoBean vehBean = null;
			String placa = request.getParameter("placa");
			String marca = request.getParameter("marca");
			String color = request.getParameter("color");
			int tipo_vehiculo = Integer.parseInt(request.getParameter("tipo_vehiculo"));
			vehBean = new VehiculoBean();
			vehBean.setColor(color);
			vehBean.setMarca(marca);
			vehBean.setPlaca(placa);
			vehBean.setTipo(tipo_vehiculo);
			
			VehiculoDAO vehiDao = dao.obtenerVehiculoDao();
			vehiDao.agregarVehiculo(vehBean);
			request.setAttribute("mensaje", "Se agregó el vehículo correctamente");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/resultado.jsp");
			rd.forward(request, response);
			
		}
	}

}
