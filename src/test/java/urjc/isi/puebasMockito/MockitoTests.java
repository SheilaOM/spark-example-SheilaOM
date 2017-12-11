package urjc.isi.puebasMockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


import static org.mockito.Mockito.*;


import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class MockitoTests {

	@Test
	public void MockListSize() {
		List list = mock(List.class);		//Crea un mock para la clase list. Tiene la interfaz de List pero no ejecuta la clase List (es un suplantador)
		when(list.size()).thenReturn(10);	//Cuando se llame a list.size() (que no es de tipo List, es un mock), devuelve un 10. when y thenReturn es de Mockito
		assertEquals(10, list.size());		//No llama al size de List, sino al mock, por eso devuelve 10, se lo he dicho yo arriba
	}

	@Test
	public void MockListSizeWithMultipleReturnValues() {
		List list = mock(List.class);
		when(list.size()).thenReturn(10).thenReturn(20);	//La primera llamada devuelve un 10, las siguientes devuelve 20
		assertEquals(10, list.size()); // Primera llamada
		assertEquals(20, list.size()); // Segunda llamada
		assertEquals(20, list.size()); // Tercera llamada y 
		      						  // subsiguientes devuelven el mismo valor
		
		verify(list, times(3)).size();	//sobre el mock list, se ha llamado 3 veces al método size.
	}

	@Test
	public void MockListGet() {
		List<String> list = mock(List.class);
		when(list.get(0)).thenReturn("Hello World");
		assertEquals("Hello World", list.get(0));
		assertNull(list.get(1));		//Como he configurado sólo para la llamada de get(0), con get(1) me devuelve null
	}

	@Test
	public void MockListGetWithAny() {
		List<String> list = mock(List.class);
		when(list.get(anyInt())).thenReturn("Hello World");			//Para cualquier entero (anyInt), devuelve Hello World
		when(list.get(3)).thenReturn("Bye World");					//Para 3 devuelve Bye World
		
		assertEquals("Hello World", list.get(0));
		assertEquals("Hello World", list.get(1));
		assertEquals("Hello World", list.get(2));
		assertEquals("Bye World", list.get(3));
		
		
	}
	
	@Test
	public void MockIterator_will_return_hello_world(){
		Iterator i = mock(Iterator.class);				//Creo un mock para la clase Iterator
		when(i.next()).thenReturn("Hello").thenReturn("World");

		String result=i.next()+" "+i.next();

		assertEquals("Hello World", result);
	}


	@Test
	public void MockWithArguments(){
		Comparable c=mock(Comparable.class);
		when(c.compareTo("Test")).thenReturn(1);
		assertEquals(1,c.compareTo("Test"));
		assertEquals(0,c.compareTo("Foo"));
	}
	

	@Test
	public void MockWithUnspecifiedArguments(){
		Comparable c=mock(Comparable.class);
		when(c.compareTo(anyInt())).thenReturn(-1);
		when(c.compareTo(3)).thenReturn(0);
		assertEquals(-1, c.compareTo(5));
		assertEquals(0, c.compareTo(3));
		verify(c).compareTo(5);		//Comprueba que se ha llamado a compareTo con 5
		verify(c).compareTo(3);		//Comprueba que se ha llamado a compareTo con 3
		verify(c, never()).compareTo(25);	//Comprueba que nunca se mmala a compareTo con 25
		verify(c, times(1)).compareTo(5);	//Comprueba que se ha llamado exactamente una vez a compareTo con 5
		verify(c, atLeastOnce()).compareTo(5);	//Comprueba que se ha llamado al menos una vez a compareTo con 5
		verify(c, atLeast(1)).compareTo(5);		//Comprueba que se ha llamado al menos 1 vez (n veces modificable) con 5
	}


	@Test(expected=IOException.class)
	public void MockOutputStreamWriterRethrowsAnExceptionFromOutputStream() 
			throws IOException{
		OutputStream mock=mock(OutputStream.class);
		OutputStreamWriter osw=new OutputStreamWriter(mock);
		doThrow(new IOException()).when(mock).close();		//Se eleva excepción cuando llame a close
		osw.close();
	}
	

	@Test
	public void MockOutputStreamWriterClosesOutputStreamOnClose()
			throws IOException{
		OutputStream mock=mock(OutputStream.class);
		OutputStreamWriter osw=new OutputStreamWriter(mock);
		osw.close();
		verify(mock).close();
	}

}