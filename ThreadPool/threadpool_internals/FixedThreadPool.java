package threadpool_internals;

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Thread;

import threadpool_internals.TaskState;
import threadpool_internals.Task;

public class FixedThreadPool implements Runnable {
	static final int MAX_TASKS = 6;
	ArrayList<Task> pendingTasks;
	ArrayList<Task> currentExecutingTasks;
	Object lock = new Object(); // lock object for synchronization

	public FixedThreadPool() {
		pendingTasks = new ArrayList<>();
		currentExecutingTasks = new ArrayList<>();
		for (int i = 0; i < MAX_TASKS; i++) {
			currentExecutingTasks.add(null);
		}
	}

	public void addTaskToPendingQueue(Task task) {
		synchronized (lock) {
			task.setTaskState(TaskState.TASK_READY);
			pendingTasks.add(task);
			lock.notify();
		}
	}

	public void run() {
		try {
			while (true) {
				synchronized (lock) {
					checkAndAddTaskToThreadPool();
					lock.wait();
				}
			}
		} catch (InterruptedException interruptException) {
			System.out.println("Interrupted thread pool");
			interruptException.printStackTrace();
		}
	}

	private void checkAndAddTaskToThreadPool() throws InterruptedException {
		int i = 0;
		Iterator<Task> pt = pendingTasks.iterator();
		
		while (i < currentExecutingTasks.size()) {
			Task t = currentExecutingTasks.get(i);
			if (t != null) {
				if (t.isAlive() == false) { // remove the thread from the current executing tasks
					t.setTaskState(TaskState.TASK_STOPPED);
					t.join();
					System.out.println("Removed thread " + t.getName());
					currentExecutingTasks.set(i, null);
					continue;
				}
			} else { // Find a task in the pending tasks queue
				if (pt.hasNext()) {
					Task p = pt.next();
					if (p.getTaskState() != TaskState.TASK_READY) { // Internal error
						System.out.println("checkAndAddTaskToThreadPool: This task requires a ready task state removing from pending tasks (internal error) don't use an already used task");
					} else {
						p.setTaskState(TaskState.TASK_RUNNING);
						currentExecutingTasks.set(i, p);
						System.out.println("Added task " + p.getName() + " to executing queue");
						p.start();
					}
					
					// So now remove that pending task and place it in the current executing tasks
					pt.remove();
				} else { // no found task wait for it...
				}
			}

			i++;
		}
	}
}