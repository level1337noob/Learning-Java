package threadpool_internals;

import java.lang.Thread;

import threadpool_internals.TaskState;

public class Task extends Thread {
	private TaskState taskState = TaskState.TASK_DORMANT;

	public Task(Runnable r) {
		super(r);
	}

	public TaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(TaskState state) {
		taskState = state;
	}
}