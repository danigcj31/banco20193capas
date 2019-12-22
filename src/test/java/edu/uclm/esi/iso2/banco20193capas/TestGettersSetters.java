package edu.uclm.esi.iso2.banco20193capas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.MovimientoCuenta;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ClienteNoAutorizadoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ClienteNoEncontradoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaInvalidaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaSinTitularesException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaYaCreadaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ImporteInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.PinInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.SaldoInsuficienteException;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Tarjeta;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaDebito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGettersSetters extends TestCase {

	@Before
	public void setUp() {
		Manager.getMovimientoDAO().deleteAll();
		Manager.getMovimientoTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaDebitoDAO().deleteAll();
		Manager.getCuentaDAO().deleteAll();
		Manager.getClienteDAO().deleteAll();
	}
	
	/*
	 * Tarjeta de credito
	 */
	@Test
	public void testID() {
		Cliente victor = new Cliente("3", "Victor", "Avila");
		victor.insert();
		Cuenta cuentavictor = new Cuenta();

		try {
		//compruebo el id de la cuenta
		cuentavictor.getId();
		cuentavictor.setId(3L);
		//cuenta 82 62 
		//80 50
		//79 50
		
			
		} catch (Exception e) {
		}
	}
	
	
	@Test 
	public void testCuentaCreada () {
		Cliente victor = new Cliente("3", "Victor", "Avila");
		victor.insert();
		Cuenta cuentavictor = new Cuenta();
		try {
			cuentavictor.setCreada(true);
			if (cuentavictor.isCreada()) {
				System.out.print("Esta creada");
			}else {
				System.out.print("No esta creada");
			}
			
			} catch (Exception e) {
			}
	}
	
	@Test
	  public void testModificarCliente() {
	    Cliente victor = new Cliente("3", "Victor", "Avila");
	    victor.insert();
	    Cuenta cuentavictor=new Cuenta(3);
	    
	    try {
	      victor.setId(568L);
	      victor.setNif("548");
	      victor.setNombre("Nerea");
	      victor.setApellidos("Cabiedas");
	      
	      String nerea=victor.getNombre();
	      String nereaApe=victor.getApellidos();
	    
	      
	    } catch (Exception e) {
	      fail("Excepción inesperada: " + e.getMessage());
	    }  
	  }
	
	@Test 
	public void testMovimientoCuenta () {
		try {
			Cliente victor = new Cliente("3", "Victor", "Avila");
			victor.insert();
			Cuenta cuentavictor = new Cuenta(45);
			Cliente ismael = new Cliente("8", "Ismael", "Espartano");
			ismael.insert();
			Cuenta cuentaismael = new Cuenta(45);
			Cliente dani = new Cliente("1", "Daniel", "Gonzalez");
			dani.insert();
			Cuenta cuentadani = new Cuenta(23);
			MovimientoCuenta mc = new MovimientoCuenta(cuentavictor, 100, "concepto");
			cuentadani.ingresar(800);
			
			mc.setCuenta(cuentaismael);
			mc.setId(45L);
			mc.setImporte(300);
			mc.setConcepto("Hola");
			Cuenta cuentaprueba = mc.getCuenta();
			String st = mc.getConcepto();
			
			if(mc.getConcepto() != null) {
				System.out.println("OK");
				System.out.println("OK");
				System.out.println("OK");
				System.out.println("OK");
				System.out.println("OK");
				System.out.println("OK");
				System.out.println("OK");
			}
			
			} catch (Exception e) {
			}
	}
	@Test
	  public void testLanzadoraMain()  {

	    Lanzadora lanzadora=new Lanzadora();
	    
	    String args[]= {"1","2", "3"};
	    try {
	      lanzadora.main(args);
	    
	      
	    } catch (Exception e) {
	      fail("Excepción inesperada: " + e.getMessage());
	    }  
	    
	  }
}
