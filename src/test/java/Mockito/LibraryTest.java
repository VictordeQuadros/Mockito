
package Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class LibraryTest {

	@Mock
	private Library library;

	@Mock
	List<String> mockedGlobalList = mock(List.class);

	@Test
	public void testSimple() {

		List<String> mockedList = mock(List.class);

		mockedList.add("um");
		mockedList.clear();

		verify(mockedList).add("um");
		verify(mockedList).clear();
	}

	@Test
	public void testWhen() {

		LinkedList<String> mockedList = mock(LinkedList.class);

		when(mockedList.get(0)).thenReturn("primerio");
		when(mockedList.get(1)).thenThrow(new RuntimeException());
		doThrow(new RuntimeException()).when(mockedList).clear();

		System.out.println(mockedList.get(0));

		System.out.println(mockedList.get(999));

		try {
			System.out.println(mockedList.get(1));
		} catch (Exception e) {
			System.out.println("Retornou a excecao: " + e.getClass());
		}

		try {
			mockedList.clear();
		} catch (Exception e) {
			System.out.println("Retornou a excecao quando tentou limpar: " + e.getClass());
		}

		verify(mockedList).get(0);
		verify(mockedList).get(999);
		verify(mockedList).clear();
//		 verify(mockedList).get(1);
	}

	@Test
	public void testAnyEq() {

		List<Integer> mockedIntegerList = mock(List.class);
		List<String> mockedStringList = mock(List.class);

		when(mockedIntegerList.get(0)).thenReturn(0);
		when(mockedStringList.get(1)).thenReturn("primerio");

		System.out.println(mockedIntegerList.get(0));
		System.out.println(mockedStringList.get(1));

		verify(mockedStringList).get(eq(1));
		verify(mockedIntegerList).get(any(Integer.class));
	}

	@Test
	public void testNumberOfInvocation() {

		List<String> mockedList = mock(List.class);

		mockedList.add("um");

		mockedList.add("dois");
		mockedList.add("dois");

		mockedList.add("tres");
		mockedList.add("tres");
		mockedList.add("tres");

		verify(mockedList).add("um");
		verify(mockedList, times(1)).add("um");

		verify(mockedList, times(2)).add("dois");
		verify(mockedList, times(3)).add("tres");

		verify(mockedList, atMostOnce()).add("um");
		verify(mockedList, atLeastOnce()).add("tres");
		verify(mockedList, atLeast(2)).add("tres");
		verify(mockedList, atMost(5)).add("tres");
	}

	@Test
	public void testRunnerAndMock() {

//		when(mockedGlobalList.get(0)).thenReturn("primerio");

	}

	@Test
	public void testNever() {

		library.addLivro("Livro Teste");

		verify(library, never()).addLivro("nunca acontece");
		verifyNoInteractions(mockedGlobalList);
	}

	@Test
	public void testOrder() {
		List<String> mockedList = mock(List.class);

		mockedList.add("primeiro");
		mockedList.add("segundo");

		InOrder inOrder = inOrder(mockedList);

		inOrder.verify(mockedList).add("primeiro");
		inOrder.verify(mockedList).add("segundo");

		List<String> firstMock = mock(List.class);
		List<String> secondMock = mock(List.class);

		firstMock.add("primeiro");
		secondMock.add("segundo");

		InOrder inOrderTwo = inOrder(firstMock, secondMock);

		inOrderTwo.verify(firstMock).add("primeiro");
		inOrderTwo.verify(secondMock).add("segundo");

	}

	@Test
	public void testSpyFirst() {

		List<String> list = new LinkedList();
		List<String> spy = spy(list);

		when(spy.size()).thenReturn(100);

		spy.add("um");
		spy.add("dois");

		System.out.println(spy.get(0));

		System.out.println(spy.size());

		verify(spy).add("um");
		verify(spy).add("dois");
	}

	@Test
	public void testSpySecond() {

		List<String> list = new LinkedList();
		List<String> spy = spy(list);

//		when(spy.get(0)).thenReturn("foo");

		doReturn("foo").when(spy).get(0);
		
		System.out.println(spy.get(0));
	}

}
