package third.facade;
//
///**
// * writeme: Should be the description of the class
// *
// * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
// */
//
//public class Test {
//	public static void main(String[] args) {
//		ThreadTest t1 = new ThreadTest("Thread1", 1000);
//		ThreadTest t2 = new ThreadTest("Thread2", 2000);
//
//		Object object = new Object();
//
//		t1.setO(object);
//		t2.setO(object);
//		t1.start();
//		t2.start();
//	}
//
//	static class Object {
//		private String name = "object";
//		private static String named = "object";
//
//		public String getName() {
//			Class clazz =this.getClass();
//
//			return name;
//		}
//
//		public synchronized static String getNamed() {
//			named = named + "f";
//
//			return named;
//		}
//
//		synchronized public void setName(String name, int sleepTime) throws InterruptedException {
//			this.name = name;
//			Thread.sleep(sleepTime);
//		}
//	}
//
//	static class ThreadTest extends Thread {
//		private String threadName;
//		private int timeout;
//		private Object o;
//
//		ThreadTest(String threadName, int timeout) {
//			this.threadName = threadName;
//			this.timeout = timeout;
//		}
//
//		public void setO(Object o) {
//			this.o = o;
//		}
//
//		public void run() {
//			System.out.println("Start Thread name: " + threadName + ": object name " + o.getName());
//			Object.getNamed();
//			try {
//				o.setName(threadName, timeout);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			System.out.println("End Thread name: " + threadName + ": object name " + o.getName());
//		}
//	}
//}


//import third.model.Users;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class Test {
//
//	public static void main(String[] args) {
//		DBUserQueryer dbUserQueryer = new DBUserQueryer();
//		String username = "user1";
//		String password = "1111";
//		boolean enabled = true;
//		Set<String> authotities = new HashSet<String>();
//		authotities.add("ROLE_USER");   // ROLE_ADMIN
//
//		Users user = new Users(username, password, authotities, enabled);
//		dbUserQueryer.saveUser(user);
//
//	}
//}