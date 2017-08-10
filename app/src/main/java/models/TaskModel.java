package models;

/**
 * Created by shajeelafzal on 08/08/2017.
 */

public class TaskModel {

    private String taskDetail;
    private boolean isPriority;

    public TaskModel() {
    }

    public TaskModel(String taskDetail, boolean isPriority) {
        this.taskDetail = taskDetail;
        this.isPriority = isPriority;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    public boolean isPriority() {
        return isPriority;
    }

    public void setPriority(boolean priority) {
        isPriority = priority;
    }
}
