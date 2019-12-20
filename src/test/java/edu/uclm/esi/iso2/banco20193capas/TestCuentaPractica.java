package edu.uclm.esi.iso2.banco20193capas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
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
public class TestCuentaPractica extends TestCase {

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
	 * TEST DANI
	 */

	@Test
	public void testTransferenciaSinDinero() {
		Cliente dani = new Cliente("1", "Daniel", "Gonzalez");
		dani.insert();
		Cuenta cuentaDani = new Cuenta(1);
		Cliente nerea = new Cliente("2", "Nerea", "Cabiedas");
		nerea.insert();
		Cuenta cuentaNerea = new Cuenta(2);

		try {
			cuentaDani.addTitular(dani);
			cuentaDani.insert();
			cuentaDani.getSaldo();
			cuentaNerea.addTitular(nerea);
			cuentaNerea.insert();
			cuentaNerea.getSaldo();

			cuentaDani.ingresar(800);
			cuentaDani.transferir(2L, 1000, "Apuntes ISO2");

			if (cuentaDani.getSaldo() < 0) {
				System.out.print("Saldo insuficiente");
			}

			fail("Esperaba SaldoInsuficienteException");
		} catch (SaldoInsuficienteException e) {

		} catch (Exception e) {
			fail("Se ha lanzado una excepcion inesperada" + e);

		}

	}
	
	
	/*
	@Test
	public void testLoginIncorrecto () {
		Cliente dani = new Cliente("1", "Daniel", "Gonzalez");
		dani.insert();
		Cuenta cuentaDani = new Cuenta(1);
		int intentos = 0;
			
		try {
			cuentaDani.addTitular(dani);
			cuentaDani.insert();
		      
		    TarjetaCredito tc = cuentaDani.emitirTarjetaCredito("1", 1000);
		    int pinIntroducido = 1234;
		    
		    while (intentos < 3) {
			    if (pinIntroducido != tc.getPin()) {
			    	System.out.print("Pin incorrecto");
			    }
			    intentos ++;
		    }
	    	System.out.print("Intentos agotados");
		     
			fail("Esperaba PinInvalido Exception");
		} catch (Exception e) {
			fail("Se ha lanzado una excepcion inesperada" + e);

		}
	}
	*/
	
	/*
	 * TEST NEREA
	 */
	
	@Test
	public void testRetiradaSaldo() {
		Cliente nerea = new Cliente("2", "nerea", "cabiedas");
		nerea.insert();
		Cuenta cuentaNerea = new Cuenta(1);
		try {
			cuentaNerea.addTitular(nerea);
			cuentaNerea.insert();
			cuentaNerea.ingresar(1000);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
		try {
			cuentaNerea.retirar(500);
		} catch (ImporteInvalidoException e) {
			fail("Se ha producido ImporteInvalidoException");
		} catch (SaldoInsuficienteException e) {
			fail("Se ha lanzado una excepcion inesperada" + e);
		}

	}
	
	@Test
	public void testCompraPorInternetConTD() {
		  Cliente nerea = new Cliente("2", "nerea", "cabiedas");
		  nerea.insert();
		  
		  Cuenta cuentaNerea = new Cuenta(1);
		  try {
		   cuentaNerea.addTitular(nerea);
		   cuentaNerea.insert();
		   
		   cuentaNerea.ingresar(1000);
		   cuentaNerea.retirar(200);;
		   assertTrue(cuentaNerea.getSaldo()==800);
		   
		   TarjetaDebito td = cuentaNerea.emitirTarjetaDebito("2");
		   int pin =td.getPin();
		   int token = td.comprarPorInternet(td.getPin(), 300);
		   td.confirmarCompraPorInternet(token);
		   assertTrue(cuentaNerea.getSaldo()==500);
		  } catch (Exception e) {
		   fail("Excepción inesperada: " + e.getMessage());
		  }
		 }
	
	
	/*
	 * TEST VICTOR
	 */
	@Test
	  public void testCambiarPin() {
	    Cliente victor = new Cliente("3", "Victor", "Avila");
	    victor.insert();
	    Cuenta cuentavictor=new Cuenta(3);
	    
	    try {
	      cuentavictor.addTitular(victor);
	      cuentavictor.insert();
	      
	      TarjetaCredito tc = cuentavictor.emitirTarjetaCredito("3", 1000);
	      
	      int pinviejo=tc.getPin();
	      tc.cambiarPin(pinviejo, 123456);
	      
	    } catch (Exception e) {
	      fail("Excepción inesperada: " + e.getMessage());
	    }  
	  }
	
	@Test
	  public void testSacarDineroDebito() {
	    Cliente victor = new Cliente("3", "Victor", "Avila");
	    victor.insert();
	    Cuenta cuentavictor=new Cuenta(3);
	    
	    
	    try {
	      cuentavictor.addTitular(victor);
	      cuentavictor.insert();
	      cuentavictor.ingresar(1000);
	      TarjetaDebito td= cuentavictor.emitirTarjetaDebito("3");
	      td.setActiva(true);
	      int pin= td.getPin();
	      td.sacarDinero(pin, 50.0);
	      
	    } catch (Exception e) {
	      fail("Excepción inesperada: " + e.getMessage());
	    }  
	    
	  }

	/*
	 * TEST NOELIA
	 */
	@Test
	  public void testNoCreacionDeUnaCuenta() {
	    Cliente noelia = new Cliente("4", "noelia", "granados");
	    noelia.insert();
	    
	    Cuenta cuentaNoelia = new Cuenta(1);
	    
	    try {
	      cuentaNoelia.insert();
	      fail("Esperaba CuentaSinTitularesException");
	    } catch (CuentaSinTitularesException e) {
	    }
	  }


}
