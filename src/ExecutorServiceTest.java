import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceTest {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<Future<String>> resultList = new ArrayList<Future<String>>();

		// ����10������ִ��
		for (int i = 0; i < 10; i++) {
			// ʹ��ExecutorServiceִ��Callable���͵����񣬲������������future������
			Future<String> future = executorService.submit(new TaskWithResult(i));
			// ������ִ�н���洢��List��
			resultList.add(future);
		}
		executorService.shutdown();

		// ��������Ľ��
		for (Future<String> fs : resultList) {
			try {
				System.out.println(fs.get()); // ��ӡ�����̣߳�����ִ�еĽ��
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				executorService.shutdownNow();
				e.printStackTrace();
				return;
			}
		}
	}
}

class TaskWithResult implements Callable<String> {
	private int id;

	public TaskWithResult(int id) {
		this.id = id;
	}

	/**
	 * ����ľ�����̣�һ�����񴫸�ExecutorService��submit��������÷����Զ���һ���߳���ִ�С�
	 * 
	 * @return
	 * @throws Exception
	 */
	public String call() throws Exception {
		System.out.println("call()�������Զ�����,�ɻ����             " + Thread.currentThread().getName());
		if (new Random().nextBoolean())
			throw new TaskException("Meet error in task." + Thread.currentThread().getName());
		// һ��ģ���ʱ�Ĳ���
		for (int i = 999999999; i > 0; i--)
			;
		return "call()�������Զ����ã�����Ľ���ǣ�" + id + "    " + Thread.currentThread().getName();
	}
}

class TaskException extends Exception {
	public TaskException(String message) {
		super(message);
	}
}